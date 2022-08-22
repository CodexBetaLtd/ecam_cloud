package com.codex.ecam.service.asset.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

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
import com.codex.ecam.dto.asset.AssetCategoryDTO;
import com.codex.ecam.model.admin.AssetBrand;
import com.codex.ecam.model.admin.AssetModel;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.asset.AssetCategory;
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
	public void importBulk(MultipartFile fileData, Integer bussinessId){

		try {
			final InputStream inputStream = fileData.getInputStream();

			final Workbook workbook = new XSSFWorkbook(fileData.getInputStream());

			importLocations(bussinessId, workbook);
			importMachines(bussinessId, workbook);

			workbook.close();
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void importLocations(Integer bussinessId, final Workbook workbook) {

		final Sheet location = workbook.getSheetAt(0);

		final Iterator<Row> iterator = location.iterator();

		while (iterator.hasNext()) {

			final Row nextRow = iterator.next();

			final int rowIndex = nextRow.getRowNum();

			if (rowIndex != 0) {

				final Iterator<Cell> cellIterator = nextRow.cellIterator();

				final Asset asset = new Asset();
				setAssetBusiness(bussinessId, asset);

				while (cellIterator.hasNext()) {

					final Cell cell = cellIterator.next();
					final int columnIndex = cell.getColumnIndex();

					switch (columnIndex) {
					case 0:
						asset.setCode(cell.getStringCellValue());
						break;
					case 1:
						setCategory(cell.getStringCellValue(),
								AssetCategoryType.LOCATIONS_OR_FACILITIES, asset);
						break;
					case 2:
						asset.setName(cell.getStringCellValue());
						asset.setDescription(cell.getStringCellValue());
						break;
					case 3:
						if (cell.getStringCellValue() != null) {

							setParentAsset(asset, cell);

						}
						break;
					case 5:

					default:
						break;
					}
				}

				// save(assetDTO, null);
			}

		}
	}

	private void importMachines(Integer bussinessId, final Workbook workbook) {

		final Sheet machine = workbook.getSheetAt(1);

		final Iterator<Row> iteratorMachine = machine.iterator();

		while (iteratorMachine.hasNext()) {

			final Row nextRow = iteratorMachine.next();

			final int rowIndex = nextRow.getRowNum();

			if (rowIndex != 0) {

				final Iterator<Cell> cellIterator = nextRow.cellIterator();

				final Asset asset = new Asset();
				asset.setIsDeleted(false);
				setAssetBusiness(bussinessId, asset);

				while (cellIterator.hasNext()) {

					final Cell cell = cellIterator.next();

					final int columnIndex = cell.getColumnIndex();

					switch (columnIndex) {

					case 0:
						asset.setName(cell.getStringCellValue());
						break;

					case 1:
						asset.setCode(cell.getStringCellValue());
						break;

					case 2:
						asset.setDescription(cell.getStringCellValue());
						break;

					case 3:
						if (cell.getStringCellValue() != null) {

							setSite(asset, cell);

						}
						break;

					case 4:

						setCategory(cell.getStringCellValue(), AssetCategoryType.EQUIPMENTS_OR_MACHINES, asset);

						break;

					case 5:

						if (cell.getStringCellValue() != null) {

							setParentAsset(asset, cell);

						}

						break;

					case 6:
						if (cell.getStringCellValue() != null ) {

							setAssetBrand(asset, cell);

						}
						break;

					case 7:
						if (cell.getStringCellValue() != null ) {

							setAssetModel(asset, cell);

						}
						break;

					case 8:
						asset.setSerialNo(cell.getStringCellValue());
						break;

					default:
						break;
					}
				}

				assetDao.save(asset);

			}

		}
	}

	private void setSite(final Asset asset, final Cell cell) {

		final Asset site = assetDao.findByAssetByCode(cell.getStringCellValue());

		if (site != null) {
			asset.setSite(site);
		}

	}

	private void setParentAsset(final Asset asset, final Cell cell) {
		final Asset parentAsset = assetDao.findByAssetByCode(cell.getStringCellValue());

		if (parentAsset != null) {
			asset.setParentAsset(parentAsset);
		}
	}

	private void setAssetBrand(final Asset asset, final Cell cell) {
		AssetBrand brand =  brandDao.findByName( cell.getStringCellValue() );

		if (brand == null) {

			brand = new AssetBrand();
			brand.setBrandName(cell.getStringCellValue());
			brand.setBusiness(asset.getBusiness());
			brand.setIsDeleted(false);

			brand = brandDao.save(brand);

		}

		asset.setBrand(brand);
	}

	private void setAssetModel(final Asset asset, final Cell cell) {
		AssetModel model =  modelDao.findByNameIgnoreCase( cell.getStringCellValue().toLowerCase() );

		if (model == null) {
			model = new AssetModel();
			model.setModelName(cell.getStringCellValue());
			model.setAssetBrand(asset.getBrand());
			model.setIsDeleted(false);

			model = modelDao.save(model);
		}

		asset.setModel(model);
	}

	private void setAssetBusiness(Integer bussinessId, final Asset asset) {
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			asset.setBusiness(businessDao.findOne(bussinessId));
		} else {
			asset.setBusiness(AuthenticationUtil.getLoginUserBusiness());
		}
	}

	private void setCategory(String code, AssetCategoryType assetCategoryType, Asset asset) {

		final AssetCategoryDTO assetCategoryDTO = new AssetCategoryDTO();

		AssetCategory assetCategory = assetCategoryDao.findByAssetCategoryByCode(code);

		if (assetCategory == null) {

			assetCategory = new AssetCategory();
			assetCategoryDTO.setName(code);
			assetCategoryDTO.setDescription(code);
			assetCategoryDTO.setType(assetCategoryType);
			assetCategoryDTO.setBusinessId(AuthenticationUtil.getLoginUserBusiness().getId());

			assetCategory = assetCategoryService.save(assetCategoryDTO).getDomainEntity();
		}

		asset.setAssetCategory( assetCategory);

	}
}
