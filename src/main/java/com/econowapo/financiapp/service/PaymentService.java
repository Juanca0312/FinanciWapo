package com.econowapo.financiapp.service;

import com.econowapo.financiapp.model.Authority;
import com.econowapo.financiapp.model.Payment;

public interface PaymentService {
    Payment createPayment(Long creditAccountId, Payment payment);
    Payment getPaymentByCreditAccountId(Long creditAccountId);
}
