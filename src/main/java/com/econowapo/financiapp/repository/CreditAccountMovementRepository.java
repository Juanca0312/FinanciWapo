package com.econowapo.financiapp.repository;

import com.econowapo.financiapp.model.CreditAccountMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditAccountMovementRepository extends JpaRepository<CreditAccountMovement, Long> {
    Page<CreditAccountMovement> findByCreditAccountId(Long creditAccountId, Pageable pageable);
    List<CreditAccountMovement> findByCreditAccountId(Long creditAccountId);


}
