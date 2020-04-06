package com.codex.ecam.service.report.aodreturn.impl;


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
import com.codex.ecam.dao.inventory.AODReturnDao;
import com.codex.ecam.dto.report.data.AODReturnRepDTO;
import com.codex.ecam.dto.report.filter.AODReturnFilterDTO;
import com.codex.ecam.dto.report.print.AODReturnPrintDTO;
import com.codex.ecam.mappers.report.AODRetrunReportMapper;
import com.codex.ecam.model.inventory.aodRetun.AODReturn;
import com.codex.ecam.model.inventory.receiptOrder.ReceiptOrder;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.report.aodreturn.api.AODReturnReportService;
import com.codex.ecam.util.ReportUtil;
import com.codex.ecam.util.search.inventory.PartSearchPropertyMapper;


@Service("aodReturnReportService")
public class AODReturnReportServiceImpl implements AODReturnReportService {
	
	@Autowired
	private AODReturnDao aodReturnDao;

	@Override
	public DataTablesOutput<AODReturnRepDTO> searchDetail(FocusDataTablesInput input, AODReturnFilterDTO filter) throws Exception {
		final Specification<AODReturn> specification = getDetailSpecification(filter);
		PartSearchPropertyMapper.getInstance().generateDataTableInput(input);

		final DataTablesOutput<AODReturn> domainOut = aodReturnDao.findAll(input, specification);
		final DataTablesOutput<AODReturnRepDTO> out = AODRetrunReportMapper.getInstance().domainToRepDTODataTablesOutput(domainOut);

		return out;
	}

	private List<AODReturnRepDTO> getReportDataList(AODReturnFilterDTO filter) throws Exception {
		final Specification<AODReturn> specification = getDetailSpecification(filter);
		final List<AODReturn> list = aodReturnDao.findAll(specification);
		final List<AODReturnRepDTO> out = AODRetrunReportMapper.getInstance().domainToRepDTOList(list);
		return out;
	}

	private Specification<AODReturn> getDetailSpecification(AODReturnFilterDTO filter) {
		final Specification<AODReturn> specification = (root, query, cb) -> {
			query.orderBy(cb.asc(root.get("returnNo")));
			final List<Predicate> predicates = new ArrayList<>();
			if (filter.getAodReturnStatus() != null) {
				predicates.add(cb.isNotNull(root.get("aodReturnStatus")));
				predicates.add(cb.equal(root.get("aodReturnStatus"), filter.getAodReturnStatus()));
			}

			if (filter.getFromDate() != null) {
				predicates.add(cb.between(root.get("returnDate"), filter.getFromDate(),new Date()));
			}
			if (filter.getToDate() != null) {
				predicates.add(cb.between(root.get("returnDate"),new Date(), filter.getToDate()));
			}
			if (filter.getAodId() != null) {
				predicates.add(cb.equal(root.get("aod").get("id"),filter.getAodId()));
			}


		
		return cb.and(predicates.toArray(new Predicate[0]));
		};

		return specification;
	}

	
	private Predicate getUserPredicate(final Root<ReceiptOrder> root, final CriteriaBuilder cb, CriteriaQuery<?> query) {
		return null;
	}


	@Override
	public void print(AODReturnFilterDTO filter, HttpServletResponse response, HttpServletRequest request, PrintType type) throws Exception {

		InputStream jasperStream;
		OutputStream outputStream;
		List<AODReturnRepDTO> list = new ArrayList<>();
		final Map<String, Object> params = new HashMap<String, Object>();
			jasperStream = ReportUtil.getInstance().getInputStream(request,AODReturnFilterDTO.getTemplatePath(), AODReturnFilterDTO.getTemplateName());
			outputStream = ReportUtil.getInstance().getOutPutStram(response, AODReturnFilterDTO.getReportName(), type.getContentType(), type.getExtention());
			list = getReportDataList(filter);
		if(type.equals(PrintType.CSV)){
			ReportUtil.getInstance().generateCSV(new AODReturnPrintDTO(list), jasperStream, params, outputStream);

		}else{
			ReportUtil.getInstance().generatePDF(new AODReturnPrintDTO(list), jasperStream, params, outputStream);

		}

		outputStream.flush();
		outputStream.close();

	}
	
}
