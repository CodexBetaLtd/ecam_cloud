package com.neolith.focus.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.neolith.focus.dao.admin.AssetEventTypeDao;
import com.neolith.focus.dao.biz.BusinessDao;
import com.neolith.focus.dto.admin.AssetEventTypeDTO;
import com.neolith.focus.mappers.admin.AssetEventTypeMapper;
import com.neolith.focus.model.asset.AssetEventType;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.admin.AssetEventTypeResult;
import com.neolith.focus.service.admin.api.AssetEventTypeService;
import com.neolith.focus.util.AuthenticationUtil;
import com.neolith.focus.util.search.admin.AssetEventTypeSearchPropertyMapper;

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
			Specification<AssetEventType> specification = (root, query, cb) -> cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
			domainOut = assetEventTypeDao.findAll(input, specification);
		}

		DataTablesOutput<AssetEventTypeDTO> out = AssetEventTypeMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	@Override
	public AssetEventTypeDTO findById(Integer id) throws Exception {
		AssetEventType domain = assetEventTypeDao.findById(id);
		if (domain != null) {
			return AssetEventTypeMapper.getInstance().domainToDto(domain);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AssetEventTypeResult delete(Integer id) {
		AssetEventTypeResult result = new AssetEventTypeResult(null, null);
		try {
			assetEventTypeDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Asset Event Deleted Successfully.");
		} catch (DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Asset Event Already Assigned. Please Remove from Assigned Asset Event and try again.");
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	public AssetEventTypeResult save(AssetEventTypeDTO dto) throws Exception {
		AssetEventTypeResult result = createAssetEventTypeResultt(dto);
		try{
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Asset Event Type updated. Please Reload Asset Event Type.");
		} catch (Exception ex) {
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
		if ((dto.getId() != null) && (dto.getId() > 0)) {
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
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getBusinessId() != null)) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}

	@Override
	public List<AssetEventTypeDTO> findAll() {
		Iterable<AssetEventType> domainList = assetEventTypeDao.findAll();
		try {
			return AssetEventTypeMapper.getInstance().domainToDTOList(domainList);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void saveAll(List<AssetEventTypeDTO> list) {
		for (AssetEventTypeDTO dto : list) {
			try {
				save(dto);
			} catch (Exception e) {

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
		Specification<AssetEventType> specification = (root, query, cb) -> cb.equal(root.get("business").get("id"), bizId);
		DataTablesOutput<AssetEventType> domainOut = assetEventTypeDao.findAll(input, specification); 
		DataTablesOutput<AssetEventTypeDTO> out = AssetEventTypeMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}


}
