package store.novabook.batch.common.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {

	@Value("${spring.datasource.store.driver-class-name}")
	private String storeDriverClassName;
	@Value("${spring.datasource.store.jdbc-url}")
	private String storeJdbcUrl;
	@Value("${spring.datasource.store.username}")
	private String storeJdbcUsername;
	@Value("${spring.datasource.store.password}")
	private String storeJdbcPassword;

	@Value("${spring.datasource.coupon.driver-class-name}")
	private String couponDriverClassName;
	@Value("${spring.datasource.coupon.jdbc-url}")
	private String couponJdbcUrl;
	@Value("${spring.datasource.coupon.username}")
	private String couponJdbcUsername;
	@Value("${spring.datasource.coupon.password}")
	private String couponJdbcPassword;

	@Value("${spring.datasource.batch.driver-class-name}")
	private String batchDriverClassName;
	@Value("${spring.datasource.batch.jdbc-url}")
	private String batchJdbcUrl;
	@Value("${spring.datasource.batch.username}")
	private String batchJdbcUsername;
	@Value("${spring.datasource.batch.password}")
	private String batchJdbcPassword;

	@Value("${spring.datasource.dbcp2.initial-size}")
	private int initialSize;
	@Value("${spring.datasource.dbcp2.max-idle}")
	private int maxIdle;
	@Value("${spring.datasource.dbcp2.min-idle}")
	private int minIdle;
	@Value("${spring.datasource.dbcp2.validation-query}")
	private String validationQuery;
	@Value("${spring.datasource.dbcp2.default-auto-commit}")
	private boolean autoCommit;

	@Bean(name = "storeDataSource")
	public DataSource storeDataSource() {
		return getDataSource(storeDriverClassName, storeJdbcUrl, storeJdbcUsername, storeJdbcPassword, initialSize,
			maxIdle, minIdle, validationQuery, autoCommit);
	}

	@Primary
	@Bean(name = "couponDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.coupon")
	public DataSource couponDataSource() {
		return getDataSource(couponDriverClassName, couponJdbcUrl, couponJdbcUsername, couponJdbcPassword, initialSize,
			maxIdle, minIdle, validationQuery, autoCommit);
	}

	@BatchDataSource
	@Bean
	public DataSource defaultDataSource() {
		return getDataSource(batchDriverClassName, batchJdbcUrl, batchJdbcUsername, batchJdbcPassword, initialSize,
			maxIdle, minIdle, validationQuery, autoCommit);
	}

	private DataSource getDataSource(String driverClassName, String jdbcUrl, String jdbcUsername, String jdbcPassword,
		int initialSize, int maxIdle, int minIdle, String validationQuery, boolean defaultAutoCommit) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(jdbcUrl);
		dataSource.setUsername(jdbcUsername);
		dataSource.setPassword(jdbcPassword);
		dataSource.setInitialSize(initialSize);
		dataSource.setMaxIdle(maxIdle);
		dataSource.setMinIdle(minIdle);
		dataSource.setValidationQuery(validationQuery);
		dataSource.setDefaultAutoCommit(defaultAutoCommit);
		return dataSource;
	}
}
