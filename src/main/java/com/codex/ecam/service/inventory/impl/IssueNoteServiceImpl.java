package com.codex.ecam.service.inventory.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.codex.ecam.constants.inventory.AODStatus;
import com.codex.ecam.constants.inventory.AODType;
import com.codex.ecam.constants.util.AffixList;
import com.codex.ecam.dao.admin.UserDao;
import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dao.biz.CustomerDao;
import com.codex.ecam.dao.inventory.AODDao;
import com.codex.ecam.dao.inventory.AODItemDao;
import com.codex.ecam.dao.inventory.StockDao;
import com.codex.ecam.dao.maintenance.WorkOrderDao;
import com.codex.ecam.dto.inventory.aod.AODItemDTO;
import com.codex.ecam.dto.inventory.aodReturn.AODReturnDTO;
import com.codex.ecam.dto.inventory.issuenote.IssueNoteDTO;
import com.codex.ecam.dto.inventory.issuenote.IssueNoteItemDTO;
import com.codex.ecam.dto.inventory.mrn.MRNItemDTO;
import com.codex.ecam.exception.inventory.stock.StockQuantityExceedException;
import com.codex.ecam.mappers.inventory.IssueNote.IssueNoteItemMapper;
import com.codex.ecam.mappers.inventory.IssueNote.IssueNoteMapper;
import com.codex.ecam.model.inventory.aod.AOD;
import com.codex.ecam.model.inventory.aod.AODItem;
import com.codex.ecam.model.inventory.aodRetun.AODReturn;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.inventory.AODResult;
import com.codex.ecam.result.inventory.AODReturnResult;
import com.codex.ecam.result.inventory.IssueNoteResult;
import com.codex.ecam.service.inventory.api.IssueNoteService;
import com.codex.ecam.service.inventory.api.StockService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.CommonUtil;
import com.codex.ecam.util.search.inventory.issuenote.IssueNoteItemPropertyMapper;

@Service
public class IssueNoteServiceImpl implements IssueNoteService {

	final static Logger logger = LoggerFactory.getLogger(IssueNoteServiceImpl.class);

	@Autowired
	AODDao aodDao;

	@Autowired
	AODItemDao aodItemDao;

	@Autowired
	private AssetDao assetDao;

	@Autowired
	private BusinessDao businessDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private WorkOrderDao workOrderDao;

	@Autowired
	private StockDao stockDao;

	@Autowired
	private StockService stockService;




	public IssueNoteResult newIssueNote() {
		final IssueNoteResult result=new IssueNoteResult(new AOD(), new IssueNoteDTO());

		try {
			result.setDtoEntity(nextItem());
			result.setResultStatusSuccess();
			result.addToErrorList("New Issue Note Created!");
		} catch (final Exception e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("Issue Note NOT Created ".concat(e.getMessage()));
			logger.error(e.getMessage());
		}
		return result;
	}

	private IssueNoteDTO nextItem() throws Exception {
		final IssueNoteDTO dto = new IssueNoteDTO();
		dto.setAodNo(getNextCode());
		return dto;
	}
	private String getNextCode() {
		Integer nextNo = 0;
		final Integer year = Calendar.getInstance().get(Calendar.YEAR);
		final AOD lastDomain = aodDao.findLastIssueNoteDomain(AODType.ISSUE_NOTE);
		if (lastDomain == null || lastDomain.getId() == null) {
			nextNo = 1;
		} else {
			final List<String> codeList = Arrays.asList(lastDomain.getAodNo().split("/"));
			if (!codeList.get(0).equalsIgnoreCase(AffixList.ISSUE_NOTE.getCode())) {
				nextNo = 1;
			} else if (Integer.parseInt(codeList.get(1)) == year) {
				nextNo = Integer.parseInt(codeList.get(2)) + 1;
			} else {
				nextNo = 1;
			}
		}
		return CommonUtil.setNextCode(AffixList.ISSUE_NOTE.getCode(), nextNo.toString());
	}


	@Override
	public IssueNoteResult save(IssueNoteDTO dto) throws Exception {
		final IssueNoteResult result = new IssueNoteResult(new AOD(), dto);
		try {
			saveOrUpdate(result);
			result.setResultStatusSuccess();
			result.addToMessageList("Issue Note Added Successfully.");
		} catch (final Exception e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToMessageList("Issue Note save Unsuccessful. ".concat(e.getMessage()));
		}
		return result;
	}

	@Override
	public IssueNoteResult update(IssueNoteDTO dto) throws Exception {
		final IssueNoteResult result = new IssueNoteResult(null, dto);
		try {
			final AOD domain = findEntityById(dto.getId());
			result.setDomainEntity(domain);
			saveOrUpdate(result);
			result.addToMessageList("Issue Note Updated Successfully.");
		} catch (final ObjectOptimisticLockingFailureException ex) {
			result.setResultStatusError();
			result.addToErrorList("Issue Note Already updated. Please Reload Issue Note.");
		} catch (final Exception e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(e.getMessage());
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(IssueNoteResult result) throws Exception {
		IssueNoteMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setAodData(result);
		aodDao.save(result.getDomainEntity());
		result.setDtoEntity(findDTOById(result.getDomainEntity().getId()));
	}

	private void setAodData(IssueNoteResult result) throws Exception {
		setAodRequestUser(result);
		setWorkOrder(result);
		setAodStatus(result);
		setAodItem(result);
		setBusinessSite(result);
	}


	private void setAodRequestUser(IssueNoteResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getRequestedUserId() != null) {
			result.getDomainEntity().setRequestedBy(userDao.findOne(result.getDtoEntity().getRequestedUserId()));
		}
	}

	private void setAodStatus(IssueNoteResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getAodStatus() != null) {
			result.getDomainEntity().setAodStatus(result.getDtoEntity().getAodStatus());
		}
	}

	private void setWorkOrder(IssueNoteResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getWoId() != null) {
			result.getDomainEntity().setWorkOrder(workOrderDao.findOne(result.getDtoEntity().getWoId()));
		}
	}

	private void setBusinessSite(IssueNoteResult result) {
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

	private void setAodItem(IssueNoteResult result) {

		final Set<AODItem> aodItems = new HashSet<>();
		final List<IssueNoteItemDTO> aodItemDTOs = result.getDtoEntity().getAodItemList();

		if (aodItemDTOs != null && aodItemDTOs.size() > 0) {

			final Set<AODItem> currentAodItems = result.getDomainEntity().getAodItemList();

			for ( final IssueNoteItemDTO aodItemDTO : aodItemDTOs ) {

				AODItem aodItem = new AODItem();

				if (currentAodItems != null && currentAodItems.size() > 0) {
					final AODItem optional = currentAodItems.stream().filter((x) -> x.getId().equals(aodItemDTO.getId())).findAny().orElseGet(AODItem :: new);
					aodItem = optional;
				} else {
					aodItem = new AODItem();
				}

				createAodItem(result, aodItemDTO, aodItem);
				aodItems.add(aodItem);
			}
		}
		result.getDomainEntity().setAodItemList(aodItems);
	}

	private void createAodItem(IssueNoteResult result, IssueNoteItemDTO aodItemDTO, AODItem aodItem) {

		if (aodItemDTO.getPartId() != null) {
			aodItem.setPart(assetDao.findOne(aodItemDTO.getPartId()));
		}
		if (aodItemDTO.getStockId() != null && aodItemDTO.getStockId() > 0) {
			aodItem.setStock(stockDao.findOne(aodItemDTO.getStockId()));
		}
		if (aodItemDTO.getWarehouseId() != null && aodItemDTO.getWarehouseId() > 0) {
			aodItem.setWarehouse(assetDao.findOne(aodItemDTO.getWarehouseId()));
		}

		aodItem.setAod(result.getDomainEntity());
		aodItem.setQuantity(aodItemDTO.getItemQuantity());
		aodItem.setDescription(aodItemDTO.getDescription());
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private AOD findEntityById(Integer id) throws Exception {
		return aodDao.findOne(id);
	}

	private IssueNoteDTO findDTOById(Integer id) throws Exception {
		return IssueNoteMapper.getInstance().domainToDto(findEntityById(id));
	}


	@Override
	public IssueNoteResult delete(Integer id) throws Exception {
		final IssueNoteResult result = new IssueNoteResult(null, null);
		try {
			deleteEntityById(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Issue Note Deleted Successfully.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToMessageList("Issue Note Deleted Unsuccessful. ".concat(ex.getMessage()));
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public IssueNoteResult deleteMultiple(Integer[] ids) throws Exception {
		final IssueNoteResult result = new IssueNoteResult(null, null);
		try {
			for (final Integer id : ids) {
				deleteEntityById(id);
			}
			result.setResultStatusSuccess();
			result.addToMessageList("Issue Note(s) Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Issue Note(s) Already Used. Cannot delete.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}


	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void deleteEntityById(Integer id) throws Exception {
		aodDao.delete(id);
	}


	@Override
	public IssueNoteResult findById(Integer id) throws Exception {
		final IssueNoteResult result = new IssueNoteResult(null, null);
		try {
			result.setDtoEntity(findDTOById(id));
			result.setResultStatusSuccess();
			result.addToMessageList("Issue Note Found.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToMessageList("Error Occurred! Issue Note NOT Found.".concat(ex.getMessage()));
		}
		return result;
	}

	public IssueNoteResult statusChange(Integer id, AODStatus status)  {
		final IssueNoteResult result = new IssueNoteResult(null, null);
		try {
			final IssueNoteDTO dto=findDTOById(id);
			dto.setAodStatus(status);
			final AOD domain = findEntityById(dto.getId());
			final String  previousStatus=domain.getAodStatus().getName();
			result.setDtoEntity(dto);
			result.setDomainEntity(domain);
			saveOrUpdate(result);
			issueNoteStatusChange(result);
			result.addToMessageList("Issue Note Status Updated Successfully. "+previousStatus+" --> "+ status.getName());
		} catch (final ObjectOptimisticLockingFailureException ex) {
			result.setResultStatusError();
			result.addToErrorList("Issue Note Already updated. Please Reload Aod.");
		} catch (final Exception e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(e.getMessage());
		}
		return result;
	}


	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void issueNoteStatusChange(IssueNoteResult result) throws Exception {
		if(result.getDomainEntity().getAodStatus().equals(AODStatus.APPROVED)){
			stockService.dispatchStock(result.getDomainEntity());

		}

	}

	@Override
	public DataTablesOutput<IssueNoteDTO> findAll(FocusDataTablesInput input) throws Exception {
		//AodPropertyMapper.getInstance().generateDataTableInput(input);
		DataTablesOutput<AOD> domainOut;
		Specification<AOD> specification =null;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			specification = (root, query, cb) ->
			cb.equal(root.get("aodType"), AODType.ISSUE_NOTE);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			specification = (root, query, cb) ->
			cb.and(
					cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness()),
					cb.equal(root.get("aodType"), AODType.ISSUE_NOTE)
					)
			;
		} else {
			specification = (root, query, cb) ->
			cb.and(
					cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness()),
					cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite()),
					cb.equal(root.get("aodType"), AODType.ISSUE_NOTE)
					);
		}			domainOut = aodDao.findAll(input, specification);

		final DataTablesOutput<IssueNoteDTO> out = IssueNoteMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	public DataTablesOutput<IssueNoteItemDTO> getIssuenoteItemDataTable(FocusDataTablesInput input) {
		IssueNoteItemPropertyMapper.getInstance().generateDataTableInput(input);
		DataTablesOutput<AODItem> domainOut;
		Specification<AODItem> specification=null;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			specification = (root, query, cb) ->
			cb.and(
					cb.equal(root.get("aod").get("aodStatus"), AODStatus.APPROVED),
					cb.equal(root.get("aod").get("aodType"), AODType.ISSUE_NOTE)
					);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			specification = (root, query, cb) ->
			cb.and(
					cb.equal(root.get("aod").get("aodStatus"), AODStatus.APPROVED),
					cb.equal(root.get("aod").get("aodType"), AODType.ISSUE_NOTE),
					cb.equal(root.get("aod").get("business"), AuthenticationUtil.getLoginUserBusiness()));

		} else {
			specification = (root, query, cb) -> cb.and(
					cb.equal(root.get("aod").get("aodStatus"), AODStatus.APPROVED),
					cb.equal(root.get("aod").get("aodType"), AODType.ISSUE_NOTE),
					cb.equal(root.get("aod").get("business"), AuthenticationUtil.getLoginUserBusiness()),
					cb.equal(root.get("aod").get("site"), AuthenticationUtil.getLoginSite().getSite())

					);
		}

		domainOut= aodItemDao.findAll(input,specification);
		DataTablesOutput<IssueNoteItemDTO> out = null;
		try {
			out = IssueNoteItemMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return out;
	}



}
