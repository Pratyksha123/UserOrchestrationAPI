package com.example.userorchestrationapi.event;

import com.example.userorchestrationapi.Exception.ExternalApiException;
import com.example.userorchestrationapi.services.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.example.userorchestrationapi.services.UserService;

@Component
public class StartupEventListener {
    private static final Logger logger = LogManager.getLogger(StartupEventListener.class);
    private UserService userService;

    public StartupEventListener(UserService userService) {
        this.userService = userService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void callApiOnStartup()  throws ExternalApiException {
        userService.loadUsers();
        logger.info("API Data Fetched and Stored");
    }
}
