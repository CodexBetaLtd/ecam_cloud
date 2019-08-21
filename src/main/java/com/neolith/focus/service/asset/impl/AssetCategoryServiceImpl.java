package com.neolith.focus.service.asset.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.neolith.focus.constants.AssetCategoryType;
import com.neolith.focus.dao.asset.AssetCategoryDao;
import com.neolith.focus.dao.biz.BusinessDao;
import com.neolith.focus.dto.asset.AssetCategoryDTO;
import com.neolith.focus.mappers.asset.AssetCategoryMapper;
import com.neolith.focus.model.asset.AssetCategory;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.admin.AssetCategoryResult;
import com.neolith.focus.service.asset.api.AssetCategoryService;
import com.neolith.focus.util.AuthenticationUtil;
import com.neolith.focus.util.search.asset.AssetCategoryPropertyMapper;

@Service
public class AssetCategoryServiceImpl implements AssetCategoryService {

	@Autowired
	private AssetCategoryDao assetCategoryDao;

	@Autowired
	private BusinessDao businessDao;

	@Override
	public DataTablesOutput<AssetCategoryDTO> findAll(FocusDataTablesInput input) throws Exception {
		DataTablesOutput<AssetCategoryDTO> out=null;
		DataTablesOutput<AssetCategory> domainOut;
		AssetCategoryPropertyMapper.getInstance().generateDataTableInput(input);
		if(AuthenticationUtil.isAuthUserAdminLevel()){
			domainOut = assetCategoryDao.findAll(input);
		} else {
			Specification<AssetCategory> specification = (root, query, cb) -> cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
			domainOut = assetCategoryDao.findAll(input, specification);
		}

		try {
			out = AssetCategoryMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}

	@Override
	public AssetCategoryDTO findById(Integer id) throws Exception {
		AssetCategory domain = assetCategoryDao.findById(id);
		if (domain != null) {
			return AssetCategoryMapper.getInstance().domainToDto(domain);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AssetCategoryResult delete(Integer id) {
		AssetCategoryResult result = new AssetCategoryResult(null, null);
		try {
			assetCategoryDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("AssetCategory Deleted Successfully.");
		} catch (DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Asset category Already Assigned. Please Remove from Assigned items and try again.");
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AssetCategoryResult update(AssetCategoryDTO dto) {
		AssetCategoryResult result = new AssetCategoryResult(null, dto);
		try {
			AssetCategory domain = assetCategoryDao.findById(dto.getId());
			result.setDomainEntity(domain);;
			saveOrUpdate(result);
			result.addToMessageList("Asset Category Updated Successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(e.getMessage());
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AssetCategoryResult save(AssetCategoryDTO dto) {
		AssetCategoryResult result = new AssetCategoryResult(new AssetCategory(), dto);
		saveOrUpdate(result);
		result.addToMessageList("AssetCategory Added Successfully.");
		return result;
	}

	private void saveOrUpdate(AssetCategoryResult result) {
		try {
			AssetCategoryMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
			setAssetCategoryData(result);
			assetCategoryDao.save(result.getDomainEntity());
			result.updateDtoIdAndVersion();
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("AssetCategory Already updated. Please Reload Asset Category.");
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
	}

	private void setAssetCategoryData(AssetCategoryResult result) {
		setParent(result.getDtoEntity(), result.getDomainEntity());
		setBusiness(result.getDtoEntity(), result.getDomainEntity());
	}

	private void setParent(AssetCategoryDTO dto, AssetCategory domain) {
		if ((dto.getParentId() != null) && (dto.getParentId() > 0)) {
			domain.setParentAssetCategory(assetCategoryDao.findOne(dto.getParentId()));
		}
	}
	private void setBusiness(AssetCategoryDTO dto, AssetCategory domain) {
		if ((dto.getBusinessId() != null) && (dto.getBusinessId() > 0)) {
			domain.setBusiness(businessDao.findOne(dto.getBusinessId()));
		}
	}

	@Override
	public List<AssetCategoryDTO> findAllBySystemCode(Integer systemCode) {
		return null;
	}

	@Override
	public List<AssetCategoryDTO> findByAssetCategoyType(AssetCategoryType type) {
		List<AssetCategory> list = assetCategoryDao.findByAssetCategoyType(type);
		try {
			return AssetCategoryMapper.getInstance().domainToDTOList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer findParentIdSystemCode(Integer parentId) throws Exception {
		return assetCategoryDao.findParentIdSystemCode(parentId);
	}

	@Override
	public List<AssetCategoryDTO> findAll() {
		return null;
	}

	@Override
	public void deleteAll() {
		assetCategoryDao.deleteAll();
	}

	@Override
	public void saveAll(List<AssetCategoryDTO> allData) {

		for (AssetCategoryDTO dto : allData) {
			try {
				save(dto);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

	}

	@Override
	public DataTablesOutput<AssetCategoryDTO> findByAssetCategoyType(FocusDataTablesInput input, AssetCategoryType type)throws Exception {
		Specification<AssetCategory> specification = null;

		AssetCategoryPropertyMapper.getInstance().generateDataTableInput(input);

		DataTablesOutput<AssetCategoryDTO> out=null;
		DataTablesOutput<AssetCategory> domainOut;
		if(AuthenticationUtil.isAuthUserAdminLevel()){
			specification = (root, query, cb) -> cb.equal(root.get("assetCategoryType"), type);		}
		else {
			specification = (root, query, cb) ->
			cb.and(
					cb.equal(
							root.get("business"), AuthenticationUtil.getLoginUserBusiness()
							),
					cb.equal(root.get("assetCategoryType"), type)
					);

		}
		domainOut = assetCategoryDao.findAll(input, specification);
		try {
			out = AssetCategoryMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return out;
	}

	@Override
	public DataTablesOutput<AssetCategoryDTO> findByAssetCategoyTypeById(FocusDataTablesInput input, Integer id)
			throws Exception {
		Specification<AssetCategory> specification = (root, query, cb) -> cb.and(
				cb.equal(root.get("parentAssetCategory").get("id"), id),
				cb.equal(root.get("assetCategoryType"),AssetCategoryType.WAREHOUSE)
				);

		AssetCategoryPropertyMapper.getInstance().generateDataTableInput(input);

		DataTablesOutput<AssetCategory> domainOut = assetCategoryDao.findAll(input,specification);
		DataTablesOutput<AssetCategoryDTO> out = null;
		try {
			out = AssetCategoryMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return out;
	}
}
