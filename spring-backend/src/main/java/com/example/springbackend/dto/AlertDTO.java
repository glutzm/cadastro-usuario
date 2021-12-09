package com.example.springbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AlertDTO {

    private String message;
    private String cssClass;
}
