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

import store.novabook.batch.store.repository.member.MemberRepository;
import store.novabook.batch.store.repository.member.MemberStatusRepository;

@EnableBatchProcessing
@SpringBootTest(classes = {MemberStatusUpdateJobConfig.class})
@Import({BatchTestConfig.class})
class MemberStatusUpdateJobConfigurationTest {

	@Autowired
	private ApplicationContext context;

	@MockBean
	private JobRepository jobRepository;

	@MockBean
	private MemberRepository memberRepository;

	@MockBean
	private MemberStatusRepository memberStatusRepository;

	@Test
	void testBeansAreLoaded() {
		assertNotNull(context.getBean("memberStatusUpdateJob", Job.class),
			"memberStatusUpdateJob bean should be loaded in the context");
		assertNotNull(context.getBean("updateMemberStatusStep", Step.class),
			"updateMemberStatusStep bean should be loaded in the context");
		;
	}

}
