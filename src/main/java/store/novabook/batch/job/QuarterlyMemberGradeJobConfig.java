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
import store.novabook.batch.store.entity.member.Member;
import store.novabook.batch.store.entity.member.MemberGradeHistory;
import store.novabook.batch.store.entity.member.MemberGradePolicy;
import store.novabook.batch.store.repository.member.MemberGradeHistoryRepository;
import store.novabook.batch.store.repository.member.MemberGradePolicyRepository;
import store.novabook.batch.store.repository.member.MemberRepository;
import store.novabook.batch.store.repository.orders.OrdersRepository;

@Configuration
@RequiredArgsConstructor
public class QuarterlyMemberGradeJobConfig {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final MemberRepository memberRepository;
	private final OrdersRepository ordersRepository;
	private final MemberGradePolicyRepository memberGradePolicyRepository;
	private final MemberGradeHistoryRepository memberGradeHistoryRepository;

	@Bean("quarterlyMemberGradeJob")
	public Job birthdayCouponJob(@Qualifier("quarterlyMemberGradeStep") Step memberGradeCalculateStep) {
		return new JobBuilder("QuarterlyMemberGradeJob", jobRepository).incrementer(new RunIdIncrementer())
			.start(memberGradeCalculateStep)
			.build();
	}

	@JobScope
	@Bean("quarterlyMemberGradeStep")
	public Step quarterlyMemberGradeStep(ItemReader<Member> memberReader,
		ItemProcessor<Member, MemberGradeHistory> memberProcessor, ItemWriter<MemberGradeHistory> memberWriter) {
		return new StepBuilder("quarterlyMemberGradeStep", jobRepository).<Member, MemberGradeHistory>chunk(500,
			transactionManager).reader(memberReader).processor(memberProcessor).writer(memberWriter).build();
	}

	@StepScope
	@Bean
	public RepositoryItemReader<Member> memberReader() {
		return new RepositoryItemReaderBuilder<Member>().name("memberReader")
			.repository(memberRepository)
			.methodName("findByMemberStatusId")
			.pageSize(500)
			.arguments(List.of(1L))
			.sorts(Collections.singletonMap("id", Sort.Direction.ASC))
			.build();
	}

	@StepScope
	@Bean
	public ItemProcessor<Member, MemberGradeHistory> memberProcessor() {
		List<MemberGradePolicy> policyList = memberGradePolicyRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime startOfQuarter = now.minusMonths(3)
			.withMonth(((now.getMonthValue() - 1) / 3) * 3 + 1)
			.withDayOfMonth(1)
			.toLocalDate()
			.atStartOfDay();
		LocalDateTime endOfQuarter = now.withMonth(((now.getMonthValue() - 1) / 3 + 1) * 3)
			.withDayOfMonth(1)
			.toLocalDate()
			.atStartOfDay()
			.minusSeconds(1);
		return member -> {
			Long totalOrderAmount = ordersRepository.sumTotalAmountByMemberAndOrdersDateBetween(member, startOfQuarter,
				endOfQuarter);
			MemberGradePolicy applicablePolicy = findApplicablePolicy(policyList, totalOrderAmount);

			return MemberGradeHistory.builder()
				.member(member)
				.memberGradePolicy(applicablePolicy)
				.quarter(LocalDateTime.now())
				.build();
		};
	}

	@StepScope
	@Bean
	public ItemWriter<MemberGradeHistory> memberWriter() {
		return memberGradeHistoryRepository::saveAll;
	}

	private MemberGradePolicy findApplicablePolicy(List<MemberGradePolicy> gradePolicies, Long orderAmount) {
		for (MemberGradePolicy policy : gradePolicies) {
			if (orderAmount >= policy.getMinRange() && orderAmount < policy.getMaxRange()) {
				return policy;
			}
		}
		return gradePolicies.getFirst();
	}
}
