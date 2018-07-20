package com.yy.frame.attachment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
import com.yy.common.utils.Constants;
import com.yy.frame.controller.ActionResultModel;
import com.yy.frame.controller.BaseController;
import com.yy.modules.sys.param.ParameterUtil;

/*
/**
 * 附件库 控制类
 *
 */

@Controller
@RequestMapping(value = "/frame/attachment")
public class AttachmentController extends BaseController<AttachmentEntity> {

	public static final String FILE_NAME = "attachment[]";

	@Autowired
	private AttachmentService service;

	@RequestMapping(method = RequestMethod.GET)
	public String view() {
		return "sys/attachment/view";
	}

	@RequestMapping(value = "/widget")
	public String widget() {
		return "sys/attachment/widget";
	}

	@RequestMapping(value = "/list")
	public String list(Model model, HttpServletRequest request, @RequestParam(value = "entityType") String entityType,
			@RequestParam(value = "entityUuid") String entityUuid) {
		model.addAttribute("entityType", entityType);
		model.addAttribute("entityUuid", entityUuid);
		model.addAttribute("editAble", true);
		return "modules/sys/attachment/attachment_main";
	}

	@RequestMapping(value = "/view")
	public String viewAtta(Model model, HttpServletRequest request,
			@RequestParam(value = "entityType") String entityType,
			@RequestParam(value = "entityUuid") String entityUuid) {
		model.addAttribute("entityType", entityType);
		model.addAttribute("entityUuid", entityUuid);
		model.addAttribute("editAble", false);
		return "modules/sys/attachment/attachment_main";
	}

	@RequestMapping(value = "/filelist")
	public String filelist(Model model, HttpServletRequest request,
			@RequestParam(value = "entityType") String entityType, @RequestParam(value = "editAble") String editAble,
			@RequestParam(value = "entityUuid") String entityUuid) {

		model.addAttribute("entityType", entityType);
		model.addAttribute("entityUuid", entityUuid);

		if (!StringUtils.isEmpty(editAble) && "1".equals(editAble)) {
			model.addAttribute("editAble", true);
		} else {
			model.addAttribute("editAble", false);
		}
		return "modules/sys/upfile/upfile";
	}

	@RequestMapping(value = "/widgetlistCombo")
	public String widgetlistCombo() {
		return "sys/attachment/widget_list_combo";
	}

	@RequestMapping(value = "/upload")
	public String upload(Model model, @RequestParam(value = "entityType") String entityType,
			@RequestParam(value = "entityUuid") String entityUuid, @RequestParam(value = "maxFiles") String maxFiles,
			@RequestParam(value = "uploadUrl") String uploadUrl) {

		model.addAttribute("entityType", entityType);
		model.addAttribute("entityUuid", entityUuid);
		model.addAttribute("maxFiles", maxFiles);
		model.addAttribute("uploadUrl", uploadUrl);
		return "frame/attachment/attachment_upload";
	}

	/**
	 * 新增
	 * 
	 * @param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addFiles")
	@ResponseBody
	private ActionResultModel add(MultipartHttpServletRequest request, AttachmentEntity attachment) {

		ActionResultModel arm = new ActionResultModel();
		List<MultipartFile> files = request.getFiles(FILE_NAME);
		try {
			List<AttachmentEntity> list = service.save(files, attachment);
			arm.setRecords(list);
			arm.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
		}

		return arm;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addAndDelFiles")
	@ResponseBody
	private ActionResultModel addAndDelFiles(MultipartHttpServletRequest request, AttachmentEntity attachment,
			@RequestParam(value = "deleteFiles[]", required = false) String[] deleteFilePKs) {

		ActionResultModel<AttachmentEntity> arm = new ActionResultModel<AttachmentEntity>();
		List<MultipartFile> files = request.getFiles(FILE_NAME);
		try {
			List<AttachmentEntity> list = service.save(files, attachment);
			if (deleteFilePKs != null && deleteFilePKs.length > 0) {
				service.delete(deleteFilePKs);
			}
			arm.setRecords(list);
			arm.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
		}

		return arm;
	}

	/**
	 * oss上传 @Title: addAndDelOssFiles @author liusheng @date 2016年4月21日 下午3:42:06 @param @param request @param @param
	 * attachment @param @param deleteFilePKs @param @return 设定文件 @return ActionResultModel 返回类型 @throws
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addAndDelOssFiles")
	@ResponseBody
	private ActionResultModel addAndDelOssFiles(AttachmentEntity attachment,
			@RequestParam(value = "deleteFiles[]", required = false) String[] deleteFilePKs, String[] addFileName,
			String[] addFileSize, String[] addFilePath) {

		ActionResultModel<AttachmentEntity> arm = new ActionResultModel<AttachmentEntity>();
		try {
			List<AttachmentEntity> list = service.saveOssFile(addFileName, addFileSize, addFilePath, attachment,
					deleteFilePKs);
			arm.setRecords(list);
			arm.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
		}

		return arm;
	}

	/**
	 * 
	 * TODO 根据entityUuid删除附件信息
	 * 
	 * @date 2013-12-9 下午6:43:56
	 * @param entityUuid
	 * @return
	 */
	@RequestMapping(value = "/deleteByEntityUuid")
	@ResponseBody
	private ActionResultModel<AttachmentEntity> deleteByEntityUuid(String entityUuid) {

		ActionResultModel<AttachmentEntity> arm = new ActionResultModel<AttachmentEntity>();
		try {
			service.deleteByEntityUuid(entityUuid);
			arm.setSuccess(true);
		} catch (ServiceException e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
		}

		return arm;
	}

	/**
	 * 
	 * TODO 附件下载
	 * 
	 * @date 2013-12-17 上午10:49:18
	 */
	@RequestMapping(value = "/download")
	@ResponseBody
	private void download(HttpServletRequest request, HttpServletResponse response, String pk) {

		ActionResultModel<AttachmentEntity> arm = new ActionResultModel<AttachmentEntity>();
		try {
			service.downLoad(request, response, pk);
			arm.setSuccess(true);
		} catch (Exception e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/downloadOssFile")
	@ResponseBody
	private void downloadOssFile(HttpServletRequest request, HttpServletResponse response, String pk) {

		ActionResultModel<AttachmentEntity> arm = new ActionResultModel<AttachmentEntity>();
		try {
			service.downloadOssFile(request, response, pk);
			arm.setSuccess(true);
		} catch (Exception e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 文件下载
	 * 
	 * @param filePath
	 * @param response
	 * @return
	 */
	@RequestMapping("/download2")
	public String download(@RequestParam String filePath, HttpServletResponse response) {
		File file = new File(filePath);
		InputStream inputStream;
		try {
			inputStream = new FileInputStream(filePath);
			response.reset();
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
			OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
			byte data[] = new byte[1024];
			while (inputStream.read(data, 0, 1024) >= 0) {
				outputStream.write(data);
			}
			outputStream.flush();
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * TODO 显示图片附件类型
	 * 
	 * @date 2013-12-17 下午11:14:10
	 * @param response
	 * @param pk
	 */
	@RequestMapping(value = "/showImg")
	@ResponseBody
	private void showImg(HttpServletResponse response, String pk) {
		ActionResultModel<AttachmentEntity> arm = new ActionResultModel<AttachmentEntity>();
		try {
			service.showImg(response, pk);
			arm.setSuccess(true);
		} catch (Exception e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 跳转到查看图片页面
	 * @Title: toViewImg 
	 * @author liusheng
	 * @date 2016年11月1日 下午6:51:12 
	 * @param @param model
	 * @param @param request
	 * @param @param pk
	 * @param @return 设定文件 
	 * @return String 返回类型 
	 * @throws
	 */
	@RequestMapping(value = "/toViewImg")
	public String toViewImg(Model model, HttpServletRequest request,String pk,String imgurl) {
		String ossurl=request.getParameter("ossurl");
		if(!StringUtils.isEmpty(imgurl)){//查看本地的图片，即为上传的图片
			try {
				imgurl=URLDecoder.decode(imgurl,"UTF-8");
				imgurl=imgurl.replaceAll( "\\\\","/"); 
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			model.addAttribute("imgurl", imgurl);
		}else{
			if(ParameterUtil.getParamValue("OSS_ViewType", "1").equals("1")){//直接访问oss的外网地址
				OSSClient client = new OSSClient(ParameterUtil.getParamValue("OSS_ENDPOINT", Constants.OSS_ENDPOINT),
						ParameterUtil.getParamValue("OSS_ACCESSID", Constants.OSS_ACCESSID),
						ParameterUtil.getParamValue("OSS_ACCESSKEY", Constants.OSS_ACCESSKEY));
				Date expiration = new Date (new java.util.Date().getTime() + 1000 * ParameterUtil.getIntParamValue("OSS_ViewExpiration", 60)); // 1 minute to expire
				GeneratePresignedUrlRequest generatePresignedUrlRequest =
				new GeneratePresignedUrlRequest(ParameterUtil.getParamValue("OSS_BUCKET", Constants.OSS_BUCKET), ossurl);
				generatePresignedUrlRequest.setExpiration(expiration);
				URL url = client.generatePresignedUrl(generatePresignedUrlRequest);
				imgurl=url.toString();
				pk=null;
			}
			model.addAttribute("pk", pk);
			model.addAttribute("imgurl", imgurl);
		}
		return "modules/sys/attachment/img_view";
	}
}
