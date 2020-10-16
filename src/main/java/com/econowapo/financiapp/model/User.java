package com.econowapo.financiapp.model;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private int enabled;

    @NotNull
    @NotBlank
    @Size(max = 60)
    private String password;

    @NotNull
    @NotBlank
    @Size(max = 9)
    private String username;



}
