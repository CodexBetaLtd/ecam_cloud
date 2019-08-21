package com.neolith.focus.dao.maintenance;

import com.neolith.focus.model.maintenance.task.Task;
import com.neolith.focus.repository.FocusDataTableRepository; 

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface TaskDao extends FocusDataTableRepository<Task, Integer> {

    @Modifying
    @Transactional
    @Query("select t from Task t where taskGroup.id = :taskGroupId order by t.name asc")
    List<Task> findTaskByTaskGroup(@Param("taskGroupId") Integer taskGroupId);


}
