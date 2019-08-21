package com.neolith.focus.dao.admin;

import com.neolith.focus.model.admin.Certification;
import com.neolith.focus.repository.FocusDataTableRepository; 
import org.springframework.stereotype.Repository;

@Repository
public interface CertificationDao extends FocusDataTableRepository<Certification, Integer>{

	Certification findById(Integer id);

}
