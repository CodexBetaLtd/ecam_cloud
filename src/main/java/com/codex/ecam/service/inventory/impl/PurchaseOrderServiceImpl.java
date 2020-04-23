package com.codex.ecam.service.inventory.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.codex.ecam.constants.PurchaseOrderAdditionalCostType;
import com.codex.ecam.constants.PurchaseOrderStatus;
import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.constants.ShippingType;
import com.codex.ecam.dao.admin.AccountDao;
import com.codex.ecam.dao.admin.ChargeDepartmentDao;
import com.codex.ecam.dao.admin.CountryDao;
import com.codex.ecam.dao.admin.CurrencyDao;
import com.codex.ecam.dao.admin.UserDao;
import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dao.inventory.MRNDao;
import com.codex.ecam.dao.inventory.MRNItemDao;
import com.codex.ecam.dao.inventory.PurchaseOrderDao;
import com.codex.ecam.dao.inventory.PurchaseOrderItemDao;
import com.codex.ecam.dao.inventory.RFQItemDao;
import com.codex.ecam.dao.maintenance.WorkOrderDao;
import com.codex.ecam.dto.inventory.purchaseOrder.PurchaseOrderAdditionalCostDTO;
import com.codex.ecam.dto.inventory.purchaseOrder.PurchaseOrderDTO;
import com.codex.ecam.dto.inventory.purchaseOrder.PurchaseOrderDiscussionDTO;
import com.codex.ecam.dto.inventory.purchaseOrder.PurchaseOrderFileDTO;
import com.codex.ecam.dto.inventory.purchaseOrder.PurchaseOrderItemDTO;
import com.codex.ecam.dto.inventory.purchaseOrder.PurchaseOrderNotificationDTO;
import com.codex.ecam.mappers.purchasing.PurchaseOrderFileMapper;
import com.codex.ecam.mappers.purchasing.PurchaseOrderItemMapper;
import com.codex.ecam.mappers.purchasing.PurchaseOrderMapper;
import com.codex.ecam.mappers.purchasing.PurchaseOrderRFQMapper;
import com.codex.ecam.model.inventory.mrn.MRN;
import com.codex.ecam.model.inventory.mrn.MRNItem;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrder;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderAdditionalCost;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderDiscussion;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderFile;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderItem;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderItemRFQItem;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderNotification;
import com.codex.ecam.model.inventory.rfq.RFQItem;
import com.codex.ecam.params.VelocityMail;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.inventory.MRNResult;
import com.codex.ecam.result.purchasing.PurchaseOrderResult;
import com.codex.ecam.service.inventory.api.PurchaseOrderService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.FileDownloadUtil;
import com.codex.ecam.util.FileUploadUtil;
import com.codex.ecam.util.VelocityEmailSender;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

	@Autowired
	private PurchaseOrderDao purchaseOrderDao;

	@Autowired
	private BusinessDao businessDao;

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private ChargeDepartmentDao chargeDepartmentDao;

	@Autowired
	private AssetDao assetDao;

	@Autowired
	private CountryDao countryDao;

	@Autowired
	private CurrencyDao currencyDao;

	@Autowired
	private WorkOrderDao workOrderDao;

	@Autowired
	private VelocityEmailSender velocityEmailService;

	@Autowired
	private RFQItemDao rfqItemDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private MRNItemDao mrnItemDao;

	@Autowired
	private MRNDao mrnDao;

	@Autowired
	private PurchaseOrderItemDao poItemDao;

	@Autowired
	Environment environment;

	@Override
	public DataTablesOutput<PurchaseOrderDTO> findAll(FocusDataTablesInput input) throws Exception {
		DataTablesOutput<PurchaseOrder> domainOut;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			domainOut = purchaseOrderDao.findAll(input);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			Specification<PurchaseOrder> specification = (root, query, cb) -> cb.equal(root.get("business"),
					AuthenticationUtil.getLoginUserBusiness());
			domainOut = purchaseOrderDao.findAll(input, specification);
		} else {
			Specification<PurchaseOrder> specification = (root, query, cb) -> cb.equal(root.get("site"),
					AuthenticationUtil.getLoginSite().getSite());
			domainOut = purchaseOrderDao.findAll(input, specification);
		}
		DataTablesOutput<PurchaseOrderDTO> out = PurchaseOrderMapper.getInstance()
				.domainToDTODataTablesOutput(domainOut);
		return out;
	}

	public DataTablesOutput<PurchaseOrderItemDTO> findAllApproved(FocusDataTablesInput input) {
		DataTablesOutput<PurchaseOrderItem> domainOut;
		Specification<PurchaseOrderItem> specification;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			specification = (root, query, cb) -> cb.equal(root.get("purchaseOrder").get("purchaseOrderStatus"),
					PurchaseOrderStatus.APPROVED);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			specification = (root, query, cb) -> cb.and(
					cb.equal(root.get("purchaseOrder").get("business"), AuthenticationUtil.getLoginUserBusiness()),
					cb.equal(root.get("purchaseOrder").get("purchaseOrderStatus"), PurchaseOrderStatus.APPROVED));
		} else {
			specification = (root, query, cb) -> cb.and(
					cb.equal(root.get("purchaseOrder").get("site"), AuthenticationUtil.getLoginSite().getSite()),
					cb.equal(root.get("purchaseOrder").get("purchaseOrderStatus"), PurchaseOrderStatus.APPROVED));

		}
		domainOut = poItemDao.findAll(input, specification);

		DataTablesOutput<PurchaseOrderItemDTO> out = null;
		try {
			out = PurchaseOrderItemMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}

	@Override
	public PurchaseOrderDTO findById(Integer id) throws Exception {
		PurchaseOrder domain = purchaseOrderDao.findOne(id);

		if (domain != null) {
			return PurchaseOrderMapper.getInstance().domainToDto(domain);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public PurchaseOrderResult update(PurchaseOrderDTO dto) {
		PurchaseOrderResult result = new PurchaseOrderResult(null, dto);
		try {
			PurchaseOrder domain = purchaseOrderDao.findOne(dto.getId());
			result.setDomainEntity(domain);
			saveOrUpdate(result);
			result.addToMessageList("Purchase Order Updated Successfully.");
		} catch (Exception e) {
			result.setResultStatusError();
			result.addToErrorList(e.getMessage());
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public PurchaseOrderResult save(PurchaseOrderDTO dto) {
		PurchaseOrderResult result = new PurchaseOrderResult(new PurchaseOrder(), dto);
		saveOrUpdate(result);
		result.addToMessageList("Purchase Order Added Successfully.");
		return result;
	}

	private void saveOrUpdate(PurchaseOrderResult result) {
		try {
			PurchaseOrderMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
			setPurchaseOrderData(result);
			purchaseOrderDao.save(result.getDomainEntity());
			result.updateDtoIdAndVersion();
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Purchase Order Already updated. Please Reload Purchase Order.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
	}

	private void setPurchaseOrderData(PurchaseOrderResult result) throws Exception {
		setBusiness(result);
		setSite(result);
		setAccount(result);
		setChargeDepartment(result);
		setPurchaseOrderCurrency(result);

		setSupplierBusiness(result);
		setSupplierCountry(result);

		setShipToFacility(result);
		setShipToCountry(result);

		setBillToFacility(result);
		setBillToCountry(result);

		setAdditionalCost(result);
		setItems(result);
		setDiscussion(result);
		setNotification(result);
		setPurchaseOrderFiles(result);

	}

	private void setItems(PurchaseOrderResult result) throws Exception {

		List<PurchaseOrderItem> items = new ArrayList<>();

		if ((result.getDtoEntity().getItems() != null) && (result.getDtoEntity().getItems().size() > 0)) {

			List<PurchaseOrderItem> currentItems = result.getDomainEntity().getPurchaseOrderItems();

			for (PurchaseOrderItemDTO itemDTO : result.getDtoEntity().getItems()) {

				PurchaseOrderItem item;

				if ((currentItems != null) && (currentItems.size() > 0)) {
					Optional<PurchaseOrderItem> optionalItem = currentItems.stream()
							.filter((x) -> x.getId() == itemDTO.getItemId()).findAny();
					if (optionalItem.isPresent()) {
						item = optionalItem.get();
					} else {
						item = new PurchaseOrderItem();
					}
				} else {
					item = new PurchaseOrderItem();
				}
				createPurchaseOrderItem(itemDTO, item, result.getDomainEntity());
				items.add(item);
			}
		}
		result.getDomainEntity().setPurchaseOrderItems(items);

	}

	private void createPurchaseOrderItem(PurchaseOrderItemDTO dto, PurchaseOrderItem domain,
			PurchaseOrder purchaseOrder) throws Exception {
		PurchaseOrderItemMapper.getInstance().dtoToDomain(dto, domain);
		domain.setPurchaseOrder(purchaseOrder);
		domain.setSupplier(purchaseOrder.getSupplier());

		if ((dto.getItemAssetId() != null) && (dto.getItemAssetId() > 0)) {
			domain.setAsset(assetDao.findOne(dto.getItemAssetId()));
		}

		if ((dto.getItemAccountId() != null) && (dto.getItemAccountId() > 0)) {
			domain.setAccount(accountDao.findOne(dto.getItemAccountId()));
		}

		if ((dto.getItemChargeDepartmentId() != null) && (dto.getItemChargeDepartmentId() > 0)) {
			domain.setChargeDepartment(chargeDepartmentDao.findOne(dto.getItemChargeDepartmentId()));
		}

		if ((dto.getItemSiteId() != null) && (dto.getItemSiteId() > 0)) {
			domain.setSite(assetDao.findOne(dto.getItemSiteId()));
		}

		if ((dto.getItemSourceWorkOrderId() != null) && (dto.getItemSourceWorkOrderId() > 0)) {
			domain.setSourceWorkOrder(workOrderDao.findOne(dto.getItemSourceWorkOrderId()));
		}

		if ((dto.getItemSourceAssetId() != null) && (dto.getItemSourceAssetId() > 0)) {
			domain.setSourceAsset(assetDao.findOne(dto.getItemSourceAssetId()));
		}

		if ((dto.getItemRfqItemId() != null) && (dto.getItemRfqItemId() > 0)) {
			RFQItem rfqItem = rfqItemDao.findOne(dto.getItemRfqItemId());

			PurchaseOrderItemRFQItem item = new PurchaseOrderItemRFQItem();
			item.setPurchaseOrderItem(domain);
			item.setRfqItem(rfqItem);
			item.setIsDeleted(false);

			domain.getRfqItems().add(item);
		}

	}

	private void setBusiness(PurchaseOrderResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getBusinessId() != null)) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}

	private void setSite(PurchaseOrderResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getSiteId() != null)) {
			result.getDomainEntity().setSite(assetDao.findOne(result.getDtoEntity().getSiteId()));
		}
	}

	private void setPurchaseOrderCurrency(PurchaseOrderResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getPurchaseCurrencyId() != null)) {
			result.getDomainEntity()
					.setPurchaseCurrency(currencyDao.findOne(result.getDtoEntity().getPurchaseCurrencyId()));
		}
	}

	private void setSupplierBusiness(PurchaseOrderResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getSupplierId() != null)) {
			result.getDomainEntity().setSupplier(businessDao.findOne(result.getDtoEntity().getSupplierId()));
		}
	}

	private void setSupplierCountry(PurchaseOrderResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getSupplierCountry() != null)) {
			result.getDomainEntity().setSupplierCountry(countryDao.findOne(result.getDtoEntity().getSupplierCountry()));
		}
	}

	private void setShipToFacility(PurchaseOrderResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getShipToId() != null)) {
			result.getDomainEntity().setShipToFacility(assetDao.findOne(result.getDtoEntity().getShipToId()));
		}
	}

	private void setShipToCountry(PurchaseOrderResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getShipToCountry() != null)) {
			result.getDomainEntity().setShipToCountry(countryDao.findOne(result.getDtoEntity().getShipToCountry()));
		}
	}

	private void setBillToFacility(PurchaseOrderResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getBillToId() != null)) {
			result.getDomainEntity().setBillToFaciltiy(assetDao.findOne(result.getDtoEntity().getBillToId()));
		}
	}

	private void setBillToCountry(PurchaseOrderResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getBillToCountry() != null)) {
			result.getDomainEntity().setBillCountry(countryDao.findOne(result.getDtoEntity().getBillToCountry()));
		}
	}

	private void setAccount(PurchaseOrderResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getAccountId() != null)) {
			result.getDomainEntity().setAccount(accountDao.findOne(result.getDtoEntity().getAccountId()));
		}
	}

	private void setChargeDepartment(PurchaseOrderResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getChargeDepartmentId() != null)) {
			result.getDomainEntity()
					.setChargeDepartment(chargeDepartmentDao.findOne(result.getDtoEntity().getChargeDepartmentId()));
		}
	}

	private void setAdditionalCost(PurchaseOrderResult result) {
		List<PurchaseOrderAdditionalCost> purchaseOrderAdditionalCosts = new ArrayList<PurchaseOrderAdditionalCost>();
		for (PurchaseOrderAdditionalCostDTO purchaseOrderAdditionalCostDTO : result.getDtoEntity()
				.getAdditionalCostDTOs()) {

			PurchaseOrderAdditionalCost purchaseOrderAdditionalCost;

			if (purchaseOrderAdditionalCostDTO.getId() != null) {
				Optional<PurchaseOrderAdditionalCost> optionalpurchaseOrderAdditionalCosts = result.getDomainEntity()
						.getPurchaseOrderAdditionalCosts().stream()
						.filter((x) -> x.getId() == purchaseOrderAdditionalCostDTO.getId()).findAny();
				if (optionalpurchaseOrderAdditionalCosts.isPresent()) {
					purchaseOrderAdditionalCost = optionalpurchaseOrderAdditionalCosts.get();
				} else {
					purchaseOrderAdditionalCost = new PurchaseOrderAdditionalCost();
				}
			} else {
				purchaseOrderAdditionalCost = new PurchaseOrderAdditionalCost();
			}
			purchaseOrderAdditionalCost.setDescription(purchaseOrderAdditionalCostDTO.getAdditionalCostName());
			purchaseOrderAdditionalCost.setIsOverridePoItemTax(purchaseOrderAdditionalCostDTO.getIsOverridePoItemTax());
			purchaseOrderAdditionalCost.setPrice(purchaseOrderAdditionalCostDTO.getAmount());
			purchaseOrderAdditionalCost.setTaxRate(purchaseOrderAdditionalCostDTO.getTaxRate());
			purchaseOrderAdditionalCost.setPurchaseOrder(result.getDomainEntity());
			purchaseOrderAdditionalCost
					.setPurchaseOrderAdditionalCostType(setAdditionalCostType(purchaseOrderAdditionalCostDTO));
			purchaseOrderAdditionalCost.setShippingType(setShippingType(purchaseOrderAdditionalCostDTO));
			purchaseOrderAdditionalCost.setIsDeleted(false);
			purchaseOrderAdditionalCosts.add(purchaseOrderAdditionalCost);

		}
		result.getDomainEntity().setPurchaseOrderAdditionalCosts(null);
		result.getDomainEntity().setPurchaseOrderAdditionalCosts(purchaseOrderAdditionalCosts);
	}

	protected PurchaseOrderAdditionalCostType setAdditionalCostType(
			PurchaseOrderAdditionalCostDTO purchaseOrderAdditionalCostDTO) {
		for (PurchaseOrderAdditionalCostType additionalCostType : PurchaseOrderAdditionalCostType
				.getAdditionalCostTypeList()) {
			if (purchaseOrderAdditionalCostDTO.getAdditionalCostTypeId() == additionalCostType.getId()) {
				return additionalCostType;
			}
		}
		return null;

	}

	protected ShippingType setShippingType(PurchaseOrderAdditionalCostDTO purchaseOrderAdditionalCostDTO) {
		for (ShippingType shippingType : ShippingType.getShippingTypeList()) {
			if (purchaseOrderAdditionalCostDTO.getShippingTypeId() == shippingType.getId()) {
				return shippingType;
			}
		}
		return null;

	}

	private void setPurchaseOrderFiles(PurchaseOrderResult result) throws Exception {
		List<PurchaseOrderFile> purchaseOrderFiles = new ArrayList<PurchaseOrderFile>();

		if ((result.getDtoEntity().getPurchaseOrderFileDTOs() != null)
				&& (result.getDtoEntity().getPurchaseOrderFileDTOs().size() > 0)) {

			List<PurchaseOrderFile> currentPurchaseOrderFiles = result.getDomainEntity().getPurchaseOrderFiles();

			for (PurchaseOrderFileDTO purchaseOrderFileDTO : result.getDtoEntity().getPurchaseOrderFileDTOs()) {
				PurchaseOrderFile purchaseOrderFile;

				if (purchaseOrderFileDTO.getId() != null) {
					purchaseOrderFile = currentPurchaseOrderFiles.stream()
							.filter((x) -> x.getId().equals(purchaseOrderFileDTO.getId())).findAny()
							.orElseGet(PurchaseOrderFile::new);
				} else {
					purchaseOrderFile = new PurchaseOrderFile();
				}

				PurchaseOrderFileMapper.getInstance().dtoToDomain(purchaseOrderFileDTO, purchaseOrderFile);
				purchaseOrderFile.setPurchaseOrder(result.getDomainEntity());

				purchaseOrderFiles.add(purchaseOrderFile);
			}
		}
		result.getDomainEntity().setPurchaseOrderFiles(purchaseOrderFiles);
	}

	private void setDiscussion(PurchaseOrderResult result) {
		List<PurchaseOrderDiscussion> purchaseOrderDiscussions = new ArrayList<PurchaseOrderDiscussion>();
		for (PurchaseOrderDiscussionDTO purchaseOrderDiscussionDTO : result.getDtoEntity().getDiscussionDTOs()) {
			PurchaseOrderDiscussion purchaseOrderDiscussion;
			if (purchaseOrderDiscussionDTO.getId() != null) {
				Optional<PurchaseOrderDiscussion> optionalPurchaseOrderDiscussions = result.getDomainEntity()
						.getPurchaseOrderDiscussions().stream()
						.filter((x) -> x.getId() == purchaseOrderDiscussionDTO.getId()).findAny();
				if (optionalPurchaseOrderDiscussions.isPresent()) {
					purchaseOrderDiscussion = optionalPurchaseOrderDiscussions.get();
				} else {
					purchaseOrderDiscussion = new PurchaseOrderDiscussion();
				}
			} else {
				purchaseOrderDiscussion = new PurchaseOrderDiscussion();
			}
			purchaseOrderDiscussion.setPurchaseOrder(result.getDomainEntity());
			purchaseOrderDiscussion.setComment(purchaseOrderDiscussionDTO.getComment());
			purchaseOrderDiscussion.setIsDeleted(false);
			purchaseOrderDiscussions.add(purchaseOrderDiscussion);
		}
		result.getDomainEntity().setPurchaseOrderDiscussions(null);
		result.getDomainEntity().setPurchaseOrderDiscussions(purchaseOrderDiscussions);
	}

	private void setNotification(PurchaseOrderResult result) {
		List<PurchaseOrderNotification> poNotificationList = new ArrayList<>();
		for (PurchaseOrderNotificationDTO notificationDTO : result.getDtoEntity().getNotificationDTOs()) {
			PurchaseOrderNotification notification = new PurchaseOrderNotification();
			notification
					.setUser(notificationDTO.getUserId() != null ? userDao.findOne(notificationDTO.getUserId()) : null);
			notification.setNotifyOnAssignment(notificationDTO.getNotifyOnAssignment());
			notification.setNotifyOnStatusChange(notificationDTO.getNotifyOnStatusChange());
			notification.setNotifyOnCompletion(notificationDTO.getNotifyOnCompletion());
			notification.setNotifyOnTaskCompleted(notificationDTO.getNotifyOnTaskCompleted());
			notification.setNotifyOnOnlineOffline(notificationDTO.getNotifyOnOnlineOffline());
			notification.setPurchaseOrder(result.getDomainEntity());
			notification.setIsDeleted(Boolean.FALSE);
			poNotificationList.add(notification);
		}
		if (poNotificationList.size() > 0) {
			result.getDomainEntity().setPurchaseOrderNotifications(null);
			result.getDomainEntity().setPurchaseOrderNotifications(poNotificationList);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public PurchaseOrderResult delete(Integer id) {
		PurchaseOrderResult result = new PurchaseOrderResult(null, null);
		try {
			purchaseOrderDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Purchase Order Deleted Successfully.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	public PurchaseOrderResult statusChange(Integer id, PurchaseOrderStatus status) {

		PurchaseOrderResult orderResult = new PurchaseOrderResult(null, null);
		try {
			PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();
			purchaseOrderDTO = findById(id);
			// PurchaseOrderStatus
			// preStaus=purchaseOrderDTO.getPurchaseOrderstatus();
			purchaseOrderDTO.setPurchaseOrderstatus(status);
			// PurchaseOrderStatus
			// currentStatus=purchaseOrderDTO.getPurchaseOrderstatus();
			orderResult = update(purchaseOrderDTO);
			// sendstatusEmail(preStaus,currentStatus);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderResult;

	}

	public void sendstatusEmail(PurchaseOrderStatus prestatus, PurchaseOrderStatus poststatus) {
		VelocityMail velocityMail = new VelocityMail();
		velocityMail.getModel().put("user", "wasantha");
		velocityMail.getModel().put("priviouspurchaseOrderstatus", prestatus.getName());
		velocityMail.getModel().put("currentpurchaseOrderstatus", poststatus.getName());
		velocityMail.setSubject("Purchase order status change");
		velocityMail.setTo("wasanthabr93@gmail.com");
		velocityMail.setVmTemplate("purchaseorderstatuschange");
		velocityEmailService.sendEmail(velocityMail);
	}

	@Override
	public PurchaseOrderDTO createPurchaseOrderFromRFQItems(String rfqItemIds) {
		String[] ids = rfqItemIds.split(",");
		// List<Integer> list =
		// Arrays.asList(ids).stream().mapToInt(Integer::parseInt).collect(Collectors.toList());
		List<Integer> list = Arrays.asList(ids).stream().map(Integer::parseInt).collect(Collectors.toList());
		return generatePurchaseOrderFromRFQItems(list);
	}

	@Override
	public PurchaseOrderDTO createPurchaseOrderFromRFQItems(List<Integer> rfqItemIds) {
		return generatePurchaseOrderFromRFQItems(rfqItemIds);
	}

	protected PurchaseOrderDTO generatePurchaseOrderFromRFQItems(List<Integer> rfqItemIds) {
		PurchaseOrderDTO dto = new PurchaseOrderDTO();
		List<PurchaseOrderItemDTO> items = new ArrayList<>();
		PurchaseOrderItemDTO item;
		for (Integer id : rfqItemIds) {
			item = new PurchaseOrderItemDTO();
			RFQItem rfqItem = rfqItemDao.findOne(id);
			item.setItemAssetId(rfqItem.getAsset().getId());
			item.setItemAssetName(rfqItem.getAsset().getName());
			item.setItemRfqItemId(rfqItem.getId());
			item.setItemRfqCodes(rfqItem.getRfq().getCode());
			item.setItemQtyOnOrder(rfqItem.getRequested());
			item.setItemUnitPrice(rfqItem.getQuotedPricePerUnit());
			items.add(item);
			try {
				dto = PurchaseOrderRFQMapper.getInstance().domainToDto(rfqItem.getRfq());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		dto.setItems(items);
		return dto;
	}

	@Override
	public void purchaseOrderFileDownload(Integer id, HttpServletResponse response) throws IOException {
		String uploadLocation = new File(environment.getProperty("upload.location")).getPath();
		if (id != null) {
			PurchaseOrderFile file = purchaseOrderDao.findByFileId(id);
			String externalFilePath = uploadLocation + file.getFileLocation();
			FileDownloadUtil.flushFile(externalFilePath, file.getFileType(), response);
		}
	}

	@Override
	public String purchaseOrderFileUpload(MultipartFile fileData, String refId) {
		String uploadFolder = environment.getProperty("upload.purchaseorder.file.folder");
		String uploadLocation = environment.getProperty("upload.location");
		try {
			return FileUploadUtil.createFile(fileData, refId, uploadFolder, uploadLocation);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void purchaseOrderFileDelete(Integer id) {

		String uploadLocation = new File(environment.getProperty("upload.location")).getPath();

		PurchaseOrderFile pofile = purchaseOrderDao.findByFileId(id);
		String externalFilePath = uploadLocation + pofile.getFileLocation();
		File file = new File(externalFilePath);
		if (file.delete()) {
			System.out.println("File deleted successfully");
		} else {
			System.out.println("Failed to delete the file");
		}
	}

	@Override
	public MRNResult generatePoFromMrn(String idStr, Integer mrnId) {
		MRNResult result = new MRNResult(null, null);
		MRN mrn = mrnDao.findOne(mrnId);
		PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();
		purchaseOrderDTO.setPurchaseOrderstatus(PurchaseOrderStatus.DRAFT);
		purchaseOrderDTO.setCode("");
		purchaseOrderDTO.setIsDeleted(Boolean.FALSE);
		if (mrn != null && mrn.getBusiness() != null) {
			purchaseOrderDTO.setBusinessId(mrn.getBusiness().getId());
		}
		if (mrn != null && mrn.getSite() != null) {
			purchaseOrderDTO.setSiteId(mrn.getSite().getId());
		}

		List<PurchaseOrderItemDTO> purchaseOrderItemDTOs = new ArrayList<>();
		List<Integer> ids = Arrays.asList(idStr.split(",")).stream().map(Integer::parseInt)
				.collect(Collectors.toList());
		for (Integer id : ids) {
			MRNItem item = mrnItemDao.findOne(id);
			PurchaseOrderItemDTO itemDTO = new PurchaseOrderItemDTO();
			itemDTO.setItemQtyOnOrder(item.getApprovedQuantity().intValue());
			itemDTO.setItemAssetId(item.getPart().getId());
			purchaseOrderItemDTOs.add(itemDTO);
		}
		purchaseOrderDTO.setItems(purchaseOrderItemDTOs);

		try {
			PurchaseOrderResult purchaseOrderResult = save(purchaseOrderDTO);
			result.setStatus(ResultStatus.SUCCESS);
			result.addToMessageList("Successfully Generated PO  ");
			result.addToMessageList(purchaseOrderResult.getDomainEntity().getId().toString());
			result.addToMessageList("Link TO PO");

		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatus.ERROR);
			result.addToErrorList("Error while PO generate");
		}
		return result;
	}

	private String getApprovesPoForItem(MRNItem mrnItem) {
		String poList = "";
		List<PurchaseOrderItem> items = purchaseOrderDao.findItemOrderBeforeDate(mrnItem.getMrn().getDate(),
				mrnItem.getPart().getId());
		if (items != null && items.size() > 0) {
			poList = poList + "Po already generated ";
			for (PurchaseOrderItem item : items) {
				poList = poList + " " + item.getPurchaseOrder().getCode();
			}
		}
		return poList;
	}

	/*
	 * @Override public PurchaseOrderDTO createPurchaseOrderFromRFQItems(String
	 * rfqItemIds) { PurchaseOrderDTO dto = new PurchaseOrderDTO();
	 * List<PurchaseOrderItemDTO> items = new ArrayList<>(); String[] ids =
	 * rfqItemIds.split(","); PurchaseOrderItemDTO item; for ( String id : ids )
	 * { item = new PurchaseOrderItemDTO(); RFQItem rfqItem =
	 * rfqItemDao.findOne(Integer.parseInt(id));
	 * item.setItemAssetId(rfqItem.getAsset().getId());
	 * item.setItemAssetName(rfqItem.getAsset().getName());
	 * item.setItemRfqItemId(rfqItem.getId());
	 * item.setItemRfqCodes(rfqItem.getRfq().getCode());
	 * item.setItemQtyOnOrder(rfqItem.getQuoted());
	 * item.setItemUnitPrice(rfqItem.getQuotedPricePerUnit()); items.add(item);
	 * } dto.setItems(items); return dto; }
	 */

}
