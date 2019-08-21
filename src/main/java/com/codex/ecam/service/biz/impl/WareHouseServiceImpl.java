package com.codex.ecam.service.biz.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.dao.admin.CountryDao;
import com.codex.ecam.dao.asset.AssetCategoryDao;
import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dto.biz.warehouse.WareHouseDTO;
import com.codex.ecam.mappers.biz.WarehouseMapper;
import com.codex.ecam.model.asset.Asset;
import com.codex.ecam.model.inventory.stock.Stock;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.asset.WareHouseResult;
import com.codex.ecam.service.biz.api.WareHouseService;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.List;

@Service
public class WareHouseServiceImpl implements WareHouseService {

	private final static Logger logger = LoggerFactory.getLogger(WareHouseServiceImpl.class);

	@Autowired
	private AssetDao wareHouseDao;

	@Autowired
	private CountryDao countryDao;

	@Autowired
	private AssetCategoryDao assetCategoryDao;

	@Autowired
	private BusinessDao businessDao;

	@Override
	public WareHouseDTO findById(Integer id) {
		Asset domain = wareHouseDao.findOne(id);
		try {
			return WarehouseMapper.getInstance().domainToDto(domain);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public WareHouseResult delete(Integer id) {
		WareHouseResult result = new WareHouseResult(null, null);
		try {
			wareHouseDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Warehouse Deleted Successfully.");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToMessageList("Error Occurred! Warehouse Deleted Unsuccessful.".concat(ex.getMessage()));
		}
		return result;
	}

	@Override
	public WareHouseResult save(WareHouseDTO dto) throws Exception {
		WareHouseResult result = createPartResult(dto);
        try {
            saveOrUpdate(result);
            result.addToMessageList(getMessageByAction(dto));
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Warehouse Already updated. Please Reload Warehouse.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	private WareHouseResult createPartResult(WareHouseDTO dto) {
		WareHouseResult result;
		if ((dto.getId() != null) && (dto.getId() > 0)) {
			result = new WareHouseResult(wareHouseDao.findOne(dto.getId()), dto);
		} else {
			result = new WareHouseResult(new Asset(), dto);
		}

		return result;
	}

	private String getMessageByAction(WareHouseDTO dto) {
		if (dto.getId() == null) {
			return "Warehouse Added Successfully.";
		} else {
			return "Warehouse Updated Successfully.";
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(WareHouseResult result) throws Exception {
		try {
			WarehouseMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
			setWarehouseData(result);
			wareHouseDao.save(result.getDomainEntity());
			result.setDtoEntity(findById(result.getDomainEntity().getId()));
		} catch (ObjectOptimisticLockingFailureException ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			result.setResultStatusError();
			result.addToErrorList("Warehouse Already updated. Please Reload Warehouse.");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
	}

	private void setWarehouseData(WareHouseResult result) throws Exception {
		setWearHouseCountry(result);
		setAssetCategory(result);
		setParentAsset(result);
		setBusiness(result);
		setSite(result);
		setLocation(result);
	}

	private void setLocation(WareHouseResult result) {
		if (result.getDtoEntity().getLocationDTO() != null) {
			result.getDomainEntity().setLatitude(result.getDtoEntity().getLocationDTO().getLatitude());
			result.getDomainEntity().setLongitude(result.getDtoEntity().getLocationDTO().getLongitude());
		}
	}

	@Override
    public DataTablesOutput<WareHouseDTO> findWearHouseByType(FocusDataTablesInput input, AssetCategoryType type) throws Exception {
        Specification<Asset> specification = (root, query, cb) -> cb.equal(root.get("assetCategory").get("assetCategoryType"), type);
        DataTablesOutput<Asset> domainOut = wareHouseDao.findAll(input, specification);
		DataTablesOutput<WareHouseDTO> out = WarehouseMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	private void setSite(WareHouseResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getSiteId() != null)) {
			result.getDomainEntity().setSite(wareHouseDao.findOne(result.getDtoEntity().getSiteId()));
		}
	}

	private void setBusiness(WareHouseResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getBusinessId() != null)) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}

	private void setWearHouseCountry(WareHouseResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getCountryId() != null)) {
			result.getDomainEntity().setCountry(countryDao.findById(result.getDtoEntity().getCountryId()));
		}
	}

	private void setAssetCategory(WareHouseResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getAssetCategoryId() != null)) {
			result.getDomainEntity().setAssetCategory(assetCategoryDao.findById(result.getDtoEntity().getAssetCategoryId()));
		}

	}

	private void setParentAsset(WareHouseResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getParentAssetId() != null)) {
			result.getDomainEntity().setParentAsset(wareHouseDao.findOne(result.getDtoEntity().getParentAssetId()));
		}
	}

	@Override
    public DataTablesOutput<WareHouseDTO> findWarehouseWithRemainQty(FocusDataTablesInput input,
                                                                     AssetCategoryType assetCategoryType, Integer partId) {
        DataTablesOutput<WareHouseDTO> out = null;

		try {
			Specification<Asset> specification = (root, query, cb) -> {
				query.groupBy(root.get("id"));
				Join<Asset, Stock> warehouseStockJoin = root.joinList("warehouseStocks", JoinType.LEFT);
				Predicate predicateEqualWarehouseStock = cb.equal(warehouseStockJoin.get("part").get("id"), partId);
                Predicate predicateEqualCategory = cb.equal(root.get("assetCategory").get("assetCategoryType"),
                        AssetCategoryType.WAREHOUSE);
                return cb.and(predicateEqualWarehouseStock, predicateEqualCategory);
            };
			DataTablesOutput<Asset> domainOut = wareHouseDao.findAll(input, specification);
			out = WarehouseMapper.getInstance().domainToDTODataTablesOutputCustom(domainOut, partId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return out;
	}

    @Override
    public List<WareHouseDTO> findWarehouseList(Integer id) {
        List<Asset> assets = wareHouseDao.findWarehouseListByTypeAndParentWarehouseId(AssetCategoryType.WAREHOUSE, id);
        try {
            return WarehouseMapper.getInstance().domainToDTOListForDataTables(assets);
        } catch (Exception e) {
            e.printStackTrace();
		}
		return null;
	}

    @Override
    public List<WareHouseDTO> findParentWarehouseList() {
        List<Asset> assets = wareHouseDao.findWarehouseListByType(AssetCategoryType.WAREHOUSE);
        try {
            return WarehouseMapper.getInstance().domainToDTOList(assets);
        } catch (Exception e) {
            e.printStackTrace();
		}
		return null;
	}

    @Override
    public DataTablesOutput<WareHouseDTO> findWarehouseByBusiness(FocusDataTablesInput input,
                                                                  AssetCategoryType warehouse, Integer id) {
        Specification<Asset> specification = (root, query, cb) -> cb.and(
                cb.equal(root.get("assetCategory").get("assetCategoryType"), AssetCategoryType.WAREHOUSE),
                cb.equal(root.get("business").get("id"), id));

		DataTablesOutput<Asset> domainOut = wareHouseDao.findAll(input, specification);
		DataTablesOutput<WareHouseDTO> out = null;
		try {
			out = WarehouseMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}

}
