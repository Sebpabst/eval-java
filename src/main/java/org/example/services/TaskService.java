package org.example.services;

import org.example.model.Task;
import org.example.model.User;
import org.example.repository.TaskRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public Task createTask(Task task, Long userId) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }
        task.setUser(userOpt.get());
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAllWithUsers();
    }

    public Task getTaskById(Long id) {
        Optional<Task> taskOpt = taskRepository.findByIdWithUser(id);
        return taskOpt.orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found"));
    }

    public Task updateTask(Long id, Task task, Long userId) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        Optional<Task> existingTaskOpt = taskRepository.findById(id);
        if (existingTaskOpt.isEmpty()) {
            throw new IllegalArgumentException("Task with ID " + id + " not found");
        }
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }
        Task existingTask = existingTaskOpt.get();
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setCompleted(task.getCompleted());
        existingTask.setUser(userOpt.get());
        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("Task with ID " + id + " not found");
        }
        taskRepository.deleteById(id);
    }
}