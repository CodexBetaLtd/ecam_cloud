package com.codex.ecam.service.report.stock.bincard.impl;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.codex.ecam.dao.inventory.StockHistoryDao;
import com.codex.ecam.dto.report.data.BinCardRepDTO;
import com.codex.ecam.dto.report.data.StockAgeRepDTO;
import com.codex.ecam.dto.report.filter.BinCardFilterDTO;
import com.codex.ecam.dto.report.filter.StockAgeFilterDTO;
import com.codex.ecam.dto.report.print.BinCardPrintDTO;
import com.codex.ecam.mappers.report.BinCardReportMapper;
import com.codex.ecam.model.inventory.receiptOrder.ReceiptOrder;
import com.codex.ecam.model.inventory.stock.StockHistory;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.report.stock.bincard.api.BinCardReportService;
import com.codex.ecam.util.ReportUtil;
import com.codex.ecam.util.search.inventory.PartSearchPropertyMapper;


@Service("binCardReportService")
public class BinCardReportServiceImpl implements BinCardReportService {
	
	@Autowired
	private StockHistoryDao stockHistoryDao;

	@Override
	public DataTablesOutput<BinCardRepDTO> searchDetail(FocusDataTablesInput input, BinCardFilterDTO filter) throws Exception {
		final Specification<StockHistory> specification = getDetailSpecification(filter);
		PartSearchPropertyMapper.getInstance().generateDataTableInput(input);
		final DataTablesOutput<StockHistory> domainOut = stockHistoryDao.findAll(input, specification);
		final DataTablesOutput<BinCardRepDTO> out = BinCardReportMapper.getInstance().domainToRepDTODataTablesOutput(domainOut);

		return out;
	}

	private List<BinCardRepDTO> getPartDetailReport(BinCardFilterDTO filter) throws Exception {
		final Specification<StockHistory> specification = getDetailSpecification(filter);
		final List<StockHistory> list = stockHistoryDao.findAll(specification);
		final List<BinCardRepDTO> out = BinCardReportMapper.getInstance().domainToRepDTOList(list);
		//setStockAgeDays(out,filter);
		return out;
	}
	


	private Specification<StockHistory> getDetailSpecification(BinCardFilterDTO filter) {
		final Specification<StockHistory> specification = (root, query, cb) -> {
			query.orderBy(cb.asc(root.get("stock").get("stockNo")));
			final List<Predicate> predicates = new ArrayList<>();
			if (filter.getStockNo() != null) {
				predicates.add(cb.equal(root.get("stock").get("stockNo"), filter.getStockNo()));
			}
			
			if (filter.getPartId() != null) {
				predicates.add(cb.equal(root.get("stock").get("part").get("id"), filter.getPartId()));
			}
		
		return cb.and(predicates.toArray(new Predicate[0]));
		};

		return specification;
	}
 private void setStockAgeDays(List<StockAgeRepDTO> list,StockAgeFilterDTO filter){
	 for(StockAgeRepDTO repDTO:list){
		 if(filter.getToDate()==null){
			 filter.setToDate(new Date()); 
		 }
		 if(repDTO.getStockAgeCreatedDate()!=null){
			 int days=setNoOfDays(repDTO.getStockAgeCreatedDate(),filter.getToDate());
			 repDTO.setStockAgeDaysAge(days);
			 repDTO.setStockAgeYears(BigDecimal.valueOf(days/365));
			 repDTO.setStockAgeMonths(BigDecimal.valueOf(days%12));
			 repDTO.setStockAgeDays(BigDecimal.valueOf((days%12)%30));
		 }

	 }
 }
	
 private Integer setNoOfDays(Date reportDate,Date stockDate){
	        long difference = Math.abs(reportDate.getTime() - stockDate.getTime());
	        long differenceDates = difference / (24 * 60 * 60 * 1000);

	        //Convert long to String
return (int) differenceDates;

 }
	private Predicate getUserPredicate(final Root<ReceiptOrder> root, final CriteriaBuilder cb, CriteriaQuery<?> query) {
		return null;
	}


	@Override
	public void print(BinCardFilterDTO filter, HttpServletResponse response, HttpServletRequest request, String type) throws Exception {

		if ("pdf".equals(type)) {
			generatePDF(filter, request, response);
		} else {
			generateCSV(filter, request, response);
		}

	}

	private void generateCSV(BinCardFilterDTO filter, HttpServletRequest request, HttpServletResponse response) throws Exception {

		InputStream jasperStream;
		OutputStream outputStream;
		List<BinCardRepDTO> aodRepDTOs = new ArrayList<>();
		final Map<String, Object> params = new HashMap<String, Object>();
			jasperStream = getInputStream(request, "binCardReport");
			outputStream = getOutPutStram(response, "bin-card-report", "text/csv", ".csv");
			aodRepDTOs = getPartDetailReport(filter);
		
		ReportUtil.getInstance().generateCSV(new BinCardPrintDTO(aodRepDTOs), jasperStream, params, outputStream);

		outputStream.flush();
		outputStream.close();
	}

	private void generatePDF(BinCardFilterDTO filter, HttpServletRequest request, HttpServletResponse response) throws Exception {

		InputStream jasperStream;
		OutputStream outputStream;
		List<BinCardRepDTO> parts = new ArrayList<>();
		final Map<String, Object> params = new HashMap<String, Object>();
			jasperStream = getInputStream(request, "binCardReport");
			outputStream = getOutPutStram(response, "bin-card-report", "application/pdf", ".pdf");
			parts = getPartDetailReport(filter);
		

		ReportUtil.getInstance().generatePDF(new BinCardPrintDTO( parts ), jasperStream, params, outputStream);

		outputStream.flush();
		outputStream.close();
	}

	private InputStream getInputStream(HttpServletRequest request, String jasperFileName) throws FileNotFoundException {
		final String filePath = request.getServletContext().getRealPath("").concat("/resources/report/inventory/stock/binCard/"+ jasperFileName +".jrxml");
		final InputStream jasperStream = new FileInputStream(filePath);
		return jasperStream;
	}

	private OutputStream getOutPutStram(HttpServletResponse response, String fileName, String contentType, String extension) throws IOException {
		response.setContentType(contentType);
		response.setHeader("Content-disposition", "attachment; filename=".concat( fileName ).concat(extension));

		final OutputStream outputStream = response.getOutputStream();
		return outputStream;
	}
	


}
