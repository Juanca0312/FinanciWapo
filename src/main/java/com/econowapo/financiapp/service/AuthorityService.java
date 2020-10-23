package com.econowapo.financiapp.service;

import com.econowapo.financiapp.model.Authority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthorityService {
    Authority createAuthority(Long userId, Authority authority);
    Authority getAuthorityByUserId(Long userId);
}
