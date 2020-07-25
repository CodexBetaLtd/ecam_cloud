package com.codex.ecam.service.report.asset.impl;

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
import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.dao.inventory.AODDao;
import com.codex.ecam.dto.report.data.aod.AODRepDTO;
import com.codex.ecam.dto.report.data.aod.AODRepItemDataDTO;
import com.codex.ecam.dto.report.data.asset.AssetRepDTO;
import com.codex.ecam.dto.report.filter.AODFilterDTO;
import com.codex.ecam.dto.report.filter.AssetFilterDTO;
import com.codex.ecam.dto.report.print.AODPrintDTO;
import com.codex.ecam.dto.report.print.AssetPrintDTO;
import com.codex.ecam.mappers.report.AODReportMapper;
import com.codex.ecam.mappers.report.AssetReportMapper;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.inventory.aod.AOD;
import com.codex.ecam.model.inventory.aod.AODItem;
import com.codex.ecam.model.inventory.receiptOrder.ReceiptOrder;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.report.aod.api.AODReportService;
import com.codex.ecam.service.report.asset.api.AssetReportService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.ReportUtil;
import com.codex.ecam.util.search.inventory.PartSearchPropertyMapper;

@Service("assetReportService")
public class AssetReportServiceImpl implements AssetReportService {

	@Autowired
	private AssetDao assetDao;

	@Override
	public DataTablesOutput<AssetRepDTO> searchDetail(FocusDataTablesInput input, AssetFilterDTO filter) throws Exception {
		PartSearchPropertyMapper.getInstance().generateDataTableInput(input);
		DataTablesOutput<AssetRepDTO> dataTablesOutput = new DataTablesOutput<AssetRepDTO>();
		List<AssetRepDTO> assetRepDTOs = getAssetReportFilter(filter);

		dataTablesOutput.setData(assetRepDTOs);
		dataTablesOutput.setRecordsFiltered(input.getLength());
		dataTablesOutput.setRecordsTotal(assetRepDTOs.size());
		return dataTablesOutput;
	}

	private List<AssetRepDTO> getReportDataList(AssetFilterDTO filter) throws Exception {
		final Specification<Asset> specification = getDetailSpecification(filter);
		final List<Asset> list = assetDao.findAll(specification);
		final List<AssetRepDTO> out = AssetReportMapper.getInstance().domainToRepDTOList(list);
		return out;
	}

	private AODRepDTO getReportData(Integer id) throws Exception {
	//	final AOD domain = aodDao.findOne(id);
		final AODRepDTO repDTO = new AODRepDTO();

/*		repDTO.setAodNo(domain.getAodNo());
		if (domain.getCustomer() != null) {
			repDTO.setCustomerName(domain.getCustomer().getName());
		}
		repDTO.setDate(domain.getDate());
		repDTO.setAodStatus(domain.getAodStatus().getName());

		repDTO.setAodType(domain.getAodType().getName());
		if (domain.getBusiness() != null) {
			repDTO.setBusinessName(domain.getBusiness().getName());
			repDTO.setBusinessAddress(domain.getBusiness().getAddress());
		}
		if (domain.getWorkOrder() != null) {
			repDTO.setWoNo(domain.getWorkOrder().getCode());
		}

		for (AODItem aodItem : domain.getAodItemList()) {
			AODRepItemDataDTO aodRepItemDataDTO = new AODRepItemDataDTO();
			aodRepItemDataDTO.setBatchNo(aodItem.getBatchNo());
			aodRepItemDataDTO.setItemQuantity(aodItem.getQuantity().doubleValue());
			if (aodItem.getReturnQuantity() != null) {
				aodRepItemDataDTO.setItemReturnQuantity(aodItem.getReturnQuantity().doubleValue());

			}
			aodRepItemDataDTO.setDescription(aodItem.getDescription());
			if (aodItem.getPart() != null) {
				aodRepItemDataDTO.setPartCode(aodItem.getPart().getCode());
			}
			repDTO.getAodItemRepDTOs().add(aodRepItemDataDTO);
		}*/
		return repDTO;
	}

	private Specification<Asset> getDetailSpecification(final AssetFilterDTO filter) {
		final Specification<Asset> specification = (root, query, cb) -> {
			query.orderBy(cb.asc(root.get("code")));
			final List<Predicate> predicates = new ArrayList<>();
			if (filter.getAssetModelId() != null) {
				predicates.add(cb.isNotNull(root.get("model")));
				predicates.add(cb.equal(root.get("model").get("id"), filter.getAssetModelId()));
			}
			if (filter.getLocationId() != null) {
				predicates.add(cb.isNotNull(root.get("site")));
				predicates.add(cb.equal(root.get("site").get("id"), filter.getLocationId()));
			}
			if(AuthenticationUtil.isAuthUserSystemLevel()){
				predicates.add(cb.equal(root.get("business").get("id"), AuthenticationUtil.getLoginUserBusiness().getId()));

			}

			

			return cb.and(predicates.toArray(new Predicate[0]));
		};

		return specification;
	}
	
	private List<AssetRepDTO> getAssetReportFilter(final AssetFilterDTO filter){
		List<Object> objects =  new ArrayList<>();
		List<AssetRepDTO> assetRepDTOs = new ArrayList<>();
		if(filter.getAssetModelId() != null && filter.getLocationId() != null ){
			objects=assetDao.getAssetCountByCategoryAndLocationAndModel( AuthenticationUtil.getLoginUserBusiness().getId(),filter.getLocationId(),filter.getAssetModelId());
		}
		else if(filter.getAssetModelId() == null && filter.getLocationId() == null ){
			objects=assetDao.getAssetCountByCategory( AuthenticationUtil.getLoginUserBusiness().getId());
		}
		else if(filter.getAssetModelId() != null && filter.getLocationId() == null ){
			objects=assetDao.getAssetCountByCategoryAndModel( AuthenticationUtil.getLoginUserBusiness().getId(),filter.getAssetModelId());

		}
		else if(filter.getAssetModelId() == null && filter.getLocationId() != null ){
			objects=assetDao.getAssetCountByCategoryAndLocation( AuthenticationUtil.getLoginUserBusiness().getId(),filter.getLocationId());

		}
		else{
			objects=assetDao.getAssetCountByCategory( AuthenticationUtil.getLoginUserBusiness().getId());

		}

		for(Object obj:objects){
			AssetRepDTO assetRepDTO= new AssetRepDTO();
			try {
				assetRepDTO=AssetReportMapper.getInstance().domainToRepDTO((Asset)((Object[])obj)[1]);
				Long assetCount=(Long)((Object[])obj)[0];
		    	assetRepDTO.setAssetCount(assetCount.intValue());
		    	assetRepDTOs.add(assetRepDTO);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		return assetRepDTOs;
	}

	private Predicate getUserPredicate(final Root<ReceiptOrder> root, final CriteriaBuilder cb,
			CriteriaQuery<?> query) {
		return null;
	}

	@Override
	public void printDoc(Integer id, HttpServletResponse response, HttpServletRequest request) throws Exception {
		InputStream jasperStream;
		OutputStream outputStream;
		AODRepDTO aodRep = new AODRepDTO();
		final Map<String, Object> params = new HashMap<String, Object>();
		jasperStream = ReportUtil.getInstance().getInputStream(request, "/resources/report/asset/",
				"AodView.jrxml");
		aodRep = getReportData(id);
		outputStream = ReportUtil.getInstance().getOutPutStram(response, aodRep.getAodNo(),
				PrintType.PDF.getContentType(), PrintType.PDF.getExtention());
		ArrayList<AODRepDTO> dataList =new ArrayList<>();
		dataList.add(aodRep);
		String reportDir=request.getRealPath("").concat("/resources/report/");
		params.put("SUBREPORT_DIR", reportDir);
		ReportUtil.getInstance().generatePDF(dataList, jasperStream, params, outputStream);

		outputStream.flush();
		outputStream.close();
	}

	public void print(AssetFilterDTO filter, HttpServletResponse response, HttpServletRequest request, PrintType type)
			throws Exception {

		InputStream jasperStream;
		OutputStream outputStream;
		List<AssetRepDTO> list = new ArrayList<>();
		final Map<String, Object> params = new HashMap<String, Object>();
		jasperStream = ReportUtil.getInstance().getInputStream(request, AssetFilterDTO.getTemplatePath(),
				AssetFilterDTO.getTemplateName());
		outputStream = ReportUtil.getInstance().getOutPutStram(response, AssetFilterDTO.getReportName(),
				type.getContentType(), type.getExtention());
		list = getAssetReportFilter(filter);
		String reportDir=request.getRealPath("").concat("/resources/report/");
		params.put("SUBREPORT_DIR", reportDir);
		if (type.equals(PrintType.CSV)) {
			ReportUtil.getInstance().generateCSV(new AssetPrintDTO(list), jasperStream, params, outputStream);

		} else {
			ReportUtil.getInstance().generatePDF(new AssetPrintDTO(list), jasperStream, params, outputStream);

		}

		outputStream.flush();
		outputStream.close();
	}

}
