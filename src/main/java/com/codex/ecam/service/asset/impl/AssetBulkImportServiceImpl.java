package com.codex.ecam.service.asset.impl;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import com.codex.ecam.service.asset.api.AssetBulkImportService;
import com.codex.ecam.service.asset.api.AssetCategoryService;
import com.codex.ecam.util.AuthenticationUtil;

@Service
public class AssetBulkImportServiceImpl implements AssetBulkImportService {

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
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void importBulk(MultipartFile fileData, Integer businessId) throws Exception {

		final FileInputStream inputStream = (FileInputStream) fileData.getInputStream();

		final Workbook workbook = new XSSFWorkbook(inputStream);

		if (businessId == null) {
			businessId = AuthenticationUtil.getLoginUserBusiness().getId();
		}

		Business business = businessDao.findOne(businessId);

		importLocations(business, workbook);
		importMachines(business, workbook);

		workbook.close();
		inputStream.close();
	}

	private void importLocations(Business business, final Workbook workbook) {

		final Sheet location = workbook.getSheetAt(0);

		final Iterator<Row> iterator = location.iterator();

		try {
			while (iterator.hasNext()) {

				final Row nextRow = iterator.next();

				final int rowIndex = nextRow.getRowNum();

				if (rowIndex != 0) {

					final Iterator<Cell> cellIterator = nextRow.cellIterator();

					final Asset asset = new Asset();

					while (cellIterator.hasNext()) {

						final Cell cell = cellIterator.next();
						final int columnIndex = cell.getColumnIndex();

						switch (columnIndex) {
						case 0:
							asset.setCode(cell.getStringCellValue());
							break;
						case 1:
							setCategory(cell.getStringCellValue(),
									AssetCategoryType.LOCATIONS_OR_FACILITIES, asset, business);
							break;
						case 2:
							asset.setName(cell.getStringCellValue());
							asset.setDescription(cell.getStringCellValue());
							break;
						case 3:
							if (isNotNullCellStringValue(cell)) {

								setSubLocation(asset, cell);

							}
							break;
						case 5:

						default:
							break;
						}
					}

					asset.setBusiness(business);

					// save(assetDTO, null);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	private void importMachines(Business business, final Workbook workbook) throws Exception {

		final Sheet machine = workbook.getSheetAt(1);

		final Iterator<Row> iteratorMachine = machine.iterator();

		try {
			while (iteratorMachine.hasNext()) {

				final Row nextRow = iteratorMachine.next();

				final int rowIndex = nextRow.getRowNum();

				if (rowIndex != 0) {

					final Iterator<Cell> cellIterator = nextRow.cellIterator();

					Asset asset = new Asset();

					while (cellIterator.hasNext()) {

						final Cell cell = cellIterator.next();

						final int columnIndex = cell.getColumnIndex();

						switch (columnIndex) {

						case 0:

							if (isNotNullCellStringValue(cell)) {

								Asset optionalAsset = assetDao.findByAssetByCode(cell.getStringCellValue());

								if (optionalAsset != null) {
									asset = optionalAsset;
								}

								asset.setCode(cell.getStringCellValue());
							}

							break;

						case 1:

							if (isNotNullCellStringValue(cell)) {
								asset.setName(cell.getStringCellValue());
							}

							break;

						case 2:

							if (isNotNullCellStringValue(cell)) {
								setCategory(cell.getStringCellValue(), AssetCategoryType.EQUIPMENTS_OR_MACHINES, asset, business);
							}

							asset.setDescription(cell.getStringCellValue());
							break;

						case 3:
							if (isNotNullCellStringValue(cell)) {

								setMainLocation(asset, cell, business);

							}
							break;

						case 4:

							if (isNotNullCellStringValue(cell)) {

								setSubLocation(asset, cell);

							}

							break;

						case 5:

							if (isNotNullCellStringValue(cell)) {

								setSubLocation(asset, cell);

							}

							break;

						case 6:
							if (isNotNullCellStringValue(cell) ) {

								setAssetBrand(asset, cell, business);

							}
							break;

						case 7:
							if (isNotNullCellStringValue(cell) ) {

								setAssetModel(asset, cell);

							}
							break;

						case 8:
							asset.setSerialNo(cell.getStringCellValue());
							break;

						case 9:
							asset.setSerialNo(cell.getStringCellValue());
							break;

						default:
							break;
						}
					}

					asset.setIsDeleted(false);
					asset.setBusiness(business);

					assetDao.save(asset);

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isNotNullCellStringValue(final Cell cell) {
		return cell.getStringCellValue() != null;
	}

	/***************************************
	 *  Asset Site	 *
	 ***************************************/
	private void setMainLocation(final Asset asset, final Cell cell, Business business) {

		final List<Asset> assets = assetDao.findByParentAssetByCodeAndBusiness(cell.getStringCellValue(), business.getId());

		if (assets != null && assets.size() > 0) {
			asset.setSite(assets.get(0));
		} else {
			asset.setSite(createMainLocation(cell.getStringCellValue(), business));
		}

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	private Asset createMainLocation(String stringCellValue, Business business) {
		Asset mainLocation = new Asset();

		mainLocation.setBusiness(business);

		return null;
	}

	private void setSubLocation(final Asset asset, final Cell cell) {
		final Asset parentAsset = assetDao.findByAssetByCode(cell.getStringCellValue());

		if (parentAsset != null) {
			asset.setParentAsset(parentAsset);
		}
	}

	private void setAssetBrand(final Asset asset, final Cell cell, Business business) throws Exception {

		AssetBrand brand =  brandDao.findByName( cell.getStringCellValue() );

		if (brand == null) {

			brand = createAssetBrand(cell.getStringCellValue(), business);

		}

		asset.setBrand(brand);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	private AssetBrand createAssetBrand(final String brandName, Business business) throws Exception {

		return brandDao.save(new AssetBrand(brandName, business, false));

	}

	private void setAssetModel(final Asset asset, final Cell cell) throws Exception {

		AssetModel model =  modelDao.findByNameIgnoreCase( cell.getStringCellValue().toLowerCase() );

		if (model == null) {

			model = createAssetModel(asset, cell.getStringCellValue());

		}

		asset.setModel(model);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	private AssetModel createAssetModel(final Asset asset, final String modelName) throws Exception {

		return modelDao.save(new AssetModel(modelName, asset.getBrand(), false));

	}

	private void setCategory(String code, AssetCategoryType assetCategoryType, Asset asset, Business business) throws Exception {

		AssetCategory assetCategory = assetCategoryDao.findByAssetCategoryByCode(code);

		if (assetCategory == null) {

			assetCategory = createAssetCategory(code, assetCategoryType, business);
		}

		asset.setAssetCategory( assetCategory);

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	private AssetCategory createAssetCategory(String code, AssetCategoryType assetCategoryType, Business business) throws Exception {

		return assetCategoryDao.save(new AssetCategory(code, code, assetCategoryType, business, false));

	}
}
