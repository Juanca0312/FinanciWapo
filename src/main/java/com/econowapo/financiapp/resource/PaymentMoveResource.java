package com.econowapo.financiapp.resource;

import lombok.Data;

import java.util.Date;

@Data
public class PaymentMoveResource {
    private Long id;
    private int state;
    private String generated_date;
    private double amount;
}
