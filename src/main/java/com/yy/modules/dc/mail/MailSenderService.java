package com.yy.modules.dc.mail;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.yy.frame.controller.ActionResultModel;
import com.yy.frame.dao.IBaseDAO;
import com.yy.frame.service.BaseServiceImpl;

/**
 * 邮件发送人service
 * @ClassName: MailSenderService
 * @author liusheng
 * @date 2016年9月1日 上午11:26:30
 */
@Service
@Transactional
public class MailSenderService extends BaseServiceImpl<MailSenderEntity, String> {

	@Autowired
	private MailSenderDao dao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected IBaseDAO getDAO() {
		return dao;
	}

	/**
	 * 根据类型查询
	 * @Title: findByType 
	 * @author liusheng
	 * @date 2016年9月1日 上午11:25:02 
	 * @param @param type
	 * @param @return 设定文件 
	 * @return MailTemplateEntity 返回类型 
	 * @throws
	 */
	public List<MailSenderEntity> findByType(String type) {
		return dao.findByType(type);
	}

	
	/**
	 * 添加
	 * @Title: saveMailSender 
	 * @author liusheng
	 * @date 2016年9月1日 上午11:33:51 
	 * @param @param name
	 * @param @param email
	 * @param @param billType
	 * @param @return 设定文件 
	 * @return ActionResultModel<MailSenderEntity> 返回类型 
	 * @throws
	 */
	public ActionResultModel<MailSenderEntity> saveMailSender(String name, String email, String billType) {
		ActionResultModel<MailSenderEntity> arm=new ActionResultModel<MailSenderEntity>();
		try {
			MailSenderEntity user=new MailSenderEntity();
			user.setName(name);
			user.setEmail(email);
			user.setBillType(billType);
			doAdd(user);
			arm.setSuccess(true);
			arm.setMsg("保存成功");
		} catch (Exception e) {
			arm.setSuccess(false);
			arm.setMsg("保存失败");
			e.printStackTrace();
		}
		return arm;
	}

	
	/**
	 * 删除
	 * @Title: delMailSender 
	 * @author liusheng
	 * @date 2016年9月1日 上午11:34:02 
	 * @param @param uuid
	 * @param @return 设定文件 
	 * @return ActionResultModel<MailSenderEntity> 返回类型 
	 * @throws
	 */
	public ActionResultModel<MailSenderEntity> delMailSender(String uuid) {
		ActionResultModel<MailSenderEntity> arm=new ActionResultModel<MailSenderEntity>();
		try {
			doDelete(uuid);
			arm.setSuccess(true);
			arm.setMsg("删除成功");
		} catch (Exception e) {
			arm.setSuccess(false);
			arm.setMsg("删除失败");
			e.printStackTrace();
		}
		return arm;
	}

	/**
	 * 保存为默认的
	 * @Title: saveDefaultSender 
	 * @author liusheng
	 * @date 2016年9月1日 下午4:12:27 
	 * @param @param pks
	 * @param @return 设定文件 
	 * @return ActionResultModel<MailSenderEntity> 返回类型 
	 * @throws
	 */
	public ActionResultModel<MailSenderEntity> saveDefaultSender(String pks,String billType) {
		ActionResultModel<MailSenderEntity> arm=new ActionResultModel<MailSenderEntity>();
		try {
			dao.updateSenderIsDefault(billType,"0");
			if(!StringUtils.isEmpty(pks)){
				List<String> idList=new ArrayList<String>();
				for(String id:pks.split(",")){
					idList.add(id);
				}
				List<MailSenderEntity> list=(List<MailSenderEntity>) findAll(idList);
				for(MailSenderEntity user:list){
					user.setIsDefault("1");
				}
				arm.setSuccess(true);
			}
		} catch (Exception e) {
			arm.setSuccess(false);
			e.printStackTrace();
		}
		return arm;
	}

}
