package com.codex.ecam.service.admin.impl;

import java.util.List;

import javax.persistence.criteria.Join;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.dao.admin.AssetBrandDao;
import com.codex.ecam.dao.admin.AssetModelDao;
import com.codex.ecam.dto.admin.AssetModelDTO;
import com.codex.ecam.mappers.admin.AssetModelMapper;
import com.codex.ecam.model.admin.AssetBrand;
import com.codex.ecam.model.admin.AssetModel;
import com.codex.ecam.model.asset.AssetCategory;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.AssetModelResult;
import com.codex.ecam.service.admin.api.AssetModelService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.search.asset.AssetModelSearchPropertyMapper;

@Service
public class AssetModelServiceImpl implements AssetModelService{

	@Autowired
	private AssetModelDao assetModelDao;

	@Autowired
	private AssetBrandDao assetBrandDao;

	@Override
	public AssetModelDTO findById(Integer id) throws Exception {
		AssetModel domain = assetModelDao.findById(id);
		if (domain != null) {
			return AssetModelMapper.getInstance().domainToDto(domain);
		}
		return null;
	}

	@Override
	public AssetModelResult delete(Integer id) {
		AssetModelResult result = new AssetModelResult(null, null);
		try {
			assetModelDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Asset model deleted successfully.");
		} catch (DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Asset model already assigned. Please Rremove from assigned Asset model and try again.");
		} catch (Exception e) {
			result.setResultStatusError();
			result.addToErrorList(e.getMessage());
		}
		return result;
	}

	@Override
	public AssetModelResult save(AssetModelDTO dto) throws Exception {
		AssetModelResult result = createAssetModelResult(dto);
		try{
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Asset Model Already updated. Please Reload Asset Model.");
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(AssetModelResult result) throws Exception {
		AssetModelMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setBrand(result);
		assetModelDao.save(result.getDomainEntity());
		result.updateDtoIdAndVersion();
	}

	private AssetModelResult createAssetModelResult(AssetModelDTO dto) {
		AssetModelResult result;
		if ((dto.getModelId() != null) && (dto.getModelId() > 0)) {
			result = new AssetModelResult(assetModelDao.findOne(dto.getModelId()), dto);
		} else {
			result = new AssetModelResult(new AssetModel(), dto);
		}

		return result;
	}

	private String getMessageByAction(AssetModelDTO dto) {
		if (dto.getModelId() == null) {
			return "Asset Model Added Successfully.";
		} else {
			return "Asset Model Updated Successfully.";
		}
	}

	private void setBrand(AssetModelResult result) {
		if((result.getDtoEntity().getBrandId() != null) && (result.getDtoEntity().getBrandId() > 0)){
			result.getDomainEntity().setAssetBrand(assetBrandDao.findById(result.getDtoEntity().getBrandId()));
		}
	}

	@Override
	public DataTablesOutput<AssetModelDTO> findAll(FocusDataTablesInput input) throws Exception {
		AssetModelSearchPropertyMapper.getInstance().generateDataTableInput(input);
		DataTablesOutput<AssetModel>  domain = null;
		DataTablesOutput<AssetModelDTO> dto = null;
		
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			domain = assetModelDao.findAll(input);
		} else {
//			Specification<AssetModel> specification = (root, query, cb) -> cb.equal(root.get("business"),
//					AuthenticationUtil.getLoginUserBusiness());
//			domain = assetModelDao.findAll(input, specification);
			domain = assetModelDao.findAll(input);

		}
		try {
			dto = AssetModelMapper.getInstance().domainToDTODataTablesOutput(domain);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	@Override
	public List<AssetModelDTO> findAll(){
		Iterable<AssetModel> models = assetModelDao.findAll();
		try {
			return AssetModelMapper.getInstance().domainToDTOList(models);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<AssetModelDTO> findByBrandId(Integer id) {
		try {
			List<AssetModelDTO> dtoList = null;
			Specification<AssetModel> specification = (root, query, cb) -> {
				Join<AssetModel, AssetBrand> joinAssetBrand = root.join("assetBrand");
				return cb.equal(joinAssetBrand.get("id"), id);
			};

			if(specification != null){
				List<AssetModel> models = assetModelDao.findAll(specification);
				dtoList =  AssetModelMapper.getInstance().domainToDTOList(models);
			}
			return dtoList;

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public DataTablesOutput<AssetModelDTO> findAllByBrand(FocusDataTablesInput input, Integer id) throws Exception {
		Specification<AssetModel> specification = (root, query, cb) -> cb.equal(root.get("assetBrand").get("id"), id);
		AssetModelSearchPropertyMapper.getInstance().generateDataTableInput(input);

		DataTablesOutput<AssetModel> domainOut = assetModelDao.findAll(input,specification);
		DataTablesOutput<AssetModelDTO> out = null;
		try {
			out = AssetModelMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return out;
	}


}
