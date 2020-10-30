package com.econowapo.financiapp.service;

import com.econowapo.financiapp.exception.ResourceNotFoundException;
import com.econowapo.financiapp.model.*;
import com.econowapo.financiapp.repository.CreditAccountRepository;
import com.econowapo.financiapp.repository.CustomerRepository;
import com.econowapo.financiapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CreditAccountRepository creditAccountRepository;

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, User userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        user.setEnabled(userRequest.getEnabled());
        user.setPassword(userRequest.getPassword());
        user.setUsername(userRequest.getUsername());
        return userRepository.save(user);
    }

    @Override
    public ResponseEntity<?> deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }

    @Override
    public List<CustomerInfo> getCustomersInfo() {
        CustomerInfo customerInfoo = new CustomerInfo();
        List<CustomerInfo> customerInfo = new ArrayList<>();
        List<Customer> customers =  customerRepository.findAll();
        User user = new User();
        CreditAccount creditAccount = new CreditAccount();
        for (Customer customer : customers){
            user = customer.getUser();
            creditAccount = creditAccountRepository.findByCustomerId(customer.getId());
            customerInfoo.setCustomerId(customer.getId());
            customerInfoo.setAddress(customer.getAddress());
            customerInfoo.setDate(creditAccount.getGenerated_date());
            customerInfoo.setName(customer.getName());
            customerInfoo.setPassword(user.getPassword());
            customerInfoo.setUsername(user.getUsername());
            customerInfoo.setRate(creditAccount.getInterest_rate_value());
            customerInfoo.setRate_type(creditAccount.getInterest_rate());
            customerInfo.add(customerInfoo);
        }
        return customerInfo;
    }
}
