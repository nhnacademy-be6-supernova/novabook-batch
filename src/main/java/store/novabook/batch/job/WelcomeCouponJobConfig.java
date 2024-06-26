package store.novabook.batch.job;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;
import store.novabook.batch.coupon.repository.CouponRepository;
import store.novabook.batch.store.member.repository.MemberRepository;

@Configuration
@RequiredArgsConstructor
public class WelcomeCouponJobConfig {
	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final CouponRepository couponRepository;
	private final MemberRepository memberRepository;



}
