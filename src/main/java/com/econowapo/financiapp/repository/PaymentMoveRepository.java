package com.econowapo.financiapp.repository;

import com.econowapo.financiapp.model.Customer;
import com.econowapo.financiapp.model.PaymentMove;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentMoveRepository extends JpaRepository<PaymentMove, Long> {
    Page<PaymentMove> findByPaymentId(Long paymentId, Pageable pageable);
    Optional<PaymentMove> findByIdAndPaymentId(Long id, Long paymentId);
}
