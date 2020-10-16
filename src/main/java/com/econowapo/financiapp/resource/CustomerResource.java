package com.econowapo.financiapp.resource;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CustomerResource {
    private Long id;
    private String address;
    private int state;
    private String name;
}
