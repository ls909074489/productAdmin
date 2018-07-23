package com.yy.modules.info.interval;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yy.frame.controller.ActionResultModel;
import com.yy.frame.controller.BaseController;

/**
 * 间隔信息
 * @author ls2008
 * @date 2017-11-18 21:54:30
 */
@Controller
@RequestMapping(value = "/info/purchase")
public class IntervalController extends BaseController<IntervalEntity> {

	@Autowired
	private IntervalService service;

	/**
	 * 
	 * @Title: listView
	 * @author ls2008
	 * @date 2017-11-18 21:54:30
	 * @param @param model
	 * @param @return 设定文件 
	 * @return String 返回类型 
	 * @throws
	 */
	@RequestMapping("/list")
	public String listView(Model model) {
		return "modules/info/interval/interval_list";
	}

	@Override
	public String addView(Model model, ServletRequest request) {
		return "modules/info/interval/interval_edit";
	}

	@Override
	public String editView(Model model, ServletRequest request, IntervalEntity entity) {
		return "modules/info/interval/interval_edit";
	}

	@Override
	public String detailView(Model model, ServletRequest request, IntervalEntity entity) {
		return "modules/info/interval/interval_detail";
	}

	
	/**
	 * 工厂确定
	 * @param request
	 * @param model
	 * @param pks
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/factoryConfirm")
	public ActionResultModel<IntervalEntity> factoryConfirm(ServletRequest request, Model model, String[] pks,String approveRemark) {
		ActionResultModel<IntervalEntity> arm = new ActionResultModel<IntervalEntity>();
		try {
			service.doFactoryConfirms(pks,approveRemark);
			arm.setSuccess(true);
		} catch (Exception e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return arm;
	}
	
	@ResponseBody
	@RequestMapping(value = "/salesConfirm")
	public ActionResultModel<IntervalEntity> salesConfirm(ServletRequest request, Model model, String[] pks,String approveRemark) {
		ActionResultModel<IntervalEntity> arm = new ActionResultModel<IntervalEntity>();
		try {
			service.doSalesConfirms(pks,approveRemark);
			arm.setSuccess(true);
		} catch (Exception e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return arm;
	}
	
	
	/**
	 * 业务员拒绝
	 * @param request
	 * @param model
	 * @param pks
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/salesReject")
	public ActionResultModel<IntervalEntity> salesReject(ServletRequest request, Model model, String[] pks,String approveRemark) {
		ActionResultModel<IntervalEntity> arm = new ActionResultModel<IntervalEntity>();
		try {
			service.doSalesRejects(pks,approveRemark);
			arm.setSuccess(true);
		} catch (Exception e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return arm;
	}
}