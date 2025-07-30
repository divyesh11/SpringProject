package com.spring.project.controller;

import com.spring.project.JournalEntity;
import com.spring.project.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntity> getAll() {
        return journalEntryService.getAllEntries();
    }

    @PostMapping
    public ResponseEntity<String> createJournal(@RequestBody JournalEntity journalEntity) {
        if (journalEntity.getContent().isBlank()) return new ResponseEntity<>("Empty Content", HttpStatus.BAD_REQUEST);
        if (journalEntity.getTitle().isBlank()) return new ResponseEntity<>("Empty Title", HttpStatus.BAD_REQUEST);
        journalEntity.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(journalEntity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalEntity> getJournalById(@PathVariable String id) {
        Optional<JournalEntity> journalEntity = journalEntryService.getEntryById(id);
        if (journalEntity.isPresent()) {
            return new ResponseEntity<JournalEntity>(journalEntity.get(), HttpStatus.OK);
        }
        return new ResponseEntity<JournalEntity>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JournalEntity> updateJournal(@PathVariable String id, @RequestBody JournalEntity journalEntity) {
        JournalEntity oldEntry = journalEntryService.getEntryById(id).orElse(null);
        if (oldEntry != null) {
            oldEntry.setTitle((journalEntity.getTitle() != null && !journalEntity.getTitle().isBlank()) ? journalEntity.getTitle() : oldEntry.getTitle());
            oldEntry.setContent((journalEntity.getContent() != null && !journalEntity.getContent().isBlank()) ? journalEntity.getContent() : oldEntry.getContent());
        } else {
            return new ResponseEntity<JournalEntity>(HttpStatus.NOT_FOUND);
        }
        journalEntryService.saveEntry(oldEntry);
        return new ResponseEntity<JournalEntity>(oldEntry, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteJournal(@PathVariable String id) {
        Optional<JournalEntity> oldEntry = journalEntryService.getEntryById(id);
        if (oldEntry.isPresent()) {
            Boolean result = journalEntryService.deleteById(id);
            return new ResponseEntity<Boolean>(result, HttpStatus.OK);
        }
        return new ResponseEntity<Boolean>(HttpStatus.NO_CONTENT);
    }
}
