package com.spring.project.config;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-prod.yml")
@ActiveProfiles("prod")
public abstract class TestConfigurations {
}
