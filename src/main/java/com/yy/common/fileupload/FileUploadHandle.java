package com.yy.common.fileupload;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import com.yy.modules.sys.param.ParameterUtil;

public abstract class FileUploadHandle {
	// protected String attaPath;
//	public String attaPath = "C:\\yyuploadfiles";
//	
	public static String attaFileName = "jlbcuploadfiles";

	public abstract boolean readFile(String path, String fileName, OutputStream outputStream);

	public abstract boolean uploadFile(InputStream input, String path, String fileName);

	public abstract boolean deleteFile(String path, String fileName);

	public String getCompletePath(String path) {
		//return attaPath + File.separator + path;
		return ParameterUtil.getParamValue("UploadFilePath", "E:\\project\\")+attaFileName+File.separator+path;
	}
}
