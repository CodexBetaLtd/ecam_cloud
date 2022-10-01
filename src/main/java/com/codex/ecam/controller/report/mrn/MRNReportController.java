package com.codex.ecam.controller.report.mrn;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codex.ecam.constants.inventory.AODStatus;
import com.codex.ecam.constants.inventory.AODType;
import com.codex.ecam.constants.util.PrintType;
import com.codex.ecam.dto.report.filter.AODFilterDTO;
import com.codex.ecam.service.report.mrn.api.MRNReportService;


@Controller
@RequestMapping(MRNReportController.REQUEST_MAPPING_URL)
public class MRNReportController {
	public static final String REQUEST_MAPPING_URL = "report/mrn";
	
	@Autowired
	private MRNReportService mrnReportService;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		setCommonData(model);
		return "report/aod/report-view";
	}
	

	@RequestMapping( value = "/print", method = RequestMethod.POST,params = "printPDF")
	public void printPDF(@Valid AODFilterDTO filterDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		mrnReportService.print(filterDTO, response, request, PrintType.PDF);
	}
	@RequestMapping( value = "/print", method = RequestMethod.POST,params = "printCSV")
	public void printCSV(@Valid AODFilterDTO filterDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		mrnReportService.print(filterDTO, response, request, PrintType.CSV);
	}

	@RequestMapping( value = "/printMrn", method = RequestMethod.GET)
	public void printPDF(@Valid Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		mrnReportService.printDoc(id, response, request);
	}
	
	private void setCommonData(Model model) {
		model.addAttribute("filterDTO", new AODFilterDTO());
		model.addAttribute("aodTypes", AODType.getAllAODTypes());
		model.addAttribute("aodStatusList", AODStatus.getAODStatusList());
	}
}
