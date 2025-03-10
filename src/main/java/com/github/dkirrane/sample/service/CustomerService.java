package com.github.dkirrane.sample.service;

import com.github.dkirrane.sample.dto.CustomerDto;
import com.github.dkirrane.sample.dto.DeleteResponseDto;
import com.github.dkirrane.sample.dto.Status;
import com.github.dkirrane.sample.repository.CustomerRepository;
import com.github.dkirrane.sample.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public Flux<CustomerDto> allCustomers(){
        return repository.findAll()
                .map(EntityDtoUtil::toDto);
    }

    public Mono<CustomerDto> customerById(Integer id){
        return repository.findById(id)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<CustomerDto> createCustomer(CustomerDto customerDto) {
        // Use Mono.just to be lazy
        System.out.println("customerDto = " + customerDto);
        return Mono.just(customerDto)
                .map(EntityDtoUtil::toEntity)
                .flatMap(repository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<CustomerDto> updateCustomer(Integer id, CustomerDto customerDto) {
        // If the ID is not found we'll do nothing here.
        return repository.findById(id)
                .map(c -> EntityDtoUtil.toEntity(id, customerDto))
                .flatMap(c -> repository.save(c))
                .map(EntityDtoUtil::toDto);
    }

    public Mono<DeleteResponseDto> deleteCustomer(Integer id){
        return repository.deleteById(id)
                .thenReturn(DeleteResponseDto.create(id, Status.SUCCESS))
                .onErrorReturn(DeleteResponseDto.create(id, Status.FAILURE));
    }

}
