package com.codex.ecam.service.inventory.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.constants.inventory.StockAdjustmentStatus;
import com.codex.ecam.constants.util.AffixList;
import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.dao.inventory.StockAdjustmentDao;
import com.codex.ecam.dao.inventory.StockDao;
import com.codex.ecam.dto.inventory.stockAdjuestment.StockAdjustmentDTO;
import com.codex.ecam.dto.inventory.stockAdjuestment.StockAdjustmentFilterDTO;
import com.codex.ecam.dto.inventory.stockAdjuestment.StockAdjustmentRepDTO;
import com.codex.ecam.mappers.inventory.stockAdjustment.StockAdjustmentMapper;
import com.codex.ecam.mappers.inventory.stockAdjustment.StockAdjustmentReportMapper;
import com.codex.ecam.model.inventory.stockAdjustment.StockAdjustment;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.AccountResult;
import com.codex.ecam.result.inventory.StockAdjustmentResult;
import com.codex.ecam.service.inventory.api.StockAdjustmentService;
import com.codex.ecam.service.inventory.api.StockService;
import com.codex.ecam.util.CommonUtil;
import com.codex.ecam.util.search.inventory.StockAdjustmentPropertyMapper;

@Service
public class StockAdjustmentServiceImpl implements StockAdjustmentService {

	@Autowired
	private StockDao stockDao;

	@Autowired
	private AssetDao assetDao;

	@Autowired
	private StockAdjustmentDao stockAdjustmentDao;

	@Autowired
	private StockService stockService;

	@Override
	public StockAdjustmentDTO newStockAdjustment() {
		final StockAdjustmentDTO dto = new StockAdjustmentDTO();
		dto.setAdjustmentStatus(StockAdjustmentStatus.DRAFT);
		dto.setAdjustmentCode(getNextCode());
		return dto;
	}

	private String getNextCode() {
		final StockAdjustment lastDomain = stockAdjustmentDao.findLastDomain();
		Integer nextNo = 0;
		final Integer year = Calendar.getInstance().get(Calendar.YEAR);
		if (lastDomain == null || lastDomain.getId() == null) {
			nextNo = 1;
		} else {
			final List<String> codeList = Arrays.asList(lastDomain.getAdjustmentCode().split("/"));
			if (!codeList.get(0).equalsIgnoreCase(AffixList.STOCK_ADJUSTMENT.getCode())) {
				nextNo = 1;
			} else if (Integer.parseInt(codeList.get(1)) == year) {
				nextNo = Integer.parseInt(codeList.get(2)) + 1;
			} else {
				nextNo = 1;
			}
		}
		return CommonUtil.setNextCode(AffixList.STOCK_ADJUSTMENT.getCode(), nextNo.toString());
	}

	private void setNextCode(StockAdjustmentResult result) {
		if (result.getDomainEntity().getId() == null) {
			result.getDomainEntity().setAdjustmentCode(getNextCode());
		}
	}

	@Override
	public StockAdjustmentResult statusChange(Integer id, StockAdjustmentStatus stockAdjustmentStatus) {
		final StockAdjustmentResult result = new StockAdjustmentResult(null, null);
		try {
			executeStatusChange(id, result);
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void executeStatusChange(Integer id, StockAdjustmentResult result) throws Exception {
		result.setDtoEntity(findById(id));
		adjustStock(result.getDtoEntity());
		updateStockAdjustmentStatus(result.getDtoEntity());
		if (result.getDtoEntity().getPartId() != null) {
			modifyStockListAvgCost(result.getDtoEntity().getPartId());
		}
		result.setResultStatusSuccess();
	}

	private void adjustStock(StockAdjustmentDTO dto) throws Exception {
		stockService.adjustStock(dto);
	}

	private void updateStockAdjustmentStatus(StockAdjustmentDTO dto) throws Exception {
		StockAdjustment adjustment = null;
		if (dto != null && dto.getId() != null) {
			adjustment = stockAdjustmentDao.findOne(dto.getId());
		}
		adjustment.setStockAdjustmentStatus(StockAdjustmentStatus.APPROVED);
		stockAdjustmentDao.save(adjustment);
	}

	private void modifyStockListAvgCost(Integer partId) throws Exception {
		try {
			//            stockService.updateItemAVGPrice(partId);
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public DataTablesOutput<StockAdjustmentDTO> findAll(FocusDataTablesInput input) throws Exception {
		StockAdjustmentPropertyMapper.getInstance().generateDataTableInput(input);
		final DataTablesOutput<StockAdjustment> domainOut = stockAdjustmentDao.findAll(input);
		DataTablesOutput<StockAdjustmentDTO> out = null;
		try {
			out = StockAdjustmentMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return out;
	}

	@Override
	public StockAdjustmentDTO findById(Integer id) throws Exception {
		final StockAdjustment domain = findEntityById(id);
		if (domain != null) {
			return StockAdjustmentMapper.getInstance().domainToDto(domain);
		}
		return null;
	}

	public StockAdjustment findEntityById(Integer id) throws Exception {
		return stockAdjustmentDao.findOne(id);
	}

	@Override
	public StockAdjustmentResult delete(Integer id) {
		final StockAdjustmentResult result = new StockAdjustmentResult(null, null);
		try {
			final StockAdjustment adjustment = findEntityById(id);
			if (adjustment.getStockAdjustmentStatus().equals(StockAdjustmentStatus.DRAFT)) {
				stockAdjustmentDao.delete(id);
				result.setResultStatusSuccess();
				result.addToMessageList("Draft Stock Adjustment Deleted Successfully.");
			} else {
				result.setResultStatusError();
				result.addToErrorList("Approved Stock Adjustment Cannot Deleted.");
			}
		} catch (final Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public StockAdjustmentResult deleteMultiple(Integer[] ids) throws Exception {
		final StockAdjustmentResult result = new StockAdjustmentResult(null, null);
		try {
			for (final Integer id : ids) {
				final StockAdjustment adjustment = findEntityById(id);
				if (adjustment.getStockAdjustmentStatus().equals(StockAdjustmentStatus.DRAFT)) {
					stockAdjustmentDao.delete(id);
				} else {
					result.setResultStatusError();
					result.addToErrorList("Approved Stock Adjustment Cannot Deleted.");
					return result;
				}
			}
			result.setResultStatusSuccess();
			result.addToMessageList("Stock Adjustment (s) Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Stock Adjustment (s) Already Used. Cannot delete.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	public StockAdjustmentResult update(StockAdjustmentDTO dto) {
		final StockAdjustmentResult result = new StockAdjustmentResult(null, dto);
		try {
			final StockAdjustment domain = stockAdjustmentDao.findOne(dto.getId());
			result.setDomainEntity(domain);
			saveOrUpdate(result);
			result.addToMessageList("Stock Updated Successfully");
		} catch (final ObjectOptimisticLockingFailureException ex) {
			result.setResultStatusError();
			result.addToErrorList("Stock Already updated. Please Reload Stock.");
		} catch (final Exception e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(e.getMessage());
		}
		return result;
	}

	@Override
	public StockAdjustmentResult save(StockAdjustmentDTO dto) {
		final StockAdjustmentResult result = new StockAdjustmentResult(new StockAdjustment(), dto);
		try {
			saveOrUpdate(result);
			result.addToMessageList("Stock Adjustment Added Successfully.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Override
	public List<StockAdjustmentRepDTO> findAll(StockAdjustmentFilterDTO filterDTO) {
		List<StockAdjustment> domainList = null;
		try {
			final Specification<StockAdjustment> specification = (root, query, cb) -> {
				final List<Predicate> predicates = new ArrayList<>();
				if (filterDTO != null && !filterDTO.getAdjustmentCode().equalsIgnoreCase("") == Boolean.TRUE) {
					predicates.add(cb.like(cb.lower(root.get("adjustmentCode")), "%" + filterDTO.getAdjustmentCode().toLowerCase() + "%"));
				}
				if (filterDTO != null && filterDTO.getAdjustmentStatus() != null && filterDTO.getAdjustmentStatus().getId() != null) {
					predicates.add(cb.equal(root.get("stockAdjustmentStatus"), filterDTO.getAdjustmentStatus()));
				}
				if (filterDTO != null && filterDTO.getStockId() != null) {
					predicates.add(cb.equal(root.get("stock").get("id"), filterDTO.getStockId()));
				}
				;
				if (filterDTO != null && filterDTO.getDate() != null) {
					predicates.add(cb.equal(root.get("stockAdjustmentDate"), filterDTO.getDate()));
				}
				if (filterDTO != null && filterDTO.getItemId() != null) {
					predicates.add(cb.equal(root.get("part").get("id"), filterDTO.getItemId()));
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			};
			domainList = stockAdjustmentDao.findAll(specification);
			return StockAdjustmentReportMapper.getInstance().domainToRepDTOList(domainList);

		} catch (final Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public StockAdjustmentRepDTO findRepDTOById(Integer id) {
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private StockAdjustmentResult saveOrUpdate(StockAdjustmentResult result) throws Exception {
		StockAdjustmentMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setResultData(result);
		stockAdjustmentDao.save(result.getDomainEntity());
		result.setDtoEntity(findById(result.getDomainEntity().getId()));

		return result;
	}

	private void setResultData(StockAdjustmentResult result) {
		setPart(result);
		setWarehouse(result);
		setStock(result);
		setNextCode(result);
	}

	private void setStock(StockAdjustmentResult result) {
		if (result.getDtoEntity().getStockId() != null) {
			result.getDomainEntity().setStock(stockDao.findOne(result.getDtoEntity().getStockId()));
		}
	}

	private void setWarehouse(StockAdjustmentResult result) {
		if (result.getDtoEntity().getWarehouseId() != null) {
			result.getDomainEntity().setWarehouse(assetDao.findOne(result.getDtoEntity().getWarehouseId()));
		}
	}

	private void setPart(StockAdjustmentResult result) {
		if (result.getDtoEntity().getPartId() != null) {
			result.getDomainEntity().setPart(assetDao.findOne(result.getDtoEntity().getPartId()));
		}
	}

}
