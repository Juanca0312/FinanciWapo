package com.econowapo.financiapp.service;

import com.econowapo.financiapp.exception.ResourceNotFoundException;
import com.econowapo.financiapp.model.Authority;
import com.econowapo.financiapp.repository.AuthorityRepository;
import com.econowapo.financiapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl implements AuthorityService{

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public Authority createAuthority(Long userId, Authority authority) {
        return userRepository.findById(userId).map(user -> {
            authority.setUser(user);
            return authorityRepository.save(authority);
        }).orElseThrow(() -> new ResourceNotFoundException(
                "User", "Id", userId
        ));
    }

    @Override
    public Authority getAuthorityByUserId(Long userId) {
        return authorityRepository.findByUserId(userId);
    }
}
