package com.yy.modules.dc.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yy.frame.dao.IBaseDAO;
import com.yy.frame.service.BaseServiceImpl;

/**
 * 邮件模板service
 * 
 * @ClassName: MailTemplateService
 * @author
 * @date 2016年24月16日 09:09:18
 */
@Service
@Transactional
public class MailTemplateService extends BaseServiceImpl<MailTemplateEntity, String> {

	@Autowired
	private MailTemplateDao dao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected IBaseDAO getDAO() {
		return dao;
	}

	public MailTemplateEntity findByCode(String templateCode) {
		return dao.findByCode(templateCode);
	}

}
