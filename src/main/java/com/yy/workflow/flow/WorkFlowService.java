package com.yy.workflow.flow;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yy.common.enums.BillStatus;
import com.yy.common.utils.DateUtil;
import com.yy.frame.entity.SuperEntity;
import com.yy.frame.security.ShiroUser;
import com.yy.modules.sys.message.MessageEntity;
import com.yy.modules.sys.message.MessageService;
import com.yy.workflow.flownode.FlownodeEntity;
import com.yy.workflow.flownode.FlownodeService;
import com.yy.workflow.group.ProcessGroupEntity;
import com.yy.workflow.group.ProcessGroupService;
import com.yy.workflow.item.ProcessItemEntity;
import com.yy.workflow.item.ProcessItemService;
import com.yy.workflow.user.ProcessUserGroupRelationEntity;
import com.yy.workflow.user.ProcessUserGroupRelationService;

@Component
@Transactional
public class WorkFlowService<T extends SuperEntity> {

	@Autowired
	ProcessGroupService processGroupService;

	@Autowired
	ProcessItemService processItemService;

	@Autowired
	MessageService messageService;

	@Autowired
	FlownodeService flownodeService;

	@Autowired
	ProcessUserGroupRelationService processUserGroupRelationService;

	/**
	 * 启动审批流-业务流
	 * 
	 * @param entity
	 *            实体
	 * @param action
	 *            动作
	 * @param flowcode
	 *            流程编码 ，唯一的编码，流程里面的单据类型配置
	 */
	public T doWorkFlow(T entity, String action, String flowcode) throws ServiceException {
		if (StringUtils.isEmpty(flowcode)) {
			return entity;
		}

		// 查询当前流程节点
		FlownodeEntity flownodeEntity = flownodeService.findByBilltypeAndBillid(flowcode, entity.getUuid());

		ProcessGroupEntity groupentity = null;
		if (flownodeEntity != null) {
			groupentity = processGroupService.getOne(flownodeEntity.getFlowgroupid());
		} else {
			// 跟进单据编码获取流程组
			groupentity = processGroupService.getGroupByCode(flowcode);
		}

		// 没有审批流
		if (groupentity == null) {
			// 如果是审批的时候
			if ("approve".equals(action)) {
				entity.setBillstatus(BillStatus.APPROVAL.toStatusValue());
			}
			return entity;
		}

		// 流程项
		List<ProcessItemEntity> processItemList = processItemService.findItemByProcessgroup(groupentity);
		// 找到下一个流程节点
		if (processItemList.size() == 0) {
			return entity;
		}

		// 发送业务消息

		/* 以下是审批流程 */
		if (flownodeEntity != null) { // 如果单据启用审批流程

			// 查询所有没有处理的消息
			List<MessageEntity> msgList = messageService.findByBillidAndFlowid(groupentity.getGroup_code(),
					entity.getUuid(), flownodeEntity.getFlowid());

			// 如果是撤销提交，删除相关的消息
			if ("unSubmit".equals(action)) {
				messageService.delete(msgList);
				flownodeService.delete(flownodeEntity);
				return entity;
			}

			// 如果单据状态为 审批通过，则流程不需要往下执行
			if (entity.getBillstatus() == BillStatus.APPROVAL.toStatusValue()) {
				dealMessages(entity, msgList);
				flownodeService.delete(flownodeEntity);
				return entity;
			}

			// 判断当前用户是否可以审核
			boolean isApprove = false;
			for (MessageEntity msgentity : msgList) {
				if (ShiroUser.getCurrentUserEntity().getUuid().equals(msgentity.getReceiver())) {
					isApprove = true;
				}
			}

			if ("unApprove".equals(action)) {
				isApprove = true;
			}
			// 如果待办消息中没有该用户，则不能审批
			if (!isApprove) {
				throw new ServiceException("不能操作！您不是当前审核人");
			}

			// 处理消息
			dealMessages(entity, msgList);

			// 找出最新的流程节点
			ProcessItemEntity nowProcessItem = processItemService.getOne(flownodeEntity.getFlowid());

			// 如果有下一个流程节点
			if (nowProcessItem != null && !StringUtils.isEmpty(nowProcessItem.getNextflowcode())) {

				// 取到下一个流程节点
				ProcessItemEntity nextProcessItem = getNextFlowNode(processItemList, nowProcessItem);

				// 如果找不到下一个流程节点
				if (nextProcessItem == null) {
					throw new ServiceException("找不到下一岗的审批流程配置。");
				}

				// "1：自由态，2：提交态，3：审批态，4：退回态，5：通过态")
				// 如果是审批动作
				if ("approve".equals(action)) {

					// 如果手工设置状态为 5，说明需要手工总结流程。
					if (entity.getBillstatus() == 5) {
						// 发送业务消息
						sendWorkMessage(entity, groupentity, action);

						// 清除当前流程
						flownodeService.delete(flownodeEntity);
						return entity;
					} else if ("1".equals(nextProcessItem.getFlownode()) || "2".equals(nextProcessItem.getFlownode())) {// 并且流程是审批节点的，设置审批状态为审批中
						entity.setBillstatus(3); // 设置审批状态为审批中
					} else if ("0".equals(nextProcessItem.getFlownode())) {
						entity.setBillstatus(5);// 设置为审批结束
						// 审批结束，删除当前流程
						flownodeService.delete(flownodeEntity);

						// 并且发送一个业务消息告诉 单据的创建人、提交人审核通过了。
						sendWorkMessage(entity, groupentity, action);
						return entity;
					}

					// 设置流程节点
					setFlownode(entity, nextProcessItem, groupentity, flownodeEntity);

					// 发送流程消息
					sendFlowMessage(entity, nextProcessItem, groupentity, action);

				} else if ("unApprove".equals(action)) {// 反审核
					// 发送业务消息
					sendWorkMessage(entity, groupentity, action);
					// 发送 审批流消息
					sendUnApproveMsg(entity, groupentity);
					// 清除当前流程
					flownodeService.delete(flownodeEntity);
				} else if ("revoke".equals(action)) {// 退回
					// 发送业务消息
					sendWorkMessage(entity, groupentity, action);

					// 清除当前流程
					flownodeService.delete(flownodeEntity);
				}

			} else {// 如果没有启用审批流程

				// 发送业务消息
				// sendFlowMessage(entity, nowProcessItem, groupentity, action);
			}

			return entity;

		} else if ("submit".equals(action)) { // 提交状态，找不到单据的流程-则开始流程
			ProcessItemEntity nowProcessItem = null;
			for (ProcessItemEntity processItemEntity : processItemList) {
				// 找到 审批流的 + 是审批开始节点的
				if ("1".equals(processItemEntity.getFlowtype()) && "1".equals(processItemEntity.getFlownode())) {
					nowProcessItem = processItemEntity;
					break;
				}
			}

			// 如果找不到开始流程节点，则结束审批流
			if (nowProcessItem == null) {
				throw new ServiceException("找不到开始的流程配置。");
			}

			// 提交发送消息
			sendSubmitMsg(entity, nowProcessItem, groupentity);

			// 流程刚发起，需要查找下一个流程
			ProcessItemEntity nextProcessItem = getNextFlowNode(processItemList, nowProcessItem);

			// 如果找不到下一个流程节点
			if (nextProcessItem == null) {
				throw new ServiceException("找不到下一岗的审批流程配置。");
			}
			// 设置流程节点
			setFlownode(entity, nextProcessItem, groupentity, flownodeEntity);

			// 发送流程消息
			sendFlowMessage(entity, nextProcessItem, groupentity, "approve");

			return entity;
		} else if ("unApprove".equals(action)) { // 反审核
			// 发送业务消息
			sendWorkMessage(entity, groupentity, action);

			// 发送 审批流消息
			sendUnApproveMsg(entity, groupentity);
		} else {
			// 如果不是提交的状态，加上找不到当前流程，不做任何处理
		}

		return entity;
	}

	/**
	 * 处理消息
	 * 
	 * @param entity
	 * @param msgList
	 */
	private void dealMessages(T entity, List<MessageEntity> msgList) {
		// 把所有的该单据的消息设置为已经处理
		if (msgList != null && msgList.size() > 0) {
			for (MessageEntity msgEntity : msgList) {
				if (ShiroUser.getCurrentUserEntity().getUuid().equals(msgEntity.getReceiver())) {
					msgEntity.setSuggestion(getFlowResult(String.valueOf(entity.getBillstatus()))
							+ (entity.getApproveremark() == null ? "" : entity.getApproveremark()));
					messageService.setNoticeIsdeal(msgEntity);
				} else {
					messageService.delete(msgEntity);
				}
			}
		}
	}

	/**
	 * 获取下一个流程节点
	 * 
	 * @param processItemList
	 * @param nowProcessItem
	 * @param groupentity
	 * @return
	 */
	private ProcessItemEntity getNextFlowNode(List<ProcessItemEntity> processItemList,
			ProcessItemEntity nowProcessItem) {
		for (ProcessItemEntity processItemEntity : processItemList) {
			// 找到 审批流的 + 匹配流程编码
			if ("1".equals(processItemEntity.getFlowtype())
					&& nowProcessItem.getNextflowcode().equals(processItemEntity.getCode())) {
				return processItemEntity;
			}
		}
		return null;
	}

	/**
	 * 设置审批流节点
	 * 
	 * @param flownodeEntity
	 */
	private void setFlownode(T entity, ProcessItemEntity pEntity, ProcessGroupEntity groupentity,
			FlownodeEntity flownodeEntity) throws ServiceException {
		if (flownodeEntity == null) {
			flownodeEntity = new FlownodeEntity();
		}
		flownodeEntity.setFlowgroupid(groupentity.getUuid());
		flownodeEntity.setBillid(entity.getUuid());
		flownodeEntity.setBilltype(groupentity.getGroup_code());
		flownodeEntity.setFlowcode(pEntity.getCode());
		flownodeEntity.setFlowid(pEntity.getUuid());
		flownodeService.doAdd(flownodeEntity);
	}

	/**
	 * 获取审批结果
	 * 
	 * @param dealresult
	 * @return
	 */
	private String getFlowResult(String dealresult) {
		if (StringUtils.isEmpty(dealresult)) {
			return "";
		}
		if ("3".equals(dealresult) || "5".equals(dealresult)) {
			return "<span class='label label-sm label-success'>通过</span>";
		} else if ("4".equals(dealresult)) {
			return "<span class='label label-sm label-danger'>退回</span>";
		} else {
			return "";
		}
	}

	/**
	 * 发送审批消息
	 * 
	 * @param entity
	 * @param nowEntity
	 * @param groupentity
	 * @return
	 * @throws ServiceException
	 */
	private void sendFlowMessage(T entity, ProcessItemEntity nowEntity, ProcessGroupEntity groupentity, String action)
			throws ServiceException {

		if (nowEntity.getUsergroup() != null) {
			List<ProcessUserGroupRelationEntity> puList = processUserGroupRelationService
					.findByItemId(nowEntity.getUsergroup().getUuid());
			// 这里可以开发过滤用户的组织
			for (ProcessUserGroupRelationEntity puEntity : puList) {
				// 如果发送类型，接收者要求是本机机构
				if ("2".equals(nowEntity.getFlowsend())
						&& !puEntity.getUser().getOrgid().equals(entity.getOrg().getUuid())) {
					continue;
				}

				// 发送类型，接收者要求是 上级机构
				if ("3".equals(nowEntity.getFlowsend())
						&& !puEntity.getUser().getOrgid().equals(entity.getOrg().getParentid())) {
					continue;
				}

				MessageEntity messageEntity = new MessageEntity();
				messageEntity.setBilltype(groupentity.getGroup_code());
				messageEntity.setBillid(entity.getUuid());
				messageEntity.setFlowid(nowEntity.getUuid());
				messageEntity.setIsnew("0");
				messageEntity.setFlowname(nowEntity.getName());
				messageEntity.setReceiver(puEntity.getUser().getUuid());
				messageEntity.setReceivername(puEntity.getUser().getUsername());

				messageEntity.setMsgtype("1");// 待办消息
				// 如果没有下一岗流程，发送业务消息
				if (StringUtils.isEmpty(nowEntity.getNextflowcode())) {
					messageEntity.setMsgtype("2");// 业务消息
				}

				messageEntity.setTitle(groupentity.getGroup_name() + "（" + entity.getBillcode() + "）");
				// messageEntity.setContent(groupentity.getGroup_name() + "（" + entity.getBillcode() + "）");

				if (!StringUtils.isEmpty(nowEntity.getUrl())) {
					messageEntity.setLink(nowEntity.getUrl() + "?uuid=" + entity.getUuid());
				} else if (!StringUtils.isEmpty(groupentity.getLink())) {
					messageEntity.setLink(groupentity.getLink() + "?uuid=" + entity.getUuid());
				} else {
					messageEntity.setLink("");
				}

				messageEntity.setIsdeal("0");
				// messageEntity.setDealresult(String.valueOf(entity.getBillstatus()));
				messageEntity.setSendtime(DateUtil.addSecond(new Date(), 1));
				messageService.sendMsg(messageEntity);
			}

		}

	}

	/**
	 * 发送 业务消息流程 主要用于审批通过后发送 业务消息的通知
	 * 
	 * @param entity
	 * @param groupentity
	 * @param action
	 */
	private void sendWorkMessage(T entity, ProcessGroupEntity groupentity, String action) throws ServiceException {
		MessageEntity messageEntity = new MessageEntity();
		messageEntity.setBilltype(groupentity.getGroup_code());
		messageEntity.setBillid(entity.getUuid());

		messageEntity.setFlowid("");
		messageEntity.setFlowname("");
		messageEntity.setReceiver(entity.getCreator());
		messageEntity.setReceivername(entity.getCreatorname());

		messageEntity.setMsgtype("2");// 业务消息

		messageEntity.setTitle(groupentity.getGroup_name() + "（" + entity.getBillcode() + "）");//
		// messageEntity.setContent(groupentity.getGroup_name() + "（" + entity.getBillcode() + "）");

		messageEntity.setIsnew("0"); // 新消息
		messageEntity.setReceivetime(new Date());
		messageEntity.setDealresult(String.valueOf(entity.getBillstatus()));
		messageEntity.setIsdeal("1");
		messageEntity.setDealtime(new Date());
		if (!StringUtils.isEmpty(groupentity.getLink())) {
			messageEntity.setLink(groupentity.getLink() + "?uuid=" + entity.getUuid());
		} else {
			messageEntity.setLink("");
		}
		messageService.sendMsg(messageEntity);
	}

	/**
	 * 反审核发送消息
	 * 
	 * @param entity
	 * @param nowProcessItem
	 * @param groupentity
	 * @param flownodeEntity
	 */
	private void sendUnApproveMsg(T entity, ProcessGroupEntity groupentity) throws ServiceException {
		// 提交的，单独发送一个反审核的流程信息，信息为已读状态
		MessageEntity messageEntity = new MessageEntity();
		messageEntity.setBilltype(groupentity.getGroup_code());
		messageEntity.setBillid(entity.getUuid());

		messageEntity.setFlowid(null);
		messageEntity.setFlowname("反审核");
		messageEntity.setReceiver(ShiroUser.getCurrentUserEntity().getUuid());
		messageEntity.setReceivername(ShiroUser.getCurrentUserEntity().getUsername());

		messageEntity.setMsgtype("1");// 待办消息

		messageEntity.setTitle(groupentity.getGroup_name() + "（" + entity.getBillcode() + "）");
		// messageEntity.setContent(groupentity.getGroup_name() + "（" + entity.getBillcode() + "）");

		messageEntity.setIsnew("1"); // 新消息
		messageEntity.setReceivetime(new Date());
		messageEntity.setIsdeal("1");// 处理消息
		messageEntity.setDealresult(String.valueOf(entity.getBillstatus()));
		messageEntity.setDealtime(new Date());
		if (!StringUtils.isEmpty(groupentity.getLink())) {
			messageEntity.setLink(groupentity.getLink() + "?uuid=" + entity.getUuid());
		} else {
			messageEntity.setLink("");
		}

		messageEntity.setSuggestion("<span class='label label-sm label-danger'>反审核</span>");

		messageService.sendMsg(messageEntity);
	}

	/**
	 * 提交发送消息
	 * 
	 * @param entity
	 * @param nowProcessItem
	 * @param groupentity
	 * @param flownodeEntity
	 */
	private void sendSubmitMsg(T entity, ProcessItemEntity nowProcessItem, ProcessGroupEntity groupentity)
			throws ServiceException {
		// 提交的，单独发送一个提交的流程信息，信息为已读状态
		MessageEntity messageEntity = new MessageEntity();
		messageEntity.setBilltype(groupentity.getGroup_code());
		messageEntity.setBillid(entity.getUuid());

		messageEntity.setFlowid(nowProcessItem.getUuid());
		messageEntity.setFlowname(nowProcessItem.getName());
		messageEntity.setReceiver(ShiroUser.getCurrentUserEntity().getUuid());
		messageEntity.setReceivername(ShiroUser.getCurrentUserEntity().getUsername());

		messageEntity.setMsgtype("1");// 待办消息

		messageEntity.setTitle(groupentity.getGroup_name() + "（" + entity.getBillcode() + "）");
		// messageEntity.setContent(groupentity.getGroup_name() + "（" + entity.getBillcode() + "）");

		messageEntity.setIsnew("1"); // 新消息
		messageEntity.setReceivetime(new Date());
		messageEntity.setIsdeal("1");// 处理消息
		messageEntity.setDealresult(String.valueOf(entity.getBillstatus()));
		messageEntity.setDealtime(new Date());
		if (!StringUtils.isEmpty(groupentity.getLink())) {
			messageEntity.setLink(groupentity.getLink() + "?uuid=" + entity.getUuid());
		} else {
			messageEntity.setLink("");
		}

		messageEntity.setSuggestion("<span class='label label-sm label-default'>提交</span>");

		messageService.sendMsg(messageEntity);
	}
}
