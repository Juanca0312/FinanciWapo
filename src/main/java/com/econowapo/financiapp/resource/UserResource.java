package com.econowapo.financiapp.resource;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserResource {
    private Long id;
    private int enabled;
    private String password;
    private String username;
}
