package com.yy.modules.sys.message;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yy.frame.dao.IBaseDAO;
import com.yy.frame.security.ShiroUser;
import com.yy.frame.service.BaseServiceImpl;
import com.yy.workflow.user.ProcessUserGroupRelationEntity;
import com.yy.workflow.user.ProcessUserGroupRelationService;

/**
 * 系统消息service
 * 
 * @ClassName: MessageService
 * @author
 * @date 2016年38月28日 10:10:34
 */
@Service
@Transactional
public class MessageService extends BaseServiceImpl<MessageEntity, String> {


	@Autowired
	ProcessUserGroupRelationService processUserGroupRelationService;

	@Autowired
	private MessageDao dao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected IBaseDAO getDAO() {
		return dao;
	}

	@Override
	public void beforeAdd(MessageEntity entity) throws ServiceException {
		if (StringUtils.isEmpty(entity.getSender())) {
			entity.setSender(ShiroUser.getCurrentUserEntity().getUuid());
			entity.setSendername(ShiroUser.getCurrentUserEntity().getUsername());
		}
		if (entity.getSendtime() == null) {
			entity.setSendtime(new Date());
		}
		super.beforeAdd(entity);
	}

	/**
	 * 发送消息
	 * 
	 * @param msgEntity
	 *            消息实体
	 * @return
	 * @throws ServiceException
	 */
	public MessageEntity sendMsg(MessageEntity msgEntity) throws ServiceException {
		checkEntity(msgEntity);
		if ("0".equals(msgEntity.getIsdeal())) {
		}
		return this.doAdd(msgEntity);
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
	 * 发送消息
	 * 
	 * @param msgEntity
	 *            消息实体
	 * @return
	 * @throws ServiceException
	 */
	public MessageEntity setDefaultValue(MessageEntity msgEntity) throws ServiceException {
		if (StringUtils.isEmpty(msgEntity.getSender())) {

		}
		return msgEntity;
	}

	/**
	 * 验证实体
	 * 
	 * @param msgEntity
	 *            消息实体
	 * @return
	 */
	private void checkEntity(MessageEntity msgEntity) {
		if (StringUtils.isEmpty(msgEntity.getReceiver())) {
			throw new ServiceException("接收人必须填写。");
		}
		if (StringUtils.isEmpty(msgEntity.getTitle())) {
			throw new ServiceException("消息标题必须填写。");
		}
	}

	/**
	 * 获取 类型的未读取的消息，
	 * 
	 * @param string
	 * @return
	 * @throws ServiceException
	 */
	public List<MessageEntity> getMessageType(String msgtype) throws ServiceException {
		String userid = ShiroUser.getCurrentUserEntity().getUuid();
		return dao.getMessageType(msgtype, userid);
	}

	/**
	 * 获取某个人的所有消息
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public List<MessageEntity> getMessages() throws ServiceException {
		String userid = ShiroUser.getCurrentUserEntity().getUuid();
		return dao.getMessageByReceiver(userid);
	}

	/**
	 * 设置消息已读
	 * 
	 * @param uuid
	 */
	public void setNoticeIsnew(MessageEntity messageEntity) throws ServiceException {
		if (messageEntity == null) {
			return;
		}
		messageEntity.setIsnew("1");
		if ("2".equals(messageEntity.getMsgtype())) {
			// messageEntity.setIsdeal("1");
		}
		this.doUpdate(messageEntity);
	}

	/**
	 * 设置消息已经处理
	 * 
	 * @param uuid
	 */
	public void setNoticeIsdeal(String uuid) {
		MessageEntity messageEntity = this.getOne(uuid);
		messageEntity.setIsnew("1");
		messageEntity.setIsdeal("1");
		this.doUpdate(messageEntity);
	}

	/**
	 * 设置消息已经处理
	 * 
	 * @param uuid
	 */
	public void setNoticeIsdeal(MessageEntity messageEntity) {
		if ("0".equals(messageEntity.getIsnew())) {
			messageEntity.setIsnew("1");
			messageEntity.setReceivetime(new Date());
		}
		messageEntity.setDealtime(new Date());
		messageEntity.setIsdeal("1");
		this.doUpdate(messageEntity);
	}

	/**
	 * 设置消息已经处理
	 * 
	 * @param uuid
	 * @return
	 */
	public List<MessageEntity> findByBillidAndFlowid(String billtype, String billid, String flowid) {
		List<MessageEntity> messageList = dao.findMessageByBillidAndFlowid(billtype, billid, flowid);
		return messageList;
	}

	public List<MessageEntity> getMessageByBillid(String msgtype, String billtype, String billid) {
		List<MessageEntity> messageList = dao.getMessageByBillid(msgtype, billtype, billid);
		return messageList;
	}

	/**
	 * 发送消息，发送业务类型的消息 专用接口
	 * 
	 * @param receiverCode
	 *            发送的用户组编码，必须
	 * @param title
	 *            消息标题，必须
	 * @param content
	 *            消息内容
	 * @param billtype
	 *            单据类型，必须
	 * @param billid
	 *            单据的id，必须
	 * @param url
	 *            消息链接的URL：/demo/bill/onDetail 系统自动会加入 ？uuid=单据id的后缀
	 * @param isdeal
	 *            是否处理，true代表需要办理，false不需要办理，只是发送一个消息,默认true
	 * @param orgid
	 *            发送指定的组织，可以为空
	 * @throws ServiceException
	 */
	public void sendWorkMsg(String receiverCode, String title, String content, String billtype, String billid,
			String url, Boolean isdeal, String orgid) throws ServiceException {
		List<ProcessUserGroupRelationEntity> list = processUserGroupRelationService.findByGroupCode(receiverCode);
		for (ProcessUserGroupRelationEntity entity : list) {
			if (!StringUtils.isEmpty(orgid) && !entity.getUser().getOrgid().equals(orgid)) {
				continue;
			}
			MessageEntity msgEntity = new MessageEntity();
			msgEntity.setMsgtype("2");// "1：审批消息，2：业务消息，3：预警消息，4：系统消息，5：个人消息"

			msgEntity.setTitle(title);
			msgEntity.setContent(content);
			msgEntity.setBilltype(billtype);
			msgEntity.setBillid(billid);
			msgEntity.setLink(url + "?uuid=" + billid);
			if (StringUtils.isEmpty(url)) {
				// 以后再实现
			}
			msgEntity.setReceiver(entity.getUser().getUuid());
			msgEntity.setReceivername(entity.getUser().getUsername());

			msgEntity.setIsnew("0");

			msgEntity.setIsdeal("0");
			if (!isdeal) {
				msgEntity.setIsdeal("1");
			}
			sendMsg(msgEntity);
		}
	}

	/**
	 * 处理消息
	 * 
	 * @param billtype
	 *            单据类型，或者制定定义的单据类型编码
	 * @param billid
	 *            单据的uuid
	 * @param Suggestion
	 *            处理意见
	 * @param isdeal
	 *            撤回消息，或者处理消息，true或者null代表需要处理业务消息，false代表撤回消息
	 * 
	 * @throws ServiceException
	 */
	public void dealWorkMsg(String billtype, String billid, String Suggestion, Boolean isdeal) throws ServiceException {
		// 查询所有没有处理的消息
		List<MessageEntity> msgList = getMessageByBillid("2", billtype, billid);
		for (MessageEntity msgEntity : msgList) {
			if (!isdeal || !ShiroUser.getCurrentUserEntity().getUuid().equals(msgEntity.getReceiver())) {
				// 如果不是自己的消息，就删除
				delete(msgEntity);
			} else {
				msgEntity.setSuggestion(Suggestion == null ? "" : Suggestion);
				setNoticeIsdeal(msgEntity);
			}
		}
	}

}
