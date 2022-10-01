package com.codex.ecam.service.inventory.api;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.constants.inventory.MRNStatus;
import com.codex.ecam.dto.inventory.mrn.MRNDTO;
import com.codex.ecam.dto.inventory.mrn.MRNItemDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.inventory.MRNResult;


public interface MRNService {

	MRNResult newMRN();

	String getNextCode(Integer businessId);

	MRNResult save(MRNDTO mrndto) throws Exception;

	MRNResult update(MRNDTO mrndto) throws Exception;

	MRNResult delete(Integer id) throws Exception;

	MRNResult findById(Integer id) throws Exception;

	MRNResult statusChange(Integer id, MRNStatus status);

	List<MRNDTO> findAll();

	DataTablesOutput<MRNDTO> findAll(FocusDataTablesInput input) throws Exception;

	DataTablesOutput<MRNDTO> findAllApprovedMRN(FocusDataTablesInput input) throws Exception;

	DataTablesOutput<MRNItemDTO> getMRNItemDataTable(FocusDataTablesInput input, Integer mrnId);

	MRNResult deleteMultiple(Integer[] ids) throws Exception;

	//AODResult statusChange(Integer id, MRNStatus mrnStatus);

}
