package com.example.userorchestrationapi.controllers;

import com.example.userorchestrationapi.Exception.UserNotFoundException;
import com.example.userorchestrationapi.Models.User;
import com.example.userorchestrationapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@Tag(name = "User API", description = "Operations related to Users")
@Validated
public class UserController {

    private UserService userService;

//    @PostMapping("/load")
//    public String loadUsers() {
//        userService.loadUsers();
//        return "Users successfully loaded into the H2 database.";
//    }

//    @GetMapping("/sayHi")
//    public String sayHi() {
//        return "Hi!";
//    }

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Fetch a user by email
     */
    @GetMapping("/getByEmail")
    @Operation(summary = "Get user by Email", description = "Fetches a user based on their Email")
    public ResponseEntity<User> searchByEmail(
            @RequestParam("email")
            @Valid @NotBlank(message = "Email must not be empty")
            @Email(message = "Invalid email format") String email)   throws UserNotFoundException {
        User user = userService.findUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Search users by firstName, lastName or SSN prefix
     */
    @GetMapping("/searchByNameOrSsnPrefix")
    @Operation(summary = "Search user", description = "Searches for a user based on their name and ssn")
    public ResponseEntity<List<User>> searchByNameOrSsnPrefix(@RequestParam("prefix") String prefix)  throws UserNotFoundException{
        List<User> user = userService.searchByNameOrSsnPrefix(prefix);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}