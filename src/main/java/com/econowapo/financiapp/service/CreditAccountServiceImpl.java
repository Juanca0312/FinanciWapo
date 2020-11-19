package com.econowapo.financiapp.service;

import com.econowapo.financiapp.exception.ResourceNotFoundException;
import com.econowapo.financiapp.model.CreditAccount;
import com.econowapo.financiapp.model.Currency;
import com.econowapo.financiapp.model.Customer;
import com.econowapo.financiapp.repository.CreditAccountRepository;
import com.econowapo.financiapp.repository.CurrencyRepository;
import com.econowapo.financiapp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.validation.constraints.Max;
import java.util.List;
import java.util.Optional;

@Service
public class CreditAccountServiceImpl implements CreditAccountService{

    @Autowired
    private CreditAccountRepository creditAccountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public CreditAccount createCreditAccount(Long customerId, CreditAccount creditAccount) {
        return customerRepository.findById(customerId).map(customer -> {
            creditAccount.setCustomer(customer);
            return creditAccountRepository.save(creditAccount);
        }).orElseThrow(() -> new ResourceNotFoundException("Customer", "Id", customerId));
    }

    @Override
    public CreditAccount updateCreditAccount(Long customerId, CreditAccount creditAccountRequest) {
        if(!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer", "Id", customerId);
        }

        CreditAccount creditAccount = creditAccountRepository.findByCustomerId(customerId);
        creditAccount.setState(creditAccountRequest.getState());
        creditAccount.setGenerated_date(creditAccountRequest.getGenerated_date());
        creditAccount.setInterest_rate(creditAccountRequest.getInterest_rate());
        creditAccount.setInterest_rate_value(creditAccountRequest.getInterest_rate_value());
        creditAccount.setBalance(creditAccountRequest.getBalance());
        creditAccount.setActual_balance(creditAccountRequest.getActual_balance());
        return creditAccountRepository.save(creditAccount);
    }


    @Override
    public CreditAccount getCreditAccountById(Long creditAccountId) {

        return creditAccountRepository.findById(creditAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("creditAccount", "Id", creditAccountId));
    }

    @Override
    public Page<CreditAccount> getAllCreditAccounts(Pageable pageable) {
        return creditAccountRepository.findAll(pageable);
    }

    @Override
    public CreditAccount getCreditAccountByCustomerId(Long customerId) {
        return creditAccountRepository.findByCustomerId(customerId);
    }

    @Override
    public ResponseEntity<?> maintenancePayment() {
        List<CreditAccount> creditAccounts =  creditAccountRepository.findAll();
        for (CreditAccount creditAccount : creditAccounts) {
            double newBalance = creditAccount.getActual_balance() - 0.1;
            creditAccount.setActual_balance(newBalance);
            creditAccountRepository.save(creditAccount);
        }

        return ResponseEntity.ok().build();
    }


}
