package com.yy.modules.dc.mail;


import com.yy.frame.dao.IBaseDAO;
import com.yy.modules.dc.mail.MailTemplateEntity;

/**
 * 邮件模板dao
 * @ClassName: MailTemplateDao
 * @author  
 * @date 2016年24月16日 09:09:18
 */
public interface MailTemplateDao extends IBaseDAO<MailTemplateEntity, String> {

	/**
	 * 根据编码查询
	 * @Title: findByCode 
	 * @author liusheng
	 * @date 2016年5月4日 下午12:02:19 
	 * @param @param templateCode
	 * @param @return 设定文件 
	 * @return MailTemplateEntity 返回类型 
	 * @throws
	 */
	public MailTemplateEntity findByCode(String templateCode);

	
}
