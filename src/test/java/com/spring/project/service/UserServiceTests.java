package com.spring.project.service;

import com.spring.project.entity.User;
import com.spring.project.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-prod.yml")
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Disabled
    @ParameterizedTest
    @ValueSource(strings = {
            "divyesh111",
            "raj",
    })
    public void testFindByUsername(String username) {
        assertNotNull(userRepository.findByUsername(username));
    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentProvider.class)
    @Disabled
    public void testSaveNewUser(User user) {
        assertTrue(userService.saveNewUser(user));
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,3,5",
            "43,45,88"
    })
    public void testAdd(int a, int b, int expected) {
        assertEquals(expected, a + b);
    }
}
