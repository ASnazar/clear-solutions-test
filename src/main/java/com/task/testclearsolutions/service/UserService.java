package com.task.testclearsolutions.service;

import java.time.LocalDate;
import java.util.List;
import com.task.testclearsolutions.model.User;

public interface UserService {
    User createUser(User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    User getUserById(Long id);

    List<User> getAllUsers();

    List<User> getUsersByBirthDateRange(LocalDate from, LocalDate to);
}
