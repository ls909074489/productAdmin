package com.yy.modules.sys.job.test;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

import com.yy.modules.sys.job.YYJob;
import com.yy.modules.sys.job.schedule.JobSchedule;

@Service("testJob1")
@DisallowConcurrentExecution
public class TestJob1 implements YYJob {

	@Override
	public void execute(JobSchedule schedule) throws JobExecutionException {

		// for (int i = 0; i < 50; i++) {
		// try {
		// Thread.sleep(100);
		// //System.out.println("后台任务1...... 序号：" + i + "......" + DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
	}

}
