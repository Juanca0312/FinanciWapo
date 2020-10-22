package com.econowapo.financiapp.resource;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class SaveCreditAccountResource {
    @NotBlank
    @NotNull
    private int state;

    @NotBlank
    @NotNull
    private Date generated_date;

    //1: simple, 2: nominal, 3:efectiva
    @NotBlank
    @NotNull
    private int interest_rate;

    @NotBlank
    @NotNull
    private double interest_rate_value;

    //saldo fijo de la cuenta
    @NotBlank
    @NotNull
    private double balance;

    //este saldo cambia con los movimientos de cuenta
    @NotBlank
    @NotNull
    private double actual_balance;
}
