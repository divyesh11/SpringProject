package com.spring.project.controller;

import com.spring.project.entity.JournalEntity;
import com.spring.project.entity.User;
import com.spring.project.service.JournalEntryService;
import com.spring.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private final JournalEntryService journalEntryService;
    private final UserService userService;

    @Autowired
    public JournalEntryController(JournalEntryService journalEntryService, UserService userService) {
        this.journalEntryService = journalEntryService;
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getAll(@PathVariable String username) {
        if (username == null || username.isBlank()) {
            return ResponseEntity.badRequest().body("Username is required");
        }
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<JournalEntity> journalEntityList = user.getJournals();
        if (journalEntityList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(journalEntityList);
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> createJournal(@RequestBody JournalEntity journalEntity, @PathVariable String username) {
        if (journalEntity.getContent().isBlank()) return new ResponseEntity<>("Empty Content", HttpStatus.BAD_REQUEST);
        if (journalEntity.getTitle().isBlank()) return new ResponseEntity<>("Empty Title", HttpStatus.BAD_REQUEST);
        if (username == null || username.isBlank()) {
            return ResponseEntity.badRequest().body("Username is required");
        }
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return new ResponseEntity<>("User Not found!", HttpStatus.NOT_FOUND);
        }
        journalEntryService.saveEntry(journalEntity, username);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntity> getJournalById(@PathVariable String id) {
        Optional<JournalEntity> journalEntity = journalEntryService.getEntryById(id);
        if (journalEntity.isPresent()) {
            return new ResponseEntity<JournalEntity>(journalEntity.get(), HttpStatus.OK);
        }
        return new ResponseEntity<JournalEntity>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{username}/id/{id}")
    public ResponseEntity<Boolean> deleteJournal(@PathVariable String id, @PathVariable String username) {
        Optional<JournalEntity> oldEntry = journalEntryService.getEntryById(id);
        if (oldEntry.isPresent()) {
            Boolean result = journalEntryService.deleteById(id, username);
            return new ResponseEntity<Boolean>(result, HttpStatus.OK);
        }
        return new ResponseEntity<Boolean>(HttpStatus.NO_CONTENT);
    }
}
