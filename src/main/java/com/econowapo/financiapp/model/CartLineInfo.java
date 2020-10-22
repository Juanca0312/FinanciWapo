package com.econowapo.financiapp.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class CartLineInfo {
    //Clase creada para recibir los objetos del carrito
    @Id
    private String id;
    private String name;
    private String price;
    private String quantity;
    private String image;
}
