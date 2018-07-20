package com.yy.workflow.group;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yy.frame.controller.ActionResultModel;
import com.yy.frame.dao.IBaseDAO;
import com.yy.frame.service.TreeServiceImpl;

@Component
@Transactional
public class ProcessGroupService extends TreeServiceImpl<ProcessGroupEntity, String> {

	@Autowired
	private ProcessGroupDAO dao;

	@Override
	protected IBaseDAO<ProcessGroupEntity, String> getDAO() {
		return dao;
	}

	public ActionResultModel<ProcessGroupEntity> deleteRoleGroup(String pk) throws ServiceException {
		ActionResultModel<ProcessGroupEntity> arm = new ActionResultModel<ProcessGroupEntity>();
		ProcessGroupEntity entity = this.getOne(pk);
		if (isExist(entity.getUuid())) {
			arm.setMsg("流程组【" + entity.getName() + "】已经存在子流程组，请先删除子流程组");
			arm.setSuccess(false);
		} else {
			if (isAssRole(entity.getUuid())) {
				arm.setMsg("流程组【" + entity.getName() + "】已经存在角色，请先删除角色");
				arm.setSuccess(false);
			} else {
				try {
					this.delete(entity);
					arm.setMsg("操作成功!");
					arm.setSuccess(true);
				} catch (Exception e) {
					arm.setMsg("操作失败!");
					arm.setSuccess(false);
					e.printStackTrace();
				}
			}
		}

		return arm;
	}

	/**
	 * @date 2014-1-3 下午12:42:28
	 * @param parentId
	 * @return
	 * @throws ServiceException
	 */
	public boolean isExist(String parentId) throws ServiceException {
		boolean flag = false;
		List<ProcessGroupEntity> lists = this.findByParentId(parentId);
		if (lists != null && lists.size() > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * @date 2014-1-3 上午10:32:26
	 * @param roleGroupId
	 * @return
	 */
	public boolean isAssRole(String roleGroupId) {
		boolean flag = false;
		// List<RoleEntity> roles = roleService.searchGroupRoles(roleGroupId);
		// if (roles != null && roles.size() > 0) {
		// flag = true;
		// }
		return flag;
	}

	/**
	 * 获取第一级节点
	 * 
	 * @return
	 */
	public List<ProcessGroupEntity> getFirstLevel() {
		return dao.getFirstLevel();
	}

	/**
	 * 根据父节点ID获取子节点，如果传入null或者空字符串，则返回第一级节点
	 * 
	 * @param parentId
	 * @return
	 */
	public List<ProcessGroupEntity> findByParentId(String parentId) {
		if (StringUtils.isBlank(parentId)) {
			return getFirstLevel();
		}
		return dao.findByParentId(parentId);
	}

	/*------平台流程专用---------*/
	/**
	 * 根据流程组编码查询
	 * 
	 * @param code
	 * @return
	 */
	public ProcessGroupEntity getGroupByCode(String code) {
		ProcessGroupEntity group = null;
		List<ProcessGroupEntity> list = dao.getGroupByCode(code);
		if (list != null && list.size() > 0) {
			group = list.get(0);
		}
		return group;
	}

}
