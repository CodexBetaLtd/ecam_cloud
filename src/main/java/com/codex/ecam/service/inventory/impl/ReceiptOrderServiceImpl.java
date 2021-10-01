package com.codex.ecam.service.inventory.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.constants.PurchaseOrderStatus;
import com.codex.ecam.constants.inventory.ReceiptOrderStatus;
import com.codex.ecam.constants.util.AffixList;
import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dao.biz.SupplierDao;
import com.codex.ecam.dao.inventory.AODItemDao;
import com.codex.ecam.dao.inventory.PurchaseOrderItemDao;
import com.codex.ecam.dao.inventory.ReceiptOrderDao;
import com.codex.ecam.dao.inventory.ReceiptOrderItemDao;
import com.codex.ecam.dao.inventory.StockDao;
import com.codex.ecam.dao.inventory.StockHistoryDao;
import com.codex.ecam.dto.inventory.mrn.MRNDTO;
import com.codex.ecam.dto.inventory.purchaseOrder.PurchaseOrderDTO;
import com.codex.ecam.dto.inventory.purchaseOrder.PurchaseOrderItemDTO;
import com.codex.ecam.dto.inventory.receiptOrder.ReceiptOrderDTO;
import com.codex.ecam.dto.inventory.receiptOrder.ReceiptOrderItemDTO;
import com.codex.ecam.dto.inventory.stock.StockDTO;
import com.codex.ecam.exception.inventory.stock.StockException;
import com.codex.ecam.mappers.inventory.stock.StockMapper;
import com.codex.ecam.mappers.purchasing.ReceiptOrderItemMapper;
import com.codex.ecam.mappers.purchasing.ReceiptOrderMapper;
import com.codex.ecam.model.inventory.mrn.MRN;
import com.codex.ecam.model.inventory.mrn.MRNItem;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrder;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderItem;
import com.codex.ecam.model.inventory.receiptOrder.ReceiptOrder;
import com.codex.ecam.model.inventory.receiptOrder.ReceiptOrderItem;
import com.codex.ecam.model.inventory.stock.Stock;
import com.codex.ecam.model.inventory.stock.StockHistory;
import com.codex.ecam.params.VelocityMail;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.inventory.MRNResult;
import com.codex.ecam.result.purchasing.PurchaseOrderResult;
import com.codex.ecam.result.purchasing.ReceiptOrderResult;
import com.codex.ecam.service.inventory.api.ReceiptOrderService;
import com.codex.ecam.service.inventory.api.StockService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.UniqueCodeUtil;
import com.codex.ecam.util.VelocityEmailSender;
import com.codex.ecam.util.search.inventory.receiptOrder.ReceiptOrderPropertyMapper;

@Service
public class ReceiptOrderServiceImpl implements ReceiptOrderService {

	@Autowired
	private ReceiptOrderDao receiptOrderDao;

	@Autowired
	private PurchaseOrderItemDao purchaseOrderItemDao;

	@Autowired
	private AssetDao assetDao;

	@Autowired
	private StockDao stockDao;

	@Autowired
	private StockHistoryDao stockHistoryDao;

	@Autowired
	private SupplierDao supplierDao;

	@Autowired
	private BusinessDao businessDao;

	@Autowired
	private VelocityEmailSender velocityEmailService;

	@Autowired
	private AODItemDao aodItemDao;

	@Autowired
	private StockService stockService;

	private ReceiptOrderDTO findDTOById(Integer id) throws Exception {
		return ReceiptOrderMapper.getInstance().domainToDto(findEntityById(id));
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private ReceiptOrder findEntityById(Integer id) throws Exception {
		return receiptOrderDao.findOne(id);
	}

	@Override
	public ReceiptOrderResult findById(Integer id) throws Exception {
		final ReceiptOrderResult result = new ReceiptOrderResult(null, null);
		try {
			result.setDtoEntity(findDTOById(id));
			result.setResultStatusSuccess();
			result.addToMessageList("Receipt Order Found.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToMessageList("Error Occurred! Receipt order NOT Found.".concat(ex.getMessage()));
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ReceiptOrderResult update(ReceiptOrderDTO dto) {
		final ReceiptOrderResult result = new ReceiptOrderResult(null, dto);
		try {
			result.setDomainEntity(findEntityById(dto.getId()));
			saveOrUpdate(result);
			result.addToMessageList("Receipt Order Updated Successfully.");
		} catch (final ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Receipt Order Already updated. Please Reload. ".concat(e.getMessage()));
		} catch (final Exception e) {
			result.setResultStatusError();
			result.addToErrorList(e.getMessage());
			result.addToMessageList("Receipt Order Update Unsuccessful. ".concat(e.getMessage()));
		}
		return result;
	}

	@Override
	public ReceiptOrderResult save(ReceiptOrderDTO dto) {
		final ReceiptOrderResult result = new ReceiptOrderResult(new ReceiptOrder(), dto);
		try {
			saveOrUpdate(result);
			result.setResultStatusSuccess();
			result.addToMessageList("Receipt Order Added Successfully.");
		} catch (final Exception e) {
			result.setResultStatusError();
			result.addToErrorList(e.getMessage());
			result.addToMessageList("Receipt order save Unsuccessful. ".concat(e.getMessage()));
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(ReceiptOrderResult result) throws Exception {
		ReceiptOrderMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setReceiptOrderData(result);
		receiptOrderDao.save(result.getDomainEntity());
		result.setDtoEntity(findDTOById(result.getDomainEntity().getId()));
	}

	private void setReceiptOrderData(ReceiptOrderResult result) throws Exception {
		setSupplier(result);
		setBusiness(result);
		setItems(result);
		// recievedStock(result);
	}

	private void setSupplier(ReceiptOrderResult result) throws Exception {
		if (result.getDtoEntity().getSupplierId() != null && result.getDtoEntity().getSupplierId() > 0) {
			result.getDomainEntity().setSupplier(supplierDao.findOne(result.getDtoEntity().getSupplierId()));
		}
	}

	private void setBusiness(ReceiptOrderResult result) throws Exception {
		if (result.getDtoEntity().getBusinessId() != null && result.getDtoEntity().getBusinessId() > 0) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}

	private void setItems(ReceiptOrderResult result) throws Exception {
		final Set<ReceiptOrderItem> items = new HashSet<>();
		if (result.getDtoEntity().getItems() != null && result.getDtoEntity().getItems().size() > 0) {
			final Set<ReceiptOrderItem> currentItems = result.getDomainEntity().getReceiptOrderItems();
			for (final ReceiptOrderItemDTO itemDTO : result.getDtoEntity().getItems()) {
				ReceiptOrderItem item;
				if (currentItems != null && currentItems.size() > 0) {
					final Optional<ReceiptOrderItem> optionalItem = currentItems.stream()
							.filter((x) -> x.getId() == itemDTO.getItemId()).findAny();
					if (optionalItem.isPresent()) {
						item = optionalItem.get();
					} else {
						item = new ReceiptOrderItem();
					}
				} else {
					item = new ReceiptOrderItem();
				}
				createReceiptOrderItem(itemDTO, item, result.getDomainEntity());
				items.add(item);
			}
		}

		result.getDomainEntity().setReceiptOrderItems(items);
	}

	private void createReceiptOrderItem(ReceiptOrderItemDTO dto, ReceiptOrderItem domain, ReceiptOrder receiptOrder)
			throws Exception {
		ReceiptOrderItemMapper.getInstance().dtoToDomain(dto, domain);
		domain.setReceiptOrder(receiptOrder);
		if (dto.getItemAssetId() != null && dto.getItemAssetId() > 0) {
			domain.setAsset(assetDao.findOne(dto.getItemAssetId()));
		}
		if (dto.getItemStockId() != null && dto.getItemStockId() > 0) {
			domain.setStock(stockDao.findOne(dto.getItemStockId()));
		}
		if (dto.getPoItemId() != null && dto.getPoItemId() > 0) {
			domain.setPurchaseOrderItem(purchaseOrderItemDao.findOne(dto.getPoItemId()));
		}
		if (dto.getIssueNoteitemId() != null && dto.getIssueNoteitemId() > 0) {
			domain.setIssueNoteItem(aodItemDao.findOne(dto.getIssueNoteitemId()));
		}

		if (receiptOrder.getReceiptOrderStatus().equals(ReceiptOrderStatus.RECEIVED)) {
			updateStock(domain);
		}
	}

	private void updateStock(ReceiptOrderItem receiptOrderItem) {

		final Stock stock=receiptOrderItem.getStock();
		try {
			final StockDTO stockDTO=StockMapper.getInstance().domainToDto(stock);
			final BigDecimal qty=stockDTO.getQtyOnHand().add(receiptOrderItem.getQuantityReceived());
			stockDTO.setQtyOnHand(qty);
			stockService.save(stockDTO);

		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addStockHistory(receiptOrderItem);
	}

	private void addStockHistory(ReceiptOrderItem domain) {
		final StockHistory stockHitory = new StockHistory();
		stockHitory.setBeforeQuantity(BigDecimal.ZERO);
		stockHitory.setAfterQuantity(domain.getQuantityReceived());
		stockHitory.setQuantity(domain.getQuantityReceived());
		stockHitory.setLastPrice(domain.getUnitPrice());
		stockHitory.setStock(domain.getStock());
		stockHitory.setDescription("Item recived to stock");
		stockHitory.setReceiptOrderItem(domain);
		stockHitory.setDate(new Date());
		stockHitory.setIsDeleted(Boolean.FALSE);
		stockHistoryDao.save(stockHitory);
	}

	/*
	 * Delete Object
	 */

	@Override
	public ReceiptOrderResult delete(Integer id) {
		final ReceiptOrderResult result = new ReceiptOrderResult(null, null);
		try {
			deleteEntityById(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Receipt Order Deleted Successfully.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToMessageList("Receipt Order Deleted Unsuccessfully. ".concat(ex.getMessage()));
		}
		return result;
	}

	@Override
	public ReceiptOrderResult deleteMultiple(Integer[] ids) throws Exception {
		final ReceiptOrderResult result = new ReceiptOrderResult(null, null);
		try {
			for (final Integer id : ids) {
				deleteEntityById(id);
			}
			result.setResultStatusSuccess();
			result.addToMessageList("Receipt Order(s) Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Receipt Order(s) Already Used. Cannot delete.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void deleteEntityById(Integer id) throws Exception {
		receiptOrderDao.delete(id);
	}

	@Override
	public ReceiptOrderResult statusChange(Integer id, ReceiptOrderStatus receiptOrderStatus) {

		final ReceiptOrderResult result = new ReceiptOrderResult(null, null);
		try {

			final ReceiptOrderDTO dto = findDTOById(id);
			dto.setReceiptOrderStatus(receiptOrderStatus);
			final ReceiptOrder domain = findEntityById(dto.getId());
			final String previousStatus = domain.getReceiptOrderStatus().getName();
			result.setDtoEntity(dto);
			result.setDomainEntity(domain);
			saveOrUpdate(result);
			result.setResultStatusSuccess();
			result.addToMessageList("Receipt order Status Updated Successfully. " + previousStatus + " --> "
					+ receiptOrderStatus.getName());
		} catch (final ObjectOptimisticLockingFailureException ex) {
			result.setResultStatusError();
			result.addToErrorList("Receipt order Already updated. Please Reload Receipt order.");
		} catch (final Exception e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(e.getMessage());
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private ReceiptOrderResult statusChange(ReceiptOrderStatus receiptOrderStatus) throws Exception {
		return null;

		/*
		 * ReceiptOrderResult result = new ReceiptOrderResult(null, null); try {
		 * ReceiptOrderDTO dto=findDTOById(id);
		 * dto.setReceiptOrderStatus(receiptOrderStatus); ReceiptOrder domain =
		 * findEntityById(dto.getId()); String
		 * previousStatus=domain.getReceiptOrderStatus().getName();
		 * result.setDtoEntity(dto); result.setDomainEntity(domain);
		 * saveOrUpdate(result);
		 * result.addToMessageList("Receipt order Status Updated Successfully. "
		 * +previousStatus+" --> "+ receiptOrderStatus.getName()); } catch
		 * (ObjectOptimisticLockingFailureException ex) {
		 * result.setResultStatusError(); result.
		 * addToErrorList("Receipt order Already updated. Please Reload Receipt order."
		 * ); } catch (Exception e) { e.printStackTrace();
		 * result.setResultStatusError(); result.addToErrorList(e.getMessage());
		 * } return result;
		 */

		// sendStatusChangeEmail(previousStatus,currentStatus);
	}

	private void sendStatusChangeEmail(ReceiptOrderStatus previousStatus, ReceiptOrderStatus currentStatus) {
		final VelocityMail velocityMail = new VelocityMail();
		velocityMail.getModel().put("user", "wasantha");
		velocityMail.getModel().put("priviousreceiptOrderStatus", previousStatus.getName());
		velocityMail.getModel().put("currentreceiptOrderstatus", currentStatus.getName());
		velocityMail.setSubject("Receipt order status change");
		velocityMail.setTo("wasanthabr93@gmail.com");
		velocityMail.setVmTemplate("receiptstatuschange");
		velocityEmailService.sendEmail(velocityMail);
	}

	@Override
	public DataTablesOutput<ReceiptOrderDTO> findAll(FocusDataTablesInput input) throws Exception {
		ReceiptOrderPropertyMapper.getInstance().generateDataTableInput(input);
		DataTablesOutput<ReceiptOrder> domainOut;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			domainOut = receiptOrderDao.findAll(input);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			final Specification<ReceiptOrder> specification = (root, query, cb) -> cb.equal(root.get("business"),
					AuthenticationUtil.getLoginUserBusiness());
			domainOut = receiptOrderDao.findAll(input, specification);
		} else {
			final Specification<ReceiptOrder> specification = (root, query, cb) -> cb.and(
					cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness()),
					cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite()));
			domainOut = receiptOrderDao.findAll(input, specification);
		}
		final DataTablesOutput<ReceiptOrderDTO> out = ReceiptOrderMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	@Override
	public ReceiptOrderResult generateGrnFromPo(String idStr, Integer poId) {

		final ReceiptOrderResult result = new ReceiptOrderResult(null, null);
		final ReceiptOrder receiptOrder = receiptOrderDao.findOne(poId);
		final ReceiptOrderDTO receiptOrderDTO = new ReceiptOrderDTO();
		receiptOrderDTO.setReceiptOrderStatus(ReceiptOrderStatus.DRAFT);
		receiptOrderDTO.setCode("");
		receiptOrderDTO.setIsDeleted(Boolean.FALSE);
		if (receiptOrder != null && receiptOrder.getBusiness() != null) {
			receiptOrderDTO.setBusinessId(receiptOrder.getBusiness().getId());
		}
		if (receiptOrder != null && receiptOrder.getSite() != null) {
			// receiptOrderDTO.setS(receiptOrder.getSite().getId());
		}

		final List<ReceiptOrderItemDTO> receiptOrderItemDTOs = new ArrayList<>();
		/*
		 * List<Integer> ids =
		 * Arrays.asList(idStr.split(",")).stream().map(Integer::parseInt).
		 * collect(Collectors.toList()); for (Integer id : ids) {
		 * PurchaseOrderItem item=purchaseOrderItemDao.findOne(id);
		 * ReceiptOrderItemDTO itemDTO=new ReceiptOrderItemDTO();
		 * receiptOrderItemDTOs.add(itemDTO); }
		 */
		receiptOrderDTO.setItems(receiptOrderItemDTOs);
		return null;
	}

	@Override
	public String getNextCode(Integer businessId) {
		if (businessId != null) {
			final ReceiptOrder lastDomain = receiptOrderDao.findLastDomainByBusiness(businessId);
			return UniqueCodeUtil.getNextCode(AffixList.RECEIPTORDER, lastDomain == null ? "" : lastDomain.getCode());
		} else {
			return "";
		}
	}

	@Override
	public ReceiptOrderResult createNewReceiptOrder() {
		final ReceiptOrderResult result = new ReceiptOrderResult(null, null);
		try {
			final ReceiptOrderDTO dto = new ReceiptOrderDTO();
			if (!AuthenticationUtil.isAuthUserAdminLevel()) {
				dto.setCode(getNextCode(AuthenticationUtil.getLoginUserBusiness().getId()));
			} else {
				dto.setCode("");
			}
			result.setDtoEntity(dto);
			result.setResultStatusSuccess();
			result.addToMessageList("Receipt Order generate successfully.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList("ERROR! Receipt Order not generated! ".concat(ex.getMessage()));
		}
		return result;
	}

}
