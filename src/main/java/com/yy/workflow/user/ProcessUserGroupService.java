package com.yy.workflow.user;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.yy.frame.dao.IBaseDAO;
import com.yy.frame.service.BaseServiceImpl;
import com.yy.modules.sys.user.UserEntity;
import com.yy.modules.sys.user.UserService;

/**
 * 流程用户组service
 * @ClassName: RoleService
 * @author liusheng 
 * @date 2015年12月17日 上午11:00:53
 */
@Service
@Transactional
public class ProcessUserGroupService extends BaseServiceImpl<ProcessUserGroupEntity, String>{

	@Autowired
	private ProcessUserGroupDao dao;
	@Autowired
	private ProcessUserGroupRelationService processUserGroupRelationService;
	@Autowired
	@Lazy
	private UserService userService;
	
	@Override
	protected IBaseDAO<ProcessUserGroupEntity, String> getDAO() {
		return dao;
	}
	
	/**
	 * 获取选中的用户
	 * @Title: findUserByItemId 
	 * @author liusheng
	 * @date 2016年5月10日 下午5:20:02 
	 * @param @param itemId
	 * @param @return 设定文件 
	 * @return List<UserEntity> 返回类型 
	 * @throws
	 */
	public List<UserEntity> findByProcessUserGroupId(String groupId) {
		List<UserEntity> list=new ArrayList<UserEntity>();
		try {
			list=userService.findByProcessUserGroupId(groupId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据流程用户组获取可选的用户
	 * @Title: findAllUserByItemId 
	 * @author liusheng
	 * @date 2016年5月10日 下午5:27:13 
	 * @param @param itemId
	 * @param @return 设定文件 
	 * @return List<UserEntity> 返回类型 
	 * @throws
	 */
	public List<UserEntity> findAllProcessUserGroupId(String groupId) {
		List<UserEntity> list=new ArrayList<UserEntity>();
		try {
			list=userService.findAllProcessUserGroupId(groupId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	

	/**
	 * 删除流程用户组
	 * @Title: delItem 
	 * @author liusheng
	 * @date 2016年5月10日 下午7:13:02 
	 * @param @param roleId 设定文件 
	 * @return void 返回类型 
	 * @throws
	 */
	public void delUserGroup(String groupId) {
		processUserGroupRelationService.deleteByGroupId(groupId);//删除用户角色表
		dao.delete(groupId);//删除用户表
	}

	
	/**
	 * 根据编码查询
	 * @Title: findbyCode 
	 * @author liusheng
	 * @date 2018年6月14日 下午11:15:49 
	 * @param @param code
	 * @param @return 设定文件 
	 * @return List<ProcessUserGroupEntity> 返回类型 
	 * @throws
	 */
	public List<ProcessUserGroupEntity> findbyCode(String code) {
		return dao.findByCode(code);
	}

	
}
 