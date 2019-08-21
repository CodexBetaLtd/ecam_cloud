package com.neolith.focus.service.admin.api;

import java.util.List;
 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.neolith.focus.dto.admin.CertificationDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.admin.CertificationResult;


public interface CertificationService {

	DataTablesOutput<CertificationDTO> findAll(FocusDataTablesInput input) throws Exception;

	CertificationDTO findById(Integer id) throws Exception;

	CertificationResult delete(Integer id);

	CertificationResult save(CertificationDTO certificationDTO) throws Exception;

	void saveAll(List<CertificationDTO> allDummyData);

	void deleteAll();

	List<CertificationDTO> findAll();

}
