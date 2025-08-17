package com.spring.project.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "journal_users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private String username;
    @Indexed(unique = true)
    private String email;
    private boolean sentimentAnalysis;
    @NonNull
    private String password;
    @DBRef
    @Builder.Default
    private List<JournalEntity> journals = new ArrayList<>();
    private List<String> roles;

    public String getId() {
        return id.toHexString();
    }
}