package store.novabook.batch.common.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "store.novabook.batch.coupon.repository", entityManagerFactoryRef = "couponEntityManagerFactory", transactionManagerRef = "couponTransactionManager")
public class CouponDataSourceConfig {


	@Primary
	@Bean(name = "couponEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean couponEntityManagerFactory(
		@Qualifier("couponDataSource") DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource);
		entityManagerFactory.setPackagesToScan("store.novabook.batch.coupon.entity");
		entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "validate");
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
		properties.put("hibernate.physical_naming_strategy",
			"org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
		properties.put("hibernate.format_sql", true);
		entityManagerFactory.setJpaPropertyMap(properties);
		return entityManagerFactory;
	}

	@Primary
	@Bean(name = "couponTransactionManager")
	public PlatformTransactionManager couponTransactionManager(
		@Qualifier("couponEntityManagerFactory") LocalContainerEntityManagerFactoryBean couponEntityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(couponEntityManagerFactory.getObject());
		return transactionManager;
	}
}
