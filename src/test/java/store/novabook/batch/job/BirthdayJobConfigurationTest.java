package store.novabook.batch.job;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import store.novabook.batch.coupon.repository.CouponRepository;
import store.novabook.batch.coupon.repository.CouponTemplateRepository;
import store.novabook.batch.store.repository.member.MemberCouponRepository;
import store.novabook.batch.store.repository.member.MemberRepository;

@EnableBatchProcessing
@SpringBootTest(classes = {BirthdayCouponJobConfig.class})
@Import({BatchTestConfig.class})
class BirthdayJobConfigurationTest {

	@Autowired
	private ApplicationContext context;

	@MockBean
	private JobRepository jobRepository;

	@MockBean
	private CouponRepository couponRepository;
	@MockBean
	private MemberRepository memberRepository;

	@MockBean
	private MemberCouponRepository memberCouponRepository;

	@MockBean
	private CouponTemplateRepository couponTemplateRepository;

	@Test
	void testBeansAreLoaded() {
		assertNotNull(context.getBean("birthdayCouponJob", Job.class),
			"birthdayCouponJob bean should be loaded in the context");
		assertNotNull(context.getBean("birthdayCouponStep", Step.class),
			"birthdayCouponStep bean should be loaded in the context");
		;
	}
}
