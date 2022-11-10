package com.igorkucher.toDoApp.repository;

import com.igorkucher.toDoApp.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StateRepository extends JpaRepository<State, Long> {

    @Query(value = "select * from states where name = ?1", nativeQuery = true)
    State getByName(String name);

    @Query(value = "select * from states order by id", nativeQuery = true)
    List<State> getAll();
}
