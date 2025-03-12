package com.github.dkirrane.sample.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@JsonInclude(JsonInclude.Include.NON_NULL) // Allow id the be null as it is created by the DB
public class CustomerDto {

    private Integer id;
    private String name;
    private Integer age;
    private String city;
}
