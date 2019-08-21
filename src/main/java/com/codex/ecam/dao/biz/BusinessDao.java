package com.codex.ecam.dao.biz;

import java.util.List;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.biz.business.Business;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface BusinessDao extends FocusDataTableRepository<Business, Integer> {

	Business findById(Integer id);

	/*
	@Query("select biz from Business biz where biz.businessType = :businessType")
	List<Business> findByType(@Param("businessType") BusinessType businessType);
	 */

	@Query("select biz from Business biz where biz.virtualBusiness = true ")
	List<Business> findVirtualBiz();

	@Query("select biz from Business biz where biz.virtualBusiness is null or biz.virtualBusiness = false ")
	List<Business> findOriginalBiz();

}
