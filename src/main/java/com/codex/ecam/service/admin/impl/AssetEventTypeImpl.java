package com.codex.ecam.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.dao.admin.AssetEventTypeDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dto.admin.AssetEventTypeDTO;
import com.codex.ecam.mappers.admin.AssetEventTypeMapper;
import com.codex.ecam.model.asset.AssetEventType;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.AssetEventTypeResult;
import com.codex.ecam.service.admin.api.AssetEventTypeService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.search.admin.AssetEventTypeSearchPropertyMapper;

@Service
public class AssetEventTypeImpl implements AssetEventTypeService {

	@Autowired
	private AssetEventTypeDao assetEventTypeDao;

	@Autowired
	private BusinessDao businessDao;

	@Override
	public DataTablesOutput<AssetEventTypeDTO> findAll(FocusDataTablesInput input) throws Exception {
		DataTablesOutput<AssetEventType> domainOut;

		AssetEventTypeSearchPropertyMapper.getInstance().generateDataTableInput(input);

		if(AuthenticationUtil.isAuthUserAdminLevel()){
			domainOut = assetEventTypeDao.findAll(input);
		} else {
			final Specification<AssetEventType> specification = (root, query, cb) -> cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
			domainOut = assetEventTypeDao.findAll(input, specification);
		}

		final DataTablesOutput<AssetEventTypeDTO> out = AssetEventTypeMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	@Override
	public AssetEventTypeDTO findById(Integer id) throws Exception {
		final AssetEventType domain = assetEventTypeDao.findById(id);
		if (domain != null) {
			return AssetEventTypeMapper.getInstance().domainToDto(domain);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AssetEventTypeResult delete(Integer id) {
		final AssetEventTypeResult result = new AssetEventTypeResult(null, null);
		try {
			assetEventTypeDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Asset Event Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Asset Event Already Assigned. Please Remove from Assigned Asset Event and try again.");
		} catch (final Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AssetEventTypeResult deleteMultiple(Integer[] ids) throws Exception {
		final AssetEventTypeResult result = new AssetEventTypeResult(null, null);
		try {
			for (final Integer id : ids) {
				assetEventTypeDao.delete(id);
			}
			result.setResultStatusSuccess();
			result.addToMessageList("Asset Event Type(s) Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Asset Event Type(s) Already Used. Cannot delete.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	public AssetEventTypeResult save(AssetEventTypeDTO dto) throws Exception {
		final AssetEventTypeResult result = createAssetEventTypeResultt(dto);
		try{
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (final ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Asset Event Type updated. Please Reload Asset Event Type.");
		} catch (final Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private AssetEventTypeResult saveOrUpdate(AssetEventTypeResult result) throws Exception {
		AssetEventTypeMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setBusiness(result);
		assetEventTypeDao.save(result.getDomainEntity());
		result.updateDtoIdAndVersion();
		return result;
	}

	private AssetEventTypeResult createAssetEventTypeResultt(AssetEventTypeDTO dto) {
		AssetEventTypeResult result;
		if (dto.getId() != null && dto.getId() > 0) {
			result = new AssetEventTypeResult(assetEventTypeDao.findOne(dto.getId()), dto);
		} else {
			result = new AssetEventTypeResult(new AssetEventType(), dto);
		}
		return result;
	}

	private String getMessageByAction(AssetEventTypeDTO dto) {
		if (dto.getId() == null) {
			return "AssetEventType Added Successfully.";
		} else {
			return "AssetEventType Updated Successfully.";
		}
	}

	private void setBusiness(AssetEventTypeResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getBusinessId() != null) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}

	@Override
	public List<AssetEventTypeDTO> findAll() {
		final Iterable<AssetEventType> domainList = assetEventTypeDao.findAll();
		try {
			return AssetEventTypeMapper.getInstance().domainToDTOList(domainList);
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void saveAll(List<AssetEventTypeDTO> list) {
		for (final AssetEventTypeDTO dto : list) {
			try {
				save(dto);
			} catch (final Exception e) {

				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteAll() {
		assetEventTypeDao.deleteAll();

	}

	@Override
	public DataTablesOutput<AssetEventTypeDTO> getAssetEventTypeByBusiness(FocusDataTablesInput input, Integer bizId) throws Exception {

		AssetEventTypeSearchPropertyMapper.getInstance().generateDataTableInput(input);
		final Specification<AssetEventType> specification = (root, query, cb) -> cb.equal(root.get("business").get("id"), bizId);
		final DataTablesOutput<AssetEventType> domainOut = assetEventTypeDao.findAll(input, specification);
		final DataTablesOutput<AssetEventTypeDTO> out = AssetEventTypeMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}


}
