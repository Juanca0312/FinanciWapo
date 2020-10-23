package com.econowapo.financiapp.controller;

import com.econowapo.financiapp.model.Authority;
import com.econowapo.financiapp.model.Customer;
import com.econowapo.financiapp.resource.AuthorityResource;
import com.econowapo.financiapp.resource.CustomerResource;
import com.econowapo.financiapp.resource.SaveCustomerResource;
import com.econowapo.financiapp.service.AuthorityService;
import com.econowapo.financiapp.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AuthorityController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AuthorityService authorityService;

    @GetMapping("/users/{userId}/authority")
    public AuthorityResource getAuthorityByUserId(
            @PathVariable(name = "userId") Long userId) {
        return convertToResource(authorityService.getAuthorityByUserId(userId));

    }

    private Authority convertToEntity(SaveCustomerResource resource) {
        return mapper.map(resource, Authority.class);
    }

    private AuthorityResource convertToResource(Authority entity) {
        return mapper.map(entity, AuthorityResource.class);
    }

}
