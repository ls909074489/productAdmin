package com.yy.modules.sys.instructions;


import org.springframework.data.jpa.repository.Query;

import com.yy.frame.dao.IBaseDAO;

/**
 * 使用说明dao
 * @ClassName: InstructionsDao
 * @author liusheng
 * @date 2016年4月13日 下午6:07:41
 */
public interface InstructionsDao extends IBaseDAO<InstructionsEntity, String> {

	@Query("from InstructionsEntity where funcId=?1 and status=1")
	public InstructionsEntity findByFuncId(String funcId);
}
