package com.econowapo.financiapp.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "articles")
@Data
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 80)
    private String description;

    @NotNull
    @NotBlank
    private int state;

    @NotNull
    @NotBlank
    @Size(max = 50)
    private String name;

    @NotNull
    @NotBlank
    private double price;

    @NotNull
    @NotBlank
    @Size(max = 50)
    private String unit;

    //Many to Many Order
    @OneToMany(mappedBy = "article")
    List<Order_Detail> order_details;

}
