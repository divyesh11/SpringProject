package com.spring.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class QuotesService {
    public final String apiKey;

    @Autowired
    QuotesService(@Value("${api.keys.quotes}") String apiKey) {
        this.apiKey = apiKey;
    }
}
