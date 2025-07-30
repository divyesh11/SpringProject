package com.spring.project;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journal_entries")
@Data
public class JournalEntity {
    @Id
    private ObjectId id;
    private String title;
    private String content;
    private LocalDateTime date;
}
