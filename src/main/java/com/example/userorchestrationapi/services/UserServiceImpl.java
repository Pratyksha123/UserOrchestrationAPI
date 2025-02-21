package com.example.userorchestrationapi.services;

import ch.qos.logback.core.pattern.parser.OptionTokenizer;
import com.example.userorchestrationapi.Models.User;
import com.example.userorchestrationapi.dtos.OuterDTO;
import com.example.userorchestrationapi.dtos.UserDTO;
import com.example.userorchestrationapi.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    //private static final String EXTERNAL_API_URL = "https://dummyjson.com/users";

    UserRepository userRepository;

    RestTemplate restTemplate;

    public UserServiceImpl(UserRepository userRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }


    public void loadUsers() {
        // Fetch users from the external API
        OuterDTO outerdto = restTemplate.getForObject("https://dummyjson.com/users", OuterDTO.class);

        if (outerdto != null) {

            List<User> listOfUsers = new ArrayList<>();
            for (UserDTO userdto : outerdto.getUsers()) {
                // Save each user into the in-memory H2 database
                User user = convertUserDTOToUserAndSave(userdto);
                listOfUsers.add(user);
            }
            userRepository.saveAll(listOfUsers);

        }
        else {
            System.out.println("No user found");
        }
    }

    private User convertUserDTOToUserAndSave(UserDTO userdto){
        User user = new User();
        user.setId(userdto.getId());
        user.setFirstName(userdto.getFirstName());
        user.setLastName(userdto.getLastName());
        user.setEmail(userdto.getEmail());
        user.setSsn(userdto.getSsn());
        user.setImage(userdto.getImage());
        return user;
    }

    public User findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new RuntimeException("User not found");
        }
        return user.get();
    }

    @Override
    public List<User> searchByNameOrSsnPrefix(String prefix) {
        List<User> users = userRepository.searchByNameOrSsnPrefix(prefix);
        if(users.isEmpty()){
            throw new RuntimeException("No user found with the given prefix");
        }
        return users;
    }


}
