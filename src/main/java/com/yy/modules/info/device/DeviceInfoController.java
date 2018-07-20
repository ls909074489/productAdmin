package com.yy.modules.info.device;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yy.common.fileupload.FileUploadHandle;
import com.yy.common.utils.Constants;
import com.yy.common.utils.QRCodeUtil;
import com.yy.frame.controller.ActionResultModel;
import com.yy.frame.controller.BaseController;
import com.yy.frame.controller.QueryRequest;
import com.yy.modules.sys.org.OrgEntity;
import com.yy.modules.sys.param.ParameterUtil;

/**
 * 设备信息
 * 
 * @author ls2008
 * @date 2017-11-17 23:22:24
 */
@Controller
@RequestMapping(value = "/info/device")
public class DeviceInfoController extends BaseController<DeviceInfoEntity> {

	@Autowired
	private DeviceInfoService service;

	
	@RequestMapping(value = "/querySationDevice")
	@ResponseBody
	public ActionResultModel<DeviceInfoEntity> querySationDevice(ServletRequest request, Model model) {
		OrgEntity station=getCurrentStation(request);
		if(station!=null&&!StringUtils.isEmpty(station.getUuid())){
			Map<String, Object> addParam = new HashMap<String, Object>();
			addParam.put("EQ_station.uuid", station.getUuid());
			addParam.put("EQ_status", "1");
			QueryRequest<DeviceInfoEntity> qr = getQueryRequest(request, addParam);
			return execQuery(qr, baseService);
		}else{
			ActionResultModel<DeviceInfoEntity> arm = new ActionResultModel<DeviceInfoEntity>();
			arm.setSuccess(false);
			arm.setMsg("无法获取当前的厂商");
			return arm;
		}
	
	}


	/**
	 * 
	 * @Title: listView 
	 * @author liusheng
	 * @date 2017年11月18日 上午10:27:19 
	 * @param @param model
	 * @param @return 设定文件 
	 * @return String 返回类型 
	 * @throws
	 */
	@RequestMapping("/list")
	public String listView(ServletRequest request,Model model) {
		setHasStation(model, request);//设置是否有厂商
		return "modules/info/device/device_list";
	}

	@Override
	public String addView(Model model, ServletRequest request) {
		OrgEntity station=getCurrentStation(request);
		String hasSelStation="0";//是否选择了当前的厂商
		if(station!=null&&!StringUtils.isEmpty(station.getUuid())){
			hasSelStation="1";
			model.addAttribute("currentStation", station);
		}
		model.addAttribute("hasSelStation", hasSelStation);
		return "modules/info/device/device_edit";
	}

	@Override
	public String editView(Model model, ServletRequest request, DeviceInfoEntity entity) {
		return "modules/info/device/device_edit";
	}

	@Override
	public String detailView(Model model, ServletRequest request, DeviceInfoEntity entity) {
		return "modules/info/device/device_detail";
	}

	
	@RequestMapping(value = "/addDevice")
	@ResponseBody
	public ActionResultModel<DeviceInfoEntity> addDevice(MultipartHttpServletRequest request, Model model, @Valid DeviceInfoEntity entity) {
		ActionResultModel<DeviceInfoEntity> arm = new ActionResultModel<DeviceInfoEntity>();
		try {
			List<MultipartFile> files = request.getFiles("attachment[]");
			entity = service.addDevice(entity,files);
			arm.setRecords(entity);
			arm.setSuccess(true);
		} catch (ServiceException e) {
			e.printStackTrace();
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			arm.setSuccess(false);
			arm.setMsg(Constants.getConstraintMsg(e.getMessage()));
		} catch (Exception e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return arm;
	}
	
	@RequestMapping(value = "/updateDevice")
	@ResponseBody
	public ActionResultModel<DeviceInfoEntity> updateDevice( Model model, DeviceInfoEntity entity,
			MultipartHttpServletRequest request) {
//		if(!(entity.getStation()!=null&&!StringUtils.isEmpty(entity.getStation().getUuid()))){
//			entity.setStation(null);
//		}
//		if(!(entity.getInterval()!=null&&!StringUtils.isEmpty(entity.getInterval().getUuid()))){
//			entity.setInterval(null);
//		}
//		if(!(entity.getSupplier()!=null&&!StringUtils.isEmpty(entity.getSupplier().getUuid()))){
//			entity.setSupplier(null);
//		}
		ActionResultModel<DeviceInfoEntity> arm = new ActionResultModel<DeviceInfoEntity>();
		try {
			List<MultipartFile> files = request.getFiles("attachment[]");
			entity = service.updateDevice(entity,files);
			arm.setRecords(entity);
			arm.setSuccess(true);
		} catch (ServiceException e) {
			arm.setSuccess(false);
			String msg = e.getMessage();
			if (msg.contains("constraint")) {
				arm.setMsg(Constants.getConstraintMsg(e.getMessage()));
			} else {
				arm.setMsg(e.getMessage());
			}
			e.printStackTrace();
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			arm.setSuccess(false);
			arm.setMsg(Constants.getConstraintMsg(e.getMessage()));
		} catch (Exception e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return arm;
	}

	
	/**
	 * 生成二维码
	 * @Title: genQrcode 
	 * @author liusheng
	 * @date 2017年12月12日 上午11:18:09 
	 * @param @param request
	 * @param @param qrtext
	 * @param @return 设定文件 
	 * @return ActionResultModel<DeviceInfoEntity> 返回类型 
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/genQrcode")
	public ActionResultModel<DeviceInfoEntity> genQrcode(ServletRequest request,String qrtext) {
		ActionResultModel<DeviceInfoEntity> arm=new ActionResultModel<DeviceInfoEntity>();
		try {
			String fileName=QRCodeUtil.encode(qrtext, ParameterUtil.getParamValue("UploadFilePath", "E:\\project\\")
					+FileUploadHandle.attaFileName+"\\qrcode\\temp\\", true);
			arm.setSuccess(true);
			arm.setMsg(fileName);
		} catch (Exception e) {
			arm.setSuccess(false);
			arm.setMsg("生成二维码失败");
			e.printStackTrace();
		}
		return arm;
	}
}