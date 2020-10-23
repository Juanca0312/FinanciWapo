package com.econowapo.financiapp.service;

import com.econowapo.financiapp.exception.ResourceNotFoundException;
import com.econowapo.financiapp.model.CreditAccount;
import com.econowapo.financiapp.model.Payment;
import com.econowapo.financiapp.repository.CreditAccountRepository;
import com.econowapo.financiapp.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CreditAccountRepository creditAccountRepository;

    @Override
    public Payment createPayment(Long creditAccountId, Payment payment) {
        return creditAccountRepository.findById(creditAccountId).map(creditAccount -> {
            payment.setCreditAccount(creditAccount);
            return paymentRepository.save(payment);
        }).orElseThrow(() -> new ResourceNotFoundException(
                "CreditAccount", "Id", creditAccountId
        ));
    }

    @Override
    public Payment getPaymentByCreditAccountId(Long creditAccountId) {
        return paymentRepository.findByCreditAccountId(creditAccountId);
    }
}
