package store.novabook.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.novabook.batch.exception.CriticalException;
import store.novabook.batch.exception.ErrorCode;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {

	private final JobLauncher jobLauncher;
	private final Job birthdayCouponJob;

	@Scheduled(cron = "0 0 0 1 * ?")
	public void runBirthdayCouponJob() {
		try {
			jobLauncher.run(birthdayCouponJob,
				new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters());
		} catch (Exception exception) {
			throw new CriticalException(ErrorCode.JOB_FAIL_BIRTHDAY);
		}
	}
}