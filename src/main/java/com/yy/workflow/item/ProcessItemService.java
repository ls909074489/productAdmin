package com.yy.workflow.item;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.yy.frame.dao.IBaseDAO;
import com.yy.frame.service.BaseServiceImpl;
import com.yy.modules.sys.user.UserEntity;
import com.yy.modules.sys.user.UserService;
import com.yy.workflow.group.ProcessGroupEntity;

/**
 * 角色service
 * 
 * @ClassName: RoleService
 * @author liusheng
 * @date 2015年12月17日 上午11:00:53
 */
@Service
@Transactional
public class ProcessItemService extends BaseServiceImpl<ProcessItemEntity, String> {

	@Autowired
	private ProcessItemDao dao;
	@Autowired
	@Lazy
	private UserService userService;

	@Override
	protected IBaseDAO<ProcessItemEntity, String> getDAO() {
		return dao;
	}

	/**
	 * 添加参数信息
	 */
	@Override
	public ProcessItemEntity save(ProcessItemEntity entity) throws ServiceException {
		return super.save(entity);

	}

	/**
	 * 获取选中的用户 @Title: findUserByItemId @author liusheng @date 2016年5月10日 下午5:20:02 @param @param itemId @param @return
	 * 设定文件 @return List<UserEntity> 返回类型 @throws
	 */
	public List<UserEntity> findUserByItemId(String itemId) {
		List<UserEntity> list = new ArrayList<UserEntity>();
		try {
			list = userService.findUserByProcessItemId(itemId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 根据流程项获取可选的用户 @Title: findAllUserByItemId @author liusheng @date 2016年5月10日 下午5:27:13 @param @param
	 * itemId @param @return 设定文件 @return List<UserEntity> 返回类型 @throws
	 */
	public List<UserEntity> findAllUserByItemId(String itemId) {
		List<UserEntity> list = new ArrayList<UserEntity>();

		try {
			list = userService.findAllUserByItemId(itemId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 删除流程项 @Title: delItem @author liusheng @date 2016年5月10日 下午7:13:02 @param @param roleId 设定文件 @return void
	 * 返回类型 @throws
	 */
	public void delItem(String itemId) {
		dao.delete(itemId);// 删除用户表
	}

	/*------平台流程专用---------*/
	public List<ProcessItemEntity> findItemByProcessgroup(ProcessGroupEntity processgroup) throws ServiceException {
		return dao.findByProcessgroup(processgroup);
	}
	public List<ProcessItemEntity> findItemByProcessgroupId(String processGroupId) throws ServiceException {
		return dao.findItemByProcessgroupId(processGroupId);
	}
}
