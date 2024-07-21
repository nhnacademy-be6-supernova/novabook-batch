package store.novabook.batch.job.listner;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class LogJobExecutionListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException ignore) {
		}
	}
}
