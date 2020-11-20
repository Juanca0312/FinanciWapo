package com.econowapo.financiapp.service;

import com.econowapo.financiapp.model.Token;
import com.econowapo.financiapp.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
@Service
public class TokenServiceImpl implements TokenService{

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public Token createToken() {
        Token token = new Token();
        Random rand = new Random();
        int upperbound = 9999;
        int lowerbound = 1000;
        int int_random = rand.nextInt(upperbound - lowerbound) + lowerbound;
        String str1 = Integer.toString(int_random);
        token.setToken(str1);
        return tokenRepository.save(token);
    }

    @Override
    public Token getLastToken() {
        List<Token> tokens = tokenRepository.findAll();
        if(tokens.size() == 0){
            return null;
        }
        return tokens.get(tokens.size()-1);
    }
}
