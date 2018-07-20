package com.yy.modules.sys.instructions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.yy.frame.dao.IBaseDAO;
import com.yy.frame.service.BaseServiceImpl;

/**
 * 使用说明service
 * @ClassName: InstructionsService
 * @author liusheng
 * @date 2016年4月13日 下午6:06:56
 */
@Service
@Transactional
public class InstructionsService extends BaseServiceImpl<InstructionsEntity, String> {

	@Autowired
	private InstructionsDao dao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected IBaseDAO getDAO() {
		return dao;
	}

	/**
	 * 根据菜单id获取使用说明
	 * @Title: findByFuncId 
	 * @author liusheng
	 * @date 2016年4月13日 下午6:40:47 
	 * @param @param funcId
	 * @param @return 设定文件 
	 * @return InstructionsEntity 返回类型 
	 * @throws
	 */
	public InstructionsEntity findByFuncId(String funcId) {
		if(!StringUtils.isEmpty(funcId)){
			return dao.findByFuncId(funcId);
		}
		return null;
	}

}
