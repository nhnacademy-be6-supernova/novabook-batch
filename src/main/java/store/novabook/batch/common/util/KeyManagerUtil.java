package store.novabook.batch.common.util;

import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import store.novabook.batch.common.dto.ElasticSearchConfigDto;
import store.novabook.batch.common.exception.ErrorCode;
import store.novabook.batch.common.exception.KeyManagerException;

public class KeyManagerUtil {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	private KeyManagerUtil() {
	}

	public static DataSource getDataSource(Environment environment, String keyId) {

		String appkey = environment.getProperty("nhn.cloud.keyManager.appkey");
		String userId = environment.getProperty("nhn.cloud.keyManager.userAccessKeyId");
		String secretKey = environment.getProperty("nhn.cloud.keyManager.secretAccessKey");
		RestTemplate restTemplate = new RestTemplate();
		String baseUrl =
			"https://api-keymanager.nhncloudservice.com/keymanager/v1.2/appkey/" + appkey + "/secrets/" + keyId;
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-TC-AUTHENTICATION-ID", userId);
		headers.set("X-TC-AUTHENTICATION-SECRET", secretKey);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<Map<String, Object>> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity,
			new ParameterizedTypeReference<>() {
			});

		var body = getStringObjectMap(response);

		String secretJson = (String)body.get("secret");
		if (secretJson.isEmpty()) {
			log.error("\"secret\" key is missing in responsxcle body");
			log.error("{}", body);
			throw new KeyManagerException(MISSING_BODY_KEY);
		}

		DatabaseConfigDto config = null;
		try {
			config = objectMapper.readValue(secretJson, DatabaseConfigDto.class);
		} catch (JsonProcessingException e) {
			log.error("DatabaseConfig{}", FAILED_CONVERSION.getMessage());
			throw new KeyManagerException(FAILED_CONVERSION);
		}

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
		dataSource.setUrl(config.url());
		dataSource.setUsername(config.username());
		dataSource.setPassword(config.password());
		dataSource.setInitialSize(
			Integer.parseInt(Objects.requireNonNull(environment.getProperty("spring.datasource.dbcp2.initial-size"))));
		dataSource.setMaxIdle(
			Integer.parseInt(Objects.requireNonNull(environment.getProperty("spring.datasource.dbcp2.max-idle"))));
		dataSource.setMinIdle(
			Integer.parseInt(Objects.requireNonNull(environment.getProperty("spring.datasource.dbcp2.min-idle"))));
		dataSource.setValidationQuery(environment.getProperty("spring.datasource.dbcp2.validation-query"));
		dataSource.setDefaultAutoCommit(
			Boolean.parseBoolean(environment.getProperty("spring.datasource.dbcp2.default-auto-commit")));
		return dataSource;
	}

	private static @NotNull Map<String, Object> getStringObjectMap(ResponseEntity<Map<String, Object>> response) {
		if (response.getBody() == null) {
			throw new KeyManagerException(RESPONSE_BODY_IS_NULL);
		}
		Object bodyObj = response.getBody().get("body");

		Map<String, Object> body;
		try {
			body = TypeUtil.castMap(bodyObj, String.class, Object.class);
		} catch (ClassCastException e) {
			throw new KeyManagerException(MISSING_BODY_KEY);
		}

		String result = (String)body.get("secret");
		if (result == null || result.isEmpty()) {
			log.error("\"secret\" key is missing or empty in response body");
			log.error("{}", body);
			throw new KeyManagerException(MISSING_SECRET_KEY);
		}

		return body;
	}

}
