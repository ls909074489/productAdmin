package com.yy.modules.sys.enumdata;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.yy.frame.dao.IBaseDAO;

public interface EnumDataDao extends IBaseDAO<EnumDataEntity, String> {

	@Query("from EnumDataSubEntity a where a.enumdata.groupcode = ?1 order by showorder")
	List<EnumDataSubEntity> findByGroupcode(String groupcode);

}
