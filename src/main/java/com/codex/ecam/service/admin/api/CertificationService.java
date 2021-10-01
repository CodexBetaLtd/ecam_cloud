package com.codex.ecam.service.admin.api;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.admin.CertificationDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.CertificationResult;


public interface CertificationService {

	DataTablesOutput<CertificationDTO> findAll(FocusDataTablesInput input) throws Exception;

	CertificationDTO findById(Integer id) throws Exception;

	CertificationResult delete(Integer id);

	CertificationResult save(CertificationDTO certificationDTO) throws Exception;

	void saveAll(List<CertificationDTO> allDummyData);

	void deleteAll();

	List<CertificationDTO> findAll();

	CertificationResult deleteMultiple(Integer[] ids) throws Exception;

}
