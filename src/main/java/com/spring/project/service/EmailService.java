package com.spring.project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String body, String... cc) {
        try {
            log.info("Sending email to: {}", to);
            log.info("Subject: {}", subject);
            log.info("Body: {}", body);

            SimpleMailMessage email = new SimpleMailMessage();
            email.setSubject(subject);
            email.setTo(to);
            email.setText(body);
            email.setCc(cc);
            javaMailSender.send(email);
        } catch (Exception e) {
            log.error("Error sending email to {}", to, e);
        }
    }
}
