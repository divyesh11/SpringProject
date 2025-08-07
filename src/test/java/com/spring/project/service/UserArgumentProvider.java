package com.spring.project.service;

import com.spring.project.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UserArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
                Arguments.of(User.builder().username("test 1").password("pass 1").build()),
                Arguments.of(User.builder().username("test 2").password("").build()),
                Arguments.of(User.builder().username("").password("pass 1").build())
        );
    }
}
