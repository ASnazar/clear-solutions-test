package com.task.testclearsolutions.service.impl;

import java.time.LocalDate;;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import com.task.testclearsolutions.exeption.UserNotFoundException;
import com.task.testclearsolutions.model.User;
import com.task.testclearsolutions.repository.UserRepository;
import com.task.testclearsolutions.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Value("${user.min-age}")
    private int minAge;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        if (calculateAge(user.getBirthDate()) < minAge) {
            throw new IllegalArgumentException("User must be at least " + minAge + " years old.");
        }
        return userRepository.save(user);
    }

    private int calculateAge(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        long years = ChronoUnit.YEARS.between(birthDate, currentDate);
        return Math.toIntExact(years);

    }

    @Override
    public User updateUser(Long id, User user) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setEmail(user.getEmail());
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setBirthDate(user.getBirthDate());
            updatedUser.setAddress(user.getAddress());
            updatedUser.setPhoneNumber(user.getPhoneNumber());
            return userRepository.save(updatedUser);
        } else {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByBirthDateRange(LocalDate from, LocalDate to) {
        List<User> users = userRepository.findByBirthDateBetween(from, to);
        return users;
    }
}
