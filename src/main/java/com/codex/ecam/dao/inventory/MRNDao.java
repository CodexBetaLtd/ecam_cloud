package com.codex.ecam.dao.inventory;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codex.ecam.model.inventory.mrn.MRN;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface MRNDao extends FocusDataTableRepository<MRN, Integer> {

	@Query("select mrn from MRN mrn where mrn.id=(select max(id) from MRN where business.id=:businessId and year(createdDate)=year(sysdate()))")
	MRN findLastDomainByBusiness(@Param("businessId") Integer businessId);
}
