package com.econowapo.financiapp.controller;

import com.econowapo.financiapp.model.Payment;
import com.econowapo.financiapp.resource.PaymentResource;
import com.econowapo.financiapp.resource.SavePaymentResource;
import com.econowapo.financiapp.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PaymentController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/creditAccounts/{creditAccountId}/payment")
    public PaymentResource getPaymentByCreditAccountId(
            @PathVariable(name = "creditAccountId") Long creditAccountId) {
        return convertToResource(paymentService.getPaymentByCreditAccountId(creditAccountId));
    }

    @PostMapping("/creditAccounts/{creditAccountId}/payment")
    public PaymentResource createPayment(@PathVariable(name = "creditAccountId") Long creditAccountId,
                                             @Valid @RequestBody SavePaymentResource resource) {
        return convertToResource(paymentService.createPayment(creditAccountId, convertToEntity(resource)));
    }


    private Payment convertToEntity(SavePaymentResource resource) {
        return mapper.map(resource, Payment.class);
    }

    private PaymentResource convertToResource(Payment entity) {
        return mapper.map(entity, PaymentResource.class);
    }
}
