package com.github.dkirrane.sample;

import com.github.dkirrane.sample.dto.Action;
import com.github.dkirrane.sample.dto.CustomerDto;
import com.github.dkirrane.sample.dto.CustomerEvent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.graphql.test.tester.WebSocketGraphQlTester;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureHttpGraphQlTester
public class GraphqlSubscriptionTest {

    public static final String WS_PATH = "ws://localhost:8080/graphql";

    @Autowired
    private HttpGraphQlTester client;

    @Test
    public void subscriptionTest() {
        var websocketClient = WebSocketGraphQlTester
                .builder(WS_PATH, new ReactorNettyWebSocketClient())
                .build();

        // Check it fails with error cause customer must be age 18 and over
        this.client.documentName("crud-test")
                .variable("customer", CustomerDto.create(null, "dessie", 42, "Tuam"))
                .operationName("CreateCustomer")
                .executeAndVerify(); // just execute API call and verify it doesn't fail

        websocketClient.documentName("subscription-test")
                .executeSubscription()
                .toFlux("customerEvents", CustomerEvent.class)
                .take(1)
                .as(StepVerifier::create)
                .consumeNextWith(response -> {
                    Assertions.assertThat(response).isNotNull();
                    Assertions.assertThat(response.getAction()).isEqualTo(Action.CREATED);
                })
                .verifyComplete();
    }

}
