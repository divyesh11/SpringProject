package com.spring.project.controller;

import com.spring.project.JournalEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<Long, JournalEntity> journalEntityMap = new HashMap<Long,JournalEntity>();

    @GetMapping("/all")
    public List<JournalEntity> getAll() {
        return new ArrayList<>(journalEntityMap.values());
    }

    @PostMapping
    public void createJournal(@RequestBody JournalEntity journalEntity) {
        journalEntityMap.put(journalEntity.getId(), journalEntity);
    }
}
