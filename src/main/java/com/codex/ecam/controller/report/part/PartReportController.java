package com.codex.ecam.controller.report.part;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codex.ecam.constants.inventory.PartType;
import com.codex.ecam.constants.util.PrintType;
import com.codex.ecam.dto.report.filter.PartFilterDTO;
import com.codex.ecam.service.report.part.api.PartReportService;


@Controller
@RequestMapping(PartReportController.REQUEST_MAPPING_URL)
public class PartReportController {
	public static final String REQUEST_MAPPING_URL = "report/part";
	
	@Autowired
	private PartReportService partService;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		setCommonData(model);
		return "report/part/report-view";
	}

	@RequestMapping( value = "/print", method = RequestMethod.POST,params = "printPDF")
	public void printPDF(@Valid PartFilterDTO filterDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		partService.print(filterDTO, response, request, PrintType.PDF);
	}
	
	@RequestMapping( value = "/print", method = RequestMethod.POST,params = "printCSV")
	public void printCSV(@Valid PartFilterDTO filterDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		partService.print(filterDTO, response, request, PrintType.CSV);
	}

	private void setCommonData(Model model) {
		model.addAttribute("filterDTO", new PartFilterDTO());
		model.addAttribute("types", PartType.getPartTypes());
	}
}
