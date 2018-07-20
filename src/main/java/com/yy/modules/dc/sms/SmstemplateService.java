package com.yy.modules.dc.sms;

import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yy.frame.dao.IBaseDAO;
import com.yy.frame.service.BaseServiceImpl;

/**
 * 短信模板service
 * 
 * @ClassName: SmstemplateService
 * @author
 * @date 2016年26月15日 10:10:56
 */
@Service
@Transactional
public class SmstemplateService extends BaseServiceImpl<SmsTemplateEntity, String> {

	@Autowired
	private SmstemplateDao dao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected IBaseDAO getDAO() {
		return dao;
	}

}
