package com.neolith.focus.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.neolith.focus.dao.admin.CountryDao;
import com.neolith.focus.dto.admin.CountryDTO;
import com.neolith.focus.mappers.admin.CountryMapper;
import com.neolith.focus.model.admin.Country;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.admin.CountryResult;
import com.neolith.focus.service.admin.api.CountryService;
import com.neolith.focus.util.search.admin.CountrySearchPropertyMapper;


@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryDao countryDao;

	@Override
	public DataTablesOutput<CountryDTO> findAll(FocusDataTablesInput input) throws Exception {
		CountrySearchPropertyMapper.getInstance().generateDataTableInput(input);
		DataTablesOutput<Country> domainOut = countryDao.findAll(input);
		DataTablesOutput<CountryDTO> out = null;
		try {
			out = CountryMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return out;
	}

	@Override
	public CountryDTO findById(Integer id) throws Exception {
		Country domain = countryDao.findById(id);
		if (domain != null) {
			return CountryMapper.getInstance().domainToDto(domain);
		}

		return null;
	}

	@Override
	public CountryResult delete(Integer id) {
		CountryResult result = new CountryResult(null, null);
		try {
			countryDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Country Deleted Successfully.");
		} catch (DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Country Already Assigned. Please Remove from Assigned Country and try again.");
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Override
	public CountryResult save(CountryDTO dto) throws Exception {
		CountryResult result = createCountryResult(dto);
		try{
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Country Already updated. Please Reload Country.");
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate( CountryResult result) throws Exception{
		CountryMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		countryDao.save(result.getDomainEntity());
		result.updateDtoIdAndVersion();
	}

	private CountryResult createCountryResult(CountryDTO dto) {
		CountryResult result;
		if ((dto.getId() != null) && (dto.getId() > 0)) {
			result = new CountryResult(countryDao.findOne(dto.getId()), dto);
		} else {
			result = new CountryResult(new Country(), dto);
		}

		return result;
	}

	private String getMessageByAction(CountryDTO dto) {
		if (dto.getId() == null) {
			return "Country Added Successfully.";
		} else {
			return "Country Updated Successfully.";
		}
	}

	@Override
	public void saveAll(List<CountryDTO> allDummyData) {
		for (CountryDTO dto : allDummyData) {
			try {
				save(dto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteAll() {
		countryDao.deleteAll();
	}

	@Override
	public List<CountryDTO> findAll() {
		Iterable<Country> countries = countryDao.findAll();
		try {
			return CountryMapper.getInstance().domainToDTOList(countries);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<CountryDTO> findAllCountries() throws Exception {
		Iterable<Country> countries = countryDao.findAll();
		return CountryMapper.getInstance().domainToDTOList(countries);
	}

}
