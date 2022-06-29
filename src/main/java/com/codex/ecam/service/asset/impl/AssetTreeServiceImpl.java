package com.codex.ecam.service.asset.impl;

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
import com.codex.ecam.mappers.asset.AssetMapper;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.asset.api.AssetTreeService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.search.asset.AssetSearchPropertyMapper;

@Service
public class AssetTreeServiceImpl implements AssetTreeService {

	@Autowired
	private AssetDao assetDao;

	@Override
	public List<AssetDTO> findAllChildrens(final Integer id) throws Exception {
		List<Asset> assets = new ArrayList<>();
		final Specification<Asset> specification = (root, query, cb) -> {
			return cb.or(cb.equal(root.get("parentAsset").get("id"), id),
					cb.equal(root.get("site").get("id"), id));
		};
		assets = assetDao.findAll(specification);
		setChildrens(assets);
		return AssetMapper.getInstance().domainToDTOList(assets);
	} 

	@Override
	public List<AssetDTO> findAllMachineChildrens(Integer id) throws Exception {
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
	
	public DataTablesOutput<AssetDTO> findMachineParentLocation(final FocusDataTablesInput input,Integer id,Integer assetId) throws Exception {
		DataTablesOutput<Asset> assets =null;
	final Specification<Asset> specification = (root, query, cb) -> { 
			return cb.equal(root.get("id"), id) ;
		};
		assets = assetDao.findAll(input, specification);

		setChildrensLocation(assets.getData(),assetId);
		return AssetMapper.getInstance().domainToDTODataTablesOutput(assets);
	}
	
	private void setChildrensLocation(final List<Asset> domainOut,Integer assetId) {
		for (final Asset parentAsset : domainOut) {
				final Integer count = (int) assetDao.count(getLocationChildCount(parentAsset.getId()));
				parentAsset.setChildCount(count);
						
		}
	}
	private void setParentLocation(final List<Asset> domainOut,Integer assetId) {
		for (final Asset parentAsset : domainOut) {
			if(assetId.equals(parentAsset.getId())){
				final Integer count = (int) assetDao.count(getLocationChildCount(parentAsset.getId()));
				parentAsset.setChildCount(count);
			}

		}
	}

	private Specification<Asset> getLocationChildCount(final Integer id) {
		final Specification<Asset> specification = (root, query, cb) -> {
			return cb.or(
			cb.equal(root.get("site").get("id"), id));
		};
		return specification;
	}

	@Override
	public DataTablesOutput<AssetDTO> findAllParentFacilities(final FocusDataTablesInput input) throws Exception {
		AssetSearchPropertyMapper.getInstance().generateDataTableInput(input);
		final Specification<Asset> specification = getParentFacilitySpecification();
		final DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specification);
		setChildrens(domainOut.getData());
		final DataTablesOutput<AssetDTO> out = AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	private Specification<Asset> getParentFacilitySpecification() {
		Specification<Asset> specification;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			specification = (root, query, cb) -> {
				final Predicate facilityPredicate = getFacilityPredicate(root, cb);
				final Predicate assetParentPredicate = getParentPredicate(root, cb);
				return cb.and(facilityPredicate, assetParentPredicate);
			};
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			specification = (root, query, cb) -> {
				final Predicate facilityPredicate = getFacilityPredicate(root, cb);
				final Predicate assetBusinessPredicate = getBusinessPredicate(root, cb);
				final Predicate assetParentPredicate = getParentPredicate(root, cb);
				return cb.and(facilityPredicate, assetBusinessPredicate, assetParentPredicate);
			};
		} else {
			specification = (root, query, cb) -> {
				final Predicate facilityPredicate = getFacilityPredicate(root, cb);
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

	private Predicate getFacilityPredicate(final Root<Asset> root, final CriteriaBuilder cb) {
		return cb.equal(root.get("assetCategory").get("assetCategoryType"), AssetCategoryType.LOCATIONS_OR_FACILITIES);
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
			return cb.or(
			cb.equal(root.get("site").get("id"), id),
			 cb.equal(root.get("parentAsset").get("id"), id));
		};
		return specification;
	}

}
