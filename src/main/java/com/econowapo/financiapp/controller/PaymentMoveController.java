package com.econowapo.financiapp.controller;

import com.econowapo.financiapp.model.Order;
import com.econowapo.financiapp.model.PaymentInfo;
import com.econowapo.financiapp.model.PaymentMove;
import com.econowapo.financiapp.resource.*;
import com.econowapo.financiapp.service.PaymentMoveService;
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
public class PaymentMoveController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PaymentMoveService paymentMoveService;

    @GetMapping("/customers/{customerId}/paymentMoves")
    public List<PaymentInfo> getAllPaymentsMovesByCustomerId(
            @PathVariable(name = "customerId") Long customerId) {
        return paymentMoveService.getAllPaymentMovesByCustomerId(customerId);
    }

    @GetMapping("/paymentMoves")
    public List<PaymentInfo> getAllPaymentMoves() {
        return paymentMoveService.getAllPaymentMoves();
    }

    @PostMapping("/creditAccount/{creditAccountId}/paymentMoves")
    public PaymentMoveResource CreatePaymentMove(@PathVariable(name = "creditAccountId") Long creditAccountId) {
        return convertToResource(paymentMoveService.createPaymentMove(creditAccountId));
    }

    @GetMapping("/creditAccount/{creditAccountId}/paymentMoves")
    public PaymentMoveResource showPaymentMove(@PathVariable(name = "creditAccountId") Long creditAccountId) {
        return convertToResource(paymentMoveService.showPaymentMove(creditAccountId));
    }

    private PaymentMove convertToEntity(SavePaymentMoveResource resource) {
        return mapper.map(resource, PaymentMove.class);
    }

    private PaymentMoveResource convertToResource(PaymentMove entity) {
        return mapper.map(entity, PaymentMoveResource.class);
    }


}
