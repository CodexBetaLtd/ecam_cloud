package com.codex.ecam.service.asset.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.constants.SMTriggerType;
import com.codex.ecam.constants.inventory.PartType;
import com.codex.ecam.dao.admin.AssetBrandDao;
import com.codex.ecam.dao.admin.AssetEventTypeDao;
import com.codex.ecam.dao.admin.AssetModelDao;
import com.codex.ecam.dao.admin.CountryDao;
import com.codex.ecam.dao.admin.MeterReadingUnitDao;
import com.codex.ecam.dao.admin.UserDao;
import com.codex.ecam.dao.asset.AssetCategoryDao;
import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.dao.asset.AssetMeterReadingDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dao.inventory.BOMGroupDao;
import com.codex.ecam.dao.inventory.BOMGroupPartDao;
import com.codex.ecam.dao.inventory.ReceiptOrderItemDao;
import com.codex.ecam.dao.maintenance.ScheduledMaintenanceAssetDao;
import com.codex.ecam.dto.asset.AssetCategoryDTO;
import com.codex.ecam.dto.asset.AssetDTO;
import com.codex.ecam.dto.asset.AssetEventDTO;
import com.codex.ecam.dto.asset.AssetEventTypeAssetDTO;
import com.codex.ecam.dto.asset.AssetFileDTO;
import com.codex.ecam.dto.asset.AssetMeterReadingConsumptionValueDTO;
import com.codex.ecam.dto.asset.AssetMeterReadingConsumptionVariableDTO;
import com.codex.ecam.dto.asset.AssetMeterReadingDTO;
import com.codex.ecam.dto.asset.AssetMeterReadingValueDTO;
import com.codex.ecam.dto.asset.AssetUserDTO;
import com.codex.ecam.dto.asset.SparePartDTO;
import com.codex.ecam.dto.inventory.AssetConsumingReferenceDTO;
import com.codex.ecam.mappers.asset.AssetEventMapper;
import com.codex.ecam.mappers.asset.AssetEventTypeAssetMapper;
import com.codex.ecam.mappers.asset.AssetFileMapper;
import com.codex.ecam.mappers.asset.AssetMapper;
import com.codex.ecam.mappers.asset.AssetMeterReadingMapper;
import com.codex.ecam.mappers.asset.AssetMeterReadingValueMapper;
import com.codex.ecam.mappers.asset.AssetUserMapper;
import com.codex.ecam.mappers.inventory.AssetConsumingReferenceMapper;
import com.codex.ecam.model.admin.MeterReadingUnit;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.asset.AssetBusiness;
import com.codex.ecam.model.asset.AssetCategory;
import com.codex.ecam.model.asset.AssetConsumingReference;
import com.codex.ecam.model.asset.AssetEvent;
import com.codex.ecam.model.asset.AssetEventTypeAsset;
import com.codex.ecam.model.asset.AssetFile;
import com.codex.ecam.model.asset.AssetMeterReading;
import com.codex.ecam.model.asset.AssetMeterReadingFormulaValue;
import com.codex.ecam.model.asset.AssetMeterReadingFormulaVariable;
import com.codex.ecam.model.asset.AssetMeterReadingValue;
import com.codex.ecam.model.asset.AssetUser;
import com.codex.ecam.model.asset.SparePart;
import com.codex.ecam.model.inventory.bom.BOMGroup;
import com.codex.ecam.model.inventory.bom.BOMGroupPart;
import com.codex.ecam.model.inventory.receiptOrder.ReceiptOrderItem;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.asset.AssetResult;
import com.codex.ecam.service.asset.api.AssetCategoryService;
import com.codex.ecam.service.asset.api.AssetService;
import com.codex.ecam.service.asset.api.WarrantyService;
import com.codex.ecam.service.maintenance.api.ScheduledService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.FileDownloadUtil;
import com.codex.ecam.util.QRCodeUtil;
import com.codex.ecam.util.aws.AmazonS3ObjectUtil;
import com.codex.ecam.util.search.asset.AssetSearchPropertyMapper;
import com.google.zxing.WriterException;

@Service
public class AssetServiceImpl implements AssetService {

	private final static Logger logger = LoggerFactory.getLogger(AssetServiceImpl.class);

	private final String ASSET_DEFAULT_IMAGE = "/resources/images/no_image.png";
	private final String ASSET_NO_QR_IMAGE = "/resources/images/no_qr.png";
	@Autowired
	Environment environment;

	@Autowired
	private AssetDao assetDao;

	@Autowired
	private AssetCategoryDao assetCategoryDao;

	@Autowired
	private BusinessDao businessDao;

	@Autowired
	private MeterReadingUnitDao meterReadingUnitDao;

	@Autowired
	private AssetEventTypeDao assetEventTypeDao;

	@Autowired
	private AssetMeterReadingDao assetMeterReadingDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private WarrantyService warrantyService;

	@Autowired
	private BOMGroupPartDao bomGroupPartDao;

	@Autowired
	private BOMGroupDao bomGroupDao;

	//	@Autowired
	//	private ReceiptOrderDao receiptOrderDao;

	@Autowired
	private ReceiptOrderItemDao receiptOrderItemDao;

	//	@Autowired
	//	private CurrencyDao currencyDao;

	@Autowired
	private CountryDao countryDao;

	@Autowired
	private AssetBrandDao assetBrandDao;

	@Autowired
	private AssetModelDao assetModelDao;

	//	@Autowired
	//	private SupplierDao supplierDao;

	@Autowired
	AssetCategoryService assetCategoryService;

	@Autowired
	ScheduledMaintenanceAssetDao scheduledMaintenanceAssetDao;

	@Autowired
	ScheduledService scheduledService;

	@Autowired
	private AmazonS3ObjectUtil amazonS3ObjectUtil;

	@Override
	public DataTablesOutput<AssetDTO> findAll(FocusDataTablesInput input) throws Exception {
		Specification<Asset> specification;
		AssetSearchPropertyMapper.getInstance().generateDataTableInput(input);

		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			specification = getAdminUserAssetSpecification();
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			specification = getSystemUserAssetSpecification(AuthenticationUtil.getLoginUserBusiness().getId());
		} else {
			specification = getGeneralUserAssetSpecification(AuthenticationUtil.getLoginSite().getSite());
		}

		final DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specification);
		final DataTablesOutput<AssetDTO> out = AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	@Override
	public DataTablesOutput<AssetDTO> findCustomerAssets(FocusDataTablesInput input) throws Exception {
		final Specification<Asset> specification = (root, query, cb) -> cb.notEqual(
				root.get("assetCategory").get("assetCategoryType"), AssetCategoryType.LOCATIONS_OR_FACILITIES);

		AssetSearchPropertyMapper.getInstance().generateDataTableInput(input);
		final DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specification);
		final DataTablesOutput<AssetDTO> out = AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	@Override
	public AssetDTO findById(Integer id) throws Exception {
		final Asset domain = findEntityById(id);
		final AssetDTO assetDTO = createAssetDto(domain);
		return assetDTO;
	}

	private AssetDTO createAssetDto(Asset domain) throws Exception {
		final AssetDTO assetDTO = AssetMapper.getInstance().domainToDto(domain);
		getAssetUser(domain, assetDTO);
		setPurchasingDetails(domain, assetDTO);
		return assetDTO;
	}

	private void setPurchasingDetails(Asset asset, AssetDTO assetDTO) {
		if (asset.getAssetCategory().getAssetCategoryType().equals(AssetCategoryType.EQUIPMENTS_OR_MACHINES)
				|| asset.getAssetCategory().getAssetCategoryType().equals(AssetCategoryType.TOOLS)) {
			final List<ReceiptOrderItem> items = receiptOrderItemDao.findByAsset(asset);
			if (items != null && items.size() > 0) {
				final ReceiptOrderItem item = items.get(0);
				if (item.getReceiptOrder().getId() != null) {
					assetDTO.getAssetPurchasingDetail().setReceiptOrderId(item.getReceiptOrder().getId());
					assetDTO.getAssetPurchasingDetail().setReceiptOrderCode(item.getReceiptOrder().getCode());
					assetDTO.getAssetPurchasingDetail().setExpiryDate(item.getDateExpiryOfInventoryItems());
					assetDTO.getAssetPurchasingDetail().setOrderedDate(item.getReceiptOrder().getDateOrdered());
					assetDTO.getAssetPurchasingDetail().setReceivedDate(item.getReceiptOrder().getDateReceived());
					if (item.getUnitPrice() != null) {
						assetDTO.getAssetPurchasingDetail().setPurchasedPrice(item.getUnitPrice().doubleValue());
					}
					assetDTO.getAssetPurchasingDetail().setItemVersion(item.getVersion());
					assetDTO.getAssetPurchasingDetail().setOrderVersion(item.getReceiptOrder().getVersion());
					if (item.getReceiptOrder().getSupplier() != null) {
						assetDTO.getAssetPurchasingDetail()
						.setPurchasedSupplierId(item.getReceiptOrder().getSupplier().getId());
					}
					if (item.getReceiptOrder().getCurrency() != null) {
						assetDTO.getAssetPurchasingDetail()
						.setPurchasedCurrencyId(item.getReceiptOrder().getCurrency().getId());
					}

					if (item.getUnitPrice() != null) {
						assetDTO.getAssetPurchasingDetail().setPurchasedPrice(item.getUnitPrice().doubleValue());
					}

					if (item.getReceiptOrder() != null && item.getReceiptOrder().getSupplier() != null) {
						assetDTO.getAssetPurchasingDetail()
						.setPurchasedSupplierId(item.getReceiptOrder().getSupplier().getId());
					}

					assetDTO.getAssetPurchasingDetail().setItemVersion(item.getVersion());
					assetDTO.getAssetPurchasingDetail().setOrderVersion(item.getReceiptOrder().getVersion());
				}

			}
		}
	}

	@Override
	public List<AssetDTO> findByAssetCategoryType(AssetCategoryType type) {
		Integer businessId = null;
		if (!AuthenticationUtil.isAuthUserAdminLevel()) {
			businessId = AuthenticationUtil.getLoginUserBusiness().getId();
		}

		return findByAssetCategoryType(businessId, type);
	}

	private void getAssetUser(Asset domain, AssetDTO assetDTO) throws Exception {
		for (final AssetUser assetUser : domain.getAssetUsers()) {
			final AssetUserDTO assetUserDTO = AssetUserMapper.getInstance().domainToDto(assetUser);
			assetDTO.getAssetUserDTOs().add(assetUserDTO);
		}
	}

	@Override
	public List<AssetDTO> findByAssetCategoryType(Integer businessId, AssetCategoryType type) {
		List<Asset> assets = new ArrayList<>();

		try {
			if (businessId == null) {
				assets = assetDao.findByAssetCategoyType(type);
			} else {
				final Specification<Asset> specification = getSystemUserLocationAsset(type, businessId);
				assets = assetDao.findAll(specification);
			}
			return AssetMapper.getInstance().domainToDTOList(assets);
		} catch (final Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	@Override
	public DataTablesOutput<AssetDTO> findAssetByCategoryTypeBusiness(FocusDataTablesInput input, Integer businessId,
			AssetCategoryType type) {
		DataTablesOutput<Asset> assets;

		try {
			final Specification<Asset> specification = (root, query, cb) -> {
				return cb.and(cb.equal(root.get("assetCategory").get("assetCategoryType"), type),
						cb.equal(root.get("business").get("id"), businessId));
			};
			assets = assetDao.findAll(input, specification);
			return AssetMapper.getInstance().domainToDTODataTablesOutput(assets);
		} catch (final Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new DataTablesOutput<AssetDTO>();
		}
	}

	@Override
	public List<AssetDTO> findByExcludingAssetList(AssetCategoryType type, List<Integer> assetList) {
		try {
			final List<Asset> list = assetDao.findByExcludingAssetList(type, assetList);
			return AssetMapper.getInstance().domainToDTOList(list);
		} catch (final Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());

			return new ArrayList<AssetDTO>();
		}
	}

	@Override
	public AssetResult delete(Integer id) {
		final AssetResult result = new AssetResult(null, null);
		try {
			assetDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Asset Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			logger.error(e.getMessage());
			result.addToErrorList("Asset Already Used. Cannot delete.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AssetResult deleteMultiple(Integer[] ids) throws Exception {
		final AssetResult result = new AssetResult(null, null);
		try {
			for (final Integer id : ids) {
				assetDao.delete(id);
			}
			result.setResultStatusSuccess();
			result.addToMessageList("Asset(s) Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Asset(s) Already Used. Cannot delete.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			logger.error(ex.getMessage());
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	public AssetResult save(AssetDTO dto, MultipartFile image) throws Exception {
		final AssetResult result = createAssetResult(dto);
		try {
			saveOrUpdate(result, image);
			result.addToMessageList(getMessageByAction(dto));
		} catch (final ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			logger.error(e.getMessage());
			result.addToErrorList("Asset Already updated. Please Reload Asset.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(AssetResult result, MultipartFile image) throws Exception {
		AssetMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setAssetData(result, image);
		assetDao.save(result.getDomainEntity());
		addAssetToBOMGroup(result.getDomainEntity());
		// addReceiptOrder(result);
		result.updateDtoIdAndVersion();
		scheduledTriggers(result);

	}

	private void setAssetData(AssetResult result, MultipartFile image) throws Exception {
		setAssetCategory(result);
		setAssetCountry(result);
		setParentAsset(result);
		setBusiness(result);
		setSite(result);
		setMeterReadings(result);
		setAssetEvents(result);
		addUsersToAsset(result);
		setPartConsumeReferences(result);
		setCustomer(result);
		setBrand(result);
		setLocation(result);
		setModel(result);
		setAssetFiles(result);
		setAssetSparePart(result);
		setAssetImage(result, image);
		warrantyService.setWarranties(result.getDtoEntity().getWarranties(), result.getDomainEntity());
		generateAssetQR(result);

	}

	private void scheduledTriggers(AssetResult result) {
		scheduledService.notifyAssetTrigger(result.getDomainEntity(), SMTriggerType.METER_READING_TRIGGER);
		scheduledService.notifyAssetTrigger(result.getDomainEntity(), SMTriggerType.ABC_METER_READING_TRIGGER);

		//	Set<ScheduledMaintenanceAsset> maintenanceAssets=result.getDomainEntity().getAssetScheduledMaintenances();
		//	List<ScheduledMaintenance> scheduledMaintenances=scheduledMaintenanceAssetDao.findByAsset(result.getDomainEntity());
		//	for(ScheduledMaintenance scheduledMaintenance:scheduledMaintenances){
		//		for(ScheduledMaintenanceTrigger scheduledMaintenanceTrigger:scheduledMaintenance.getScheduledMaintenanceTriggers()){
		//			scheduledService.createWorkOrderFromTriggerType(scheduledMaintenanceTrigger, scheduledMaintenanceTrigger.getTriggerType());
		//		}
		//	}
	}

	private void autotriggerSchedule(AssetMeterReadingValue meterReadingValue) {

	}

	private void generateAssetQR(AssetResult result) throws WriterException, IOException {

		final String uploadFolder = environment.getProperty("upload.location.asset.qr.s3");
		final String uploadLocation = environment.getProperty("upload.location.s3");
		final String host = environment.getProperty("common.url");
		final String qrCodeText = host + "/asset/machine/edit?id=" + result.getDomainEntity().getId();
		final String filePath = uploadLocation + uploadFolder + "ECAM-ASSET(" + result.getDomainEntity().getCode() + ").png";
		final int size = 500;
		final String fileType = "png";
		final File qrFile = new File(filePath);
		final InputStream inputStream = QRCodeUtil.createQRImage(qrFile, qrCodeText, size, fileType);
		amazonS3ObjectUtil.uploadS3Object(filePath, inputStream);
		result.getDomainEntity().setAssetUrl(filePath);
	}

	private void setAssetSparePart(AssetResult result) throws Exception {
		final Set<SparePart> spareParts = new HashSet<>();

		if (result.getDtoEntity().getSparePartDTOs() != null
				&& result.getDtoEntity().getSparePartDTOs().size() > 0) {

			final Set<SparePart> currentSpareParts = result.getDomainEntity().getSpareParts();

			for (final SparePartDTO sparePartDTO : result.getDtoEntity().getSparePartDTOs()) {
				SparePart sparePart;

				if (sparePartDTO.getId() != null) {
					sparePart = currentSpareParts.stream().filter((x) -> x.getId().equals(sparePartDTO.getId()))
							.findAny().orElseGet(SparePart::new);
				} else {
					sparePart = new SparePart();
				}

				sparePart.setId(sparePartDTO.getId());
				sparePart.setVersion(sparePartDTO.getVersion());
				sparePart.setQuantity(sparePartDTO.getQuantity());
				sparePart.setDescription(sparePartDTO.getDescription());
				sparePart.setAsset(result.getDomainEntity());
				sparePart.setIsDeleted(Boolean.FALSE);
				setSparePartPart(sparePart, sparePartDTO);
				spareParts.add(sparePart);
			}
		}
		result.getDomainEntity().setSpareParts(spareParts);
	}

	private void setSparePartPart(SparePart sparePart, SparePartDTO sparePartDTO) {
		if (sparePartDTO != null && sparePartDTO.getPartId() != null) {
			sparePart.setSparePart(findEntityById(sparePartDTO.getPartId()));
		}
	}

	private void setAssetImage(AssetResult result, MultipartFile image) throws Exception {
		if (image != null) {
			try {
				result.getDomainEntity().setImageLocation(saveImageS3Bucket(result.getDtoEntity(), image));
			} catch (final Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
	}

	private String saveImageS3Bucket(AssetDTO dto, MultipartFile image) throws IOException {

		final String key = environment.getProperty("upload.location.s3")
				+ environment.getProperty("upload.location.asset.image.s3") + dto.getId() + "/" + getFileName(image);
		try {
			if (dto.getImageLocation() != null) {
				amazonS3ObjectUtil.deleteS3Object(dto.getImageLocation());
			}
			amazonS3ObjectUtil.uploadS3Object(key, image);

		} catch (final IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (final Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		return key;
	}

	private String getFileName(MultipartFile file) {
		final String fileName = FilenameUtils.getBaseName(file.getOriginalFilename()).replace(" ", "_");
		final String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		final String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());

		return fileName + "_" + timeStamp + "." + extension;
	}

	private AssetResult createAssetResult(AssetDTO dto) {
		AssetResult result;
		if (dto.getId() != null && dto.getId() > 0) {
			result = new AssetResult(assetDao.findOne(dto.getId()), dto);
		} else {
			result = new AssetResult(new Asset(), dto);
		}

		return result;
	}

	private String getMessageByAction(AssetDTO dto) {
		if (dto.getId() == null) {
			return "Asset Added Successfully.";
		} else {
			return "Asset Updated Successfully.";
		}
	}

	private void setCustomer(AssetResult result) throws Exception {
		if (result.getDtoEntity().getCustomerId() != null && result.getDtoEntity().getCustomerId() > 0) {
			if (isCustomerChanged(result)) {
				result.getDomainEntity().setCustomer(businessDao.findById(result.getDtoEntity().getCustomerId()));
				setAssetCustomers(result);
			}
		} else {
			result.getDomainEntity().setCustomer(null);
		}
	}

	private boolean isCustomerChanged(AssetResult result) {
		if (result.getDomainEntity().getCustomer() == null
				|| result.getDomainEntity().getCustomer().getId() != result.getDtoEntity().getCustomerId()) {
			return true;
		}
		return false;
	}

	private void setAssetCustomers(AssetResult result) throws Exception {

		Set<AssetBusiness> assetCustomers = result.getDomainEntity().getAssetBusinesses();

		if (assetCustomers == null) {
			assetCustomers = new HashSet<>();
			result.getDomainEntity().setAssetBusinesses(assetCustomers);
		}

		if (result.getDomainEntity().getCustomer() != null) {

			final AssetBusiness assetCustomer = new AssetBusiness();
			assetCustomer.setAsset(result.getDomainEntity());
			assetCustomer.setBusiness(result.getDomainEntity().getCustomer());
			assetCustomer.setIsDeleted(false);
			assetCustomers.add(assetCustomer);
		}
		result.getDomainEntity().setAssetBusinesses(assetCustomers);;
	}

	private void setAssetEvents(AssetResult result) throws Exception {
		final Set<AssetEventTypeAsset> assetEventTypeAssets = new HashSet<>();

		if (result.getDtoEntity().getAssetEventTypeAssets() != null
				&& result.getDtoEntity().getAssetEventTypeAssets().size() > 0) {

			for (final AssetEventTypeAssetDTO assetEventTypeAssetDto : result.getDtoEntity().getAssetEventTypeAssets()) {

				final AssetEventTypeAsset assetEventTypeAsset = getCurrentAssetEvent(
						result.getDomainEntity().getAssetEventTypeAssets(), assetEventTypeAssetDto);
				final List<AssetEventDTO> assetEventDtos = getAssetEventByEventType(result, assetEventTypeAssetDto);
				updateAssetEventTypeAsset(assetEventTypeAsset, assetEventTypeAssetDto, assetEventDtos,
						result.getDomainEntity());
				assetEventTypeAssets.add(assetEventTypeAsset);
			}
		}
		result.getDomainEntity().setAssetEventTypeAssets(assetEventTypeAssets);
	}

	private AssetEventTypeAsset getCurrentAssetEvent(Set<AssetEventTypeAsset> currentAssetEventTypeAssets,
			AssetEventTypeAssetDTO assetEventTypeAssetDto) {

		AssetEventTypeAsset assetEventTypeAsset;

		if (currentAssetEventTypeAssets != null && currentAssetEventTypeAssets.size() > 0) {
			assetEventTypeAsset = currentAssetEventTypeAssets.stream()
					.filter((x) -> x.getId().equals(assetEventTypeAssetDto.getId())).findAny()
					.orElseGet(AssetEventTypeAsset::new);
		} else {
			assetEventTypeAsset = new AssetEventTypeAsset();
		}

		return assetEventTypeAsset;
	}

	private List<AssetEventDTO> getAssetEventByEventType(AssetResult result,
			AssetEventTypeAssetDTO assetEventTypeAssetDto) {
		final List<AssetEventDTO> assetEventDtos = new ArrayList<>();

		for (final AssetEventDTO assetEventDto : result.getDtoEntity().getAssetEvents()) {
			if (assetEventDto.getAssetEventTypeId().equals(assetEventTypeAssetDto.getAssetEventTypeId())) {
				assetEventDtos.add(assetEventDto);
			}
		}
		return assetEventDtos;
	}

	private void updateAssetEventTypeAsset(AssetEventTypeAsset domain, AssetEventTypeAssetDTO dto,
			List<AssetEventDTO> assetEventDtos, Asset asset) throws Exception {
		AssetEventTypeAssetMapper.getInstance().dtoToDomain(dto, domain);

		domain.setAsset(asset);
		domain.setAssetEventType(assetEventTypeDao.findById(dto.getAssetEventTypeId()));

		Set<AssetEvent> assetEvents = domain.getAssetEvents();

		if (assetEvents == null) {
			assetEvents = new HashSet<>();
		}

		AssetEvent assetEvent;

		for (final AssetEventDTO assetEventDto : assetEventDtos) {

			assetEvent = new AssetEvent();
			AssetEventMapper.getInstance().dtoToDomain(assetEventDto, assetEvent);

			assetEvent.setAdditionalDescription(assetEventDto.getAssetEventDescription());
			assetEvent.setOccuredDate(new Date(assetEventDto.getAssetEventOccuredDate()));
			assetEvent.setAssetEventTypeAsset(domain);

			if (dto.getLatestAssetEventIndexId().equals(assetEventDto.getAssetEventIndex())) {
				domain.setLatestAssetEvent(assetEvent);
			}

			assetEvents.add(assetEvent);
		}

		domain.setAssetEvents(assetEvents);
	}

	//	private void addReceiptOrder(AssetResult result) {
	//		final AssetCategoryType type = result.getDomainEntity().getAssetCategory().getAssetCategoryType();
	//		if (type.equals(AssetCategoryType.EQUIPMENTS_OR_MACHINES) || type.equals(AssetCategoryType.TOOLS)) {
	//			final ReceiptOrder receiptOrder = createReceiptOrder(result.getDtoEntity().getAssetPurchasingDetail(),
	//					result.getDomainEntity());
	//			receiptOrderDao.save(receiptOrder);
	//		}
	//
	//	}
	//
	//	private ReceiptOrder createReceiptOrder(AssetPurchasingDTO assetPurchasingDetail, Asset asset) {
	//		ReceiptOrder ro;
	//
	//		if (assetPurchasingDetail.getReceiptOrderId() != null && assetPurchasingDetail.getReceiptOrderId() > 0) {
	//			ro = receiptOrderDao.findOne(assetPurchasingDetail.getReceiptOrderId());
	//		} else {
	//			ro = new ReceiptOrder();
	//			ro.setCode("RO_" + asset.getCode());
	//		}
	//
	//		ro.setReceiptOrderStatus(ReceiptOrderStatus.RECEIVED);
	//		addReceiptOrderItem(ro, assetPurchasingDetail, asset);
	//
	//		if (assetPurchasingDetail.getPurchasedCurrencyId() != null
	//				&& assetPurchasingDetail.getPurchasedCurrencyId() > 0) {
	//			ro.setCurrency(currencyDao.findById(assetPurchasingDetail.getPurchasedCurrencyId()));
	//		}
	//
	//		if (assetPurchasingDetail.getPurchasedSupplierId() != null
	//				&& assetPurchasingDetail.getPurchasedSupplierId() > 0) {
	//			ro.setSupplier(supplierDao.findOne(assetPurchasingDetail.getPurchasedSupplierId()));
	//		}
	//
	//		if (assetPurchasingDetail.getOrderedDate() != null) {
	//			ro.setDateOrdered(assetPurchasingDetail.getOrderedDate());
	//		}
	//
	//		if (assetPurchasingDetail.getReceivedDate() != null) {
	//			ro.setDateReceived(assetPurchasingDetail.getReceivedDate());
	//		}
	//
	//		ro.setIsDeleted(false);
	//		ro.setVersion(assetPurchasingDetail.getOrderVersion());
	//
	//		return ro;
	//	}
	//
	//	private void addReceiptOrderItem(ReceiptOrder ro, AssetPurchasingDTO assetPurchasingDetail, Asset asset) {
	//		final Set<ReceiptOrderItem> items = new HashSet<>();
	//		ReceiptOrderItem item = new ReceiptOrderItem();
	//		if (ro.getReceiptOrderItems() != null && ro.getReceiptOrderItems().size() > 0) {
	//			// item = ro.getReceiptOrderItems().get(0);
	//			final Optional<ReceiptOrderItem> first = ro.getReceiptOrderItems().stream().findFirst();
	//			if (first.isPresent()) {
	//				item = first.get();
	//			}
	//
	//		} else {
	//			item = new ReceiptOrderItem();
	//		}
	//		item.setAsset(asset);
	//		item.setReceiptOrder(ro);
	//		if (assetPurchasingDetail.getPurchasedPrice() != null && assetPurchasingDetail.getPurchasedPrice() > 0) {
	//			item.setTotalPrice(BigDecimal.valueOf(assetPurchasingDetail.getPurchasedPrice()));
	//			item.setUnitPrice(BigDecimal.valueOf(assetPurchasingDetail.getPurchasedPrice()));
	//			item.setQuantityReceived(BigDecimal.ONE);
	//		}
	//
	//		if (assetPurchasingDetail.getExpiryDate() != null) {
	//			item.setDateExpiryOfInventoryItems(assetPurchasingDetail.getExpiryDate());
	//		}
	//		item.setIsDeleted(Boolean.FALSE);
	//		item.setVersion(assetPurchasingDetail.getItemVersion());
	//		items.add(item);
	//		ro.setReceiptOrderItems(items);
	//	}

	private void addAssetToBOMGroup(Asset asset) throws Exception {
		final List<BOMGroup> assetBOMGroups = bomGroupDao.findGroupsByAssetId(asset.getId());
		for (final AssetConsumingReference consumeRef : asset.getPartConsumingReferences()) {
			if (consumeRef.getBomGroupAsset() != null) {
				final Optional<BOMGroup> group = assetBOMGroups.stream()
						.filter((x) -> x.getId().equals(consumeRef.getBomGroupAsset().getBomGroup().getId())).findAny();
				if (!group.isPresent()) {
					createBOMGroupPart(consumeRef.getBomGroupAsset().getBomGroup(), asset);
				}
			}
		}
	}

	private void createBOMGroupPart(BOMGroup bomGroup, Asset asset) {
		final BOMGroupPart groupPart = new BOMGroupPart();
		groupPart.setBomGroup(bomGroup);
		groupPart.setPart(asset);
		groupPart.setIsDeleted(false);

		bomGroupPartDao.save(groupPart);
	}

	private void setPartConsumeReferences(AssetResult result) throws Exception {

		final Set<AssetConsumingReference> partConsumeRefs = new HashSet<>();

		if (result.getDtoEntity().getPartConsumeRefs() != null
				&& result.getDtoEntity().getPartConsumeRefs().size() > 0) {

			for (final AssetConsumingReferenceDTO partConsumeRefDTO : result.getDtoEntity().getPartConsumeRefs()) {
				final AssetConsumingReference partConsumeRef = getCurrentPartConsumeRef(
						result.getDomainEntity().getPartConsumingReferences(), partConsumeRefDTO);
				updatePartConsumeReference(partConsumeRefDTO, result.getDomainEntity(), partConsumeRef);
				partConsumeRefs.add(partConsumeRef);
			}

		}
		result.getDomainEntity().setPartConsumingReferences(partConsumeRefs);
	}

	private AssetConsumingReference getCurrentPartConsumeRef(Set<AssetConsumingReference> currentPartConsRefs,
			AssetConsumingReferenceDTO partConsumeRefDTO) {

		AssetConsumingReference partConsumeRef;

		if (currentPartConsRefs != null && currentPartConsRefs.size() > 0) {
			partConsumeRef = currentPartConsRefs.stream().filter((x) -> x.getId().equals(partConsumeRefDTO.getId()))
					.findAny().orElseGet(AssetConsumingReference::new);
		} else {
			partConsumeRef = new AssetConsumingReference();
		}

		return partConsumeRef;
	}

	private void updatePartConsumeReference(AssetConsumingReferenceDTO dto, Asset asset, AssetConsumingReference domain)
			throws Exception {

		AssetConsumingReferenceMapper.getInstance().dtoToDomain(dto, domain);
		if (dto.getBomGroupPartId() != null && dto.getBomGroupPartId() > 0) {
			domain.setBomGroupAsset(bomGroupPartDao.findOne(dto.getBomGroupPartId()));
		}
		domain.setPart(assetDao.findOne(dto.getPartId()));
		domain.setAsset(asset);
	}

	private void setParentAsset(AssetResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getParentAssetId() != null) {
			result.getDomainEntity().setParentAsset(findEntityById(result.getDtoEntity().getParentAssetId()));
		}
	}

	private void setBrand(AssetResult result) {
		if (result.getDtoEntity().getBrand() != null && result.getDtoEntity().getBrand() > 0) {
			result.getDomainEntity().setBrand(assetBrandDao.findById(result.getDtoEntity().getBrand()));
		}
	}

	private void setLocation(AssetResult result) {
		if (result.getDtoEntity().getLocationDTO() != null) {
			result.getDomainEntity().setLatitude(result.getDtoEntity().getLocationDTO().getLatitude());
			result.getDomainEntity().setLongitude(result.getDtoEntity().getLocationDTO().getLongitude());
		}
	}

	private void setModel(AssetResult result) {
		if (result.getDtoEntity().getModel() != null && result.getDtoEntity().getModel() > 0) {
			result.getDomainEntity().setModel(assetModelDao.findById(result.getDtoEntity().getModel()));
		}
	}

	private void setAssetCategory(AssetResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getAssetCategoryId() != null) {
			result.getDomainEntity()
			.setAssetCategory(assetCategoryDao.findById(result.getDtoEntity().getAssetCategoryId()));
		}
	}

	private void setAssetCountry(AssetResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getCountryId() != null) {
			result.getDomainEntity().setCountry(countryDao.findById(result.getDtoEntity().getCountryId()));
		}
	}

	private void addUsersToAsset(AssetResult result) {

		final Set<AssetUser> assetUsers = new HashSet<AssetUser>();
		for (final AssetUserDTO assetUserDTO : result.getDtoEntity().getAssetUserDTOs()) {
			final AssetUser assetUser = getCurrentAssetUser(result.getDomainEntity().getAssetUsers(), assetUserDTO);
			assetUser.setAsset(result.getDomainEntity());
			assetUser.setUser(userDao.findById(assetUserDTO.getUserId()));
			assetUser.setIsDeleted(false);
			assetUsers.add(assetUser);
		}
		result.getDomainEntity().setAssetUsers(null);
		result.getDomainEntity().setAssetUsers(assetUsers);
	}

	private AssetUser getCurrentAssetUser(Set<AssetUser> currentAssetUsers, AssetUserDTO assetUserDTO) {
		AssetUser assetUser;
		if (currentAssetUsers != null && currentAssetUsers.size() > 0) {
			assetUser = currentAssetUsers.stream().filter((x) -> x.getId().equals(assetUserDTO.getId())).findAny()
					.orElseGet(AssetUser::new);
		} else {
			assetUser = new AssetUser();
		}

		return assetUser;
	}

	private void setBusiness(AssetResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getBusinessId() != null) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}

	private void setSite(AssetResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getSiteId() != null) {
			result.getDomainEntity().setSite(assetDao.findOne(result.getDtoEntity().getSiteId()));
		}
	}

	private void setMeterReadings(AssetResult result) throws Exception {

		final Set<AssetMeterReading> assetMeterReadings = new HashSet<AssetMeterReading>();
		if (result.getDtoEntity().getAssetMeterReadings() != null
				&& result.getDtoEntity().getAssetMeterReadings().size() > 0) {

			for (final AssetMeterReadingDTO assetsMeterReadingDTO : result.getDtoEntity().getAssetMeterReadings()) {
				final AssetMeterReading assetMeterReading = getCurrentAssetMeterReading(
						result.getDomainEntity().getAssetMeterReadings(), assetsMeterReadingDTO);
				final List<AssetMeterReadingValueDTO> assetMeterReadingValues = getMeterReadingValuesByMeterReading(result,
						assetsMeterReadingDTO);
				updateAssetMeterReading(assetMeterReading, assetsMeterReadingDTO, assetMeterReadingValues,
						result.getDomainEntity());
				updateAverageMeterReadingValue(assetMeterReading);
				assetMeterReadings.add(assetMeterReading);
				setMeterReadingConsumptionVariable(assetsMeterReadingDTO, assetMeterReading);
			}
		}

		result.getDomainEntity().setAssetMeterReadings(assetMeterReadings);
	}

	private void setMeterReadingConsumptionVariable(AssetMeterReadingDTO assetsMeterReadingDTO,
			AssetMeterReading assetMeterReading) {
		final Set<AssetMeterReadingFormulaVariable> assetMeterReadingConsumptionVariables = new HashSet<AssetMeterReadingFormulaVariable>();
		for (final AssetMeterReadingConsumptionVariableDTO consumptionVariableDTO : assetsMeterReadingDTO
				.getConsumptionVariableDTO()) {
			final AssetMeterReadingFormulaVariable assetMeterReadingConsumptionVariable = new AssetMeterReadingFormulaVariable();
			assetMeterReadingConsumptionVariable.setVariableName(consumptionVariableDTO.getVariable());
			assetMeterReadingConsumptionVariable.setAssetMeterReading(assetMeterReading);
			assetMeterReadingConsumptionVariable.setIsDeleted(Boolean.FALSE);
			setMeterReadingVariableUnit(consumptionVariableDTO, assetMeterReadingConsumptionVariable);
			assetMeterReadingConsumptionVariables.add(assetMeterReadingConsumptionVariable);
		}
		assetMeterReading.setFormulaVariables(assetMeterReadingConsumptionVariables);
	}

	private void setMeterReadingVariableUnit(AssetMeterReadingConsumptionVariableDTO consumptionVariableDTO,
			AssetMeterReadingFormulaVariable assetMeterReadingConsumptionVariable) {
		if (consumptionVariableDTO != null && consumptionVariableDTO.getMeteReadingUnitId() != null) {
			assetMeterReadingConsumptionVariable
			.setMeterReadingUnit(meterReadingUnitDao.findOne(consumptionVariableDTO.getMeteReadingUnitId()));
		}
	}

	@Override
	public synchronized void updateAverageMeterReadingValue(AssetMeterReading assetMeterReading) {

		Double avgval = 0.00;

		if (assetMeterReading.getAssetMeterReadingValues() != null
				&& assetMeterReading.getAssetMeterReadingValues().size() > 1) {
			final List<AssetMeterReadingValue> list = assetMeterReading.getAssetMeterReadingValues().stream()
					.sorted(Comparator.comparing(AssetMeterReadingValue::getAddedDate).reversed())
					.collect(Collectors.toList());
			final Long diff = list.get(0).getAddedDate().getTime() - list.get(1).getAddedDate().getTime();
			final long diffDays = diff / (24 * 60 * 60 * 1000);
			if (diffDays > 0) {
				avgval = (list.get(0).getMeterReadingValue() - list.get(1).getMeterReadingValue()) / diffDays;
			}
		}

		assetMeterReading.setAvgMeterReadingValue(avgval);
	}

	private AssetMeterReading getCurrentAssetMeterReading(Set<AssetMeterReading> currentAssetMeterReadings,
			AssetMeterReadingDTO assetsMeterReadingDTO) {
		AssetMeterReading assetMeterReading;

		if (currentAssetMeterReadings != null && currentAssetMeterReadings.size() > 0) {
			assetMeterReading = currentAssetMeterReadings.stream()
					.filter((x) -> x.getId().equals(assetsMeterReadingDTO.getMeterReadingId())).findAny()
					.orElseGet(AssetMeterReading::new);
		} else {
			assetMeterReading = new AssetMeterReading();
		}

		return assetMeterReading;
	}

	private synchronized void updateAssetMeterReading(AssetMeterReading domain, AssetMeterReadingDTO dto,
			List<AssetMeterReadingValueDTO> assetMeterReadingValueDtos, Asset asset) throws Exception {

		AssetMeterReadingMapper.getInstance().dtoToDomain(dto, domain);

		domain.setAsset(asset);
		domain.setMeterReadingUnit(meterReadingUnitDao.findById(dto.getMeterReadingUnitId()));

		Set<AssetMeterReadingValue> assetMeterReadingValues = domain.getAssetMeterReadingValues();

		if (assetMeterReadingValues == null) {
			assetMeterReadingValues = new HashSet<>();
			domain.setAssetMeterReadingValues(assetMeterReadingValues);
		}

		AssetMeterReadingValue meterReadingValue;
		for (final AssetMeterReadingValueDTO assetMeterReadingValueDto : assetMeterReadingValueDtos) {

			meterReadingValue = new AssetMeterReadingValue();
			AssetMeterReadingValueMapper.getInstance().dtoToDomain(assetMeterReadingValueDto, meterReadingValue);

			meterReadingValue.setAssetMeterReading(domain);
			meterReadingValue.setFunctionString(assetMeterReadingValueDto.getMeterReadingConsumptionFunction());

			if (dto.getMeterReadingCurrentValueIndex()
					.equals(assetMeterReadingValueDto.getAssetMeterReadingValueIndex())) {
				domain.setCurrentAssetMeterReadingValue(meterReadingValue);
			}
			autotriggerSchedule(meterReadingValue);
			updateAssetMeterReadingConsumption(assetMeterReadingValueDto, meterReadingValue);
			assetMeterReadingValues.add(meterReadingValue);
		}
	}

	private void updateAssetMeterReadingConsumption(AssetMeterReadingValueDTO meterReadingValueDTO,
			AssetMeterReadingValue meterReadingValue) throws Exception {
		final List<AssetMeterReadingFormulaValue> valueConsumptions = new ArrayList<>();
		for (final AssetMeterReadingConsumptionValueDTO assetMeterReadingValueConsumptionDTO : meterReadingValueDTO
				.getValueConsumptionDTO()) {

			final AssetMeterReadingFormulaValue consumption = new AssetMeterReadingFormulaValue();
			consumption.setMeterReadingIndex(meterReadingValueDTO.getAssetMeterReadingValueIndex());
			// consumption.setVariable(assetMeterReadingValueConsumptionDTO.getVariable());
			consumption.setIsDeleted(Boolean.FALSE);
			consumption.setValue(assetMeterReadingValueConsumptionDTO.getValue());
			consumption.setAssetMeterReadingValue(meterReadingValue);
			valueConsumptions.add(consumption);
		}
		meterReadingValue.setAssetMeterReadingFormulaValues(valueConsumptions);
	}

	private List<AssetMeterReadingValueDTO> getMeterReadingValuesByMeterReading(AssetResult result,
			AssetMeterReadingDTO assetMeterReadingDTO) {
		final List<AssetMeterReadingValueDTO> assetMeterReadingValueDtos = new ArrayList<>();
		for (final AssetMeterReadingValueDTO meterReadingValueDto : result.getDtoEntity().getAssetMeterReadingValues()) {
			if (meterReadingValueDto.getAssetMeterReadingIndex().equals(assetMeterReadingDTO.getMeterReadingIndex())) {
				assetMeterReadingValueDtos.add(meterReadingValueDto);
			}
		}
		return assetMeterReadingValueDtos;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteAll() {
		assetDao.deleteAll();
	}

	@Override
	public List<AssetDTO> findAllByLevel() {
		try {
			Specification<Asset> specification;
			if (AuthenticationUtil.isAuthUserAdminLevel()) {
				specification = getAdminUserAssetSpecification();
			} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
				specification = getSystemUserAssetSpecification(AuthenticationUtil.getLoginUserBusiness().getId());
			} else {
				specification = getGeneralUserAssetSpecification(AuthenticationUtil.getLoginSite().getSite());
			}
			final List<Asset> list = assetDao.findAll(specification);
			return AssetMapper.getInstance().domainToDTOList(list);

		} catch (final Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public Asset findEntityById(Integer id) {
		Asset asset = new Asset();
		asset = assetDao.findOne(id);
		return asset;
	}

	@Override
	public List<AssetDTO> findAllSiteByLevel() throws Exception {
		List<AssetDTO> dtoList = null;
		Specification<Asset> specification = null;

		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			specification = getAdminUserLocationAsset(AssetCategoryType.LOCATIONS_OR_FACILITIES);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			specification = getSystemUserLocationAsset(AssetCategoryType.LOCATIONS_OR_FACILITIES,
					AuthenticationUtil.getLoginUserBusiness().getId());
		}

		if (specification != null) {
			final List<Asset> list = assetDao.findAll(specification);
			dtoList = AssetMapper.getInstance().domainToDTOList(list);
		} else {
			dtoList = AuthenticationUtil.getUserSiteList();
		}
		return dtoList;

	}

	@Override
	public List<AssetDTO> findAllSiteByBusiness(Integer businessId) throws Exception {
		List<AssetDTO> dtoList = null;
		Specification<Asset> specification = null;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			specification = getAdminUserLocationAsset(AssetCategoryType.LOCATIONS_OR_FACILITIES);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			specification = getSystemUserLocationAsset(AssetCategoryType.LOCATIONS_OR_FACILITIES, businessId);
		}

		if (specification != null) {
			final List<Asset> list = assetDao.findAll(specification);
			dtoList = AssetMapper.getInstance().domainToDTOList(list);
		}

		return dtoList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveAll(List<AssetDTO> entities) throws Exception {
		for (final AssetDTO dto : entities) {
			save(dto, null);
		}
	}

	@Override
	public List<AssetDTO> findAll() {

		final List<Asset> assets = (List<Asset>) assetDao.findAll();
		try {
			return AssetMapper.getInstance().domainToDTOList(assets);
		} catch (final Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public List<AssetMeterReadingDTO> findByMeterReadingByAsset(String name) throws Exception {
		if (name != null) {
			final Asset asset = assetDao.findAssetMetereReadingsByAssetName(name);
			final Integer id = asset.getId();
			final List<AssetMeterReadingDTO> dtos = AssetMeterReadingMapper.getInstance()
					.domainToDTOList(assetMeterReadingDao.findAssetMeterReadingByAssetId(id));
			return dtos;
		}
		return null;
	}

	@Override
	public List<AssetMeterReadingDTO> findAssetMeterReadingByAssetId(Integer id) throws Exception {
		if (id != null) {
			final List<AssetMeterReadingDTO> dtos = AssetMeterReadingMapper.getInstance()
					.domainToDTOList(assetMeterReadingDao.findAssetMeterReadingByAssetId(id));
			return dtos;
		}
		return new ArrayList<>();
	}

	@Override
	public DataTablesOutput<AssetMeterReadingDTO> findMeterReadingHistory(Integer assetId) {

		final DataTablesOutput<AssetMeterReadingDTO> dataTablesOutput = new DataTablesOutput<>();

		final List<AssetMeterReadingValue> list = assetMeterReadingDao.findLAllAssetMetereReadingsByAsset(assetId);
		final List<AssetMeterReadingDTO> assetMeterReadingDTOs = new ArrayList<>();
		for (final AssetMeterReadingValue assetMeterReadingValue : list) {
			assetMeterReadingDTOs.add(addAssetMeterReading(assetMeterReadingValue));
		}
		dataTablesOutput.setData(assetMeterReadingDTOs);

		return dataTablesOutput;
	}

	private AssetMeterReadingDTO addAssetMeterReading(AssetMeterReadingValue assetMeterReadingValue) {
		final AssetMeterReadingDTO assetMeterReadingDTO = new AssetMeterReadingDTO();
		assetMeterReadingDTO.setMeterReadingName(assetMeterReadingValue.getAssetMeterReading().getMeterReadingName());
		assetMeterReadingDTO.setMeterReadingCurrentValue(assetMeterReadingValue.getMeterReadingValue());
		assetMeterReadingDTO.setMeterReadingViewName(
				assetMeterReadingValue.getAssetMeterReading().getMeterReadingUnit().getName() + " ("
						+ assetMeterReadingValue.getAssetMeterReading().getMeterReadingUnit().getSymbol() + " )");
		return assetMeterReadingDTO;
	}

	@Override
	public List<AssetMeterReadingDTO> findAllAssetMeterReading() throws Exception {
		final Iterable<AssetMeterReading> assetMeterReadings = assetMeterReadingDao.findAll();
		final List<AssetMeterReadingDTO> assetMeterReadingDTOs = AssetMeterReadingMapper.getInstance()
				.domainToDTOList(assetMeterReadings);
		return assetMeterReadingDTOs;
	}

	@Override
	public List<AssetMeterReadingDTO> findByMeterReadingByAssetId(Integer id) throws Exception {
		final List<AssetMeterReadingDTO> dtos = AssetMeterReadingMapper.getInstance()
				.domainToDTOList(assetMeterReadingDao.findAssetMeterReadingByAssetId(id));
		for (final AssetMeterReadingDTO assetMeterReadingDTO : dtos) {
			final MeterReadingUnit meterReadingUnit = meterReadingUnitDao
					.findById(assetMeterReadingDTO.getMeterReadingUnitId());
			assetMeterReadingDTO.setMeterReadingViewName(
					assetMeterReadingDTO.getMeterReadingName() + " (" + meterReadingUnit.getSymbol() + ")");
		}
		return dtos;
	}

	private Specification<Asset> getAdminUserAssetSpecification() {

		final Specification<Asset> specification = (root, query, cb) -> cb
				.notEqual(root.get("assetCategory").get("assetCategoryType"), AssetCategoryType.PARTS_AND_SUPPLIES);
		return specification;
	}

	private Specification<Asset> getSystemUserAssetSpecification(Integer businessId) {

		final Specification<Asset> specification = (root, query, cb) -> {
			return cb.and(
					cb.notEqual(root.get("assetCategory").get("assetCategoryType"),
							AssetCategoryType.PARTS_AND_SUPPLIES),
					cb.equal(root.get("business").get("id"), businessId));
		};

		return specification;
	}

	private Specification<Asset> getGeneralUserAssetSpecification(Asset site) {

		final Specification<Asset> specification = (root, query, cb) -> cb.and(
				cb.notEqual(root.get("assetCategory").get("assetCategoryType"), AssetCategoryType.PARTS_AND_SUPPLIES),
				cb.equal(root.get("site"), site));

		return specification;
	}

	private Specification<Asset> getSystemUserLocationAsset(AssetCategoryType assetCategoryType, Integer businessId) {

		final Specification<Asset> specification = (root, query, cb) -> {
			return cb.and(cb.equal(root.get("assetCategory").get("assetCategoryType"), assetCategoryType),
					cb.equal(root.get("business").get("id"), businessId));
		};

		return specification;
	}

	private Specification<Asset> getAdminUserLocationAsset(AssetCategoryType assetCategoryType) {

		final Specification<Asset> specification = (root, query, cb) -> cb
				.equal(root.get("assetCategory").get("assetCategoryType"), assetCategoryType);

		return specification;
	}

	@Override
	public DataTablesOutput<AssetDTO> findParts(FocusDataTablesInput input) throws Exception {

		Specification<Asset> specification;
		AssetSearchPropertyMapper.getInstance().generateDataTableInput(input);
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			specification = (root, query, cb) -> cb.notEqual(root.get("assetCategory").get("assetCategoryType"),
					AssetCategoryType.LOCATIONS_OR_FACILITIES);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			specification = (root, query, cb) -> {
				return cb.and(
						cb.notEqual(root.get("assetCategory").get("assetCategoryType"),
								AssetCategoryType.LOCATIONS_OR_FACILITIES),
						cb.equal(root.get("business").get("id"), AuthenticationUtil.getLoginUserBusiness().getId()));
			};
		} else {
			specification = (root, query, cb) -> cb.and(
					cb.notEqual(root.get("assetCategory").get("assetCategoryType"),
							AssetCategoryType.LOCATIONS_OR_FACILITIES),
					cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite()));
		}

		final DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specification);
		final DataTablesOutput<AssetDTO> out = AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	@Override
	public List<AssetDTO> findSiteByBusinessId(Integer businessId, AssetCategoryType assetCategoryType) {
		try {
			List<AssetDTO> dtoList = null;

			final Specification<Asset> specification = (root, query, cb) -> {
				return cb.and(cb.equal(root.get("assetCategory").get("assetCategoryType"), assetCategoryType),
						cb.equal(root.get("business").get("id"), businessId));
			};

			if (specification != null) {
				final List<Asset> list = assetDao.findAll(specification);
				dtoList = AssetMapper.getInstance().domainToDTOList(list);
			}

			return dtoList;

		} catch (final Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public AssetMeterReadingDTO findAssetMeterReadingById(Integer id) throws Exception {
		AssetMeterReadingDTO assetMeterReadingDTO = new AssetMeterReadingDTO();
		assetMeterReadingDTO = AssetMeterReadingMapper.getInstance().domainToDto(assetMeterReadingDao.findOne(id));
		return assetMeterReadingDTO;
	}

	@Override
	public DataTablesOutput<AssetDTO> findByCategoryType(FocusDataTablesInput input, AssetCategoryType type)
			throws Exception {

		Specification<Asset> specification;
		AssetSearchPropertyMapper.getInstance().generateDataTableInput(input);
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			specification = (root, query, cb) -> cb.equal(root.get("assetCategory").get("assetCategoryType"), type);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			specification = (root, query, cb) -> {
				final Predicate assetCategory = cb.equal(root.get("assetCategory").get("assetCategoryType"), type);
				final Predicate assetBusiness = cb.equal(root.get("business").get("id"),
						AuthenticationUtil.getLoginUserBusiness().getId());
				return cb.and(assetCategory, assetBusiness);
			};
		} else {
			specification = (root, query, cb) -> {
				final Predicate assetCategory = cb.equal(root.get("assetCategory").get("assetCategoryType"), type);
				final Predicate assetSite = cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite());
				return cb.and(assetCategory, assetSite);
			};
		}
		AssetSearchPropertyMapper.getInstance().generateDataTableInput(input);
		final DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specification);
		final DataTablesOutput<AssetDTO> out = AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	@Override
	public DataTablesOutput<AssetDTO> findSiteByBusiness(FocusDataTablesInput input, Integer bizId) throws Exception {
		try {
			AssetSearchPropertyMapper.getInstance().generateDataTableInput(input);
			final DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specSiteListByBusiness(bizId));
			return AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (final Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new DataTablesOutput<>();
		}
	}

	private Specification<Asset> specSiteListByBusiness(Integer bizId) {
		return (root, query, cb) -> {
			final List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("business").get("id"), bizId));
			predicates.add(cb.equal(root.get("assetCategory").get("assetCategoryType"),
					AssetCategoryType.LOCATIONS_OR_FACILITIES));
			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}

	@Override
	public DataTablesOutput<AssetDTO> findAnyAssetTypeByBusiness(FocusDataTablesInput input, Integer bizId)
			throws Exception {
		try {
			AssetSearchPropertyMapper.getInstance().generateDataTableInput(input);
			final DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specByBusiness(bizId));
			return AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (final Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new DataTablesOutput<>();
		}
	}

	@Override
	public DataTablesOutput<AssetDTO> getAssetByLogUserBusiness(FocusDataTablesInput input) throws Exception {
		try {
			AssetSearchPropertyMapper.getInstance().generateDataTableInput(input);
			Integer bizId;
			if (!AuthenticationUtil.isAuthUserAdminLevel()) {
				bizId = AuthenticationUtil.getCurrentUser().getBusiness().getId();
			} else {
				throw new Exception("ERROR..! ADMIN User NOT assigned to the business");
			}
			final DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specByBusiness(bizId));
			return AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (final Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new DataTablesOutput<>();
		}
	}

	private Specification<Asset> specByBusiness(Integer bizId) {
		return (root, query, cb) -> {
			final List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("business").get("id"), bizId));
			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}

	@Override
	public DataTablesOutput<AssetDTO> findAssetByAssetTypeAndBusiness(FocusDataTablesInput input,
			AssetCategoryType categoryType, Integer bizId) throws Exception {
		try {
			AssetSearchPropertyMapper.getInstance().generateDataTableInput(input);
			final DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specAssetByBusiness(bizId, categoryType));
			return AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (final Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new DataTablesOutput<>();
		}
	}

	private Specification<Asset> specAssetByBusiness(Integer bizId, AssetCategoryType categoryType) {
		return (root, query, cb) -> {
			final List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("business").get("id"), bizId));
			predicates.add(cb.equal(root.get("assetCategory").get("assetCategoryType"), categoryType));
			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}

	@Override
	public DataTablesOutput<AssetDTO> findAssetNotByAssetTypeAndBusiness(FocusDataTablesInput input,
			AssetCategoryType categoryType, Integer bizId) throws Exception {
		try {
			AssetSearchPropertyMapper.getInstance().generateDataTableInput(input);
			final DataTablesOutput<Asset> domainOut = assetDao.findAll(input,
					specAssetNotAssetTypeByAndBusiness(bizId, categoryType));
			return AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (final Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new DataTablesOutput<>();
		}
	}

	private Specification<Asset> specAssetNotAssetTypeByAndBusiness(Integer bizId, AssetCategoryType categoryType) {
		return (root, query, cb) -> {
			final List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("business").get("id"), bizId));
			predicates.add(cb.notEqual(root.get("assetCategory").get("assetCategoryType"), categoryType));
			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}

	@Override
	public DataTablesOutput<AssetDTO> findAllFacilitiesByLevel(FocusDataTablesInput input) throws Exception {

		AssetSearchPropertyMapper.getInstance().generateDataTableInput(input);

		final Specification<Asset> specification = (root, query, cb)->{
			final List<Predicate> predicates = new ArrayList<>();
			if (AuthenticationUtil.isAuthUserSystemLevel()) {
				predicates.add(cb.equal(root.get("business").get("id"), AuthenticationUtil.getLoginUserBusiness().getId()));
			} else if(AuthenticationUtil.isAuthUserGeneralLevel()) {
				predicates.add(cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite()));
			}
			predicates.add(cb.equal(root.get("assetCategory").get("assetCategoryType"), AssetCategoryType.LOCATIONS_OR_FACILITIES));
			return cb.and(predicates.toArray(new Predicate[0]));
		};

		final DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specification);
		final DataTablesOutput<AssetDTO> out = AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	@Override
	public DataTablesOutput<AssetDTO> findAllMachineAndToolsByLevel(FocusDataTablesInput input) throws Exception {

		Specification<Asset> specification;
		AssetSearchPropertyMapper.getInstance().generateDataTableInput(input);
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			specification = (root, query, cb) -> cb.and(
					cb.notEqual(root.get("assetCategory").get("assetCategoryType"),
							AssetCategoryType.PARTS_AND_SUPPLIES),
					cb.notEqual(root.get("assetCategory").get("assetCategoryType"),
							AssetCategoryType.LOCATIONS_OR_FACILITIES));

		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			specification = (root, query, cb) -> {
				return cb.and(
						cb.notEqual(root.get("assetCategory").get("assetCategoryType"),
								AssetCategoryType.LOCATIONS_OR_FACILITIES),
						cb.notEqual(root.get("assetCategory").get("assetCategoryType"),
								AssetCategoryType.PARTS_AND_SUPPLIES),
						cb.equal(root.get("business").get("id"), AuthenticationUtil.getLoginUserBusiness().getId()));
			};
		} else {
			specification = (root, query, cb) -> cb.and(
					cb.notEqual(root.get("assetCategory").get("assetCategoryType"),
							AssetCategoryType.LOCATIONS_OR_FACILITIES),
					cb.notEqual(root.get("assetCategory").get("assetCategoryType"),
							AssetCategoryType.PARTS_AND_SUPPLIES),
					cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite()));
		}

		final DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specification);
		final DataTablesOutput<AssetDTO> out = AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	@Override
	public String assetFileUpload(MultipartFile file, String refId) throws Exception {
		final String key = environment.getProperty("upload.location.s3")
				+ environment.getProperty("upload.location.asset.file.s3") + refId + "/" + file.getOriginalFilename();
		try {
			amazonS3ObjectUtil.uploadS3Object(key, file);
			return key;
		} catch (final Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void assetFileDownload(Integer refId, HttpServletResponse response) throws Exception {
		if (refId != null) {
			final AssetFile file = assetDao.findByFileId(refId);
			final int index = file.getFileLocation().lastIndexOf("\\");
			final String fileName = file.getFileLocation().substring(index + 1);
			amazonS3ObjectUtil.downloadToResponse(file.getFileLocation(), fileName, response);

		}
	}

	@Override
	public void assetQRDownload(Integer id, HttpServletResponse response) throws Exception {
		if (id != null) {
			final String file = assetDao.getAssetQRLocation(id);
			final int index = file.lastIndexOf("\\");
			final String fileName = file.substring(index + 1);
			amazonS3ObjectUtil.downloadToResponse(file, fileName, response);
		}
	}

	private void setAssetFiles(AssetResult result) throws Exception {
		final Set<AssetFile> assetFiles = new HashSet<>();

		if (result.getDtoEntity().getAssetFileDTOs() != null
				&& result.getDtoEntity().getAssetFileDTOs().size() > 0) {

			final Set<AssetFile> currentAssetFiles = result.getDomainEntity().getAssetFiles();

			for (final AssetFileDTO assetFileDTO : result.getDtoEntity().getAssetFileDTOs()) {
				AssetFile assetFile;

				if (assetFileDTO.getId() != null) {
					assetFile = currentAssetFiles.stream().filter((x) -> x.getId().equals(assetFileDTO.getId()))
							.findAny().orElseGet(AssetFile::new);
				} else {
					assetFile = new AssetFile();
				}

				AssetFileMapper.getInstance().dtoToDomain(assetFileDTO, assetFile);
				assetFile.setAsset(result.getDomainEntity());

				assetFiles.add(assetFile);
			}
		}
		result.getDomainEntity().setAssetFiles(assetFiles);
	}

	@Override
	public byte[] getAssetImageStream(Integer id, HttpServletRequest request) throws IOException {

		if (id != null) {
			final String imagePath = assetDao.getAssetImageLocation(id);
			if (imagePath != null) {
				// return FileDownloadUtil.getByteInputStream(uploadLocation + imagePath);
				return amazonS3ObjectUtil.downloadByteArray(imagePath);
			}
		}

		return FileDownloadUtil
				.getByteInputStream(request.getServletContext().getRealPath("").concat(ASSET_DEFAULT_IMAGE));
	}

	@Override
	public byte[] getAssetQRStream(Integer id, HttpServletRequest request) throws IOException {

		if (id != null) {
			final String imagePath = assetDao.getAssetQRLocation(id);
			if (imagePath != null) {
				return amazonS3ObjectUtil.downloadByteArray(imagePath);
				// return FileDownloadUtil.getByteInputStream(imagePath);
			}
		}

		return FileDownloadUtil
				.getByteInputStream(request.getServletContext().getRealPath("").concat(ASSET_NO_QR_IMAGE));
	}

	@Override
	public DataTablesOutput<AssetDTO> getMachineToolsByBusiness(FocusDataTablesInput input, Integer bizId)
			throws Exception {

		AssetSearchPropertyMapper.getInstance().generateDataTableInput(input);
		final Specification<Asset> specification = (root, query, cb) -> cb.and(
				cb.notEqual(root.get("assetCategory").get("assetCategoryType"),
						AssetCategoryType.LOCATIONS_OR_FACILITIES),
				cb.notEqual(root.get("assetCategory").get("assetCategoryType"), AssetCategoryType.PARTS_AND_SUPPLIES),
				cb.equal(root.get("business").get("id"), bizId));

		final DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specification);
		final DataTablesOutput<AssetDTO> out = AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	@Override
	public List<AssetDTO> findAssetsByCategoryBusiness(Integer bizId, Integer categoryId) throws Exception {
		List<Asset> assets = new ArrayList<>();
		try {
			final Specification<Asset> specification = (root, query, cb) -> {
				return cb.and(cb.equal(root.get("assetCategory").get("id"), categoryId),
						cb.equal(root.get("business").get("id"), bizId));
			};
			assets = assetDao.findAll(specification);
			return AssetMapper.getInstance().domainToDTOList(assets);
		} catch (final Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new ArrayList<>();
		}
	}

	@Override
	public DataTablesOutput<AssetDTO> findPartsByBusiness(FocusDataTablesInput input, Integer bizId) throws Exception {

		AssetSearchPropertyMapper.getInstance().generateDataTableInput(input);
		final Specification<Asset> specification = (root, query, cb) -> cb.and(
				cb.equal(root.get("assetCategory").get("assetCategoryType"), AssetCategoryType.PARTS_AND_SUPPLIES),
				cb.equal(root.get("business").get("id"), bizId));

		final DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specification);
		final DataTablesOutput<AssetDTO> out = AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	@Override
	public DataTablesOutput<AssetDTO> findRepairablePartsByBusiness(FocusDataTablesInput input, Integer bizId)
			throws Exception {

		AssetSearchPropertyMapper.getInstance().generateDataTableInput(input);
		final Specification<Asset> specification = (root, query, cb) -> cb.and(
				cb.equal(root.get("assetCategory").get("assetCategoryType"), AssetCategoryType.PARTS_AND_SUPPLIES),
				cb.isNotNull(root.get("partType")), cb.equal(root.get("partType"), PartType.REPAIRABLE),
				cb.equal(root.get("business").get("id"), bizId)

				);

		final DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specification);
		final DataTablesOutput<AssetDTO> out = AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	@Override
	public void importBulkAssets(MultipartFile fileData, Integer bussinessId) throws Exception {

		final FileInputStream inputStream = (FileInputStream) fileData.getInputStream();

		final Workbook workbook = new XSSFWorkbook(inputStream);
		final Sheet location = workbook.getSheetAt(0);

		final Iterator<Row> iterator = location.iterator();

		while (iterator.hasNext()) {
			final Row nextRow = iterator.next();
			final int rowIndex = nextRow.getRowNum();
			if (rowIndex != 0) {
				final Iterator<Cell> cellIterator = nextRow.cellIterator();
				final AssetDTO assetDTO = new AssetDTO();
				while (cellIterator.hasNext()) {
					final Cell cell = cellIterator.next();
					final int columnIndex = cell.getColumnIndex();
					switch (columnIndex) {
					case 0:
						System.out.print("Asset No - " + cell.getStringCellValue());
						assetDTO.setCode(cell.getStringCellValue());
						break;
					case 1:
						System.out.print("Asset Group Code - " + cell.getStringCellValue());
						assetDTO.setAssetCategoryId(findParentAssetCategoryByCode(cell.getStringCellValue(),
								AssetCategoryType.LOCATIONS_OR_FACILITIES).getId());

						break;
					case 2:
						System.out.print("Short Description - " + cell.getStringCellValue());
						assetDTO.setName(cell.getStringCellValue());
						assetDTO.setDescription(cell.getStringCellValue());

						break;
					case 3:
						System.out.print("Parent Asset - " + cell.getStringCellValue());
						if (cell.getStringCellValue() != null) {
							final Asset parentAsset = assetDao.findByAssetByCode(cell.getStringCellValue());
							if (parentAsset != null) {
								assetDTO.setParentAssetId(parentAsset.getId());

							}

						} else {
							// asset.setParentAsset(parentAsset);
						}
						break;
					case 5:
						System.out.print("Asset Type - " + cell.getCellType());
						//					System.out.print("Test 4 "+cell.getRow().getCell(4).getNumericCellValue());
						//						break;
					default:
						break;
					}
					System.out.print(" ___ ");
				}
				if (AuthenticationUtil.isAuthUserAdminLevel()) {
					assetDTO.setBusinessId(bussinessId);
				} else {
					assetDTO.setBusinessId(AuthenticationUtil.getLoginUserBusiness().getId());

				}
				// save(assetDTO, null);

				System.out.println();
			}

		}
		final Sheet machine = workbook.getSheetAt(1);
		System.out.println("machine");
		final Iterator<Row> iteratorMachine = machine.iterator();
		//List<Asset> assetList=new ArrayList<>();
		while (iteratorMachine.hasNext()) {
			final Row nextRow = iteratorMachine.next();
			final int rowIndex = nextRow.getRowNum();
			if (rowIndex != 0) {
				final Iterator<Cell> cellIterator = nextRow.cellIterator();
				final AssetDTO assetDTO = new AssetDTO();
				while (cellIterator.hasNext()) {
					final Cell cell = cellIterator.next();
					final int columnIndex = cell.getColumnIndex();
					switch (columnIndex) {
					case 0:
						System.out.print("Asset No - " + cell.getStringCellValue());
						assetDTO.setCode(cell.getStringCellValue());
						break;
					case 1:
						System.out.print("Asset Group Code - " + cell.getStringCellValue());
						assetDTO.setAssetCategoryId(findParentAssetCategoryByCode(cell.getStringCellValue(),
								AssetCategoryType.EQUIPMENTS_OR_MACHINES).getId());

						break;
					case 2:
						System.out.print("Short Description - " + cell.getStringCellValue());
						assetDTO.setName(cell.getStringCellValue());
						assetDTO.setDescription(cell.getStringCellValue());

						break;
					case 3:
						System.out.print("Parent Asset - " + cell.getStringCellValue());
						if (cell.getStringCellValue() != null) {
							final Asset parentAsset = assetDao.findByAssetByCode(cell.getStringCellValue());
							if (parentAsset != null) {
								assetDTO.setParentAssetId(parentAsset.getId());

							}

						} else {
							// asset.setParentAsset(parentAsset);
						}
						break;
					case 5:
						System.out.print("Asset Type - " + cell.getCellType());
						//					System.out.print("Test 4 "+cell.getRow().getCell(4).getNumericCellValue());
						//						break;
					default:
						break;
					}
					System.out.print(" ___ ");
				}
				if (AuthenticationUtil.isAuthUserAdminLevel()) {
					assetDTO.setBusinessId(bussinessId);
				} else {
					assetDTO.setBusinessId(AuthenticationUtil.getLoginUserBusiness().getId());

				}
				save(assetDTO, null);

				System.out.println();
			}

		}

		workbook.close();
		inputStream.close();
		/// assetDao.save(assetList);
	}

	private AssetCategory findParentAssetCategoryByCode(String code, AssetCategoryType assetCategoryType) {
		final AssetCategoryDTO assetCategoryDTO = new AssetCategoryDTO();
		AssetCategory assetCategory = assetCategoryDao.findByAssetCategoryByCode(code);
		if (assetCategory != null) {
		} else {
			assetCategory = new AssetCategory();
			assetCategoryDTO.setName(code);
			assetCategoryDTO.setDescription(code);
			assetCategoryDTO.setType(assetCategoryType);
			assetCategoryDTO.setBusinessId(AuthenticationUtil.getLoginUserBusiness().getId());
			assetCategory = assetCategoryService.save(assetCategoryDTO).getDomainEntity();
		}

		return assetCategory;

	}

}
