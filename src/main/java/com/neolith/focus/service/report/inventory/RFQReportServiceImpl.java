package com.neolith.focus.service.report.inventory;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neolith.focus.dto.inventory.rfq.RFQFilterDTO;
import com.neolith.focus.dto.inventory.rfq.RFQRepDTO;
import com.neolith.focus.params.VelocityMail;
import com.neolith.focus.service.inventory.api.RFQService;
import com.neolith.focus.service.report.BaseReportService;
import com.neolith.focus.util.ReportUtil;
import com.neolith.focus.util.VelocityEmailSender;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service("rfqReportService")
public class RFQReportServiceImpl implements BaseReportService<RFQRepDTO, RFQFilterDTO> {

    @Autowired
    private RFQService rfqService;
    
	@Autowired
	private VelocityEmailSender velocityEmailService;

    @Override
    public RFQRepDTO findReport(Integer id) {
        try {
            return rfqService.findRFQRepDTOById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new RFQRepDTO();
        }
    }

    @Override
    public List<RFQRepDTO> findReport(RFQFilterDTO filterDTO) {
        try {
            //return rfqService.findAll(filterDTO);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void generatePDFReport(List<RFQRepDTO> dtos, String reportDir, String inputFilePath, OutputStream outputStream) {
        Map<String, Object> params = new HashMap<String, Object>();
       DataSource file= getPDFAttachment(ReportUtil.getInstance().generatePDF(dtos, reportDir, params, inputFilePath, outputStream));
       sendRFQToSupplier(file,dtos);
       
    }
    
	private void sendRFQToSupplier(DataSource file,List<RFQRepDTO> dtos) {
		VelocityMail velocityMail = new VelocityMail();	
		RFQRepDTO repDTO=dtos.get(0);
		velocityMail.setAttachmentFile(file);
		velocityMail.setFileName(repDTO.getCode());
		velocityMail.setSubject("Request for quotation");		
		//velocityMail.setTo(repDTO.getSupplierEmail());
		velocityMail.setTo("wasanthabr93@gmail.com");
		velocityMail.setVmTemplate("rfqdetails");
		velocityEmailService.sendEmail(velocityMail);

	}
	
	private DataSource getPDFAttachment(JasperPrint jasperPrint){      
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    try {
			JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
		} catch (JRException e) {
			e.printStackTrace();
		}
	    DataSource aAttachment =  new ByteArrayDataSource(baos.toByteArray(), "application/pdf");
		return aAttachment;
	}

    @Override
    public void generateCSVReport(List<RFQRepDTO> dtos, String reportDir, String inputFilePath, OutputStream outputStream) {
        Map<String, Object> params = new HashMap<String, Object>();
        ReportUtil.getInstance().generateCSV(dtos, reportDir, params, inputFilePath, outputStream);
    }


}
