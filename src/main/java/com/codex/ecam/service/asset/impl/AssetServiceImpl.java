package com.codex.ecam.service.asset.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.codex.ecam.constants.inventory.ReceiptOrderStatus;
import com.codex.ecam.dao.admin.AssetBrandDao;
import com.codex.ecam.dao.admin.AssetEventTypeDao;
import com.codex.ecam.dao.admin.AssetModelDao;
import com.codex.ecam.dao.admin.CountryDao;
import com.codex.ecam.dao.admin.CurrencyDao;
import com.codex.ecam.dao.admin.MeterReadingUnitDao;
import com.codex.ecam.dao.admin.UserDao;
import com.codex.ecam.dao.asset.AssetCategoryDao;
import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.dao.asset.AssetMeterReadingDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dao.biz.SupplierDao;
import com.codex.ecam.dao.inventory.BOMGroupDao;
import com.codex.ecam.dao.inventory.BOMGroupPartDao;
import com.codex.ecam.dao.inventory.ReceiptOrderDao;
import com.codex.ecam.dao.inventory.ReceiptOrderItemDao;
import com.codex.ecam.dto.asset.AssetDTO;
import com.codex.ecam.dto.asset.AssetEventDTO;
import com.codex.ecam.dto.asset.AssetEventTypeAssetDTO;
import com.codex.ecam.dto.asset.AssetFileDTO;
import com.codex.ecam.dto.asset.AssetMeterReadingConsumptionValueDTO;
import com.codex.ecam.dto.asset.AssetMeterReadingConsumptionVariableDTO;
import com.codex.ecam.dto.asset.AssetMeterReadingDTO;
import com.codex.ecam.dto.asset.AssetMeterReadingValueDTO;
import com.codex.ecam.dto.asset.AssetPurchasingDTO;
import com.codex.ecam.dto.asset.AssetUserDTO;
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
import com.codex.ecam.model.asset.AssetConsumingReference;
import com.codex.ecam.model.asset.AssetEvent;
import com.codex.ecam.model.asset.AssetEventTypeAsset;
import com.codex.ecam.model.asset.AssetFile;
import com.codex.ecam.model.asset.AssetMeterReading;
import com.codex.ecam.model.asset.AssetMeterReadingFormulaValue;
import com.codex.ecam.model.asset.AssetMeterReadingFormulaVariable;
import com.codex.ecam.model.asset.AssetMeterReadingValue;
import com.codex.ecam.model.asset.AssetUser;
import com.codex.ecam.model.inventory.bom.BOMGroup;
import com.codex.ecam.model.inventory.bom.BOMGroupPart;
import com.codex.ecam.model.inventory.receiptOrder.ReceiptOrder;
import com.codex.ecam.model.inventory.receiptOrder.ReceiptOrderItem;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.asset.AssetResult;
import com.codex.ecam.service.asset.api.AssetService;
import com.codex.ecam.service.asset.api.WarrantyService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.FileDownloadUtil;
import com.codex.ecam.util.FileUploadUtil;
import com.codex.ecam.util.search.asset.AssetSearchPropertyMapper;

@Service
public class AssetServiceImpl implements AssetService {

	private final String ASSET_DEFAULT_IMAGE = "/resources/images/no_image.png";
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
	
	@Autowired
	private ReceiptOrderDao receiptOrderDao;
	
	@Autowired
	private ReceiptOrderItemDao receiptOrderItemDao;
	
	@Autowired
	private CurrencyDao currencyDao;
	
	@Autowired
	private CountryDao countryDao;
	
	@Autowired
	private AssetBrandDao assetBrandDao;
	
	@Autowired
	private AssetModelDao assetModelDao;
	
	@Autowired
	private SupplierDao supplierDao;

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

		DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specification);
		DataTablesOutput<AssetDTO> out = AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	@Override
	public DataTablesOutput<AssetDTO> findCustomerAssets(FocusDataTablesInput input) throws Exception {
		Specification<Asset> specification = (root, query, cb) -> cb.notEqual(
				root.get("assetCategory").get("assetCategoryType"), AssetCategoryType.LOCATIONS_OR_FACILITIES);

		AssetSearchPropertyMapper.getInstance().generateDataTableInput(input);
		DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specification);
		DataTablesOutput<AssetDTO> out = AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	@Override
	public AssetDTO findById(Integer id) throws Exception {
		Asset domain = findEntityById(id);
		AssetDTO assetDTO = createAssetDto(domain);
		return assetDTO;
	}

	private AssetDTO createAssetDto(Asset domain) throws Exception {
		AssetDTO assetDTO = AssetMapper.getInstance().domainToDto(domain);
		getAssetUser(domain, assetDTO);
		setPurchasingDetails(domain, assetDTO);
		return assetDTO;
	}

	private void setPurchasingDetails(Asset asset, AssetDTO assetDTO) {
		if (asset.getAssetCategory().getAssetCategoryType().equals(AssetCategoryType.EQUIPMENTS_OR_MACHINES)
				|| asset.getAssetCategory().getAssetCategoryType().equals(AssetCategoryType.TOOLS)) {
			List<ReceiptOrderItem> items = receiptOrderItemDao.findByAsset(asset);
			if ((items != null) && (items.size() > 0)) {
				ReceiptOrderItem item = items.get(0);
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

					if ((item.getReceiptOrder() != null) && (item.getReceiptOrder().getSupplier() != null)) {
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
		for (AssetUser assetUser : domain.getAssetUsers()) {
			AssetUserDTO assetUserDTO = AssetUserMapper.getInstance().domainToDto(assetUser);
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
				Specification<Asset> specification = getSystemUserLocationAsset(type, businessId);
				assets = assetDao.findAll(specification);
			}
			return AssetMapper.getInstance().domainToDTOList(assets);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	@Override
	public DataTablesOutput<AssetDTO> findAssetByCategoryTypeBusiness(FocusDataTablesInput input, Integer businessId,
			AssetCategoryType type) {
		DataTablesOutput<Asset> assets;

		try {
			Specification<Asset> specification = (root, query, cb) -> {
				return cb.and(cb.equal(root.get("assetCategory").get("assetCategoryType"), type),
						cb.equal(root.get("business").get("id"), businessId));
			};
			assets = assetDao.findAll(input, specification);
			return AssetMapper.getInstance().domainToDTODataTablesOutput(assets);
		} catch (Exception e) {
			e.printStackTrace();
			return new DataTablesOutput<AssetDTO>();
		}
	}

	@Override
	public List<AssetDTO> findByExcludingAssetList(AssetCategoryType type, List<Integer> assetList) {
		try {
			List<Asset> list = assetDao.findByExcludingAssetList(type, assetList);
			return AssetMapper.getInstance().domainToDTOList(list);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<AssetDTO>();
		}
	}

	@Override
	public AssetResult delete(Integer id) {
		AssetResult result = new AssetResult(null, null);
		try {
			assetDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Asset Deleted Successfully.");
		} catch (DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Asset Already Used. Cannot delete.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	public AssetResult save(AssetDTO dto, MultipartFile image) throws Exception {
		AssetResult result = createAssetResult(dto);
		try {
			saveOrUpdate(result, image);
			result.addToMessageList(getMessageByAction(dto));
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Asset Already updated. Please Reload Asset.");
		} catch (Exception ex) {
			ex.printStackTrace();
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
		addReceiptOrder(result);
		result.updateDtoIdAndVersion();
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
		setAssetImage(result, image);
		warrantyService.setWarranties(result.getDtoEntity().getWarranties(), result.getDomainEntity());
	}

	private void setAssetImage(AssetResult result, MultipartFile image) throws Exception {
		if (image != null) {
			String uploadFolder = environment.getProperty("upload.asset.file.folder");
			String uploadLocation = environment.getProperty("upload.location");
			try {
				String fileLocation = FileUploadUtil.createFile(image, result.getDtoEntity().getCode(),
						result.getDtoEntity().getCode(), uploadFolder, uploadLocation);
				result.getDomainEntity().setImageLocation(fileLocation);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private AssetResult createAssetResult(AssetDTO dto) {
		AssetResult result;
		if ((dto.getId() != null) && (dto.getId() > 0)) {
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
		if ((result.getDtoEntity().getCustomerId() != null) && (result.getDtoEntity().getCustomerId() > 0)) {
			if (isCustomerChanged(result)) {
				result.getDomainEntity().setCustomer(businessDao.findById(result.getDtoEntity().getCustomerId()));
				setAssetCustomers(result);
			}
		} else {
			result.getDomainEntity().setCustomer(null);
		}
	}

	private boolean isCustomerChanged(AssetResult result) {
		if ((result.getDomainEntity().getCustomer() == null)
				|| (result.getDomainEntity().getCustomer().getId() != result.getDtoEntity().getCustomerId())) {
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

			AssetBusiness assetCustomer = new AssetBusiness();
			assetCustomer.setAsset(result.getDomainEntity());
			assetCustomer.setBusiness(result.getDomainEntity().getCustomer());
			assetCustomer.setIsDeleted(false);
			assetCustomers.add(assetCustomer);
		}
	}

	private void setAssetEvents(AssetResult result) throws Exception {
		Set<AssetEventTypeAsset> assetEventTypeAssets = new HashSet<>();

		if ((result.getDtoEntity().getAssetEventTypeAssets() != null)
				&& (result.getDtoEntity().getAssetEventTypeAssets().size() > 0)) {

			for (AssetEventTypeAssetDTO assetEventTypeAssetDto : result.getDtoEntity().getAssetEventTypeAssets()) {

				AssetEventTypeAsset assetEventTypeAsset = getCurrentAssetEvent(
						result.getDomainEntity().getAssetEventTypeAssets(), assetEventTypeAssetDto);
				List<AssetEventDTO> assetEventDtos = getAssetEventByEventType(result, assetEventTypeAssetDto);
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

		if ((currentAssetEventTypeAssets != null) && (currentAssetEventTypeAssets.size() > 0)) {
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
		List<AssetEventDTO> assetEventDtos = new ArrayList<>();

		for (AssetEventDTO assetEventDto : result.getDtoEntity().getAssetEvents()) {
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
		for (AssetEventDTO assetEventDto : assetEventDtos) {

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

	private void addReceiptOrder(AssetResult result) {
		AssetCategoryType type = result.getDomainEntity().getAssetCategory().getAssetCategoryType();
		if (type.equals(AssetCategoryType.EQUIPMENTS_OR_MACHINES) || type.equals(AssetCategoryType.TOOLS)) {
			ReceiptOrder receiptOrder = createReceiptOrder(result.getDtoEntity().getAssetPurchasingDetail(),
					result.getDomainEntity());
			receiptOrderDao.save(receiptOrder);
		}

	}

	private ReceiptOrder createReceiptOrder(AssetPurchasingDTO assetPurchasingDetail, Asset asset) {
		ReceiptOrder ro;

		if ((assetPurchasingDetail.getReceiptOrderId() != null) && (assetPurchasingDetail.getReceiptOrderId() > 0)) {
			ro = receiptOrderDao.findOne(assetPurchasingDetail.getReceiptOrderId());
		} else {
			ro = new ReceiptOrder();
			ro.setCode("RO_" + asset.getCode());
		}

		ro.setReceiptOrderStatus(ReceiptOrderStatus.RECEIVED);
		addReceiptOrderItem(ro, assetPurchasingDetail, asset);

		if ((assetPurchasingDetail.getPurchasedCurrencyId() != null)
				&& (assetPurchasingDetail.getPurchasedCurrencyId() > 0)) {
			ro.setCurrency(currencyDao.findById(assetPurchasingDetail.getPurchasedCurrencyId()));
		}

		if ((assetPurchasingDetail.getPurchasedSupplierId() != null)
				&& (assetPurchasingDetail.getPurchasedSupplierId() > 0)) {
			ro.setSupplier(supplierDao.findOne(assetPurchasingDetail.getPurchasedSupplierId()));
		}

		if (assetPurchasingDetail.getOrderedDate() != null) {
			ro.setDateOrdered(assetPurchasingDetail.getOrderedDate());
		}

		if (assetPurchasingDetail.getReceivedDate() != null) {
			ro.setDateReceived(assetPurchasingDetail.getReceivedDate());
		}

		ro.setIsDeleted(false);
		ro.setVersion(assetPurchasingDetail.getOrderVersion());

		return ro;
	}

	private void addReceiptOrderItem(ReceiptOrder ro, AssetPurchasingDTO assetPurchasingDetail, Asset asset) {
		Set<ReceiptOrderItem> items = new HashSet<>();
		ReceiptOrderItem item = new ReceiptOrderItem();
		if ((ro.getReceiptOrderItems() != null) && (ro.getReceiptOrderItems().size() > 0)) {
			// item = ro.getReceiptOrderItems().get(0);
			Optional<ReceiptOrderItem> first = ro.getReceiptOrderItems().stream().findFirst();
			if (first.isPresent()) {
				item = first.get();
			}

		} else {
			item = new ReceiptOrderItem();
		}
		item.setAsset(asset);
		item.setReceiptOrder(ro);
		if ((assetPurchasingDetail.getPurchasedPrice() != null) && (assetPurchasingDetail.getPurchasedPrice() > 0)) {
			item.setTotalPrice(BigDecimal.valueOf(assetPurchasingDetail.getPurchasedPrice()));
			item.setUnitPrice(BigDecimal.valueOf(assetPurchasingDetail.getPurchasedPrice()));
			item.setQuantityReceived(BigDecimal.ONE);
		}

		if (assetPurchasingDetail.getExpiryDate() != null) {
			item.setDateExpiryOfInventoryItems(assetPurchasingDetail.getExpiryDate());
		}
		item.setIsDeleted(Boolean.FALSE);
		item.setVersion(assetPurchasingDetail.getItemVersion());
		items.add(item);
		ro.setReceiptOrderItems(items);
	}

	private void addAssetToBOMGroup(Asset asset) throws Exception {
		List<BOMGroup> assetBOMGroups = bomGroupDao.findGroupsByAssetId(asset.getId());
		for (AssetConsumingReference consumeRef : asset.getPartConsumingReferences()) {
			if (consumeRef.getBomGroupAsset() != null) {
				Optional<BOMGroup> group = assetBOMGroups.stream()
						.filter((x) -> x.getId().equals(consumeRef.getBomGroupAsset().getBomGroup().getId())).findAny();
				if (!group.isPresent()) {
					createBOMGroupPart(consumeRef.getBomGroupAsset().getBomGroup(), asset);
				}
			}
		}
	}

	private void createBOMGroupPart(BOMGroup bomGroup, Asset asset) {
		BOMGroupPart groupPart = new BOMGroupPart();
		groupPart.setBomGroup(bomGroup);
		groupPart.setPart(asset);
		groupPart.setIsDeleted(false);

		bomGroupPartDao.save(groupPart);
	}

	private void setPartConsumeReferences(AssetResult result) throws Exception {

		Set<AssetConsumingReference> partConsumeRefs = new HashSet<>();

		if ((result.getDtoEntity().getPartConsumeRefs() != null)
				&& (result.getDtoEntity().getPartConsumeRefs().size() > 0)) {

			for (AssetConsumingReferenceDTO partConsumeRefDTO : result.getDtoEntity().getPartConsumeRefs()) {
				AssetConsumingReference partConsumeRef = getCurrentPartConsumeRef(
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

		if ((currentPartConsRefs != null) && (currentPartConsRefs.size() > 0)) {
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
		if ((dto.getBomGroupPartId() != null) && (dto.getBomGroupPartId() > 0)) {
			domain.setBomGroupAsset(bomGroupPartDao.findOne(dto.getBomGroupPartId()));
		}
		domain.setPart(assetDao.findOne(dto.getPartId()));
		domain.setAsset(asset);
	}

	private void setParentAsset(AssetResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getParentAssetId() != null)) {
			result.getDomainEntity().setParentAsset(findEntityById(result.getDtoEntity().getParentAssetId()));
		}
	}

	private void setBrand(AssetResult result) {
		if ((result.getDtoEntity().getBrand() != null) && (result.getDtoEntity().getBrand() > 0)) {
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
		if ((result.getDtoEntity().getModel() != null) && (result.getDtoEntity().getModel() > 0)) {
			result.getDomainEntity().setModel(assetModelDao.findById(result.getDtoEntity().getModel()));
		}
	}

	private void setAssetCategory(AssetResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getAssetCategoryId() != null)) {
			result.getDomainEntity()
					.setAssetCategory(assetCategoryDao.findById(result.getDtoEntity().getAssetCategoryId()));
		}
	}

	private void setAssetCountry(AssetResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getCountryId() != null)) {
			result.getDomainEntity().setCountry(countryDao.findById(result.getDtoEntity().getCountryId()));
		}
	}

	private void addUsersToAsset(AssetResult result) {

		Set<AssetUser> assetUsers = new HashSet<AssetUser>();
		for (AssetUserDTO assetUserDTO : result.getDtoEntity().getAssetUserDTOs()) {
			AssetUser assetUser = getCurrentAssetUser(result.getDomainEntity().getAssetUsers(), assetUserDTO);
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
		if ((currentAssetUsers != null) && (currentAssetUsers.size() > 0)) {
			assetUser = currentAssetUsers.stream().filter((x) -> x.getId().equals(assetUserDTO.getId())).findAny()
					.orElseGet(AssetUser::new);
		} else {
			assetUser = new AssetUser();
		}

		return assetUser;
	}

	private void setBusiness(AssetResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getBusinessId() != null)) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}

	private void setSite(AssetResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getSiteId() != null)) {
			result.getDomainEntity().setSite(assetDao.findOne(result.getDtoEntity().getSiteId()));
		}
	}

	private void setMeterReadings(AssetResult result) throws Exception {

		Set<AssetMeterReading> assetMeterReadings = new HashSet<AssetMeterReading>();
		if ((result.getDtoEntity().getAssetMeterReadings() != null)
				&& (result.getDtoEntity().getAssetMeterReadings().size() > 0)) {

			for (AssetMeterReadingDTO assetsMeterReadingDTO : result.getDtoEntity().getAssetMeterReadings()) {
				AssetMeterReading assetMeterReading = getCurrentAssetMeterReading(
						result.getDomainEntity().getAssetMeterReadings(), assetsMeterReadingDTO);
				List<AssetMeterReadingValueDTO> assetMeterReadingValues = getMeterReadingValuesByMeterReading(result,
						assetsMeterReadingDTO);
				updateAssetMeterReading(assetMeterReading, assetsMeterReadingDTO, assetMeterReadingValues,
						result.getDomainEntity());
				updateAverageMeterReadingValue(assetMeterReading);
				assetMeterReadings.add(assetMeterReading);
				setMeterReadingConsumptionVariable(assetsMeterReadingDTO,assetMeterReading);
			}
		}

		result.getDomainEntity().setAssetMeterReadings(assetMeterReadings);
	}
	
	
	private void setMeterReadingConsumptionVariable(AssetMeterReadingDTO assetsMeterReadingDTO,AssetMeterReading assetMeterReading){
		Set<AssetMeterReadingFormulaVariable> assetMeterReadingConsumptionVariables = new HashSet<AssetMeterReadingFormulaVariable>();
		for (AssetMeterReadingConsumptionVariableDTO consumptionVariableDTO:assetsMeterReadingDTO.getConsumptionVariableDTO()) {
			AssetMeterReadingFormulaVariable assetMeterReadingConsumptionVariable=new AssetMeterReadingFormulaVariable();
			assetMeterReadingConsumptionVariable.setVariableName(consumptionVariableDTO.getVariable());
			assetMeterReadingConsumptionVariable.setAssetMeterReading(assetMeterReading);
			assetMeterReadingConsumptionVariable.setIsDeleted(Boolean.FALSE);
			setMeterReadingVariableUnit(consumptionVariableDTO,assetMeterReadingConsumptionVariable);
			assetMeterReadingConsumptionVariables.add(assetMeterReadingConsumptionVariable);
		}
		assetMeterReading.setFormulaVariables(assetMeterReadingConsumptionVariables);
	}
	
	private void setMeterReadingVariableUnit(AssetMeterReadingConsumptionVariableDTO consumptionVariableDTO,AssetMeterReadingFormulaVariable assetMeterReadingConsumptionVariable){
		if(consumptionVariableDTO!=null && consumptionVariableDTO.getMeteReadingUnitId()!=null){
			assetMeterReadingConsumptionVariable.setMeterReadingUnit(meterReadingUnitDao.findOne(consumptionVariableDTO.getMeteReadingUnitId()));
		}
	}
	
	@Override
	public synchronized void updateAverageMeterReadingValue(AssetMeterReading assetMeterReading) {

		Double avgval = 0.00;

		if ((assetMeterReading.getAssetMeterReadingValues() != null)
				&& (assetMeterReading.getAssetMeterReadingValues().size() > 1)) {
			List<AssetMeterReadingValue> list = assetMeterReading.getAssetMeterReadingValues().stream()
					.sorted(Comparator.comparing(AssetMeterReadingValue::getAddedDate).reversed())
					.collect(Collectors.toList());
			Long diff = list.get(0).getAddedDate().getTime() - list.get(1).getAddedDate().getTime();
			long diffDays = diff / (24 * 60 * 60 * 1000);
			if (diffDays > 0) {
				avgval = (list.get(0).getMeterReadingValue() - list.get(1).getMeterReadingValue()) / diffDays;
			}
		}

		assetMeterReading.setAvgMeterReadingValue(avgval);
	}

	private AssetMeterReading getCurrentAssetMeterReading(Set<AssetMeterReading> currentAssetMeterReadings,
			AssetMeterReadingDTO assetsMeterReadingDTO) {
		AssetMeterReading assetMeterReading;

		if ((currentAssetMeterReadings != null) && (currentAssetMeterReadings.size() > 0)) {
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
		for (AssetMeterReadingValueDTO assetMeterReadingValueDto : assetMeterReadingValueDtos) {

			meterReadingValue = new AssetMeterReadingValue();
			AssetMeterReadingValueMapper.getInstance().dtoToDomain(assetMeterReadingValueDto, meterReadingValue);

			meterReadingValue.setAssetMeterReading(domain);
			meterReadingValue.setFunctionString(assetMeterReadingValueDto.getMeterReadingConsumptionFunction());

			if (dto.getMeterReadingCurrentValueIndex()
					.equals(assetMeterReadingValueDto.getAssetMeterReadingValueIndex())) {
				domain.setCurrentAssetMeterReadingValue(meterReadingValue);
			}
			updateAssetMeterReadingConsumption(assetMeterReadingValueDto,meterReadingValue);
			assetMeterReadingValues.add(meterReadingValue);
		}
	}

	
	private void updateAssetMeterReadingConsumption(AssetMeterReadingValueDTO meterReadingValueDTO,AssetMeterReadingValue meterReadingValue) throws Exception {
		List<AssetMeterReadingFormulaValue> valueConsumptions = new ArrayList<>() ;
	for(AssetMeterReadingConsumptionValueDTO assetMeterReadingValueConsumptionDTO:meterReadingValueDTO.getValueConsumptionDTO()){
			
		AssetMeterReadingFormulaValue consumption= new AssetMeterReadingFormulaValue();
			consumption.setMeterReadingIndex(meterReadingValueDTO.getAssetMeterReadingValueIndex());
		//	consumption.setVariable(assetMeterReadingValueConsumptionDTO.getVariable());
			consumption.setIsDeleted(Boolean.FALSE);
			consumption.setValue(assetMeterReadingValueConsumptionDTO.getValue());
			consumption.setAssetMeterReadingValue(meterReadingValue);
			valueConsumptions.add(consumption);
		}
	meterReadingValue.setAssetMeterReadingFormulaValues(valueConsumptions);
	}
	
	private List<AssetMeterReadingValueDTO> getMeterReadingValuesByMeterReading(AssetResult result,
			AssetMeterReadingDTO assetMeterReadingDTO) {
		List<AssetMeterReadingValueDTO> assetMeterReadingValueDtos = new ArrayList<>();
		for (AssetMeterReadingValueDTO meterReadingValueDto : result.getDtoEntity().getAssetMeterReadingValues()) {
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
			List<Asset> list = assetDao.findAll(specification);
			return AssetMapper.getInstance().domainToDTOList(list);

		} catch (Exception e) {
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
			List<Asset> list = assetDao.findAll(specification);
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
			List<Asset> list = assetDao.findAll(specification);
			dtoList = AssetMapper.getInstance().domainToDTOList(list);
		}

		return dtoList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveAll(List<AssetDTO> entities) throws Exception {
		for (AssetDTO dto : entities) {
			save(dto, null);
		}
	}

	@Override
	public List<AssetDTO> findAll() {

		List<Asset> assets = (List<Asset>) assetDao.findAll();
		try {
			return AssetMapper.getInstance().domainToDTOList(assets);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<AssetMeterReadingDTO> findByMeterReadingByAsset(String name) throws Exception {
		if (name != null) {
			Asset asset = assetDao.findAssetMetereReadingsByAssetName(name);
			Integer id = asset.getId();
			List<AssetMeterReadingDTO> dtos = AssetMeterReadingMapper.getInstance()
					.domainToDTOList(assetMeterReadingDao.findAssetMeterReadingByAssetId(id));
			return dtos;
		}
		return null;
	}

	@Override
	public List<AssetMeterReadingDTO> findAssetMeterReadingByAssetId(Integer id) throws Exception {
		if (id != null) {
			List<AssetMeterReadingDTO> dtos = AssetMeterReadingMapper.getInstance()
					.domainToDTOList(assetMeterReadingDao.findAssetMeterReadingByAssetId(id));
			return dtos;
		}
		return new ArrayList<>();
	}

	@Override
	public DataTablesOutput<AssetMeterReadingDTO> findMeterReadingHistory(Integer assetId) {

		DataTablesOutput<AssetMeterReadingDTO> dataTablesOutput = new DataTablesOutput<>();

		List<AssetMeterReadingValue> list = assetMeterReadingDao.findLAllAssetMetereReadingsByAsset(assetId);
		List<AssetMeterReadingDTO> assetMeterReadingDTOs = new ArrayList<>();
		for (AssetMeterReadingValue assetMeterReadingValue : list) {
			assetMeterReadingDTOs.add(addAssetMeterReading(assetMeterReadingValue));
		}
		dataTablesOutput.setData(assetMeterReadingDTOs);

		return dataTablesOutput;
	}

	private AssetMeterReadingDTO addAssetMeterReading(AssetMeterReadingValue assetMeterReadingValue) {
		AssetMeterReadingDTO assetMeterReadingDTO = new AssetMeterReadingDTO();
		assetMeterReadingDTO.setMeterReadingName(assetMeterReadingValue.getAssetMeterReading().getMeterReadingName());
		assetMeterReadingDTO.setMeterReadingCurrentValue(assetMeterReadingValue.getMeterReadingValue());
		assetMeterReadingDTO.setMeterReadingViewName(
				assetMeterReadingValue.getAssetMeterReading().getMeterReadingUnit().getName() + " ("
						+ assetMeterReadingValue.getAssetMeterReading().getMeterReadingUnit().getSymbol() + " )");
		return assetMeterReadingDTO;
	}

	@Override
	public List<AssetMeterReadingDTO> findAllAssetMeterReading() throws Exception {
		Iterable<AssetMeterReading> assetMeterReadings = assetMeterReadingDao.findAll();
		List<AssetMeterReadingDTO> assetMeterReadingDTOs = AssetMeterReadingMapper.getInstance()
				.domainToDTOList(assetMeterReadings);
		return assetMeterReadingDTOs;
	}

	@Override
	public List<AssetMeterReadingDTO> findByMeterReadingByAssetId(Integer id) throws Exception {
		List<AssetMeterReadingDTO> dtos = AssetMeterReadingMapper.getInstance()
				.domainToDTOList(assetMeterReadingDao.findAssetMeterReadingByAssetId(id));
		for (AssetMeterReadingDTO assetMeterReadingDTO : dtos) {
			MeterReadingUnit meterReadingUnit = meterReadingUnitDao
					.findById(assetMeterReadingDTO.getMeterReadingUnitId());
			assetMeterReadingDTO.setMeterReadingViewName(
					assetMeterReadingDTO.getMeterReadingName() + " (" + meterReadingUnit.getSymbol() + ")");
		}
		return dtos;
	}

	private Specification<Asset> getAdminUserAssetSpecification() {

		Specification<Asset> specification = (root, query, cb) -> cb
				.notEqual(root.get("assetCategory").get("assetCategoryType"), AssetCategoryType.PARTS_AND_SUPPLIES);
		return specification;
	}

	private Specification<Asset> getSystemUserAssetSpecification(Integer businessId) {

		Specification<Asset> specification = (root, query, cb) -> {
			return cb.and(
					cb.notEqual(root.get("assetCategory").get("assetCategoryType"),
							AssetCategoryType.PARTS_AND_SUPPLIES),
					cb.equal(root.get("business").get("id"), businessId));
		};

		return specification;
	}

	private Specification<Asset> getGeneralUserAssetSpecification(Asset site) {

		Specification<Asset> specification = (root, query, cb) -> cb.and(
				cb.notEqual(root.get("assetCategory").get("assetCategoryType"), AssetCategoryType.PARTS_AND_SUPPLIES),
				cb.equal(root.get("site"), site));

		return specification;
	}

	private Specification<Asset> getSystemUserLocationAsset(AssetCategoryType assetCategoryType, Integer businessId) {

		Specification<Asset> specification = (root, query, cb) -> {
			return cb.and(cb.equal(root.get("assetCategory").get("assetCategoryType"), assetCategoryType),
					cb.equal(root.get("business").get("id"), businessId));
		};

		return specification;
	}

	private Specification<Asset> getAdminUserLocationAsset(AssetCategoryType assetCategoryType) {

		Specification<Asset> specification = (root, query, cb) -> cb
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

		DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specification);
		DataTablesOutput<AssetDTO> out = AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	@Override
	public List<AssetDTO> findSiteByBusinessId(Integer businessId, AssetCategoryType assetCategoryType) {
		try {
			List<AssetDTO> dtoList = null;

			Specification<Asset> specification = (root, query, cb) -> {
				return cb.and(cb.equal(root.get("assetCategory").get("assetCategoryType"), assetCategoryType),
						cb.equal(root.get("business").get("id"), businessId));
			};

			if (specification != null) {
				List<Asset> list = assetDao.findAll(specification);
				dtoList = AssetMapper.getInstance().domainToDTOList(list);
			}

			return dtoList;

		} catch (Exception e) {
			e.printStackTrace();
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
				Predicate assetCategory = cb.equal(root.get("assetCategory").get("assetCategoryType"), type);
				Predicate assetBusiness = cb.equal(root.get("business").get("id"),
						AuthenticationUtil.getLoginUserBusiness().getId());
				return cb.and(assetCategory, assetBusiness);
			};
		} else {
			specification = (root, query, cb) -> {
				Predicate assetCategory = cb.equal(root.get("assetCategory").get("assetCategoryType"), type);
				Predicate assetSite = cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite());
				return cb.and(assetCategory, assetSite);
			};
		}
		AssetSearchPropertyMapper.getInstance().generateDataTableInput(input);
		DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specification);
		DataTablesOutput<AssetDTO> out = AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	@Override
	public DataTablesOutput<AssetDTO> findSiteByBusiness(FocusDataTablesInput input, Integer bizId) throws Exception {
		try {
			AssetSearchPropertyMapper.getInstance().generateDataTableInput(input);
			DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specSiteListByBusiness(bizId));
			return AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (Exception e) {
			e.printStackTrace();
			return new DataTablesOutput<>();
		}
	}

	private Specification<Asset> specSiteListByBusiness(Integer bizId) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
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
			DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specByBusiness(bizId));
			return AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (Exception e) {
			e.printStackTrace();
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
			DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specByBusiness(bizId));
			return AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (Exception e) {
			e.printStackTrace();
			return new DataTablesOutput<>();
		}
	}

	private Specification<Asset> specByBusiness(Integer bizId) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("business").get("id"), bizId));
			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}

	@Override
	public DataTablesOutput<AssetDTO> findAssetByAssetTypeAndBusiness(FocusDataTablesInput input,
			AssetCategoryType categoryType, Integer bizId) throws Exception {
		try {
			AssetSearchPropertyMapper.getInstance().generateDataTableInput(input);
			DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specAssetByBusiness(bizId, categoryType));
			return AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (Exception e) {
			e.printStackTrace();
			return new DataTablesOutput<>();
		}
	}

	private Specification<Asset> specAssetByBusiness(Integer bizId, AssetCategoryType categoryType) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
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
			DataTablesOutput<Asset> domainOut = assetDao.findAll(input,
					specAssetNotAssetTypeByAndBusiness(bizId, categoryType));
			return AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (Exception e) {
			e.printStackTrace();
			return new DataTablesOutput<>();
		}
	}

	private Specification<Asset> specAssetNotAssetTypeByAndBusiness(Integer bizId, AssetCategoryType categoryType) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("business").get("id"), bizId));
			predicates.add(cb.notEqual(root.get("assetCategory").get("assetCategoryType"), categoryType));
			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}

	@Override
	public DataTablesOutput<AssetDTO> findAllFacilitiesByLevel(FocusDataTablesInput input) throws Exception {

		Specification<Asset> specification;
		AssetSearchPropertyMapper.getInstance().generateDataTableInput(input);
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			specification = (root, query, cb) -> cb.equal(root.get("assetCategory").get("assetCategoryType"),
					AssetCategoryType.LOCATIONS_OR_FACILITIES);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			specification = (root, query, cb) -> {
				return cb.and(
						cb.equal(root.get("assetCategory").get("assetCategoryType"),
								AssetCategoryType.LOCATIONS_OR_FACILITIES),
						cb.equal(root.get("business").get("id"), AuthenticationUtil.getLoginUserBusiness().getId()));
			};
		} else {
			specification = (root, query, cb) -> cb.and(
					cb.equal(root.get("assetCategory").get("assetCategoryType"),
							AssetCategoryType.LOCATIONS_OR_FACILITIES),
					cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite()));
		}

		DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specification);
		DataTablesOutput<AssetDTO> out = AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);

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

		DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specification);
		DataTablesOutput<AssetDTO> out = AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	@Override
	public String assetFileUpload(MultipartFile file, String refId) throws Exception {
		String uploadFolder = environment.getProperty("upload.asset.file.folder");
		String uploadLocation = environment.getProperty("upload.location");
		try {
			return FileUploadUtil.createFile(file, refId, uploadFolder, uploadLocation);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void assetFileDownload(Integer refId, HttpServletResponse response) throws Exception {
		String uploadLocation = new File(environment.getProperty("upload.location")).getPath();
		if (refId != null) {
			AssetFile file = assetDao.findByFileId(refId);
			String externalFilePath = uploadLocation + file.getFileLocation();
			FileDownloadUtil.flushFile(externalFilePath, file.getFileType(), response);
		}
	}

	private void setAssetFiles(AssetResult result) throws Exception {
		Set<AssetFile> assetFiles = new HashSet<>();

		if ((result.getDtoEntity().getAssetFileDTOs() != null)
				&& (result.getDtoEntity().getAssetFileDTOs().size() > 0)) {

			Set<AssetFile> currentAssetFiles = result.getDomainEntity().getAssetFiles();

			for (AssetFileDTO assetFileDTO : result.getDtoEntity().getAssetFileDTOs()) {
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
			String imagePath = assetDao.getAssetImageLocation(id);
			String uploadLocation = new File(environment.getProperty("upload.location")).getPath();
			if (imagePath != null) {
				return FileDownloadUtil.getByteInputStream(uploadLocation + imagePath);
			}
		}

		return FileDownloadUtil
				.getByteInputStream(request.getServletContext().getRealPath("").concat(ASSET_DEFAULT_IMAGE));
	}

	@Override
	public DataTablesOutput<AssetDTO> getMachineToolsByBusiness(FocusDataTablesInput input, Integer bizId)
			throws Exception {

		AssetSearchPropertyMapper.getInstance().generateDataTableInput(input);
		Specification<Asset> specification = (root, query, cb) -> cb.and(
				cb.notEqual(root.get("assetCategory").get("assetCategoryType"),
						AssetCategoryType.LOCATIONS_OR_FACILITIES),
				cb.notEqual(root.get("assetCategory").get("assetCategoryType"), AssetCategoryType.PARTS_AND_SUPPLIES),
				cb.equal(root.get("business").get("id"), bizId));

		DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specification);
		DataTablesOutput<AssetDTO> out = AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	@Override
	public List<AssetDTO> findAssetsByCategoryBusiness(Integer bizId, Integer categoryId) throws Exception {
		List<Asset> assets = new ArrayList<>();
		try {
			Specification<Asset> specification = (root, query, cb) -> {
				return cb.and(cb.equal(root.get("assetCategory").get("id"), categoryId),
						cb.equal(root.get("business").get("id"), bizId));
			};
			assets = assetDao.findAll(specification);
			return AssetMapper.getInstance().domainToDTOList(assets);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	@Override
	public DataTablesOutput<AssetDTO> findPartsByBusiness(FocusDataTablesInput input, Integer bizId) throws Exception {

		AssetSearchPropertyMapper.getInstance().generateDataTableInput(input);
		Specification<Asset> specification = (root, query, cb) -> cb.and(
				cb.equal(root.get("assetCategory").get("assetCategoryType"), AssetCategoryType.PARTS_AND_SUPPLIES),
				cb.equal(root.get("business").get("id"), bizId));

		DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specification);
		DataTablesOutput<AssetDTO> out = AssetMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

}
