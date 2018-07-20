package com.yy.workflow.user;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yy.frame.controller.ActionResultModel;
import com.yy.frame.dao.IBaseDAO;
import com.yy.frame.service.BaseServiceImpl;
import com.yy.modules.sys.user.UserEntity;

/**
 * 流程用户组和用户中间表service
 * 
 * @ClassName: ProcessUserGroupRelationService
 * @author liusheng
 * @date 2016年5月17日 上午11:27:31
 */
@Service
@Transactional
public class ProcessUserGroupRelationService extends BaseServiceImpl<ProcessUserGroupRelationEntity, String> {

	@Autowired
	private ProcessUserGroupRelationDAO dao;

	@Override
	protected IBaseDAO<ProcessUserGroupRelationEntity, String> getDAO() {
		return dao;
	}

	/**
	 * 选入流程项用户 @Title: selecGroupUser @author liusheng @date 2016年5月17日 上午11:28:04 @param @param itemId @param @param
	 * userId @param @return 设定文件 @return ActionResultModel<ProcessUserGroupRelationEntity> 返回类型 @throws
	 */
	public ActionResultModel<ProcessUserGroupRelationEntity> selecGroupUser(String groupId, String userId) {
		ActionResultModel<ProcessUserGroupRelationEntity> result = new ActionResultModel<ProcessUserGroupRelationEntity>();
		ProcessUserGroupRelationEntity obj = dao.findByUserIdAndByGroupId(userId, groupId);
		if (obj != null && obj.getUuid() != null) {
			result.setMsg("该用户已选入");
			result.setSuccess(false);
		} else {
			try {
				onSaveUserGroupRealtion(userId, groupId);
				result.setMsg("操作成功");
				result.setSuccess(true);
			} catch (Exception e) {
				result.setMsg("操作失败");
				result.setSuccess(false);
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 移除流程项用户 @Title: delGroupUser @author liusheng @date 2016年5月17日 上午11:29:53 @param @param groupId @param @param
	 * userId @param @return 设定文件 @return ActionResultModel<ProcessUserGroupRelationEntity> 返回类型 @throws
	 */
	public ActionResultModel<ProcessUserGroupRelationEntity> delGroupUser(String groupId, String userId) {
		ActionResultModel<ProcessUserGroupRelationEntity> result = new ActionResultModel<ProcessUserGroupRelationEntity>();
		try {
			dao.deleteByUserIdAndByGroupId(userId, groupId);
			result.setMsg("操作成功");
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMsg("操作失败");
			result.setSuccess(false);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * @Title: onSaveUserGroupRealtion @author liusheng @date 2016年5月17日 上午11:30:00 @param @param userId @param @param
	 *         groupId @param @throws ServiceException 设定文件 @return void 返回类型 @throws
	 */
	public void onSaveUserGroupRealtion(String userId, String groupId) throws ServiceException {
		ProcessUserGroupRelationEntity entity = new ProcessUserGroupRelationEntity();
		UserEntity user = new UserEntity();
		user.setUuid(userId);
		ProcessUserGroupEntity userGroup = new ProcessUserGroupEntity();
		userGroup.setUuid(groupId);

		entity.setUserGroup(userGroup);
		entity.setUser(user);

		dao.save(entity);
	}

	/**
	 * 根据流程项删除 @Title: deleteByGroupoId @author liusheng @date 2016年5月10日 下午7:25:41 @param @param itemId 设定文件 @return
	 * void 返回类型 @throws
	 */
	public void deleteByGroupId(String groupId) {
		dao.delUserGroup(groupId);
	}

	/**
	 * 根据流程项查找
	 * 
	 * @param itemId
	 * @return
	 */
	public List<ProcessUserGroupRelationEntity> findByItemId(String itemId) {
		return dao.findByItemId(itemId);
	}

	/**
	 * 根据分组查找
	 * 
	 * @param itemId
	 * @return
	 */
	public List<ProcessUserGroupRelationEntity> findByGroupCode(String itemId) {
		return dao.findByGroupCode(itemId);
	}

}
