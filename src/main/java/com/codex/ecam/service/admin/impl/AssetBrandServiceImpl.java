package com.codex.ecam.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.dao.admin.AssetBrandDao;
import com.codex.ecam.dto.admin.AssetBrandDTO;
import com.codex.ecam.mappers.admin.AssetBrandMapper;
import com.codex.ecam.model.admin.AssetBrand;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.AssetBrandResult;
import com.codex.ecam.service.admin.api.AssetBrandService;
import com.codex.ecam.util.search.admin.AssetBrandSearchPropertyMapper;

@Service
public class AssetBrandServiceImpl implements AssetBrandService{

	@Autowired
	private AssetBrandDao assetBrandDao;

	@Override
	public AssetBrandDTO findById(Integer id) throws Exception {
		AssetBrand domain = assetBrandDao.findById(id);
		if(domain != null){
			return AssetBrandMapper.getInstance().domainToDto(domain);
		}
		return null;
	}

	@Override
	public AssetBrandResult delete(Integer id) {
		AssetBrandResult result = new AssetBrandResult(null, null);
		try {
			assetBrandDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Asset Brand Deleted Successfully.");
		} catch (DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Asset Brand already assigned. Please Rremove from assigned Asset Brand and try again.");
		} catch (Exception e) {
			result.setResultStatusError();
			result.addToErrorList(e.getMessage());
		}

		return result;
	}

	@Override
	public AssetBrandResult save(AssetBrandDTO dto) throws Exception {
		AssetBrandResult result = createAssetBrandResult(dto);
		try{
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Asset Brand Already updated. Please Reload Asset Brand.");
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate( AssetBrandResult result) throws Exception{
		AssetBrandMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		assetBrandDao.save(result.getDomainEntity());
		result.updateDtoIdAndVersion();
	}

	private AssetBrandResult createAssetBrandResult(AssetBrandDTO dto) {
		AssetBrandResult result;
		if ((dto.getBrandId() != null) && (dto.getBrandId() > 0)) {
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

		AssetBrandSearchPropertyMapper.getInstance().generateDataTableInput(input);
		DataTablesOutput<AssetBrand> domain = assetBrandDao.findAll(input);
		DataTablesOutput<AssetBrandDTO> dto = null;
		try {
			dto = AssetBrandMapper.getInstance().domainToDTODataTablesOutput(domain);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	@Override
	public List<AssetBrandDTO> findAll(){
		Iterable<AssetBrand> brands = assetBrandDao.findAll();
		try {
			return AssetBrandMapper.getInstance().domainToDTOList(brands);
		} catch (Exception e) {
			return null;
		}
	}

}
