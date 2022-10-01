package com.codex.ecam.service.maintenance.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.constants.WorkOrderStatus;
import com.codex.ecam.dao.admin.UserDao;
import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dao.biz.SupplierDao;
import com.codex.ecam.dao.maintenance.ExWorkOrderDao;
import com.codex.ecam.dto.maintenance.exworkorder.ExWorkOrderDTO;
import com.codex.ecam.mappers.maintenance.ExWorkOrderMapper;
import com.codex.ecam.model.maintenance.ExWorkOrder;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.maintenance.ExWorkOrderResult;
import com.codex.ecam.service.maintenance.api.ExWorkOrderService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.search.workorder.WorkOrderSearchPropertyMapper;

@Service
public class ExWorkOrderServiceImpl implements ExWorkOrderService {

	private final static Logger logger = LoggerFactory.getLogger(ExWorkOrderServiceImpl.class);

	@Autowired
	Environment environment;

	@Autowired
	private UserDao userDao;

	@Autowired
	private BusinessDao businessDao;

	@Autowired
	private SupplierDao supplierDao;

	@Autowired
	private AssetDao assetDao;

	@Autowired
	private ExWorkOrderDao exWoDao;


	@Override
	public ExWorkOrderDTO findById(Integer id) throws Exception {
		final ExWorkOrder domain = exWoDao.findById(id);
		if (domain != null) {
			return ExWorkOrderMapper.getInstance().domainToDto(domain);
		}
		return null;
	}

	@Override
	public ExWorkOrderResult delete(Integer id) {
		final ExWorkOrderResult result = new ExWorkOrderResult(null, null);
		try {
			exWoDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("External Work Order Deleted Successfully.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ExWorkOrderResult deleteMultiple(Integer[] ids) throws Exception {
		final ExWorkOrderResult result = new ExWorkOrderResult(null, null);
		try {
			for (final Integer id : ids) {
				exWoDao.delete(id);
			}
			result.setResultStatusSuccess();
			result.addToMessageList("ExWorkOrder(s) Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("ExWorkOrder(s) Already Used. Cannot delete.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	public ExWorkOrderResult save(ExWorkOrderDTO exWorkOrderDTO) {

		final ExWorkOrderResult result = createWorkOrderResult(exWorkOrderDTO);
		try {
			saveOrUpdate(result);
		} catch (final ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("External Work Order Already updated. Please Reload External Work Order.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(ExWorkOrderResult result) throws Exception {
		ExWorkOrderMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setWorkOrderData(result);
		exWoDao.save(result.getDomainEntity());
		//stateChange(result.getDtoEntity());
		result.addToMessageList(getMessageByAction(result));
		result.updateDtoIdAndVersion();
	}

	private void setWorkOrderData(ExWorkOrderResult result) throws Exception {
		setAssetToWorkOrder(result);
		setBusiness(result);
		setSite(result);
		setServiceProvider(result);

		setRequestedBy(result);
		//setWoStatus(result);
	}

	private void setBusiness(ExWorkOrderResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getBusinessId() != null) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}

	private void setSite(ExWorkOrderResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getSiteId() != null) {
			result.getDomainEntity().setSite(assetDao.findOne(result.getDtoEntity().getSiteId()));
		}
	}
	private void setAssetToWorkOrder(ExWorkOrderResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getAssetId() != null) {
			result.getDomainEntity().setAsset(assetDao.findOne(result.getDtoEntity().getAssetId()));
		}
	}

	private void setServiceProvider(ExWorkOrderResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getServiceProviderId() != null) {
			result.getDomainEntity().setSupplier(supplierDao.findOne(result.getDtoEntity().getServiceProviderId()));
		}
	}

	private void setRequestedBy(ExWorkOrderResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getServiceRequestId() != null) {
			result.getDomainEntity().setRequestedByUser(userDao.findOne(result.getDtoEntity().getServiceRequestId()));
		}
	}



	private ExWorkOrderResult createWorkOrderResult(ExWorkOrderDTO dto) {
		ExWorkOrderResult result;
		if (dto.getId() != null && dto.getId() > 0) {
			result = new ExWorkOrderResult(exWoDao.findOne(dto.getId()), dto);
		} else {
			result = new ExWorkOrderResult(new ExWorkOrder(), dto);
		}

		return result;
	}

	private String getMessageByAction(ExWorkOrderResult result) {
		if (result.getDtoEntity().getId() == null) {
			return "External Work Order Added Successfully.";
		} else {
			return "External Work Order Updated Successfully.";
		}
	}
	@Override
	public ExWorkOrderResult statusChange(Integer id, WorkOrderStatus status, String date, String note)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataTablesOutput<ExWorkOrderDTO> findAll(FocusDataTablesInput input) throws Exception {
		DataTablesOutput<ExWorkOrder> domainOut;
		WorkOrderSearchPropertyMapper.getInstance().generateDataTableInput(input);
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			domainOut = exWoDao.findAll(input);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			final Specification<ExWorkOrder> specification = (root, query, cb) -> cb.equal(root.get("business"),
					AuthenticationUtil.getLoginUserBusiness());
			domainOut = exWoDao.findAll(input, specification);
		} else {
			final Specification<ExWorkOrder> specification = (root, query, cb) -> cb.equal(root.get("site"),
					AuthenticationUtil.getLoginSite().getSite());
			domainOut = exWoDao.findAll(input, specification);
		}
		final DataTablesOutput<ExWorkOrderDTO> out = ExWorkOrderMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

}
