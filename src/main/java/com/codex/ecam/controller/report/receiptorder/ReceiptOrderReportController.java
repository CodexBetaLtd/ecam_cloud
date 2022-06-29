package com.codex.ecam.controller.report.receiptorder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codex.ecam.constants.inventory.ReceiptOrderStatus;
import com.codex.ecam.constants.inventory.ReceiptOrderType;
import com.codex.ecam.constants.util.PrintType;
import com.codex.ecam.dto.report.filter.ReceiptOrderFilterDTO;
import com.codex.ecam.service.report.receiptorder.api.ReceiptOrderReportService;


@Controller
@RequestMapping(ReceiptOrderReportController.REQUEST_MAPPING_URL)
public class ReceiptOrderReportController {
	public static final String REQUEST_MAPPING_URL = "report/receiptorder";
	
	@Autowired
	private ReceiptOrderReportService service;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		setCommonData(model);
		return "report/receiptorder/report-view";
	}


	@RequestMapping( value = "/print", method = RequestMethod.POST,params = "printPDF")
	public void printPDF(@Valid ReceiptOrderFilterDTO filterDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		service.print(filterDTO, response, request, PrintType.PDF);
	}
	
	@RequestMapping( value = "/print", method = RequestMethod.POST,params = "printCSV")
	public void printCSV(@Valid ReceiptOrderFilterDTO filterDTO, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		service.print(filterDTO, response, request, PrintType.CSV);
	}
	
	@RequestMapping( value = "/printRO", method = RequestMethod.GET)
	public void printPDF(@Valid Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		service.printDoc(id, response, request);
	}
	


	private void setCommonData(Model model) {
		model.addAttribute("filterDTO", new ReceiptOrderFilterDTO());
		model.addAttribute("types", ReceiptOrderType.getAllReceiptOrderType());
        model.addAttribute("grnStatusList", ReceiptOrderStatus.getAllStatus());
	}
}
