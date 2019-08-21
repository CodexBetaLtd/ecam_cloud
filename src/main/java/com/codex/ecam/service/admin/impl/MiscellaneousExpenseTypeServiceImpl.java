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

import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dao.maintenance.MiscellaneousExpenseTypeDao;
import com.codex.ecam.dto.maintenance.miscellaneousExpense.MiscellaneousExpenseTypeDTO;
import com.codex.ecam.mappers.maintenance.MiscellaneousExpenseTypeMapper;
import com.codex.ecam.model.maintenance.miscellaneous.MiscellaneousExpenseType;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.MiscellaneousExpenseTypeResult;
import com.codex.ecam.service.admin.api.MiscellaneousExpenseTypeService;
import com.codex.ecam.util.AuthenticationUtil;


@Service
public class MiscellaneousExpenseTypeServiceImpl implements MiscellaneousExpenseTypeService {


	@Autowired
	private MiscellaneousExpenseTypeDao miscellaneousExpenseTypeDao;

	@Autowired
	private BusinessDao businessDao;

	@Override
	public DataTablesOutput<MiscellaneousExpenseTypeDTO> findAll(FocusDataTablesInput input) throws Exception {
		DataTablesOutput<MiscellaneousExpenseType> domainOut;

		//PrioritySearchPropertyMapper.getInstance().generateDataTableInput(input);

		if(AuthenticationUtil.isAuthUserAdminLevel()){
			domainOut = miscellaneousExpenseTypeDao.findAll(input);
		} else {
			Specification<MiscellaneousExpenseType> specification = (root, query, cb) -> cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
			domainOut = miscellaneousExpenseTypeDao.findAll(input, specification);
		}

		DataTablesOutput<MiscellaneousExpenseTypeDTO> out = MiscellaneousExpenseTypeMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	@Override
	public MiscellaneousExpenseTypeDTO findById(Integer id) throws Exception {
		MiscellaneousExpenseType domain = findEntityById(id);
		if (domain != null) {
			return MiscellaneousExpenseTypeMapper.getInstance().domainToDto(domain);
		}
		return null;
	}

	@Override
	public MiscellaneousExpenseTypeResult delete(Integer id) {
		MiscellaneousExpenseTypeResult result = new MiscellaneousExpenseTypeResult(null, null);
		try {
			miscellaneousExpenseTypeDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Miscellaneous Expense Type Deleted Successfully.");
		} catch (DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Miscellaneous Expense Type Already Assigned. Please Remove from Assigned Miscellaneous Expense Type and try again.");
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Override
	public MiscellaneousExpenseTypeResult save(MiscellaneousExpenseTypeDTO dto) throws Exception {
		MiscellaneousExpenseTypeResult result = createMiscellaneousExpenseTypeResult(dto);
		try{
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Priority Already updated. Please Reload Priority.");
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(MiscellaneousExpenseTypeResult result) throws Exception {
		MiscellaneousExpenseTypeMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setBusiness(result);
		miscellaneousExpenseTypeDao.save(result.getDomainEntity());
		result.updateDtoIdAndVersion();
	}

	private MiscellaneousExpenseTypeResult createMiscellaneousExpenseTypeResult(MiscellaneousExpenseTypeDTO dto) {
		MiscellaneousExpenseTypeResult result;
		if ((dto.getId() != null) && (dto.getId() > 0)) {
			result = new MiscellaneousExpenseTypeResult(miscellaneousExpenseTypeDao.findOne(dto.getId()), dto);
		} else {
			result = new MiscellaneousExpenseTypeResult(new MiscellaneousExpenseType(), dto);
		}

		return result;
	}

	private String getMessageByAction(MiscellaneousExpenseTypeDTO dto) {
		if (dto.getId() == null) {
			return "Miscellaneous Expense Type  Added Successfully.";
		} else {
			return "Miscellaneous Expense Type  Updated Successfully.";
		}
	}

	private void setBusiness(MiscellaneousExpenseTypeResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getBusinessId() != null)) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}


	public MiscellaneousExpenseType findEntityById(Integer id) {
		return miscellaneousExpenseTypeDao.findOne(id);
	}

	@Override
	public List<MiscellaneousExpenseTypeDTO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}



}
