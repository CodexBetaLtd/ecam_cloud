package com.codex.ecam.service.report.purchaseorder.impl;

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
import com.codex.ecam.dao.inventory.PurchaseOrderDao;
import com.codex.ecam.dto.report.data.aod.AODRepDTO;
import com.codex.ecam.dto.report.data.purchaseorder.PurchaseorderRepDTO;
import com.codex.ecam.dto.report.data.purchaseorder.PurchaseorderRepItemDTO;
import com.codex.ecam.dto.report.data.purchaseorder.PurchaseorderRepTaxDTO;
import com.codex.ecam.dto.report.filter.AODFilterDTO;
import com.codex.ecam.dto.report.print.AODPrintDTO;
import com.codex.ecam.mappers.report.AODReportMapper;
import com.codex.ecam.model.inventory.aod.AOD;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrder;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderItem;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderTax;
import com.codex.ecam.model.inventory.receiptOrder.ReceiptOrder;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.report.purchaseorder.api.PurchaseorderReportService;
import com.codex.ecam.util.ReportUtil;
import com.codex.ecam.util.search.inventory.PartSearchPropertyMapper;

@Service("purchaseorderReportService")
public class PurchaseorderReportServiceImpl implements PurchaseorderReportService {

	@Autowired
	private PurchaseOrderDao purchaseOrderDao;

	@Override
	public DataTablesOutput<AODRepDTO> searchDetail(FocusDataTablesInput input, AODFilterDTO filter) throws Exception {
		final Specification<AOD> specification = getDetailSpecification(filter);
		PartSearchPropertyMapper.getInstance().generateDataTableInput(input);
		//final DataTablesOutput<AOD> domainOut = aodDao.findAll(input, specification);
		final DataTablesOutput<AODRepDTO> out = AODReportMapper.getInstance().domainToRepDTODataTablesOutput(null);

		return out;
	}

	private List<AODRepDTO> getReportDataList(AODFilterDTO filter) throws Exception {
		final Specification<AOD> specification = getDetailSpecification(filter);
		//final List<AOD> list = aodDao.findAll(specification);
		final List<AODRepDTO> out = AODReportMapper.getInstance().domainToRepDTOList(null);
		return out;
	}

	private PurchaseorderRepDTO getReportData(Integer id) throws Exception {
		final PurchaseOrder domain = purchaseOrderDao.findOne(id);
		final PurchaseorderRepDTO repDTO = new PurchaseorderRepDTO();

		repDTO.setPoNo(domain.getCode());
		if (domain.getSupplier() != null) {
			repDTO.setSuppierName(domain.getSupplier().getName());
		}
		repDTO.setExpectedDeliveryDate(domain.getExpectedDeliveryDate());
		repDTO.setPoStatus(domain.getPurchaseOrderStatus().getName());

		if (domain.getBusiness() != null) {
			repDTO.setBusinessName(domain.getBusiness().getName());
			repDTO.setBusinessAddress(domain.getBusiness().getAddress());
		}

		List<PurchaseorderRepItemDTO> itemList= new ArrayList<>();
		Double itemCost=0.0;
		for (PurchaseOrderItem purchaseOrderItem : domain.getPurchaseOrderItems()) {
			PurchaseorderRepItemDTO purchaseorderRepItemDTO = new PurchaseorderRepItemDTO();
			if(purchaseOrderItem.getAsset()!=null){
				purchaseorderRepItemDTO.setPartName(purchaseOrderItem.getAsset().getName());
				purchaseorderRepItemDTO.setPartCode(purchaseOrderItem.getAsset().getCode());
			}

			purchaseorderRepItemDTO.setItemCost(purchaseOrderItem.getUnitPrice());
			if(purchaseOrderItem.getQtyOnOrder()!=null){
				purchaseorderRepItemDTO.setItemQuantity(purchaseOrderItem.getQtyOnOrder().doubleValue());

			}
			if(purchaseOrderItem.getTotalPrice()!=null){

			purchaseorderRepItemDTO.setItemAmount(purchaseOrderItem.getTotalPrice().doubleValue());
			itemCost=itemCost+purchaseOrderItem.getTotalPrice().doubleValue();
			}
			purchaseorderRepItemDTO.setTaxValue(purchaseOrderItem.getTaxRate());
			itemList.add(purchaseorderRepItemDTO);
		}
		repDTO.setTotalItemCost(itemCost);
		repDTO.setPurchaseorderRepItemDTOs(itemList);
		
		List<PurchaseorderRepTaxDTO> taxList= new ArrayList<>();
		for (PurchaseOrderTax purchaseOrderTax : domain.getPurchaseOrderTaxs()) {
			PurchaseorderRepTaxDTO purchaseorderRepTaxDTO = new PurchaseorderRepTaxDTO();
			if(purchaseOrderTax.getTaxValue()!=null){
	
				if(purchaseOrderTax.getTaxValue().getTax()!=null){
					purchaseorderRepTaxDTO.setTaxType(purchaseOrderTax.getTaxValue().getTax().getTaxType().getName());
					purchaseorderRepTaxDTO.setTaxName(purchaseOrderTax.getTaxValue().getTax().getName());
					if(purchaseOrderTax.getTaxValue().getValue()!=null){
						if(purchaseOrderTax.getTaxValue().getTax().getTaxType().equals(TAXType.PERCENTAGE)){
							Double taxValue=(purchaseOrderTax.getTaxValue().getValue().doubleValue()/100)*itemCost;
							purchaseorderRepTaxDTO.setTaxValue(taxValue);
						}else{
							purchaseorderRepTaxDTO.setTaxValue(purchaseOrderTax.getTaxValue().getValue().doubleValue());

						}

					}

				}
			}

			taxList.add(purchaseorderRepTaxDTO);
		}
		repDTO.setPurchaseorderRepTaxDTOs(taxList);
		return repDTO;
	}

	private Specification<AOD> getDetailSpecification(AODFilterDTO filter) {
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
		PurchaseorderRepDTO purchaseorderRepDTO = new PurchaseorderRepDTO();
		final Map<String, Object> params = new HashMap<String, Object>();
		jasperStream = ReportUtil.getInstance().getInputStream(request, "/resources/report/inventory/purchaseorder/",
				"POView.jrxml");
		purchaseorderRepDTO = getReportData(id);
		outputStream = ReportUtil.getInstance().getOutPutStram(response, "PO_1",
				PrintType.PDF.getContentType(), PrintType.PDF.getExtention());
		ArrayList<PurchaseorderRepDTO> dataList =new ArrayList<>();
		dataList.add(purchaseorderRepDTO);
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
