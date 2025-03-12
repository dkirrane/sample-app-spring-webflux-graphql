package com.github.dkirrane.sample.client;

import com.github.dkirrane.sample.dto.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Slf4j
//@Component
public class ClientCommandLineRunner implements CommandLineRunner {

    @Autowired
    private CustomerClient client;

    @Override
    public void run(String... args) throws Exception {
        rawQueryDemo()
                .subscribe();
    }

    private Mono<Void> rawQueryDemo() {
        String query = """
                {
                    customers{
                        id
                        name
                        age
                        city
                    }
                }
                """;

        Mono<List<CustomerDto>> mono = this.client.rawQuery(query)
                .map(cr -> cr.field("customers").toEntityList(CustomerDto.class));

        /*
         * Wait for 1 second, then run Query and then print response to console
         */
        log.info("Executing GraphQL query: \n{}", query);
        return Mono.delay(Duration.ofSeconds(1))
                .doFirst(() -> System.out.println("Raw Query")) // executes the provided Runnable (in this case, printing "Raw Query") as soon as the Mono is subscribed to, before any other signals are emitted.
                .then(mono) // After the delay is finished, ignore the delay value, and continue with the execution of the mono provided in the then method
//                .doOnNext(System.out::println)// print when the Mono method emits an element
                .doOnNext(customerDtos -> {
                    for (CustomerDto customerDto : customerDtos) {
                        log.info("{}", customerDto);
                    }  
                })
                .then(); // Creates a Mono<Void> - used when you're interested in the completion of the Mono but not in its emitted value. Here we'll return Mono just to Subscribe in the CommandLineRunner.run()
    }
}
