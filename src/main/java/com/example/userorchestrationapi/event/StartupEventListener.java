package com.example.userorchestrationapi.event;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.example.userorchestrationapi.services.UserService;

@Component
public class StartupEventListener {

    private UserService userService;

    public StartupEventListener(UserService userService) {
        this.userService = userService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void callApiOnStartup() {
        userService.loadUsers();
        System.out.println("API Data Fetched and Stored");
    }
}
