package com.codex.ecam.service.inventory.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.multipart.MultipartFile;

import com.codex.ecam.dto.biz.part.PartDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.inventory.PartResult;

public interface PartService {

	PartDTO findById(Integer id);

	PartResult findByIdWithOpenPOs(Integer id);

	PartResult delete(Integer id);

	PartResult save(PartDTO part, MultipartFile image) throws Exception;

	List<PartDTO> findAll() throws Exception;

	byte[] getPartImageStream(Integer id, HttpServletRequest request) throws IOException;

	DataTablesOutput<PartDTO> findAll(FocusDataTablesInput input) throws Exception;
	DataTablesOutput<PartDTO> findAllSparePart(FocusDataTablesInput input) throws Exception;

	DataTablesOutput<PartDTO> getPartsByBusiness(FocusDataTablesInput input, Integer bizId) throws Exception;

}
