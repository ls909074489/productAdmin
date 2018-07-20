package com.yy.modules.sys.job;

import org.quartz.JobExecutionException;

import com.yy.modules.sys.job.schedule.JobSchedule;

public interface YYJob {
	void execute(JobSchedule schedule) throws JobExecutionException;

	// void triggerJob(JobKey jobKey, JobDataMap data) throws JobExecutionException;

	// abstract void execute(JobExecutionContext schedule)
	// throws JobExecutionException;

}
