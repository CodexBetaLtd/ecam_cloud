package com.codex.ecam.service.report.assetdepreciation.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.constants.util.PrintType;
import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.dto.report.data.assetdepreciation.AssetDepreciationRepDTO;
import com.codex.ecam.dto.report.filter.assetdepreciation.AssetDepreciationFilterDTO;
import com.codex.ecam.dto.report.print.assetdepreciation.AssetDepreciationPrintDTO;
import com.codex.ecam.helper.AssetDepreciationReportCalculateHelper;
import com.codex.ecam.mappers.report.assetdepreciation.AssetDepreciationReportMapper;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.service.report.assetdepreciation.api.AssetDepreciationReportService;
import com.codex.ecam.util.ReportUtil;

@Service("assetDepreciationReportService")
public class AssetDepreciationReportServiceImpl implements AssetDepreciationReportService {

	private AssetDao assetDao;

	@Autowired
	public AssetDepreciationReportServiceImpl(AssetDao assetDao) {
		super();
		this.assetDao = assetDao;
	}

	private List<Asset> getReportDataList(AssetDepreciationFilterDTO filter) throws Exception {
		return assetDao.findAll(getDetailSpecification(filter));
	}

	private Specification<Asset> getDetailSpecification(AssetDepreciationFilterDTO filter) {
		return (root, query, cb) -> {

			query.orderBy(cb.asc(root.get("name")));

			final List<Predicate> predicates = new ArrayList<>();

			filterByAssetCategory(filter, root, cb, predicates);
			filterByMainLocation(filter, root, cb, predicates);
			filterBySubLocation(filter, root, cb, predicates);
			filterBySubLocation2(filter, root, cb, predicates);
			filterByDepartment(filter, root, cb, predicates);

			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}

	private void filterByDepartment(AssetDepreciationFilterDTO filter, Root<Asset> root, CriteriaBuilder cb,
			List<Predicate> predicates) {
		if (filter.getDepartmentName() != null) {
			predicates.add(cb.like(cb.lower(root.get("department")), "%" + filter.getDepartmentName().toLowerCase() + "%"));
		}
	}

	private void filterByMainLocation(AssetDepreciationFilterDTO filter, Root<Asset> root, CriteriaBuilder cb,
			List<Predicate> predicates) {
		if (filter.getMainLocationId() != null) {
			predicates.add(cb.equal(root.get("parentAsset").get("id"), filter.getMainLocationId()));
		}

	}

	private void filterBySubLocation(AssetDepreciationFilterDTO filter, Root<Asset> root, CriteriaBuilder cb,
			List<Predicate> predicates) {
		if (filter.getSubLocationId() != null) {
			predicates.add(cb.equal(root.get("site").get("id"), filter.getSubLocationId()));
		}

	}

	private void filterBySubLocation2(AssetDepreciationFilterDTO filter, Root<Asset> root, CriteriaBuilder cb,
			List<Predicate> predicates) {
		if (filter.getSubLocation2Id() != null) {
			predicates.add(cb.equal(root.get("subSite").get("id"), filter.getSubLocation2Id()));
		}

	}

	private void filterByAssetCategory(AssetDepreciationFilterDTO filter, Root<Asset> root, CriteriaBuilder cb,
			final List<Predicate> predicates) {
		if (filter.getAssetCategoryId() != null) {
			predicates.add(cb.equal(root.get("assetCategory").get("id"), filter.getAssetCategoryId()));
		} else {
			predicates.add(cb.notEqual(root.get("assetCategory").get("assetCategoryType"), AssetCategoryType.LOCATIONS_OR_FACILITIES));
		}
	}

	@Override
	public void print(AssetDepreciationFilterDTO filter, HttpServletResponse response, HttpServletRequest request, PrintType type)
			throws Exception {

		final Map<String, Object> params = new HashMap<>();

		InputStream jasperStream = ReportUtil.getInstance().getInputStream(request, filter.getTemplatePath(),
				filter.getTemplateName());
		OutputStream outputStream = ReportUtil.getInstance().getOutPutStram(response, filter.getReportName(),
				type.getContentType(), type.getExtention());

		List<AssetDepreciationRepDTO> list = new ArrayList<>();

		for (Asset asset : getReportDataList(filter)) {
			AssetDepreciationRepDTO dto = AssetDepreciationReportMapper.getInstance().domainToRepDTO(asset);
			AssetDepreciationReportCalculateHelper.getInstance().calculate(filter.getFromDate(), filter.getToDate(), dto);
			dto.setFromDate(filter.getFromDate());
			dto.setToDate(filter.getToDate());
			list.add(dto);
		}

		if (type.equals(PrintType.CSV)) {
			ReportUtil.getInstance().generateCSV(new AssetDepreciationPrintDTO(list), jasperStream, params, outputStream);

		}

		outputStream.flush();
		outputStream.close();
	}

}
