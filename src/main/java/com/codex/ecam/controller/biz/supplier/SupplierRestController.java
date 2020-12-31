package com.codex.ecam.controller.biz.supplier;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.biz.supplier.SupplierDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.biz.api.SupplierService;

import javax.validation.Valid;

@RestController
@RequestMapping(SupplierRestController.REQUEST_MAPPING_URL)
public class SupplierRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/supplier";

	@Autowired
    private SupplierService supplierService;

	@RequestMapping(value = "/tabledata", method = RequestMethod.GET)
    public DataTablesOutput<SupplierDTO> findAllSuppliers(@Valid FocusDataTablesInput input) {
        try {
            return supplierService.findAllByLevel(input);
        } catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/virtualSupplierList", method = RequestMethod.GET)
    public DataTablesOutput<SupplierDTO> findAllVirtualSuppliers(@Valid FocusDataTablesInput input) {
        try {
            return supplierService.findAllVirtualSupplier(input);
        } catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/originalSupplierList", method = RequestMethod.GET)
    public DataTablesOutput<SupplierDTO> findAllOriginalSuppliers(@Valid FocusDataTablesInput input) {
        try {
            return supplierService.findAllOriginalSupplier(input);
        } catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
