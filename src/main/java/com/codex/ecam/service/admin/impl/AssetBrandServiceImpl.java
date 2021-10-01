package com.codex.ecam.service.admin.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.dao.admin.AssetBrandDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dto.admin.AssetBrandDTO;
import com.codex.ecam.mappers.admin.AssetBrandMapper;
import com.codex.ecam.model.admin.AssetBrand;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.AssetBrandResult;
import com.codex.ecam.service.admin.api.AssetBrandService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.search.admin.AssetBrandSearchPropertyMapper;

@Service
public class AssetBrandServiceImpl implements AssetBrandService{

	@Autowired
	private AssetBrandDao assetBrandDao;

	@Autowired
	private BusinessDao businessDao;

	@Override
	public AssetBrandDTO findById(Integer id) throws Exception {
		final AssetBrand domain = assetBrandDao.findById(id);
		if(domain != null){
			return AssetBrandMapper.getInstance().domainToDto(domain);
		}
		return null;
	}

	@Override
	public AssetBrandResult delete(Integer id) {
		final AssetBrandResult result = new AssetBrandResult(null, null);
		try {
			assetBrandDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Asset Brand Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Asset Brand already assigned. Please Rremove from assigned Asset Brand and try again.");
		} catch (final Exception e) {
			result.setResultStatusError();
			result.addToErrorList(e.getMessage());
		}

		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AssetBrandResult deleteMultiple(Integer[] ids) throws Exception {
		final AssetBrandResult result = new AssetBrandResult(null, null);
		try {
			for (final Integer id : ids) {
				assetBrandDao.delete(id);
			}
			result.setResultStatusSuccess();
			result.addToMessageList("Asset Brand(s) Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Asset Brand(s) Already Used. Cannot delete.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	public AssetBrandResult save(AssetBrandDTO dto) throws Exception {
		final AssetBrandResult result = createAssetBrandResult(dto);
		try{
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (final ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Asset Brand Already updated. Please Reload Asset Brand.");
		} catch (final Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate( AssetBrandResult result) throws Exception{
		AssetBrandMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setBrandData(result.getDtoEntity(), result.getDomainEntity());
		assetBrandDao.save(result.getDomainEntity());
		result.updateDtoIdAndVersion();
	}

	private void setBrandData(AssetBrandDTO dto, AssetBrand domain) {
		setBrandBusiness(dto, domain);
	}

	private void setBrandBusiness(AssetBrandDTO dto, AssetBrand domain) {
		if (dto.getBrandBusinessId() != null) {
			domain.setBusiness(businessDao.findOne(dto.getBrandBusinessId()));
		}
	}

	private AssetBrandResult createAssetBrandResult(AssetBrandDTO dto) {
		AssetBrandResult result;
		if (dto.getBrandId() != null && dto.getBrandId() > 0) {
			result = new AssetBrandResult(assetBrandDao.findOne(dto.getBrandId()), dto);
		} else {
			result = new AssetBrandResult(new AssetBrand(), dto);
		}

		return result;
	}

	private String getMessageByAction(AssetBrandDTO dto) {
		if (dto.getBrandId() == null) {
			return "Asset Brand Added Successfully.";
		} else {
			return "Asset Brand Updated Successfully.";
		}
	}

	@Override
	public DataTablesOutput<AssetBrandDTO> findAll(FocusDataTablesInput input) throws Exception {

		DataTablesOutput<AssetBrandDTO> dto = new DataTablesOutput<AssetBrandDTO>();

		try {
			AssetBrandSearchPropertyMapper.getInstance().generateDataTableInput(input);
			final Specification<AssetBrand> specification = (root, query, cb)->{
				final List<Predicate> predicates = new ArrayList<>();
				addPredicateAuthenticationLevel(root, cb, predicates);
				return cb.and(predicates.toArray(new Predicate[0]));
			};

			final DataTablesOutput<AssetBrand> domain = assetBrandDao.findAll(input, specification);
			dto = AssetBrandMapper.getInstance().domainToDTODataTablesOutput(domain);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	@Override
	public List<AssetBrandDTO> findAll(){

		try {
			final Specification<AssetBrand> specification = (root, query, cb)->{
				final List<Predicate> predicates = new ArrayList<>();
				addPredicateAuthenticationLevel(root, cb, predicates);
				return cb.and(predicates.toArray(new Predicate[0]));
			};
			final List<AssetBrand> brands = assetBrandDao.findAll(specification);
			return AssetBrandMapper.getInstance().domainToDTOList(brands);
		} catch (final Exception e) {
			return new ArrayList<>();
		}
	}

	private void addPredicateAuthenticationLevel(Root<AssetBrand> root, CriteriaBuilder cb,
			final List<Predicate> predicates) {
		if (AuthenticationUtil.isAuthUserSystemLevel()) {
			predicates.add(cb.equal(root.get("business").get("id"), AuthenticationUtil.getLoginUserBusiness().getId()));
		} else if(AuthenticationUtil.isAuthUserGeneralLevel()) {
			predicates.add(cb.equal(root.get("business").get("id"), AuthenticationUtil.getLoginSite().getSite().getBusiness().getId()));
		}
	}

}
