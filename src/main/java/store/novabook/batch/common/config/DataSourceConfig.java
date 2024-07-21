package store.novabook.batch.common.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import store.novabook.batch.common.util.KeyManagerUtil;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {
	private final Environment environment;


	@Bean(name = "storeDataSource")
	public DataSource storeDataSource() {
		String keyid = environment.getProperty("nhn.cloud.keyManager.storeKeyId");
		RestTemplate restTemplate = new RestTemplate();
		return KeyManagerUtil.getDataSource(environment, keyid, restTemplate);
	}

	@Primary
	@Bean(name = "couponDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.coupon")
	public DataSource couponDataSource() {
		String keyid = environment.getProperty("nhn.cloud.keyManager.couponKeyId");
		RestTemplate restTemplate = new RestTemplate();
		return KeyManagerUtil.getDataSource(environment, keyid, restTemplate);
	}

	@BatchDataSource
	@Bean
	public DataSource defaultDataSource() {
		String keyid = environment.getProperty("nhn.cloud.keyManager.batchKeyId");
		RestTemplate restTemplate = new RestTemplate();
		return KeyManagerUtil.getDataSource(environment, keyid, restTemplate);
	}
}
