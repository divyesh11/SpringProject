package com.spring.project.scheduler;

import com.spring.project.cache.AppCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppCacheScheduler {
    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 */5 * * * *")
    public void refreshAppCache() {
        appCache.init();
        log.info("AppCache reloaded successfully");
    }
}
