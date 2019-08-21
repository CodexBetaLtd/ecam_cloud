package com.codex.ecam.service.biz.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.dto.biz.supplier.SupplierDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.biz.SupplierResult;

import java.util.List;

public interface SupplierService {

    SupplierResult newSupplier();

    SupplierResult findById(Integer id);

    SupplierResult save(SupplierDTO supplierDTO);

    SupplierResult update(SupplierDTO supplierDTO);

    SupplierResult delete(Integer id);

    DataTablesOutput<SupplierDTO> findAllByLevel(FocusDataTablesInput input) throws Exception;

    DataTablesOutput<SupplierDTO> findAllVirtualSupplier(FocusDataTablesInput input) throws Exception;
    
    DataTablesOutput<SupplierDTO> findAllOriginalSupplier(FocusDataTablesInput input) throws Exception;
    
    List<SupplierDTO> findAllVirtualSupplierList();

    List<SupplierDTO> findAllOriginalSupplierList();

}

