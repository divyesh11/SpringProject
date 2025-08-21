package com.spring.project.service;

import com.spring.project.config.TestConfigurations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisTests extends TestConfigurations {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    @Disabled
    public void testRedis() {
//        redisTemplate.opsForValue().set("email", "divyeshjivani000@gmail.com");
        Object email = redisTemplate.opsForValue().get("name");
        Assertions.assertEquals("divyesh1", email);
    }
}
