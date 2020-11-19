package com.econowapo.financiapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "credit_accounts")
@Data
public class CreditAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @NotBlank
    @NotNull
    private String currency;

    //Many to One Currency
    /*@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "currency_id", nullable = false)
    @JsonIgnore
    private Currency currency;*/

    //One to One Customer
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    //Many to Many Order-CreditAccount
    @OneToMany(mappedBy = "creditAccount")
    List<CreditAccountMovement> creditAccountMovements;





}
