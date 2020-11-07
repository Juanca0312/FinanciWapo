package com.econowapo.financiapp.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class OrderInfo {
    private Long orderId;
    private String customerName;
    private int state;
    private Date accepted_date;
    private Date generated_date;
    private int payment_method;
    private double total_amount;
}
