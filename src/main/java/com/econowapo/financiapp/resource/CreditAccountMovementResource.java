package com.econowapo.financiapp.resource;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreditAccountMovementResource {
    private Long id;
    private int state;
    private Date generated_date;
    private double amount;
}
