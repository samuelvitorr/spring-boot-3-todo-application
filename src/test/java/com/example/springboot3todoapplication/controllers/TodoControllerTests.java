package com.example.springboot3todoapplication.controllers;

import java.util.Map;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.springboot3todoapplication.models.StatusType;
import com.example.springboot3todoapplication.models.Todo;
import com.example.springboot3todoapplication.services.TodoService;

class TodoControllerTests {

    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    public TodoControllerTests() {
        // Inicializa os mocks criados com as anotações @Mock e @InjectMocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEditTodoItem() {
        // Criação de uma tarefa simulada existente
        long id = 1L;
        var existingTodo = new Todo();
        existingTodo.setId(id);
        existingTodo.setDescription("Old Todo");
        existingTodo.setStatus(StatusType.BACKLOG);

        // Criação do resultado esperado após edição
        var updatedTodo = new Todo();
        updatedTodo.setId(id);
        updatedTodo.setDescription("Updated Todo");
        updatedTodo.setStatus(StatusType.BACKLOG);

        // Mocka o comportamento do serviço
        when(todoService.getById(id)).thenReturn(existingTodo);
        when(todoService.save(existingTodo)).thenReturn(updatedTodo);

        // Simula o corpo da requisição com nova descrição
        Map<String, String> body = Map.of("description", "Updated Todo");

        // Chama o método do controller
        Todo result = todoController.editDescription(id, body);

        // Valida se a descrição foi realmente atualizada
        assertEquals("Updated Todo", result.getDescription());
    }

    @Test
    void testDeleteTodoItem() {
        // Mocka o comportamento de exclusão
        doNothing().when(todoService).deleteById(1L);

        // Executa o método de exclusão
        ResponseEntity<Void> response = todoController.deleteTodoItem(1L);

        // Verifica se o status da resposta é 204 No Content
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
