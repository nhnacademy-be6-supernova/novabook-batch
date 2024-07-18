package store.novabook.batch.job.listner;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class LogJobExecutionListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
		// Job 시작 전에 실행할 코드
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		// Job 완료 후 3초간 대기
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException ignore) {
		}
	}
}
