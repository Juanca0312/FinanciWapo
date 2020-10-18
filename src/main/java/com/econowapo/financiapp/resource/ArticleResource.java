package com.econowapo.financiapp.resource;

import lombok.Data;


@Data
public class ArticleResource {
    private Long id;
    private String description;
    private int state;
    private String name;
    private double price;
    private String unit;
}
