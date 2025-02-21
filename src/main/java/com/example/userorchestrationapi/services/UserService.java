package com.example.userorchestrationapi.services;

import ch.qos.logback.core.pattern.parser.OptionTokenizer;
import com.example.userorchestrationapi.Models.User;

import java.util.List;

public interface UserService {


    void loadUsers();

    User findUserByEmail(String email);

    List<User> searchByNameOrSsnPrefix(String prefix);
}
