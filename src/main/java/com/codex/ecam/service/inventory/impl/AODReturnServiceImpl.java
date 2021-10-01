package com.codex.ecam.service.inventory.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.persistence.criteria.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.constants.inventory.AODReturnStatus;
import com.codex.ecam.constants.util.AffixList;
import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dao.inventory.AODDao;
import com.codex.ecam.dao.inventory.AODItemDao;
import com.codex.ecam.dao.inventory.AODReturnDao;
import com.codex.ecam.dto.inventory.aodReturn.AODReturnDTO;
import com.codex.ecam.dto.inventory.aodReturn.AODReturnFilterDTO;
import com.codex.ecam.dto.inventory.aodReturn.AODReturnItemDTO;
import com.codex.ecam.dto.inventory.aodReturn.AODReturnRepDTO;
import com.codex.ecam.mappers.inventory.aodReturn.AODReturnItemMapper;
import com.codex.ecam.mappers.inventory.aodReturn.AODReturnMapper;
import com.codex.ecam.mappers.inventory.aodReturn.AODReturnReportMapper;
import com.codex.ecam.model.inventory.aod.AODItem;
import com.codex.ecam.model.inventory.aod.AODItemStock;
import com.codex.ecam.model.inventory.aodRetun.AODReturn;
import com.codex.ecam.model.inventory.aodRetun.AODReturnItem;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.inventory.AODReturnResult;
import com.codex.ecam.service.inventory.api.AODReturnService;
import com.codex.ecam.service.inventory.api.StockService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.CommonUtil;
import com.codex.ecam.util.search.inventory.aodReturn.AODReturnPropertyMapper;

@Service
public class AODReturnServiceImpl implements AODReturnService {

	final static Logger logger = LoggerFactory.getLogger(AODReturnServiceImpl.class);

	@Autowired
	private AODReturnDao aodReturnDao;

	@Autowired
	private AODDao aodDao;

	@Autowired
	private AODItemDao aodItemDao;

	@Autowired
	private BusinessDao businessDao;

	@Autowired
	private AssetDao assetDao;
	@Autowired
	private StockService stockService;

	public AODReturnDTO findDTOById(Integer id) throws Exception {
		return AODReturnMapper.getInstance().domainToDto(findEntityById(id));
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private AODReturn findEntityById(Integer id) {
		return aodReturnDao.findOne(id);
	}


	@Override
	public AODReturnResult newAODReturn() {
		final AODReturnResult result = new AODReturnResult(null, null);
		try {
			result.setDtoEntity(nextItem());
			result.setResultStatusSuccess();
			result.addToErrorList("New AOD Created!");
		} catch (final Exception e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("AOD NOT Created ".concat(e.getMessage()));
			logger.error(e.getMessage());
		}
		return result;
	}

	private AODReturnDTO nextItem() throws Exception {
		final AODReturnDTO dto = new AODReturnDTO();
		dto.setReturnNo(getNextCode());
		return dto;
	}
	private String getNextCode() {
		Integer nextNo = 0;
		final Integer year = Calendar.getInstance().get(Calendar.YEAR);
		final AODReturn lastDomain = aodReturnDao.findLastDomain();
		if (lastDomain == null || lastDomain.getId() == null) {
			nextNo = 1;
		} else {
			final List<String> codeList = Arrays.asList(lastDomain.getReturnNo().split("/"));
			if (!codeList.get(0).equalsIgnoreCase(AffixList.AOD_RETURN.getCode())) {
				nextNo = 1;
			} else if (Integer.parseInt(codeList.get(1)) == year) {
				nextNo = Integer.parseInt(codeList.get(2)) + 1;
			} else {
				nextNo = 1;
			}
		}
		return CommonUtil.setNextCode(AffixList.AOD_RETURN.getCode(), nextNo.toString());
	}

	private void setNextCode(AODReturnResult result) {
		if (result.getDomainEntity().getId() == null) {
			result.getDomainEntity().setReturnNo(getNextCode());
		}
	}
	@Override
	public AODReturnResult save(AODReturnDTO dto) throws Exception {
		final AODReturnResult result = new AODReturnResult(new AODReturn(), dto);
		try {
			saveOrUpdate(result);
			result.setResultStatusSuccess();
			result.addToMessageList("AOD Return save operation SUCCESS.");
		} catch (final Exception ex) {
			result.setResultStatusError();
			result.addToErrorList("AOD Return save operation UNSUCCESS. ".concat(ex.getMessage()));
			logger.error(ex.getMessage());
		}
		return result;
	}
	@Override
	public AODReturnResult update(AODReturnDTO dto) throws Exception {
		final AODReturnResult result = new AODReturnResult(null, dto);
		try {
			result.setDomainEntity(findEntityById(dto.getId()));
			saveOrUpdate(result);
			result.addToMessageList("AOD Return Updated Successfully.");
		} catch (final ObjectOptimisticLockingFailureException ex) {
			result.setResultStatusError();
			result.addToErrorList("AODReturn Already updated. Please Reload AODReturn.");
			logger.error(ex.getMessage(), "AODReturn Already updated. Please Reload AODReturn.");
		} catch (final Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
			logger.error(ex.getMessage());
		}
		return result;
	}
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(AODReturnResult result) throws Exception {
		AODReturnMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setDomainData(result);
		aodReturnDao.save(result.getDomainEntity());
		result.setDtoEntity(findDTOById(result.getDomainEntity().getId()));
	}

	private void setDomainData(AODReturnResult result) throws Exception {
		setAODReturnItem(result);
		setAOD(result);
		setBusinessSite(result);
		setNextCode(result);
	}

	private void setAODReturnItem(AODReturnResult result) throws Exception {
		final Set<AODReturnItem> returnItems = new HashSet<>();
		if (result.getDtoEntity().getAodReturnItemList() != null && result.getDtoEntity().getAodReturnItemList().size() > 0) {
			final Set<AODReturnItem> currentItems = result.getDomainEntity().getAodReturnItems();
			for (final AODReturnItemDTO aodItemDTO : result.getDtoEntity().getAodReturnItemList()) {
				AODReturnItem returnItem;
				if (currentItems != null && currentItems.size() > 0) {
					final Optional<AODReturnItem> optional = currentItems.stream().filter((x) -> x.getId().equals(aodItemDTO.getId())).findAny();
					returnItem = optional.orElseGet(AODReturnItem::new);
				} else {
					returnItem = new AODReturnItem();
				}
				try {
					AODReturnItemMapper.getInstance().dtoToDomain(aodItemDTO, returnItem);
				} catch (final Exception e) {
					e.printStackTrace();
				}
				if (aodItemDTO.getAodItemId() != null) {
					final AODItem aodItem = aodItemDao.findOne(aodItemDTO.getAodItemId());


					if (aodItem.getQuantity() != null && returnItem.getReturnQty().compareTo(aodItem.getQuantity()) > 0) {
						new RuntimeException("Error! AOD Return Item quantity should be less than AOD Item quantity");
					}
					/*
                    if (aodItem.getQuantity() != null && returnItem.getReturnQty() > aodItem.getQuantity()) {
                        new RuntimeException("Error! AOD Return Item quantity should be less than AOD Item quantity");
                    }
					 */
					returnItem.setAodItem(aodItem);
				}
				/*
                AODItem aodItem = aodItemDao.findOne(aodItemDTO.getAodItemId());
				returnItem.setAodItem(aodItem);
				 */
				returnItem.setAodReturn(result.getDomainEntity());
				returnItems.add(returnItem);
			}
		}
		result.getDomainEntity().setAodReturnItems(returnItems);
	}

	private void setAOD(AODReturnResult result) {
		if (result.getDtoEntity().getAodId() != null && result.getDtoEntity().getAodId() > 0) {
			result.getDomainEntity().setAod(aodDao.findOne(result.getDtoEntity().getAodId()));
		}
	}

	private void setBusinessSite(AODReturnResult result) {
		if (result.getDtoEntity().getBusinessId() != null && result.getDtoEntity().getBusinessId() > 0) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		} else {
			result.getDomainEntity().setBusiness(AuthenticationUtil.getLoginUserBusiness());
		}

		if (result.getDtoEntity().getSiteId() != null && result.getDtoEntity().getSiteId() > 0) {
			result.getDomainEntity().setSite(assetDao.findOne(result.getDtoEntity().getSiteId()));
		} else if (!AuthenticationUtil.isAuthUserAdminLevel()) {
			result.getDomainEntity().setSite(AuthenticationUtil.getLoginSite().getSite());
		}
	}

	@Override
	public AODReturnResult delete(Integer id) throws Exception {
		final AODReturnResult result = new AODReturnResult(null, null);
		try {
			deleteEntityById(id);
			result.setResultStatusSuccess();
			result.addToMessageList("AOD Return delete operation SUCCESS.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("AOD Return delete operation unsuccessful.".concat(ex.getMessage()));
			logger.error(ex.getMessage());
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AODReturnResult deleteMultiple(Integer[] ids) throws Exception {
		final AODReturnResult result = new AODReturnResult(null, null);
		try {
			for (final Integer id : ids) {
				deleteEntityById(id);
			}
			result.setResultStatusSuccess();
			result.addToMessageList("AODReturn(s) Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("AODReturn(s) Already Used. Cannot delete.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void deleteEntityById(Integer id) throws Exception {
		aodReturnDao.delete(id);
	}

	@Override
	public AODReturnResult findById(Integer id) throws Exception {
		final AODReturnResult result = new AODReturnResult(null, null);
		try {
			result.setDtoEntity(findDTOById(id));
			result.setResultStatusSuccess();
			result.addToMessageList("AOD Return Found.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToMessageList("Error Occurred! AOD Return NOT Found.".concat(ex.getMessage()));
		}
		return result;
	}

	@Override
	public AODReturnResult returnByAODItem() {
		final AODReturnResult result = new AODReturnResult(null, null);
		try {
			result.setDtoEntity(AODReturnByAOD());
			result.setResultStatusSuccess();
			result.addToMessageList("AOD Return Created By AOD .");
		} catch (final Exception e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToMessageList("Error Occurred! AOD Return NOT Created. ".concat(e.getMessage()));
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private AODReturnDTO AODReturnByAOD() throws Exception {
		final AODReturnDTO returnDTO = new AODReturnDTO();
		returnDTO.setReturnNo("r-aod-ots" + new Random().nextInt(12));
		returnDTO.setAodReturnItemList((List<AODReturnItemDTO>) aodReturnItemByAODItem());
		return returnDTO;
	}

	@SuppressWarnings("unused")
	private Set<AODReturnItemDTO> aodReturnItemByAODItem() throws Exception {
		final Set<AODReturnItemDTO> aodReturnItemDTOList = new HashSet<>();
		for (final AODReturnItemDTO returnDTO : aodReturnItemDTOList) {
			final AODReturnItemDTO itemDTO = new AODReturnItemDTO();
			aodReturnItemDTOList.add(itemDTO);
		}
		return aodReturnItemDTOList;
	}

	@Override
	public List<AODReturnDTO> getUnFinalizedAODReturns() {
		final List<AODReturnDTO> dtos = new ArrayList<>();
		final List<AODReturn> aodReturns = aodReturnDao.findAODReturnByStatus(AODReturnStatus.DRAFT.getId());
		try {
			for (final AODReturn aodReturn : aodReturns) {
				aodReturn.setAodReturnItems(null);
				dtos.add(AODReturnMapper.getInstance().domainToDto(aodReturn));
			}
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<AODReturnDTO> findAll() {
		try {
			return AODReturnMapper.getInstance().domainToDTOList(aodReturnDao.findAll());
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public AODReturnRepDTO findAODReturnRepById(Integer id) {
		try {
			return AODReturnReportMapper.getInstance().domainToRepDTO(aodReturnDao.findOne(id));
		} catch (final Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AODReturnResult statusChange(Integer id, AODReturnStatus status) throws Exception {
		final AODReturnResult result = new AODReturnResult(null, null);
		try {
			updateStatus(result, id, status);
			aodReturnDao.save(result.getDomainEntity());
			if (result.getDomainEntity().getAodReturnStatus() == AODReturnStatus.APPROVED) {
				updateAODItem(result);
			}
			result.setResultStatusSuccess();
			result.addToMessageList("AOD Return Status Successfully Changed!");
		} catch (final Exception e) {
			result.setResultStatusError();
			result.addToErrorList("AOD Return Status Unsuccessful.Status NOT Changed !");
			e.printStackTrace();
		}
		result.setDtoEntity(findById(result.getDomainEntity().getId()).getDtoEntity());
		return result;
	}


	private void updateStatus(AODReturnResult result, Integer id, AODReturnStatus status) throws Exception {
		result.setDomainEntity(aodReturnDao.findOne(id));
		result.getDomainEntity().setAodReturnStatus(status);
	}

	private void updateAODItem(AODReturnResult result) throws Exception {
		for (final AODReturnItem returnItem : result.getDomainEntity().getAodReturnItems()) {
			try {
				if (returnItem.getAodItem() != null) {
					final AODItem aodItem = aodItemDao.findOne(returnItem.getAodItem().getId());
					aodItem.setReturnQuantity(aodItem.getReturnQuantity());
					if(aodItem.getQuantity().subtract(aodItem.getReturnQuantity()).compareTo(BigDecimal.ZERO) >=0){
						aodItem.getStock().getCurrentQuantity().add(aodItem.getReturnQuantity());
					}
					BigDecimal returnQty = returnItem.getReturnQty();
					//Collections.sort(aodItem.getAodItemStocks(), (a, b) -> ((AODItem) a).getStock().getId().compareTo(b.getStock().getId()));
					//Collections.reverse(aodItem.getAodItemStocks());
					if (aodItem.getAodItemStocks() != null && aodItem.getAodItemStocks().size() > 0) {
						//								Double totalReturnQty =  aodItem.getAodItemStocks().stream().mapToDouble(obj -> obj.getReturnQuantity()).sum();
						//								if (aodItem.getQuantity().compareTo(BigDecimal.valueOf(totalReturnQty).add(returnQty)) < 0) {
						//									throw new RuntimeException(" Total Return Item Qty is more than Total AOD Qty");
						//								}
					}
					for (final AODItemStock aodItemStock : aodItem.getAodItemStocks()) {
						if (aodItemStock.getQuantity().equals(aodItemStock.getReturnQuantity())) {
							continue;
						}
						if (aodItemStock.getQuantity().compareTo(returnQty) >= 0 && returnQty.equals(BigDecimal.ZERO)) {
							aodItemStock.setReturnQuantity(aodItemStock.getReturnQuantity().add(returnQty));
							updateStock(aodItemStock, returnQty);
							returnQty = BigDecimal.ZERO;
						} else {
							aodItemStock.setReturnQuantity(aodItemStock.getQuantity());
							returnQty = returnQty.subtract(aodItemStock.getQuantity());
							updateStock(aodItemStock, aodItemStock.getQuantity());
						}
					}
					aodItemDao.save(aodItem);
					result.setResultStatusSuccess();
					result.addToMessageList("AOD Item Return value update Successful!");
				}
			} catch (final Exception ex) {
				ex.printStackTrace();
				result.setResultStatusError();
				result.addToErrorList("AOD Item Return value update Not Successful!");
				logger.error(ex.getMessage());
			}
		}

	}

	private void updateStock(AODItemStock aodItemStock, BigDecimal returnQty) throws Exception {
		if (aodItemStock.getStock() != null) {
			aodItemStock.setStock(stockService.dispatchedReturn(aodItemStock.getStock().getId(), returnQty));
		}
	}


	@Override
	public List<AODReturnRepDTO> findAll(AODReturnFilterDTO aodReturnFilterDTO) {
		List<AODReturn> domainList = null;
		try {
			final Specification<AODReturn> specification = (root, query, cb) -> {
				final List<Predicate> predicates = new ArrayList<>();
				if (aodReturnFilterDTO != null && aodReturnFilterDTO.getAodReturnStatus() != null && aodReturnFilterDTO.getAodReturnStatus().getId() != null) {
					predicates.add(cb.equal(root.get("aodReturnStatus"), aodReturnFilterDTO.getAodReturnStatus()));
				}
				if (aodReturnFilterDTO != null && !aodReturnFilterDTO.getReturnNo().equalsIgnoreCase("") == Boolean.TRUE) {
					predicates.add(cb.like(cb.lower(root.get("returnNo")), "%" + aodReturnFilterDTO.getReturnNo().toLowerCase() + "%"));
				}
				if (aodReturnFilterDTO != null && !aodReturnFilterDTO.getRefNo().equalsIgnoreCase("") == Boolean.TRUE) {
					predicates.add(cb.like(cb.lower(root.get("returnRefNo")), "%" + aodReturnFilterDTO.getRefNo().toLowerCase() + "%"));
				}
				if (aodReturnFilterDTO != null && aodReturnFilterDTO.getAodReturnDate() != null) {
					predicates.add(cb.equal(root.get("returnDate"), aodReturnFilterDTO.getAodReturnDate()));
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			};
			domainList = aodReturnDao.findAll(specification);
			return AODReturnReportMapper.getInstance().domainToRepDTOList(domainList);
		} catch (final Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}


	@Override
	public DataTablesOutput<AODReturnDTO> findAll(FocusDataTablesInput input) throws Exception {
		AODReturnPropertyMapper.getInstance().generateDataTableInput(input);
		DataTablesOutput<AODReturn> domainOut;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			domainOut = aodReturnDao.findAll(input);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			final Specification<AODReturn> specification = (root, query, cb) -> cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
			domainOut = aodReturnDao.findAll(input, specification);
		} else {
			final Specification<AODReturn> specification = (root, query, cb) -> cb.and(
					cb.equal(root.get("business"), AuthenticationUtil.getLoginSite().getSite().getBusiness()),
					cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite())
					);
			domainOut = aodReturnDao.findAll(input, specification);
		}
		final DataTablesOutput<AODReturnDTO> out = AODReturnMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

}
