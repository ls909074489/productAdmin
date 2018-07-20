package com.yy.modules.sys.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yy.frame.dao.IBaseDAO;
import com.yy.frame.service.BaseServiceImpl;
/**
 * 意见反馈service
 * @ClassName: FeedbackService
 * @author liusheng
 * @date 2016年4月18日 下午7:30:13
 */
@Service
@Transactional
public class FeedbackService extends BaseServiceImpl<FeedbackEntity, String> {
	@Autowired
	private FeedbackDAO dao;

	@SuppressWarnings({ "unchecked", "rawtypes" })	
	@Override
	protected IBaseDAO getDAO() {
		return dao;
	}


}
