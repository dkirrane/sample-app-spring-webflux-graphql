package com.github.dkirrane.sample.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "create")
public class DeleteResponseDto {

    private Integer id;
    private Status status;
}
