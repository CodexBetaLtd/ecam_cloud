package com.codex.ecam.service.inventory.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.constants.PurchaseOrderStatus;
import com.codex.ecam.constants.inventory.PartType;
import com.codex.ecam.constants.inventory.PartUsageType;
import com.codex.ecam.dao.admin.AssetBrandDao;
import com.codex.ecam.dao.admin.AssetModelDao;
import com.codex.ecam.dao.admin.UserDao;
import com.codex.ecam.dao.asset.AssetCategoryDao;
import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dao.inventory.BOMGroupPartDao;
import com.codex.ecam.dao.inventory.PurchaseOrderItemDao;
import com.codex.ecam.dto.asset.AssetUserDTO;
import com.codex.ecam.dto.biz.part.PartDTO;
import com.codex.ecam.dto.biz.part.PartNotificationDTO;
import com.codex.ecam.dto.inventory.AssetConsumingReferenceDTO;
import com.codex.ecam.dto.inventory.stock.StockDTO;
import com.codex.ecam.dto.inventory.stock.StockNotificationDTO;
import com.codex.ecam.mappers.inventory.AssetConsumingReferenceMapper;
import com.codex.ecam.mappers.inventory.part.PartMapper;
import com.codex.ecam.mappers.inventory.part.PartNotificationMapper;
import com.codex.ecam.mappers.inventory.stock.StockMapper;
import com.codex.ecam.mappers.inventory.stock.StockNotificationMapper;
import com.codex.ecam.mappers.purchasing.PurchaseOrderItemMapper;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.asset.AssetConsumingReference;
import com.codex.ecam.model.asset.AssetUser;
import com.codex.ecam.model.biz.part.PartNotification;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderItem;
import com.codex.ecam.model.inventory.stock.Stock;
import com.codex.ecam.model.inventory.stock.StockNotification;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.inventory.PartResult;
import com.codex.ecam.service.asset.api.WarrantyService;
import com.codex.ecam.service.inventory.api.PartService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.FileDownloadUtil;
import com.codex.ecam.util.FileUploadUtil;
import com.codex.ecam.util.aws.AmazonS3ObjectUtil;
import com.codex.ecam.util.search.asset.AssetSearchPropertyMapper;
import com.codex.ecam.util.search.inventory.PartSearchPropertyMapper;

@Service
public class PartServiceImpl implements PartService {

	private final static Logger logger = LoggerFactory.getLogger(PartServiceImpl.class);

	private final String PART_DEFAULT_IMAGE = "/resources/images/no_image.png";

	@Autowired
	private AssetDao partDao;

	@Autowired
	private BusinessDao businessDao;

	@Autowired
	private AssetCategoryDao assetCategoryDao;

	@Autowired
	private AssetDao assetDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private WarrantyService warrantyService;

	@Autowired
	private BOMGroupPartDao bomGroupPartDao;

	@Autowired
	private PurchaseOrderItemDao purchaseOrderItemDao;

	@Autowired
	private AssetBrandDao assetBrandDao;

	@Autowired
	private AssetModelDao assetModelDao;

	@Autowired
	private Environment environment;

	@Autowired
	private AmazonS3ObjectUtil amazonS3ObjectUtil;

	@Override
	public PartDTO findById(Integer id) {
		Asset part = partDao.findOne(id);
		try {
			return PartMapper.getInstance().domainToDto(part);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public PartResult findByIdWithOpenPOs(Integer id) {
		PartResult result = new PartResult(null, null);
		try {
			result.setDtoEntity(withOpenPOs(id));
			result.setResultStatusSuccess();
			result.addToMessageList(
					"Part ".concat(result.getDtoEntity().getCode()).concat(" ").concat("Found Successfully."));
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			result.setResultStatusError();
			result.addToMessageList("Error Occurred! Part Not Found.");
		}
		return result;
	}

	private PartDTO withOpenPOs(Integer id) throws Exception {
		PartDTO partDTO = findById(id);
		List<PurchaseOrderItem> orderItems = purchaseOrderItemDao.findOpenPOItemListByAsset(partDTO.getId(),
				PurchaseOrderStatus.DRAFT.getId());
		partDTO.setOpenPOs(PurchaseOrderItemMapper.getInstance().domainToDTOList(orderItems));

		return partDTO;
	}

	@Override
	public PartResult delete(Integer id) {
		PartResult result = new PartResult(null, null);
		try {
			partDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Part Deleted Successfully.");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToMessageList("Error Occurred! Part Deleted Unsuccessful.".concat(ex.getMessage()));
		}
		return result;
	}

	@Override
	public PartResult save(PartDTO dto, MultipartFile image) throws Exception {
		PartResult result = createPartResult(dto);
		try {
			saveOrUpdate(result, image);
			result.addToMessageList(getMessageByAction(dto));
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Part Already updated. Please Reload Part.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	private PartResult createPartResult(PartDTO dto) {
		PartResult result;
		if ((dto.getId() != null) && (dto.getId() > 0)) {
			result = new PartResult(partDao.findOne(dto.getId()), dto);
		} else {
			result = new PartResult(new Asset(), dto);
		}

		return result;
	}

	private String getMessageByAction(PartDTO dto) {
		if (dto.getId() == null) {
			return "Part Added Successfully.";
		} else {
			return "Part Updated Successfully.";
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(PartResult result, MultipartFile image) throws Exception {
		try {
			PartMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
			saveUpdatePartData(result, image);
			partDao.save(result.getDomainEntity());
			result.setDtoEntity(findById(result.getDomainEntity().getId()));
		} catch (ObjectOptimisticLockingFailureException ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			result.setResultStatusError();
			result.addToErrorList("Part Already updated. Please Reload Asset.");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
	}

	private void saveUpdatePartData(PartResult result, MultipartFile image) throws Exception {
		setPartCategory(result);
		setBusiness(result);
		addUsersToPart(result);
		setAssetConsumeReferences(result);
		setPartStock(result);
		setWarranty(result);
		setBrand(result);
		setModel(result);
		setPartNotification(result);
		setPartImage(result, image);
		setPartcode(result);
	}

	private void setPartcode(PartResult result) {
		if (result.getDtoEntity().getCode() != null && result.getDtoEntity().getPartType() != null) {
			if (result.getDtoEntity().getPartType().equals(PartType.REPAIRABLE)) {
				result.getDomainEntity().setCode(result.getDtoEntity().getCode() + "-R");
			}
		}
	}

	private void setPartNotification(PartResult result) throws Exception {
		Set<PartNotification> partNotifications = new HashSet<>();
		List<PartNotificationDTO> partNotificationDTOs = result.getDtoEntity().getPartNotificationDTOs();

		if ((partNotificationDTOs != null) && (partNotificationDTOs.size() > 0)) {
			Set<PartNotification> currentPartNotifications = result.getDomainEntity().getPartNotifications();
			PartNotification partNotification = new PartNotification();

			for (PartNotificationDTO partNotificationDTO : partNotificationDTOs) {
				if ((currentPartNotifications != null) && (currentPartNotifications.size() > 0)) {
					partNotification = currentPartNotifications.stream()
							.filter((x) -> x.getId().equals(partNotificationDTO.getId())).findAny()
							.orElseGet(PartNotification::new);
				} else {
					partNotification = new PartNotification();
				}
				createNotification(result.getDomainEntity(), partNotificationDTO, partNotification);
				partNotifications.add(partNotification);
			}
		}
		result.getDomainEntity().setPartNotifications(partNotifications);
	}

	private void createNotification(Asset domainEntity, PartNotificationDTO partNotificationDTO,
			PartNotification partNotification) throws Exception {
		PartNotificationMapper.getInstance().dtoToDomain(partNotificationDTO, partNotification);
		partNotification.setPart(domainEntity);
		partNotification.setUser(
				partNotificationDTO.getUserId() != null ? userDao.findOne(partNotificationDTO.getUserId()) : null);
	}

	private void setPartImage(PartResult result, MultipartFile image) throws Exception {
		if (image != null) {
			String uploadFolder = environment.getProperty("upload.asset.file.folder");
			String uploadLocation = environment.getProperty("upload.location");
			try {
				String fileLocation = FileUploadUtil.createFile(image, result.getDtoEntity().getCode(),
						result.getDtoEntity().getCode(), uploadFolder, uploadLocation);
				result.getDomainEntity().setImageLocation(saveImageS3Bucket(result.getDtoEntity(), image));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String saveImageS3Bucket(PartDTO dto, MultipartFile image) throws IOException {

		// s3 key for storage
//		final String key = amazonS3Util.getCommonUploadKey() + amazonS3Util.getAssetImageUploadKey() + dto.getId()
//				+ File.separator + getFileName(image);
		final String key = environment.getProperty("upload.location.s3")
				+ environment.getProperty("upload.location.part.image.s3") + dto.getId() + "/" + getFileName(image);
		try {
			if (dto.getImageLocation() != null) {
				amazonS3ObjectUtil.deleteS3Object(dto.getImageLocation());
			}
			amazonS3ObjectUtil.uploadS3Object(key, image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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

	private void setBusiness(PartResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getBusinessId() != null)) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}

	private void setBrand(PartResult result) {
		if ((result.getDtoEntity().getBrandId() != null) && (result.getDtoEntity().getBrandId() > 0)) {
			result.getDomainEntity().setBrand(assetBrandDao.findOne(result.getDtoEntity().getBrandId()));
		}
	}

	private void setModel(PartResult result) {
		if ((result.getDtoEntity().getModelId() != null) && (result.getDtoEntity().getModelId() > 0)) {
			result.getDomainEntity().setModel(assetModelDao.findOne(result.getDtoEntity().getModelId()));
		}
	}

	private void setPartCategory(PartResult result) throws Exception {
		if ((result.getDtoEntity().getPartCategoryId() != null) && (result.getDtoEntity().getPartCategoryId() > 0)) {
			result.getDomainEntity()
					.setAssetCategory(assetCategoryDao.findOne(result.getDtoEntity().getPartCategoryId()));
		}
	}

	private void addUsersToPart(PartResult result) throws Exception {
		Set<AssetUser> assetUsers = new HashSet<AssetUser>();
		for (AssetUserDTO assetUserDTO : result.getDtoEntity().getAssetUserDTOs()) {
			AssetUser assetUser;
			if (assetUserDTO.getId() != null) {
				assetUser = result.getDomainEntity().getAssetUsers().stream()
						.filter((x) -> x.getId().equals(assetUserDTO.getId())).findAny().orElseGet(AssetUser::new);
			} else {
				assetUser = new AssetUser();
			}

			assetUser.setAsset(result.getDomainEntity());
			assetUser.setUser(userDao.findById(assetUserDTO.getUserId()));
			assetUser.setIsDeleted(false);
			assetUsers.add(assetUser);
		}

		result.getDomainEntity().setAssetUsers(assetUsers);
	}

	private void setAssetConsumeReferences(PartResult result) throws Exception {
		if ((result.getDtoEntity().getAssetConsumeRefs() != null)
				&& (result.getDtoEntity().getAssetConsumeRefs().size() > 0)) {
			Set<AssetConsumingReference> assetConsumeRefs = new HashSet<>();
			for (AssetConsumingReferenceDTO assetConsumeRefDTO : result.getDtoEntity().getAssetConsumeRefs()) {
				AssetConsumingReference assetConsumeRef = findAssetConsumingRefByAssetIdAndBomGroupPartId(
						assetConsumeRefDTO.getAssetId(), assetConsumeRefDTO.getBomGroupPartId(),
						result.getDomainEntity().getAssetConsumingReferences());
				if (assetConsumeRef == null) {
					assetConsumeRef = new AssetConsumingReference();
				}
				createAssetConsumeReference(assetConsumeRefDTO, result.getDomainEntity(), assetConsumeRef);
				assetConsumeRefs.add(assetConsumeRef);
			}
			result.getDomainEntity().setAssetConsumingReferences(assetConsumeRefs);
		}
	}

	private void setWarranty(PartResult result) throws Exception {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getWarranties() != null)) {
			warrantyService.setWarranties(result.getDtoEntity().getWarranties(), result.getDomainEntity());
		}
	}

	private AssetConsumingReference findAssetConsumingRefByAssetIdAndBomGroupPartId(Integer assetId,
			Integer bomGroupPartId, Set<AssetConsumingReference> assetConsumingRefs) {
		AssetConsumingReference assetConsumeRef = null;
		if ((assetConsumingRefs != null) && (assetConsumingRefs.size() > 0)) {
			for (AssetConsumingReference consumingRef : assetConsumingRefs) {
				if ((assetId != null) && (bomGroupPartId == null)) {
					if ((consumingRef.getBomGroupAsset() == null) && consumingRef.getAsset().getId().equals(assetId)) {
						assetConsumeRef = consumingRef;
						break;
					}
				}
				if ((assetId == null) && (bomGroupPartId != null)) {
					if (consumingRef.getBomGroupAsset().getId().equals(bomGroupPartId)
							&& (consumingRef.getAsset() == null)) {
						assetConsumeRef = consumingRef;
						break;
					}
				}
			}
		}
		return assetConsumeRef;
	}

	private void createAssetConsumeReference(AssetConsumingReferenceDTO dto, Asset part, AssetConsumingReference domain)
			throws Exception {
		AssetConsumingReferenceMapper.getInstance().dtoToDomain(dto, domain);
		if ((dto.getBomGroupPartId() != null) && (dto.getBomGroupPartId() > 0)) {
			domain.setBomGroupAsset(bomGroupPartDao.findOne(dto.getBomGroupPartId()));
		} else {
			domain.setAsset(assetDao.findOne(dto.getAssetId()));
		}
		domain.setPart(part);
	}

	private void setPartStock(PartResult result) throws Exception {
		Set<Stock> stocks = new HashSet<>();
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getStockDTOs() != null)) {
			for (StockDTO dto : result.getDtoEntity().getStockDTOs()) {
				Stock stock = new Stock();
				if ((dto.getId() != null) && (result.getDomainEntity().getStocks() != null)
						&& (result.getDomainEntity().getStocks().size() > 0)) {
					stock = result.getDomainEntity().getStocks().stream().filter((x) -> x.getId().equals(dto.getId()))
							.findAny().orElseGet(Stock::new);
				} else {
					stock = new Stock();
					stock.setStockNo("");
					// stock.setStockNo("SK".concat(result.getDtoEntity().getName().concat("_").concat(tempVal.toString())));
				}
				setStockPart(dto, stock, result);
				setStockNotification(dto, stock, result);
				stocks.add(stock);
			}
			result.getDomainEntity().setStocks(stocks);
		}
	}

	private void setStockNotification(StockDTO dto, Stock stock, PartResult result) throws Exception {
		Set<StockNotification> stockNotifications = new HashSet<>();

		if ((dto.getStockNotificationDTOs() != null) && (dto.getStockNotificationDTOs().size() > 0)) {

			Set<StockNotification> currentNotifications = stock.getStockNotifications();
			StockNotification stockNotification = new StockNotification();

			for (StockNotificationDTO stockNotificationDTO : dto.getStockNotificationDTOs()) {
				if ((stockNotificationDTO.getId() != null) && (currentNotifications != null)
						&& (currentNotifications.size() > 0)) {
					stockNotification = currentNotifications.stream()
							.filter((x) -> x.getId().equals(stockNotificationDTO.getId())).findAny()
							.orElseGet(StockNotification::new);
				} else {
					stockNotification = new StockNotification();
				}

				createStockNotification(stockNotificationDTO, stockNotification, stock);
				stockNotifications.add(stockNotification);
			}
		}
		stock.setStockNotifications(stockNotifications);
	}

	private void createStockNotification(StockNotificationDTO stockNotificationDTO, StockNotification stockNotification,
			Stock domainEntity) throws Exception {
		StockNotificationMapper.getInstance().dtoToDomain(stockNotificationDTO, stockNotification);
		stockNotification.setStock(domainEntity);
		stockNotification.setUser(
				stockNotificationDTO.getUserId() != null ? userDao.findOne(stockNotificationDTO.getUserId()) : null);
	}

	private void setStockPart(StockDTO dto, Stock stock, PartResult result) throws Exception {
		StockMapper.getInstance().dtoToDomain(dto, stock);
		if (dto.getWarehouseId() != null) {
			stock.setWarehouse(assetDao.findOne(dto.getWarehouseId()));
		}
		stock.setPart(result.getDomainEntity());
		if (dto.getBusinessId() != null) {
			stock.setBusiness(businessDao.findOne(dto.getBusinessId()));
		}
		if (dto.getSiteId() != null) {
			stock.setSite(assetDao.findOne(dto.getSiteId()));
		}
		stock.setDate(new Date());
	}

	@Override
	public List<PartDTO> findAll() throws Exception {
		List<Asset> domainOut;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			Specification<Asset> specification = (root, query, cb) -> cb
					.equal(root.get("assetCategory").get("assetCategoryType"), AssetCategoryType.PARTS_AND_SUPPLIES);
			domainOut = partDao.findAll(specification);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			Specification<Asset> specification = (root, query, cb) -> {
				return cb.and(
						cb.equal(root.get("assetCategory").get("assetCategoryType"),
								AssetCategoryType.PARTS_AND_SUPPLIES),
						cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness()));
			};
			domainOut = partDao.findAll(specification);
		} else {
			Specification<Asset> specification = (root, query, cb) -> {
				return cb.and(
						cb.equal(root.get("assetCategory").get("assetCategoryType"),
								AssetCategoryType.PARTS_AND_SUPPLIES),
						cb.equal(root.get("business"), AuthenticationUtil.getLoginSite().getSite().getBusiness()));
			};
			domainOut = partDao.findAll(specification);
		}
		List<PartDTO> out = PartMapper.getInstance().domainToDTOList(domainOut);
		return out;

	}

	@Override
	public DataTablesOutput<PartDTO> findAll(FocusDataTablesInput input) throws Exception {
		DataTablesOutput<Asset> domainOut;
		PartSearchPropertyMapper.getInstance().generateDataTableInput(input);
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			Specification<Asset> specification = (root, query, cb) -> cb
					.equal(root.get("assetCategory").get("assetCategoryType"), AssetCategoryType.PARTS_AND_SUPPLIES);
			domainOut = partDao.findAll(input, specification);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			Specification<Asset> specification = (root, query, cb) -> {
				return cb.and(
						cb.equal(root.get("assetCategory").get("assetCategoryType"),
								AssetCategoryType.PARTS_AND_SUPPLIES),
						cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness()));
			};
			domainOut = partDao.findAll(input, specification);
		} else {
			Specification<Asset> specification = (root, query, cb) -> {
				return cb.and(
						cb.equal(root.get("assetCategory").get("assetCategoryType"),
								AssetCategoryType.PARTS_AND_SUPPLIES),
						cb.equal(root.get("business"), AuthenticationUtil.getLoginSite().getSite().getBusiness()));
			};
			domainOut = partDao.findAll(input, specification);
		}
		DataTablesOutput<PartDTO> out = PartMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	@Override
	public DataTablesOutput<PartDTO> findAllSparePart(FocusDataTablesInput input) throws Exception {
		DataTablesOutput<Asset> domainOut;
		PartSearchPropertyMapper.getInstance().generateDataTableInput(input);
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			Specification<Asset> specification = (root, query, cb) -> {
				return cb.and(
						cb.equal(root.get("assetCategory").get("assetCategoryType"),
								AssetCategoryType.PARTS_AND_SUPPLIES),
						cb.equal(root.get("partUsageType"), PartUsageType.SPARE_PART));
			};
			domainOut = partDao.findAll(input, specification);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			Specification<Asset> specification = (root, query, cb) -> {
				return cb.and(
						cb.equal(root.get("assetCategory").get("assetCategoryType"),
								AssetCategoryType.PARTS_AND_SUPPLIES),
						cb.equal(root.get("partUsageType"), PartUsageType.SPARE_PART),
						cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness()));
			};
			domainOut = partDao.findAll(input, specification);
		} else {
			Specification<Asset> specification = (root, query, cb) -> {
				return cb.and(
						cb.equal(root.get("assetCategory").get("assetCategoryType"),
								AssetCategoryType.PARTS_AND_SUPPLIES),
						cb.equal(root.get("partUsageType"), PartUsageType.SPARE_PART),
						cb.equal(root.get("business"), AuthenticationUtil.getLoginSite().getSite().getBusiness()));
			};
			domainOut = partDao.findAll(input, specification);
		}
		DataTablesOutput<PartDTO> out = PartMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	@Override
	public byte[] getPartImageStream(Integer id, HttpServletRequest request) throws IOException {
		if (id != null) {
			String imagePath = partDao.getAssetImageLocation(id);
			String uploadLocation = new File(environment.getProperty("upload.location")).getPath();
			if (imagePath != null) {
				// return FileDownloadUtil.getByteInputStream(uploadLocation + imagePath);
				return amazonS3ObjectUtil.downloadByteArray(imagePath);

			}
		}

		return FileDownloadUtil
				.getByteInputStream(request.getServletContext().getRealPath("").concat(PART_DEFAULT_IMAGE));
	}

	@Override
	public DataTablesOutput<PartDTO> getPartsByBusiness(FocusDataTablesInput input, Integer bizId) throws Exception {

		AssetSearchPropertyMapper.getInstance().generateDataTableInput(input);
		Specification<Asset> specification = (root, query, cb) -> cb.and(
				cb.equal(root.get("assetCategory").get("assetCategoryType"), AssetCategoryType.PARTS_AND_SUPPLIES),
				cb.equal(root.get("business").get("id"), bizId));

		DataTablesOutput<Asset> domainOut = assetDao.findAll(input, specification);
		DataTablesOutput<PartDTO> out = PartMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

}
