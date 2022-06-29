package com.codex.ecam.service.report.stockadjustment.impl;


import java.io.InputStream;
import java.io.OutputStream;
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

import com.codex.ecam.constants.util.PrintType;
import com.codex.ecam.dao.inventory.StockAdjustmentDao;
import com.codex.ecam.dto.report.data.StockAdjustmentRepDTO;
import com.codex.ecam.dto.report.filter.AODFilterDTO;
import com.codex.ecam.dto.report.filter.StockAdjustmentFilterDTO;
import com.codex.ecam.dto.report.print.StockAdjustmentPrintDTO;
import com.codex.ecam.mappers.report.StockAdjustmentReportMapper;
import com.codex.ecam.model.inventory.receiptOrder.ReceiptOrder;
import com.codex.ecam.model.inventory.stockAdjustment.StockAdjustment;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.report.stockadjustment.api.StockAdjustmentReportService;
import com.codex.ecam.util.ReportUtil;
import com.codex.ecam.util.search.inventory.PartSearchPropertyMapper;


@Service("adjustmentReportService")
public class StockAdjusmentReportServiceImpl implements StockAdjustmentReportService {
	
	@Autowired
	private StockAdjustmentDao stockAdjustmentDao;

	@Override
	public DataTablesOutput<StockAdjustmentRepDTO> searchDetail(FocusDataTablesInput input, StockAdjustmentFilterDTO filter) throws Exception {
		final Specification<StockAdjustment> specification = getDetailSpecification(filter);
		PartSearchPropertyMapper.getInstance().generateDataTableInput(input);
		final DataTablesOutput<StockAdjustment> domainOut = stockAdjustmentDao.findAll(input, specification);
		final DataTablesOutput<StockAdjustmentRepDTO> out = StockAdjustmentReportMapper.getInstance().domainToRepDTODataTablesOutput(domainOut);

		return out;
	}

	private List<StockAdjustmentRepDTO> getReportDataList(StockAdjustmentFilterDTO filter) throws Exception {
		final Specification<StockAdjustment> specification = getDetailSpecification(filter);
		final List<StockAdjustment> list = stockAdjustmentDao.findAll(specification);
		final List<StockAdjustmentRepDTO> out = StockAdjustmentReportMapper.getInstance().domainToRepDTOList(list);
		return out;
	}

	private Specification<StockAdjustment> getDetailSpecification(StockAdjustmentFilterDTO filter) {
		final Specification<StockAdjustment> specification = (root, query, cb) -> {
			query.orderBy(cb.asc(root.get("adjustmentCode")));
			final List<Predicate> predicates = new ArrayList<>();
			if (filter.getItemId() != null) {
				predicates.add(cb.equal(root.get("part").get("id"), filter.getItemId()));
			}
			if (filter.getStockId() != null) {
				predicates.add(cb.equal(root.get("stock").get("id"), filter.getStockId()));
			}
			if (filter.getFromDate() != null) {
				predicates.add(cb.between(root.get("date"), filter.getFromDate(),new Date()));
			}
			if (filter.getToDate() != null) {
				predicates.add(cb.between(root.get("date"),new Date(), filter.getToDate()));
			}
			if(filter.getAdjustmentStatus()!=null){
				predicates.add(cb.equal(root.get("adjustmentStatus"), filter.getAdjustmentStatus()));
			}
		
		return cb.and(predicates.toArray(new Predicate[0]));
		};

		return specification;
	}

	
	private Predicate getUserPredicate(final Root<ReceiptOrder> root, final CriteriaBuilder cb, CriteriaQuery<?> query) {
		return null;
	}

	public void print(StockAdjustmentFilterDTO filter, HttpServletResponse response, HttpServletRequest request,PrintType type) throws Exception {

		InputStream jasperStream;
		OutputStream outputStream;
		List<StockAdjustmentRepDTO> list = new ArrayList<>();
		final Map<String, Object> params = new HashMap<String, Object>();
			jasperStream = ReportUtil.getInstance().getInputStream(request,StockAdjustmentFilterDTO.getTemplatePath(), StockAdjustmentFilterDTO.getTemplateName());
			outputStream = ReportUtil.getInstance().getOutPutStram(response, StockAdjustmentFilterDTO.getReportName(), type.getContentType(), type.getExtention());
			list = getReportDataList(filter);
			
		if(type.equals(PrintType.CSV)){
			ReportUtil.getInstance().generateCSV(new StockAdjustmentPrintDTO(list), jasperStream, params, outputStream);

		}else{
			ReportUtil.getInstance().generatePDF(new StockAdjustmentPrintDTO(list), jasperStream, params, outputStream);

		}

		outputStream.flush();
		outputStream.close();
	}
	
}
