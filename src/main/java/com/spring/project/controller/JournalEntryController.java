package com.spring.project.controller;

import com.spring.project.JournalEntity;
import com.spring.project.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
    public void createJournal(@RequestBody JournalEntity journalEntity) {
        journalEntity.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(journalEntity);
    }

    @PutMapping("/{id}")
    public Boolean updateJournal(@PathVariable String id, @RequestBody JournalEntity journalEntity) {
        JournalEntity oldEntry = journalEntryService.getEntryById(id).orElse(null);
        if (oldEntry != null) {
            oldEntry.setTitle((journalEntity.getTitle() != null && !journalEntity.getTitle().isBlank()) ? journalEntity.getTitle() : oldEntry.getTitle());
            oldEntry.setContent((journalEntity.getContent() != null && !journalEntity.getContent().isBlank()) ? journalEntity.getContent() : oldEntry.getContent());
        } else {
            return false;
        }
        journalEntryService.saveEntry(oldEntry);
        return true;
    }

    @DeleteMapping("/{id}")
    public Boolean deleteJournal(@PathVariable String id) {
        JournalEntity oldEntry = journalEntryService.getEntryById(id).orElse(null);
        if (oldEntry != null) {
            return journalEntryService.deleteById(id);
        } else {
            return false;
        }
    }
}
