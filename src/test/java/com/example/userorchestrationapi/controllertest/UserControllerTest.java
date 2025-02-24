package com.example.userorchestrationapi.controllers;

import com.example.userorchestrationapi.Models.User;
import com.example.userorchestrationapi.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("Test User");
    }

    @Test
    void testGetUserByEmail() {
        when(userService.findUserByEmail("test@example.com")).thenReturn(user);
        ResponseEntity<User> response = userController.searchByEmail("test@example.com");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("test@example.com", response.getBody().getEmail());
    }

    @Test
    void testSearchByNamePrefix() {
        List<User> users = Arrays.asList(user);
        when(userService.searchByNameOrSsnPrefix("Test"))
                .thenReturn(users);
        ResponseEntity<List<User>> response = userController.searchByNameOrSsnPrefix("Test");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Test User", response.getBody().get(0).getFirstName());
    }
}
