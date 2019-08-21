package com.codex.ecam.service.inventory.impl;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dao.inventory.InventoryGroupDao;
import com.codex.ecam.dto.inventory.inventoryGroup.InventoryGroupDTO;
import com.codex.ecam.dto.inventory.inventoryGroup.InventoryGroupPartDTO;
import com.codex.ecam.mappers.inventory.InventoryGroup.InventoryGroupMapper;
import com.codex.ecam.model.inventory.inventoryGroup.InventoryGroup;
import com.codex.ecam.model.inventory.inventoryGroup.InventoryGroupPart;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.inventory.InventoryGroupResult;
import com.codex.ecam.service.inventory.api.InventoryGroupService;
import com.codex.ecam.util.AuthenticationUtil;

@Service
public class InventoryGroupServiceImpl implements InventoryGroupService {

	final static Logger logger = LoggerFactory.getLogger(InventoryGroupServiceImpl.class);

	@Autowired
	private InventoryGroupDao inventoryGroupDao;

	@Autowired
	private BusinessDao businessDao;

	@Autowired
	private AssetDao assetDao;

	public InventoryGroupDTO findDTOById(Integer id) throws Exception {
		return InventoryGroupMapper.getInstance().domainToDto(findEntityById(id));
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private InventoryGroup findEntityById(Integer id) {
		return inventoryGroupDao.findOne(id);
	}

	@Override
	public InventoryGroupResult newInventoryGroup() {
		InventoryGroupResult result = new InventoryGroupResult(null, null);
		try {
			result.setDtoEntity(nextItem());
			result.setResultStatusSuccess();
			result.addToErrorList("New Inventory Group Created!");
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("Inventory Group NOT Created ".concat(e.getMessage()));
			logger.error(e.getMessage());
		}
		return result;
	}

	private InventoryGroupDTO nextItem() throws Exception {
		InventoryGroupDTO dto = new InventoryGroupDTO();
		return dto;
	}

	@Override
	public InventoryGroupResult save(InventoryGroupDTO dto) throws Exception {
		InventoryGroupResult result = new InventoryGroupResult(new InventoryGroup(), dto);
		try {
			saveOrUpdate(result);
			result.setResultStatusSuccess();
			result.addToMessageList("Inventory Group save operation SUCCESS.");
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList("Inventory Group save operation UNSUCCESS. ".concat(ex.getMessage()));
			logger.error(ex.getMessage());
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(InventoryGroupResult result) throws Exception {
		InventoryGroupMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setDomainData(result);
		inventoryGroupDao.save(result.getDomainEntity());
		result.setDtoEntity(findDTOById(result.getDomainEntity().getId()));
	}

	@Override
	public InventoryGroupResult update(InventoryGroupDTO dto) throws Exception {
		InventoryGroupResult result = new InventoryGroupResult(null, dto);
		try {
			result.setDomainEntity(findEntityById(dto.getId()));
			saveOrUpdate(result);
			result.addToMessageList("Inventory Group Updated Successfully.");
		} catch (ObjectOptimisticLockingFailureException ex) {
			result.setResultStatusError();
			result.addToErrorList("Inventory Group Already updated. Please Reload Inventory Group.");
			logger.error(ex.getMessage(), "Inventory Group Already updated. Please Reload Inventory Group.");
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
			logger.error(ex.getMessage());
		}
		return result;
	}

	private void setDomainData(InventoryGroupResult result) throws Exception {
		setBusinessSite(result);
		setInventoryGroupPart(result);
	}

	private void setInventoryGroupPart(InventoryGroupResult result){
		Set<InventoryGroupPart> inventoryGroupParts = new HashSet<>();
		if ((result.getDtoEntity().getInventoryGroupPartDTOs() != null) && (result.getDtoEntity().getInventoryGroupPartDTOs().size() > 0)) {
			Set<InventoryGroupPart> currentItems = result.getDomainEntity().getInventoryGroupParts();
			for (InventoryGroupPartDTO inventoryGroupPartDTO : result.getDtoEntity().getInventoryGroupPartDTOs()) {
				InventoryGroupPart inventoryGroupPart;
				if ((currentItems != null) && (currentItems.size() > 0)) {
					inventoryGroupPart = currentItems.stream().filter((x) -> x.getId().equals(inventoryGroupPartDTO.getId())).findAny().orElseGet(InventoryGroupPart :: new);
				} else {
					inventoryGroupPart = new InventoryGroupPart();
				}
				inventoryGroupPart.setInventoryGroup(result.getDomainEntity());
				inventoryGroupPart.setPart(assetDao.findOne(inventoryGroupPartDTO.getPartId()));
				inventoryGroupParts.add(inventoryGroupPart);
			}
		}
		result.getDomainEntity().setInventoryGroupParts(inventoryGroupParts);
	}

	private void setBusinessSite(InventoryGroupResult result) {
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

	@Override
	public InventoryGroupResult delete(Integer id) throws Exception {
		InventoryGroupResult result = new InventoryGroupResult(null, null);
		try {
			deleteEntityById(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Inventory Group  delete operation SUCCESS.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("Inventory Group  delete operation unsuccessful.".concat(ex.getMessage()));
			logger.error(ex.getMessage());
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void deleteEntityById(Integer id) throws Exception {
		inventoryGroupDao.delete(id);
	}

	@Override
	public InventoryGroupResult findById(Integer id) throws Exception {
		InventoryGroupResult result = new InventoryGroupResult(null, null);
		try {
			result.setDtoEntity(findDTOById(id));
			result.setResultStatusSuccess();
			result.addToMessageList("Inventory Group Found.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToMessageList("Error Occurred! Inventory Group NOT Found.".concat(ex.getMessage()));
		}
		return result;
	}

	@Override
	public DataTablesOutput<InventoryGroupDTO> findAll(FocusDataTablesInput input) throws Exception {
		DataTablesOutput<InventoryGroup> domainOut;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			domainOut = inventoryGroupDao.findAll(input);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			Specification<InventoryGroup> specification = (root, query, cb) -> cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
			domainOut = inventoryGroupDao.findAll(input, specification);
		} else {
			Specification<InventoryGroup> specification = (root, query, cb) -> cb.and(
					cb.equal(root.get("business"), AuthenticationUtil.getLoginSite().getSite().getBusiness()),
					cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite())
					);
			domainOut = inventoryGroupDao.findAll(input, specification);
		}
		DataTablesOutput<InventoryGroupDTO> out = InventoryGroupMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

}
