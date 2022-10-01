package com.codex.ecam.service.admin.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.dao.admin.MaintenanceTypeDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dto.admin.MaintenanceTypeDTO;
import com.codex.ecam.mappers.admin.MaintenanceTypeMapper;
import com.codex.ecam.model.maintenance.MaintenanceType;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.MaintenanceTypeResult;
import com.codex.ecam.service.admin.api.MaintenanceTypeService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.search.admin.MaintenanceTypeSearchPropertyMapper;

@Service
public class MaintenanceTypeServiceImpl implements MaintenanceTypeService {

	@Autowired
	private MaintenanceTypeDao maintenanceTypeDao;

	@Autowired
	private BusinessDao businessDao;

	@Override
	public DataTablesOutput<MaintenanceTypeDTO> findAll(FocusDataTablesInput input) throws Exception {
		DataTablesOutput<MaintenanceType> domainOut;

		MaintenanceTypeSearchPropertyMapper.getInstance().generateDataTableInput(input);

		if(AuthenticationUtil.isAuthUserAdminLevel()){
			domainOut = maintenanceTypeDao.findAll(input);
		} else {
			final Specification<MaintenanceType> specification = (root, query, cb) -> cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
			domainOut = maintenanceTypeDao.findAll(input, specification);
		}

		final DataTablesOutput<MaintenanceTypeDTO> out = MaintenanceTypeMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	@Override
	public DataTablesOutput<MaintenanceTypeDTO> findByBusiness(FocusDataTablesInput input, Integer bizId) throws Exception {

		MaintenanceTypeSearchPropertyMapper.getInstance().generateDataTableInput(input);

		final Specification<MaintenanceType> specification = (root, query, cb) -> cb.equal(root.get("business"), bizId);

		final DataTablesOutput<MaintenanceType> domainOut = maintenanceTypeDao.findAll(input, specification);

		return MaintenanceTypeMapper.getInstance().domainToDTODataTablesOutput(domainOut);

	}

	@Override
	public MaintenanceTypeDTO findById(Integer id) throws Exception {
		final MaintenanceType domain = findEntityById(id);
		if (domain != null) {
			return MaintenanceTypeMapper.getInstance().domainToDto(domain);
		}
		return null;
	}

	@Override
	public MaintenanceTypeResult delete(Integer id) {
		final MaintenanceTypeResult result = new MaintenanceTypeResult(null, null);
		try {
			maintenanceTypeDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("MaintenanceType Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("MaintenanceType Already Assigned. Please Remove from Assigned MaintenanceType and try again.");
		} catch (final Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MaintenanceTypeResult deleteMultiple(Integer[] ids) throws Exception {
		final MaintenanceTypeResult result = new MaintenanceTypeResult(null, null);
		try {
			for (final Integer id : ids) {
				maintenanceTypeDao.delete(id);
			}
			result.setResultStatusSuccess();
			result.addToMessageList("Maintenance Type(s) Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Maintenance Type(s) Already Used. Cannot delete.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override

	public MaintenanceTypeResult save(MaintenanceTypeDTO dto) throws Exception {

		final MaintenanceTypeResult result = createMaintenanceTypeResult(dto);

		try{
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (final ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("MaintenanceType Already updated. Please Reload MaintenanceType.");
		} catch (final Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED ,rollbackFor = Exception.class)
	private void saveOrUpdate(MaintenanceTypeResult result) throws Exception {
		MaintenanceTypeMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setBusiness(result);
		maintenanceTypeDao.save(result.getDomainEntity());
		result.updateDtoIdAndVersion();
	}

	private MaintenanceTypeResult createMaintenanceTypeResult(MaintenanceTypeDTO dto) {
		MaintenanceTypeResult result;
		if (dto.getId() != null && dto.getId() > 0) {
			result = new MaintenanceTypeResult(maintenanceTypeDao.findOne(dto.getId()), dto);
		} else {
			result = new MaintenanceTypeResult(new MaintenanceType(), dto);
		}

		return result;
	}

	private String getMessageByAction(MaintenanceTypeDTO dto) {
		if (dto.getId() == null) {
			return "MaintenanceType Added Successfully.";
		} else {
			return "MaintenanceType Updated Successfully.";
		}
	}

	private void setBusiness(MaintenanceTypeResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getBusinessId() != null) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}

	@Override
	public void saveAll(List<MaintenanceTypeDTO> allDummyData) {
		for (final MaintenanceTypeDTO dto : allDummyData) {
			try {
				save(dto);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteAll() {
		maintenanceTypeDao.deleteAll();
	}

	@Override
	public List<MaintenanceTypeDTO> findAll() {
		try {
			List<MaintenanceType> domainOut;
			if(AuthenticationUtil.isAuthUserAdminLevel()){
				domainOut = (List<MaintenanceType>) maintenanceTypeDao.findAll();
			} else {
				final Specification<MaintenanceType> specification = (root, query, cb) -> cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
				domainOut = maintenanceTypeDao.findAll(specification);
			}
			return MaintenanceTypeMapper.getInstance().domainToDTOList(domainOut);
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<MaintenanceTypeDTO> findAllByBusiness(Integer id) {
		try {
			List<MaintenanceTypeDTO> dtoOut = new ArrayList<>();
			final Specification<MaintenanceType> specification = (root, query, cb) -> cb.equal(root.get("business").get("id"), id);

			if (specification != null) {
				final List<MaintenanceType> domainOut = maintenanceTypeDao.findAll(specification);
				dtoOut =  MaintenanceTypeMapper.getInstance().domainToDTOList(domainOut);
			}

			return dtoOut;
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public MaintenanceType findEntityById(Integer id) {
		return maintenanceTypeDao.findById(id);
	}

}
