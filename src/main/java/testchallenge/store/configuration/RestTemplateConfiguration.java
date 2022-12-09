package testchallenge.store.configuration;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.httpclient.LogbookHttpRequestInterceptor;
import org.zalando.logbook.httpclient.LogbookHttpResponseInterceptor;

import java.util.function.Supplier;

@Configuration
public class RestTemplateConfiguration {
    @Bean
    public RestTemplate getRestTemplateBuilder(RestTemplateBuilder restTemplateBuilder,
                                               Logbook logbook) {
        CloseableHttpClient client =
                HttpClientBuilder.create()
                        .addInterceptorFirst(new LogbookHttpRequestInterceptor(logbook))
                        .addInterceptorFirst(new LogbookHttpResponseInterceptor())
                        .build();
        Supplier<ClientHttpRequestFactory> requestFactorySupplier = () -> new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory(client));

        return restTemplateBuilder.requestFactory(requestFactorySupplier).build();
    }

}
