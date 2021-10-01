package com.codex.ecam.service.inventory.api;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.constants.inventory.AODStatus;
import com.codex.ecam.dto.inventory.issuenote.IssueNoteDTO;
import com.codex.ecam.dto.inventory.issuenote.IssueNoteItemDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.inventory.IssueNoteResult;


public interface IssueNoteService {

	IssueNoteResult newIssueNote();

	IssueNoteResult save(IssueNoteDTO mrndto) throws Exception;

	IssueNoteResult update(IssueNoteDTO mrndto) throws Exception;

	IssueNoteResult delete(Integer id) throws Exception;

	IssueNoteResult findById(Integer id) throws Exception;

	IssueNoteResult statusChange(Integer id, AODStatus status);

	DataTablesOutput<IssueNoteDTO> findAll(FocusDataTablesInput input) throws Exception;

	DataTablesOutput<IssueNoteItemDTO> getIssuenoteItemDataTable(FocusDataTablesInput input);

	IssueNoteResult deleteMultiple(Integer[] ids) throws Exception;



}
