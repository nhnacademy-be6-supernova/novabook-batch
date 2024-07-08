package store.novabook.batch.common.util;

import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import store.novabook.batch.common.dto.DatabaseConfigDto;

public class KeyManagerUtil {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	private KeyManagerUtil() {
	}

	public static DataSource getDataSource(Environment environment, String keyId) {

		String appkey = environment.getProperty("nhn.cloud.keyManager.appkey");
		String userId = environment.getProperty("nhn.cloud.keyManager.userAccessKeyId");
		String secretKey = environment.getProperty("nhn.cloud.keyManager.secretAccessKey");

		RestTemplate restTemplate = new RestTemplate();
		String baseUrl = "https://api-keymanager.nhncloudservice.com/keymanager/v1.2/appkey/{appkey}/secrets/{keyid}";
		String url = baseUrl.replace("{appkey}", appkey).replace("{keyid}", keyId);
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-TC-AUTHENTICATION-ID", userId);
		headers.set("X-TC-AUTHENTICATION-SECRET", secretKey);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.GET, entity,
			new ParameterizedTypeReference<Map<String, Object>>() {
			});

		Map<String, String> body = (Map<String, String>)response.getBody().get("body");

		// body 안의 secret 값을 JSON 문자열로 추출
		String secretJson = body.get("secret");

		// JSON 문자열을 DTO로 변환

		DatabaseConfigDto config;
		try {
			config = objectMapper.readValue(secretJson, DatabaseConfigDto.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
		dataSource.setUrl(config.url());
		dataSource.setUsername(config.username());
		dataSource.setPassword(config.password());
		dataSource.setInitialSize(Integer.parseInt(
			Objects.requireNonNull(environment.getProperty("spring.datasource.dbcp2.initial-size"))));
		dataSource.setMaxIdle(Integer.parseInt(
			Objects.requireNonNull(environment.getProperty("spring.datasource.dbcp2.max-idle"))));
		dataSource.setMinIdle(Integer.parseInt(
			Objects.requireNonNull(environment.getProperty("spring.datasource.dbcp2.min-idle"))));
		dataSource.setValidationQuery(environment.getProperty("spring.datasource.dbcp2.validation-query"));
		dataSource.setDefaultAutoCommit(
			Boolean.parseBoolean(environment.getProperty("spring.datasource.dbcp2.default-auto-commit")));
		return dataSource;
	}

}
