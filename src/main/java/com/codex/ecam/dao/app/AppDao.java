package com.codex.ecam.dao.app;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.app.App;
import com.codex.ecam.model.app.AppWiget;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface AppDao extends FocusDataTableRepository<App, Integer> {

	@Query("from App")
	List<App> findAllApps();

	@Query("from AppWiget wiget where wiget.app.id =:appId")
	List<AppWiget> findAllWigetByApp(@Param("appId") Integer appId);
}
