package com.codex.ecam.dao.admin;

import org.springframework.stereotype.Repository;

import com.codex.ecam.model.admin.Certification;
import com.codex.ecam.repository.FocusDataTableRepository;

@Repository
public interface CertificationDao extends FocusDataTableRepository<Certification, Integer>{

	Certification findById(Integer id);

}
