package com.yy.modules.info.supplier;

import com.yy.frame.dao.IBaseDAO;
import com.yy.frame.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 供应商信息
 * @author ls2008
 * @date 2017-11-18 21:06:10
 */
@Service
@Transactional(readOnly=true)
public class SupplierService extends BaseServiceImpl<SupplierEntity,String> {

	@Autowired
	private SupplierDao dao;
	//@Autowired
	//private DbUtilsDAO dbDao;

	protected IBaseDAO<SupplierEntity, String> getDAO() {
		return dao;
	}

}