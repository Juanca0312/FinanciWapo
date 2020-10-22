package com.econowapo.financiapp.controller;

import com.econowapo.financiapp.model.CreditAccount;
import com.econowapo.financiapp.resource.CreditAccountResource;
import com.econowapo.financiapp.resource.SaveCreditAccountResource;
import com.econowapo.financiapp.service.CreditAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CreditAccountController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CreditAccountService creditAccountService;


    //Get by CustomerId
    @GetMapping("customers/{customerId}/creditAccounts")
    public CreditAccountResource getCreditAccountByCustomerId(@PathVariable(name = "customerId") Long customerId) {
        CreditAccount creditAccount = creditAccountService.getCreditAccountByCustomerId(customerId);
        return convertToResource(creditAccount);

    }

    //Create
    @PostMapping("/customers/{customerId}/creditAccounts")
    public CreditAccountResource createCreditAccount(@PathVariable(name = "customerId") Long customerId, @Valid @RequestBody SaveCreditAccountResource resource) {
        CreditAccount creditAccount = convertToEntity(resource);
        return convertToResource(creditAccountService.createCreditAccount(customerId, creditAccount));
    }

    @PutMapping("customers/{customerId}/creditAccounts")
    public CreditAccountResource updateCreditAccount(@PathVariable(name = "customerId") Long customerId,
                                         @Valid @RequestBody SaveCreditAccountResource resource) {
        return convertToResource(creditAccountService.updateCreditAccount(customerId, convertToEntity(resource)));
    }



    private CreditAccount convertToEntity(SaveCreditAccountResource resource) {
        return mapper.map(resource, CreditAccount.class);
    }

    private CreditAccountResource convertToResource(CreditAccount entity) {
        return mapper.map(entity, CreditAccountResource.class);
    }

}
