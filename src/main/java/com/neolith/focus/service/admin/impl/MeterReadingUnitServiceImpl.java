package com.neolith.focus.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.neolith.focus.dao.admin.MeterReadingUnitDao;
import com.neolith.focus.dto.admin.MeterReadingUnitDTO;
import com.neolith.focus.mappers.admin.MeterReadingUnitMapper;
import com.neolith.focus.model.admin.MeterReadingUnit;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.admin.MeterReadingUnitResult;
import com.neolith.focus.service.admin.api.MeterReadingUnitService;
import com.neolith.focus.util.search.admin.MeterReadingUnitSearchPropertyMapper;

@Service
public class MeterReadingUnitServiceImpl implements MeterReadingUnitService {

	@Autowired
	private MeterReadingUnitDao meterReadingUnitDao;

	@Override
	public DataTablesOutput<MeterReadingUnitDTO> findAll(FocusDataTablesInput input) throws Exception {
		MeterReadingUnitSearchPropertyMapper.getInstance().generateDataTableInput(input);
		DataTablesOutput<MeterReadingUnit> domainOut = meterReadingUnitDao.findAll(input);
		DataTablesOutput<MeterReadingUnitDTO> out = null;
		try {
			out = MeterReadingUnitMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return out;
	}

	@Override
	public MeterReadingUnitDTO findById(Integer id) throws Exception {
		MeterReadingUnit domain = meterReadingUnitDao.findById(id);
		if (domain != null) {
			return MeterReadingUnitMapper.getInstance().domainToDto(domain);
		}
		return null;
	}

	@Override
	public MeterReadingUnitResult delete(Integer id) {
		MeterReadingUnitResult result = new MeterReadingUnitResult(null, null);
		try {
			meterReadingUnitDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Meter Reading Unit Deleted Successfully.");
		} catch (DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Meter Reading Unit Already Assigned. Please Remove from Assigned Meter Reading Unit and try again.");
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	public MeterReadingUnitResult save(MeterReadingUnitDTO dto) throws Exception {
		MeterReadingUnitResult result = createMeterReadingUnitResult(dto);
		try{
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Meter Reading Unit Already updated. Please Reload Meter Reading Unit.");
		} catch (Exception ex) {
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
		if ((dto.getId() != null) && (dto.getId() > 0)) {
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
		for (MeterReadingUnitDTO dto : allDummyData) {
			try {
				save(dto);
			} catch (Exception e) {
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

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
