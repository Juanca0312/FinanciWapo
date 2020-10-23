package com.econowapo.financiapp.controller;

import com.econowapo.financiapp.model.Authority;
import com.econowapo.financiapp.model.Customer;
import com.econowapo.financiapp.resource.AuthorityResource;
import com.econowapo.financiapp.resource.CustomerResource;
import com.econowapo.financiapp.resource.SaveAuthorityResource;
import com.econowapo.financiapp.resource.SaveCustomerResource;
import com.econowapo.financiapp.service.AuthorityService;
import com.econowapo.financiapp.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


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

    @PostMapping("/users/{userId}/authority")
    public AuthorityResource createAuthority(@PathVariable(name = "userId") Long userId,
                                           @Valid @RequestBody SaveAuthorityResource resource) {
        return convertToResource(authorityService.createAuthority(userId, convertToEntity(resource)));
    }

    private Authority convertToEntity(SaveAuthorityResource resource) {
        return mapper.map(resource, Authority.class);
    }

    private AuthorityResource convertToResource(Authority entity) {
        return mapper.map(entity, AuthorityResource.class);
    }

}
