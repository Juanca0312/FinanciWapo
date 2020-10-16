package com.econowapo.financiapp.service;

import com.econowapo.financiapp.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Long userId,Customer costumer);
    Customer updateCustomer(Long userId,Long customerId, Customer customerDetail);
    ResponseEntity<?> deleteCustomer(Long userId, Long customerId);
    List<Customer> getAllCustomers();
    Page<Customer> getAllCustomersByUserId(Long userId, Pageable pageable);
    Customer getCustomerByIdAndUserId(Long userId, Long customerId);

}
