package com.codex.ecam.service.asset.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.codex.ecam.dao.asset.AssetMeterReadingValueDao;
import com.codex.ecam.dto.asset.AssetMeterReadingValueDTO;
import com.codex.ecam.mappers.asset.AssetMeterReadingValueMapper;
import com.codex.ecam.model.asset.AssetMeterReadingValue;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.asset.api.AssetMeterReadingValueService;

@Service
public class AssetMeterReadingValueServiceImpl implements AssetMeterReadingValueService {

	@Autowired
	private AssetMeterReadingValueDao assetMeterReadingValueDao;

	@Override
	public DataTablesOutput<AssetMeterReadingValueDTO> findMeterReadingHistory(FocusDataTablesInput input, Integer meterReadingId) throws Exception {
		Specification<AssetMeterReadingValue> specification = (root, query, cb) -> cb.equal(root.get("assetMeterReading").get("id"), meterReadingId);

		DataTablesOutput<AssetMeterReadingValue> domainOut = assetMeterReadingValueDao.findAll(input, specification);
		DataTablesOutput<AssetMeterReadingValueDTO> out = AssetMeterReadingValueMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	@Override
	public AssetMeterReadingValueDTO findMeterReadingValueById(Integer id) throws Exception {
		AssetMeterReadingValueDTO assetMeterReadingValueDTO = new AssetMeterReadingValueDTO();
		assetMeterReadingValueDTO = AssetMeterReadingValueMapper.getInstance().domainToDto(assetMeterReadingValueDao.findById(id));
		return assetMeterReadingValueDTO;
	}

}
