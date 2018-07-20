package com.yy.modules.sys.admins;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yy.frame.dao.IBaseDAO;
import com.yy.frame.service.TreeServiceImpl;

/**
 * 行政区域
 * @ClassName: AdministrativeService 
 * @Description:  
 * @author liusheng
 * @date 2016年2月3日 上午11:24:39
 */
@Service
@Transactional
public class AdminisService extends TreeServiceImpl<AdminisEntity, String> {

	@Autowired
	private AdminisDAO dao;

	@Override
	protected IBaseDAO<AdminisEntity, String> getDAO() {
		return dao;
	}


}
