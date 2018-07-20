package com.yy.workflow.flownode;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yy.frame.dao.IBaseDAO;
import com.yy.frame.service.BaseServiceImpl;

/**
 * 审批节点
 * 
 * @author ChenXiaoTing
 * @Dtae 2016-1-20
 *
 */
@Component
@Transactional
public class FlownodeService extends BaseServiceImpl<FlownodeEntity, String> {

	@Autowired
	private FlownodeDAO dao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected IBaseDAO getDAO() {
		return dao;
	}

	/**
	 * 跟进单据类型，单据ID，查询当前的审批节点
	 * 
	 * @param billid
	 * @return
	 */
	public FlownodeEntity findByBilltypeAndBillid(String billtype, String billid) {
		List<FlownodeEntity> list = dao.findByBilltypeAndBillid(billtype, billid);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		FlownodeEntity entity = null;
		return entity;
	}

}
