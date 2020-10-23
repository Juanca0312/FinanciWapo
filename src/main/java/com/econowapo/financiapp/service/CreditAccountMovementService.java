package com.econowapo.financiapp.service;

import com.econowapo.financiapp.model.Article;
import com.econowapo.financiapp.model.CreditAccountMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CreditAccountMovementService {
    Page<CreditAccountMovement> getAllCreditAccountMovements(Pageable pageable);
    Page<CreditAccountMovement> getAllCreditAccountMovementsByCreditAccountId(Long creditAccountId, Pageable pageable);
}
