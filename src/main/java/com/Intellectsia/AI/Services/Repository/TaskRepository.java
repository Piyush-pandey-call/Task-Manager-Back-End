package com.Intellectsia.AI.Services.Repository;

import com.Intellectsia.AI.Services.Entity.Task;
import com.Intellectsia.AI.Services.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);
}
