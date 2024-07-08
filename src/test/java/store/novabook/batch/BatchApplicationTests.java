package store.novabook.batch;

import static org.hibernate.validator.internal.util.Contracts.*;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import store.novabook.batch.coupon.repository.CouponRepository;

@SpringBootTest
class BatchApplicationTests {

	@Autowired
	private DataSource dataSource;


	@Test
	void contextLoads() {
		assertNotNull(dataSource);
	}

}
