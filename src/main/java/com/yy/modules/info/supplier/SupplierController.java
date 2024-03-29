package com.yy.modules.info.supplier;

import org.springframework.ui.Model;
import javax.servlet.ServletRequest;
import com.yy.frame.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 供应商信息
 * @author ls2008
 * @date 2017-11-18 21:06:10
 */
@Controller
@RequestMapping(value = "/info/postgood")
public class SupplierController extends BaseController<SupplierEntity> {

	@Autowired
	private SupplierService service;

	/**
	 * 
	 * @Title: listView
	 * @author ls2008
	 * @date 2017-11-18 21:06:10
	 * @param @param model
	 * @param @return 设定文件 
	 * @return String 返回类型 
	 * @throws
	 */
	@RequestMapping("/list")
	public String listView(Model model) {
		return "modules/info/supplier/supplier_list";
	}
	
	@Override
	public String addView(Model model, ServletRequest request) {
		return "modules/info/supplier/supplier_edit";
	}

	@Override
	public String editView(Model model, ServletRequest request, SupplierEntity entity) {
		return "modules/info/supplier/supplier_edit";
	}

	@Override
	public String detailView(Model model, ServletRequest request, SupplierEntity entity) {
		return "modules/info/supplier/supplier_detail";
	}

}