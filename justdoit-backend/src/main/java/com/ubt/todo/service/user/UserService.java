package com.ubt.todo.service.user;

import com.ubt.todo.transports.UserTransport;
import com.ubt.todo.mappers.UserTransportMapper;
import com.ubt.todo.model.User;
import com.ubt.todo.repository.UserRepository;
import com.ubt.todo.request.CreateUserTransport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@AllArgsConstructor
@Service
@Slf4j
public class UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserTransport createUser(CreateUserTransport createUserTransport) {
        return createUser(createUserTransport.getId(), createUserTransport.getUsername(), createUserTransport.getEmail());
    }

    @Transactional
    public UserTransport createUser(Long id, String email, String username) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setCreatedAt(new Date());
        userRepository.save(user);
        LOGGER.info("User saved successfully in backend service");
        return UserTransportMapper.toTransport(user);
    }

    public User fndAuthenticatedUserByEmail(String email) {
        LOGGER.info("Getting authenticated user with email: {} ", email);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User with email " + email + " does not exist");
        }
        return user;
    }
}
