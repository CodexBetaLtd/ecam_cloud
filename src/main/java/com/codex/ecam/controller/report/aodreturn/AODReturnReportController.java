package com.codex.ecam.controller.report.aodreturn;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codex.ecam.constants.inventory.AODReturnStatus;
import com.codex.ecam.constants.util.PrintType;
import com.codex.ecam.dto.report.filter.AODReturnFilterDTO;
import com.codex.ecam.service.report.aodreturn.api.AODReturnReportService;


@Controller
@RequestMapping(AODReturnReportController.REQUEST_MAPPING_URL)
public class AODReturnReportController {
	public static final String REQUEST_MAPPING_URL = "report/aodreturn";
	
	@Autowired
	private AODReturnReportService aodReturnReportService;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		setCommonData(model);
		return "report/aodReturn/report-view";
	}
	@RequestMapping(value = "/aodView", method = RequestMethod.GET)
	public String viewAod(Model model) {
		return "report/modal/tbl-modal-aod";
	}

	@RequestMapping( value = "/print", method = RequestMethod.POST,params = "printPDF")
	public void printPDF(@Valid AODReturnFilterDTO filterDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		aodReturnReportService.print(filterDTO, response, request,PrintType.PDF);
	}
	
	@RequestMapping( value = "/print", method = RequestMethod.POST,params = "printCSV")
	public void printCSV(@Valid AODReturnFilterDTO filterDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		aodReturnReportService.print(filterDTO, response, request, PrintType.CSV);
	}
	
	@RequestMapping( value = "/printAodReturn", method = RequestMethod.GET)
	public void printPDF(@Valid Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		aodReturnReportService.printDoc(id, response, request);
	}
	private void setCommonData(Model model) {
		model.addAttribute("filterDTO", new AODReturnFilterDTO());
		model.addAttribute("aodReturnStatusList", AODReturnStatus.getAODReturnStatus());
	}
}
