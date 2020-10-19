package com.econowapo.financiapp.resource;

import com.econowapo.financiapp.model.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class OrderResource {
    private Long id;
    private int state;
    private Date accepted_date;
    private Date generated_date;
    private int payment_method;
}
