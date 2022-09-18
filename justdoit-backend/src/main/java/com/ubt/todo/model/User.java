package com.ubt.todo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    private Long id;

    @NotBlank(message = "Username cannot be null")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "E-mail cannot be null")
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
