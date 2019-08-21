package com.neolith.focus.dao.app;

import java.util.List;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.neolith.focus.model.app.App;
import com.neolith.focus.repository.FocusDataTableRepository;

@Repository
public interface AppDao extends FocusDataTableRepository<App, Integer> {

	@Query("from App")
	List<App> findAllApps();

}
