package com.econowapo.financiapp.resource;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SaveCustomerResource {
    @NotBlank
    @NotNull
    @Size(max = 50)
    private String address;

    @NotNull
    @NotBlank
    private int state;

    @Size(max = 50)
    @NotBlank
    @NotNull
    private String name;
}
