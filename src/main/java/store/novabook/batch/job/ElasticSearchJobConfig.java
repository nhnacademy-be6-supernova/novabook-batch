package store.novabook.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;
import store.novabook.batch.search.service.ElasticSearchService;

@Configuration
@RequiredArgsConstructor
public class ElasticSearchJobConfig {
	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final ElasticSearchService elasticSearchService;


	@Bean
	public Job productUpdateJob() {
		return new JobBuilder("productUpdateJob", jobRepository)
			.start(productUpdateStep())
			.build();
	}

	@Bean
	public Step productUpdateStep() {
		return new StepBuilder("productUpdateStep", jobRepository)
			.tasklet(productUpdateTasklet(), transactionManager)
			.build();
	}

	@Bean
	public Tasklet productUpdateTasklet() {
		return ((contribution, chunkContext) -> {
			elasticSearchService.productDocumentsUpdate(100);
			return RepeatStatus.FINISHED;
		});
	}
}