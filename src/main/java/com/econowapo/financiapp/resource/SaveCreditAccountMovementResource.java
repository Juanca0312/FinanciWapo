package com.econowapo.financiapp.resource;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class SaveCreditAccountMovementResource {
    @NotBlank
    @NotNull
    private int state;

    @NotBlank
    @NotNull
    private Date generated_date;

    @NotBlank
    @NotNull
    private double amount;
}
