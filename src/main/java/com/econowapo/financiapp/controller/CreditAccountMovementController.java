package com.econowapo.financiapp.controller;

import com.econowapo.financiapp.model.CreditAccountMovement;
import com.econowapo.financiapp.model.Customer;
import com.econowapo.financiapp.resource.CreditAccountMovementResource;
import com.econowapo.financiapp.resource.CustomerResource;
import com.econowapo.financiapp.resource.SaveCreditAccountMovementResource;
import com.econowapo.financiapp.resource.SaveCustomerResource;
import com.econowapo.financiapp.service.CreditAccountMovementService;
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
public class CreditAccountMovementController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CreditAccountMovementService creditAccountMovementService;

    //getAll
    @GetMapping("/creditAccountMovements")
    public Page<CreditAccountMovementResource> getAllCreditAccountMovements(Pageable pageable){
        Page<CreditAccountMovement> creditAccountMovementPage = creditAccountMovementService.getAllCreditAccountMovements(pageable);
        List<CreditAccountMovementResource> resources = creditAccountMovementPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    //getAll By CreditAccount Id
    @GetMapping("/creditAccount/{creditAccountId}/creditAccountMovements")
    public Page<CreditAccountMovementResource> getAllCreditAccountMovementsByCreditAccountId(@PathVariable(name = "creditAccountId") Long creditAccountId, Pageable pageable){
        Page<CreditAccountMovement> creditAccountMovementPage = creditAccountMovementService.getAllCreditAccountMovementsByCreditAccountId(creditAccountId, pageable);
        List<CreditAccountMovementResource> resources = creditAccountMovementPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    private CreditAccountMovement convertToEntity(SaveCreditAccountMovementResource resource) {
        return mapper.map(resource, CreditAccountMovement.class);
    }

    private CreditAccountMovementResource convertToResource(CreditAccountMovement entity) {
        return mapper.map(entity, CreditAccountMovementResource.class);
    }

}
