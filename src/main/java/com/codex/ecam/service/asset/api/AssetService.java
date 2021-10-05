package com.codex.ecam.service.asset.api;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.multipart.MultipartFile;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.dto.asset.AssetDTO;
import com.codex.ecam.dto.asset.AssetMeterReadingDTO;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.asset.AssetMeterReading;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.asset.AssetResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface AssetService {

	AssetDTO findById(Integer id) throws Exception;

	List<AssetDTO> findByAssetCategoryType(AssetCategoryType type);

	List<AssetDTO> findByAssetCategoryType(Integer businessId, AssetCategoryType type);

	List<AssetDTO> findByExcludingAssetList(AssetCategoryType type, List<Integer> assetList);

	AssetResult delete(Integer id);

	AssetResult save(AssetDTO asset, MultipartFile file) throws Exception;

	void deleteAll();

	void saveAll(List<AssetDTO> entities) throws Exception;

	List<AssetDTO> findAll();

	List<AssetDTO> findAllByLevel();

	Asset findEntityById(Integer siteId);

	List<AssetDTO> findAllSiteByLevel() throws Exception;

	List<AssetDTO> findAllSiteByBusiness(Integer businessId) throws Exception;

	List<AssetMeterReadingDTO> findByMeterReadingByAsset(String name) throws Exception;

	List<AssetMeterReadingDTO> findByMeterReadingByAssetId(Integer id) throws Exception;

	List<AssetMeterReadingDTO> findAllAssetMeterReading() throws Exception;

	List<AssetMeterReadingDTO> findAssetMeterReadingByAssetId(Integer assetId) throws Exception;

	List<AssetDTO> findAssetsByCategoryBusiness(Integer bizId, Integer type) throws Exception;

	//    DataTablesOutput<AssetMeterReadingDTO> findAssetEventType(Integer assetId);

	List<AssetDTO> findSiteByBusinessId(Integer businessId, AssetCategoryType assetCategoryType);

	public DataTablesOutput<AssetDTO> findCustomerAssets(FocusDataTablesInput input) throws Exception;

	AssetMeterReadingDTO findAssetMeterReadingById(Integer id) throws Exception;

	DataTablesOutput<AssetDTO> findAll(FocusDataTablesInput input) throws Exception;

	DataTablesOutput<AssetDTO> findParts(FocusDataTablesInput input) throws Exception;

	DataTablesOutput<AssetMeterReadingDTO> findMeterReadingHistory(Integer assetId);

	DataTablesOutput<AssetDTO> findByCategoryType(FocusDataTablesInput input, AssetCategoryType type) throws Exception;

	DataTablesOutput<AssetDTO> findSiteByBusiness(FocusDataTablesInput input, Integer id) throws Exception;

	DataTablesOutput<AssetDTO> findAnyAssetTypeByBusiness(FocusDataTablesInput input, Integer bizId) throws Exception;

	DataTablesOutput<AssetDTO> getAssetByLogUserBusiness(FocusDataTablesInput input) throws Exception;

	DataTablesOutput<AssetDTO> findAssetByAssetTypeAndBusiness(FocusDataTablesInput input, AssetCategoryType categoryType, Integer bizId) throws Exception;

	DataTablesOutput<AssetDTO> findAssetNotByAssetTypeAndBusiness(FocusDataTablesInput input, AssetCategoryType categoryType, Integer bizId) throws Exception;

	DataTablesOutput<AssetDTO> findAllFacilitiesByLevel(FocusDataTablesInput input) throws Exception;

	DataTablesOutput<AssetDTO> findAllMachineAndToolsByLevel(FocusDataTablesInput input) throws Exception;

	DataTablesOutput<AssetDTO> findAssetByCategoryTypeBusiness(FocusDataTablesInput input, Integer businessId, AssetCategoryType type);

	DataTablesOutput<AssetDTO> findPartsByBusiness(FocusDataTablesInput input, Integer id) throws Exception;

	DataTablesOutput<AssetDTO> findRepairablePartsByBusiness(FocusDataTablesInput input, Integer id) throws Exception;

	String assetFileUpload(MultipartFile file,String refId) throws Exception;

	void assetFileDownload(Integer id,HttpServletResponse response) throws Exception;
	void assetQRDownload(Integer id,HttpServletResponse response) throws Exception;

	byte[] getAssetImageStream(Integer id, HttpServletRequest request) throws IOException;
	byte[] getAssetQRStream(Integer id, HttpServletRequest request) throws IOException;

	DataTablesOutput<AssetDTO> getMachineToolsByBusiness(FocusDataTablesInput input, Integer bizId) throws Exception;

	void updateAverageMeterReadingValue(AssetMeterReading assetMeterReading);

	void importBulkAssets(MultipartFile file,Integer bussinessId) throws IOException, Exception ;

	AssetResult deleteMultiple(Integer[] ids) throws Exception;



}
