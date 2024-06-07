package com.korliakow.project.persistence.dao;

import com.korliakow.project.persistence.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectDao extends JpaRepository<Project, Long> {

}
