package com.spring.project.service;

import com.spring.project.entity.JournalEntity;
import com.spring.project.entity.User;
import com.spring.project.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;
    private final UserService userService;

    @Autowired
    public JournalEntryService(JournalEntryRepository journalEntryRepository, UserService userService) {
        this.journalEntryRepository = journalEntryRepository;
        this.userService = userService;
    }

    @Transactional
    public void saveEntry(JournalEntity journalEntity, String username) {
        User user = userService.getUserByUsername(username);
        journalEntity.setDate(LocalDateTime.now());
        JournalEntity savedEntry = journalEntryRepository.save(journalEntity);
        log.debug("Saved Entry : + {}", savedEntry.getId());
        user.getJournals().add(savedEntry);
        user.setUsername(null);
        userService.saveUser(user);
    }

    public List<JournalEntity> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntity> getEntryById(String id) {
        return journalEntryRepository.findById(new ObjectId(id));
    }

    @Transactional
    public Boolean deleteById(String id, String username) {
        ObjectId journalId = new ObjectId(id);
        User user = userService.getUserByUsername(username);
        user.getJournals().removeIf(entry -> entry.getId().equals(journalId));
        userService.saveUser(user);
        journalEntryRepository.deleteById(journalId);
        return Boolean.TRUE;
    }
}
