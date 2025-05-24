package com.example.springboot3todoapplication.services;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.springboot3todoapplication.models.StatusType;
import com.example.springboot3todoapplication.models.Todo;
import com.example.springboot3todoapplication.repositories.TodoRepository;

class TodoServiceTests {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    public TodoServiceTests() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Todo todo1 = new Todo(1L, "Test Todo 1", StatusType.BACKLOG, LocalDateTime.now(), LocalDateTime.now());
        Todo todo2 = new Todo(2L, "Test Todo 2", StatusType.DOING, LocalDateTime.now(), LocalDateTime.now());
        when(todoRepository.findAll()).thenReturn(Arrays.asList(todo1, todo2));

        List<Todo> todos = todoService.getAll();
        assertEquals(2, todos.size());
    }

    @Test
    void testGetById() {
        Todo todo = new Todo(1L, "Test Todo", StatusType.BACKLOG, LocalDateTime.now(), LocalDateTime.now());
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        Todo foundTodo = todoService.getById(1L);
        assertEquals("Test Todo", foundTodo.getDescription());
    }

    @Test
    void testSave() {
        Todo todo = new Todo(null, "New Todo", StatusType.BACKLOG, null, null);
        when(todoRepository.save(any(Todo.class))).thenReturn(new Todo(1L, "New Todo", StatusType.BACKLOG, LocalDateTime.now(), LocalDateTime.now()));

        Todo savedTodo = todoService.save(todo);
        assertNotNull(savedTodo.getId());
        assertEquals("New Todo", savedTodo.getDescription());
    }

    @Test
    void testDeleteById() {
        doNothing().when(todoRepository).deleteById(1L);
        assertDoesNotThrow(() -> todoService.deleteById(1L));
        verify(todoRepository, times(1)).deleteById(1L);
    }
}