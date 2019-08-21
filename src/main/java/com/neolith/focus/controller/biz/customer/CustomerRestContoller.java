package com.neolith.focus.controller.biz.customer;

import com.neolith.focus.dto.asset.CustomerDTO;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.service.asset.api.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(CustomerRestContoller.REQUEST_MAPPING_URL)
public class CustomerRestContoller {

	public static final String REQUEST_MAPPING_URL = "restapi/customer";

	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/tabledata", method = RequestMethod.GET)
	public DataTablesOutput<CustomerDTO> getAssetList(@Valid FocusDataTablesInput input) {
		try {
			return customerService.findAll(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    @RequestMapping(value = "/tabledata-by-business", method = {RequestMethod.GET})
    public DataTablesOutput<CustomerDTO> getUsersByBusinessId(@Valid FocusDataTablesInput dataTablesInput, Integer id) {
        try {
            return customerService.findAllByBusiness(dataTablesInput, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DataTablesOutput<CustomerDTO>();
    }

}
