package com.github.dkirrane.sample.exceptions;

import org.springframework.graphql.execution.ErrorType;
import reactor.core.publisher.Mono;

import java.util.Map;

public class ApplicationErrors {

    public static <T> Mono<T> customerNotFound(Integer id){
        return Mono.error(new ApplicationException(
                ErrorType.NOT_FOUND, "Customer Not Found", Map.of("customerId", id)
        ));
    }

    public static <T> Mono<T> mustBe18(Integer age){
        return Mono.error(new ApplicationException(
                ErrorType.BAD_REQUEST, "Customer must be 18", Map.of("age", age)
        ));
    }
}
