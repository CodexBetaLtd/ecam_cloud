package com.neolith.focus.dao.maintenance;

import com.neolith.focus.model.maintenance.CalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalenderEventDao extends JpaRepository<CalendarEvent, Integer> {

}
