package com.neolith.focus.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.neolith.focus.dao.admin.CurrencyDao;
import com.neolith.focus.dto.admin.CurrencyDTO;
import com.neolith.focus.mappers.admin.CurrencyMapper;
import com.neolith.focus.model.admin.Currency;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.admin.CurrencyResult;
import com.neolith.focus.service.admin.api.CurrencyService;
import com.neolith.focus.util.search.admin.CurrencySearchPropertyMapper;

@Service
public class CurrencyServiceImpl implements CurrencyService {

	@Autowired
	private CurrencyDao currencyDao;

	@Override
	public DataTablesOutput<CurrencyDTO> findAll(FocusDataTablesInput input) throws Exception {
		CurrencySearchPropertyMapper.getInstance().generateDataTableInput(input);
		DataTablesOutput<Currency> domainOut = currencyDao.findAll(input);
		DataTablesOutput<CurrencyDTO> out = null;
		try {
			out = CurrencyMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}

	@Override
	public CurrencyDTO findById(Integer id) throws Exception {
		Currency domain = currencyDao.findById(id);
		if (domain != null) {
			return CurrencyMapper.getInstance().domainToDto(domain);
		}
		return null;
	}

	@Override
	public CurrencyResult delete(Integer id) {
		CurrencyResult result = new CurrencyResult(null, null);
		try {
			currencyDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Currency Deleted Successfully.");
		} catch (DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Currency Already Assigned. Please Remove from Assigned Currency and try again.");
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	public CurrencyResult save(CurrencyDTO dto) throws Exception {
		CurrencyResult result = createCurrencyResult(dto);
		try{
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Currency Already updated. Please Reload Currency.");
		} catch (Exception ex) {
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
		if ((dto.getId() != null) && (dto.getId() > 0)) {
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
		Iterable<Currency> domainList = currencyDao.findAll();
		try {
			return CurrencyMapper.getInstance().domainToDTOList(domainList);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void saveAll(List<CurrencyDTO> allDummyData) {
		for (CurrencyDTO dto : allDummyData) {
			try {
				save(dto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteAll() {
		currencyDao.deleteAll();
	}

}
