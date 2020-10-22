package com.econowapo.financiapp.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "concepts")
@Data
public class Concept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String description;

    @NotNull
    @NotBlank
    private int state;

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private double value;
}
