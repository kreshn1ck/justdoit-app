package com.ubt.todo.controller;

import com.ubt.todo.service.relationships.UserRelationshipService;
import com.ubt.todo.transports.UserRelationshipTransport;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/relationship")
public class UserRelationshipController {

    private final UserRelationshipService relationshipService;

    @PostMapping("/{id}")
    public ResponseEntity<UserRelationshipTransport> create(@PathVariable Long id, @RequestHeader("email") String email) {
        UserRelationshipTransport relationship = relationshipService.createFriendship(id, email);
        return ResponseEntity.ok(relationship);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRelationshipTransport> get(@PathVariable Long id, 
                                                         @RequestHeader("email") String email,
                                                         @RequestHeader("userId") String userId) {
        UserRelationshipTransport relationship = relationshipService.getRelationship(id, email, Long.parseLong(userId));
        return ResponseEntity.ok(relationship);
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<UserRelationshipTransport> accept(@PathVariable Long id,
                                                            @RequestHeader("email") String email,
                                                            @RequestHeader("userId") String userId) {
        UserRelationshipTransport relationship = relationshipService.acceptFriendship(id, email, Long.parseLong(userId));
        return ResponseEntity.ok(relationship);
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<UserRelationshipTransport> reject(@PathVariable Long id, 
                                                            @RequestHeader("email") String email,
                                                            @RequestHeader("userId") String userId) {
        UserRelationshipTransport relationship = relationshipService.rejectFriendship(id, email, Long.parseLong(userId));
        return ResponseEntity.ok(relationship);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}/cancel")
    public void cancel(@PathVariable Long id, @RequestHeader("email") String email) {
        relationshipService.deleteFriendship(id, email);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @RequestHeader("email") String email) {
        relationshipService.deleteFriendship(id, email);
    }

    @GetMapping("/pending")
    public List<UserRelationshipTransport> geAllPendingRequests(@RequestHeader("userId") String userId) {
        return relationshipService.getCurrentUserPendingRequests(Long.parseLong(userId));
    }

    @GetMapping("/requested")
    public List<UserRelationshipTransport> getAllRequestedRequests(@RequestHeader("userId") String userId) {
        return relationshipService.getCurrentUserRequestedFriendships(Long.parseLong(userId));
    }

    @GetMapping("/friends")
    public List<UserRelationshipTransport> getAllFriends(@RequestHeader("userId") String userId) {
        return relationshipService.getAllCurrentUserFriendships(Long.parseLong(userId));
    }

    @GetMapping("/all")
    public List<UserRelationshipTransport> getAllPeople(@RequestHeader("email") String email) {
        return relationshipService.getAllForUser(email);
    }
}
