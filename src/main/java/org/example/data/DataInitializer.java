package org.example.data;

import org.example.model.Task;
import org.example.model.User;
import org.example.repository.TaskRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    private void initializeData() {

        User user1 = new User();
        User user2 = new User();
        User user3 = new User();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        Task task1 = new Task("task1", "Faire la tache 1", user1);
        Task task2 = new Task("task2", "Faire la tache 2", user1);
        task2.setCompleted(true);

        Task task3 = new Task("task3", "Faire la tache 3", user2);

        Task task4 = new Task("task4", "Faire la tache 4", user2);

        Task task5 = new Task("task5", "Faire la tache 5", user3);

        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);
        taskRepository.save(task4);
        taskRepository.save(task5);

        System.out.println("- " + userRepository.count() + " utilisateurs créés");
        System.out.println("- " + taskRepository.count() + " tâches créées");
    }
}