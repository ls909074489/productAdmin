package com.yy.modules.sys.admins;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yy.frame.controller.TreeController;

/**
 * 行政区域
 * 
 * @ClassName: AdminisController
 * @Description: TODO
 * @author liusheng
 * @date 2016年2月3日 上午11:15:17
 */
@Controller
@RequestMapping(value = "/sys/adminis")
public class AdminisController extends TreeController<AdminisEntity> {

	private AdminisService getService() {
		return (AdminisService) super.baseService;
	}

	@RequestMapping(value = "/getAllAdmin")
	@ResponseBody
	public List<AdminisBean> getAllAdmin() {

		List<AdminisEntity> list = AdminisUtils.cacheResult;
		if (list == null) {
			list = (List<AdminisEntity>) getService().findAll();
		}
		// List<AdminisEntity> list = (List<AdminisEntity>) getService().findAll();
		List<AdminisBean> result = new ArrayList<AdminisBean>();
		AdminisBean a = null;
		if (list != null && list.size() > 0) {
			for (AdminisEntity s : list) {
				a = new AdminisBean();
				a.setAdmin_name(s.getAdmin_name());
				a.setUuid(s.getUuid());
				if (s.getParent() != null) {
					a.setParentId(s.getParent().getUuid());
				}
				result.add(a);
			}
		}
		return result;
	}

}
