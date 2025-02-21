package com.example.userorchestrationapi.controllers;

import com.example.userorchestrationapi.Models.User;
import com.example.userorchestrationapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@Tag(name = "User API", description = "Operations related to Users")
public class UserController {
    @Autowired
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

    @GetMapping("/getByEmail")
    @Operation(summary = "Get user by Email", description = "Fetches a user based on their Email")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        User user = userService.findUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/searchByNamePrefix")
    @Operation(summary = "Search user", description = "Searches for a user based on their name and ssn")
    public ResponseEntity<List<User>> searchByNamePrefix(@RequestParam String prefix) {
        List<User> user = userService.searchByNameOrSsnPrefix(prefix);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}