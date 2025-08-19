package com.spring.project.service;

import com.spring.project.config.TestConfigurations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EmailServiceTests extends TestConfigurations {
    @Autowired
    private EmailService emailService;

    @Test
    public void testSendEmail() {
        String to = "";
        String subject = "Subject";
        String[] ccList = List.of(
                "divyeshjivani000@gmail.com"
        ).toArray(new String[0]);
        String body = "body";
        emailService.sendEmail(to, subject, body, ccList);
    }
}
