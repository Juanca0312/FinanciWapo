package com.econowapo.financiapp.service;

import com.econowapo.financiapp.model.Authority;


public interface AuthorityService {
    Authority createAuthority(Long userId, Authority authority);
    Authority getAuthorityByUserId(Long userId);
}
