package org.example.repository;

import org.example.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserId(Long userId);

    List<Task> findByCompleted(Boolean completed);

    List<Task> findByUserIdAndCompleted(Long userId, Boolean completed);

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.user WHERE t.id = :id")
    Optional<Task> findByIdWithUser(Long id);

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.user")
    List<Task> findAllWithUsers();
}