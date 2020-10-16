package com.econowapo.financiapp.service;

import com.econowapo.financiapp.exception.ResourceNotFoundException;
import com.econowapo.financiapp.model.Customer;
import com.econowapo.financiapp.repository.CustomerRepository;
import com.econowapo.financiapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Customer createCustomer(Long userId, Customer customer) {
        return userRepository.findById(userId).map(user -> {
            customer.setUser(user);
            return customerRepository.save(customer);
        }).orElseThrow(() -> new ResourceNotFoundException(
                "User", "Id", userId
        ));
    }

    @Override
    public Customer updateCustomer(Long userId, Long customerId, Customer customerDetail) {
        if(!userRepository.existsById(userId))
            throw new ResourceNotFoundException("User", "Id", userId);

        return customerRepository.findById(customerId).map(customer -> {
            customer.setName(customerDetail.getName());
            customer.setAddress(customerDetail.getAddress());
            customer.setState(customerDetail.getState());
            return customerRepository.save(customer);
        }).orElseThrow(() -> new ResourceNotFoundException("Customer", "Id", customerId));

    }

    @Override
    public ResponseEntity<?> deleteCustomer(Long userId, Long customerId) {
        return customerRepository.findByIdAndUserId(customerId, userId).map(customer ->{
            customerRepository.delete(customer);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(
                " Customer not found with Id " + customerId + " and UserId " + userId
        ));    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Page<Customer> getAllCustomersByUserId(Long userId, Pageable pageable) {
        return customerRepository.findByUserId(userId, pageable);
    }

    @Override
    public Customer getCustomerByIdAndUserId(Long userId, Long customerId) {
        return customerRepository.findByIdAndUserId(customerId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with Id" + customerId +
                                "and UserId " + userId
                ));
    }
}
