package com.codex.ecam.service.biz.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.dto.asset.AssetDTO;
import com.codex.ecam.dto.biz.warehouse.WareHouseDTO;
import com.codex.ecam.mappers.asset.AssetMapper;
import com.codex.ecam.mappers.biz.WarehouseMapper;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.biz.api.WareHouseTreeService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.search.asset.AssetSearchPropertyMapper;

@Service
public class WareHouseTreeServiceImpl implements WareHouseTreeService {

	@Autowired
	private AssetDao assetDao;

	@Override
	public List<WareHouseDTO> findAllChildrens(final Integer id) throws Exception {
		List<Asset> assets = new ArrayList<>();
		final Specification<Asset> specification = (root, query, cb) -> {
			return cb.equal(root.get("parentAsset").get("id"), id);
		};
		assets = assetDao.findAll(specification);
		setChildrens(assets);
		return WarehouseMapper.getInstance().domainToDTOList(assets);
	} 

	@Override
	public List<AssetDTO> findAllWareHouseChildrens(Integer id) throws Exception {
		List<Asset> assets = new ArrayList<>();
		final Specification<Asset> specification = (root, query, cb) -> { 
			query.orderBy(cb.asc(root.get("site").get("id")));
			return cb.or(cb.equal(root.get("parentAsset").get("id"), id),
					cb.equal(root.get("site").get("id"), id)) ;
		};
		assets = assetDao.findAll(specification);
		setChildrens(assets);
		return AssetMapper.getInstance().domainToDTOList(assets);
	}

	@Override
	public DataTablesOutput<WareHouseDTO> findAllParentFacilities(final FocusDataTablesInput input) throws Exception {
		//AssetSearchPropertyMapper.getInstance().generateDataTableInput(input);
		final Specification<Asset> specification = getParentWarehouseSpecification();
		final DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specification);
		setChildrens(domainOut.getData());
		final DataTablesOutput<WareHouseDTO> out = WarehouseMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}
	

	private Specification<Asset> getParentWarehouseSpecification() {
		Specification<Asset> specification;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			specification = (root, query, cb) -> {
				final Predicate facilityPredicate = getWareHousePredicate(root, cb);
				final Predicate assetParentPredicate = getParentPredicate(root, cb);
				return cb.and(facilityPredicate, assetParentPredicate);
			};
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			specification = (root, query, cb) -> {
				final Predicate facilityPredicate = getWareHousePredicate(root, cb);
				final Predicate assetBusinessPredicate = getBusinessPredicate(root, cb);
				final Predicate assetParentPredicate = getParentPredicate(root, cb);
				return cb.and(facilityPredicate, assetBusinessPredicate, assetParentPredicate);
			};
		} else {
			specification = (root, query, cb) -> {
				final Predicate facilityPredicate = getWareHousePredicate(root, cb);
				final Predicate assetSitePredicate = getAssetSitePredicate(root, cb);
				final Predicate assetParentPredicate = getParentPredicate(root, cb);
				return cb.and(facilityPredicate, assetSitePredicate, assetParentPredicate);
			};
		}
		return specification;
	}

	@Override
	public DataTablesOutput<AssetDTO> findAllParentMachinesAndTools(final FocusDataTablesInput input) throws Exception {
		AssetSearchPropertyMapper.getInstance().generateDataTableInput(input);
		final Specification<Asset> specification = getParentMachineAndToolsSpecification();

		final DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specification);
		setChildrens(domainOut.getData());
		final DataTablesOutput<AssetDTO> out = AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	private Specification<Asset> getParentMachineAndToolsSpecification() {
		Specification<Asset> specification;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			specification = (root, query, cb) -> {
				final Predicate assetPredicate = getMachinePredicate(root, cb);
				final Predicate assetParentPredicate = getParentPredicate(root, cb);
				return cb.and(assetPredicate, assetParentPredicate);
			};

		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			specification = (root, query, cb) -> {
				final Predicate assetPredicate = getMachinePredicate(root, cb);
				final Predicate assetParentPredicate = getParentPredicate(root, cb);
				final Predicate assetBusinessPredicate = getBusinessPredicate(root, cb);
				return cb.and(assetPredicate, assetBusinessPredicate, assetParentPredicate);
			};
		} else {
			specification = (root, query, cb) -> {
				final Predicate assetPredicate = getMachinePredicate(root, cb);
				final Predicate assetParentPredicate = getParentPredicate(root, cb);
				final Predicate assetSitePredicate = getAssetSitePredicate(root, cb);
				return cb.and(assetPredicate, assetSitePredicate, assetParentPredicate);
			};
		}
		return specification;
	}

	private Predicate getBusinessPredicate(final Root<Asset> root, final CriteriaBuilder cb) {
		return cb.equal(root.get("business").get("id"), AuthenticationUtil.getLoginUserBusiness().getId());
	}

	private Predicate getAssetSitePredicate(final Root<Asset> root, final CriteriaBuilder cb) {
		return cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite());
	}

	private Predicate getParentPredicate(final Root<Asset> root, final CriteriaBuilder cb) {
		return cb.isNull(root.get("parentAsset"));
	}

	private Predicate getWareHousePredicate(final Root<Asset> root, final CriteriaBuilder cb) {
		return cb.equal(root.get("assetCategory").get("assetCategoryType"), AssetCategoryType.WAREHOUSE);
	}

	private Predicate getMachinePredicate(final Root<Asset> root, final CriteriaBuilder cb) {
		return cb.and(
				cb.notEqual(root.get("assetCategory").get("assetCategoryType"),
						AssetCategoryType.LOCATIONS_OR_FACILITIES),
				cb.notEqual(root.get("assetCategory").get("assetCategoryType"), AssetCategoryType.PARTS_AND_SUPPLIES));
	}

	private void setChildrens(final List<Asset> domainOut) {
		for (final Asset parentAsset : domainOut) {
			final Integer count = (int) assetDao.count(getChildCount(parentAsset.getId()));
			parentAsset.setChildCount(count);
		}
	}

	private Specification<Asset> getChildCount(final Integer id) {
		final Specification<Asset> specification = (root, query, cb) -> {
			return
			 cb.equal(root.get("parentAsset").get("id"), id);
		};
		return specification;
	}

}
