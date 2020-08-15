package org.hmhb.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 * Spring {@link Configuration} for setting up a {@link RestTemplate} for
 * making http calls.
 */
@Configuration
public class RestTemplateConfig {

    private static final int TIMEOUT = 10000;

    @Bean
    public ClientHttpRequestFactory getClientHttpRequestFactory() {
        RequestConfig config = RequestConfig
                .custom()
                .setConnectTimeout(TIMEOUT)
                .setConnectionRequestTimeout(TIMEOUT)
                .setSocketTimeout(TIMEOUT)
                .build();
        CloseableHttpClient client = HttpClientBuilder
                .create()
                .setDefaultRequestConfig(config)
                .build();
        return new HttpComponentsClientHttpRequestFactory(client);
    }

    @Bean
    public RestOperations getRestTemplate(ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }

}
