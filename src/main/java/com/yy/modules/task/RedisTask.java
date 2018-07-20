package com.yy.modules.task;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yy.modules.sys.documents.DocumentsService;
import com.yy.modules.sys.job.YYJob;
import com.yy.modules.sys.job.schedule.JobSchedule;

@Service("redisTask")
@DisallowConcurrentExecution
public class RedisTask implements YYJob {

	@Autowired
	DocumentsService documentsService;
	private static Logger logger = LoggerFactory.getLogger(RedisTask.class);

	@Override
	public void execute(JobSchedule schedule) throws JobExecutionException {
		try {
			documentsService.refreshToBill();
		} catch (Exception e) {
			logger.info("从redis同步缓存到单据号设置失败：" + e);
		}
	}

}