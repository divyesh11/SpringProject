package com.spring.project.scheduler;

import com.spring.project.constants.KafkaTopics;
import com.spring.project.entity.User;
import com.spring.project.entity.enums.Sentiment;
import com.spring.project.kafkamodels.SentimentData;
import com.spring.project.repository.UserRepositoryImpl;
import com.spring.project.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

    @Scheduled(cron = "0 0 9 * * Sun")
    public void fetchUsersAndSendSaMail() {
        List<User> users = userRepository.findUsersForSentimentAnalysis();
        log.info("Fetched user data for cron job {}", users.toString());
        for (User user : users) {
            SentimentData sentimentData = SentimentData.builder()
                    .email(user.getEmail())
                    .sentiment("Your sentiment for the last 7 days is: " + Sentiment.HAPPY.name())
                    .build();
            kafkaTemplate.send(KafkaTopics.WEEKLY_SENTIMENT_ANALYSIS, sentimentData.getEmail(), sentimentData);
        }
    }
}
