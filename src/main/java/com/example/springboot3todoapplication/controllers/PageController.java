package com.example.springboot3todoapplication.controllers;

import java.time.Instant;
import java.time.ZoneId;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.springboot3todoapplication.models.StatusType;
import com.example.springboot3todoapplication.models.Todo;
import com.example.springboot3todoapplication.services.TodoService;


@Controller
public class PageController {

    private final TodoService todoService;

    public PageController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");

        modelAndView.addObject("backlog", todoService.getAllByStatus(StatusType.BACKLOG));
        modelAndView.addObject("doing", todoService.getAllByStatus(StatusType.DOING));
        modelAndView.addObject("done", todoService.getAllByStatus(StatusType.DONE));

        modelAndView.addObject("newTodo", new Todo());
        modelAndView.addObject("today", Instant.now().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfWeek());
        return modelAndView;
    }

    @GetMapping("/sobre")
    public ModelAndView sobre() {
        ModelAndView modelAndView = new ModelAndView("sobre");
        return modelAndView;
    }
}