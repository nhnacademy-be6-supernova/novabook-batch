package store.novabook.batch.config;

import java.util.Objects;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
	basePackages = "store.novabook.batch.store.*.repository",
	entityManagerFactoryRef = "storeEntityManagerFactory",
	transactionManagerRef = "storeTransactionManager"
)
public class StoreDataSourceConfig {

	@Value("${spring.datasource.store.url}")
	private String storeDbUrl;

	@Value("${spring.datasource.store.username}")
	private String storeDbUsername;

	@Value("${spring.datasource.store.password}")
	private String storeDbPassword;

	@Value("${spring.datasource.store.driver-class-name}")
	private String storeDbDriverClassName;

	@Bean(name = "storeDataSource")
	public DataSource storeDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(storeDbDriverClassName);
		dataSource.setUrl(storeDbUrl);
		dataSource.setUsername(storeDbUsername);
		dataSource.setPassword(storeDbPassword);
		return dataSource;
	}

	@Bean(name = "storeEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean storeEntityManagerFactory(
		@Qualifier("storeDataSource") DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource);
		entityManagerFactory.setPackagesToScan("store.novabook.batch.store.entity");
		entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		return entityManagerFactory;
	}

	@Bean(name = "storeTransactionManager")
	public PlatformTransactionManager storeTransactionManager(
		@Qualifier("storeEntityManagerFactory") LocalContainerEntityManagerFactoryBean storeEntityManagerFactory) {
		return new JpaTransactionManager(Objects.requireNonNull(storeEntityManagerFactory.getObject()));
	}
}
