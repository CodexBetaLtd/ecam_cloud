package com.codex.ecam.dao.maintenance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.maintenance.CalendarEvent;

@Repository
public interface CalenderEventDao extends JpaRepository<CalendarEvent, Integer> {

}
