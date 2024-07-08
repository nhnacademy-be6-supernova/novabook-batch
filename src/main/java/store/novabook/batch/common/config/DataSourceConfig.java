package store.novabook.batch.common.config;

import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import store.novabook.batch.common.dto.DatabaseConfigDto;
import store.novabook.batch.common.util.KeyManagerUtil;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {
	private final Environment environment;


	@Bean(name = "storeDataSource")
	public DataSource storeDataSource() {
		String keyid = environment.getProperty("nhn.cloud.keyManager.storeKeyId");
		return KeyManagerUtil.getDataSource(environment, keyid);
	}

	@Primary
	@Bean(name = "couponDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.coupon")
	public DataSource couponDataSource() {
		String keyid = environment.getProperty("nhn.cloud.keyManager.couponKeyId");
		return KeyManagerUtil.getDataSource(environment, keyid);
	}

	@BatchDataSource
	@Bean
	public DataSource defaultDataSource() {
		String keyid = environment.getProperty("nhn.cloud.keyManager.batchKeyId");
		return KeyManagerUtil.getDataSource(environment, keyid);
	}





}
