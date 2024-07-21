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

import store.novabook.batch.store.repository.member.MemberGradeHistoryRepository;
import store.novabook.batch.store.repository.member.MemberGradePolicyRepository;
import store.novabook.batch.store.repository.member.MemberRepository;
import store.novabook.batch.store.repository.orders.OrdersRepository;

@EnableBatchProcessing
@SpringBootTest(classes = {QuarterlyMemberGradeJobConfig.class})
@Import({BatchTestConfig.class})
class QuarterlyMemberGradeJobConfigurationTest {

	@Autowired
	private ApplicationContext context;

	@MockBean
	private JobRepository jobRepository;

	@MockBean
	private MemberRepository memberRepository;

	@MockBean
	private OrdersRepository ordersRepository;

	@MockBean
	private MemberGradePolicyRepository memberGradePolicyRepository;

	@MockBean
	private MemberGradeHistoryRepository memberGradeHistoryRepository;

	@Test
	void testBeansAreLoaded() {
		assertNotNull(context.getBean("quarterlyMemberGradeJob", Job.class),
			"quarterlyMemberGradeJob bean should be loaded in the context");
		assertNotNull(context.getBean("quarterlyMemberGradeStep", Step.class),
			"quarterlyMemberGradeStep bean should be loaded in the context");
		;
	}

}
