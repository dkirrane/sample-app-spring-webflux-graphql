package com.github.dkirrane.sample.service;

import com.github.dkirrane.sample.dto.CustomerEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class CustomerEventService {

    // A sink that will broadcast multiple signals to one or more Subscribers
    private final Sinks.Many<CustomerEvent> sink = Sinks.many().multicast().onBackpressureBuffer();
    // Expose the Flux view of the sink to the downstream consumers.
    // Do not cache anything as the value comes emit it i.e. Hot Publisher
    private final Flux<CustomerEvent> flux = sink.asFlux().cache(0);

    public void emitEvent(CustomerEvent event) {
        this.sink.tryEmitNext(event);
    }

    public Flux<CustomerEvent> subscribe() {
        return this.flux;
    }

}
