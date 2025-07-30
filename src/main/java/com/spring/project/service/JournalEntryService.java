package com.spring.project.service;

import com.spring.project.JournalEntity;
import com.spring.project.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntity journalEntity) {
        journalEntryRepository.save(journalEntity);
    }

    public List<JournalEntity> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntity> getEntryById(String id) {
        return journalEntryRepository.findById(new ObjectId(id));
    }

    public Boolean deleteById(String id) {
        journalEntryRepository.deleteById(new ObjectId(id));
        return Boolean.TRUE;
    }
}
