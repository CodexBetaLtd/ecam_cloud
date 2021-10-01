package com.codex.ecam.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.dao.admin.CurrencyDao;
import com.codex.ecam.dto.admin.CurrencyDTO;
import com.codex.ecam.mappers.admin.CurrencyMapper;
import com.codex.ecam.model.admin.Currency;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.CurrencyResult;
import com.codex.ecam.service.admin.api.CurrencyService;
import com.codex.ecam.util.search.admin.CurrencySearchPropertyMapper;

@Service
public class CurrencyServiceImpl implements CurrencyService {

	@Autowired
	private CurrencyDao currencyDao;

	@Override
	public DataTablesOutput<CurrencyDTO> findAll(FocusDataTablesInput input) throws Exception {
		CurrencySearchPropertyMapper.getInstance().generateDataTableInput(input);
		final DataTablesOutput<Currency> domainOut = currencyDao.findAll(input);
		DataTablesOutput<CurrencyDTO> out = null;
		try {
			out = CurrencyMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return out;
	}

	@Override
	public CurrencyDTO findById(Integer id) throws Exception {
		final Currency domain = currencyDao.findById(id);
		if (domain != null) {
			return CurrencyMapper.getInstance().domainToDto(domain);
		}
		return null;
	}

	@Override
	public CurrencyResult delete(Integer id) {
		final CurrencyResult result = new CurrencyResult(null, null);
		try {
			currencyDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Currency Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Currency Already Assigned. Please Remove from Assigned Currency and try again.");
		} catch (final Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public CurrencyResult deleteMultiple(Integer[] ids) throws Exception {
		final CurrencyResult result = new CurrencyResult(null, null);
		try {
			for (final Integer id : ids) {
				currencyDao.delete(id);
			}
			result.setResultStatusSuccess();
			result.addToMessageList("Currency(s) Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Currency(s) Already Used. Cannot delete.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	public CurrencyResult save(CurrencyDTO dto) throws Exception {
		final CurrencyResult result = createCurrencyResult(dto);
		try{
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (final ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Currency Already updated. Please Reload Currency.");
		} catch (final Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate( CurrencyResult result) throws Exception{
		CurrencyMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		currencyDao.save(result.getDomainEntity());
		result.updateDtoIdAndVersion();
	}

	private CurrencyResult createCurrencyResult(CurrencyDTO dto) {
		CurrencyResult result;
		if (dto.getId() != null && dto.getId() > 0) {
			result = new CurrencyResult(currencyDao.findOne(dto.getId()), dto);
		} else {
			result = new CurrencyResult(new Currency(), dto);
		}

		return result;
	}

	private String getMessageByAction(CurrencyDTO dto) {
		if (dto.getId() == null) {
			return "Currency Added Successfully.";
		} else {
			return "Currency Updated Successfully.";
		}
	}

	@Override
	public List<CurrencyDTO> findAll() {
		final Iterable<Currency> domainList = currencyDao.findAll();
		try {
			return CurrencyMapper.getInstance().domainToDTOList(domainList);
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void saveAll(List<CurrencyDTO> allDummyData) {
		for (final CurrencyDTO dto : allDummyData) {
			try {
				save(dto);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteAll() {
		currencyDao.deleteAll();
	}

}
