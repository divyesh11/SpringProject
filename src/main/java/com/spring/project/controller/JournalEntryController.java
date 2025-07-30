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

    @GetMapping("/all")
    public List<JournalEntity> getAll() {
        return journalEntryService.getAllEntries();
    }

    @PostMapping
    public void createJournal(@RequestBody JournalEntity journalEntity) {
        journalEntity.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(journalEntity);
    }
}
