package com.codex.ecam.dao.maintenance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.maintenance.project.ProjectUser;

import javax.transaction.Transactional;

@Repository
public interface ProjectUserDao extends JpaRepository<ProjectUser, Integer> {

    @Modifying
    @Transactional
    @Query("delete from ProjectUser where project.id = :projectId")
    void deleteByProjectId(@Param("projectId") Integer projectId);

}
