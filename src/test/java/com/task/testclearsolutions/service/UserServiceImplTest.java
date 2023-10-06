package com.task.testclearsolutions.service;

import com.task.testclearsolutions.model.User;
import com.task.testclearsolutions.repository.UserRepository;
import com.task.testclearsolutions.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;
import java.time.LocalDate;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Value("${user.min-age}")
    private int minAge;

    @Test
    public void testCreateUserValidAge() {
        User newUser = new User();
        newUser.setEmail("test@example.com");
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setBirthDate(LocalDate.of(1990, 1, 1));

        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User createdUser = userService.createUser(newUser);

        assertNotNull(createdUser);
        assertEquals(newUser.getEmail(), createdUser.getEmail());
        assertEquals(newUser.getFirstName(), createdUser.getFirstName());
        assertEquals(newUser.getLastName(), createdUser.getLastName());
        assertEquals(newUser.getBirthDate(), createdUser.getBirthDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserInvalidAge() {
        User newUser = new User();
        newUser.setEmail("test@example.com");
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setBirthDate(LocalDate.now().minusYears(15));

        ReflectionTestUtils.setField(userService, "minAge", 18);

        userService.createUser(newUser);
    }
}
