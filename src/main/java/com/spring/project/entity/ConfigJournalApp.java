package com.spring.project.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "config_journal_app")
@Data
@NoArgsConstructor
public class ConfigJournalApp {
    @Id
    private ObjectId id;
    @NonNull
    private String key;
    @NonNull
    private String value;
}
