package com.spring.project.cache;

import com.spring.project.entity.ConfigJournalApp;
import com.spring.project.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class AppCache {
    private final ConfigJournalAppRepository configJournalAppRepository;
    private Map<String, String> cache;

    @Autowired
    AppCache(ConfigJournalAppRepository configJournalAppRepository) {
        this.configJournalAppRepository = configJournalAppRepository;
    }

    @PostConstruct
    public void init() {
        this.cache = new HashMap<>();
        Map<String, String> tempCache = new HashMap<>();
        List<ConfigJournalApp> entries = configJournalAppRepository.findAll();
        entries.forEach(entry -> tempCache.put(entry.getKey(), entry.getValue()));
        this.cache = tempCache;
        log.info("AppCache initialized with {} entries", cache.size());
    }

    public String get(String key) {
        return cache.get(key);
    }

    public void put(String key, String value) {
        cache.put(key, value);
    }

    public void clear() {
        cache.clear();
    }

    public boolean containsKey(String key) {
        return cache.containsKey(key);
    }

    public Map<String, String> getAll() {
        return new HashMap<>(cache);
    }

    public void remove(String key) {
        cache.remove(key);
    }

    public int size() {
        return cache.size();
    }

    public boolean isEmpty() {
        return cache.isEmpty();
    }
}
