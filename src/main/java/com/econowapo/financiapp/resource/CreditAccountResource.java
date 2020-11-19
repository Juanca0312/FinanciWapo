package com.econowapo.financiapp.resource;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreditAccountResource {
    private Long id;
    private int state;
    private Date generated_date;
    private int interest_rate;
    private double interest_rate_value;
    private double balance;
    private double actual_balance;
    private String currency;
}
