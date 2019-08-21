package com.neolith.focus.service.biz.api;

import com.neolith.focus.dto.biz.supplier.SupplierDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.biz.SupplierResult;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

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

