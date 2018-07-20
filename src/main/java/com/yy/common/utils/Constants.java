package com.yy.common.utils;

import com.yy.modules.sys.tabconstr.TableConstraintsEntity;
import com.yy.modules.sys.tabconstr.TableConstraintsService;

/**
 * 常量
 * 
 * @ClassName: Constants
 * @author liusheng
 * @date 2015年12月17日 下午2:57:19
 */
public class Constants {

	public static final Integer PERSISTENCE_DELETE_STATUS = 0;// 删除

	public static final Integer PERSISTENCE_NOMAL_STATUS = 1;// 未删除

	public static final Integer JSON_SUC = 1;// 返回json操作表示成功
	public static final Integer JSON_FAIL = 0;// 返回json操作表示失败

	public static final String DEFAULT_CHINA_ID = "a037aaef-ca55-11e5-b7f9-5cb9018f5fb4";// 中国的区域id
	public static final String DEFAULT_CHINA_NAME = "中华人民共和国";// 中国

	public static final String EXPORT_MAX_COUNT = "4000";// 导出的最大条数

	public static final String CURRENTSTATION="currentStation";//当前session的厂商key
	
	public static final String DEFAULT_ORG_ID="xxxxxx";
	
	public static String DEFAULT_ADMIN_DEPT_NAME="xxxxxx";
	
	public static String OSS_ENDPOINT = "oss-cn-hangzhou.aliyuncs.com";//oss-cn-hangzhou-internal.aliyuncs.com
	public static String OSS_ACCESSID = "xmYt1ZCeJyA8yWkL";
	public static String OSS_ACCESSKEY = "z4UzrXOIqvDSiagXJeMcwefxtaq6fy";
	public static String OSS_BUCKET = "bucket-csc";//"csc-bucket-csc";
	
	public static final String MAIL_PASSWORD_SECRETKEY="MZ@c3p2GB";//
	
	
	public static final String VERSION_DEVICE_TYPE="1";//设备信息版本
	public static final String VERSION_STATION_TYPE="2";//厂站信息版本
	public static final String VERSION_MASTER_TYPE="3";//主站信息版本
	public static final String VERSION_STANDARD_TYPE="4";//标准信息点表
	public static final String VERSION_SLAVE_TYPE="5";//子站息点表
	public static final String VERSION_TYPICAL_TYPE="6";//典型信息点表
	
	public static final Integer MASVER_START=1;//起始的版本数
	
	public static final String SUBSTATUS_NOT="0";//0:未审核 1：质疑   2：通过
	public static final String SUBSTATUS_QUES="1";//0:未审核 1：质疑   2：通过
	public static final String SUBSTATUS_PASS="2";//0:未审核 1：质疑   2：通过
	
	
	/**
	 * boolean值
	 * 
	 * @ClassName: BooleanType
	 * @author liusheng
	 * @date 2016年3月3日 下午4:07:07
	 */
	public static class BooleanType {
		public static String YES = "1";
		public static String NO = "0";
	}

	// 用户管理下的用户类型
	public static class UserType {
		public static Integer COMMON = 1;// 普通
		public static Integer ADMIN = 2;// 管理
	}

	public static class RoleGroup {
		public static String OTGROUP = "OTGROUP";// 网点角色组
	}

	public static class ParamCode {
		public static String DEFAULT_PWD = "DEFAULT_PWD";// 默认的用户密码
	}

	public static class MSG {
		public static String SUC = "操作成功!";
		public static String FAIL = "操作失败!";
	}


	public static String getConstraintMsg(String exceptionStr) {
		String msg = "";
		try {
			TableConstraintsEntity consObj = TableConstraintsService.constraintsMap
					.get(TableConstraintsService.getFkname(exceptionStr));
			if (consObj != null) {
				msg = (consObj.getColumnNameDes() == null ? "" : consObj.getColumnNameDes()) + "不能重复";
			} else {
				msg = exceptionStr;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	public static void main(String[] args) {
		String a = "could not execute statement; SQL [n/a]; constraint [UK_q4419n0fnuy0m89mkibtuje0p]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement";

		System.out.println(a.substring(a.indexOf("constraint [") + 12, a.indexOf("]; nested")));

	}

}
