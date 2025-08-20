package com.spring.project.scheduler;

import com.spring.project.entity.User;
import com.spring.project.repository.UserRepositoryImpl;
import com.spring.project.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Scheduled(cron = "0 0 9 * * Sun")
    public void fetchUsersAndSendSaMail() {
        List<User> users = userRepository.findUsersForSentimentAnalysis();
        log.info("Fetched user data for cron job {}", users.toString());
        for (User user : users) {
//            List<JournalEntity> journalEntityList = user.getJournals();
//            List<Sentiment> sentimentList = journalEntityList.stream().filter(journalEntity -> journalEntity.getDate().isAfter(LocalDateTime.now().minusDays(7))).map(JournalEntity::getSentiment).toList();
//            String entry = String.join(" ", sentimentList.stream().map(Enum::name).toList());
            emailService.sendEmail(
                    user.getEmail(),
                    "Sentiment Analysis Result For Last 7 Days",
                    "Your sentiment for the last 7 days is: " + "Positive"
            );
        }
    }
}
