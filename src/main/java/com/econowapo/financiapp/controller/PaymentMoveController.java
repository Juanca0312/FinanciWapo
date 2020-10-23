package com.econowapo.financiapp.controller;

import com.econowapo.financiapp.model.Order;
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

    @GetMapping("/payments/{paymentId}/paymentMoves")
    public Page<PaymentMoveResource> getAllPaymentsMovesByMovementId(
            @PathVariable(name = "paymentId") Long paymentId,
            Pageable pageable) {
        Page<PaymentMove> paymentMovePage = paymentMoveService.getAllPaymentMovesByPaymentId(paymentId, pageable);
        List<PaymentMoveResource> resources = paymentMovePage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/paymentMoves")
    public List<PaymentMove> getAllOrders() {
        return paymentMoveService.getAllPaymentMoves();
    }

    @PostMapping("/payments/{paymentId}/paymentMoves")
    public PaymentMoveResource CreatePaymentMove(@PathVariable(name = "paymentId") Long paymentId) {
        return convertToResource(paymentMoveService.createPaymentMove(paymentId));
    }

    private PaymentMove convertToEntity(SavePaymentMoveResource resource) {
        return mapper.map(resource, PaymentMove.class);
    }

    private PaymentMoveResource convertToResource(PaymentMove entity) {
        return mapper.map(entity, PaymentMoveResource.class);
    }


}
