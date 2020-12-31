package com.codex.ecam.controller.inventory.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.biz.part.PartDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.inventory.api.PartService;
import com.codex.ecam.util.FileDownloadUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(PartRestController.REQUEST_MAPPING_URL)
public class PartRestController {

	public static final String REQUEST_MAPPING_URL = "restapi/part";

	private final String PART_DEFAULT_IMAGE = "/resources/images/no_image.png";
	
	@Autowired
	private PartService partService;

	@RequestMapping(value = "/tabledata", method = RequestMethod.GET)
	public DataTablesOutput<PartDTO> parts(@Valid FocusDataTablesInput input) {
		try {
			return partService.findAll(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/spare-part", method = RequestMethod.GET)
	public DataTablesOutput<PartDTO> getAllSpareParts(@Valid FocusDataTablesInput input) {
		try {
			return partService.findAllSparePart(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }

    @RequestMapping(value = "/tabledata-by-business", method = RequestMethod.GET)
    public DataTablesOutput<PartDTO> getPartsByBusiness(@Valid FocusDataTablesInput input, @Valid Integer bizId) {
        try {
			return partService.getPartsByBusiness(input, bizId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} 
	
	@RequestMapping(value = "/part-image" , method = RequestMethod.GET)
	public @ResponseBody byte[] getPartImage( Integer id, HttpServletRequest request) throws IOException {
		try {
			return partService.getPartImageStream(id, request);
		} catch (Exception e) {
			return FileDownloadUtil.getByteInputStream( request.getServletContext().getRealPath("").concat(PART_DEFAULT_IMAGE));
		} 
	}

}
