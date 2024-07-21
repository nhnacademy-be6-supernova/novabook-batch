package store.novabook.batch.job.listner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

class LogJobExecutionListenerTest {

	private JobExecutionListener listener;
	private JobExecution jobExecution;

	@BeforeEach
	void setUp() {
		listener = new LogJobExecutionListener();
		jobExecution = Mockito.mock(JobExecution.class);
	}

	@Test
	void testBeforeJob() {
		listener.beforeJob(jobExecution);
	}

	@Test
	void testAfterJob() {
		long startTime = System.currentTimeMillis();
		listener.afterJob(jobExecution);
		long endTime = System.currentTimeMillis();
		assert (endTime - startTime) >= 3000;
	}
}
