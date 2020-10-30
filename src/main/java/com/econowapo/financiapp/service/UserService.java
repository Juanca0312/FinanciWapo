package com.econowapo.financiapp.service;

import com.econowapo.financiapp.model.CustomerInfo;
import com.econowapo.financiapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    Page<User> getAllUsers(Pageable pageable);
    User getUserById(Long userId);
    User createUser(User user);
    User updateUser(Long userId, User userRequest);
    ResponseEntity<?> deleteUser(Long userId);
    List<CustomerInfo> getCustomersInfo();

}
