package com.codex.ecam.service.inventory.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.constants.inventory.PartType;
import com.codex.ecam.constants.inventory.StockType;
import com.codex.ecam.constants.util.AffixList;
import com.codex.ecam.dao.admin.TaxDao;
import com.codex.ecam.dao.admin.UserDao;
import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dao.inventory.AODItemDao;
import com.codex.ecam.dao.inventory.AODReturnItemDao;
import com.codex.ecam.dao.inventory.ReceiptOrderItemDao;
import com.codex.ecam.dao.inventory.StockAdjustmentDao;
import com.codex.ecam.dao.inventory.StockDao;
import com.codex.ecam.dao.inventory.StockHistoryDao;
import com.codex.ecam.dto.inventory.stock.StockDTO;
import com.codex.ecam.dto.inventory.stock.StockHistoryDTO;
import com.codex.ecam.dto.inventory.stock.StockNotificationDTO;
import com.codex.ecam.dto.inventory.stock.StockViewFilterDTO;
import com.codex.ecam.dto.inventory.stockAdjuestment.StockAdjustmentDTO;
import com.codex.ecam.exception.inventory.stock.StockException;
import com.codex.ecam.exception.inventory.stock.StockQuantityExceedException;
import com.codex.ecam.mappers.inventory.stock.StockHistoryMapper;
import com.codex.ecam.mappers.inventory.stock.StockMapper;
import com.codex.ecam.mappers.inventory.stock.StockNotificationMapper;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.asset.AssetConsumingReference;
import com.codex.ecam.model.biz.part.AvgPrice;
import com.codex.ecam.model.inventory.aod.AOD;
import com.codex.ecam.model.inventory.aod.AODItem;
import com.codex.ecam.model.inventory.aod.AODItemStock;
import com.codex.ecam.model.inventory.aodRetun.AODReturnItem;
import com.codex.ecam.model.inventory.receiptOrder.ReceiptOrderItem;
import com.codex.ecam.model.inventory.receiptOrder.ReceiptOrderTax;
import com.codex.ecam.model.inventory.stock.Stock;
import com.codex.ecam.model.inventory.stock.StockHistory;
import com.codex.ecam.model.inventory.stock.StockNotification;
import com.codex.ecam.model.inventory.stockAdjustment.StockAdjustment;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.inventory.StockResult;
import com.codex.ecam.service.inventory.api.StockService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.CommonUtil;
import com.codex.ecam.util.search.inventory.stock.StockPartPropertyMapper;
import com.codex.ecam.util.search.inventory.stock.StockPropertyMapper;
import com.codex.ecam.util.search.inventory.stock.StockWarehousePropertyMapper;

@Service
public class StockServiceImpl implements StockService {

	private final static Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

	@Autowired
	private StockDao stockDao;

	@Autowired
	private StockHistoryDao stockHistoryDao;

	@Autowired
	private AssetDao assetDao;

	@Autowired
	private ReceiptOrderItemDao receiptOrderItemDao;

	@Autowired
	private AODItemDao aodItemDao;

	@Autowired
	private AODReturnItemDao aodReturnItemDao;

	@Autowired
	private BusinessDao businessDao;

	@Autowired
	private StockAdjustmentDao stockAdjustmentDao;

	@Autowired
	private TaxDao taxDao;

	@Autowired
	private UserDao userDao;

	/*
	 *
	 * @Override public StockDTO findById(Integer id) throws Exception { try { Stock
	 * stock = stockDao.findOne(id); return
	 * StockMapper.getInstance().domainToDto(stock); } catch (Exception ex) {
	 * ex.printStackTrace(); return new StockDTO(); } }
	 *
	 * @Override public DataTablesOutput<StockDTO> findStockByPart(DataTablesInput
	 * input, Integer partId) throws Exception { DataTablesOutput<Stock> domainOut;
	 * if (AuthenticationUtil.isAuthUserAdminLevel()) { Specification<Stock>
	 * specification = new Specification<Stock>() {
	 *
	 * @Override public Predicate toPredicate(Root<Stock> root, CriteriaQuery<?>
	 * query, CriteriaBuilder cb) { return
	 * cb.equal(root.get("assetBusiness").get("asset").get("id"), partId); } };
	 * domainOut = stockDao.findAll(input, specification); } else if
	 * (AuthenticationUtil.isAuthUserSystemLevel()) { Specification<Stock>
	 * specification = new Specification<Stock>() {
	 *
	 * @Override public Predicate toPredicate(Root<Stock> root, CriteriaQuery<?>
	 * query, CriteriaBuilder cb) { return
	 * cb.and(cb.equal(root.get("assetBusiness").get("asset").get("id"), partId),
	 * cb.equal(root.get("business"),AuthenticationUtil.getLoginUserBusiness())); }
	 * }; domainOut = stockDao.findAll(input, specification); } else {
	 * Specification<Stock> specification = new Specification<Stock>() {
	 *
	 * @Override public Predicate toPredicate(Root<Stock> root, CriteriaQuery<?>
	 * query, CriteriaBuilder cb) { return
	 * cb.and(cb.equal(root.get("assetBusiness").get("asset").get("id"), partId),
	 * cb.equal(root.get("business"),
	 * AuthenticationUtil.getLoginSite().getSite().getAssetBusinesses())); } };
	 * domainOut = stockDao.findAll(input, specification); }
	 * DataTablesOutput<StockDTO> out =
	 * StockMapper.getInstance().domainToDTODataTablesOutput(domainOut);
	 *
	 * return out; }
	 *
	 * @Override public DataTablesOutput<StockDTO> findStockByAsset(DataTablesInput
	 * input, Integer assetId) throws Exception { DataTablesOutput<Stock> domainOut;
	 * Specification<Stock> specification; if
	 * (AuthenticationUtil.isAuthUserAdminLevel()) { specification = new
	 * Specification<Stock>() {
	 *
	 * @Override public Predicate toPredicate(Root<Stock> root, CriteriaQuery<?>
	 * query, CriteriaBuilder cb) { Join<Stock, AssetBusiness> joinAssetBussiness =
	 * root.join("assetBusiness"); Join<AssetBusiness, Asset> joinAsset =
	 * joinAssetBussiness.join("asset"); Join<Asset, AssetConsumingReference>
	 * joinAssetConsumeRef = joinAsset.join("assetConsumingReferences");
	 *
	 * query.groupBy(root.get("id"));
	 *
	 * return cb.equal(joinAssetConsumeRef.get("asset").get("id"), assetId); } }; }
	 * else if (AuthenticationUtil.isAuthUserSystemLevel()) { specification = new
	 * Specification<Stock>() {
	 *
	 * @Override public Predicate toPredicate(Root<Stock> root, CriteriaQuery<?>
	 * query, CriteriaBuilder cb) {
	 *
	 * Join<Stock, AssetBusiness> joinAssetBussiness = root.join("assetBusiness");
	 * Join<AssetBusiness, Asset> joinAsset = joinAssetBussiness.join("asset");
	 * Join<Asset, AssetConsumingReference> joinAssetConsumeRef =
	 * joinAsset.join("assetConsumingReferences");
	 *
	 * query.groupBy(root.get("id"));
	 *
	 * return cb.and(cb.equal(joinAssetConsumeRef.get("asset").get("id"), assetId),
	 * cb.equal(root.get("assetBusiness").get("business"),
	 * AuthenticationUtil.getLoginUserBusiness())); } }; } else { specification =
	 * new Specification<Stock>() {
	 *
	 * @Override public Predicate toPredicate(Root<Stock> root, CriteriaQuery<?>
	 * query, CriteriaBuilder cb) {
	 *
	 * Join<Stock, AssetBusiness> joinAssetBussiness = root.join("assetBusiness");
	 * Join<AssetBusiness, Asset> joinAsset = joinAssetBussiness.join("asset");
	 * Join<Asset, AssetConsumingReference> joinAssetConsumeRef =
	 * joinAsset.join("assetConsumingReferences");
	 *
	 * query.groupBy(root.get("id"));
	 *
	 * return cb.and(cb.equal(joinAssetConsumeRef.get("asset").get("id"), assetId),
	 * cb.equal(root.get("assetBusiness").get("business"),
	 * AuthenticationUtil.getLoginSite().getSite().getAssetBusinesses())); } };
	 *
	 * } domainOut = stockDao.findAll(input, specification);
	 * DataTablesOutput<StockDTO> out =
	 * StockMapper.getInstance().domainToDTODataTablesOutput(domainOut); return out;
	 * }
	 *
	 *
	 */

//	private void changeFIFOStock(Integer partId, BigDecimal qty) {
//		StockDTO dto=getFIFOStock(partId);
//		BigDecimal remainQuantity=BigDecimal.ZERO;
//		dto.setQtyMovement(qty);
//		remainQuantity=(dto.getQtyOnHand().subtract(qty));
//		dto.setQtyOnHand(remainQuantity);
//		save(dto);
//	}

//	private StockDTO getFIFOStock(Integer partId){
//		Stock stock=stockDao.findStockByFIFO(partId);
//		try {
//			return StockMapper.getInstance().domainToDto(stock);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//		
//	}

	/*****************************************************************************************
	 * ************************************************************************************
	 *****************************************************************************************/

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public StockResult stockReceived(ReceiptOrderItem receiptOrderItem, BigDecimal subTotal,
			List<ReceiptOrderTax> receiptOrderTaxes) {
		StockResult result = new StockResult(new Stock(), new StockDTO());
		result.setDomainEntity(new Stock());
		try {
			setStockDomainData(result, receiptOrderItem, subTotal, receiptOrderTaxes);
			stockDao.save(result.getDomainEntity());
			updateItemAVGPrice(result.getDomainEntity());
			result.addToMessageList("Stock Added Successfully.");
			result.setResultStatusSuccess();
		} catch (Exception ex) {
			result.setResultStatusError();
			result.addToErrorList("Error! Stock NOT Added");
			ex.printStackTrace();
		}
		return result;
	}

	private void setStockDomainData(StockResult result, ReceiptOrderItem receiptOrderItem, BigDecimal subTotal,
			List<ReceiptOrderTax> receiptOrderTaxes) throws Exception {
		setBusinessSite(result.getDomainEntity(), receiptOrderItem);
		setReceiptOrderToStock(result.getDomainEntity(), receiptOrderItem);
		setStockUnitCost(receiptOrderItem, result.getDomainEntity(), subTotal,
				receiptOrderTaxes); /* Set Stock Unit Price */
		setAVGPrice(receiptOrderItem.getAsset(), result.getDomainEntity()); /* Set AVG Price */
		setStockHistory(result.getDomainEntity());
		if ((result.getDomainEntity() != null) && (result.getDomainEntity().getId() == null)) {
			setNextCode(result.getDomainEntity());
		}
	}

	private void setBusinessSite(Stock stock, ReceiptOrderItem receiptOrderItem) {
		if ((receiptOrderItem.getReceiptOrder() != null) && (receiptOrderItem.getReceiptOrder().getBusiness() != null)
				&& (receiptOrderItem.getReceiptOrder().getBusiness().getId() != null)
				&& (receiptOrderItem.getReceiptOrder().getBusiness().getId() > 0)) {
			stock.setBusiness(businessDao.findOne(receiptOrderItem.getReceiptOrder().getBusiness().getId()));
		} else {
			stock.setBusiness(AuthenticationUtil.getLoginUserBusiness());
		}
		if ((receiptOrderItem.getReceiptOrder() != null) && (receiptOrderItem.getReceiptOrder().getSite() != null)
				&& (receiptOrderItem.getReceiptOrder().getSite().getId() != null)
				&& (receiptOrderItem.getReceiptOrder().getSite().getId() > 0)) {
			stock.setSite(assetDao.findOne(receiptOrderItem.getReceiptOrder().getSite().getId()));
		} else if (!AuthenticationUtil.isAuthUserAdminLevel()) {
			stock.setSite(AuthenticationUtil.getLoginSite().getSite());
		}
	}

	private void setReceiptOrderToStock(Stock stock, ReceiptOrderItem receiptOrderItem) throws Exception {
		try {
			if (receiptOrderItem.getAsset() != null) {
				stock.setPart(receiptOrderItem.getAsset());
			}
			if (receiptOrderItem.getWarehouse() != null) {
				stock.setWarehouse(receiptOrderItem.getWarehouse());
			}
			stock.setReceiptOrderItem(receiptOrderItem);
			stock.setCurrentQuantity(
					receiptOrderItem.getQuantityReceived() != null ? receiptOrderItem.getQuantityReceived()
							: BigDecimal.ZERO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// TODO: Check again
	private void setStockUnitCost(ReceiptOrderItem receiptOrderItem, Stock stock, BigDecimal subTotal,
			List<ReceiptOrderTax> receiptOrderTaxes) {
		/*
		 * if ((receiptOrderTaxes != null) && (receiptOrderTaxes.size() > 0)) {
		 * ReceiptOrderTax tax = new ReceiptOrderTax(); Optional<ReceiptOrderTax>
		 * optional = receiptOrderTaxes.stream() .filter((x) ->
		 * x.getTax().getId().equals(TAXType.NBT.getId())).findAny(); if
		 * (optional.isPresent()) { tax = optional.get(); } else { // Todo: Get the
		 * current NBT Tax //taxDao.findLastTaxByTaxType(TAXType.NBT); } if ((tax !=
		 * null) && (tax.getId() != null)) { // TODO: Round Here
		 * stock.setUnitPrice(receiptOrderItem.getUnitPrice()
		 * .add((tax.getTaxAmount().multiply(receiptOrderItem.getUnitPrice())).divide(
		 * subTotal))); } }
		 */
	}

	private void setAVGPrice(Asset part, Stock stock) {
		stock.setAvgPrice(getAVGPrice(part.getId()));
	}

	private void setStockHistory(Stock stock) {
		Set<StockHistory> list = new HashSet<>();
		StockHistory stockHistory = new StockHistory();
		stockHistory.setStock(stock);
		stockHistory.setDescription("[STOCK ADDED] ");
		stockHistory.setQuantity(stock.getCurrentQuantity());
		stockHistory.setBeforeQuantity(BigDecimal.ZERO);
		stockHistory.setAfterQuantity(stock.getCurrentQuantity());
		stockHistory.setDate(new Date());
		stockHistory.setLastPrice(stock.getUnitPrice());
		list.add(stockHistory);
		stock.setStockHistoryList(list);
	}

	private void setNextCode(Stock domain) {
		Integer year = Calendar.getInstance().get(Calendar.YEAR);
		Stock lastDomain = stockDao.findLastDomain();
		Integer nextNo = 0;
		if ((lastDomain == null) || (lastDomain.getId() == null)) {
			nextNo = 1;
		} else {
			List<String> codeList = Arrays.asList(lastDomain.getStockNo().split("/"));
			if (!codeList.get(0).equalsIgnoreCase(AffixList.STOCK.getCode())) {
				nextNo = 1;
			} else if (Integer.parseInt(codeList.get(1)) == year) {
				nextNo = Integer.parseInt(codeList.get(2)) + 1;
			} else {
				nextNo = 1;
			}
		}
		domain.setStockNo(CommonUtil.setNextCode(AffixList.STOCK.getCode(), nextNo.toString()));
	}

	private void updateItemAVGPrice(Stock stock) throws Exception {
		if ((stock.getPart() != null) && (stock.getPart().getId() != null)) {
			updateAVGPrice(stock.getPart().getId());
		}
	}

	/****************************************
	 * Stock Dispatch
	 * 
	 * @return
	 *****************************************/

	private StockResult dispatchStockByItemWithoutFIFO(StockResult result, AODItem aodItem) throws Exception {
		setStockData(result, aodItem);
		stockDao.save(result.getDomainEntity());
		result.setResultStatusSuccess();
		return result;
	}

	private void setStockData(StockResult result, AODItem aodItem) throws StockQuantityExceedException {
		if (aodItem.getStock() != null) {
			Stock stock = stockDao.findOne(aodItem.getStock().getId());

			if (stock.getCurrentQuantity().compareTo(aodItem.getQuantity()) == 0
					|| stock.getCurrentQuantity().compareTo(aodItem.getQuantity()) == 1) {
				BigDecimal remainingQty = (stock.getCurrentQuantity().subtract(aodItem.getQuantity()));
				stock.setCurrentQuantity(remainingQty);
			} else {
				result.setResultStatusError();
				throw new StockQuantityExceedException(
						"Stock has not required quantity for AOD item :- " + aodItem.getPart().getName() + " .");
			}
			result.setDomainEntity(stock);
		}
	}

	@Override
	public StockResult dispatchStock(final AOD aod) throws Exception {
		final StockResult result = new StockResult(null, null);
		if (aod.getAodItemList() != null && aod.getAodItemList().size() > 0) {
			for (final AODItem aodItem : aod.getAodItemList()) {
				if (aod.getBusiness().getIsFIFO()) {
					dispatchStockByItemWithFIFO(result, aodItem);
				} else {
					dispatchStockByItemWithoutFIFO(result, aodItem);
				}
				addStockHistory(aodItem);
			}
		}
		return result;
	}

	private void addStockHistory(AODItem domain) {
		StockHistory stockHitory = new StockHistory();
		stockHitory.setBeforeQuantity(domain.getStock().getCurrentQuantity());
		stockHitory.setAfterQuantity(domain.getStock().getLastQuantity().subtract(domain.getQuantity()));
		stockHitory.setQuantity(domain.getQuantity());
		stockHitory.setLastPrice(domain.getStock().getUnitPrice());
		stockHitory.setStock(domain.getStock());
		stockHitory.setDescription("Item dispatch from stock");
		stockHitory.setAodItem(domain);
		stockHitory.setDate(new Date());
		stockHitory.setIsDeleted(Boolean.FALSE);
		stockHistoryDao.save(stockHitory);
	}

	// first in first out
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void dispatchStockByItemWithFIFO(final StockResult result, final AODItem aodItem) throws StockException {
		final Set<AODItemStock> aodItemStocks = new HashSet<>();
		BigDecimal itemQty = aodItem.getQuantity();
		Integer itemCount = 0;
		final List<Stock> stockList = stockDao.findNonEmptyStockByItemId(aodItem.getPart().getId());
		if (stockList != null && stockList.size() > 0) {
			for (; itemQty.compareTo(BigDecimal.ZERO) > 0;) {
				final AODItemStock aodItemStock = new AODItemStock();
				BigDecimal tempItemQty = BigDecimal.ZERO;
				if (itemQty.compareTo(BigDecimal.ZERO) > 0
						&& itemQty.compareTo(stockList.get(itemCount).getCurrentQuantity()) >= 0) {
					tempItemQty = stockList.get(itemCount).getCurrentQuantity();
					itemQty = itemQty.subtract(tempItemQty);
					stockList.get(itemCount).setCurrentQuantity(BigDecimal.ZERO);
					addStockHistory(stockList.get(itemCount), stockList.get(itemCount).getCurrentQuantity());
				} else if (itemQty.compareTo(BigDecimal.ZERO) > 0
						&& itemQty.compareTo(stockList.get(itemCount).getCurrentQuantity()) < 0) {
					stockList.get(itemCount)
							.setCurrentQuantity(stockList.get(itemCount).getCurrentQuantity().subtract(itemQty));
					addStockHistory(stockList.get(itemCount), itemQty);
					tempItemQty = itemQty;
					itemQty = BigDecimal.ZERO;
				}
				stockDao.save(stockList.get(itemCount));
				aodItemStock.setAodItem(aodItem);
				aodItemStock.setStock(stockDao.findOne(stockList.get(itemCount).getId()));
				aodItemStock.setQuantity(tempItemQty);
				aodItemStocks.add(aodItemStock);
				itemCount++;
			}
			aodItem.setAodItemStocks(aodItemStocks);
			// aodItemDao.save(aodItem);
		} else {
			throw new StockException(
					"ERROR! STOCK Empty for AOD Item :- ".concat(aodItem.getPart().getName()).concat(" ."));
		}
	}

	private void addStockHistory(final Stock stock, final BigDecimal qty) {
		final StockHistory stockHistory = new StockHistory();
		stockHistory.setDescription("[DISPATCHED] ".concat(stock.getPart().getName()));
		stockHistory.setLastPrice(stock.getUnitPrice());
		addStockHistory(stock, stockHistory, qty);
	}

	private void addStockHistory(final Stock stock, final StockHistory stockHistory, final BigDecimal qty) {
		StockHistory tempLastHistory = new StockHistory();
		stockHistory.setStock(stock);
		stockHistory.setQuantity(qty);
		final Optional<StockHistory> lastObj = stock.getStockHistoryList().stream()
				.reduce((first, second) -> first.getId() > second.getId() ? first : second);
		if (lastObj.isPresent()) {
			tempLastHistory = lastObj.get();
			stockHistory.setBeforeQuantity(tempLastHistory.getAfterQuantity());
		}
		stockHistory.setAfterQuantity(stock.getCurrentQuantity());
		stockHistory.setDate(new Date());
		stock.getStockHistoryList().add(stockHistory);
	}

	/****************************************
	 * Stock Dispatch Return
	 *****************************************/
	// TODO: Not in used
	/*
	 * @Override
	 *
	 * @Transactional(propagation = Propagation.REQUIRED, rollbackFor =
	 * Exception.class) public void dispatchedReturn(AODReturnItem returnItem)
	 * throws Exception { if (returnItem.getAodItem() != null) { if
	 * (returnItem.getAodItem().getStock() != null) { if
	 * (returnItem.getAodItem().getStock().getId() != null) { AODItem aodItem =
	 * aodItemDao.findOne(returnItem.getAodItem().getId()); Stock stock =
	 * stockDao.findOne(returnItem.getAodItem().getStock().getId());
	 * stock.setCurrentQuantity(stock.getCurrentQuantity() +
	 * returnItem.getReturnQty()); setAvgPrice(stock); setDispatchedHistory(stock,
	 * returnItem.getReturnQty()); stockDao.save(stock); } } } }
	 */
	@Override
	public Stock dispatchedReturn(Integer stockId, BigDecimal returnQty) throws Exception {
		Stock stock = stockDao.findOne(stockId);
		stock.setCurrentQuantity(stock.getCurrentQuantity().add(returnQty));
		setAvgPrice(stock);
		setDispatchedHistory(stock, returnQty);
		return stock;
	}

	private void setAvgPrice(Stock stock) throws Exception {
		AvgPrice avgPrice = new AvgPrice();
		avgPrice.setPart(stock.getPart());
		avgPrice.setAvgPrice(getAVGPrice(stock.getPart().getId()));
		// stock.getPart().getAssetAvgPrices().add(avgPrice);
	}

	private void setDispatchedHistory(Stock stock, BigDecimal qty) throws Exception {
		StockHistory stockHistory = new StockHistory();
		stockHistory.setDescription("[DISPATCH RETURN] ".concat(stock.getPart().getName()));
		stockHistory.setLastPrice(stock.getUnitPrice());
		addStockHistory(stock, stockHistory, qty);
	}

	/****************************************
	 * Stock Adjustment
	 *****************************************/
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void adjustStock(StockAdjustmentDTO dto) throws Exception {
		Stock stock = new Stock();
		if ((dto != null) && (dto.getStockId() != null)) {
			stock = stockDao.findOne(dto.getStockId());
		}
		BigDecimal newQty = dto.getNewQuantity();
		BigDecimal oldQty = stock.getCurrentQuantity(); // Todo: there can be some issue regrading with concurrent save
		setStockAdjustment(stock);
		stock.setCurrentQuantity(newQty);
		setStockAdjustmentStockHistory(stock, newQty, oldQty);
		// stock.setAvgPrice(getAVGPrice(stock.getPart().getId()));
		stock.setAvgPrice(BigDecimal.ZERO);
		stockDao.save(stock);
	}

	void setStockAdjustment(Stock stock) {
		StockAdjustment stockAdjustment = null;
		if ((stock != null) && (stock.getStockAdjustmentList() != null)) {
			stock.getStockAdjustmentList().add(stockAdjustment);
		} else if ((stock != null) && (stock.getStockAdjustmentList() == null)) {
			Set<StockAdjustment> list = new HashSet<>();
			list.add(stockAdjustment);
			stock.setStockAdjustmentList(list);
		}
	}

	private void setStockAdjustmentStockHistory(Stock stock, BigDecimal newQty, BigDecimal oldQty) {
		StockHistory stockHistory = new StockHistory();
		stockHistory.setDescription("[ADJUSTMENT] Adjust Stock Item  ".concat(stock.getPart().getName()));
		stockHistory.setLastPrice((stock != null) && (stock.getId() != null) ? stock.getUnitPrice() : BigDecimal.ZERO);
		addStockHistory(stock, stockHistory, (newQty.subtract(oldQty)).abs());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateItemAVGPrice(Integer partId) throws Exception {
		// updateAVGPrice(partId);
	}

	private void updateAVGPrice(Integer partId) throws Exception {
//		BigDecimal avgPrice = getAVGPrice(partId);
//		Asset part = assetDao.findOne(partId);

		/*
		 * part.setAvgPrice(avgPrice); if (part.getAssetAvgPrices() != null) {
		 * part.getAssetAvgPrices().add(setAvgPrice(part, avgPrice)); }
		 */

	}

//	private AvgPrice setAvgPrice(Asset part, BigDecimal price) {
//		AvgPrice avgPrice = new AvgPrice();
//		avgPrice.setAvgPrice(price);
//		avgPrice.setPart(part);
//		return avgPrice;
//	}

	/****************************************
	 * Stock History
	 *****************************************/

//	private void addStockHistory(Stock stock, AODItem aodItem) {
//		addStockHistory(stock, aodItem.getQuantity());
//	}

//	private void addStockHistory(Stock stock, BigDecimal qty) {
//		StockHistory stockHistory = new StockHistory();
//		stockHistory.setDescription("[DISPATCHED] ".concat(stock.getPart().getName()));
//		stockHistory.setLastPrice(stock.getUnitPrice());
//		addStockHistory(stock, stockHistory, qty);
//	}

	@Override
	public StockDTO findById(Integer id) throws Exception {
		try {
			Stock stock = stockDao.findOne(id);
			return StockMapper.getInstance().domainToDto(stock);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new StockDTO();
		}
	}

	@Override
	public StockResult findStockOnHand(Integer partId, Integer warehouseId) throws Exception {
		StockResult result = new StockResult(null, null);
		result.setDtoEntity(new StockDTO());
		try {
			List<Stock> stockList = stockDao.findNonEmptyStockByItemIdAndWarehouse(partId, warehouseId);
			result.getDtoEntity().setQtyOnHand(BigDecimal
					.valueOf(stockList.stream().mapToDouble(stock -> stock.getCurrentQuantity().doubleValue()).sum()));
		} catch (Exception ex) {
			result.setResultStatusError();
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public List<StockDTO> getStockSummary(StockViewFilterDTO stockViewFilterDTO) {
		List<Stock> stocks = new ArrayList<>();
		if ((stockViewFilterDTO.getSiteId() != null) && (stockViewFilterDTO.getItemId() != null)) {
			stocks = stockDao.findStock(stockViewFilterDTO.getSiteId(), stockViewFilterDTO.getItemId());
		} else if (stockViewFilterDTO.getSiteId() != null) {
			stocks = stockDao.findStockBySite(stockViewFilterDTO.getSiteId());
		} else if (stockViewFilterDTO.getItemId() != null) {
			stocks = stockDao.findStockByPart(stockViewFilterDTO.getItemId());
		}
		return stockData(stocks);
	}

	@Override
	public List<StockDTO> findStockByPart(Integer partId) {
		List<Stock> stocks = stockDao.findStockByPart(partId);
		return stockData(stocks);
	}

	protected List<StockDTO> stockData(List<Stock> stocks) {
		List<StockDTO> stockDTOs = new ArrayList<>();
		for (Stock stock : stocks) {
			StockDTO stockDTO = new StockDTO();
			if (stock.getSite() != null) {
				stockDTO.setSite(stock.getSite().getName());
				stockDTO.setSiteId(stock.getSite().getId());
			}
			if (stock.getPart() != null) {
				stockDTO.setPartName(stock.getPart().getName());
				stockDTO.setPartId(stock.getPart().getId());
			}
			if (stock.getReceiptOrderItem() != null) {
				stockDTO.setReceiptOrderId(stock.getReceiptOrderItem().getReceiptOrder().getId());
				stockDTO.setReceiptOrderCode(stock.getReceiptOrderItem().getReceiptOrder().getCode());
				stockDTO.setStockTransactionDescription(
						"GRN Item with no ".concat(stock.getReceiptOrderItem().getReceiptOrder().getCode()));
			}
			stockDTO.setStockNo(stock.getStockNo());
			stockDTO.setQtyOnHand(stock.getCurrentQuantity());
			stockDTO.setId(stock.getId());
			stockDTOs.add(stockDTO);
		}
		return stockDTOs;
	}

	@Override
	public List<StockDTO> getStockDetailList(StockViewFilterDTO stockViewFilterDTO) {
		List<ReceiptOrderItem> stockIn = getStockIn(stockViewFilterDTO);
		List<AODItem> stockOut = getStockOut(stockViewFilterDTO);
		List<AODReturnItem> stockRestore = getStockRestore(stockViewFilterDTO);
		List<StockAdjustment> stockAdjustments = getStockAdjustment(stockViewFilterDTO);
		return stockDetailData(stockIn, stockOut, stockRestore, stockAdjustments);
	}

	private List<ReceiptOrderItem> getStockIn(StockViewFilterDTO stockViewFilterDTO) {
		List<ReceiptOrderItem> stockIn = new ArrayList<>();
		try {
			Specification<ReceiptOrderItem> specification = (root, query, cb) -> {
				List<Predicate> predicates = new ArrayList<>();
				if ((stockViewFilterDTO != null) && (stockViewFilterDTO.getSiteId() != null)) {
					predicates.add(cb.equal(root.get("receiveToFacility").get("id"), stockViewFilterDTO.getSiteId()));
				}
				if ((stockViewFilterDTO != null) && (stockViewFilterDTO.getWarehouseId() != null)) {
					predicates.add(cb.equal(root.get("warehouse").get("id"), stockViewFilterDTO.getWarehouseId()));
				}
				if ((stockViewFilterDTO != null) && (stockViewFilterDTO.getItemId() != null)) {
					predicates.add(cb.equal(root.get("asset").get("id"), stockViewFilterDTO.getItemId()));
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			};
			stockIn = receiptOrderItemDao.findAll(specification);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return stockIn;
	}

	private List<AODItem> getStockOut(StockViewFilterDTO stockViewFilterDTO) {
		List<AODItem> stockOut = new ArrayList<>();
		try {
			Specification<AODItem> specification = (root, query, cb) -> {
				List<Predicate> predicates = new ArrayList<>();
				if ((stockViewFilterDTO != null) && (stockViewFilterDTO.getSiteId() != null)) {
					predicates.add(cb.equal(root.get("site").get("id"), stockViewFilterDTO.getSiteId()));
				}
				if ((stockViewFilterDTO != null) && (stockViewFilterDTO.getWarehouseId() != null)) {
					predicates.add(cb.equal(root.get("warehouse").get("id"), stockViewFilterDTO.getWarehouseId()));
				}
				if ((stockViewFilterDTO != null) && (stockViewFilterDTO.getItemId() != null)) {
					predicates.add(cb.equal(root.get("part").get("id"), stockViewFilterDTO.getItemId()));
				}
				if ((stockViewFilterDTO != null) && (stockViewFilterDTO.getStockNo() != null)
						&& (!stockViewFilterDTO.getStockNo().equalsIgnoreCase("") == Boolean.TRUE)) {
					predicates.add(cb.like(cb.lower(root.get("stock").get("stockNo")),
							"%" + stockViewFilterDTO.getStockNo().toLowerCase() + "%"));
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			};
			stockOut = aodItemDao.findAll(specification);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return stockOut;
	}

	private List<AODReturnItem> getStockRestore(StockViewFilterDTO stockViewFilterDTO) {
		List<AODReturnItem> stockRestore = new ArrayList<>();
		try {
			Specification<AODReturnItem> specification = (root, query, cb) -> {
				List<Predicate> predicates = new ArrayList<>();
				if ((stockViewFilterDTO != null) && (stockViewFilterDTO.getSiteId() != null)) {
					predicates
							.add(cb.equal(root.get("site").get("aodReturn").get("id"), stockViewFilterDTO.getSiteId()));
				}
				if ((stockViewFilterDTO != null) && (stockViewFilterDTO.getWarehouseId() != null)) {
					predicates.add(cb.equal(root.get("aodItem").get("warehouse").get("id"),
							stockViewFilterDTO.getWarehouseId()));
				}
				if ((stockViewFilterDTO != null) && (stockViewFilterDTO.getItemId() != null)) {
					predicates.add(cb.equal(root.get("aodItem").get("part").get("id"), stockViewFilterDTO.getItemId()));
				}
				if ((stockViewFilterDTO != null) && (stockViewFilterDTO.getStockNo() != null)
						&& (!stockViewFilterDTO.getStockNo().equalsIgnoreCase("") == Boolean.TRUE)) {
					predicates.add(cb.like(cb.lower(root.get("aodItem").get("stock").get("stockNo")),
							"%" + stockViewFilterDTO.getStockNo().toLowerCase() + "%"));
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			};
			stockRestore = aodReturnItemDao.findAll(specification);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return stockRestore;
	}

	private List<StockAdjustment> getStockAdjustment(StockViewFilterDTO stockViewFilterDTO) {
		List<StockAdjustment> stockAdjustments = new ArrayList<>();
		try {
			Specification<StockAdjustment> specification = (root, query, cb) -> {
				List<Predicate> predicates = new ArrayList<>();
				/*
				 * if (stockViewFilterDTO != null && stockViewFilterDTO.getSiteId() != null) {
				 * predicates.add(cb.equal(root.get("site").get("id"),
				 * stockViewFilterDTO.getSiteId())); }
				 */
				if ((stockViewFilterDTO != null) && (stockViewFilterDTO.getWarehouseId() != null)) {
					predicates.add(cb.equal(root.get("warehouse").get("id"), stockViewFilterDTO.getWarehouseId()));
				}
				if ((stockViewFilterDTO != null) && (stockViewFilterDTO.getItemId() != null)) {
					predicates.add(cb.equal(root.get("part").get("id"), stockViewFilterDTO.getItemId()));
				}
				if ((stockViewFilterDTO != null) && (stockViewFilterDTO.getStockNo() != null)
						&& (!stockViewFilterDTO.getStockNo().equalsIgnoreCase("") == Boolean.TRUE)) {
					predicates.add(cb.like(cb.lower(root.get("stock").get("stockNo")),
							"%" + stockViewFilterDTO.getStockNo().toLowerCase() + "%"));
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			};
			stockAdjustments = stockAdjustmentDao.findAll(specification);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return stockAdjustments;
	}

	private List<StockDTO> stockDetailData(List<ReceiptOrderItem> stockIn, List<AODItem> stockOut,
			List<AODReturnItem> stockRestore, List<StockAdjustment> stockAdjustments) {
		List<StockDTO> stockDTOs = new ArrayList<>();
		for (ReceiptOrderItem item : stockIn) {
			StockDTO stockDTO = new StockDTO();
			stockDTO.setRefCratedDate(item.getCreatedDate());
			if (item.getReceiptOrder().getBusiness() != null) {
				stockDTO.setSupplierName(item.getReceiptOrder().getSupplier().getName());
			}
			if ((item.getReceiptOrder() != null) && (item.getReceiptOrder().getSite() != null)) {
				stockDTO.setSiteId(item.getReceiptOrder().getSite().getId());
				stockDTO.setSite(item.getReceiptOrder().getSite().getName());
			}
			if (item.getWarehouse() != null) {
				stockDTO.setWarehouseId(item.getWarehouse().getId());
				stockDTO.setWarehouseName(item.getWarehouse().getName());
			}
			if (item.getAsset() != null) {
				stockDTO.setPartId(item.getAsset().getId());
				stockDTO.setPartName(item.getAsset().getName());
				stockDTO.setPartCode(item.getAsset().getCode());
			}
			if (item.getId() != null) {
				stockDTO.setReceiptOrderId(item.getReceiptOrder().getId());
				stockDTO.setReceiptOrderCode(item.getReceiptOrder().getCode());
				stockDTO.setStockTransactionDescription("GRN No ".concat(item.getReceiptOrder().getCode()));
			}
			/*
			 * if (item.getStocks().size() > 0) {
			 * stockDTO.setId(item.getStocks().get(0).getId());
			 * stockDTO.setStockNo(item.getStocks().get(0).getStockNo()); }
			 */
			stockDTO.setQtyMovement(item.getQuantityReceived());
			stockDTOs.add(stockDTO);
		}
		for (AODItem item : stockOut) {
			if ((item.getAodItemStocks() != null) && (item.getAodItemStocks().size() > 0)) {
				for (AODItemStock itemStock : item.getAodItemStocks()) {
					StockDTO stockDTO = new StockDTO();
					stockDTO.setRefCratedDate(item.getCreatedDate());
					if (item.getAod().getCustomer() != null) {
						stockDTO.setCustomerName(item.getAod().getCustomer().getName());
					}
					if (item.getSite() != null) {
						stockDTO.setSite(item.getSite().getName());
						stockDTO.setSiteId(item.getSite().getId());
					}
					if (item.getPart() != null) {
						stockDTO.setPartId(item.getPart().getId());
						stockDTO.setPartName(item.getPart().getName());
						stockDTO.setPartCode(item.getPart().getCode());
					}
					if ((item.getWarehouse() != null) && (item.getWarehouse().getId() != null)) {
						stockDTO.setWarehouseName(item.getWarehouse().getName());
						stockDTO.setWarehouseId(item.getWarehouse().getId());
					}
					if (item.getId() != null) {
						stockDTO.setAodId(item.getAod().getId());
						stockDTO.setAodCode(item.getAod().getAodNo());
						stockDTO.setStockTransactionDescription("AOD No ".concat(item.getAod().getAodNo()));
					}
					if ((itemStock.getStock() != null) && (itemStock.getStock().getId() != null)) {
						stockDTO.setStockNo(itemStock.getStock().getStockNo());
						stockDTO.setId(itemStock.getStock().getId());
					}
					stockDTO.setQtyMovement(itemStock.getQuantity());
					stockDTOs.add(stockDTO);
				}
			}

		}
		for (AODReturnItem item : stockRestore) {
//			BigDecimal returnQty = item.getReturnQty();
			for (AODItemStock aodItemStock : item.getAodItem().getAodItemStocks()) {
				StockDTO stockDTO = new StockDTO();
				stockDTO.setRefCratedDate(item.getCreatedDate());
				stockDTO.setId(aodItemStock.getStock().getId());
				stockDTO.setStockNo(aodItemStock.getStock().getStockNo());
				stockDTO.setQtyMovement(aodItemStock.getReturnQuantity());
				if (aodItemStock.getAodItem().getAod().getCustomer() != null) {
					stockDTO.setCustomerName(aodItemStock.getAodItem().getAod().getCustomer().getName());
				}
				if ((item.getAodItem() != null) && (item.getAodItem().getPart() != null)) {
					stockDTO.setPartCode(item.getAodItem().getPart().getCode());
					stockDTO.setPartName(item.getAodItem().getPart().getName());
					stockDTO.setPartId(item.getAodItem().getPart().getId());
					if ((item.getAodItem().getWarehouse() != null)
							&& (item.getAodItem().getWarehouse().getId() != null)) {
						stockDTO.setWarehouseId(item.getAodItem().getWarehouse().getId());
						stockDTO.setWarehouseName(item.getAodItem().getWarehouse().getName());
					}
				}
				if ((item.getAodReturn() != null) && (item.getAodReturn().getId() != null)) {
					stockDTO.setAodReturnId(item.getAodReturn().getId());
					stockDTO.setAodReturnNo(item.getAodReturn().getReturnNo());
					stockDTO.setStockTransactionDescription("AOD Return No ".concat(item.getAodReturn().getReturnNo()));
				}
				stockDTOs.add(stockDTO);
			}

		}
		for (StockAdjustment item : stockAdjustments) {
			BigDecimal qtyMovement = (item.getLastQuantity().subtract(item.getNewQuantity())).abs();
			StockDTO stockDTO = new StockDTO();
			if ((item.getStock() != null) && (item.getStock().getId() != null)) {
				stockDTO.setRefCratedDate(item.getCreatedDate());
				stockDTO.setId(item.getStock().getId());
				stockDTO.setStockNo(item.getStock().getStockNo());
				stockDTO.setQtyMovement(qtyMovement);
			}
			if ((item.getWarehouse() != null) && (item.getWarehouse().getId() != null)) {
				stockDTO.setWarehouseId(item.getWarehouse().getId());
				stockDTO.setWarehouseName(item.getWarehouse().getName());
			}
			if ((item.getPart() != null) && (item.getPart().getId() != null)) {
				stockDTO.setPartId(item.getPart().getId());
				stockDTO.setPartName(item.getPart().getName());
				stockDTO.setPartCode(item.getPart().getCode());
			}
			stockDTO.setStockAdjustmentId(item.getId());
			stockDTO.setStockAdjustmentCode(item.getAdjustmentCode());
			stockDTO.setStockTransactionDescription("Stock Adjustment ".concat(item.getAdjustmentCode()));
			stockDTOs.add(stockDTO);
		}
		stockDTOs.sort(Comparator.comparing(a -> a.getRefCratedDate()));
		return stockDTOs;
	}

	/*********************************************************************
	 * Stock History
	 *********************************************************************/

	@Override
	public List<StockHistoryDTO> findStockHistoryByStock(Integer stockId) {
		try {
			return StockHistoryMapper.getInstance().domainToDTOList(stockHistoryDao.findStockHistoryByStock(stockId));
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/*********************************************************************
	 * Stock Data Table
	 *********************************************************************/

	// Todo: Do we need based on the business or site
	@Override
	public DataTablesOutput<StockDTO> findStockByPart(FocusDataTablesInput input, Integer partId) throws Exception {
		StockPropertyMapper.getInstance().generateDataTableInput(input);
		DataTablesOutput<Stock> domainOut;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			Specification<Stock> specification = (root, query, cb) -> cb.and(
					cb.equal(root.get("part").get("id"), partId),
					cb.equal(root.get("part").get("partType"), PartType.REPAIRABLE));
			domainOut = stockDao.findAll(input, specification);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			Specification<Stock> specification = (root, query, cb) -> {
				query.groupBy(root.get("part").get("id"));
				return cb.and(
						// cb.equal(root.get("business"),
						// AuthenticationUtil.getLoginSite().getSite().getAssetBusinesses()),
						cb.equal(root.get("part").get("id"), partId),
						cb.equal(root.get("part").get("partType"), PartType.REPAIRABLE));
			};
			domainOut = stockDao.findAll(input, specification);
		} else {
			Specification<Stock> specification = (root, query, cb) -> {
				query.groupBy(root.get("part").get("id"));
				return cb.and(
						// cb.equal(root.get("business"),
						// AuthenticationUtil.getLoginSite().getSite().getAssetBusinesses()),
						// cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite()),
						cb.equal(root.get("part").get("id"), partId),
						cb.equal(root.get("part").get("partType"), PartType.REPAIRABLE));
			};
			domainOut = stockDao.findAll(input, specification);
		}
		return StockMapper.getInstance().domainToDTODataTablesOutput(domainOut);
	}

	@Override
	public DataTablesOutput<StockDTO> findStockByAsset(FocusDataTablesInput input, Integer assetId) throws Exception {
		DataTablesOutput<Stock> domainOut = new DataTablesOutput<>();
		StockPropertyMapper.getInstance().generateDataTableInput(input);
//		Specification<Stock> specification;
		/*
		 * if (AuthenticationUtil.isAuthUserAdminLevel()) { specification = (root,
		 * query, cb) -> cb.equal(root.get("part").get("id"), assetId); } else {
		 * specification = (root, query, cb) -> { Join<Stock, Asset> joinAsset =
		 * root.join("asset"); query.groupBy(root.get("id")); return
		 * cb.equal(joinAsset.get("asset").get("id"), assetId); }; }
		 */
		List<AssetConsumingReference> references = assetDao.findAssetConsumingPartReferenceByAssetId(assetId);
		List<Integer> partIdList = new ArrayList<Integer>();
		for (AssetConsumingReference reference : references) {
			if (reference.getPart() != null) {
				partIdList.add(reference.getPart().getId());
			}
		}

		/*
		 * Expression<Integer> exp = root.get("part").get("id"); specification = (root,
		 * query, cb) -> cb.in(exp); CriteriaQuery<Pet> cq = cb.createQuery(Pet.class);
		 * Root<Pet> pet = cq.from(Pet.class); cq.groupBy(pet.get(Pet_.color));
		 * cq.having(cb.in(pet.get(Pet_.color)).value("brown").value("blonde");
		 */

		// specification = (root, query, cb) -> cb.equal(root.get("part").get("id"),
		// assetId);

		// specification = (root, query, cb) -> {
		// Join<Stock, Asset> joinAsset = root.join("part");
		// Join<Asset, AssetConsumingReference> joinAssetConsumeRef =
		// joinAsset.join("assetConsumingReferences");
		// query.groupBy(root.get("id"));
		// return cb.equal(joinAssetConsumeRef.get("part").get("id"), assetId);
		// };

		/*
		 * specification = (root, query, cb) -> { Expression<Integer> exp =
		 * root.get("part").get("id"); Join<Stock, Asset> joinAsset = root.join("part");
		 * Join<Asset, AssetConsumingReference> joinAssetConsumeRef =
		 * joinAsset.join("assetConsumingReferences"); // query.groupBy(root.get("id"));
		 * return cb.in(exp); };
		 */

		/*
		 * if (partIdList.size()> 0) domainOut = stockDao.findAll(input,
		 * applicantsMatchMobility(partIdList)); else stockDao.findAll(input);
		 */
		domainOut = partIdList.size() > 0 ? stockDao.findAll(input, applicantsMatchMobility(partIdList))
				: new DataTablesOutput<>();
		return StockMapper.getInstance().domainToDTODataTablesOutput(domainOut);
	}

	private Specification<Stock> applicantsMatchMobility(List<Integer> partIdList) {
		return (root, query, builder) -> {
//			Collection<Predicate> predicates = new ArrayList<>();
			Join<Stock, Asset> stockAssetJoin = root.join("part");
			// Expression<Integer> idExp = stockAssetJoin.<Asset>get("id");
			Expression<Integer> idExp = stockAssetJoin.get("id");
			return builder.and(idExp.in(partIdList));
		};
	}

	@Override
	public DataTablesOutput<StockDTO> findStockByAssetTemp(FocusDataTablesInput input, Integer assetId)
			throws Exception {
		DataTablesOutput<Stock> domainOut;
		StockPropertyMapper.getInstance().generateDataTableInput(input);
		Specification<Stock> specification;
		specification = (root, query, cb) -> cb.isNotNull(root.get("part").get("id"));
		domainOut = stockDao.findAll(input, specification);
		DataTablesOutput<StockDTO> out = StockMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	@Override
	public DataTablesOutput<StockDTO> findStockByUserLevel(FocusDataTablesInput input, Integer assetId)
			throws Exception {
		DataTablesOutput<Stock> domainOut;
		StockPropertyMapper.getInstance().generateDataTableInput(input);
		Specification<Stock> specification;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			specification = (root, query, cb) -> cb.equal(root.get("part").get("id"), assetId);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			specification = (root, query, cb) -> {
				Join<Stock, Asset> joinAsset = root.join("asset");
				Join<Asset, AssetConsumingReference> joinAssetConsumeRef = joinAsset.join("assetConsumingReferences");
				query.groupBy(root.get("id"));
				return cb.and(cb.equal(joinAssetConsumeRef.get("asset").get("id"), assetId),
						cb.equal(root.get("assetBusiness").get("business"), AuthenticationUtil.getLoginUserBusiness()));
			};
		} else {
			specification = (root, query, cb) -> {
				Join<Stock, Asset> joinAsset = root.join("asset");
				Join<Asset, AssetConsumingReference> joinAssetConsumeRef = joinAsset.join("assetConsumingReferences");
				query.groupBy(root.get("id"));
				return cb.and(cb.equal(joinAssetConsumeRef.get("asset").get("id"), assetId),
						cb.equal(root.get("assetBusiness").get("business"),
								AuthenticationUtil.getLoginSite().getSite().getBusiness()));
			};
		}
		domainOut = stockDao.findAll(input, specification);
		DataTablesOutput<StockDTO> out = StockMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	@Override
	public DataTablesOutput<StockDTO> findStockByPartAndWarehouse(FocusDataTablesInput input, Integer partId,
			Integer warehouseId) throws Exception {
		StockWarehousePropertyMapper.getInstance().generateDataTableInput(input);
		DataTablesOutput<Stock> domainOut;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			Specification<Stock> specification = (root, query, cb) -> cb.and(
					cb.equal(root.get("warehouse").get("id"), warehouseId),
					cb.equal(root.get("part").get("id"), partId));
			domainOut = stockDao.findAll(input, specification);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			Specification<Stock> specification = (root, query, cb) -> cb.and(
					cb.equal(root.get("warehouse").get("id"), warehouseId),
					cb.equal(root.get("part").get("id"), partId));
			domainOut = stockDao.findAll(input, specification);
		} else {
			Specification<Stock> specification = (root, query, cb) -> cb.and(
					cb.equal(root.get("warehouse").get("id"), warehouseId),
					cb.equal(root.get("part").get("id"), partId));
			domainOut = stockDao.findAll(input, specification);
		}
		DataTablesOutput<StockDTO> out = StockMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	@Override
	public DataTablesOutput<StockDTO> findRemainStockPartList(FocusDataTablesInput input) {
		StockPropertyMapper.getInstance().generateDataTableInput(input);
		DataTablesOutput<StockDTO> out = null;
		try {
			DataTablesOutput<Stock> domainOut;
			if (AuthenticationUtil.isAuthUserAdminLevel()) {
				Specification<Stock> specification = (root, query, cb) -> {
					// query.groupBy(root.get("part").get("id"));
					return cb.gt(root.get("currentQuantity"), BigDecimal.ZERO);
				};
				domainOut = stockDao.findAll(input, specification);
			} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
				Specification<Stock> specification = (root, query, cb) -> {
					// query.groupBy(root.get("part").get("id"));
					return cb.and(cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness()),
							cb.gt(root.get("currentQuantity"), BigDecimal.ZERO));
				};
				domainOut = stockDao.findAll(input, specification);
			} else {
				Specification<Stock> specification = (root, query, cb) -> {
					query.groupBy(root.get("part").get("id"));
					return cb.and(cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness()),
							cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite()),
							cb.gt(root.get("currentQuantity"), BigDecimal.ZERO));
				};
				domainOut = stockDao.findAll(input, specification);
			}
			out = StockMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return out;
	}

	@Override
	public DataTablesOutput<StockDTO> findStockPartList(FocusDataTablesInput input) throws Exception {
		StockPartPropertyMapper.getInstance().generateDataTableInput(input);
		DataTablesOutput<Stock> domainOut;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			Specification<Stock> specification = (root, query, cb) -> {
				query.groupBy(root.get("part").get("id"));
				return null;
			};
			domainOut = stockDao.findAll(input, specification);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			Specification<Stock> specification = (root, query, cb) -> {
				query.groupBy(root.get("part").get("id"));
				return cb.equal(root.get("business"), AuthenticationUtil.getLoginSite().getSite().getBusiness());
			};
			domainOut = stockDao.findAll(input, specification);
		} else {
			Specification<Stock> specification = (root, query, cb) -> {
				query.groupBy(root.get("part").get("id"));
				return cb.and(cb.equal(root.get("business"), AuthenticationUtil.getLoginSite().getSite().getBusiness()),
						cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite()));
			};
			domainOut = stockDao.findAll(input, specification);
		}
		return StockMapper.getInstance().domainToDTODataTablesOutput(domainOut);

	}

	/*********************************************************************
	 * [COMMON] Get AVG Price
	 *********************************************************************/

	private BigDecimal getAVGPrice(Integer partId) {
		List<Stock> stockList = stockDao.findNonEmptyStockByItemId(partId);
		BigDecimal totalVal = BigDecimal.ZERO;
		BigDecimal totalQty = BigDecimal.ZERO;
		for (Stock stk : stockList) {
			totalVal = totalVal.add((stk.getUnitPrice().multiply(stk.getCurrentQuantity())));
			totalQty = totalQty.add(stk.getCurrentQuantity());
		}
		return (totalVal != BigDecimal.ZERO) && (totalQty != BigDecimal.ZERO) ? (totalVal.divide(totalQty))
				: BigDecimal.ZERO;
	}

	/*********************************************************************
	 * [COMMON] Set STOCK History
	 *********************************************************************/
	/*
	 * private void addStockHistory(Stock stock, StockHistory stockHistory,
	 * BigDecimal qty) { StockHistory tempLastHistory = new StockHistory();
	 * stockHistory.setStock(stock); stockHistory.setQuantity(qty);
	 * Optional<StockHistory> lastObj =
	 * stock.getStockHistoryList().stream().reduce((first, second) -> second); if
	 * (lastObj.isPresent()) { tempLastHistory = lastObj.get();
	 * stockHistory.setBeforeQuantity(tempLastHistory.getAfterQuantity()); }
	 * stockHistory.setAfterQuantity(stock.getCurrentQuantity());
	 * stockHistory.setDate(new Date());
	 * stock.getStockHistoryList().add(stockHistory); }
	 */

	/*********************************************************************
	 *********************************************************************
	 *********************************************************************
	 *********************************************************************
	 ************************ T E M P ************************************ (METHODS)
	 * ***********************************
	 *********************************************************************
	 *********************************************************************
	 *********************************************************************
	 */

	@Override
	public void saveUpdatePartStock(List<StockDTO> stockDTOs) {

	}

	@Override
	public StockResult save(StockDTO dto) {
		StockResult result = createPartResult(dto);
		try {
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Stock Already updated. Please Reload Stock.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	private StockResult createPartResult(StockDTO dto) {
		StockResult result;

		if ((dto.getId() != null) && (dto.getId() > 0)) {
			result = new StockResult(stockDao.findOne(dto.getId()), dto);
		} else {
			result = new StockResult(new Stock(), dto);
		}

		return result;
	}

	private String getMessageByAction(StockDTO dto) {
		if (dto.getId() == null) {
			return "Stock Added Successfully.";
		} else {
			return "Stock Updated Successfully.";
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(StockResult result) throws Exception {
		try {
			StockMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
			saveUpdateData(result);
			stockDao.save(result.getDomainEntity());
			result.setDtoEntity(findById(result.getDomainEntity().getId()));
		} catch (ObjectOptimisticLockingFailureException ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			result.setResultStatusError();
			result.addToErrorList("Stock Already updated. Please Reload Stock.");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
	}

	private void saveUpdateData(StockResult result) throws Exception {
		setBusiness(result);
		setWarehouse(result);
		setPart(result);
		setNotifications(result);
	}

	private void setBusiness(StockResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getBusinessId() != null)) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}

	private void setWarehouse(StockResult result) {
		if (result.getDtoEntity().getWarehouseId() != null) {
			result.getDomainEntity().setWarehouse(assetDao.findOne(result.getDtoEntity().getWarehouseId()));
		}
	}

	private void setPart(StockResult result) {
		if (result.getDtoEntity().getPartId() != null) {
			result.getDomainEntity().setPart(assetDao.findOne(result.getDtoEntity().getPartId()));
		}
	}

	private void setNotifications(StockResult result) throws Exception {
		Set<StockNotification> stockNotifications = new HashSet<>();
		List<StockNotificationDTO> stockNotificationDTOs = result.getDtoEntity().getStockNotificationDTOs();

		if (stockNotificationDTOs != null && stockNotificationDTOs.size() > 0) {
			Set<StockNotification> currentNotifications = result.getDomainEntity().getStockNotifications();
			StockNotification stockNotification = new StockNotification();
			for (StockNotificationDTO stockNotificationDTO : stockNotificationDTOs) {
				if (currentNotifications != null && currentNotifications.size() > 0) {
					stockNotification = currentNotifications.stream()
							.filter((x) -> x.getId().equals(stockNotificationDTO.getId())).findAny()
							.orElseGet(StockNotification::new);
				} else {
					stockNotification = new StockNotification();
				}
				createStockNotification(stockNotificationDTO, stockNotification, result.getDomainEntity());
				stockNotifications.add(stockNotification);
			}
		}

		result.getDomainEntity().setStockNotifications(stockNotifications);
	}

	private void createStockNotification(StockNotificationDTO stockNotificationDTO, StockNotification stockNotification,
			Stock domainEntity) throws Exception {
		StockNotificationMapper.getInstance().dtoToDomain(stockNotificationDTO, stockNotification);
		stockNotification.setStock(domainEntity);
		stockNotification.setUser(
				stockNotificationDTO.getUserId() != null ? userDao.findOne(stockNotificationDTO.getUserId()) : null);
	}

	@Override
	public StockResult findByIdWithOpenPOs(Integer id) {
		return null;

	}

	@Override
	public StockResult delete(Integer id) {
		StockResult result = new StockResult(null, null);
		try {
			stockDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Stock Deleted Successfully.");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToMessageList("Error Occurred! Stock Deleted Unsuccessful.".concat(ex.getMessage()));
		}
		return result;
	}

	@Override
	public Stock findOne(Integer id) throws Exception {
		try {
			return stockDao.findOne(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public Integer findMinimumStock() {
		return (int) stockDao.count(getAllLoWStockItem());
	}

	private Specification<Stock> getAllLoWStockItem() {
		Specification<Stock> specification;
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			specification = (root, query, cb) -> {
				return getLowStockItemPredicate(root, cb);
			};
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			specification = (root, query, cb) -> {
				Predicate predicate = cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
				Predicate predicate2 = getLowStockItemPredicate(root, cb);
				return cb.and(predicate, predicate2);
			};
		} else {
			specification = (root, query, cb) -> {
				Predicate predicate = cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite());
				Predicate predicate2 = getLowStockItemPredicate(root, cb);
				return cb.and(predicate, predicate2);
			};

		}
		return specification;

	}

	private Predicate getLowStockItemPredicate(Root<Stock> root, CriteriaBuilder cb) {
		return cb.ge(root.get("minQuantity"), root.get("currentQuantity"));
	}

	@Override
	public DataTablesOutput<StockDTO> findLowStockPartItem(FocusDataTablesInput input) throws Exception {
		StockPartPropertyMapper.getInstance().generateDataTableInput(input);
		DataTablesOutput<Stock> domainOut;
		Specification<Stock> specification = getAllLoWStockItem();
		domainOut = stockDao.findAll(input, specification);
		return StockMapper.getInstance().domainToDTODataTablesOutput(domainOut);

	}

	@Override
	public DataTablesOutput<StockDTO> findStock(FocusDataTablesInput input) throws Exception {
		StockPropertyMapper.getInstance().generateDataTableInput(input);
		DataTablesOutput<Stock> domainOut;
		domainOut = stockDao.findAll(input);
		return StockMapper.getInstance().domainToDTODataTablesOutput(domainOut);

	}

	@Override
	public StockDTO createNewStock(Integer partId) {
		StockDTO dto = new StockDTO();
		Asset asset = assetDao.findOne(partId);

		dto.setPartId(asset.getId());
		dto.setPartName(asset.getName());
		dto.setPartCode(asset.getCode());
		if (AuthenticationUtil.isAuthUserSystemLevel()) {
			dto.setBusinessId(AuthenticationUtil.getLoginUserBusiness().getId());
		}

		if (asset.getPartType() != null) {
			setStockType(dto, asset.getPartType());
		} else {
			setStockType(dto, PartType.NORMAL);
		}
		return dto;
	}

	private void setStockType(StockDTO dto, PartType partType) {
		switch (partType) {
		case NORMAL:
			dto.setStockType(StockType.NORMAL);
			break;
		case REPAIRABLE:
			dto.setStockType(StockType.REFURBISH);
			break;
		case OTHER:
			dto.setStockType(StockType.OTHER);
			break;

		default:
			dto.setStockType(StockType.NORMAL);
			break;
		}
	}

}
