package com.econowapo.financiapp.model;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerInfo {
    private Long customerId;
    private String name;
    private String username;
    private String password;
    private String address;
    private Date date;
    private double rate;
    private int rate_type;

}
