package com.econowapo.financiapp.controller;

import com.econowapo.financiapp.model.Token;
import com.econowapo.financiapp.model.User;
import com.econowapo.financiapp.resource.SaveTokenResource;
import com.econowapo.financiapp.resource.SaveUserResource;
import com.econowapo.financiapp.resource.TokenResource;
import com.econowapo.financiapp.resource.UserResource;
import com.econowapo.financiapp.service.TokenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class TokenController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/tokens")
    public Token createToken()  {
        return tokenService.createToken();
    }

    @GetMapping("/tokens")
    public Token getLastToken()  {
        return tokenService.getLastToken();
    }

    private Token convertToEntity(SaveTokenResource resource) { return mapper.map(resource, Token.class); }

    private TokenResource convertToResource(Token entity) { return mapper.map(entity, TokenResource.class); }


}
