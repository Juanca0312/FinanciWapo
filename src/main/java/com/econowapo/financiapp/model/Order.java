package com.econowapo.financiapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    //One to many Customer
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    private Customer customer;

    //Many to Many Article
    @OneToMany(mappedBy = "order")
    List<Order_Detail> order_details;

    //Many To Many Order-CreditAccount
    @OneToMany(mappedBy = "order")
    List<CreditAccountMovement> creditAccountMovements;

}
