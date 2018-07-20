package com.yy.modules.info.interval;

import com.yy.frame.dao.IBaseDAO;
import com.yy.frame.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

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
	//@Autowired
	//private DbUtilsDAO dbDao;

	protected IBaseDAO<IntervalEntity, String> getDAO() {
		return dao;
	}

}