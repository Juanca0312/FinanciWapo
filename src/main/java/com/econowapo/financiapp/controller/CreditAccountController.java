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
import org.springframework.http.ResponseEntity;
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

    //Get All
    @GetMapping("/creditAccounts")
    public Page<CreditAccountResource> getAllCreditAccount(Pageable pageable) {
        Page<CreditAccount> creditAccountPage = creditAccountService.getAllCreditAccounts(pageable);
        List<CreditAccountResource> resources = creditAccountPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    //Get By Id
    //Get profiles by id
    @GetMapping("/creditAccounts/{id}")
    public CreditAccountResource getCreditAccountById(@PathVariable(name = "id") Long creditAccountId) {
        return convertToResource(creditAccountService.getCreditAccountById(creditAccountId));
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

    @PutMapping("/maintenancePayment")
    public ResponseEntity<?> maintenancePayment(){
        return creditAccountService.maintenancePayment();
    }


    private CreditAccount convertToEntity(SaveCreditAccountResource resource) {
        return mapper.map(resource, CreditAccount.class);
    }

    private CreditAccountResource convertToResource(CreditAccount entity) {
        return mapper.map(entity, CreditAccountResource.class);
    }

}
