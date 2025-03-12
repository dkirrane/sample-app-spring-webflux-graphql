package com.github.dkirrane.sample.controller;

import com.github.dkirrane.sample.dto.CustomerDto;
import com.github.dkirrane.sample.dto.DeleteResponseDto;
import com.github.dkirrane.sample.exceptions.ApplicationErrors;
import com.github.dkirrane.sample.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @QueryMapping
    public Flux<CustomerDto> customers() {
        return customerService.allCustomers();
    }

    @QueryMapping
    public Mono<CustomerDto> customerById(@Argument Integer id) {
        return customerService.customerById(id)
                .switchIfEmpty(ApplicationErrors.customerNotFound(id));
    }

    @MutationMapping
    public Mono<CustomerDto> createCustomer(@Argument CustomerDto customer) {
        Predicate<CustomerDto> isValid = c -> (c.getAge() >= 18);

        return Mono.just(customer)
                .filter(isValid)
                .flatMap(c -> customerService.createCustomer(c))
                .switchIfEmpty(ApplicationErrors.mustBe18(customer.getAge()));
    }

    @MutationMapping
    public Mono<CustomerDto> updateCustomer(@Argument Integer id, @Argument CustomerDto customer) {
        return customerService.updateCustomer(id, customer);
    }

    @MutationMapping
    public Mono<DeleteResponseDto> deleteCustomer(@Argument Integer id) {
        return customerService.deleteCustomer(id);
    }
}

