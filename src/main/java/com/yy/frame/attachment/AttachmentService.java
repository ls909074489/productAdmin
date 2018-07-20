package com.yy.frame.attachment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.yy.common.fileupload.FileUploadFactory;
import com.yy.common.fileupload.FileUploadHandle;
import com.yy.common.utils.Constants;
import com.yy.common.utils.DateUtil;
import com.yy.frame.dao.IBaseDAO;
import com.yy.frame.security.ShiroUser;
import com.yy.frame.service.BaseServiceImpl;
import com.yy.frame.utils.RequestUtil;
import com.yy.modules.sys.param.ParameterUtil;
import com.yy.modules.sys.user.UserEntity;

/*
*	版权信息 (c) RAP 保留所有权利.
*
*	维护记录
*	新建： RAP Codegen
*   修改：
*/
/**
 * 附件库 服务类
 * 
 * @author RAP Team
 *
 */

@Service
@Transactional(rollbackFor = { Exception.class })
public class AttachmentService extends BaseServiceImpl<AttachmentEntity, String> {
	public static final String CONTENT_TYPE = "application/octet-stream; charset=utf-8";
	public static final String CONTENT_LENGTH = "Content-Length";
	public static final String CONTENT_DISPOSITION = "Content-Disposition";
	FileUploadHandle handle;
	@Autowired
	private AttachmentDAO dao;

	@SuppressWarnings("unchecked")
	@Override
	protected IBaseDAO getDAO() {
		return dao;
	}

	/**
	 * 
	 * 附件及附件实体信息保存
	 * 
	 * @date 2013-12-17 上午10:36:02
	 * @param files
	 * @param entity
	 * @return
	 * @throws ServiceException
	 * @throws IOException
	 */
	public List<AttachmentEntity> save(List<MultipartFile> files, AttachmentEntity entity)
			throws ServiceException, IOException {
		List<AttachmentEntity> entities = new ArrayList<AttachmentEntity>();
		// UserEntity loginUser = ShiroUser.getCurrentUserEntity();
		boolean hasException = false;
		for (MultipartFile file : files) {
			AttachmentEntity attach = new AttachmentEntity();
			attach.setEntityType(entity.getEntityType());
			attach.setEntityUuid(entity.getEntityUuid());
			attach.setAttaType(entity.getAttaType());
			attach.setTag(entity.getTag());
			String fileName = file.getOriginalFilename();
			attach.setFileName(fileName);
			attach.setFileSize(String.valueOf(file.getSize()));
			// attach.setFileType(file.getContentType());
			attach.setFileType(fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toUpperCase());
			attach.setUploadDate(new Date()); // 上传时间
			// attach.setUploadUser(loginUser);//上传用户
			UserEntity user = ShiroUser.getCurrentUserEntity();
			attach.setUploadUserId(user.getUuid());
			attach.setUploadUserName(user.getUsername());
			try {
				super.save(attach);
				if (upLoad(file, attach)) {
					entities.add(attach);
				} else {
					hasException = true;
					break;
				}
			} catch (IOException e) {
				hasException = true;
				break;
			}
		}
		if (hasException) {
			delete(entities);
		}
		return entities;
	}

	
	
	public void deleteFiles(List<AttachmentEntity> entities) throws ServiceException {
		for (AttachmentEntity entity : entities) {
			deleteAttachment(entity);
		}
	}

	/**
	 * 附件及附件实体信息删除
	 */
	@Override
	public void delete(String[] pks) throws ServiceException {
		for (String pk : pks) {
			AttachmentEntity entity = getOne(pk);
			if (entity == null) {
				continue;
			}
			deleteAttachment(entity);
			super.delete(pk);
		}

	}

	@Override
	public void delete(Iterable<AttachmentEntity> entities) throws ServiceException {
		for (AttachmentEntity entity : entities) {
			deleteAttachment(entity);
			super.delete(entity);
		}
	}

	/**
	 * 
	 * 根据实体uuid删除附件及附件实体信息
	 * 
	 * @date 2013-12-17 上午10:37:49
	 * @param entityUuid
	 * @throws ServiceException
	 */
	public void deleteByEntityUuid(String entityUuid) throws ServiceException {
		List<AttachmentEntity> entities = dao.findByEntityUuid(entityUuid);
		for (AttachmentEntity entity : entities) {
			deleteAttachment(entity);
		}
		dao.deleteByEntityUuid(entityUuid);
	}

	public List<AttachmentEntity> findByEntityUuid(String entityUuid) throws ServiceException {
		try {
			List<AttachmentEntity> entities = dao.findByEntityUuid(entityUuid);
			return entities;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 * 
	 * 附件上传
	 * 
	 * @date 2013-12-17 上午10:38:16
	 * @param file
	 * @param uuid
	 * @throws IOException
	 * @throws ServiceException
	 */
	public boolean upLoad(MultipartFile file, AttachmentEntity entity) throws IOException, ServiceException {
		InputStream input = file.getInputStream();

		String path = getFileRelativePath(entity.getEntityType(), entity.getUploadDate());
		String fileSaveType = ParameterUtil.getParamValue(ParameterUtil.PARAM_CODE_FILE_SAVE_TYPE);
		String fileName=genFileName(entity.getUuid(), entity.getFileName());
		entity.setUrl(FileUploadHandle.attaFileName+File.separator+path+fileName);
		handle = FileUploadFactory.createFileUploadHandle(fileSaveType);
		if (handle == null)
			throw new ServiceException("请联系系统管理员在系统参数中设置文件管理模式！");
		return handle.uploadFile(input, path,fileName);
	}

	/**
	 * 
	 * 附件删除
	 * 
	 * @param picture
	 * @return
	 * @throws ServiceException
	 */
	public boolean deleteAttachment(AttachmentEntity entity) throws ServiceException {
		String fileName = genFileName(entity.getUuid(), entity.getFileName());
		String path = getFileRelativePath(entity.getEntityType(), entity.getUploadDate());
		String fileSaveType = ParameterUtil.getParamValue(ParameterUtil.PARAM_CODE_FILE_SAVE_TYPE);
		handle = FileUploadFactory.createFileUploadHandle(fileSaveType);
		if (handle == null)
			throw new ServiceException("请联系系统管理员在系统参数中设置文件管理模式！");
		return handle.deleteFile(path, fileName);
	}

	/**
	 * 
	 * 附件下载
	 * 
	 * @date 2013-12-17 上午10:55:25
	 * @param request
	 * @param pk
	 * @param request
	 * @throws ServiceException
	 * @throws IOException
	 */
	public void downLoad(HttpServletRequest request, HttpServletResponse response, String pk)
			throws ServiceException, IOException {
		AttachmentEntity att = getOne(pk);

		String path = getFileRelativePath(att.getEntityType(), att.getUploadDate());
		String name = genFileName(att.getUuid(), att.getFileName());
		String fileSaveType = ParameterUtil.getParamValue(ParameterUtil.PARAM_CODE_FILE_SAVE_TYPE);
		handle = FileUploadFactory.createFileUploadHandle(fileSaveType);
		if (handle == null)
			throw new ServiceException("请联系系统管理员在系统参数中设置文件管理模式！");
		setResponseBeforeDownload(request, response, att.getFileName());
		response.setHeader(CONTENT_LENGTH, String.valueOf(att.getFileSize()));
		handle.readFile(path, name, response.getOutputStream());
	}

	/**
	 * 下载oss文件 @Title: downloadOssFile @author liusheng @date 2016年4月21日 下午9:04:47 @param @param request @param @param
	 * response @param @param pk @param @throws ServiceException @param @throws IOException 设定文件 @return void
	 * 返回类型 @throws
	 */
	public void downloadOssFile(HttpServletRequest request, HttpServletResponse response, String pk)
			throws ServiceException, IOException {
		if (!StringUtils.isEmpty(pk)) {
			try {
				AttachmentEntity att = getOne(pk);
				if (att != null) {
					setResponseBeforeDownload(request, response, att.getFileName());
					response.setHeader(CONTENT_LENGTH, String.valueOf(att.getFileSize()));

					BufferedInputStream input = null;
					ServletOutputStream output = response.getOutputStream();
					try {
						OSSClient client = new OSSClient(Constants.OSS_ENDPOINT, Constants.OSS_ACCESSID,
								Constants.OSS_ACCESSKEY);
						OSSObject object = client.getObject(new GetObjectRequest(Constants.OSS_BUCKET, att.getUrl()));
						input = new BufferedInputStream(object.getObjectContent());
						byte[] buff = new byte[2048];
						int readLen = 0;
						while ((readLen = input.read(buff, 0, buff.length)) != -1) {
							output.write(buff, 0, readLen);
						}
						output.flush();
					} catch (IOException e) {
						throw e;
					} finally {
						if (input != null) {
							try {
								input.close();
							} catch (IOException e) {
							} finally {
								input = null;
							}
						}
						if (output != null) {
							try {
								output.close();
							} catch (IOException e) {
							} finally {
								output = null;
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 写附件到输出流
	 * 
	 * @author wzw 2015年4月15日
	 * @param att
	 * @return
	 * @throws ServiceException
	 */
	public void writeFileToOutputStream(AttachmentEntity att, OutputStream outputStream) throws ServiceException {
		String path = getFileRelativePath(att.getEntityType(), att.getUploadDate());
		String name = genFileName(att.getUuid(), att.getFileName());
		String fileSaveType = ParameterUtil.getParamValue(ParameterUtil.PARAM_CODE_FILE_SAVE_TYPE);
		handle = FileUploadFactory.createFileUploadHandle(fileSaveType);
		if (handle == null)
			throw new ServiceException("请联系系统管理员在系统参数中设置文件管理模式！");
		handle.readFile(path, name, outputStream);
	}

	/**
	 * 获取附件输出流
	 * 
	 * @author wzw 2015年4月15日
	 * @param att
	 * @return
	 * @throws ServiceException
	 */
	public OutputStream getFileOutputStream(AttachmentEntity att) throws ServiceException {
		File file = getFile(att);
		OutputStream out;
		try {
			out = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			throw new ServiceException("创建临时文件失败");
		}
		return out;
	}

	public File getFile(AttachmentEntity att) throws ServiceException {
//		String path = getFileRelativePath(att.getEntityType(), att.getUploadDate());
		String name = genFileName(att.getUuid(), att.getFileName());
		String fileSaveType = ParameterUtil.getParamValue(ParameterUtil.PARAM_CODE_FILE_SAVE_TYPE);
		handle = FileUploadFactory.createFileUploadHandle(fileSaveType);
		if (handle == null)
			throw new ServiceException("请联系系统管理员在系统参数中设置文件管理模式！");
		File file = null;
		// if(handle instanceof FTPFileUploadHandle){
		// //从文件服务器下载到应用服务器临时目录
		// file = new File( ParameterUtil.getParamValue(ParameterUtil.PARAM_CODE_TEMPPATH)+name);
		// OutputStream out;
		// try {
		// out = new FileOutputStream(file);
		// } catch (FileNotFoundException e) {
		// throw new ServiceException("创建临时文件失败");
		// }
		// handle.readFile(path, name, out);
		// }else{
		file = new File(getFilePath(att.getEntityType(), att.getUploadDate()) + name);
		// }
		return file;
	}

	/**
	 * 
	 * 显示图片附件类型
	 * 
	 * @date 2013-12-17 下午11:13:14
	 * @param response
	 * @param pk
	 * @throws ServiceException
	 * @throws IOException
	 */
	public void showImg(HttpServletResponse response, String pk) throws ServiceException, IOException {
		AttachmentEntity att = getOne(pk);

		String path = getFileRelativePath(att.getEntityType(), att.getUploadDate());
		String name = genFileName(att.getUuid(), att.getFileName());

		response.setContentType(att.getFileType());
		String fileSaveType = ParameterUtil.getParamValue(ParameterUtil.PARAM_CODE_FILE_SAVE_TYPE);
		handle = FileUploadFactory.createFileUploadHandle(fileSaveType);
		if (handle == null)
			throw new ServiceException("请联系系统管理员在系统参数中设置文件管理模式！");
		handle.readFile(path, name, response.getOutputStream());
	}

	private void setOutputStream(final HttpServletResponse response, final File file) throws IOException {
		BufferedOutputStream output = null;
		BufferedInputStream input = null;

		try {
			input = new BufferedInputStream(new FileInputStream(file));
			output = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int readLen = 0;
			while ((readLen = input.read(buff, 0, buff.length)) != -1) {
				output.write(buff, 0, readLen);
			}
			output.flush();
		} catch (IOException e) {
			throw e;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
				} finally {
					input = null;
				}
			}
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
				} finally {
					output = null;
				}
			}
		}
	}

	/**
	 * 
	 *  文件名生成规则. uuid_fileName
	 * 
	 * @date 2013-12-17 上午11:07:01
	 * @param uuid
	 * @param fileName
	 * @return
	 */
	public String genFileName(String uuid, String fileName) {
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
//		return sdf.format(new Date())+"_"+fileName;
//		return uuid + "_" + fileName;
		// return uuid + "_" + fileName;
		 return uuid+fileName.substring(fileName.lastIndexOf("."), fileName.length());
	}

	public String getFilePath() {
		String saveType = ParameterUtil.getParamValue(ParameterUtil.PARAM_CODE_FILE_SAVE_TYPE);
		if ("FTP".equals(saveType)) {
			return ParameterUtil.getParamValue(ParameterUtil.PARAM_CODE_FTP_ATTAPATH);
		}
		return ParameterUtil.getParamValue(ParameterUtil.PARAM_CODE_ATTACHMENT_PATH);
	}

	public String getFileRelativePath(String entityType, Date date) {
		return entityType + File.separator + formatDate(date) + File.separator;
	}

	public String getFilePath(String entityType, Date date) {
		return getFilePath() + File.separator + getFileRelativePath(entityType, date);
	}

	public String formatDate(Date date) {
		return DateUtil.formatDate(date, "yyyyMMdd");
	}

	/**
	 * 根据EntityUuid和EntityType查找文件
	 * 
	 * @author wzw 2014年11月18日
	 * @return
	 */
	public List<AttachmentEntity> findByEntityUuidAndEntityType(String entityUuid, String entityType) {
		return this.dao.findByEntityUuidAndEntityType(entityUuid, entityType);
	}

	private void setResponseBeforeDownload(HttpServletRequest request, HttpServletResponse response, String newFileName)
			throws UnsupportedEncodingException {
		response.setContentType(CONTENT_TYPE);
		String explorerType = RequestUtil.getExplorerType(request);
		if (explorerType == null || explorerType.contains("IE")) {
			// IE
			response.setHeader(CONTENT_DISPOSITION,
					"attachment; filename=\"" + RequestUtil.encode(newFileName, getSystemFileCharset()) + "\"");
		} else {
			// fireFox/Chrome
			response.setHeader(CONTENT_DISPOSITION,
					"attachment; filename=" + new String(newFileName.getBytes(getSystemFileCharset()), "ISO8859-1"));
		}
	}

	public static String getSystemFileCharset() {
		Properties pro = System.getProperties();
		return pro.getProperty("file.encoding");
	}

	/**
	 * 保存oss方式存储的附件 @Title: saveOssFile @author liusheng @date 2016年4月21日 下午3:51:29 @param @param
	 * addFileName @param @param addFileSize @param @param addFilePath @param @param attachment @param @param
	 * deleteFilePKs @param @return 设定文件 @return List<AttachmentEntity> 返回类型 @throws
	 */
	public List<AttachmentEntity> saveOssFile(String[] addFileName, String[] addFileSize, String[] addFilePath,
			AttachmentEntity entity, String[] deleteFilePKs) {
		List<AttachmentEntity> entities = new ArrayList<AttachmentEntity>();
		if (addFileName != null && addFileName.length > 0) {
			for (int i = 0; i < addFileName.length; i++) {
				AttachmentEntity attach = new AttachmentEntity();
				attach.setEntityType(entity.getEntityType());
				attach.setEntityUuid(entity.getEntityUuid());
				attach.setAttaType("1");// attach.setAttaType(entity.getAttaType());
				attach.setTag(entity.getTag());
				String fileName = addFileName[i];
				attach.setFileName(fileName);
				attach.setUrl(addFilePath[i]);
				attach.setFileSize(addFileSize[i]);
				// attach.setFileType(file.getContentType());
				attach.setFileType(fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toUpperCase());
				attach.setUploadDate(new Date()); // 上传时间
				// attach.setUploadUser(loginUser);//上传用户
				UserEntity user = ShiroUser.getCurrentUserEntity();
				attach.setUploadUserId(user.getUuid());
				attach.setUploadUserName(user.getUsername());
				try {
					super.save(attach);
					entities.add(attach);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (deleteFilePKs != null && deleteFilePKs.length > 0) {
			try {
				OSSClient client = new OSSClient(Constants.OSS_ENDPOINT, Constants.OSS_ACCESSID,
						Constants.OSS_ACCESSKEY);
				List<String> keys = new ArrayList<String>();
				for (String pk : deleteFilePKs) {
					AttachmentEntity delObj = getOne(pk);
					if (delObj != null) {
						keys.add(delObj.getUrl());
						super.delete(pk);
					}
				}
				DeleteObjectsResult deleteObjectsResult = client
						.deleteObjects(new DeleteObjectsRequest(Constants.OSS_BUCKET).withKeys(keys));
				deleteObjectsResult.getDeletedObjects();
				// List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
				// for (String object : deletedObjects) {
				// System.out.println("=================" + object);
				// }
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return entities;
	}
	
	/**
	 * 保存导入的excel
	 * @param fileName
	 * @param entityId
	 * @param file
	 * @throws ServiceException
	 * @throws IOException
	 */
	public void saveImportExcel(String fileName,String entityId,String entityType,MultipartFile file) throws ServiceException, IOException{
		//保存导入的excel
		AttachmentEntity attach = new AttachmentEntity();
		attach.setEntityType(entityType);
		attach.setEntityUuid(entityId);
		attach.setAttaType("0");
		attach.setFileName(fileName);
		attach.setFileSize(String.valueOf(file.getSize()));
		attach.setFileType(fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toUpperCase());
		attach.setUploadDate(new Date()); // 上传时间
		UserEntity user = ShiroUser.getCurrentUserEntity();
		attach.setUploadUserId(user.getUuid());
		attach.setUploadUserName(user.getUsername());
		save(attach);
		upLoad(file, attach);
	}
}
