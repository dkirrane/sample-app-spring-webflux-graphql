package com.github.dkirrane.sample;

import com.github.dkirrane.sample.dto.CustomerDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.graphql.test.tester.HttpGraphQlTester;

@SpringBootTest
@AutoConfigureHttpGraphQlTester
public class GraphqlErrorTest {

    @Autowired
    private HttpGraphQlTester client;

    @Test
    public void createCustomerTest() {
        // Check it fails with error cause customer must be age 18 and over
        this.client.documentName("crud-test")
                .variable("customer", CustomerDto.create(null, "dessie", 12, "Tuam"))
                .operationName("CreateCustomer")
                .execute()
                .errors().satisfy(responseErrors -> {
                    Assertions.assertThat(responseErrors).hasSize(1);
                    Assertions.assertThat(responseErrors.get(0).getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
                    Assertions.assertThat(responseErrors.get(0).getMessage()).contains("Customer must be 18");
                })
                .path("response").valueIsNull();
    }
}
