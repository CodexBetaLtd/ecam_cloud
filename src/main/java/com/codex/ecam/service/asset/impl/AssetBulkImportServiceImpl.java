package com.codex.ecam.service.asset.impl;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.dao.admin.AssetBrandDao;
import com.codex.ecam.dao.admin.AssetModelDao;
import com.codex.ecam.dao.asset.AssetCategoryDao;
import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.model.admin.AssetBrand;
import com.codex.ecam.model.admin.AssetModel;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.asset.AssetCategory;
import com.codex.ecam.model.biz.business.Business;
import com.codex.ecam.result.asset.AssetResult;
import com.codex.ecam.service.asset.api.AssetBulkImportService;
import com.codex.ecam.service.asset.api.AssetCategoryService;
import com.codex.ecam.util.AuthenticationUtil;

@Service
public class AssetBulkImportServiceImpl implements AssetBulkImportService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AssetBulkImportServiceImpl.class);

	@Autowired
	private BusinessDao businessDao;

	@Autowired
	private AssetDao assetDao;

	@Autowired
	private AssetBrandDao brandDao;

	@Autowired
	private AssetModelDao modelDao;

	@Autowired
	private AssetCategoryDao assetCategoryDao;

	@Autowired
	AssetCategoryService assetCategoryService;

	@Override
	//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AssetResult importBulk(MultipartFile fileData, Integer businessId){
		AssetResult result=new AssetResult(null, null);
		try {
			final Workbook workbook = WorkbookFactory.create(fileData.getInputStream());

			if (businessId == null) {
				businessId = AuthenticationUtil.getLoginUserBusiness().getId();
			}

			Business business = businessDao.findOne(businessId);

			if(business!=null) {
				importMachines(business, workbook);
				result.setResultStatusSuccess();
				result.addToMessageList("Asset Import Successfully.");
			}else {
				result.setResultStatusError();
				result.addToErrorList("Error occured: Can not find business");
			}

			workbook.close();
		}
		catch (final FileNotFoundException e) {
			result.setResultStatusError();
			result.addToErrorList("File not upload, Please try agian");
			LOGGER.error(e.getMessage());

			throw new RuntimeException(result.getErrorList().get(0));
		}  catch (final SizeLimitExceededException e) {
			result.setResultStatusError();
			result.addToErrorList("File size is exceeded");
			LOGGER.error(e.getMessage());

		}catch (final Exception e) {
			result.setResultStatusError();
			result.addToErrorList("Error occured while saving Asset -> " + e.getMessage());
			LOGGER.error(e.getMessage());

		}

		return result;

	}

	private void importMachines(Business business, final Workbook workbook) throws Exception {

		final Sheet machine = workbook.getSheetAt(0);

		final Iterator<Row> iteratorMachine = machine.iterator();

		
			while (iteratorMachine.hasNext()) {

				saveAsset(business, iteratorMachine);

			}

		
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveAsset(Business business, final Iterator<Row> iteratorMachine) throws Exception {

		final Row nextRow = iteratorMachine.next();

		final int rowIndex = nextRow.getRowNum();

		if (rowIndex != 0) {

			final Iterator<Cell> cellIterator = nextRow.cellIterator();

			Asset asset = new Asset();

			Asset mainLocation = null;
			Asset subLocation_1 = null;
			Asset subLocation_2 = null;

			while (cellIterator.hasNext()) {

				final Cell cell = cellIterator.next();

				final int columnIndex = cell.getColumnIndex();

				switch (columnIndex) {

				case 0:

					asset = getExistAssetAndSetCode(asset, cell);
					break;

				case 1:

					setName(asset, cell);
					break;

				case 2:

					setCategory(business, asset, cell);
					break;

				case 3:

					mainLocation = setMainLocation(business, asset, mainLocation, cell);
					break;

				case 4:

					subLocation_1 = getSubLocation(business, mainLocation, subLocation_1, cell);
					break;

				case 5:

					subLocation_2 = getSubLocation(business, subLocation_1, subLocation_2, cell);
					break;

				case 6:

					setDepartment(asset, cell);
					break;

				case 7:

					setDescription(asset, cell);
					break;

				case 8:

					setAssetBrand(asset, cell, business);
					break;

				case 9:

					setAssetModel(asset, cell);
					break;

				case 10:

					setSize(asset, cell);
					break;

				case 11:

					setSerialNo(asset, cell);
					break;

				case 12:

					setQuantity(asset, cell);
					break;

				case 13:

					setUnitCost(asset, cell);
					break;

				case 14:

					//					setTotalCost(asset, cell);
					break;

				case 15:

					setDateOfPurchase(asset, cell);
					break;

				case 16:

					setUsefulLife(asset, cell);
					break;

				case 17:

					setRemarks(asset, cell);
					break;

				case 18:

					setYearlyDepreciationValue(asset, cell);
					break;

				case 19:

					setYearEndNetBookValue(asset, cell);
					break;

				case 20:

					setAccumulatedDepreciation(asset, cell);
					break;

				default:
					break;
				}
			}

			asset.setIsDeleted(false);
			asset.setIsOnline(false);
			asset.setBusiness(business);

			setAssetTotalCost(asset);

			setMainLocation(asset, mainLocation);

			setSubLocation_1(asset, mainLocation, subLocation_1);

			saveSubLocation_2(asset, subLocation_1, subLocation_2);

			setAssetCategory(asset);

			assetDao.save(asset);

		}
	}

	private void setDescription(Asset asset, final Cell cell) {
		if (isNotNull(cell) ) {

			asset.setDescription( String.valueOf( getCellValue(cell) ) );

		}
	}

	private void setSize(Asset asset, final Cell cell) {
		if (isNotNull(cell) ) {

			asset.setSize( String.valueOf( getCellValue(cell) ));
		}
	}

	private void setSerialNo(Asset asset, final Cell cell) {
		if (isNotNull(cell) ) {

			asset.setSerialNo( String.valueOf( getCellValue(cell) ));

		}
	}

	private void setQuantity(Asset asset, final Cell cell) {
		if (isNotNull(cell) ) {

			asset.setQuantity( ifDoubleConvertToBigDecimal( getCellValue(cell)) );
		}
	}

	private void setUnitCost(Asset asset, final Cell cell) {
		if (isNotNull(cell) ) {

			asset.setUnitCost( ifDoubleConvertToBigDecimal( getCellValue(cell)) );

		}
	}

	private void setTotalCost(Asset asset, final Cell cell) {
		if (isNotNull(cell) ) {

			asset.setTotalCost( ifDoubleConvertToBigDecimal( getCellValue(cell)) );

		}
	}

	private void setDateOfPurchase(Asset asset, final Cell cell) {
		if (isNotNull(cell) ) {

			asset.setDateOfPurchase( (Date) getCellValue(cell));

		}
	}

	private void setUsefulLife(Asset asset, final Cell cell) {
		if (isNotNull(cell) ) {

			asset.setUsefulLife( ifDoubleConvertToBigDecimal( getCellValue(cell)) );

		}
	}

	private void setRemarks(Asset asset, Cell cell) {
		if (isNotNull(cell) ) {

			asset.setRemarks( String.valueOf( getCellValue(cell) ));

		}
	}

	private void setYearlyDepreciationValue(Asset asset, final Cell cell) {
		if (isNotNull(cell) ) {

			asset.setYearlyDepreciationValue( ifDoubleConvertToBigDecimal( getCellValue(cell)));

		}
	}

	private void setYearEndNetBookValue(Asset asset, final Cell cell) {
		if (isNotNull(cell) ) {

			asset.setYearEndNetBookValue( ifDoubleConvertToBigDecimal( getCellValue(cell)));

		}
	}

	private void setAccumulatedDepreciation(Asset asset, final Cell cell) {
		if (isNotNull(cell) ) {

			asset.setAccumulatedDepreciation( ifDoubleConvertToBigDecimal( getCellValue(cell)));

		}
	}

	private void setAssetCategory(Asset asset) {
		if (asset.getAssetCategory() == null) {
			asset.setAssetCategory( assetCategoryDao.findByAssetCategoryByName("Equipments or Machines") );
		}
	}

	private void setSubLocation_1(Asset asset, Asset mainLocation, Asset subLocation) {
		if (subLocation != null) {

			saveSubLocation(mainLocation, subLocation);

			asset.setSite(subLocation);
		}
	}

	private void saveSubLocation_2(Asset asset, Asset mainLocation, Asset subLocation) {
		if (subLocation != null) {

			saveSubLocation(mainLocation, subLocation);

			asset.setSubSite(subLocation);
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	private void saveSubLocation(Asset mainLocation, Asset subLocation) {

		if (subLocation.getId() == null) {

			subLocation.setParentAsset(mainLocation);
			subLocation = assetDao.save(subLocation);

		}
	}

	private void setAssetTotalCost(Asset asset) {
		if (asset.getQuantity() != null && asset.getUnitCost() != null) {
			asset.setTotalCost( asset.getQuantity().multiply(asset.getUnitCost()));
		}
	}

	private void setMainLocation(Asset asset, Asset mainLocation) {
		if (mainLocation != null ) {

			saveMainLocation(mainLocation);

			asset.setParentAsset(mainLocation);
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	private void saveMainLocation(Asset mainLocation) {

		if (mainLocation.getId() == null) {
			mainLocation = assetDao.save(mainLocation);
		}

	}

	private void setDepartment(Asset asset, final Cell cell) {

		if (isNotNull(cell)) {
			asset.setDepartment( String.valueOf( getCellValue(cell) ) );
		}

	}

	private Asset getSubLocation(Business business, Asset mainLocation, Asset subLocation, final Cell cell) {

		if (isNotNull(cell)) {

			subLocation = getSubLocation( cell, mainLocation, business);

		}

		return subLocation;
	}

	private Asset setMainLocation(Business business, Asset asset, Asset mainLocation, final Cell cell) {

		if (isNotNull(cell)) {

			mainLocation = getMainLocation(asset, cell, business);

		}

		return mainLocation;
	}

	private void setCategory(Business business, Asset asset, final Cell cell) throws Exception {

		if (isNotNull(cell)) {
			setCategory( String.valueOf( getCellValue(cell) ), AssetCategoryType.EQUIPMENTS_OR_MACHINES, asset, business);
		} else {
			setCategory( "Equipments or Machines" , AssetCategoryType.EQUIPMENTS_OR_MACHINES, asset, business);
		}

	}

	private void setName(Asset asset, final Cell cell) {

		if (isNotNull(cell)) {
			asset.setName( String.valueOf( getCellValue(cell) ) );
		}

	}

	private Asset getExistAssetAndSetCode(Asset asset, final Cell cell) {

		if (isNotNull(cell)) {

			String code =  String.valueOf( getCellValue(cell) );

			System.out.println(code);

			Asset optionalAsset = assetDao.findByAssetByCode( code );

			if (optionalAsset != null) {
				asset = optionalAsset;
			}

			asset.setCode( code );
		}

		return asset;
	}

	/***************************************
	 *  Asset Site	 *
	 ***************************************/
	private Asset getMainLocation(final Asset asset, final Cell cell, Business business) {

		final List<Asset> assets = assetDao.findByParentAssetByCodeAndBusiness( String.valueOf( getCellValue(cell) ), business.getId());

		if (assets != null && assets.size() > 0) {
			return assets.get(0);
		}

		return createMainLocation( String.valueOf( getCellValue(cell) ), business) ;

	}

	private Asset createMainLocation(String code, Business business) {

		Asset mainLocation = new Asset();

		mainLocation.setBusiness(business);
		mainLocation.setCode(code);
		mainLocation.setName(code);
		mainLocation.setDescription(code);
		mainLocation.setAssetCategory(assetCategoryDao.findByAssetCategoryByName("Locations or Facilities"));
		mainLocation.setIsDeleted(false);
		mainLocation.setIsOnline(false);

		return mainLocation;
	}

	private Asset getSubLocation( final Cell cell, Asset mainLocation, Business business) {

		final List<Asset> childAssets = assetDao.findByChildAssetByCodeAndBusiness( String.valueOf( getCellValue(cell) ), business.getId());

		if (childAssets != null && childAssets.size() > 0) {

			return childAssets.get(0);

		}

		return createSubLocation( String.valueOf( getCellValue(cell) ) , mainLocation, business) ;

	}

	private Asset createSubLocation(String code, Asset mainLocation, Business business) {

		Asset subLocation = new Asset();

		subLocation.setBusiness(business);
		subLocation.setCode(code);
		subLocation.setName(code);
		subLocation.setDescription(code);
		subLocation.setAssetCategory(assetCategoryDao.findByAssetCategoryByName("Locations or Facilities"));
		subLocation.setIsDeleted(false);
		subLocation.setIsOnline(false);

		return subLocation;

	}

	private void setAssetBrand(final Asset asset, final Cell cell, Business business) throws Exception {

		if (isNotNull(cell) ) {

			AssetBrand brand =  brandDao.findByName(  String.valueOf( getCellValue(cell) ) );

			if (brand == null) {

				brand = createAssetBrand( String.valueOf( getCellValue(cell) ), business);

			}

			asset.setBrand(brand);

		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	private AssetBrand createAssetBrand(final String brandName, Business business) throws Exception {

		return brandDao.save(new AssetBrand(brandName, business, false));

	}

	private void setAssetModel(final Asset asset, final Cell cell) throws Exception {

		if (isNotNull(cell) ) {

			AssetModel model =  modelDao.findByNameIgnoreCase(  String.valueOf( getCellValue(cell) ).toLowerCase() );

			if (model == null) {

				model = createAssetModel(asset,  String.valueOf( getCellValue(cell) ) );

			}

			asset.setModel(model);

		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	private AssetModel createAssetModel(final Asset asset, final String modelName) throws Exception {

		return modelDao.save(new AssetModel(modelName, asset.getBrand(), false));

	}

	private void setCategory(String code, AssetCategoryType assetCategoryType, Asset asset, Business business) throws Exception {

		AssetCategory assetCategory = assetCategoryDao.findByAssetCategoryByName(code);

		if (assetCategory == null) {
			if (!code.isEmpty()) {
				assetCategory = createAssetCategory(code, assetCategoryType, business);
			} else {
				assetCategory = assetCategoryDao.findByAssetCategoryByName("Equipments or Machines");
			}
		}

		asset.setAssetCategory( assetCategory);

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	private AssetCategory createAssetCategory(String code, AssetCategoryType assetCategoryType, Business business) throws Exception {

		return assetCategoryDao.save(new AssetCategory(code, code, assetCategoryType, business, false));

	}

	private BigDecimal ifDoubleConvertToBigDecimal(Object val) {

		if( val instanceof Double) {

			return new BigDecimal((Double)val);
		} else if( val instanceof String) {

			if(!((String) val).isEmpty()) {

				return new BigDecimal((String) val);
			}

			return BigDecimal.ZERO;
		}

		return (BigDecimal) val;
	}

	private boolean isNotNull(final Cell cell) {
		return cell == null ? false :  true;
	}

	private Object getCellValue(Cell cell) {

		switch (cell.getCellType()) {

		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue();
			}
			return cell.getNumericCellValue();

		case BOOLEAN:
			return cell.getBooleanCellValue();

		case STRING:
			return cell.getStringCellValue();

		default:
			return cell.getStringCellValue();
		}
	}
}
