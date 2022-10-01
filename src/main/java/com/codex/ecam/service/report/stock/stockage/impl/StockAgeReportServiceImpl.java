package com.codex.ecam.service.report.stock.stockage.impl;


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

import com.codex.ecam.dao.inventory.StockDao;
import com.codex.ecam.dto.report.data.StockAgeRepDTO;
import com.codex.ecam.dto.report.filter.StockAgeFilterDTO;
import com.codex.ecam.dto.report.print.StockAgePrintDTO;
import com.codex.ecam.mappers.report.StockAgeReportMapper;
import com.codex.ecam.model.inventory.receiptOrder.ReceiptOrder;
import com.codex.ecam.model.inventory.stock.Stock;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.report.stock.stockage.api.StockAgeReportService;
import com.codex.ecam.util.ReportUtil;
import com.codex.ecam.util.search.inventory.PartSearchPropertyMapper;
import com.codex.ecam.util.search.stockage.StockAgePropertyMapper;


@Service("stockAgeReportService")
public class StockAgeReportServiceImpl implements StockAgeReportService {
	
	@Autowired
	private StockDao stockDao;

	@Override
	public DataTablesOutput<StockAgeRepDTO> searchDetail(FocusDataTablesInput input, StockAgeFilterDTO filter) throws Exception {
		final Specification<Stock> specification = getDetailSpecification(filter);
		StockAgePropertyMapper.getInstance().generateDataTableInput(input);
		final DataTablesOutput<Stock> domainOut = stockDao.findAll(input, specification);
		final DataTablesOutput<StockAgeRepDTO> out = StockAgeReportMapper.getInstance().domainToRepDTODataTablesOutput(domainOut);
		//setStockAgeDays(out.getData(),filter);

		return out;
	}

	private List<StockAgeRepDTO> getPartDetailReport(StockAgeFilterDTO filter) throws Exception {
		final Specification<Stock> specification = getDetailSpecification(filter);
		final List<Stock> list = stockDao.findAll(specification);
		final List<StockAgeRepDTO> out = StockAgeReportMapper.getInstance().domainToRepDTOList(list);
		//setStockAgeDays(out,filter);
		return out;
	}
	


	private Specification<Stock> getDetailSpecification(StockAgeFilterDTO filter) {
		final Specification<Stock> specification = (root, query, cb) -> {
			query.orderBy(cb.asc(root.get("stockNo")));
			final List<Predicate> predicates = new ArrayList<>();
			if (filter.getStockCode() != null) {
				predicates.add(cb.equal(root.get("part").get("id"), filter.getItemId()));
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
	public void print(StockAgeFilterDTO filter, HttpServletResponse response, HttpServletRequest request, String type) throws Exception {

		if ("pdf".equals(type)) {
			generatePDF(filter, request, response);
		} else {
			generateCSV(filter, request, response);
		}

	}

	private void generateCSV(StockAgeFilterDTO filter, HttpServletRequest request, HttpServletResponse response) throws Exception {

		InputStream jasperStream;
		OutputStream outputStream;
		List<StockAgeRepDTO> aodRepDTOs = new ArrayList<>();
		final Map<String, Object> params = new HashMap<String, Object>();
			jasperStream = getInputStream(request, "StockAge");
			outputStream = getOutPutStram(response, "stock-age-report", "text/csv", ".csv");
			aodRepDTOs = getPartDetailReport(filter);
		
		ReportUtil.getInstance().generateCSV(new StockAgePrintDTO(aodRepDTOs), jasperStream, params, outputStream);

		outputStream.flush();
		outputStream.close();
	}

	private void generatePDF(StockAgeFilterDTO filter, HttpServletRequest request, HttpServletResponse response) throws Exception {

		InputStream jasperStream;
		OutputStream outputStream;
		List<StockAgeRepDTO> parts = new ArrayList<>();
		final Map<String, Object> params = new HashMap<String, Object>();
			jasperStream = getInputStream(request, "StockAge");
			outputStream = getOutPutStram(response, "stock-age-report", "application/pdf", ".pdf");
			parts = getPartDetailReport(filter);
		

		ReportUtil.getInstance().generatePDF(new StockAgePrintDTO( parts ), jasperStream, params, outputStream);

		outputStream.flush();
		outputStream.close();
	}

	private InputStream getInputStream(HttpServletRequest request, String jasperFileName) throws FileNotFoundException {
		final String filePath = request.getServletContext().getRealPath("").concat("/resources/report/inventory/stock/stockAge/"+ jasperFileName +".jrxml");
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
