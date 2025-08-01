package org.example.controllers;

import org.example.model.Task;
import org.example.model.User;
import org.example.repository.TaskRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/")
    public String index(Model model) {
        long userCount = userRepository.count();
        long taskCount = taskRepository.count();
        long completedTasks = taskRepository.findByCompleted(true).size();

        model.addAttribute("userCount", userCount);
        model.addAttribute("taskCount", taskCount);
        model.addAttribute("completedTasks", completedTasks);

        return "index";
    }

    @GetMapping("/task")
    public String task(Model model) {
        List<Task> tasks = taskRepository.findAllWithUsers();
        model.addAttribute("task", tasks);
        return "task";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users/list";
    }

    @GetMapping("/users/{id}")
    public String userDetail(@PathVariable Long id, Model model) {
        Optional<User> userOpt = userRepository.findByIdWithTasks(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            model.addAttribute("user", user);
            model.addAttribute("tasks", user.getTasks());
            return "users/detail";
        } else {
            model.addAttribute("error", "Utilisateur non trouvé");
            return "error";
        }
    }

    @GetMapping("/tasks")
    public String listTasks(Model model) {
        List<Task> tasks = taskRepository.findAllWithUsers();
        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }

    @GetMapping("/tasks/{id}")
    public String taskDetail(@PathVariable Long id, Model model) {
        Optional<Task> taskOpt = taskRepository.findByIdWithUser(id);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            model.addAttribute("task", task);
            return "tasks/detail";
        } else {
            model.addAttribute("error", "Tâche non trouvée");
            return "error";
        }
    }

    @GetMapping("/api/users")
    @ResponseBody
    public List<User> apiListUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/api/users/{id}")
    @ResponseBody
    public User apiUserDetail(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @GetMapping("/api/tasks")
    @ResponseBody
    public List<Task> apiListTasks() {
        return taskRepository.findAllWithUsers();
    }

    @GetMapping("/api/tasks/{id}")
    @ResponseBody
    public Task apiTaskDetail(@PathVariable Long id) {
        return taskRepository.findById(id).orElse(null);
    }
}