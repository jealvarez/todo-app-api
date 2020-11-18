package org.jealvarez.todoapp.api.services;

import org.jealvarez.todoapp.api.domain.Task;
import org.jealvarez.todoapp.api.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(final TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Optional<Task> findById(final long id) {
        return taskRepository.findById(id);
    }

    public Optional<Task> save(final Task task) {
        return taskRepository.save(task);
    }

    public Optional<Task> update(final Task task) {
        return taskRepository.update(task);
    }

    public void delete(final long id) {
        taskRepository.delete(id);
    }

}
