package com.igorkucher.toDoApp.repository;

import com.igorkucher.toDoApp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
