package com.example.userorchestrationapi.servicetest;

import com.example.userorchestrationapi.Models.User;
import com.example.userorchestrationapi.repositories.UserRepository;
import com.example.userorchestrationapi.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setSsn("123-45-6789");
    }

    @Test
    void testFindUserByEmail_UserFound() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        User foundUser = userService.findUserByEmail("test@example.com");
        assertNotNull(foundUser);
        assertEquals("test@example.com", foundUser.getEmail());
    }

    @Test
    void testFindUserByEmail_UserNotFound() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> userService.findUserByEmail("test@example.com"));
        assertEquals("User not found with the given email", exception.getMessage());
    }

    @Test
    void testSearchByNameOrSsnPrefix_UserFound() {
        List<User> users = Arrays.asList(user);
        when(userRepository.searchByNameOrSsnPrefix("Test")).thenReturn(users);
        List<User> foundUsers = userService.searchByNameOrSsnPrefix("Test");
        assertFalse(foundUsers.isEmpty());
        assertEquals(1, foundUsers.size());
        assertEquals("Test", foundUsers.get(0).getFirstName());
    }

    @Test
    void testSearchByNameOrSsnPrefix_UserNotFound() {
        when(userRepository.searchByNameOrSsnPrefix("Unknown")).thenReturn(Arrays.asList());
        Exception exception = assertThrows(RuntimeException.class, () -> userService.searchByNameOrSsnPrefix("Unknown"));
        assertEquals("No users found with the given prefix", exception.getMessage());
    }
}
