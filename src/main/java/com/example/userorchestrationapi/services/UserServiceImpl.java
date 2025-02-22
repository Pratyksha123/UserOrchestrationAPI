package com.example.userorchestrationapi.services;

import com.example.userorchestrationapi.Exception.ExternalApiException;
import com.example.userorchestrationapi.Exception.UserNotFoundException;
import com.example.userorchestrationapi.Models.User;
import com.example.userorchestrationapi.dtos.OuterDTO;
import com.example.userorchestrationapi.dtos.UserDTO;
import com.example.userorchestrationapi.repositories.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
//import io.github.resilience4j.retry.annotation.Retry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Value("${external.users.url}")
    private String usersApiUrl;

    @Value("${external.users.api.retry}")
    private int retryConfig;

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    public UserServiceImpl(UserRepository userRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    @Cacheable(value = "users")
//    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackGetUsers")
//    @Retry(name = "userService", fallbackMethod = "fallbackGetUsers")
    public void loadUsers() {
        OuterDTO outerDto = null;
        int retryAttempts = retryConfig; // Store retry value in a local variable to avoid unintended side effects.

        while (retryAttempts > 0) {
            try {
                retryAttempts--;
                outerDto = restTemplate.getForObject(usersApiUrl, OuterDTO.class);

                if (outerDto != null && outerDto.getUsers() != null) {
                    List<User> userList = new ArrayList<>();
                    for (UserDTO userDto : outerDto.getUsers()) {
                        User user = convertUserDTOToUser(userDto);
                        userList.add(user);
                    }
                    userRepository.saveAll(userList);
                    logger.info("Successfully loaded {} users into the database.", userList.size());
                    return; // Exit loop on success
                }

            } catch (RestClientException e) {
                logger.error("Error fetching users from external API. Attempts remaining: {}", retryAttempts, e);
            }
        }
        if(outerDto == null || outerDto.getUsers() == null){
            logger.error("Failed to load users from external API after {} attempts.", retryConfig);
            throw new ExternalApiException("Failed to load users from external API after " + retryConfig + " attempts");
        }
    }
    public void fallbackGetUsers(Throwable t) {
        logger.error("Fallback method executed. Unable to load users.", t);
    }
    private User convertUserDTOToUser(UserDTO userDto) {
        return new User(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.getSsn(),
                userDto.getImage()
        );
    }

    @Override
    public User findUserByEmail(String email) {
        logger.info("Fetching user by email: {}", email);
        Optional<User> user =  userRepository.findByEmail(email);
        if(user.isEmpty()) {
            logger.error("User not found with the given email: {}", email);
            throw new UserNotFoundException("User not found with the given email");
        }
        return user.get();

    }

    @Override
    public List<User> searchByNameOrSsnPrefix(String prefix) {
        logger.info("Searching users by prefix: {}", prefix);
        List<User> users = userRepository.searchByNameOrSsnPrefix(prefix);

        if (users.isEmpty()) {
            logger.error("No users found with the given prefix: {}", prefix);
            throw new UserNotFoundException("No users found with the given prefix");
        }
        return users;
    }
}
