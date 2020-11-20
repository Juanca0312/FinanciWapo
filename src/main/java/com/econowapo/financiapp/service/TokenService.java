package com.econowapo.financiapp.service;

import com.econowapo.financiapp.model.Token;

public interface TokenService {
    Token createToken();
    Token getLastToken();
}
