package com.econowapo.financiapp.resource;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SaveArticleResource {
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
}
