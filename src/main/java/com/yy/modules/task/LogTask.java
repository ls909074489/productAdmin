package com.yy.modules.task;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yy.modules.sys.job.YYJob;
import com.yy.modules.sys.job.schedule.JobSchedule;
import com.yy.modules.sys.log.LogLoginService;
import com.yy.modules.sys.log.LogService;

@Service("logTask")
@DisallowConcurrentExecution
public class LogTask implements YYJob {

	@Autowired
	LogService logService;

	@Autowired
	LogLoginService logLoginService;

	private static Logger logger = LoggerFactory.getLogger(LogTask.class);

	@Override
	public void execute(JobSchedule schedule) throws JobExecutionException {
		try {
			// 删除7天前操作日志
			logService.deleteLog(30);
		} catch (Exception e) {
			logger.info("清除平台操作日志失败：" + e);
		}

		try {
			// 删除7天前登录日志
			logLoginService.deleteLog(100);
		} catch (Exception e) {
			logger.info("清除平台登录日志失败：" + e);
		}

	}

}