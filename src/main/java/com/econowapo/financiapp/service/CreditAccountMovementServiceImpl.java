package com.econowapo.financiapp.service;

import com.econowapo.financiapp.model.CreditAccountMovement;
import com.econowapo.financiapp.repository.CreditAccountMovementRepository;
import com.econowapo.financiapp.repository.CreditAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CreditAccountMovementServiceImpl implements CreditAccountMovementService {

    @Autowired
    private CreditAccountMovementRepository creditAccountMovementRepository;

    @Autowired
    private CreditAccountRepository creditAccountRepository;

    @Override
    public Page<CreditAccountMovement> getAllCreditAccountMovements(Pageable pageable) {
        return creditAccountMovementRepository.findAll(pageable);
    }

    @Override
    public Page<CreditAccountMovement> getAllCreditAccountMovementsByCreditAccountId(Long creditAccountId, Pageable pageable) {
        return creditAccountMovementRepository.findByCreditAccountId(creditAccountId, pageable);
    }
}
