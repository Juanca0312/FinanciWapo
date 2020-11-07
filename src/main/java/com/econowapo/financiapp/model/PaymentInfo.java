package com.econowapo.financiapp.model;

import lombok.Data;

@Data
public class PaymentInfo {
    private Long paymentId;
    private double amount;
    private String generated_date;
    private int state;
    private String customerName;
}
