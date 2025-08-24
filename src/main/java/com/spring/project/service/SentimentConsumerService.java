package com.spring.project.service;

import com.spring.project.constants.KafkaConsumerGroups;
import com.spring.project.constants.KafkaTopics;
import com.spring.project.kafkamodels.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SentimentConsumerService {
    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = KafkaTopics.WEEKLY_SENTIMENT_ANALYSIS, groupId = KafkaConsumerGroups.WEEKLY_SENTIMENT_GROUP)
    public void consume(SentimentData sentimentData) {
        sendMail(sentimentData);
    }

    private void sendMail(SentimentData sentimentData) {
        String subject = "Your Weekly Sentiment Analysis";
        String body = "Hello,\n\n" + sentimentData.getSentiment() + "\n\nBest regards,\nTeam";
        emailService.sendEmail(sentimentData.getEmail(), subject, body);
    }
}
