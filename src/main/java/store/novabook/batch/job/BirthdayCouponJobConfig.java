package store.novabook.batch.job;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
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
import store.novabook.batch.coupon.entity.Coupon;
import store.novabook.batch.coupon.entity.CouponStatus;
import store.novabook.batch.coupon.entity.CouponTemplate;
import store.novabook.batch.coupon.entity.CouponType;
import store.novabook.batch.coupon.repository.CouponRepository;
import store.novabook.batch.coupon.repository.CouponTemplateRepository;
import store.novabook.batch.common.exception.ErrorCode;
import store.novabook.batch.common.exception.InformationException;
import store.novabook.batch.job.listner.LogJobExecutionListener;
import store.novabook.batch.store.entity.member.Member;
import store.novabook.batch.store.entity.member.MemberCoupon;
import store.novabook.batch.store.repository.member.MemberCouponRepository;
import store.novabook.batch.store.repository.member.MemberRepository;

@Configuration
@RequiredArgsConstructor
public class BirthdayCouponJobConfig {
	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final CouponRepository couponRepository;
	private final MemberRepository memberRepository;
	private final MemberCouponRepository memberCouponRepository;
	private final CouponTemplateRepository couponTemplateRepository;

	@Bean("birthdayCouponJob")
	public Job birthdayCouponJob(@Qualifier("birthdayCouponStep") Step birthdayCouponStep) {
		return new JobBuilder("birthdayCouponJob", jobRepository).incrementer(new RunIdIncrementer())
			.start(birthdayCouponStep)
			.listener(new LogJobExecutionListener())
			.build();
	}

	@JobScope
	@Bean("birthdayCouponStep")
	public Step birthdayCouponStep(ItemReader<Member> birthdayMemberReader,
		ItemProcessor<Member, MemberCoupon> birthdayCouponProcessor, ItemWriter<MemberCoupon> birthdayCouponWriter) {
		return new StepBuilder("birthdayCouponStep", jobRepository).<Member, MemberCoupon>chunk(500, transactionManager)
			.reader(birthdayMemberReader)
			.processor(birthdayCouponProcessor)
			.writer(birthdayCouponWriter)
			.build();
	}

	@StepScope
	@Bean
	public RepositoryItemReader<Member> birthdayMemberReader() {
		return new RepositoryItemReaderBuilder<Member>().name("birthdayMemberReader")
			.repository(memberRepository)
			.methodName("findByBirthMonthAndMemberStatusId")
			.pageSize(500)
			.arguments(List.of(LocalDate.now().getMonthValue(), 1L))
			.sorts(Collections.singletonMap("id", Sort.Direction.ASC))
			.build();
	}

	@StepScope
	@Bean
	public ItemProcessor<Member, MemberCoupon> birthdayCouponProcessor() {
		LocalDateTime startOfDay = LocalDate.now().withDayOfMonth(1).atStartOfDay();
		CouponTemplate birthday = couponTemplateRepository.findTopByTypeOrderByCreatedAtDesc(CouponType.BIRTHDAY)
			.orElseThrow(() -> new InformationException(ErrorCode.BIRTHDAY_COUPON_NOT_FOUND));
		return member -> {
			Coupon coupon = Coupon.of(birthday, CouponStatus.UNUSED, startOfDay.plusHours(birthday.getUsePeriod()));
			Coupon saved = couponRepository.save(coupon);
			return MemberCoupon.builder().couponId(saved.getId()).member(member).build();
		};
	}

	@StepScope
	@Bean
	public ItemWriter<MemberCoupon> birthdayCouponWriter() {
		return memberCouponRepository::saveAll;
	}

}
