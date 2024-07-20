package store.novabook.batch.common.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableElasticsearchRepositories(basePackages = "store.novabook.batch.search")
@RequiredArgsConstructor
public class ElasticSearchClientConfig {
	private final Environment environment;

	@Bean
	public ElasticsearchClient getRestClient() {
		RestClient restClient = RestClient.builder(HttpHost.create("125.6.36.57:9200")).build();

		// Create the transport and the API client
		ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
		return  new ElasticsearchClient(transport);
	}

}