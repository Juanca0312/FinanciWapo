package com.econowapo.financiapp.resource;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SaveAuthorityResource {
    @NotNull
    @NotBlank
    private String authority;
}
