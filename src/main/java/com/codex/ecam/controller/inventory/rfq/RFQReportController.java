package com.codex.ecam.controller.inventory.rfq;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.controller.BaseReportController;
import com.codex.ecam.dto.inventory.rfq.RFQFilterDTO;
import com.codex.ecam.dto.inventory.rfq.RFQRepDTO;
import com.codex.ecam.service.inventory.api.RFQService;
import com.codex.ecam.service.report.BaseReportService;

@Controller
@RequestMapping(RFQReportController.REQUEST_MAPPING_URL)
public class RFQReportController extends BaseReportController<RFQRepDTO, RFQFilterDTO> {

    public static final String REQUEST_MAPPING_URL = "report/rfq";

    @Autowired
    private RFQService rfqService;

    @SuppressWarnings("rawtypes")
	@Autowired
    @Qualifier("rfqReportService")
    private BaseReportService baseReportService;


    /*********************************************************************
     * Report View
     *********************************************************************/
    @RequestMapping(value = "/rfqView", method = RequestMethod.GET)
    public void downloadPDFFile(HttpServletRequest request, Integer id, HttpServletResponse response,Model model, RedirectAttributes ra) throws Exception {
    	String inputFilePath = "/rfq/rfq_report";
    	try {
        	RFQRepDTO dto = rfqService.findRFQRepDTOById(id); 
            String fileName = dto.getCode();
            toPDF(dto, request, response, inputFilePath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    public void getPDF(List<RFQRepDTO> repDTOs, String reportDir, String inputFilePath, OutputStream outputStream) throws Exception {    
    	baseReportService.generatePDFReport(repDTOs, reportDir, inputFilePath, outputStream);
    }

    @SuppressWarnings("unchecked")
	@Override
    public void getCVS(List<RFQRepDTO> repDTOs, String reportDir, String inputFilePath, OutputStream outputStream) throws Exception {
        baseReportService.generateCSVReport(repDTOs, reportDir, inputFilePath, outputStream);
    }

	@Override
	public String generatePDF(RFQFilterDTO repFilterDTO, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateCSV(RFQFilterDTO repFilterDTO, Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
