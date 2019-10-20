package com.codex.ecam.mappers.asset;

import com.codex.ecam.dto.asset.AssetMeterReadingConsumptionVariableDTO;
import com.codex.ecam.dto.asset.AssetMeterReadingDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.asset.AssetMeterReading;
import com.codex.ecam.model.asset.AssetMeterReadingFormulaValue;
import com.codex.ecam.model.asset.AssetMeterReadingFormulaVariable;
import com.codex.ecam.model.asset.AssetMeterReadingValue;

public class AssetMeterReadingMapper extends GenericMapper<AssetMeterReading, AssetMeterReadingDTO> {

	private static AssetMeterReadingMapper instance = null;

	private AssetMeterReadingMapper() {
	}

	public static AssetMeterReadingMapper getInstance() {
		if (instance == null) {
			instance = new AssetMeterReadingMapper();
		}
		return instance;
	}

	@Override
	public AssetMeterReadingDTO domainToDto(AssetMeterReading domain) throws Exception {
		AssetMeterReadingDTO dto = new AssetMeterReadingDTO();
		dto.setMeterReadingId(domain.getId());
		dto.setMeterReadingName(domain.getMeterReadingName());
		dto.setMeterReadingUnitId(domain.getMeterReadingUnit().getId());
		dto.setMeterReadingUnitName(domain.getMeterReadingUnit().getName());
		dto.setMeterReadingAssetId(domain.getAsset().getId());
		dto.setMeterReadingDescription(domain.getDescription());
		dto.setMeterReadingAvgValue(domain.getAvgMeterReadingValue());
		dto.setIsMultipleMeterReading(domain.getIsMultipleMeterReading());
		dto.setConsumptionFormula(domain.getConsumptionFormula());
		setMeterReadingValues(domain, dto);
		setMeterReadingConsumptionVariables(domain, dto);

		setCommanDTOFields(dto, domain);

		return dto;
	}

	private void setMeterReadingConsumptionVariables(AssetMeterReading domain, AssetMeterReadingDTO dto){
		if (domain.getFormulaVariables().size() > 0) {
			for (AssetMeterReadingFormulaVariable assetMeterReadingConsumptionVariable : domain.getFormulaVariables()) {
				AssetMeterReadingConsumptionVariableDTO consumptionVariableDTO=new AssetMeterReadingConsumptionVariableDTO();
				consumptionVariableDTO.setVariable(assetMeterReadingConsumptionVariable.getVariableName());
				consumptionVariableDTO.setId(assetMeterReadingConsumptionVariable.getId());
				consumptionVariableDTO.setVersion(assetMeterReadingConsumptionVariable.getVersion());
				if(assetMeterReadingConsumptionVariable.getMeterReadingUnit()!=null){
					consumptionVariableDTO.setMeteReadingUnitId(assetMeterReadingConsumptionVariable.getMeterReadingUnit().getId());
					consumptionVariableDTO.setMeteReadingUnitName(assetMeterReadingConsumptionVariable.getMeterReadingUnit().getSymbol());
				}

				dto.getConsumptionVariableDTO().add(consumptionVariableDTO);
			}
		}
	}
	private void setMeterReadingValues(AssetMeterReading domain, AssetMeterReadingDTO dto) throws Exception {
		if (domain.getAssetMeterReadingValues().size() > 0) {
			for (AssetMeterReadingValue assetMeterReadingValue : domain.getAssetMeterReadingValues()) {
				dto.setMeterReadingCurrentValue(assetMeterReadingValue.getMeterReadingValue());
				dto.setMeterReadingCurrentValueId(domain.getCurrentAssetMeterReadingValue().getId());
				if( domain.getCurrentAssetMeterReadingValue().getAssetMeterReadingFormulaValues().size()>0){
					String meterVariable="";
					for(AssetMeterReadingFormulaValue formulaValue:domain.getCurrentAssetMeterReadingValue().getAssetMeterReadingFormulaValues()){
						if(meterVariable==""){
							meterVariable=formulaValue.getValue().toString();

						}else{
							meterVariable=meterVariable+","+formulaValue.getValue().toString();

						}
					}
					dto.setMeterReadingConsumptionValues(meterVariable);

				}
			}
		}
	}

	@Override
	public void dtoToDomain(AssetMeterReadingDTO dto, AssetMeterReading domain) throws Exception {
		domain.setId(dto.getMeterReadingId());
		domain.setMeterReadingName(dto.getMeterReadingName());
		domain.setDescription(dto.getMeterReadingDescription());
		domain.setIsMultipleMeterReading(dto.getIsMultipleMeterReading());
		domain.setConsumptionFormula(dto.getConsumptionFormula());
		setCommanDomainFields(dto, domain);
	}

	@Override
	public AssetMeterReadingDTO domainToDtoForDataTable(AssetMeterReading domain) throws Exception {
		AssetMeterReadingDTO dto = new AssetMeterReadingDTO();
		dto.setMeterReadingId(domain.getId());
		dto.setMeterReadingName(domain.getMeterReadingName());
		dto.setMeterReadingUnitName(domain.getMeterReadingUnit().getName());
		return dto;
	}
}
