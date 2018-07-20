package com.yy.frame.initial;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.yy.frame.redis.YYRedisCache;
import com.yy.modules.sys.admins.AdminisEntity;
import com.yy.modules.sys.admins.AdminisService;
import com.yy.modules.sys.admins.AdminisUtils;
import com.yy.modules.sys.alertmsg.AlertmsgService;
import com.yy.modules.sys.alertmsg.YYMsg;
import com.yy.modules.sys.documents.DocumentsEntity;
import com.yy.modules.sys.documents.DocumentsService;
import com.yy.modules.sys.documents.DocumentsUtil;
import com.yy.modules.sys.enumdata.EnumDataService;
import com.yy.modules.sys.enumdata.EnumDataSubEntity;
import com.yy.modules.sys.enumdata.EnumDataUtils;
import com.yy.modules.sys.imexlate.ImexlateService;
import com.yy.modules.sys.imexlate.ImexlateUtil;
import com.yy.modules.sys.org.OrgService;
import com.yy.modules.sys.org.OrgUtils;
import com.yy.modules.sys.param.ParameterService;
import com.yy.modules.sys.param.ParameterUtil;
import com.yy.modules.sys.tabconstr.TableConstraintsService;

/**
 * 启动初始化类
 * 
 * @author zhangcb
 *
 */
@Service
public class YYInitializtion implements ApplicationListener<ContextRefreshedEvent> {

	private static Logger logger = LoggerFactory.getLogger(YYInitializtion.class);

	@Autowired
	ParameterService parameterService;

	@Autowired
	OrgService orgService;

	@Autowired
	EnumDataService enumDataService;

	@Autowired
	AlertmsgService alertmsgService;

	@Autowired
	AdminisService adminisService;

	@Autowired
	TableConstraintsService tableConstraintsService;

	@Autowired
	DocumentsService documentsService;

	@Autowired
	ImexlateService imexlateService;

	@Autowired
	YYRedisCache yyRedisCache;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			// 在bean初始化前执行
			beforeInitialization(event);
		} else {
			// 在bean初始 后 前执行
			afterInitialization(event);
		}
	}

	/**
	 * 在bean初始前执行
	 * 
	 * @param event
	 */
	public void beforeInitialization(ContextRefreshedEvent event) {
		// System.out.println("----------在bean初始前执行:--------------");
		// CacheManager cacheManager = CacheManager.create();
	}

	/**
	 * 在bean初始后执行
	 * 
	 * @param event
	 */
	public void afterInitialization(ContextRefreshedEvent event) {

		System.out.println(">>>>>>>>>> 平台开始处理缓存:--------------");

		try {
			Map<String, List<EnumDataSubEntity>> enumMap = enumDataService.getEnumDataMap();
			EnumDataUtils.updateEnumDatas(enumMap);
			logger.info(">>>>>>>>>> 加载枚举完成！>>>>>>>>");
		} catch (ServiceException e) {
			logger.error(">>>>>>>>> 加载枚举失败！！！>>>>>>>>");
			e.printStackTrace();
		}

		try {
			ParameterUtil.updateParam(parameterService.findAll());
			logger.info(">>>>>>>>>> 系统参数加载完成！>>>>>>>>");
		} catch (ServiceException e) {
			logger.error(">>>>>>>>> 系统参数参数失败！>>>>>>>>");
			e.printStackTrace();
		}

		try {
			tableConstraintsService.intiConstraintsMap();
			logger.info(">>>>>>>>>> 表主外键信息加载完成！>>>>>>>>");
		} catch (ServiceException e) {
			logger.error(">>>>>>>>> 表主外键信息加载失败！>>>>>>>>");
			e.printStackTrace();
		}

		try {
			YYMsg.updateEntitys(alertmsgService.findAll());
			logger.info(">>>>>>>>>> 消息提示信息缓存加载完成！>>>>>>>>");
		} catch (ServiceException e) {
			logger.error(">>>>>>>>> 消息提示信息缓存加载失败！>>>>>>>>");
			e.printStackTrace();
		}

		try {
			AdminisUtils.updateEntitys((ArrayList<AdminisEntity>) adminisService.findAll());
			logger.info(">>>>>>>>>> 行政区域缓存加载完成！>>>>>>>>");
		} catch (ServiceException e) {
			logger.error(">>>>>>>>> 行政区域缓存加载失败！>>>>>>>>");
			e.printStackTrace();
		}

		try {
			OrgUtils.updateEntitys(orgService.findAll());
			logger.info(">>>>>>>>>> 组织架构缓存加载完成！>>>>>>>>");
		} catch (ServiceException e) {
			logger.error(">>>>>>>>> 组织架构缓存加载失败！>>>>>>>>");
			e.printStackTrace();
		}

		try {
			DocumentsUtil.updateEntitys(documentsService.findAll());
			logger.info(">>>>>>>>>> 单据号规则缓存加载完成！>>>>>>>>");
		} catch (ServiceException e) {
			logger.error(">>>>>>>>> 单据号规则缓存加载失败！>>>>>>>>");
			e.printStackTrace();
		}

		try {
			imexlateService.initImexlate();
			logger.info(">>>>>>>>>> 导出导入模板生成完成！>>>>>>>>");
		} catch (ServiceException e) {
			logger.error(">>>>>>>>> 导出导入模板生成失败！！！>>>>>>>>");
			e.printStackTrace();
		}
		
		try {
			ImexlateUtil.updateImexlate(imexlateService.findAll());
			logger.info(">>>>>>>>>> 导出导入模板缓存加载完成！>>>>>>>>");
		} catch (ServiceException e) {
			logger.error(">>>>>>>>> 导出导入模板缓存加载完成！！！>>>>>>>>");
			e.printStackTrace();
		}

		// 是否启用redis，启用就清除所有的缓存
		if ("1".endsWith(ParameterUtil.getParamValue("sys_redis", "0"))) {
			// yyRedisCache.clear();
			// logger.info(">>>>>>>>>> 清除所有的redis缓存完成！>>>>>>>>");
			try {
				Iterable<DocumentsEntity> documentsEntitys = documentsService.findAll();
				for (DocumentsEntity entity : documentsEntitys) {
					if (yyRedisCache.get("billtype:" + entity.getDocumentType()) == null) {
						yyRedisCache.put("billtype:" + entity.getDocumentType(), entity);
					}
				}
				logger.info(">>>>>>>>>> 单据号规则redis缓存加载完成！>>>>>>>>");
			} catch (ServiceException e) {
				logger.error(">>>>>>>>> 单据号规则redis缓存加载失败！>>>>>>>>");
				e.printStackTrace();
			}
		}
	}

}