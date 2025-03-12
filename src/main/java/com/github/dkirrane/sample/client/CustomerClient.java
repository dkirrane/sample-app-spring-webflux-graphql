package com.github.dkirrane.sample.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.client.ClientGraphQlResponse;
import org.springframework.graphql.client.HttpGraphQlClient;
import reactor.core.publisher.Mono;

@Slf4j
//@Component
public class CustomerClient {

    private final HttpGraphQlClient client;

    public CustomerClient(@Value("${customer.service.url}") String baseUrl) {
        log.info("baseUrl = {}", baseUrl);
        this.client = HttpGraphQlClient.builder()
                .webClient(b -> b.baseUrl(baseUrl))
                .build();
    }

    public Mono<ClientGraphQlResponse> rawQuery(String query) {
         return this.client.document(query)
                .execute();
    }
}
