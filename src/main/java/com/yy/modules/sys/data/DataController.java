package com.yy.modules.sys.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PolicyConditions;
import com.yy.common.bean.TreeBaseBean;
import com.yy.common.utils.Constants;
import com.yy.frame.controller.ActionResultModel;
import com.yy.frame.security.ShiroUser;
import com.yy.frame.tree.TreeNode;
import com.yy.frame.tree.TreeUtils;
import com.yy.modules.sys.department.DepartmentEntity;
import com.yy.modules.sys.department.DepartmentService;
import com.yy.modules.sys.org.OrgEntity;
import com.yy.modules.sys.org.OrgService;
import com.yy.modules.sys.role.RoleEntity;
import com.yy.modules.sys.role.RoleService;
import com.yy.modules.sys.user.UserEntity;
import com.yy.modules.sys.user.UserService;
import com.yy.workflow.user.ProcessUserGroupEntity;
import com.yy.workflow.user.ProcessUserGroupService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/sys/data")
public class DataController {

	@Autowired
	private OrgService orgService;// 业务单元service
	@Autowired
	private DepartmentService deptService;// 业务部门service
	@Autowired
	private DataService dataService;
	@Autowired
	private RoleService roleService;// 角色service

	@Autowired
	private ProcessUserGroupService processUserGroupService;// 流程用户组service

	@Autowired
	private UserService userService;// 用户service

	public OrgService getOrgService() {
		return orgService;
	}

	public void setOrgService(OrgService orgService) {
		this.orgService = orgService;
	}

	/**
	 * 选择单位 @Title: selectOrg @author liusheng @date 2016年1月20日 上午11:31:34 @param @return 设定文件 @return String
	 * 返回类型 @throws
	 */
	@RequestMapping(value = "/selectOrg")
	public String selectOrg(Model model, String callBackMethod) {
		model.addAttribute("callBackMethod", callBackMethod);
		return "modules/sys/data/org_select";
	}

	/**
	 * 返回单位数据 
	 * @param pid
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/dataOrg")
	@ResponseBody
	private ActionResultModel dataOrg(@RequestParam(value = "node", defaultValue = "") String pid) {
		ActionResultModel<TreeNode> arm = new ActionResultModel<TreeNode>();
		try {
			List<OrgEntity> list = orgService.findByParentId(pid);// <OrgEntity>
			List allList = (List) orgService.findAll(new Sort(Sort.Direction.ASC, "nodepath"));
			TreeNode tree = TreeUtils.getTreeNode(list.get(0), allList);
			arm.setRecords(tree);
			arm.setTotal(0);
			arm.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			arm.setMsg(e.getMessage());
		}
		return arm;
	}

	/**
	 * 获取业务单元树列表 @Title: dataOrgList @author liusheng @date 2016年5月12日 下午2:33:32 @param @param request @param @param
	 * pid @param @return 设定文件 @return List<TreeBaseBean> 返回类型 @throws
	 */
	@RequestMapping(value = "/dataOrgList")
	@ResponseBody
	private List<TreeBaseBean> dataOrgList() {
		List<TreeBaseBean> treeList = new ArrayList<TreeBaseBean>();
		TreeBaseBean bean = null;
		List<OrgEntity> orgList = (List<OrgEntity>) getOrgService().findAll(new Sort(Sort.Direction.ASC, "org_code"));
		if (orgList != null && orgList.size() > 0) {
			for (OrgEntity d : orgList) {
				bean = new TreeBaseBean(d.getUuid(), d.getName(), d.getParentId(), false, false, d);
				treeList.add(bean);
			}
		}
		return treeList;
	}
	
	@RequestMapping(value = "/dataOwnOrgs")
	@ResponseBody
	private List<TreeBaseBean> dataOwnOrgs() {
		UserEntity user=ShiroUser.getCurrentUserEntity();
		List<TreeBaseBean> treeList = new ArrayList<TreeBaseBean>();
		TreeBaseBean bean = null;
		List<OrgEntity> orgList = getOrgService().findOwnOrg(user.getUuid());
		if (orgList != null && orgList.size() > 0) {
			for (OrgEntity d : orgList) {
				bean = new TreeBaseBean(d.getUuid(), d.getName()+"("+d.getOrg_code()+")", d.getParentid(), false, false, d);
				treeList.add(bean);
			}
		}
		return treeList;
	}

	/**
	 * 跳转到选择机构树页面
	 * 
	 * @param model
	 * @param callBackMethod
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/toOrgTree")
	public String toOrgTree(Model model, String callBackMethod, String userId) {
		model.addAttribute("callBackMethod", callBackMethod);
		model.addAttribute("userId", userId);
		return "modules/sys/data/ref_org_tree";
	}

	/**
	 * 获取机构树数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/dataOrgTree")
	public List<TreeBaseBean> dataOrgTree(HttpServletRequest request) throws Exception {
		List<OrgEntity> allList = (List<OrgEntity>) orgService.findAll(new Sort(Sort.Direction.ASC, "org_code"));

		List<TreeBaseBean> treeList = new ArrayList<TreeBaseBean>();
		TreeBaseBean treeBean = null;
		boolean checked = false;
		boolean open = false;
		for (OrgEntity o : allList) {
			checked = false;
			open = false;
			// for (UserOrgEntity rf : rfList) {
			// if (rf.getPk_corp().equals(o.getUuid())) {
			// checked = true;
			// open = true;
			// break;
			// }
			// }
			treeBean = new TreeBaseBean(o.getUuid(), o.getName(), o.getParentId(), checked, open);
			treeBean.setNodeData(o);
			treeList.add(treeBean);
		}
		return treeList;
	}

	/**
	 * 选择部门 @Title: selectDept @author liusheng @date 2016年1月20日 上午11:32:06 @param @param model @param @param
	 * callBackMethod @param @param pk_corp 部门所在的单元 @param @return 设定文件 @return String 返回类型 @throws
	 */
	@RequestMapping(value = "/selectDept")
	public String selectDept(Model model, String callBackMethod, String pk_corp) {
		model.addAttribute("callBackMethod", callBackMethod);
		model.addAttribute("pk_corp", pk_corp);
		return "modules/sys/data/dept_select";
	}

	/**
	 * 返回部门数据 @Title: getTreeNodes @author liusheng @date 2016年1月20日 上午11:32:14 @param @param request @param @param
	 * pid @param @return 设定文件 @return ActionResultModel 返回类型 @throws
	 */
	@RequestMapping(value = "/dataDept")
	@ResponseBody
	private List<TreeBaseBean> dataDept(String pk_corp) {
		TreeBaseBean treeBean = null;
		List<TreeBaseBean> treeList = new ArrayList<TreeBaseBean>();
		List<DepartmentEntity> deptList = null;
		if (StringUtils.isEmpty(pk_corp)) {// 为空时返回所有的单元和部门
			List<OrgEntity> orgList = (List<OrgEntity>) orgService.findAll(new Sort(Sort.Direction.ASC, "nodepath"));// 获取业务单位
			if (orgList != null && orgList.size() > 0) {
				for (OrgEntity o : orgList) {
					treeBean = new TreeBaseBean(o.getUuid(), o.getName(), o.getParentId(), false, false, "iconSkinOrg",
							o.getName(), "1", o);// 单位type=1作为判断
					treeList.add(treeBean);
				}
			}
			deptList = (List<DepartmentEntity>) deptService.findAll();// 业务部门
			if (deptList != null & deptList.size() > 0) {
				for (DepartmentEntity dept : deptList) {
					treeBean = new TreeBaseBean(dept.getUuid(), dept.getName(), dept.getParentId(), false, false,
							"iconSkinDept", dept.getName(), "2", dept);// 部门type=2作为判断
					treeList.add(treeBean);
				}
			}
		} else {
			OrgEntity o = orgService.findById(pk_corp);
			treeBean = new TreeBaseBean(o.getUuid(), o.getName(), o.getParentId(), false, false, "iconSkinOrg",
					o.getName(), "1", o);// 单位type=1作为判断
			treeList.add(treeBean);
			deptList = (List<DepartmentEntity>) deptService.getDeptByOrgId(pk_corp);// 业务部门
			if (deptList != null & deptList.size() > 0) {
				for (DepartmentEntity dept : deptList) {
					treeBean = new TreeBaseBean(dept.getUuid(), dept.getName(), dept.getParentId(), false, false,
							"iconSkinDept", dept.getName(), "2", dept);// 部门type=2作为判断
					treeList.add(treeBean);
				}
			}
		}
		return treeList;
	}

	/**
	 * 树方式选择业务人员 @Title: selectPerson @author liusheng @date 2016年1月25日 下午4:41:13 @param @return 设定文件 @return String
	 * 返回类型 @throws
	 */
	@RequestMapping(value = "/selectPerson")
	public String selectPerson(Model model, String callBackMethod) {
		model.addAttribute("orgId", Constants.DEFAULT_ORG_ID);
		model.addAttribute("callBackMethod", callBackMethod);
		return "modules/sys/data/person_select_main";
	}

	/**
	 * 列表方式选择业务人员
	 * 
	 * @Title: selectPerson
	 * @author liusheng
	 * @date 2016年1月25日 下午4:41:13
	 * @param @return
	 *            设定文件
	 * @return String 返回类型 @throws
	 */
	@RequestMapping(value = "/listPerson")
	public String listPerson(Model model, String callBackMethod, String orgId) {
		// model.addAttribute("orgId", Constants.DEFAULT_ORG_ID);
		model.addAttribute("orgId", orgId);
		model.addAttribute("callBackMethod", callBackMethod);
		return "modules/sys/data/person_list_main";
	}


	/**
	 * 获取总部人员，即魅族科技下的人员 @Title: listHeadquarters @author liusheng @date 2016年2月24日 下午2:26:27 @param @param
	 * model @param @param callBackMethod @param @return 设定文件 @return String 返回类型 @throws
	 */
	@RequestMapping(value = "/listHeadquarters")
	public String listHeadquarters(Model model, String callBackMethod, String orgId) {
		if (!StringUtils.isEmpty(orgId)) {
			model.addAttribute("orgId", orgId);
		} else {
			model.addAttribute("orgId", Constants.DEFAULT_ORG_ID);// 默认显示魅族下的人员
		}
		model.addAttribute("callBackMethod", callBackMethod);
		return "modules/sys/data/headquarters_list_main";
	}


	/**
	 * 角色列表 @Title: listRole @author liusheng @date 2016年1月28日 下午6:57:18 @param @param model @param @param
	 * callBackMethod @param @return 设定文件 @return String 返回类型 @throws
	 */
	@RequestMapping(value = "/listRole")
	public String listRole(Model model, String callBackMethod, String selUserId) {
		model.addAttribute("orgId", Constants.DEFAULT_ORG_ID);
		model.addAttribute("callBackMethod", callBackMethod);
		model.addAttribute("selUserId", selUserId);
		return "modules/sys/data/role_list_main";
	}

	@ResponseBody
	@RequestMapping(value = "/dataRoleList")
	public ActionResultModel<RoleEntity> dataRoleList(RoleEntity obj, String selUserId, ServletRequest request) {
		ActionResultModel<RoleEntity> arm = new ActionResultModel<RoleEntity>();
		try {
			List<RoleEntity> objList = null;
			if (!StringUtils.isEmpty(selUserId)) {// 判断是否已选入的
				objList = roleService.findAllAndSel(selUserId);
			} else {
				objList = (List<RoleEntity>) roleService.findAll(new Sort(Sort.Direction.ASC, "rolegroup"));
			}

			arm.setRecords(objList);
			arm.setSuccess(true);
		} catch (ServiceException e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return arm;
	}

	@ResponseBody
	@RequestMapping(value = "/dataAdmin")
	public List<TreeBaseBean> dataAdmin(ServletRequest request) {
		return dataService.dataAllAdmin();
	}

	/**
	 * 获取国家 @Title: dataCountry @author liusheng @date 2016年1月26日 下午2:42:43 @param @param pId @param @param
	 * request @param @return 设定文件 @return List<TreeBaseBean> 返回类型 @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/dataCountry")
	public List<TreeBaseBean> dataCountry(@RequestParam String pId, ServletRequest request) {
		List<TreeBaseBean> list = dataService.dataCountry(pId);
		return list;
	}

	/**
	 * 获取省 @Title: dataProvince @author liusheng @date 2016年1月26日 下午2:42:30 @param @param pId @param @param
	 * request @param @return 设定文件 @return List<TreeBaseBean> 返回类型 @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/dataProvince")
	public List<TreeBaseBean> dataProvince(@RequestParam String pId, ServletRequest request) {
		return dataService.dataProvince(pId);
	}

	/**
	 * 获取市 @Title: dataCity @author liusheng @date 2016年1月27日 下午7:18:43 @param @param pId @param @param
	 * request @param @return 设定文件 @return List<TreeBaseBean> 返回类型 @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/dataCity")
	public List<TreeBaseBean> dataCity(@RequestParam String pId, ServletRequest request) {
		return dataService.dataCity(pId);
	}

	/**
	 * 获取区 @Title: dataDistrict @author liusheng @date 2016年1月26日 下午2:42:05 @param @param pId @param @param
	 * request @param @return 设定文件 @return List<TreeBaseBean> 返回类型 @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/dataDistrict")
	public List<TreeBaseBean> dataDistrict(@RequestParam String pId, ServletRequest request) {
		return dataService.dataDistrict(pId);
	}

	@ResponseBody
	@RequestMapping(value = "/dataCouAndPro")
	public List<TreeBaseBean> dataCouAndPro(ServletRequest request) {
		List<TreeBaseBean> list = dataService.dataProvince(Constants.DEFAULT_CHINA_ID);
		TreeBaseBean bean = new TreeBaseBean();
		bean.setId(Constants.DEFAULT_CHINA_ID);
		bean.setName(Constants.DEFAULT_CHINA_NAME);
		bean.setpId("0");
		list.add(bean);
		return list;
	}

	/**
	 * 组织列表按where语句查询
	 * 
	 * @param model
	 * @param callBackMethod
	 * @param where
	 * @return
	 */
	@RequestMapping(value = "/listCorp")
	public String listCorp(Model model, String callBackMethod, String where) {
		model.addAttribute("callBackMethod", callBackMethod);
		model.addAttribute("where", where);
		return "modules/sys/data/ref_corplist";
	}

	/**
	 * 获取oss配置信息
	 * 
	 * @param response
	 * @param endpoint
	 * @param accessId
	 * @param accessKey
	 * @param bucket
	 * @param entityType
	 */
	@RequestMapping(value = "/getOSSConfigure")
	public void getOSSConfigure(HttpServletResponse response, String endpoint, String accessId, String accessKey,
			String bucket, String entityType) {

		if (!StringUtils.isEmpty(endpoint)) {
			Constants.OSS_ENDPOINT = endpoint;
		}
		if (!StringUtils.isEmpty(accessId)) {
			Constants.OSS_ACCESSID = accessId;
		}
		if (!StringUtils.isEmpty(accessKey)) {
			Constants.OSS_ACCESSKEY = accessKey;
		}
		if (!StringUtils.isEmpty(bucket)) {
			Constants.OSS_BUCKET = bucket;
		}

		String dir = "uploadfiles/";// 上传文件的路径
		if (!StringUtils.isEmpty(entityType)) {
			dir += entityType.toLowerCase() + "/";
		}
		String host = "http://" + Constants.OSS_BUCKET + "." + Constants.OSS_ENDPOINT;
		OSSClient client = new OSSClient(Constants.OSS_ENDPOINT, Constants.OSS_ACCESSID, Constants.OSS_ACCESSKEY);
		try {
			long expireTime = 30;
			long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
			Date expiration = new Date(expireEndTime);
			PolicyConditions policyConds = new PolicyConditions();
			policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
			policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

			String postPolicy = client.generatePostPolicy(expiration, policyConds);
			byte[] binaryData = postPolicy.getBytes("utf-8");
			String encodedPolicy = BinaryUtil.toBase64String(binaryData);
			String postSignature = client.calculatePostSignature(postPolicy);

			Map<String, String> respMap = new LinkedHashMap<String, String>();
			respMap.put("accessid", Constants.OSS_ACCESSID);
			respMap.put("policy", encodedPolicy);
			respMap.put("signature", postSignature);
			// respMap.put("expire", formatISO8601Date(expiration));
			respMap.put("dir", dir);
			respMap.put("host", host);
			respMap.put("expire", String.valueOf(expireEndTime / 1000));

			// respMap.put("callback",
			// "eyJjYWxsYmFja1VybCI6Imh0dHA6XC9cL29zcy1kZW1vLmFsaXl1bmNzLmNvbToyMzQ1MCIsImNhbGxiYWNrQm9keSI6ImZpbGVuYW1lPSR7b2JqZWN0fSZzaXplPSR7c2l6ZX0mbWltZVR5cGU9JHttaW1lVHlwZX0maGVpZ2h0PSR7aW1hZ2VJbmZvLmhlaWdodH0md2lkdGg9JHtpbWFnZUluZm8ud2lkdGh9IiwiY2FsbGJhY2tCb2R5VHlwZSI6ImFwcGxpY2F0aW9uXC94LXd3dy1mb3JtLXVybGVuY29kZWQifQ==");

			JSONObject ja1 = JSONObject.fromObject(respMap);
			// return ja1.toString();
			// 将实体对象转换为JSON Object转换
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.append(ja1.toString());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取oss上文件信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getOssFiles")
	public String getOssFiles() {
		OSSClient client = new OSSClient(Constants.OSS_ENDPOINT, Constants.OSS_ACCESSID, Constants.OSS_ACCESSKEY);
		StringBuffer str = new StringBuffer("{");
		try {

			ObjectListing objectListing = null;
			String nextMarker = null;
			final int maxKeys = 30;
			long i = 0;
			do {
				objectListing = client.listObjects(
						new ListObjectsRequest(Constants.OSS_BUCKET).withMarker(nextMarker).withMaxKeys(maxKeys));
				List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
				for (OSSObjectSummary s : sums) {
					i++;
					str.append("\"").append(i).append("\":").append("\"").append(s.getKey()).append("\",");
				}
				nextMarker = objectListing.getNextMarker();
			} while (objectListing.isTruncated());

			str.append("}");

		} catch (OSSException oe) {
			System.out.println("Caught an OSSException, which means your request made it to OSS, "
					+ "but was rejected with an error response for some reason.");
			System.out.println("Error Message: " + oe.getErrorCode());
			System.out.println("Error Code:       " + oe.getErrorCode());
			System.out.println("Request ID:      " + oe.getRequestId());
			System.out.println("Host ID:           " + oe.getHostId());
		} catch (ClientException ce) {
			System.out.println("Caught an ClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with OSS, "
					+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ce.getMessage());
		} finally {
			client.shutdown();
		}

		return str.toString();
	}

	/**
	 * 选择流程用户组页面
	 * 
	 * @param model
	 * @param callBackMethod
	 * @return
	 */
	@RequestMapping(value = "/listProcessUserGroup")
	public String listProcessUserGroup(Model model, String callBackMethod) {
		model.addAttribute("callBackMethod", callBackMethod);

		return "modules/sys/data/ref_process_usergroup_main";
	}

	/**
	 * 获取流程用户组数据 @Title: dataProcessUserGroup @author liusheng @date 2016年5月18日 上午10:46:53 @param @return 设定文件 @return
	 * ActionResultModel<ProcessUserGroupEntity> 返回类型 @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/dataProcessUserGroup")
	public ActionResultModel<ProcessUserGroupEntity> dataProcessUserGroup() {
		ActionResultModel<ProcessUserGroupEntity> arm = new ActionResultModel<ProcessUserGroupEntity>();
		try {
			List<ProcessUserGroupEntity> result = (List<ProcessUserGroupEntity>) processUserGroupService.findAll();
			arm.setRecords(result);
			arm.setSuccess(true);
		} catch (ServiceException e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
		}
		return arm;
	}

	/**
	 * 选择用户列表页面 @Title: listUser @author liusheng @date 2016年5月18日 上午11:08:59 @param @param model @param @param
	 * callBackMethod @param @return 设定文件 @return String 返回类型 @throws
	 */
	@RequestMapping(value = "/listUser")
	public String listUser(Model model, String callBackMethod) {
		model.addAttribute("callBackMethod", callBackMethod);
		return "modules/sys/data/ref_user_main";
	}

	/**
	 * 获取用户数据 @Title: dataUser @author liusheng @date 2016年5月18日 上午11:10:18 @param @return 设定文件 @return
	 * ActionResultModel<UserEntity> 返回类型 @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/dataUser")
	public ActionResultModel<UserEntity> dataUser() {
		ActionResultModel<UserEntity> arm = new ActionResultModel<UserEntity>();
		try {
			List<UserEntity> result = (List<UserEntity>) userService.getUserBySql();
			arm.setRecords(result);
			arm.setSuccess(true);
		} catch (ServiceException e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
		}
		return arm;
	}

	/**
	 * 获取业务单元列表信息 @Title: dataOrgList @author liusheng @date 2016年5月12日 下午2:38:59 @param @param request @param @param
	 * pid @param @return 设定文件 @return ActionResultModel<OrgEntity> 返回类型 @throws
	 */
	@RequestMapping(value = "/dataOrgLists")
	@ResponseBody
	private String dataOrgLists() {
		ActionResultModel<OrgEntity> arm = new ActionResultModel<OrgEntity>();
		String jsonStr = "";
		try {
			List<OrgEntity> list = (List<OrgEntity>) orgService.findAll(new Sort(Sort.Direction.ASC, "org_code"));
			arm.setRecords(list);
			arm.setSuccess(true);

		} catch (ServiceException e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
		}
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter(OrgEntity.class, "uuid", "org_name", "org_code");
		jsonStr = JSON.toJSONString(arm, filter);
		return jsonStr;
	}

}