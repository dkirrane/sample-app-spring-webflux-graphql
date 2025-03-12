package com.github.dkirrane.sample.controller;

import com.github.dkirrane.sample.exceptions.ApplicationException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolver;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Component
public class ExceptionResolver implements DataFetcherExceptionResolver {

    @Override
    public Mono<List<GraphQLError>> resolveException(Throwable t, DataFetchingEnvironment env) {
        var applicationException = toApplicationException(t);
        return Mono.just(
                List.of(
                        GraphqlErrorBuilder.newError(env)
                                .message(applicationException.getMessage())
                                .errorType(applicationException.getErrorType())
                                .extensions(applicationException.getExtensions())
                                .build()
                )
        );
    }

    /**
     * Convert every Throwable to an ApplicationException.
     * Anything we don't handle is treated as an INTERNAL_ERROR
     */
    private ApplicationException toApplicationException(Throwable t){
        return ApplicationException.class.equals(t.getClass())
                ? (ApplicationException) t : new ApplicationException(ErrorType.INTERNAL_ERROR, t.getMessage(), Collections.emptyMap());
    }
}
