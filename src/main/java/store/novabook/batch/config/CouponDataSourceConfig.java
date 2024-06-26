package store.novabook.batch.config;

import java.util.Objects;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
	basePackages = "store.novabook.batch.coupon.repository",
	entityManagerFactoryRef = "couponEntityManagerFactory",
	transactionManagerRef = "couponTransactionManager"
)
public class CouponDataSourceConfig {

	@Value("${spring.datasource.coupon.url}")
	private String couponDbUrl;

	@Value("${spring.datasource.coupon.username}")
	private String couponDbUsername;

	@Value("${spring.datasource.coupon.password}")
	private String couponDbPassword;

	@Value("${spring.datasource.coupon.driver-class-name}")
	private String couponDbDriverClassName;

	@Primary
	@Bean(name = "couponDataSource")
	public DataSource couponDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(couponDbDriverClassName);
		dataSource.setUrl(couponDbUrl);
		dataSource.setUsername(couponDbUsername);
		dataSource.setPassword(couponDbPassword);
		return dataSource;
	}

	@Primary
	@Bean(name = "couponEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean couponEntityManagerFactory(
		@Qualifier("couponDataSource") DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource);
		entityManagerFactory.setPackagesToScan("store.novabook.batch.coupon.entity");
		entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		return entityManagerFactory;
	}

	@Primary
	@Bean(name = "couponTransactionManager")
	public PlatformTransactionManager couponTransactionManager(
		@Qualifier("couponEntityManagerFactory") LocalContainerEntityManagerFactoryBean couponEntityManagerFactory) {
		return new JpaTransactionManager(Objects.requireNonNull(couponEntityManagerFactory.getObject()));
	}

	@Bean
	public DataSource dataSource() {
		return couponDataSource();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return couponTransactionManager(couponEntityManagerFactory(couponDataSource()));
	}
}
