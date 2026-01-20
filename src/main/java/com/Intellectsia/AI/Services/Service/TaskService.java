package com.Intellectsia.AI.Services.Service;

import com.Intellectsia.AI.Services.Entity.Task;
import com.Intellectsia.AI.Services.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private UserService userService;

    public List<Task> getAllTasks() {
        return taskRepo.findByUser(userService.getLoggedInUser());
    }

    public Task createTask(Task task) {
        task.setUser(userService.getLoggedInUser());
        return taskRepo.save(task);
    }

    public Task updateTask(Long id, Task task) {
        Task existing = taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!existing.getUser().getId()
                .equals(userService.getLoggedInUser().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "You cannot update this task");
        }

        existing.setTitle(task.getTitle());
        existing.setDescription(task.getDescription());

        return taskRepo.save(existing);
    }

    public void deleteTask(Long id) {
        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId()
                .equals(userService.getLoggedInUser().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "You cannot delete this task");
        }

        taskRepo.delete(task);
    }
}

