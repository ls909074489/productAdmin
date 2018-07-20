package com.yy.frame.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yy.common.enums.BillStatus;
import com.yy.frame.entity.SuperEntity;
import com.yy.frame.security.ShiroUser;
import com.yy.modules.sys.documents.BillCodeService;
import com.yy.workflow.flow.WorkFlowService;

/**
 * IService基本实现
 * 
 * @author kevin
 * 
 * @param <T>
 *            实体类型
 * @param <PK>
 *            主键类型
 */
// 所有service都要加上rollbackFor={Exception.class}才能回滚
@Service
@Transactional(rollbackFor = { Exception.class })
public abstract class SuperServiceImpl<T extends SuperEntity, PK extends Serializable> extends BaseServiceImpl<T, PK> {

	@Autowired
	BillCodeService billCodeService;

	@Autowired
	WorkFlowService<T> workFlowService;

	@Override
	@Transactional
	public T doAdd(T entity) throws ServiceException {
		entity.setBilltype(entity.getBilltype());
		if (isCreateBillCode() && StringUtils.isEmpty(entity.getBillcode())) {
			entity.setBillcode(billCodeService.createBillCode(entity.getBilltype(), null));
		}
		return super.doAdd(entity);
	}

	@Override
	public void beforeAdd(T entity) throws ServiceException {
		verification(entity);
		super.beforeAdd(entity);
	}

	/**
	 * 是否生成单据号
	 * 
	 * @return
	 */
	public Boolean isCreateBillCode() {
		return true;
	}

	/**
	 * 重写删除操作，业务单据逻辑删除
	 */
	@Override
	@Transactional
	public void doDelete(PK pk) throws ServiceException {
		try {
			T entity = this.getOne(pk);
			SuperEntity superEntity = (SuperEntity) entity;
			beforeDelete(entity);
			superEntity.setStatus(0);
			save(entity);
			afterDelete(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	// 批量提交
	public List<T> doBatchSubmit(PK[] pks) throws ServiceException {
		try {
			List<T> list = new ArrayList<T>();
			T entity = null;
			if (pks != null && pks.length > 0) {
				for (PK pk : pks) {
					entity = this.getOne(pk);
					list.add(this.doSubmit(entity));
				}
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

	}

	// 批量撤销提交
	public List<T> doBatchUnSubmit(PK[] pks) throws ServiceException {
		try {
			List<T> list = new ArrayList<T>();
			T entity = null;
			if (pks != null && pks.length > 0) {
				for (PK pk : pks) {
					entity = this.getOne(pk);
					list.add(this.doUnSubmit(entity));
				}
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

	}

	// 提交
	public T doSubmit(T entity) throws ServiceException {
		try {
			if (BillStatus.FREE.toStatusValue() != entity.getBillstatus()
					&& BillStatus.REJECT.toStatusValue() != entity.getBillstatus()) {
				throw new ServiceException("非自由态或退回态的单据不能提交！");
			}
			beforeSubmit(entity);
			if (entity != null) {
				// 清空审核人、审核时间、审核意见
				entity.setApproveremark(null);
				entity.setApprove_time(null);
				entity.setApprovetime(null);
				entity.setApprover(null);
				entity.setApprovername(null);
				// 更改单据状态
				entity.setBillstatus(BillStatus.SUBMIT.toStatusValue());
				entity.setSubmittime(new Date());
			}
			entity = this.save(entity);
			// 审批流 - 提交
			doWorkFlow(entity, "submit");
			afterSubmit(entity);
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

	}

	// 提交之后
	public void afterSubmit(T entity) throws ServiceException {

	}

	// 提交之前
	public void beforeSubmit(T entity) throws ServiceException {

	}

	// 撤销提交
	public T doUnSubmit(T entity) throws ServiceException {
		try {
			if (BillStatus.SUBMIT.toStatusValue() != entity.getBillstatus()) {
				throw new ServiceException("非提交态单据不能撤销提交！");
			}
			beforeUnSubmit(entity);
			if (entity != null) {
				entity.setBillstatus(BillStatus.FREE.toStatusValue());
			}
			doWorkFlow(entity, "unSubmit");
			entity = this.save(entity);
			afterUnSubmit(entity);
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

	}

	// 撤销提交之后
	public void afterUnSubmit(T entity) {

	}

	// 撤销提交之前
	public void beforeUnSubmit(T entity) {

	}

	// 退回
	public T doRevoke(T entity, String approveRemark) throws ServiceException {
		try {
			if (BillStatus.SUBMIT.toStatusValue() != entity.getBillstatus()
					&& BillStatus.INAPPROVED.toStatusValue() != entity.getBillstatus()) {
				throw new ServiceException("非提交态或审批态的单据不能退回！");
			}
			beforeRevoke(entity);
			if (entity != null) {
				entity.setApprover(ShiroUser.getCurrentUserEntity().getUuid());
				entity.setApprovername(ShiroUser.getCurrentUserEntity().getUsername());
				entity.setApprovetime(new Date());
				entity.setBillstatus(BillStatus.REJECT.toStatusValue());
				entity.setApproveremark(approveRemark);
			}
			entity = this.save(entity);
			// 审批流 - 退回
			doWorkFlow(entity, "revoke");
			afterRevoke(entity);
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

	}

	// 退回之后
	public void afterRevoke(T entity) throws ServiceException {

	}

	// 退回之前
	public void beforeRevoke(T entity) throws ServiceException {

	}

	// 批量退回
	public List<T> doBatchRevoke(PK[] pks, String approveRemark) {
		try {
			List<T> list = new ArrayList<T>();
			T entity = null;
			if (pks != null && pks.length > 0) {
				for (PK pk : pks) {
					entity = this.getOne(pk);
					list.add(this.doRevoke(entity, approveRemark));
				}
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	// 审核
	public T doApprove(T entity, String approveRemark) throws ServiceException {
		try {
			if (BillStatus.SUBMIT.toStatusValue() != entity.getBillstatus()
					&& BillStatus.INAPPROVED.toStatusValue() != entity.getBillstatus()) {
				throw new ServiceException("非提交态和审批态的单据不能审核！");
			}

			beforeApprove(entity);
			if (entity != null) {
				if (StringUtils.isEmpty(entity.getApprover())) {
					entity.setApprover(ShiroUser.getCurrentUserEntity().getUuid());
					entity.setApprovername(ShiroUser.getCurrentUserEntity().getUsername());
				}
				entity.setApprovetime(new Date());
				entity.setApproveremark(approveRemark);
			}

			// 审批流 - 审核
			doWorkFlow(entity, "approve");

			// 如果没有审批流 设置单据状态未 审核中或者审批通过，则设置为审核通过。
			if (entity.getBillstatus() == BillStatus.SUBMIT.toStatusValue()) {
				entity.setBillstatus(BillStatus.APPROVAL.toStatusValue());
			}

			// 如果还有审批节点，直接返回。
			if (entity.getBillstatus() == BillStatus.INAPPROVED.toStatusValue()) {
				return this.save(entity);
			} else {
				entity = this.save(entity);
			}

			afterApprove(entity);
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

	}

	// 审核之后
	public void afterApprove(T entity) throws ServiceException {

	}

	// 审核之前
	public void beforeApprove(T entity) throws ServiceException {

	}

	/**
	 * 平台提供检验方法
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	public void verification(T entity) throws ServiceException {

	}

	// 反审核
	public T doUnApprove(T entity) throws ServiceException {
		try {
			if (BillStatus.APPROVAL.toStatusValue() != entity.getBillstatus()) {
				throw new ServiceException("非通过态的单据不能反审核！");
			}
			beforeUnApprove(entity);
			if (entity != null) {
				entity.setApproveremark(null);
				entity.setApprove_time(null);
				entity.setApprovetime(null);
				entity.setApprover(null);
				entity.setApprovername(null);
				entity.setBillstatus(BillStatus.REJECT.toStatusValue());

			}
			entity = this.save(entity);
			// 审批流 - 反审核
			doWorkFlow(entity, "unApprove");

			afterUnApprove(entity);
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

	}

	// 反审核之后
	public void afterUnApprove(T entity) throws ServiceException {

	}

	// 反审核之前
	public void beforeUnApprove(T entity) throws ServiceException {

	}

	// 批量审核
	public List<T> doBatchApprove(PK[] pks, String approveRemark) {
		try {
			// 批量审批之前
			beforeBatchApprove(pks);
			List<T> list = new ArrayList<T>();
			T entity = null;
			if (pks != null && pks.length > 0) {
				for (PK pk : pks) {
					entity = this.getOne(pk);
					list.add(this.doApprove(entity, approveRemark));
				}
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	// 批量反审核
	public List<T> doBatchUnApprove(PK[] pks, String approveRemark) {
		try {
			List<T> list = new ArrayList<T>();
			T entity = null;
			if (pks != null && pks.length > 0) {
				for (PK pk : pks) {
					entity = this.getOne(pk);
					entity.setApproveremark(approveRemark);
					list.add(this.doUnApprove(entity));
				}
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	// 保存并提交
	public T doSaveSubmit(T entity) throws ServiceException {
		try {
			// 保存-提交
			if (entity.getUuid() == null || "".equals(entity.getUuid())) {
				entity = this.doAdd(entity);
				entity = this.doSubmit(entity);
			}
			// 修改-提交
			else {
				entity = this.doUpdate(entity);
				entity = this.doSubmit(entity);
			}
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());

		}

	}

	public void doCloseBills(PK[] pks) throws ServiceException {
		try {
			for (PK pk : pks) {
				T entity = this.getOne(pk);
				doCloseBill(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	public void doCloseBill(T entity) throws ServiceException {
		try {
			beforeCloseBill(entity);
			entity.setClosestatus(0);
			this.save(entity);
			afterCloseBill(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	// 关闭之后
	public void afterCloseBill(T entity) throws ServiceException {

	}

	// 关闭之前
	public void beforeCloseBill(T entity) throws ServiceException {

	}

	/**
	 * 审批流
	 * 
	 * @param entity
	 * @param action
	 * @return
	 */
	public T doWorkFlow(T entity, String action) {
		return workFlowService.doWorkFlow(entity, action, getFlowcode(entity));
	}

	/**
	 * 获取审批流的唯一编码，可重写·
	 * 
	 * @param entity
	 * @return
	 */
	public String getFlowcode(T entity) {
		return entity.getBilltype();
	}

	// 批量审批之前
	protected void beforeBatchApprove(PK[] pks) {
	}
}
