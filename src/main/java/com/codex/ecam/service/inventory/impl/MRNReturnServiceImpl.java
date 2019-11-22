package com.codex.ecam.service.inventory.impl;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.constants.inventory.MRNReturnStatus;
import com.codex.ecam.dao.admin.UserDao;
import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dao.inventory.MRNDao;
import com.codex.ecam.dao.inventory.MRNItemDao;
import com.codex.ecam.dao.inventory.MRNReturnDao;
import com.codex.ecam.dto.inventory.mrnReturn.MRNReturnDTO;
import com.codex.ecam.dto.inventory.mrnReturn.MRNReturnItemDTO;
import com.codex.ecam.mappers.inventory.mrnReturn.MRNReturnMapper;
import com.codex.ecam.model.inventory.mrn.MRNItem;
import com.codex.ecam.model.inventory.mrnReturn.MRNReturn;
import com.codex.ecam.model.inventory.mrnReturn.MRNReturnItem;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.inventory.MRNReturnResult;
import com.codex.ecam.service.inventory.api.MRNReturnService;
import com.codex.ecam.util.AuthenticationUtil;

@Service
public class MRNReturnServiceImpl implements MRNReturnService {

	@Autowired
	MRNReturnDao mrnReturnDao;
	
	@Autowired
	MRNDao mrnDao;
	
	@Autowired
	MRNItemDao mrnItemDao;
	
	@Autowired
	private AssetDao assetDao;

	@Autowired
	private BusinessDao businessDao;

	@Autowired
	private UserDao userDao;
	

	@Override
	public MRNReturnResult newMRN() {
		MRNReturnResult result=new MRNReturnResult(new MRNReturn(), new MRNReturnDTO());
		return result;
	}

	public MRNReturnResult save(MRNReturnDTO dto) throws Exception {
		MRNReturnResult result = new MRNReturnResult(new MRNReturn(), dto);
		try {
			saveOrUpdate(result);
			result.setResultStatusSuccess();
			result.addToMessageList("MRN Added Successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToMessageList("Receipt order save Unsuccessful. ".concat(e.getMessage()));
		}
		return result;
	}

	public MRNReturnResult update(MRNReturnDTO dto) throws Exception {
		MRNReturnResult result = new MRNReturnResult(null, dto);
		try {
			MRNReturn domain = findEntityById(dto.getId());
			result.setDomainEntity(domain);
			saveOrUpdate(result);
			result.addToMessageList("MRN Updated Successfully.");
		} catch (ObjectOptimisticLockingFailureException ex) {
			result.setResultStatusError();
			result.addToErrorList("MRN Already updated. Please Reload MRN.");
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(e.getMessage());
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(MRNReturnResult result) throws Exception {
		MRNReturnMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setMRNData(result);
		mrnReturnDao.save(result.getDomainEntity()); 
		result.setDtoEntity(findDTOById(result.getDomainEntity().getId())); 
	}
	
	private void setMRNData(MRNReturnResult result) throws Exception {
		setMRNRequestUser(result);
		setMRN(result);
		setMRNStatus(result);
		setMRNReturnItem(result);
		setBusinessSite(result);
	}
	
	private void setMRNRequestUser(MRNReturnResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getRequestedUserId() != null)) {
			result.getDomainEntity().setRequestedBy(userDao.findOne(result.getDtoEntity().getRequestedUserId()));
		}
	}

	private void setMRNStatus(MRNReturnResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getMrnReturnStatus() != null)) {
			result.getDomainEntity().setMrnReturnStatus(result.getDtoEntity().getMrnReturnStatus());
		}
	}
	
	private void setMRN(MRNReturnResult result) {
		if ((result.getDtoEntity().getMrnId() != null) && (result.getDtoEntity().getMrnId() > 0)) {
			result.getDomainEntity().setMrn(mrnDao.findOne(result.getDtoEntity().getMrnId()));
		}
	}
	
	private void setBusinessSite(MRNReturnResult result) {
		if ((result.getDtoEntity().getBusinessId() != null) && (result.getDtoEntity().getBusinessId() > 0)) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		} else {
			result.getDomainEntity().setBusiness(AuthenticationUtil.getLoginUserBusiness());
		}
		if ((result.getDtoEntity().getSiteId() != null) && (result.getDtoEntity().getSiteId() > 0)) {
			result.getDomainEntity().setSite(assetDao.findOne(result.getDtoEntity().getSiteId()));
		} else if (!AuthenticationUtil.isAuthUserAdminLevel()) {
			result.getDomainEntity().setSite(AuthenticationUtil.getLoginSite().getSite());
		}
	}

	private void setMRNReturnItem(MRNReturnResult result) {
		
		Set<MRNReturnItem> mrnReturnItems = new HashSet<>();
		List<MRNReturnItemDTO> mrnReturnItemDTOs = result.getDtoEntity().getMrnReturnItemDTOs();
		
		if (mrnReturnItemDTOs != null && mrnReturnItemDTOs.size() > 0) {
			
			Set<MRNReturnItem> currentMRNItems = result.getDomainEntity().getReturnItems();
			
			for ( MRNReturnItemDTO mrnReturnItemDTO : mrnReturnItemDTOs ) {
				
				MRNReturnItem mrnReturnItem = new MRNReturnItem();
				
				if ((currentMRNItems != null) && (currentMRNItems.size() > 0)) {
					MRNReturnItem optional = currentMRNItems.stream().filter((x) -> x.getId().equals(mrnReturnItemDTO.getId())).findAny().orElseGet(MRNReturnItem :: new);
					mrnReturnItem = optional; 
				} else {
					mrnReturnItem = new MRNReturnItem();
				}
				
			createMRNReturnItem(result, mrnReturnItemDTO, mrnReturnItem);
			mrnReturnItems.add(mrnReturnItem);
			}
		}
	result.getDomainEntity().setReturnItems(mrnReturnItems);
	}
	
	private void createMRNReturnItem(MRNReturnResult result, MRNReturnItemDTO mrnReturnItemDTO, MRNReturnItem mrnReturnItem) {
		
		if (mrnReturnItemDTO.getMrnItemId() != null) {
			mrnReturnItem.setMrnItem(mrnItemDao.findOne(mrnReturnItemDTO.getMrnItemId()));
		}
		
		mrnReturnItem.setMrnReturn(result.getDomainEntity()); 
		mrnReturnItem.setMrnItemCurrentQuantity(mrnReturnItemDTO.getItemQuantity());
		mrnReturnItem.setReturnQuantity(mrnReturnItemDTO.getItemReturnQuantity());
		mrnReturnItem.setDescription(mrnReturnItemDTO.getDescription());
		if(result.getDomainEntity().getMrnReturnStatus().equals(MRNReturnStatus.APPROVED)){
			MRNItem mrnItem=mrnItemDao.findOne(mrnReturnItem.getMrnItem().getId());
			BigDecimal mrnitemRemainingQuntity=mrnReturnItem.getMrnItemCurrentQuantity().subtract(mrnReturnItem.getReturnQuantity());
			mrnItem.setRemainQuantity(mrnitemRemainingQuntity);
			mrnItemDao.save(mrnItem);
		}
	}
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private MRNReturn findEntityById(Integer id) throws Exception {
		return mrnReturnDao.findOne(id);
	}
	
	private MRNReturnDTO findDTOById(Integer id) throws Exception {
		return MRNReturnMapper.getInstance().domainToDto(findEntityById(id));
	}
	@Override
	public MRNReturnResult delete(Integer id) throws Exception {
		MRNReturnResult result = new MRNReturnResult(null, null);
		try {
			deleteEntityById(id);
			result.setResultStatusSuccess();
			result.addToMessageList("MRN Deleted Successfully.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToMessageList("MRN Deleted Unsuccessful. ".concat(ex.getMessage()));
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void deleteEntityById(Integer id) throws Exception {
		mrnReturnDao.delete(id);
	} 


	@Override
	public MRNReturnResult findById(Integer id) throws Exception {
		MRNReturnResult result = new MRNReturnResult(null, null);
		try {
			result.setDtoEntity(findDTOById(id));
			result.setResultStatusSuccess();
			result.addToMessageList("MRN Found.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToMessageList("Error Occurred! MRN NOT Found.".concat(ex.getMessage()));
		}
		return result;
	}

	public MRNReturnResult statusChange(Integer id, MRNReturnStatus status)  {
		MRNReturnResult result = new MRNReturnResult(null, null);
		try {
			MRNReturnDTO dto=findDTOById(id);
			dto.setMrnReturnStatus(status);
			MRNReturn domain = findEntityById(dto.getId());
			String  previousStatus=domain.getMrnReturnStatus().getName();
			result.setDtoEntity(dto);
			//result.setDomainEntity(domain);
			update(result.getDtoEntity());
		result.addToMessageList("MRN Return Status Updated Successfully. "+previousStatus+" --> "+ status.getName());
		} catch (ObjectOptimisticLockingFailureException ex) {
			result.setResultStatusError();
			result.addToErrorList("MRN Return Already updated. Please Reload MRN Return.");
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(e.getMessage());
		}
		return result;
	}



	@Override
	public DataTablesOutput<MRNReturnDTO> findAll(FocusDataTablesInput input) throws Exception {
		//AODPropertyMapper.getInstance().generateDataTableInput(input);
		DataTablesOutput<MRNReturn> domainOut;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			domainOut = mrnReturnDao.findAll(input);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			Specification<MRNReturn> specification = (root, query, cb) -> cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
			domainOut = mrnReturnDao.findAll(input, specification);
		} else {
			Specification<MRNReturn> specification = (root, query, cb) -> cb.and(
					cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness()),
					cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite()) );
			domainOut = mrnReturnDao.findAll(input, specification);
		}
		DataTablesOutput<MRNReturnDTO> out = MRNReturnMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}


}
