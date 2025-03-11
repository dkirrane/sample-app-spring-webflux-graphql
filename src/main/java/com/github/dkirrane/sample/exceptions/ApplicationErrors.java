package com.github.dkirrane.sample.exceptions;

import org.springframework.graphql.execution.ErrorType;
import reactor.core.publisher.Mono;

import java.util.Map;

public class ApplicationErrors {

    public static <T> Mono<T> noSuchUser(Integer id){
        return Mono.error(new ApplicationException(
                ErrorType.BAD_REQUEST, "No such user", Map.of("customerId", id)
        ));
    }
}
