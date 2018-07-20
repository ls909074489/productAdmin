package com.yy.modules.info.interval;

import org.springframework.ui.Model;
import javax.servlet.ServletRequest;
import com.yy.frame.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 间隔信息
 * @author ls2008
 * @date 2017-11-18 21:54:30
 */
@Controller
@RequestMapping(value = "/info/interval")
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

}