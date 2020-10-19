package com.econowapo.financiapp.resource;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class SaveOrderResource {
    @NotNull
    @NotBlank
    private int state;

    @NotBlank
    @NotNull
    private Date accepted_date;

    @NotBlank
    @NotNull
    private Date generated_date;

    @NotBlank
    @NotNull
    private int payment_method;
}
