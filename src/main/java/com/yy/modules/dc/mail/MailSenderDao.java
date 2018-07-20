package com.yy.modules.dc.mail;


import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.yy.frame.dao.IBaseDAO;

/**
 * 邮件模板dao
 * @ClassName: MailTemplateDao
 * @author  
 * @date 2016年24月16日 09:09:18
 */
public interface MailSenderDao extends IBaseDAO<MailSenderEntity, String> {

	/**
	 * 
	 * @Title: findByType 
	 * @author liusheng
	 * @date 2016年9月1日 上午11:25:31 
	 * @param @param type
	 * @param @return 设定文件 
	 * @return List<MailSenderEntity> 返回类型 
	 * @throws
	 */
	@Query("from MailSenderEntity where status=1 and billType=?1 order by ts desc")
	public List<MailSenderEntity> findByType(String type);

	/**
	 * 设置是否为默认
	 * @Title: updateSenderIsDefault 
	 * @author liusheng
	 * @date 2016年9月1日 下午4:35:27 
	 * @param @param billType
	 * @param @param isDefault 设定文件 
	 * @return void 返回类型 
	 * @throws
	 */
	@Modifying
	@Query("update MailSenderEntity set isDefault=?2 where status=1 and billType=?1")
	public void updateSenderIsDefault(String billType, String isDefault);

	
}
