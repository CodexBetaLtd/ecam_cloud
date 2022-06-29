package com.codex.ecam.service.report.receiptorder.impl;


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

import com.codex.ecam.constants.TAXType;
import com.codex.ecam.constants.util.PrintType;
import com.codex.ecam.dao.inventory.ReceiptOrderDao;

import com.codex.ecam.dto.report.data.purchaseorder.PurchaseorderRepDTO;
import com.codex.ecam.dto.report.data.purchaseorder.PurchaseorderRepItemDTO;
import com.codex.ecam.dto.report.data.purchaseorder.PurchaseorderRepTaxDTO;
import com.codex.ecam.dto.report.data.receiptOrder.ReceiptOrderItemRepDTO;
import com.codex.ecam.dto.report.data.receiptOrder.ReceiptOrderRepDTO;
import com.codex.ecam.dto.report.filter.ReceiptOrderFilterDTO;
import com.codex.ecam.dto.report.print.ReceiptOrderPrintDTO;
import com.codex.ecam.mappers.report.ReceiptOrderReportMapper;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrder;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderItem;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderTax;
import com.codex.ecam.model.inventory.receiptOrder.ReceiptOrder;
import com.codex.ecam.model.inventory.receiptOrder.ReceiptOrderItem;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.report.receiptorder.api.ReceiptOrderReportService;
import com.codex.ecam.util.CommonUtil;
import com.codex.ecam.util.ReportUtil;
import com.codex.ecam.util.search.inventory.PartSearchPropertyMapper;


@Service("receiptorderReportService")
public class ReceiptOrderReportServiceImpl implements ReceiptOrderReportService {
	@Autowired
	private ReceiptOrderDao receiptOrderDao;

	@Override
	public DataTablesOutput<ReceiptOrderRepDTO> searchDetail(FocusDataTablesInput input, ReceiptOrderFilterDTO filter) throws Exception {
		final Specification<ReceiptOrder> specification = getDetailSpecification(filter);
		PartSearchPropertyMapper.getInstance().generateDataTableInput(input);
		final DataTablesOutput<ReceiptOrder> domainOut = receiptOrderDao.findAll(input, specification);
		final DataTablesOutput<ReceiptOrderRepDTO> out = ReceiptOrderReportMapper.getInstance().domainToRepDTODataTablesOutput(domainOut);

		return out;
	}

	private List<ReceiptOrderRepDTO> getReportDataList(ReceiptOrderFilterDTO filter) throws Exception {
		final Specification<ReceiptOrder> specification = getDetailSpecification(filter);
		final List<ReceiptOrder> receiptOrderList = receiptOrderDao.findAll(specification);
		final List<ReceiptOrderRepDTO> out = ReceiptOrderReportMapper.getInstance().domainToRepDTOList(receiptOrderList);
		return out;
	}

	private Specification<ReceiptOrder> getDetailSpecification(ReceiptOrderFilterDTO filter) {
		final Specification<ReceiptOrder> specification = (root, query, cb) -> {
			query.orderBy(cb.asc(root.get("code")));
			final List<Predicate> predicates = new ArrayList<>();
			if (filter.getGrnType() != null) {
				predicates.add(cb.equal(root.get("receiptOrderType"), filter.getGrnType()));
			}
			if (filter.getGrnStatus() != null) {
				predicates.add(cb.equal(root.get("receiptOrderStatus"), filter.getGrnStatus()));
			}
			if (filter.getDateOrdered() != null) {
				predicates.add(cb.between(root.get("dateOrdered"), filter.getDateOrdered(), new Date()));
			}
			if (filter.getDateReceived() != null) {
				predicates.add(cb.between(root.get("dateReceived"), filter.getDateReceived(), new Date()));
			}
		
		return cb.and(predicates.toArray(new Predicate[0]));
		};

		return specification;
	}

	
	private Predicate getUserPredicate(final Root<ReceiptOrder> root, final CriteriaBuilder cb, CriteriaQuery<?> query) {
		return null;
	}


	public void print(ReceiptOrderFilterDTO filter, HttpServletResponse response, HttpServletRequest request,PrintType type) throws Exception {

		InputStream jasperStream;
		OutputStream outputStream;
		List<ReceiptOrderRepDTO> list = new ArrayList<>();
		final Map<String, Object> params = new HashMap<String, Object>();
			jasperStream = ReportUtil.getInstance().getInputStream(request,ReceiptOrderFilterDTO.getTemplatePath(), ReceiptOrderFilterDTO.getTemplateName());
			outputStream = ReportUtil.getInstance().getOutPutStram(response, ReceiptOrderFilterDTO.getReportName(), type.getContentType(), type.getExtention());
			list = getReportDataList(filter);
		if(type.equals(PrintType.CSV)){
			ReportUtil.getInstance().generateCSV(new ReceiptOrderPrintDTO(list), jasperStream, params, outputStream);

		}else{
			ReportUtil.getInstance().generatePDF(new ReceiptOrderPrintDTO(list), jasperStream, params, outputStream);

		}

		outputStream.flush();
		outputStream.close();
	}

	@Override
	public void printDoc(Integer id, HttpServletResponse response, HttpServletRequest request) throws Exception {
		InputStream jasperStream;
		OutputStream outputStream;
		ReceiptOrderRepDTO receiptOrderRepDTO = new ReceiptOrderRepDTO();
		final Map<String, Object> params = new HashMap<String, Object>();
		jasperStream = ReportUtil.getInstance().getInputStream(request, "/resources/report/inventory/receiptOrder/",
				"ReceiptOrderView.jrxml");
		receiptOrderRepDTO = getReportData(id);
		outputStream = ReportUtil.getInstance().getOutPutStram(response, "ReceiptOrder",
				PrintType.PDF.getContentType(), PrintType.PDF.getExtention());
		ArrayList<ReceiptOrderRepDTO> dataList =new ArrayList<>();
		dataList.add(receiptOrderRepDTO);
		String reportDir=request.getRealPath("").concat("/resources/report/");
		params.put("SUBREPORT_DIR", reportDir);
		ReportUtil.getInstance().generatePDF(dataList, jasperStream, params, outputStream);

		outputStream.flush();
		outputStream.close();
	}
	
	
	private ReceiptOrderRepDTO getReportData(Integer id) throws Exception {
		final ReceiptOrder domain = receiptOrderDao.findOne(id);
		final ReceiptOrderRepDTO repDTO = new ReceiptOrderRepDTO();

		repDTO.setCode(domain.getCode());
		if ((domain.getSupplier() != null) && (domain.getSupplier().getId() != null)) {
			repDTO.setSupplierName(domain.getSupplier().getName());
			repDTO.setSupplierAddressLine1(domain.getSupplier().getAddress());
			repDTO.setSupplierAddressLine2(domain.getSupplier().getCity());
			repDTO.setSupplierAddressLine3(domain.getSupplier().getProvince());
			if ((domain.getSupplier().getCountry() != null) && (domain.getSupplier().getCountry().getId() != null)) {
				repDTO.setSupplierCountry(domain.getSupplier().getCountry().getName());
			}
		}
		repDTO.setDateOrdered(domain.getDateOrdered());
		repDTO.setDateReceived(domain.getDateReceived());
		repDTO.setInvoiceNo("");

		if (domain.getBusiness() != null) {
			repDTO.setBusinessName(domain.getBusiness().getName());
			repDTO.setBusinessAddress(domain.getBusiness().getAddress());
		}

		List<ReceiptOrderItemRepDTO> itemList= new ArrayList<>();
		Double itemCost=0.0;
		for (ReceiptOrderItem receiptOrderItem : domain.getReceiptOrderItems()) {
			ReceiptOrderItemRepDTO receiptOrderItemRepDTO = new ReceiptOrderItemRepDTO();
			receiptOrderItemRepDTO.setGrnNo(receiptOrderItem.getReceiptOrder().getCode());
			receiptOrderItemRepDTO.setDate(receiptOrderItem.getReceiptOrder().getDateReceived());
		//	receiptOrderItemRepDTO.setInvoiceNo(receiptOrderItem.getReceiptOrder().getInvoiceNo());
		//	receiptOrderItemRepDTO.setRemarks(receiptOrderItem.getReceiptOrder().getRemark());
			receiptOrderItemRepDTO.setItemCode(receiptOrderItem.getAsset().getCode());
			receiptOrderItemRepDTO.setItemId(receiptOrderItem.getAsset().getId());
			receiptOrderItemRepDTO.setQuantity(receiptOrderItem.getQuantityReceived().doubleValue());
			receiptOrderItemRepDTO.setUnitCost(receiptOrderItem.getUnitPrice().doubleValue());
			receiptOrderItemRepDTO.setWeightedCost(0.0);        
			receiptOrderItemRepDTO.setDescription(receiptOrderItem.getDescription());       
	       // dto.setAmount(CommonUtil.round((receiptOrderItem.getUnitPrice() * receiptOrderItem.getQuantityReceived()), 2));
			receiptOrderItemRepDTO.setStatus(receiptOrderItem.getReceiptOrder().getReceiptOrderStatus().getName());

	        itemList.add(receiptOrderItemRepDTO);
		}
		repDTO.setItemRepDTOs(itemList);
		return repDTO;
	}
	
}
