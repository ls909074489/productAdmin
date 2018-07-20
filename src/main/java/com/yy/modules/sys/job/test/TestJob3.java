package com.yy.modules.sys.job.test;

import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

import com.yy.modules.sys.job.YYJob;
import com.yy.modules.sys.job.schedule.JobSchedule;

@Service("testJob3")
public class TestJob3 implements YYJob {

	@Override
	public void execute(JobSchedule schedule) throws JobExecutionException {
		// System.out.println("后台任务3......" + DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
	}
}
