package store.novabook.batch.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
	basePackages = "store.novabook.batch.store.member.repository",
	entityManagerFactoryRef = "storeEntityManagerFactory",
	transactionManagerRef = "storeTransactionManager"
)
public class StoreDataSourceConfig {

	@Bean(name = "storeDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.store")
	public DataSource storeDataSource() {
		return DataSourceBuilder.create()
			.build();
	}

	@Bean(name = "storeEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean storeEntityManagerFactory(
		@Qualifier("storeDataSource") DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource);
		entityManagerFactory.setPackagesToScan("store.novabook.batch.store.member.entity");
		entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "validate");
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
		properties.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
		properties.put("hibernate.format_sql", true);

		entityManagerFactory.setJpaPropertyMap(properties);

		return entityManagerFactory;
	}

	@Bean(name = "storeTransactionManager")
	public PlatformTransactionManager storeTransactionManager(
		@Qualifier("storeEntityManagerFactory") LocalContainerEntityManagerFactoryBean storeEntityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(storeEntityManagerFactory.getObject());
		return transactionManager;	}


}
