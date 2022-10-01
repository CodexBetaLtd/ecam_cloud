package com.codex.ecam.dao.app;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.biz.business.BusinessApp;
import com.codex.ecam.model.biz.business.BusinessWiget;

@Repository
public interface BusinessAppDao extends JpaRepository<BusinessApp, Serializable> {

	@Query("select count(businessApp) > 0 from BusinessApp businessApp where businessApp.app.id =:appId and businessApp.business.id =:businessId")
	Boolean isAppInstalled( @Param("appId") Integer appId, @Param("businessId") Integer businessId);

	@Query("from BusinessApp businessApp where businessApp.app.id =:appId and businessApp.business.id =:businessId")
	BusinessApp findByAppIdAndBusinessId(@Param("appId") Integer appId, @Param("businessId") Integer businessId);
	
	@Query("from BusinessWiget wiget where wiget.businessApp.business.id =:businessId group by wiget.appWiget.widgets")
	List<BusinessWiget> findByWigetBusinessId(@Param("businessId") Integer businessId);

}
