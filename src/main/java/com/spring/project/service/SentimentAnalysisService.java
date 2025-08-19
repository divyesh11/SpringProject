package com.spring.project.service;

import org.springframework.stereotype.Service;

@Service
public class SentimentAnalysisService {
    public String analyzeSentiment(String text) {
        return "Positive";
    }
}
