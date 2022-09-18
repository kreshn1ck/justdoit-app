package com.ubt.todo.service.relationships;

import com.ubt.todo.enums.UserRelationshipType;
import com.ubt.todo.mappers.UserRelationshipTransportMapper;
import com.ubt.todo.model.User;
import com.ubt.todo.model.UserRelationship;
import com.ubt.todo.model.UserRelationshipId;
import com.ubt.todo.repository.UserRelationshipRepository;
import com.ubt.todo.repository.UserRepository;
import com.ubt.todo.service.user.UserService;
import com.ubt.todo.transports.UserRelationshipTransport;
import com.ubt.todo.utils.ValidationExceptionBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserRelationshipService {

    private final UserRepository userRepository;
    private final UserRelationshipRepository userRelationshipRepository;
    private final UserService userService;

    @Transactional
    public UserRelationshipTransport createFriendship(Long relatedUserId, String email) {
        User loggedInUser = userService.fndAuthenticatedUserByEmail(email);
        User relatedUser = userRepository.findUserById(relatedUserId);
        UserRelationship existingUserRelationship =
                getRelationshipForCurrentUserAndOtherUser(loggedInUser.getId(), relatedUser);
        if (existingUserRelationship != null) {
            throw ValidationExceptionBuilder.withErrorCode(RelationshipFailureReason.RELATIONSHIP_ALREADY_EXISTS.name());
        }
        UserRelationshipId userRelationshipId = new UserRelationshipId(loggedInUser.getId(), relatedUserId);
        UserRelationship userRelationship = UserRelationshipTransportMapper
                .createUserRelationship(userRelationshipId,loggedInUser, relatedUser);

        userRelationshipRepository.save(userRelationship);
        return UserRelationshipTransportMapper.toTransport(userRelationship, loggedInUser.getId());
    }

    @Transactional
    public void deleteFriendship(Long otherUserId, String email) {
        UserRelationship userRelationship = getUserRelationshipOrThrow(otherUserId, email);
        userRelationshipRepository.delete(userRelationship);
    }

    @Transactional
    public UserRelationshipTransport acceptFriendship(Long relatingUserId, String email, Long currentUserId) {
        UserRelationship userRelationship = getPendingFriendship(relatingUserId, email);
        userRelationship.setUserRelationshipType(UserRelationshipType.FRIENDS);
        userRelationshipRepository.save(userRelationship);
        return UserRelationshipTransportMapper.toTransport(userRelationship, currentUserId);
    }

    @Transactional
    public UserRelationshipTransport rejectFriendship(Long relatingUserId, String email, Long currentUserId) {
        UserRelationship userRelationship = getPendingFriendship(relatingUserId, email);
        userRelationshipRepository.delete(userRelationship);
        return UserRelationshipTransportMapper.toTransport(userRelationship, currentUserId);
    }

    public UserRelationshipTransport getRelationship(Long otherUserId, String email, Long currentUserId) {
        UserRelationship userRelationship = getUserRelationshipOrThrow(otherUserId, email);
        return UserRelationshipTransportMapper.toTransport(userRelationship, currentUserId);
    }

    public List<UserRelationshipTransport> getCurrentUserPendingRequests(Long userId) {
        // Long currentUserId = authenticationService.getAuthenticatedUserId();
        List<UserRelationship> relationships = userRelationshipRepository
                .findAllByRelatedUserIdAndType(userId, UserRelationshipType.PENDING.name());
        return UserRelationshipTransportMapper.toTransportList(relationships, userId);
    }

    public List<UserRelationshipTransport> getCurrentUserRequestedFriendships(Long currentUserId) {
        List<UserRelationship> relationships =  userRelationshipRepository
                .findAllByRelatingUserIdAndType(currentUserId, UserRelationshipType.PENDING.name());
        return UserRelationshipTransportMapper.toTransportList(relationships, currentUserId);
    }

    public List<UserRelationshipTransport> getAllCurrentUserFriendships(Long currentUserId) {
        List<UserRelationship> relationships =  userRelationshipRepository
                .findAllUserFriendships(currentUserId);
        return UserRelationshipTransportMapper.toTransportList(relationships, currentUserId);
    }

    public List<UserRelationshipTransport> getAllForUser(String email) {
        User currentUser = userService.fndAuthenticatedUserByEmail(email);
        Long currentUserId = currentUser.getId();

        Sort sortByUsernameAsc = Sort.by(Sort.Direction.ASC, "username");
        List<User> users = userRepository.findAll(sortByUsernameAsc);

        List<UserRelationshipTransport> existingRelationships = UserRelationshipTransportMapper
                .toTransportList(userRelationshipRepository.findAllUserRelationships(currentUserId), currentUserId);
        Map<Long, UserRelationshipTransport> relationshipsByRelatedUserId = existingRelationships.stream()
                .collect(Collectors.toMap(r -> r.getRelatedUser().getId(), Function.identity()));

        List<UserRelationshipTransport> allRelationships = new ArrayList<>();
        users.forEach(user -> {
            UserRelationshipTransport relationship = relationshipsByRelatedUserId.get(user.getId());
            if (relationship == null) {
                UserRelationshipId userRelationshipId = new UserRelationshipId(currentUserId, user.getId());
                relationship = UserRelationshipTransportMapper
                        .buildNonExistingRelationship(userRelationshipId, currentUser, user);
            }
            if (!user.getId().equals(currentUserId)) {
                allRelationships.add(relationship);
            }
        });
        return allRelationships;
    }

    private UserRelationship getUserRelationshipOrThrow(Long otherUserId, String email) {
        User loggedInUser = userService.fndAuthenticatedUserByEmail(email);
        User relatedUser = userRepository.findUserById(otherUserId);
        UserRelationship existingUserRelationship =
                getRelationshipForCurrentUserAndOtherUser(loggedInUser.getId(), relatedUser);
        if (existingUserRelationship == null) {
            throw ValidationExceptionBuilder.withErrorCode(RelationshipFailureReason.RELATIONSHIP_NOT_FOUND.name());
        }
        return existingUserRelationship;
    }

    private UserRelationship getRelationshipForCurrentUserAndOtherUser(Long currentUserId, User otherUser) {
        if (otherUser == null) {
            throw ValidationExceptionBuilder.withErrorCode(RelationshipFailureReason.RELATIONSHIP_USER_NOT_FOUND.name());
        }
        return userRelationshipRepository
                .findUserRelationship(currentUserId, otherUser.getId());
    }

    private UserRelationship getPendingFriendship(Long relatingUserId, String email) {
        User loggedInUser = userService.fndAuthenticatedUserByEmail(email);
        User relatingUser = userRepository.findUserById(relatingUserId);
        if (relatingUser == null) {
            throw ValidationExceptionBuilder.withErrorCode(RelationshipFailureReason.RELATIONSHIP_USER_NOT_FOUND.name());
        }
        UserRelationship userRelationship = userRelationshipRepository
                .findOneByRelatingUserIdAndRelatedUserIdAndType(relatingUserId,
                        loggedInUser.getId(), UserRelationshipType.PENDING.name());
        if (userRelationship == null) {
            throw ValidationExceptionBuilder.withErrorCode(RelationshipFailureReason.FRIEND_REQUEST_NOT_FOUND.name());
        }
        return userRelationship;
    }
}
