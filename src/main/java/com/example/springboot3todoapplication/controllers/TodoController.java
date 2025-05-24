package com.example.springboot3todoapplication.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.springboot3todoapplication.models.StatusType;
import com.example.springboot3todoapplication.models.Todo;
import com.example.springboot3todoapplication.services.TodoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TodoController {

    // Injeção automática do serviço de tarefas
    final TodoService todoService;

    /**
     * ✅ Endpoint para AVANÇAR o status da tarefa:
     * BACKLOG → DOING → DONE.
     *
     * ⚠️ Este método é exclusivo para mudar o status da tarefa.
     * Não deve ser usado para editar a descrição!
     */
    @PutMapping("/todos/{id}")
    public Todo updateStatus(@PathVariable("id") long id) {
        Todo todo = todoService.getById(id);

        // Avança o status de acordo com o estado atual
        if (todo.getStatus() == StatusType.BACKLOG) {
            todo.setStatus(StatusType.DOING);
        } else if (todo.getStatus() == StatusType.DOING) {
            todo.setStatus(StatusType.DONE);
        }

        // Salva a alteração e retorna o objeto atualizado
        return todoService.save(todo);
    }

    /**
     * ✅ Endpoint EXCLUSIVO para editar a DESCRIÇÃO da tarefa.
     *
     * ⚠️ Garante que o status da tarefa NÃO será alterado aqui.
     *
     * @param id ID da tarefa a ser editada
     * @param body JSON contendo: { "description": "nova descrição" }
     */
    @PutMapping("/todos/edit/{id}")
    public Todo editDescription(@PathVariable("id") long id, @RequestBody Map<String, String> body) {
        Todo todo = todoService.getById(id);

        // Verifica se o corpo contém a nova descrição
        if (body.containsKey("description") && body.get("description") != null) {
            todo.setDescription(body.get("description")); // Apenas atualiza a descrição
        }

        // Salva a tarefa sem alterar o status
        return todoService.save(todo);
    }

    /**
     * ✅ Endpoint para deletar uma tarefa.
     *
     * @param id ID da tarefa a ser excluída
     * @return 204 No Content se sucesso
     */
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> deleteTodoItem(@PathVariable("id") long id) {
        todoService.deleteById(id);
        return ResponseEntity.noContent().build(); // Retorna HTTP 204
    }
}
