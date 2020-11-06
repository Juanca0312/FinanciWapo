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
import java.util.Optional;

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
    public User login(User userRequest) {
        User myUser = userRepository.findByUsername(userRequest.getUsername());
        if(myUser == null){
            return userRequest; //wrong username
        }
        if(userRequest.getPassword().equals(myUser.getPassword())){
            return myUser; //found user
        }
        return userRequest; //wrong password
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

        List<CustomerInfo> customerInfoList = new ArrayList<>();
        List<Customer> customers =  customerRepository.findAll();

        for (Customer customer : customers){
            CustomerInfo customerInfo = new CustomerInfo();

            User user = new User();
            user = customer.getUser();

            CreditAccount creditAccount = new CreditAccount();
            creditAccount = creditAccountRepository.findByCustomerId(customer.getId());

            customerInfo.setCustomerId(customer.getId());
            customerInfo.setAddress(customer.getAddress());
            customerInfo.setDate(creditAccount.getGenerated_date());
            customerInfo.setName(customer.getName());
            customerInfo.setPassword(user.getPassword());
            customerInfo.setUsername(user.getUsername());
            customerInfo.setRate(creditAccount.getInterest_rate_value());
            customerInfo.setRate_type(creditAccount.getInterest_rate());

            customerInfoList.add(customerInfo);
        }
        return customerInfoList;
    }
}
