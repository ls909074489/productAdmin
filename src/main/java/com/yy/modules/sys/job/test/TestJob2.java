package com.yy.modules.sys.job.test;

import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

import com.yy.modules.sys.job.YYJob;
import com.yy.modules.sys.job.schedule.JobSchedule;

@Service("testJob2")
public class TestJob2 implements YYJob {

	@Override
	public void execute(JobSchedule schedule) throws JobExecutionException {
		// System.out.println("后台任务2......" + DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
	}

}
