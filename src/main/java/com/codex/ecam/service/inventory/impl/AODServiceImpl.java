package com.codex.ecam.service.inventory.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.constants.inventory.AODStatus;
import com.codex.ecam.constants.util.AffixList;
import com.codex.ecam.dao.admin.UserDao;
import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dao.inventory.AODDao;
import com.codex.ecam.dao.inventory.AODItemDao;
import com.codex.ecam.dao.inventory.StockDao;
import com.codex.ecam.dao.maintenance.WorkOrderDao;
import com.codex.ecam.dto.inventory.aod.AODDTO;
import com.codex.ecam.dto.inventory.aod.AODFilterDTO;
import com.codex.ecam.dto.inventory.aod.AODItemDTO;
import com.codex.ecam.dto.inventory.aod.AODRepDTO;
import com.codex.ecam.exception.inventory.StockQuantityExceedException;
import com.codex.ecam.mappers.inventory.aod.AODItemMapper;
import com.codex.ecam.mappers.inventory.aod.AODMapper;
import com.codex.ecam.mappers.inventory.aod.AODReportMapper;
import com.codex.ecam.model.inventory.aod.AOD;
import com.codex.ecam.model.inventory.aod.AODItem;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.inventory.AODResult;
import com.codex.ecam.service.inventory.api.AODService;
import com.codex.ecam.service.inventory.api.StockService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.CommonUtil;
import com.codex.ecam.util.search.inventory.aod.AODItemPropertyMapper;
import com.codex.ecam.util.search.inventory.aod.AODPropertyMapper;

@Service
public class AODServiceImpl implements AODService {

	@Autowired
	private AODDao aodDao;

	@Autowired
	private AODItemDao aodItemDao;

	@Autowired
	private AssetDao assetDao;

	@Autowired
	private BusinessDao businessDao;

	@Autowired
	private StockDao stockDao;

	@Autowired
	private StockService stockService;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private WorkOrderDao workOrderDao;


	private AODDTO findDTOById(Integer id) throws Exception {
		return AODMapper.getInstance().domainToDto(findEntityById(id));
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private AOD findEntityById(Integer id) throws Exception {
		return aodDao.findOne(id);
	}


	@Override
	public AODResult newAOD() {
		AODResult result = new AODResult(null, null);
		try {
			result.setDtoEntity(nextAOD());
			result.setResultStatusSuccess();
			result.addToErrorList("New AOD Return Created!");
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("AOD Return NOT Created ".concat(e.getMessage()));

		}
		return result;
	}

	private AODDTO nextAOD() {
		AODDTO dto = new AODDTO();
		dto.setAodNo(getNextCode());
		return dto;
	}

	private String getNextCode() {
		AOD lastDomain = aodDao.findLastDomain();
		Integer year = Calendar.getInstance().get(Calendar.YEAR);
		Integer nextNo = 0;
		if ((lastDomain == null) || (lastDomain.getId() == null)) {
			nextNo = 1;
		} else {
			List<String> codeList = Arrays.asList(lastDomain.getAodNo().split("/"));
			if (!codeList.get(0).equalsIgnoreCase(AffixList.AOD.getCode())) {
				nextNo = 1;
			} else if (Integer.parseInt(codeList.get(1)) == year) {
				nextNo = Integer.parseInt(codeList.get(2)) + 1;
			} else {
				nextNo = 1;
			}
		}
		return CommonUtil.setNextCode(AffixList.AOD.getCode(), nextNo.toString());
	}


	@Override
	public AODResult save(AODDTO dto) throws Exception {
		AODResult result = new AODResult(new AOD(), dto);
		try {
			saveOrUpdate(result);
			result.setResultStatusSuccess();
			result.addToMessageList("AOD Added Successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToMessageList("Receipt order save Unsuccessful. ".concat(e.getMessage()));
		}
		return result;
	}

	@Override
	public AODResult update(AODDTO dto) throws Exception {
		AODResult result = new AODResult(null, dto);
		try {
			AOD domain = findEntityById(dto.getId());
			result.setDomainEntity(domain);
			saveOrUpdate(result);
			result.addToMessageList("AOD Updated Successfully.");
		} catch (ObjectOptimisticLockingFailureException ex) {
			result.setResultStatusError();
			result.addToErrorList("AOD Already updated. Please Reload AOD.");
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(e.getMessage());
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(AODResult result) throws Exception {
		AODMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setAODData(result);
		aodDao.save(result.getDomainEntity()); 
		result.setDtoEntity(findDTOById(result.getDomainEntity().getId())); 
	}

	private void setAODData(AODResult result) throws Exception {
		setAODCustomer(result);
		setAODRequestUser(result);
		setWorkOrder(result);
		setAODStatus(result);
		setAODItem(result);
		setBusinessSite(result);
		setNextAODNo(result);
	}

	private void setAODCustomer(AODResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getAodCustomerId() != null)) {
			result.getDomainEntity().setCustomer(businessDao.findOne(result.getDtoEntity().getAodCustomerId()));
		}
	}

	private void setAODRequestUser(AODResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getRequestedUserId() != null)) {
			result.getDomainEntity().setRequestedBy(userDao.findOne(result.getDtoEntity().getRequestedUserId()));
		}
	}

	private void setAODStatus(AODResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getAodStatus() != null)) {
			result.getDomainEntity().setAodStatus(result.getDtoEntity().getAodStatus());
		}
	}
	
	private void setWorkOrder(AODResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getWoId() != null) {
			result.getDomainEntity().setWorkOrder(workOrderDao.findOne(result.getDtoEntity().getWoId()));
		}
	}

	private void setAODItem(AODResult result) {
		
		Set<AODItem> aodItems = new HashSet<>();
		List<AODItemDTO> aodItemDTOs = result.getDtoEntity().getAodItemList();
		
		if (aodItemDTOs != null && aodItemDTOs.size() > 0) {
			
			Set<AODItem> currentAODItems = result.getDomainEntity().getAodItemList();
			
			for ( AODItemDTO aodItemDTO : aodItemDTOs ) {
				
				AODItem aodItem = new AODItem();
				
				if ((currentAODItems != null) && (currentAODItems.size() > 0)) {
					AODItem optional = currentAODItems.stream().filter((x) -> x.getId().equals(aodItemDTO.getId())).findAny().orElseGet(AODItem :: new);
					aodItem = optional; 
				} else {
					aodItem = new AODItem();
				}
				
				createAODItem(result, aodItemDTO, aodItem);
				aodItems.add(aodItem);
			}
		}
		result.getDomainEntity().setAodItemList(aodItems);
	}

	private void createAODItem(AODResult result, AODItemDTO aodItemDTO, AODItem aodItem) {
		
		if (aodItemDTO.getPartId() != null) {
			aodItem.setPart(assetDao.findOne(aodItemDTO.getPartId()));
		}
		if ((aodItemDTO.getStockId() != null) && (aodItemDTO.getStockId() > 0)) {
			aodItem.setStock(stockDao.findOne(aodItemDTO.getStockId()));
		}
		if ((aodItemDTO.getWarehouseId() != null) && (aodItemDTO.getWarehouseId() > 0)) {
			aodItem.setWarehouse(assetDao.findOne(aodItemDTO.getWarehouseId()));
		}
		
		aodItem.setAod(result.getDomainEntity()); 
		aodItem.setQuantity(aodItemDTO.getItemQuantity());
		aodItem.setDescription(aodItemDTO.getDescription());
	}

	private void setBusinessSite(AODResult result) {
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

	private void setNextAODNo(AODResult result) {
		if (result.getDomainEntity().getId() == null) {
			result.getDomainEntity().setAodNo(getNextCode());
		}
	} 

	@Override
	public AODResult delete(Integer id) throws Exception {
		AODResult result = new AODResult(null, null);
		try {
			deleteEntityById(id);
			result.setResultStatusSuccess();
			result.addToMessageList("AOD Deleted Successfully.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToMessageList("AOD Deleted Unsuccessful. ".concat(ex.getMessage()));
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void deleteEntityById(Integer id) throws Exception {
		aodDao.delete(id);
	} 

	@Override
	public AODResult findById(Integer id) throws Exception {
		AODResult result = new AODResult(null, null);
		try {
			result.setDtoEntity(findDTOById(id));
			result.setResultStatusSuccess();
			result.addToMessageList("AOD Found.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToMessageList("Error Occurred! AOD NOT Found.".concat(ex.getMessage()));
		}
		return result;
	}

	@Override
	public AODResult statusChange(Integer id, AODStatus status) {
		AODResult result = new AODResult(null, null);
		try {
			result.setDomainEntity(findEntityById(id));
			aodStatusChange(result);
			result.setResultStatusSuccess();
			result.addToMessageList("AOD status change successful.");
		} catch (StockQuantityExceedException e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("Error Occurred! AOD Status Cannot change. ".concat(e.getMessage())); 
		} 
		catch (Exception e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("Error Occurred! AOD Status Cannot change. ".concat(e.getMessage()));
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void aodStatusChange(AODResult result) throws Exception {
		approveAOD(result); 
	}
	
	private void approveAOD(AODResult result) throws Exception { 
		stockService.dispatchStock(result.getDomainEntity()); 
		result.getDomainEntity().setAodStatus(AODStatus.APPROVED);
		aodDao.save(result.getDomainEntity());
		result.setDtoEntity(findDTOById(result.getDomainEntity().getId()));
		result.setResultStatusSuccess(); 
	}

	@Override
	public AODRepDTO findAODRepById(Integer id) throws Exception {
		try {
			return AODReportMapper.getInstance().domainToRepDTO(findEntityById(id));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public BigDecimal getAODItemRemainQty(Integer aodItemId) {
		AODItem aodItem = aodItemDao.findOne(aodItemId);
		return aodItem.getQuantity().subtract(aodItem.getReturnQuantity());
	}

	@Override
	public List<AODRepDTO> findAll(AODFilterDTO aodFilterDTO) throws Exception {
		List<AOD> domainList = null;
		try {
			Specification<AOD> specification = (root, query, cb) -> {
				List<Predicate> predicates = new ArrayList<>();
				if ((aodFilterDTO != null) && (aodFilterDTO.getAodStatus() != null) && (aodFilterDTO.getAodStatus().getId() != null)) {
					predicates.add(cb.equal(root.get("aodStatus"), aodFilterDTO.getAodStatus()));
				}
				if ((aodFilterDTO != null) && (!aodFilterDTO.getAodNo().equalsIgnoreCase("") == Boolean.TRUE)) {
					predicates.add(cb.like(cb.lower(root.get("aodNo")), "%" + aodFilterDTO.getAodNo().toLowerCase() + "%"));
				}
				if ((aodFilterDTO != null) && (aodFilterDTO.getAodType() != null) && (aodFilterDTO.getAodType().getId() != null)) {
					predicates.add(cb.equal(root.get("aodType"), aodFilterDTO.getAodType()));
				}
				if ((aodFilterDTO != null) && (aodFilterDTO.getAodDate() != null)) {
					predicates.add(cb.equal(root.get("date"), aodFilterDTO.getAodDate()));
				}
				if ((aodFilterDTO != null) && (aodFilterDTO.getRequestedByUserId() != null)) {
					predicates.add(cb.equal(root.get("requestedBy").get("id"), aodFilterDTO.getRequestedByUserId()));
				}
				if ((aodFilterDTO != null) && (aodFilterDTO.getCustomerId() != null)) {
					predicates.add(cb.equal(root.get("customer").get("id"), aodFilterDTO.getCustomerId()));
				}
				if ((aodFilterDTO != null) && (aodFilterDTO.getJobId() != null)) {
					predicates.add(cb.equal(root.get("job").get("id"), aodFilterDTO.getJobId()));
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			};
			domainList = aodDao.findAll(specification);
			return AODReportMapper.getInstance().domainToRepDTOList(domainList);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<AODDTO> findAll() {
		try {
			return AODMapper.getInstance().domainToDTOList(aodDao.findAll());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public DataTablesOutput<AODDTO> findAll(FocusDataTablesInput input) throws Exception {
		AODPropertyMapper.getInstance().generateDataTableInput(input);
		DataTablesOutput<AOD> domainOut;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			domainOut = aodDao.findAll(input);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			Specification<AOD> specification = (root, query, cb) -> cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
			domainOut = aodDao.findAll(input, specification);
		} else {
			Specification<AOD> specification = (root, query, cb) -> cb.and(
					cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness()),
					cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite()) );
			domainOut = aodDao.findAll(input, specification);
		}
		DataTablesOutput<AODDTO> out = AODMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	@Override
	public DataTablesOutput<AODItemDTO> findAll(FocusDataTablesInput input, Integer id) {
		DataTablesOutput<AODItemDTO> out = new DataTablesOutput<>();
		AODItemPropertyMapper.getInstance().generateDataTableInput(input);
		DataTablesOutput<AODItem> domainOut = new DataTablesOutput<>();
		try {
			if (id != null) {
				if (AuthenticationUtil.isAuthUserAdminLevel()) {
					Specification<AODItem> specification = (root, query, cb) -> cb.equal(root.get("aod").get("id"), id);
					domainOut = aodItemDao.findAll(input, specification);
				} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
					Specification<AODItem> specification = (root, query, cb) -> cb.and(
							cb.equal(root.get("aod").get("business"), AuthenticationUtil.getLoginUserBusiness()),
							cb.equal(root.get("aod").get("id"), id)
							);
					domainOut = aodItemDao.findAll(input, specification);
				} else {
					Specification<AODItem> specification = (root, query, cb) -> cb.and(
							cb.equal(root.get("aod").get("business"), AuthenticationUtil.getLoginUserBusiness()),
							cb.equal(root.get("aod").get("site"), AuthenticationUtil.getLoginSite().getSite()),
							cb.equal(root.get("aod").get("id"), id)
							);
					domainOut = aodItemDao.findAll(input, specification);
				}
			}
			out = AODItemMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return out;
	}


}
