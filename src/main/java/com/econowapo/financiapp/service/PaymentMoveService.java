package com.econowapo.financiapp.service;

import com.econowapo.financiapp.model.Customer;
import com.econowapo.financiapp.model.PaymentInfo;
import com.econowapo.financiapp.model.PaymentMove;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface PaymentMoveService {
    PaymentMove createPaymentMove(Long paymentId);
    PaymentMove updatePaymentMove(Long paymentId, Long paymentMoveId, PaymentMove paymentMove);
    List<PaymentInfo> getAllPaymentMoves();
    List<PaymentInfo> getAllPaymentMovesByCustomerId(Long customerId);
    PaymentMove getPaymentMoveByIdAndPaymentId(Long Id, Long paymentId);
}
