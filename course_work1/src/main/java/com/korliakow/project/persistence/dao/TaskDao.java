package com.korliakow.project.persistence.dao;

import com.korliakow.project.persistence.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskDao extends JpaRepository<Task, Long> {

    List<Task> findByProjectId(Long projectId);

    @Query(
            value = """
                    SELECT SUM(CASE WHEN t.status = 'DONE' THEN 1 ELSE 0 END) / COUNT(t) * 100 
                      FROM Task t 
                     WHERE t.project_id = :projectId
                    """,
            nativeQuery = true
    )
    Double countProjectProgress(Long projectId);

}
