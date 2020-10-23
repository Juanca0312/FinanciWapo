package com.econowapo.financiapp.service;

import com.econowapo.financiapp.model.CreditAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CreditAccountService {
    CreditAccount createCreditAccount(Long customerId, CreditAccount creditAccount);
    CreditAccount updateCreditAccount(Long customerId, CreditAccount creditAccountRequest);
    CreditAccount getCreditAccountById(Long creditAccountId);
    Page<CreditAccount> getAllCreditAccounts(Pageable pageable);
    CreditAccount getCreditAccountByCustomerId(Long customerId);

}
