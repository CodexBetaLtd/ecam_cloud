package com.neolith.focus.service.inventory.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.neolith.focus.constants.inventory.ReceiptOrderStatus;
import com.neolith.focus.dao.asset.AssetDao;
import com.neolith.focus.dao.inventory.ReceiptOrderDao;
import com.neolith.focus.dao.inventory.StockDao;
import com.neolith.focus.dto.inventory.receiptOrder.ReceiptOrderDTO;
import com.neolith.focus.dto.inventory.receiptOrder.ReceiptOrderItemDTO;
import com.neolith.focus.mappers.purchasing.ReceiptOrderItemMapper;
import com.neolith.focus.mappers.purchasing.ReceiptOrderMapper;
import com.neolith.focus.model.inventory.receiptOrder.ReceiptOrder;
import com.neolith.focus.model.inventory.receiptOrder.ReceiptOrderItem;
import com.neolith.focus.params.VelocityMail;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.purchasing.ReceiptOrderResult;
import com.neolith.focus.service.inventory.api.ReceiptOrderService;
import com.neolith.focus.util.AuthenticationUtil;
import com.neolith.focus.util.VelocityEmailSender;
import com.neolith.focus.util.search.inventory.receiptOrder.ReceiptOrderPropertyMapper;

@Service
public class ReceiptOrderServiceImpl implements ReceiptOrderService {

	@Autowired
	private ReceiptOrderDao receiptOrderDao;

	@Autowired
	private AssetDao assetDao;

	@Autowired
	private StockDao stockDao;

	@Autowired
	private VelocityEmailSender velocityEmailService;


	private ReceiptOrderDTO findDTOById(Integer id) throws Exception {
		return ReceiptOrderMapper.getInstance().domainToDto(findEntityById(id));
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private ReceiptOrder findEntityById(Integer id) throws Exception {
		return receiptOrderDao.findOne(id);
	}


	@Override
	public ReceiptOrderResult findById(Integer id) throws Exception {
		ReceiptOrderResult result = new ReceiptOrderResult(null, null);
		try {
			result.setDtoEntity(findDTOById(id));
			result.setResultStatusSuccess();
			result.addToMessageList("Receipt Order Found.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToMessageList("Error Occurred! Receipt order NOT Found.".concat(ex.getMessage()));
		}
		return result;
	}


	@Override
	public ReceiptOrderResult update(ReceiptOrderDTO dto) {
		ReceiptOrderResult result = new ReceiptOrderResult(new ReceiptOrder(), dto);
		try {
			result.setDomainEntity(findEntityById(dto.getId()));
			saveOrUpdate(result);
			result.addToMessageList("Receipt Order Updated Successfully.");
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Receipt Order Already updated. Please Reload. ".concat(e.getMessage()));
		} catch (Exception e) {
			result.setResultStatusError();
			result.addToErrorList(e.getMessage());
			result.addToMessageList("Receipt Order Update Unsuccessful. ".concat(e.getMessage()));
		}
		return result;
	}

	@Override
	public ReceiptOrderResult save(ReceiptOrderDTO dto) {
		ReceiptOrderResult result = new ReceiptOrderResult(null, null);
		try {
			saveOrUpdate(result);
			result.setResultStatusSuccess();
			result.addToMessageList("Receipt Order Added Successfully.");
		} catch (Exception e) {
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
		setItems(result);
	}

	private void setSupplier(ReceiptOrderResult result) throws Exception {
		if ( (result.getDtoEntity().getSupplierId() != null) && (result.getDtoEntity().getSupplierId() > 0) ) {
			//			result.getDomainEntity().setSupplier(supplierDao.findOne(result.getDtoEntity().getSupplierId()));
		}
	}

	private void setItems(ReceiptOrderResult result) throws Exception {
		Set<ReceiptOrderItem> items = new HashSet<>();
		if ((result.getDtoEntity().getItems() != null) && (result.getDtoEntity().getItems().size() > 0)) {
			Set<ReceiptOrderItem> currentItems = result.getDomainEntity().getReceiptOrderItems();
			for (ReceiptOrderItemDTO itemDTO : result.getDtoEntity().getItems()) {
				ReceiptOrderItem item;
				if ( (currentItems != null) && (currentItems.size() > 0) ) {
					Optional<ReceiptOrderItem> optionalItem = currentItems.stream().filter((x) -> x.getId() == itemDTO.getItemId()).findAny();
					if ( optionalItem.isPresent() ) {
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

	private void createReceiptOrderItem(ReceiptOrderItemDTO dto, ReceiptOrderItem domain, ReceiptOrder receiptOrder) throws Exception {
		ReceiptOrderItemMapper.getInstance().dtoToDomain(dto, domain);
		domain.setReceiptOrder(receiptOrder);
		if ( (dto.getItemAssetId() != null) && (dto.getItemAssetId() > 0)) {
			domain.setAsset(assetDao.findOne(dto.getItemAssetId()));
		}
		if ( (dto.getItemStockId() != null) && (dto.getItemStockId() > 0) ) {
			domain.setStock(stockDao.findOne(dto.getItemStockId()));
		}
	}






	/*
	 * Delete Object
	 * */

	@Override
	public ReceiptOrderResult delete(Integer id) {
		ReceiptOrderResult result = new ReceiptOrderResult(null, null);
		try {
			deleteEntityById(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Receipt Order Deleted Successfully.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToMessageList("Receipt Order Deleted Unsuccessfully. ".concat(ex.getMessage()));
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void deleteEntityById(Integer id) throws Exception {
		receiptOrderDao.delete(id);
	}





	@Override
	public ReceiptOrderResult statusChange(Integer id, ReceiptOrderStatus receiptOrderStatus) {
		ReceiptOrderResult result = new ReceiptOrderResult(null, null);
		try {
			result.setDtoEntity(findDTOById(id));
			statusChange(result, receiptOrderStatus);
			result.setResultStatusSuccess();
			result.addToMessageList("Receipt Order Status Change Successful.");
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToMessageList("Receipt Order Status Change Unsuccessful. ".concat(e.getMessage()));
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void statusChange(ReceiptOrderResult result, ReceiptOrderStatus receiptOrderStatus) throws Exception {
		ReceiptOrderStatus previousStatus = result.getDtoEntity().getReceiptOrderStatus();
		result.getDtoEntity().setReceiptOrderStatus(receiptOrderStatus);
		ReceiptOrderStatus currentStatus = result.getDtoEntity().getReceiptOrderStatus();
		update(result.getDtoEntity());
		sendStatusChangeEmail(previousStatus,currentStatus);
	}

	private void sendStatusChangeEmail(ReceiptOrderStatus previousStatus, ReceiptOrderStatus currentStatus) {
		VelocityMail velocityMail = new VelocityMail();
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
			Specification<ReceiptOrder> specification = (root, query, cb) -> cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
			domainOut = receiptOrderDao.findAll(input, specification);
		} else {
			Specification<ReceiptOrder> specification = (root, query, cb) -> cb.and(
					cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness()),
					cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite())
					);
			domainOut = receiptOrderDao.findAll(input, specification);
		}
		DataTablesOutput<ReceiptOrderDTO> out = ReceiptOrderMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}


}

