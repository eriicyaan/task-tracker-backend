package com.repository;

import com.entity.Task;
import com.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    @Query("from Task t where t.user.id = :id")
    List<Task> findAllByUserId(UUID id);

    Optional<Task> findById(UUID id);

    void deleteById(UUID id);
}
