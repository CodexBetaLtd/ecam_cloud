package com.neolith.focus.service.admin.api;

import java.util.List; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.neolith.focus.dto.admin.ChargeDepartmentDTO;
import com.neolith.focus.model.maintenance.ChargeDepartment;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.admin.ChargeDepartmentResult;

public interface ChargeDepartmentService {

	DataTablesOutput<ChargeDepartmentDTO> findAll(FocusDataTablesInput input) throws Exception;

	ChargeDepartmentDTO findById(Integer id) throws Exception;

	ChargeDepartmentResult delete(Integer id);

	ChargeDepartmentResult save(ChargeDepartmentDTO chargeDepartmentDTO) throws Exception; 

	void saveAll(List<ChargeDepartmentDTO> list);

	void deleteAll();

	ChargeDepartment findEntityById(Integer id);

	List<ChargeDepartmentDTO> findAll();

	List<ChargeDepartmentDTO> findAllByBusiness(Integer id);

}
