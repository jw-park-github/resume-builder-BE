package com.devdoc.backend.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TokenStore {
    private final Set<String> tokens = new HashSet<>();

    public void addToken(String token) {
        tokens.add(token);
    }

    public boolean isValidToken(String token) {
        return tokens.contains(token);
    }

    public void removeToken(String token) {
        tokens.remove(token);
    }
}


