package com.github.dkirrane.sample;

import com.github.dkirrane.sample.dto.CustomerDto;
import com.github.dkirrane.sample.dto.DeleteResponseDto;
import com.github.dkirrane.sample.dto.Status;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;

import java.util.Map;

@SpringBootTest
@AutoConfigureHttpGraphQlTester
public class GraphqlCrudTest {

    @Autowired
    private HttpGraphQlTester client;

    @Test
    public void allCustomersTest() {
        var doc = """
                    query {
                        customers {
                            id
                            name
                            age
                            city
                            type: __typename
                        }
                    }
                """;
        this.client.document(doc)
                .execute()
                .path("customers").entityList(Object.class).hasSizeGreaterThan(2)
                .path("customers.[0].name").entity(String.class).isEqualTo("sam");
    }

    @Test
    public void customerByIdTest() {
        this.client.documentName("crud-operations")
                .variables(Map.of("id1", 1, "id2", 2))
                .operationName("GetCustomerById")
                .execute()
                .path("a.id").entity(Integer.class).isEqualTo(1)
                .path("b.id").entity(Integer.class).isEqualTo(2);
    }

    @Test
    public void createCustomerTest() {
        this.client.documentName("crud-operations")
                .variable("customer", CustomerDto.create(null, "dessie", 42, "Tuam"))
                .operationName("CreateCustomer")
                .execute()
                .path("response.id").hasValue()
                .path("response.name").entity(String.class).isEqualTo("dessie")
                .path("response.age").entity(Integer.class).isEqualTo(42)
                .path("response.city").entity(String.class).isEqualTo("Tuam");
    }

    @Test
    public void updateCustomerTest() {
        this.client.documentName("crud-operations")
                .variables(Map.of(
                        "id", 1,
                        "customer", CustomerDto.create(null, "dessie", 42, "Tuam")
                ))
                .operationName("UpdateCustomer")
                .execute()
                .path("response.id").entity(Integer.class).isEqualTo(1)
                .path("response.name").entity(String.class).isEqualTo("dessie")
                .path("response.age").entity(Integer.class).isEqualTo(42)
                .path("response.city").entity(String.class).isEqualTo("Tuam");
    }

    @Test
    public void deleteCustomerTest() {
        this.client.documentName("crud-operations")
                .variable("id", 2)
                .operationName("DeleteCustomer")
                .execute()
                .path("response").entity(DeleteResponseDto.class).satisfies(deleteResponseDto -> {
                            Assertions.assertThat(deleteResponseDto.getId()).isEqualTo(2);
                            Assertions.assertThat(deleteResponseDto.getStatus()).isEqualTo(Status.SUCCESS);
                        }
                );
    }
}
