package com.codex.ecam.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.dao.admin.CountryDao;
import com.codex.ecam.dto.admin.CountryDTO;
import com.codex.ecam.mappers.admin.CountryMapper;
import com.codex.ecam.model.admin.Country;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.CountryResult;
import com.codex.ecam.service.admin.api.CountryService;
import com.codex.ecam.util.search.admin.CountrySearchPropertyMapper;


@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryDao countryDao;

	@Override
	public DataTablesOutput<CountryDTO> findAll(FocusDataTablesInput input) throws Exception {
		CountrySearchPropertyMapper.getInstance().generateDataTableInput(input);
		final DataTablesOutput<Country> domainOut = countryDao.findAll(input);
		DataTablesOutput<CountryDTO> out = null;
		try {
			out = CountryMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return out;
	}

	@Override
	public CountryDTO findById(Integer id) throws Exception {
		final Country domain = countryDao.findById(id);
		if (domain != null) {
			return CountryMapper.getInstance().domainToDto(domain);
		}

		return null;
	}

	@Override
	public CountryResult delete(Integer id) {
		final CountryResult result = new CountryResult(null, null);
		try {
			countryDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Country Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Country Already Assigned. Please Remove from Assigned Country and try again.");
		} catch (final Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public CountryResult deleteMultiple(Integer[] ids) throws Exception {
		final CountryResult result = new CountryResult(null, null);
		try {
			for (final Integer id : ids) {
				countryDao.delete(id);
			}
			result.setResultStatusSuccess();
			result.addToMessageList("Country(s) Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Country(s) Already Used. Cannot delete.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	public CountryResult save(CountryDTO dto) throws Exception {
		final CountryResult result = createCountryResult(dto);
		try{
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (final ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Country Already updated. Please Reload Country.");
		} catch (final Exception ex) {
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
		if (dto.getId() != null && dto.getId() > 0) {
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
		for (final CountryDTO dto : allDummyData) {
			try {
				save(dto);
			} catch (final Exception e) {
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
		final Iterable<Country> countries = countryDao.findAll();
		try {
			return CountryMapper.getInstance().domainToDTOList(countries);
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<CountryDTO> findAllCountries() throws Exception {
		final Iterable<Country> countries = countryDao.findAll();
		return CountryMapper.getInstance().domainToDTOList(countries);
	}

}
