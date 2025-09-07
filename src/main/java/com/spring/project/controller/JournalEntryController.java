package com.spring.project.controller;

import com.spring.project.entity.JournalEntity;
import com.spring.project.entity.User;
import com.spring.project.service.JournalEntryService;
import com.spring.project.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
@Tag(name = "Journal APIs")
public class JournalEntryController {

    private final JournalEntryService journalEntryService;
    private final UserService userService;

    @Autowired
    public JournalEntryController(JournalEntryService journalEntryService, UserService userService) {
        this.journalEntryService = journalEntryService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
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

    @PostMapping
    public ResponseEntity<?> createJournal(@RequestBody JournalEntity journalEntity) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
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
    public ResponseEntity<?> getJournalById(@PathVariable String id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        List<JournalEntity> collect = user.getJournals().stream().filter(x -> x.getId().equals(id)).toList();
        if (collect.isEmpty()) {
            return new ResponseEntity<JournalEntity>(HttpStatus.NOT_FOUND);
        }
        Optional<JournalEntity> journalEntity = journalEntryService.getEntryById(id);
        return journalEntity.map(entity -> new ResponseEntity<>(entity, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> updateJournalById(@PathVariable String id, @RequestBody JournalEntity newJournalEntity) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        List<JournalEntity> collect = user.getJournals().stream().filter(x -> x.getId().equals(id)).toList();
        if (collect.isEmpty()) {
            return new ResponseEntity<JournalEntity>(HttpStatus.NOT_FOUND);
        }
        Optional<JournalEntity> oldEntry = journalEntryService.getEntryById(id);
        if (!oldEntry.isPresent()) {
            return new ResponseEntity<>("Entry not found", HttpStatus.NOT_FOUND);
        }
        JournalEntity journalEntity = oldEntry.get();
        journalEntity.setTitle(newJournalEntity.getTitle() == null ? journalEntity.getTitle() : newJournalEntity.getTitle());
        journalEntity.setContent(newJournalEntity.getContent() == null ? journalEntity.getContent() : newJournalEntity.getContent());
        journalEntryService.updateEntry(journalEntity, username);
        return new ResponseEntity<>(journalEntity, HttpStatus.OK);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<Boolean> deleteJournal(@PathVariable String id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<JournalEntity> oldEntry = journalEntryService.getEntryById(id);
        if (oldEntry.isPresent()) {
            Boolean result = journalEntryService.deleteById(id, username);
            return new ResponseEntity<Boolean>(result, HttpStatus.OK);
        }
        return new ResponseEntity<Boolean>(HttpStatus.NO_CONTENT);
    }
}
