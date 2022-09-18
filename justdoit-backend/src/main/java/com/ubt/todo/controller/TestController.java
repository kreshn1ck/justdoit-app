package com.ubt.todo.controller;

import com.ubt.todo.model.UserRelationship;
import com.ubt.todo.repository.UserRelationshipRepository;
import com.ubt.todo.service.relationships.UserRelationshipService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
public class TestController {

    private final UserRelationshipRepository userRelationshipRepository;
    private final UserRelationshipService userRelationshipService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/api/test")
    public void validateResetToken(@RequestHeader("userId") String userId) {
        // LOGGER.info("Getting user token: {}", token);
        userRelationshipService.getCurrentUserPendingRequests(Long.parseLong(userId));
        UserRelationship relationship = userRelationshipRepository.findUserRelationship(3L, 4L);
        if (relationship == null) {
            System.out.println("NULLAJ");
        }
    }
}
