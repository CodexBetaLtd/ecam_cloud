package com.codex.ecam.service.report.part.impl;


import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.constants.util.PrintType;
import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.dto.report.data.PartRepDTO;
import com.codex.ecam.dto.report.filter.PartFilterDTO;
import com.codex.ecam.dto.report.print.PartPrintDTO;
import com.codex.ecam.mappers.report.PartReportMapper;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.report.part.api.PartReportService;
import com.codex.ecam.util.ReportUtil;
import com.codex.ecam.util.search.inventory.PartSearchPropertyMapper;


@Service("partReportService")
public class PartReportServiceImpl implements PartReportService {
	@Autowired
	private AssetDao assetDao;

	@Override
	public DataTablesOutput<PartRepDTO> searchDetail(FocusDataTablesInput input, PartFilterDTO filter) throws Exception {
		final Specification<Asset> specification = getDetailSpecification(filter);
		PartSearchPropertyMapper.getInstance().generateDataTableInput(input);
		final DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specification);
		final DataTablesOutput<PartRepDTO> out = PartReportMapper.getInstance().domainToRepDTODataTablesOutput(domainOut);

		return out;
	}

	private List<PartRepDTO> getPartDetailReport(PartFilterDTO filter) throws Exception {
		final Specification<Asset> specification = getDetailSpecification(filter);
		final List<PartRepDTO> out = searchPartDetailReport(specification);
		return out;
	}

	private List<PartRepDTO> searchPartDetailReport(Specification<Asset> specification) throws Exception {
		final List<Asset> partList = assetDao.findAll(specification);
		final List<PartRepDTO> out = PartReportMapper.getInstance().domainToRepDTOList(partList);
		return out;
	}

	private Specification<Asset> getDetailSpecification(PartFilterDTO filter) {
		final Specification<Asset> specification = (root, query, cb) -> {
			query.orderBy(cb.asc(root.get("code")));
			final List<Predicate> predicates = new ArrayList<>();
				predicates.add(getPartPredicate(root, cb, query));
			if (filter.getPartType() != null) {
				predicates.add(getPartTypePredicate(filter,root, cb, query));
			}
			return cb.and(predicates.toArray(new Predicate[0]));
		};

		return specification;
	}

	
	private Predicate getPartPredicate(final Root<Asset> root, final CriteriaBuilder cb, CriteriaQuery<?> query) {
		
		final Predicate predicate1 = cb.isNotNull(root.get("assetCategory").get("assetCategoryType"));
		final Predicate predicate2 = cb.equal(root.get("assetCategory").get("assetCategoryType"),AssetCategoryType.PARTS_AND_SUPPLIES);
		//final Predicate predicate3 = getUserPredicate(root, cb, query);
	
		//return cb.and(predicate1,predicate2,predicate3);
		return cb.and(predicate1,predicate2);
	}
	
	private Predicate getUserPredicate(final Root<Asset> root, final CriteriaBuilder cb, CriteriaQuery<?> query) {
		return null;
	/*	 if (AuthenticationUtil.isAuthUserSystemLevel()) {
			return	cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
		} else {
			return cb.equal(root.get("business"), AuthenticationUtil.getLoginSite().getSite().getBusiness());
		}*/
	}
	
	private Predicate getPartTypePredicate(final PartFilterDTO filter,final Root<Asset> root, final CriteriaBuilder cb, CriteriaQuery<?> query) {
		return cb.equal(root.get("partType"), filter.getPartType());
	}



	public void print(PartFilterDTO filter, HttpServletResponse response, HttpServletRequest request,PrintType type) throws Exception {

		InputStream jasperStream;
		OutputStream outputStream;
		List<PartRepDTO> partList = new ArrayList<>();
		final Map<String, Object> params = new HashMap<String, Object>();
			jasperStream = ReportUtil.getInstance().getInputStream(request,PartFilterDTO.getTemplatePath(), PartFilterDTO.getTemplateName());
			outputStream = ReportUtil.getInstance().getOutPutStram(response, PartFilterDTO.getReportName(), type.getContentType(), type.getExtention());
			partList = getPartDetailReport(filter);
		if(type.equals(PrintType.CSV)){
			ReportUtil.getInstance().generateCSV(new PartPrintDTO(partList), jasperStream, params, outputStream);

		}else{
			ReportUtil.getInstance().generatePDF(new PartPrintDTO(partList), jasperStream, params, outputStream);
		}

		outputStream.flush();
		outputStream.close();
	}




}
