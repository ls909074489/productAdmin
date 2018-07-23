package com.yy.modules.info.interval;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yy.common.enums.AuditStatus;
import com.yy.frame.dao.IBaseDAO;
import com.yy.frame.security.ShiroUser;
import com.yy.frame.service.BaseServiceImpl;
import com.yy.modules.sys.message.MessageEntity;
import com.yy.modules.sys.message.MessageService;
import com.yy.modules.sys.user.UserEntity;

/**
 * 间隔信息
 * @author ls2008
 * @date 2017-11-18 21:54:30
 */
@Service
@Transactional(readOnly=true)
public class IntervalService extends BaseServiceImpl<IntervalEntity,String> {

	@Autowired
	private IntervalDao dao;
	@Autowired
	private MessageService messageService;

	protected IBaseDAO<IntervalEntity, String> getDAO() {
		return dao;
	}

	/**
	 * 保存日志
	 * @param user
	 * @param billId
	 * @param msg
	 */
	@Transactional
	private void saveLog(UserEntity user,String billId,String msg){
		MessageEntity message = new MessageEntity();
		message.setMsgtype("2");
		message.setBilltype("OrderLog");
		message.setBillid(billId);
		message.setTitle("审核流程");
		message.setContent(msg);
		message.setIsdeal("0");
//		if(entity.getAuditorUser()!=null){
//			message.setReceiver(entity.getAuditorUser().getUuid());
//			message.setReceivername(entity.getAuditorUser().getUsername());
//			message.setLink("/ver/slaveapprove/slaveCheckList?approveType=0");
//			message.setOpenType("1");
//			message.setTabName("信息点表核查（子站）");
//			message.setTabDataIndex("33055f27-c0ee-406c-afc4-36285820c7a0");
//		}
		messageService.doAdd(message);
	}


	
	@Transactional
	public void doFactoryConfirms(String[] pks,String approveRemark) {
		try {
			List<IntervalEntity> list = new ArrayList<IntervalEntity>();
			IntervalEntity entity = null;
			if (pks != null && pks.length > 0) {
				for (String pk : pks) {
					entity = this.getOne(pk);
					list.add(this.doFactoryConfirm(entity,approveRemark));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
	@Transactional
	public IntervalEntity doFactoryConfirm(IntervalEntity entity,String approveRemark) throws ServiceException {
		try {
			if (entity.getBillstatus() == null || AuditStatus.FREE.toStatusValue() == entity.getBillstatus()
					|| AuditStatus.SALES_REJECT.toStatusValue() == entity.getBillstatus()) {
				if (entity != null) {
					UserEntity user=ShiroUser.getCurrentUserEntity();
					
					entity.setFactoryConfrimTime(new Date());
					entity.setBillstatus(AuditStatus.FACTORY_CONFIRM.toStatusValue());
					
					//保存日志
					saveLog(user, entity.getUuid(),"工厂确认【"+user.getUsername()+"】");
				}
				entity = this.save(entity);
			} else {
				throw new ServiceException("当前单据状态不能审核！");
			}
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 * 业务员回价
	 * @param pks
	 */
	@Transactional
	public void doSalesConfirms(String[] pks,String approveRemark) {
		try {
			List<IntervalEntity> list = new ArrayList<IntervalEntity>();
			IntervalEntity entity = null;
			if (pks != null && pks.length > 0) {
				for (String pk : pks) {
					entity = this.getOne(pk);
					list.add(this.dosalesConfirm(entity,approveRemark));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 * 业务员回价
	 * @param entity
	 * @return
	 */
	@Transactional
	private IntervalEntity dosalesConfirm(IntervalEntity entity,String approveRemark) {
		try {
			if (entity.getBillstatus() != null || AuditStatus.FACTORY_CONFIRM.toStatusValue() == entity.getBillstatus()) {
				if (entity != null) {
					UserEntity user=ShiroUser.getCurrentUserEntity();
					entity.setFactoryConfrimTime(new Date());
					entity.setBillstatus(AuditStatus.SALES_CONFIRM.toStatusValue());
					//保存日志
					saveLog(user, entity.getUuid(),"业务人员【"+user.getUsername()+"】确认报价");
				}
				entity = this.save(entity);
			} else {
				throw new ServiceException("工厂确认才能回价！");
			}
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 * 业务员拒绝
	 * @param pks
	 */
	@Transactional
	public void doSalesRejects(String[] pks,String approveRemark) {
		try {
			List<IntervalEntity> list = new ArrayList<IntervalEntity>();
			IntervalEntity entity = null;
			if (pks != null && pks.length > 0) {
				for (String pk : pks) {
					entity = this.getOne(pk);
					list.add(this.doSalesReject(entity,approveRemark));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
	@Transactional
	private IntervalEntity doSalesReject(IntervalEntity entity,String approveRemark) {
		try {
			if (entity.getBillstatus() != null || AuditStatus.FACTORY_CONFIRM.toStatusValue() == entity.getBillstatus()) {
				if (entity != null) {
					UserEntity user=ShiroUser.getCurrentUserEntity();
					entity.setFactoryConfrimTime(new Date());
					entity.setBillstatus(AuditStatus.SALES_REJECT.toStatusValue());
					//保存日志
					saveLog(user, entity.getUuid(),"业务人员【"+user.getUsername()+"】拒绝报价");
				}
				entity = this.save(entity);
			} else {
				throw new ServiceException("工厂确认才能拒绝！");
			}
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
}