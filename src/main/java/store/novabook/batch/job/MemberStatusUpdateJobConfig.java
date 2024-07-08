package store.novabook.batch.job;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;
import store.novabook.batch.common.exception.CriticalException;
import store.novabook.batch.common.exception.ErrorCode;
import store.novabook.batch.store.entity.member.Member;
import store.novabook.batch.store.entity.member.MemberStatus;
import store.novabook.batch.store.repository.member.MemberRepository;
import store.novabook.batch.store.repository.member.MemberStatusRepository;

@Configuration
@RequiredArgsConstructor
public class MemberStatusUpdateJobConfig {
	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final MemberRepository memberRepository;
	private final MemberStatusRepository memberStatusRepository;

	@Bean("memberStatusUpdateJob")
	public Job memberStatusUpdateJob(@Qualifier("updateMemberStatusStep") Step updateMemberStatusStep) {
		return new JobBuilder("memberStatusUpdateJob", jobRepository).incrementer(new RunIdIncrementer())
			.start(updateMemberStatusStep)
			.build();
	}

	@JobScope
	@Bean("updateMemberStatusStep")
	public Step updateMemberStatusStep(ItemReader<Member> inactiveMemberReader,
		ItemProcessor<Member, Member> memberStatusProcessor, ItemWriter<Member> memberStatusWriter) {
		return new StepBuilder("updateMemberStatusStep", jobRepository).<Member, Member>chunk(500, transactionManager)
			.reader(inactiveMemberReader)
			.processor(memberStatusProcessor)
			.writer(memberStatusWriter)
			.build();
	}

	@StepScope
	@Bean
	public RepositoryItemReader<Member> inactiveMemberReader() {
		return new RepositoryItemReaderBuilder<Member>().name("inactiveMemberReader")
			.repository(memberRepository)
			.methodName("findAllByLatestLoginAtBefore")
			.pageSize(500)
			.arguments(List.of(LocalDateTime.now().minusMonths(3)))
			.sorts(Collections.singletonMap("id", Sort.Direction.ASC))
			.build();
	}

	@StepScope
	@Bean
	public ItemProcessor<Member, Member> memberStatusProcessor() {
		MemberStatus inactiveStatus = memberStatusRepository.findByName("휴면")
			.orElseThrow(() -> new CriticalException(ErrorCode.NOT_FOUND_MEMBER_STATUS));
		return member -> {
			member.setMemberStatus(inactiveStatus);
			return member;
		};
	}

	@StepScope
	@Bean
	public ItemWriter<Member> memberStatusWriter() {
		return memberRepository::saveAll;
	}
}
