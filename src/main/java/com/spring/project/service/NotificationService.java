package com.spring.project.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {
//    @Autowired
//    DynamoDbClient dynamoDbClient;

    @PostConstruct
    public void sendNotification() {
//        try {
//            // List all tables (same as: aws dynamodb list-tables)
//            ListTablesResponse response = dynamoDbClient.listTables();
//            log.info("✅ Connected to DynamoDB!");
//            log.info("📊 Available tables: {}",response.tableNames());
//        } catch (Exception e) {
//            log.error("❌ Failed to connect to DynamoDB: {}", e.getMessage(), e);
//        }
    }
}
