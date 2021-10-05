package com.codex.ecam.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.dao.admin.MeterReadingUnitDao;
import com.codex.ecam.dto.admin.MeterReadingUnitDTO;
import com.codex.ecam.mappers.admin.MeterReadingUnitMapper;
import com.codex.ecam.model.admin.MeterReadingUnit;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.MeterReadingUnitResult;
import com.codex.ecam.service.admin.api.MeterReadingUnitService;
import com.codex.ecam.util.search.admin.MeterReadingUnitSearchPropertyMapper;

@Service
public class MeterReadingUnitServiceImpl implements MeterReadingUnitService {

	@Autowired
	private MeterReadingUnitDao meterReadingUnitDao;

	@Override
	public DataTablesOutput<MeterReadingUnitDTO> findAll(FocusDataTablesInput input) throws Exception {
		MeterReadingUnitSearchPropertyMapper.getInstance().generateDataTableInput(input);
		final DataTablesOutput<MeterReadingUnit> domainOut = meterReadingUnitDao.findAll(input);
		return MeterReadingUnitMapper.getInstance().domainToDTODataTablesOutput(domainOut);
	}

	@Override
	public MeterReadingUnitDTO findById(Integer id) throws Exception {
		final MeterReadingUnit domain = meterReadingUnitDao.findById(id);
		if (domain != null) {
			return MeterReadingUnitMapper.getInstance().domainToDto(domain);
		}
		return null;
	}

	@Override
	public MeterReadingUnitResult delete(Integer id) {
		final MeterReadingUnitResult result = new MeterReadingUnitResult(null, null);
		try {
			meterReadingUnitDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Meter Reading Unit Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Meter Reading Unit Already Assigned. Please Remove from Assigned Meter Reading Unit and try again.");
		} catch (final Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MeterReadingUnitResult deleteMultiple(Integer[] ids) throws Exception {
		final MeterReadingUnitResult result = new MeterReadingUnitResult(null, null);
		try {
			for (final Integer id : ids) {
				meterReadingUnitDao.delete(id);
			}
			result.setResultStatusSuccess();
			result.addToMessageList("Meter Reading Unit(s) Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Meter Reading Unit(s) Already Used. Cannot delete.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	public MeterReadingUnitResult save(MeterReadingUnitDTO dto) throws Exception {
		final MeterReadingUnitResult result = createMeterReadingUnitResult(dto);
		try{
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (final ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Meter Reading Unit Already updated. Please Reload Meter Reading Unit.");
		} catch (final Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(MeterReadingUnitResult result) throws Exception {
		MeterReadingUnitMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		meterReadingUnitDao.save(result.getDomainEntity());
		result.updateDtoIdAndVersion();
	}

	private MeterReadingUnitResult createMeterReadingUnitResult(MeterReadingUnitDTO dto) {
		MeterReadingUnitResult result;
		if (dto.getId() != null && dto.getId() > 0) {
			result = new MeterReadingUnitResult(meterReadingUnitDao.findOne(dto.getId()), dto);
		} else {
			result = new MeterReadingUnitResult(new MeterReadingUnit(), dto);
		}

		return result;
	}

	private String getMessageByAction(MeterReadingUnitDTO dto) {
		if (dto.getId() == null) {
			return "Meter Reading Unit Added Successfully.";
		} else {
			return "Meter Reading Unit Updated Successfully.";
		}
	}

	@Override
	public void saveAll(List<MeterReadingUnitDTO> allDummyData) {
		for (final MeterReadingUnitDTO dto : allDummyData) {
			try {
				save(dto);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteAll() {
		meterReadingUnitDao.deleteAll();
	}

	@Override
	public List<MeterReadingUnitDTO> findAllMeterReadings() {

		try {

			Iterable<MeterReadingUnit> domainList;
			//if(AuthenticationUtil.isAuthUserAdminLevel()){
			domainList = meterReadingUnitDao.findAll();
			//} else {
			//Specification<MeterReadingUnit> specification = (root, query, cb) -> cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
			//domainList = meterReadingUnitDao.findAll(specification);
			//}
			return MeterReadingUnitMapper.getInstance().domainToDTOList(domainList);

		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
