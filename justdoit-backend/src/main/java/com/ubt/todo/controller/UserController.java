package com.ubt.todo.controller;

import com.ubt.todo.service.user.UserService;
import com.ubt.todo.transports.UserTransport;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @PostMapping("/users/create")
    public ResponseEntity<UserTransport> createUser(@RequestParam Long id,
                                                    @RequestParam String email,
                                                    @RequestParam String username) {
        LOGGER.info("Endpoint createUser in backend is called");
        UserTransport userTransport = userService.createUser(id, email, username);
        return ResponseEntity.ok(userTransport);
    }
}
