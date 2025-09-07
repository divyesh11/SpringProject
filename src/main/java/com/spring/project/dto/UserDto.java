package com.spring.project.dto;

import com.spring.project.entity.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotEmpty
    private String username;
    private String email;
    private boolean sentimentAnalysis;
    @NotEmpty
    private String password;

    public User toUserEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .sentimentAnalysis(this.sentimentAnalysis)
                .build();
    }
}
