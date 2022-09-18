package com.ubt.todo.controller;

import com.ubt.todo.service.todo.TodoService;
import com.ubt.todo.transports.TodoTransport;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/todos")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoTransport> create(@RequestBody TodoTransport todoTransport, @RequestHeader("email") String email) {
        TodoTransport todo = todoService.createTodo(todoTransport, email);
        return ResponseEntity.ok(todo);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<TodoTransport> get(@PathVariable Long todoId, @RequestHeader("userId") String userId) {
        TodoTransport todo = todoService.getTodo(todoId, Long.parseLong(userId));
        return ResponseEntity.ok(todo);
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<TodoTransport> update(@PathVariable Long todoId,
                                                @RequestBody TodoTransport todoTransport,
                                                @RequestHeader("userId") String userId) {
        TodoTransport todo = todoService.updateTodo(todoId, todoTransport, Long.parseLong(userId));
        return ResponseEntity.ok(todo);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{todoId}")
    public void delete(@PathVariable Long todoId, @RequestHeader("userId") String userId) {
        todoService.deleteTodo(todoId, Long.parseLong(userId));
    }

    @PutMapping("/{todoId}/finished")
    public ResponseEntity<TodoTransport> markFinished(@PathVariable Long todoId, @RequestHeader("userId") String userId) {
        TodoTransport todo = todoService.markFinished(todoId, Long.parseLong(userId));
        return ResponseEntity.ok(todo);
    }

    @PutMapping("/{todoId}/not-finished")
    public ResponseEntity<TodoTransport> markNotFinished(@PathVariable Long todoId, @RequestHeader("userId") String userId) {
        TodoTransport todo = todoService.markNotFinished(todoId, Long.parseLong(userId));
        return ResponseEntity.ok(todo);
    }

    @GetMapping("/created")
    public List<TodoTransport> getCreatedTodos(@RequestParam(required = false, defaultValue = "true") boolean oldestFirst,
                                               @RequestHeader("email") String email) {
        return todoService.getCurrentCreatedTodos(oldestFirst, email);
    }

    @GetMapping("/assigned")
    public List<TodoTransport> getAssignedTodos(@RequestParam(required = false, defaultValue = "true") boolean oldestFirst,
                                                @RequestHeader("email") String email) {
        return todoService.getCurrentUserAssignedTodos(oldestFirst, email);
    }
}
