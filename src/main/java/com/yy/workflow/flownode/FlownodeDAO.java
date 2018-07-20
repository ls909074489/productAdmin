package com.yy.workflow.flownode;

import java.util.List;

import com.yy.frame.dao.IBaseDAO;

/**
 * 审批节点
 */
public interface FlownodeDAO extends IBaseDAO<FlownodeEntity, String> {

	List<FlownodeEntity> findByBilltypeAndBillid(String billtype, String billid);

}
