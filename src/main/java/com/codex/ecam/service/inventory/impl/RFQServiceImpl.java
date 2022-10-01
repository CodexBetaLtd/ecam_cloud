package com.codex.ecam.service.inventory.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

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

import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.constants.inventory.RFQStatus;
import com.codex.ecam.constants.util.AffixList;
import com.codex.ecam.dao.admin.CountryDao;
import com.codex.ecam.dao.admin.UserDao;
import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dao.biz.SupplierDao;
import com.codex.ecam.dao.inventory.PurchaseOrderItemDao;
import com.codex.ecam.dao.inventory.RFQDao;
import com.codex.ecam.dao.inventory.mrn.MRNDao;
import com.codex.ecam.dao.inventory.mrn.MRNItemDao;
import com.codex.ecam.dto.inventory.purchaseOrder.PurchaseOrderDTO;
import com.codex.ecam.dto.inventory.rfq.RFQDTO;
import com.codex.ecam.dto.inventory.rfq.RFQFileDTO;
import com.codex.ecam.dto.inventory.rfq.RFQItemDTO;
import com.codex.ecam.dto.inventory.rfq.RFQNotificationDTO;
import com.codex.ecam.dto.inventory.rfq.RFQRepDTO;
import com.codex.ecam.dto.inventory.rfq.RFQSupplierDTO;
import com.codex.ecam.mappers.purchasing.rfq.RFQFileMapper;
import com.codex.ecam.mappers.purchasing.rfq.RFQItemMapper;
import com.codex.ecam.mappers.purchasing.rfq.RFQMapper;
import com.codex.ecam.mappers.purchasing.rfq.RFQNotificationMapper;
import com.codex.ecam.mappers.purchasing.rfq.RFQReportMapper;
import com.codex.ecam.model.admin.User;
import com.codex.ecam.model.inventory.mrn.MRN;
import com.codex.ecam.model.inventory.mrn.MRNItem;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderItem;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderItemRFQItem;
import com.codex.ecam.model.inventory.rfq.RFQ;
import com.codex.ecam.model.inventory.rfq.RFQFile;
import com.codex.ecam.model.inventory.rfq.RFQItem;
import com.codex.ecam.model.inventory.rfq.RFQNotification;
import com.codex.ecam.model.inventory.rfq.RFQSupplier;
import com.codex.ecam.params.VelocityMail;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.inventory.MRNResult;
import com.codex.ecam.result.purchasing.RFQResult;
import com.codex.ecam.service.inventory.api.RFQService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.UniqueCodeUtil;
import com.codex.ecam.util.VelocityEmailSender;
import com.codex.ecam.util.aws.AmazonS3ObjectUtil;
import com.codex.ecam.util.search.inventory.rfq.RFQPropertyMapper;

@Service
public class RFQServiceImpl implements RFQService {

	private final static Logger logger = LoggerFactory.getLogger(RFQServiceImpl.class);

	@Autowired
	Environment environment;

	@Autowired
	private RFQDao rfqDao;

	@Autowired
	private BusinessDao businessDao;

	@Autowired
	private AssetDao assetDao;

	@Autowired
	private CountryDao countryDao;

	@Autowired
	private SupplierDao supplierDao;

	@Autowired
	private PurchaseOrderItemDao purchaseOrderItemDao;

	@Autowired
	private UserDao userDao;
	@Autowired
	private MRNDao mrnDao;

	@Autowired
	private MRNItemDao mrnItemDao;

	@Autowired
	private VelocityEmailSender velocityEmailService;

	@Autowired
	private AmazonS3ObjectUtil amazonS3ObjectUtil;

	@Override
	public RFQDTO findById(Integer id) throws Exception {
		final RFQ domain = rfqDao.findOne(id);
		if (domain != null) {
			return RFQMapper.getInstance().domainToDto(domain);
		}
		return null;
	}

	@Override
	public RFQRepDTO findRFQRepDTOById(Integer id) throws Exception {
		RFQRepDTO repDTO = null;
		try {
			final RFQ domain = rfqDao.findOne(id);
			if (domain != null) {
				repDTO = RFQReportMapper.getInstance().domainToRepDTO(domain);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return repDTO;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public RFQResult update(RFQDTO dto) {
		final RFQResult result = new RFQResult(null, dto);
		try {
			final RFQ domain = rfqDao.findOne(dto.getId());
			result.setDomainEntity(domain);
			saveOrUpdate(result);
			result.addToMessageList("RFQ Updated Successfully.");
		} catch (final Exception e) {
			result.setResultStatusError();
			result.addToErrorList(e.getMessage());
		}
		return result;
	}

	@Override
	public PurchaseOrderDTO saveWithPurchaseOrder(RFQDTO rfq) {
		return null;
		// List<Integer> rfqItemIds =
		// save(rfq).getDomainEntity().getRfqItems().stream().map(RFQItem::getId).collect(Collectors.toList());
		// return
		// purchaseOrderService.createPurchaseOrderFromRFQItems(rfqItemIds);
	}

	@Override
	public RFQResult save(RFQDTO dto) {
		final RFQResult result = createPartResult(dto);
		try {
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (final ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("RFQ Already updated. Please Reload RFQ.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	private RFQResult createPartResult(RFQDTO dto) {
		RFQResult result;
		if (dto.getId() != null && dto.getId() > 0) {
			result = new RFQResult(rfqDao.findOne(dto.getId()), dto);
		} else {
			result = new RFQResult(new RFQ(), dto);
		}

		return result;
	}

	private String getMessageByAction(RFQDTO dto) {
		if (dto.getId() == null) {
			return "RFQ Added Successfully.";
		} else {
			return "RFQ Updated Successfully.";
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(RFQResult result) throws Exception {
		try {
			// setRFQStatusChange(result);
			RFQMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
			setRFQData(result);
			rfqDao.save(result.getDomainEntity());
			result.setDtoEntity(findById(result.getDomainEntity().getId()));
		} catch (final ObjectOptimisticLockingFailureException ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			result.setResultStatusError();
			result.addToErrorList("RFQ Already updated. Please Reload RFQ.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public RFQResult delete(Integer id) {
		final RFQResult result = new RFQResult(null, null);
		try {
			rfqDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("RFQ Deleted Successfully.");
		} catch (final Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToMessageList("Error Occurred! RFQ Deleted Unsuccessful.".concat(ex.getMessage()));
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public RFQResult deleteMultiple(Integer[] ids) throws Exception {
		final RFQResult result = new RFQResult(null, null);
		try {
			for (final Integer id : ids) {
				rfqDao.delete(id);
			}
			result.setResultStatusSuccess();
			result.addToMessageList("RFQ(s) Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("RFQ(s) Already Used. Cannot delete.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	public DataTablesOutput<RFQDTO> findAll(FocusDataTablesInput dataTablesInput) throws Exception {
		RFQPropertyMapper.getInstance().generateDataTableInput(dataTablesInput);

		DataTablesOutput<RFQ> domainOut;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			domainOut = rfqDao.findAll(dataTablesInput);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			final Specification<RFQ> specification = (root, query, cb) -> cb.equal(root.get("business"),
					AuthenticationUtil.getLoginUserBusiness());
			domainOut = rfqDao.findAll(dataTablesInput, specification);
		} else {
			final Specification<RFQ> specification = (root, query, cb) -> cb.and(
					cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness()),
					cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite()));
			domainOut = rfqDao.findAll(dataTablesInput, specification);
		}
		final DataTablesOutput<RFQDTO> out = RFQMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	private void setRFQData(RFQResult result) throws Exception {
		setBusiness(result);
		setSite(result);
		setSupplier(result);
		setSupplierCountry(result);
		setShipToFacility(result);
		setShipToCountry(result);
		setItems(result);
		setRFQFiles(result);
		setRFQNotification(result);

		// genaratePDF(result,request,response);
	}

	// private void genaratePDF(RFQResult result,HttpServletRequest request,
	// HttpServletResponse response) throws IOException, JRException{
	//
	// try {
	// String inputFilePath = "/rfq/rfq_report";
	// String fileName = result.getDtoEntity().getCode();
	// //List<RFQDTO> dtoList = new ArrayList<>();
	//
	// String reportDir = request.getRealPath("").concat("/resources/report/");
	// OutputStream outputStream = null;
	// Map<String, Object> params = new HashMap<String, Object>();
	// String uploadFolder = environment.getProperty("upload.rfq.file.folder");
	// String uploadLocation = environment.getProperty("upload.location");
	// List<RFQRepDTO> dtoList = new ArrayList<>();
	// String outPutDir=uploadLocation+uploadFolder + "/reports/"+fileName;
	// dtoList.add(RFQReportMapper.getInstance().repDTOToDto(result.getDtoEntity()));
	// JasperExportManager.exportReportToPdfFile(ReportUtil.getInstance().generatePDF(dtoList,
	// reportDir, params, inputFilePath, outputStream),outPutDir);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }

	private void setRFQFiles(RFQResult result) throws Exception {
		final Set<RFQFile> rfqFiles = new HashSet<>();

		if (result.getDtoEntity().getRfqFileDTOs() != null && result.getDtoEntity().getRfqFileDTOs().size() > 0) {

			final Set<RFQFile> currentRfqFiles = result.getDomainEntity().getRfqFiles();

			for (final RFQFileDTO rfqFileDTO : result.getDtoEntity().getRfqFileDTOs()) {
				RFQFile rfqFile;

				if (rfqFileDTO.getId() != null) {
					rfqFile = currentRfqFiles.stream().filter((x) -> x.getId().equals(rfqFileDTO.getId())).findAny()
							.orElseGet(RFQFile::new);
				} else {
					rfqFile = new RFQFile();
				}

				RFQFileMapper.getInstance().dtoToDomain(rfqFileDTO, rfqFile);
				rfqFile.setRfq(result.getDomainEntity());

				rfqFiles.add(rfqFile);
			}
		}
		result.getDomainEntity().setRfqFiles(rfqFiles);
	}

	private void setItems(RFQResult result) throws Exception {
		final List<RFQItem> items = new ArrayList<>();

		if (result.getDtoEntity().getItems() != null && result.getDtoEntity().getItems().size() > 0) {

			final List<RFQItem> currentItems = result.getDomainEntity().getRfqItems();

			for (final RFQItemDTO itemDTO : result.getDtoEntity().getItems()) {

				RFQItem item;

				if (currentItems != null && currentItems.size() > 0) {
					final Optional<RFQItem> optionalItem = currentItems.stream()
							.filter((x) -> x.getId() == itemDTO.getItemId()).findAny();
					if (optionalItem.isPresent()) {
						item = optionalItem.get();
					} else {
						item = new RFQItem();
					}
				} else {
					item = new RFQItem();
				}
				createRFQItem(itemDTO, item, result.getDomainEntity());
				items.add(item);
			}
		}
		result.getDomainEntity().setRfqItems(items);
	}

	private void createRFQItem(RFQItemDTO dto, RFQItem domain, RFQ rfq) throws Exception {
		RFQItemMapper.getInstance().dtoToDomain(dto, domain);
		domain.setRfq(rfq);

		if (dto.getItemAssetId() != null && dto.getItemAssetId() > 0) {
			domain.setAsset(assetDao.findOne(dto.getItemAssetId()));
		}

		if (dto.getItemPurchaseOrderItemId() != null && dto.getItemPurchaseOrderItemId() > 0) {

			final PurchaseOrderItem poItem = purchaseOrderItemDao.findOne(dto.getItemPurchaseOrderItemId());

			final PurchaseOrderItemRFQItem item = new PurchaseOrderItemRFQItem();
			item.setPurchaseOrderItem(poItem);
			item.setRfqItem(domain);
			item.setIsDeleted(false);

			domain.getPurchaseOrderItems().add(item);
		}

	}

	private void setBusiness(RFQResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getBusinessId() != null) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}

	private void setSite(RFQResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getSiteId() != null) {
			result.getDomainEntity().setSite(assetDao.findOne(result.getDtoEntity().getSiteId()));
		}
	}

	private void setSupplier(RFQResult result) {
		final Set<RFQSupplier> rfqSuppliers = new HashSet<>();
		final List<RFQSupplierDTO> rfqSupplierDTOs = result.getDtoEntity().getRfqSupplireDTOs();

		if (rfqSupplierDTOs != null && rfqSupplierDTOs.size() > 0) {
			final Set<RFQSupplier> currentRFQSuppliers = result.getDomainEntity().getRfqSupplier();
			RFQSupplier rfqSupplier = new RFQSupplier();

			for (final RFQSupplierDTO rfqSupplierDTO : rfqSupplierDTOs) {
				if (currentRFQSuppliers != null && currentRFQSuppliers.size() > 0) {
					rfqSupplier = currentRFQSuppliers.stream().filter((x) -> x.getId().equals(rfqSupplierDTO.getId()))
							.findAny().orElseGet(RFQSupplier::new);
				} else {
					rfqSupplier = new RFQSupplier();
				}
				createSupplier(result.getDomainEntity(), rfqSupplierDTO, rfqSupplier);
				rfqSuppliers.add(rfqSupplier);
			}
		}
		result.getDomainEntity().setRfqSupplier(rfqSuppliers);
	}

	private void createSupplier(RFQ domainEntity, RFQSupplierDTO rfqSupplierDTO, RFQSupplier rfqSupplier) {
		rfqSupplier.setRfq(domainEntity);
		rfqSupplier.setIsDeleted(Boolean.FALSE);
		rfqSupplier.setSupplier(
				rfqSupplierDTO.getSupplierId() != null ? supplierDao.findOne(rfqSupplierDTO.getSupplierId()) : null);
	}

	private void setSupplierCountry(RFQResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getSupplierCountry() != null) {
			result.getDomainEntity().setSupplierCountry(countryDao.findOne(result.getDtoEntity().getSupplierCountry()));
		}
	}

	private void setShipToFacility(RFQResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getShipToId() != null) {
			result.getDomainEntity().setShipTo(assetDao.findOne(result.getDtoEntity().getShipToId()));
		}
	}

	private void setShipToCountry(RFQResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getShipToCountry() != null) {
			result.getDomainEntity().setShippingCountry(countryDao.findOne(result.getDtoEntity().getShipToCountry()));
		}
	}

	private void setRFQNotification(RFQResult result) throws Exception {
		final Set<RFQNotification> rfqNotifications = new HashSet<>();
		final List<RFQNotificationDTO> rfqNotificationDTOs = result.getDtoEntity().getNotificationDTOs();

		if (rfqNotificationDTOs != null && rfqNotificationDTOs.size() > 0) {
			final Set<RFQNotification> currentRFQtNotifications = result.getDomainEntity().getRfqNotifications();
			RFQNotification rfqNotification = new RFQNotification();

			for (final RFQNotificationDTO rfqNotificationDTO : rfqNotificationDTOs) {
				if (currentRFQtNotifications != null && currentRFQtNotifications.size() > 0) {
					rfqNotification = currentRFQtNotifications.stream()
							.filter((x) -> x.getId().equals(rfqNotificationDTO.getId())).findAny()
							.orElseGet(RFQNotification::new);
				} else {
					rfqNotification = new RFQNotification();
				}
				createNotification(result.getDomainEntity(), rfqNotificationDTO, rfqNotification);
				rfqNotifications.add(rfqNotification);
			}
		}
		result.getDomainEntity().setRfqNotifications(rfqNotifications);
	}

	private void createNotification(RFQ domainEntity, RFQNotificationDTO rfqNotificationDTO,
			RFQNotification rfqNotification) throws Exception {
		RFQNotificationMapper.getInstance().dtoToDomain(rfqNotificationDTO, rfqNotification);
		rfqNotification.setRfq(domainEntity);
		rfqNotification.setUser(
				rfqNotificationDTO.getUserId() != null ? userDao.findOne(rfqNotificationDTO.getUserId()) : null);
	}

	/***************************************
	 ********** RFQ Status Change **********
	 ***************************************/
	@Override
	public RFQResult statusChange(Integer id, RFQStatus rfqStatus) {
		RFQResult result = new RFQResult(null, null);
		try {
			final RFQDTO rfqdto = findById(id);
			final RFQStatus rfqPreviousStatus = rfqdto.getRfqStatus();
			if (rfqStatus != null) {
				if (rfqStatus.equals(RFQStatus.SENT)) {
					rfqdto.setRfqStatus(RFQStatus.SENT);
					result.addToMessageList("RFQ Status Changes as SENT.");
				} else if (rfqStatus.equals(RFQStatus.RECEIVED)) {
					rfqdto.setRfqStatus(RFQStatus.RECEIVED);
					result.addToMessageList("RFQ Status Changes as RECEIVED.");
				} else if (rfqStatus.equals(RFQStatus.CONFIRM)) {
					rfqdto.setRfqStatus(RFQStatus.CONFIRM);
					result.addToMessageList("RFQ Status Changes as CONFIRM.");
				} else if (rfqStatus.equals(RFQStatus.CANCELLED)) {
					rfqdto.setRfqStatus(RFQStatus.DRAFT);
					result.addToMessageList("RFQ Status Changes as DRAFT.");
				}
			}

			result = update(rfqdto);
			sendStatusChangeMail(result, rfqPreviousStatus);
			return result;
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void sendStatusChangeMail(RFQResult result, RFQStatus prestatus) {
		final List<RFQNotification> domainOut = rfqDao.findByNotificationById(result.getDtoEntity().getId());
		for (final RFQNotification notification : domainOut) {
			if (notification.getUser() != null && notification.getNotifyOnStatusChannged()) {
				rfqStatusChangeMail(result, prestatus, notification.getUser());
			}
		}
	}

	private void rfqStatusChangeMail(RFQResult result, RFQStatus prestatus, User user) {
		final VelocityMail velocityMail = new VelocityMail();
		velocityMail.getModel().put("rfqCode", result.getDtoEntity().getCode());
		velocityMail.getModel().put("previousstatus", prestatus.getName());
		velocityMail.getModel().put("currentstatus", result.getDtoEntity().getRfqStatus().getName());
		velocityMail.setSubject("RFQ status change - " + result.getDtoEntity().getCode());
		velocityMail.setTo(user.getEmailAddress());
		velocityMail.setVmTemplate("rfqstatuschange");
		velocityEmailService.sendEmail(velocityMail);
	}

	@Override
	public RFQDTO createRFQFromPoItems(String poItemIds) {
		final RFQDTO dto = new RFQDTO();
		final List<RFQItemDTO> items = new ArrayList<>();
		final String[] ids = poItemIds.split(",");

		RFQItemDTO item;

		for (final String id : ids) {
			item = new RFQItemDTO();
			final PurchaseOrderItem poItem = purchaseOrderItemDao.findOne(Integer.parseInt(id));
			item.setItemAssetId(poItem.getAsset().getId());
			item.setItemAssetName(poItem.getAsset().getName());
			item.setItemQtyRequested(poItem.getQtyOnOrder());
			item.setItemPurchaseOrderItemId(poItem.getId());
			item.setItemPurchaseOrderCodes(poItem.getPurchaseOrder().getCode());
			items.add(item);
		}

		dto.setItems(items);
		return dto;
	}

	/************************************
	 ************* RFQ File Upload ******
	 ************************************/
	@Override
	public String rfqFileUpload(MultipartFile file, String refId) throws Exception {
		final String key = environment.getProperty("upload.location.s3")
				+ environment.getProperty("upload.location.rfq.file.s3") + refId + "/" + file.getOriginalFilename();
		try {
			amazonS3ObjectUtil.uploadS3Object(key, file);
			return key;
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/************************************
	 ********** RFQ File Download ******
	 ************************************/
	@Override
	public void rfqFileDownload(Integer id, HttpServletResponse response) throws Exception {
		if (id != null) {
			final RFQFile file = rfqDao.findByFileId(id);
			final int index = file.getFileLocation().lastIndexOf("\\");
			final String fileName = file.getFileLocation().substring(index + 1);
			amazonS3ObjectUtil.downloadToResponse(file.getFileLocation(), fileName, response);

		}

	}

	@Override
	public void rfqFileDelete(Integer id) throws Exception {
		final String uploadLocation = new File(environment.getProperty("upload.location")).getPath();

		final RFQFile rfqFile = rfqDao.findByFileId(id);
		final String externalFilePath = uploadLocation + rfqFile.getFileLocation();
		final File file = new File(externalFilePath);
		if (file.delete()) {
			System.out.println("File deleted successfully");
		} else {
			System.out.println("Failed to delete the file");
		}

	}

	@Override
	public MRNResult generateRFQFromMrn(String idStr, Integer mrnId) {
		final MRNResult result = new MRNResult(null, null);
		final MRN mrn = mrnDao.findOne(mrnId);
		final RFQDTO rfqDTO = new RFQDTO();
		rfqDTO.setRfqStatus(RFQStatus.DRAFT);
		rfqDTO.setCode("");
		rfqDTO.setIsDeleted(Boolean.FALSE);
		if (mrn != null && mrn.getBusiness() != null) {
			rfqDTO.setBusinessId(mrn.getBusiness().getId());
		}
		if (mrn != null && mrn.getSite() != null) {
			rfqDTO.setSiteId(mrn.getSite().getId());
		}

		final List<RFQItemDTO> rfqItemDTOs = new ArrayList<>();
		final List<Integer> ids = Arrays.asList(idStr.split(",")).stream().map(Integer::parseInt)
				.collect(Collectors.toList());
		for (final Integer id : ids) {
			final MRNItem item = mrnItemDao.findOne(id);
			final RFQItemDTO itemDTO = new RFQItemDTO();
			itemDTO.setItemQtyRequested(item.getApprovedQuantity().intValue());
			itemDTO.setItemAssetId(item.getPart().getId());
			rfqItemDTOs.add(itemDTO);
		}
		rfqDTO.setItems(rfqItemDTOs);

		try {
			final RFQResult rfqResult = save(rfqDTO);
			result.setStatus(ResultStatus.SUCCESS);
			result.addToMessageList("Successfully Generated the RFQ as ");
			result.addToMessageList(rfqResult.getDomainEntity().getId().toString());
			result.addToMessageList("Link to rfq");

		} catch (final Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatus.ERROR);
			result.addToErrorList("Error while RFQ generate");
		}
		return result;
	}

	@Override
	public RFQResult createNewRFQ() {
		final RFQResult result = new RFQResult(null, null);
		try {
			final RFQDTO dto = new RFQDTO();
			if (!AuthenticationUtil.isAuthUserAdminLevel()) {
				dto.setCode(getNextCode(AuthenticationUtil.getLoginUserBusiness().getId()));
			} else {
				dto.setCode("");
			}
			result.setDtoEntity(dto);
			result.setResultStatusSuccess();
			result.addToMessageList("RFQ generate successfully.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("ERROR! RFQ not generated! ".concat(ex.getMessage()));
		}
		return result;
	}

	@Override

	public String getNextCode(Integer businessId) {
		if (businessId != null) {
			final RFQ lastDomain = rfqDao.findLastDomainByBusiness(businessId);
			return UniqueCodeUtil.getNextCode(AffixList.RFQ, lastDomain == null ? "" : lastDomain.getCode());
		} else {
			return "";
		}
	}

}
