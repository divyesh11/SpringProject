package com.spring.project.scheduler;

import com.spring.project.entity.JournalEntity;
import com.spring.project.entity.User;
import com.spring.project.repository.UserRepositoryImpl;
import com.spring.project.service.EmailService;
import com.spring.project.service.SentimentAnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Scheduled(cron = "0 0 9 * * Sun")
    public void fetchUsersAndSendSaMail() {
        List<User> users = userRepository.findUsersForSentimentAnalysis();
        log.info("Fetched user data for cron job {}", users.toString());
        for (User user : users) {
            List<JournalEntity> journalEntityList = user.getJournals();
            List<String> filteredList = journalEntityList.stream().filter(journalEntity -> journalEntity.getDate().isAfter(LocalDateTime.now().minusDays(7))).map(JournalEntity::getContent).toList();
            String entry = String.join(" ", filteredList);
            String sentiment = sentimentAnalysisService.analyzeSentiment(entry);
            emailService.sendEmail(
                    user.getEmail(),
                    "Sentiment Analysis Result For Last 7 Days",
                    "Your sentiment for the last 7 days is: " + sentiment
            );
        }
    }
}
