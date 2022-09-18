package com.ubt.todo.repository;

import com.ubt.todo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(Long id);

    User findByEmail(String email);

    // Page<User> findAlByFirstNameContainingOrLastNameContaining(String name, String lastName, Pageable pageable);

    void deleteAllByIdIn(List<Long> ids);
}
