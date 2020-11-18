package org.jealvarez.todoapp.api.controllers;

import org.jealvarez.todoapp.api.domain.Task;
import org.jealvarez.todoapp.api.services.TaskService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(final TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getTasks() {
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable("id") final long id) {
        return taskService.findById(id).orElseThrow(() -> new HttpClientErrorException(NOT_FOUND));
    }

    @PostMapping
    public Task save(@RequestBody final Task task) {
        return taskService.save(task).orElseThrow(() -> new HttpClientErrorException(BAD_REQUEST));
    }

    @PutMapping
    public Task update(@RequestBody final Task task) {
        return taskService.update(task).orElseThrow(() -> new HttpClientErrorException(BAD_REQUEST));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") final long id) {
        taskService.delete(id);
    }

}
