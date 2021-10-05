package com.codex.ecam.service.report.mrn.impl;

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
import com.codex.ecam.dao.inventory.AODDao;
import com.codex.ecam.dao.inventory.mrn.MRNDao;
import com.codex.ecam.dto.report.data.aod.AODRepDTO;
import com.codex.ecam.dto.report.data.aod.AODRepItemDataDTO;
import com.codex.ecam.dto.report.data.mrn.MRNRepDTO;
import com.codex.ecam.dto.report.data.mrn.MRNRepItemDTO;
import com.codex.ecam.dto.report.filter.AODFilterDTO;
import com.codex.ecam.dto.report.print.AODPrintDTO;
import com.codex.ecam.mappers.report.AODReportMapper;
import com.codex.ecam.model.inventory.aod.AOD;
import com.codex.ecam.model.inventory.aod.AODItem;
import com.codex.ecam.model.inventory.mrn.MRN;
import com.codex.ecam.model.inventory.mrn.MRNItem;
import com.codex.ecam.model.inventory.receiptOrder.ReceiptOrder;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.report.aod.api.AODReportService;
import com.codex.ecam.service.report.mrn.api.MRNReportService;
import com.codex.ecam.util.ReportUtil;
import com.codex.ecam.util.search.inventory.PartSearchPropertyMapper;

@Service("mrnReportService")
public class MRNReportServiceImpl implements MRNReportService {

	@Autowired
	private MRNDao mrnDao;

	@Override
	public DataTablesOutput<AODRepDTO> searchDetail(FocusDataTablesInput input, AODFilterDTO filter) throws Exception {
//		final Specification<AOD> specification = getDetailSpecification(filter);
//		PartSearchPropertyMapper.getInstance().generateDataTableInput(input);
//		final DataTablesOutput<AOD> domainOut = aodDao.findAll(input, specification);
//		final DataTablesOutput<AODRepDTO> out = AODReportMapper.getInstance().domainToRepDTODataTablesOutput(domainOut);

		//return out;
		return null;
	}

	private List<AODRepDTO> getReportDataList(AODFilterDTO filter) throws Exception {
//		final Specification<AOD> specification = getDetailSpecification(filter);
//		final List<AOD> list = aodDao.findAll(specification);
//		final List<AODRepDTO> out = AODReportMapper.getInstance().domainToRepDTOList(list);
//		return out;
		return null;

	}

	private MRNRepDTO getReportData(Integer id) throws Exception {
		final MRN domain = mrnDao.findOne(id);
		final MRNRepDTO repDTO = new MRNRepDTO();

		repDTO.setMrnNo(domain.getMrnNo());

		repDTO.setDate(domain.getDate());
		repDTO.setMrnStatus(domain.getMrnStatus().getName());

		repDTO.setMrnType(domain.getMrnType().getName());
		if (domain.getBusiness() != null) {
			repDTO.setBusinessName(domain.getBusiness().getName());
			repDTO.setBusinessAddress(domain.getBusiness().getAddress());
		}
		if (domain.getWorkOrder() != null) {
			repDTO.setWoNo(domain.getWorkOrder().getCode());
		}

		for (MRNItem mrnItem : domain.getMrnItems()) {
			MRNRepItemDTO mrnRepItemDTO = new MRNRepItemDTO();
			mrnRepItemDTO.setBatchNo(mrnItem.getBatchNo());
			mrnRepItemDTO.setItemQuantity(mrnItem.getQuantity().doubleValue());
			if (mrnItem.getRemainQuantity() != null) {
				mrnRepItemDTO.setItemReturnQuantity(mrnItem.getRemainQuantity().doubleValue());

			}
			mrnRepItemDTO.setDescription(mrnItem.getDescription());
			if (mrnItem.getPart() != null) {
				mrnRepItemDTO.setPartCode(mrnItem.getPart().getCode());
			}
			repDTO.getMrnRepItemDTOs().add(mrnRepItemDTO);
		}
		return repDTO;
	}

	private Specification<AOD> getDetailSpecification(final AODFilterDTO filter) {
		final Specification<AOD> specification = (root, query, cb) -> {
			query.orderBy(cb.asc(root.get("aodNo")));
			final List<Predicate> predicates = new ArrayList<>();
			if (filter.getAodStatus() != null) {
				predicates.add(cb.isNotNull(root.get("aodStatus")));
				predicates.add(cb.equal(root.get("aodStatus"), filter.getAodStatus()));
			}
			if (filter.getAodType() != null) {
				predicates.add(cb.isNotNull(root.get("aodType")));
				predicates.add(cb.equal(root.get("aodType"), filter.getAodType()));
			}
			if (filter.getFromDate() != null) {
				predicates.add(cb.between(root.get("date"), filter.getFromDate(), new Date()));
			}
			if (filter.getToDate() != null) {
				predicates.add(cb.between(root.get("date"), new Date(), filter.getToDate()));
			}
			if (filter.getRequestedByUserId() != null) {
				predicates.add(cb.equal(root.get("requestedBy").get("id"), filter.getRequestedByUserId()));
			}
			if (filter.getCustomerId() != null) {
				predicates.add(cb.equal(root.get("customer").get("id"), filter.getCustomerId()));
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};

		return specification;
	}

	private Predicate getUserPredicate(final Root<ReceiptOrder> root, final CriteriaBuilder cb,
			CriteriaQuery<?> query) {
		return null;
	}

	@Override
	public void printDoc(Integer id, HttpServletResponse response, HttpServletRequest request) throws Exception {
		InputStream jasperStream;
		OutputStream outputStream;
		MRNRepDTO mrnRepDTO = new MRNRepDTO();
		final Map<String, Object> params = new HashMap<String, Object>();
		jasperStream = ReportUtil.getInstance().getInputStream(request, "/resources/report/inventory/mrn/",
				"MRNView.jrxml");
		mrnRepDTO = getReportData(id);
		outputStream = ReportUtil.getInstance().getOutPutStram(response, mrnRepDTO.getMrnNo(),
				PrintType.PDF.getContentType(), PrintType.PDF.getExtention());
		ArrayList<MRNRepDTO> dataList =new ArrayList<>();
		dataList.add(mrnRepDTO);
		String reportDir=request.getRealPath("").concat("/resources/report/");
		params.put("SUBREPORT_DIR", reportDir);
		ReportUtil.getInstance().generatePDF(dataList, jasperStream, params, outputStream);

		outputStream.flush();
		outputStream.close();
	}

	public void print(AODFilterDTO filter, HttpServletResponse response, HttpServletRequest request, PrintType type)
			throws Exception {

		InputStream jasperStream;
		OutputStream outputStream;
		List<AODRepDTO> list = new ArrayList<>();
		final Map<String, Object> params = new HashMap<String, Object>();
		jasperStream = ReportUtil.getInstance().getInputStream(request, AODFilterDTO.getTemplatePath(),
				AODFilterDTO.getTemplateName());
		outputStream = ReportUtil.getInstance().getOutPutStram(response, AODFilterDTO.getReportName(),
				type.getContentType(), type.getExtention());
		list = getReportDataList(filter);

		if (type.equals(PrintType.CSV)) {
			ReportUtil.getInstance().generateCSV(new AODPrintDTO(list), jasperStream, params, outputStream);

		} else {
			ReportUtil.getInstance().generatePDF(new AODPrintDTO(list), jasperStream, params, outputStream);

		}

		outputStream.flush();
		outputStream.close();
	}

}
