package com.github.dkirrane.sample.controller;

import com.github.dkirrane.sample.dto.CustomerEvent;
import com.github.dkirrane.sample.service.CustomerEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
public class CustomerEventController {

    @Autowired
    private CustomerEventService customerEventService;

    @SubscriptionMapping
    public Flux<CustomerEvent> customerEvents() {
        return customerEventService.subscribe();
    }
}
