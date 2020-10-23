package com.econowapo.financiapp.resource;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class SavePaymentMoveResource {
    @NotNull
    @NotBlank
    private int state;

    @NotNull
    @NotBlank
    private String generated_date;

    @NotNull
    @NotBlank
    private double amount;
}
