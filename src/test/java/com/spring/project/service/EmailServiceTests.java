package com.spring.project.service;

import com.spring.project.config.TestConfigurations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailServiceTests extends TestConfigurations {
    @Autowired
    private EmailService emailService;

    @Test
    public void testSendEmail() {
        String to = "divyeshjivani000@gmail.com";
        String subject = "Test Email";
        String body = "This is a test email sent from the EmailServiceTests.";
        emailService.sendEmail(to, subject, body);
    }
}
