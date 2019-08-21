package com.neolith.focus.service.inventory.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.neolith.focus.dao.asset.AssetDao;
import com.neolith.focus.dao.biz.BusinessDao;
import com.neolith.focus.dao.inventory.BOMGroupDao;
import com.neolith.focus.dto.asset.AssetDTO;
import com.neolith.focus.dto.biz.part.PartDTO;
import com.neolith.focus.dto.inventory.bom.BOMGroupDTO;
import com.neolith.focus.mappers.inventory.BomGroupMapper;
import com.neolith.focus.model.asset.Asset;
import com.neolith.focus.model.asset.AssetConsumingReference;
import com.neolith.focus.model.inventory.bom.BOMGroup;
import com.neolith.focus.model.inventory.bom.BOMGroupPart;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.result.inventory.BOMGroupResult;
import com.neolith.focus.service.inventory.api.BOMGroupService;
import com.neolith.focus.util.AuthenticationUtil;
import com.neolith.focus.util.search.bomgroup.BOMGroupSearchPropertyMapper;

@Service
public class BOMGroupServiceImpl implements BOMGroupService {

	@Autowired
	private BOMGroupDao bomGroupDao;

	@Autowired
	private BusinessDao businessDao;

	@Autowired
	private AssetDao partDao;

	@Override
	public DataTablesOutput<BOMGroupDTO> findAll(FocusDataTablesInput input) throws Exception {
		DataTablesOutput<BOMGroup> domainOut;
		BOMGroupSearchPropertyMapper.getInstance().generateDataTableInput(input);
		if (AuthenticationUtil.isAuthUserAdminLevel()) {
			domainOut = bomGroupDao.findAll(input);
		} else if (AuthenticationUtil.isAuthUserSystemLevel()) {
			Specification<BOMGroup> specification = (root, query, cb) -> cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
			domainOut = bomGroupDao.findAll(input, specification);
		} else {
			Specification<BOMGroup> specification = (root, query, cb) -> cb.equal(root.get("business"), AuthenticationUtil.getLoginSite().getSite().getBusiness());
			domainOut = bomGroupDao.findAll(input, specification);
		}
		DataTablesOutput<BOMGroupDTO> out = BomGroupMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	@Override
	public BOMGroupDTO findById(Integer id) throws Exception {
		BOMGroup domain = bomGroupDao.findOne(id);
		BOMGroupDTO bomGroupDTO = new BOMGroupDTO();
		bomGroupDTO = BomGroupMapper.getInstance().domainToDto(domain);
		return bomGroupDTO;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BOMGroupResult delete(Integer id) {
		BOMGroupResult result = new BOMGroupResult(null, null);
		try {
			bomGroupDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("BOMGroup Deleted Successfully.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BOMGroupResult update(BOMGroupDTO dto) {
		BOMGroupResult result = new BOMGroupResult(null, dto);
		try {
			BOMGroup domain = bomGroupDao.findOne(dto.getId());
			result.setDomainEntity(domain);
			saveOrUpdate(result);
			result.addToMessageList("BOMGroup Updated Successfully.");

		} catch (Exception e) {
			e.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(e.getMessage());
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BOMGroupResult save(BOMGroupDTO dto) {
		BOMGroupResult result = new BOMGroupResult(new BOMGroup(), dto);
		saveOrUpdate(result);
		result.addToMessageList("BOM Group Added Successfully.");
		return result;
	}

	private void saveOrUpdate(BOMGroupResult result) {
		try {
			BomGroupMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
			setBusiness(result);
			setPartsAndAssets(result);
			bomGroupDao.save(result.getDomainEntity());
			result.getDtoEntity().setId(result.getDomainEntity().getId());
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("BOMGroup Already updated. Please Reload BOMGroup.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
	}

	private void setPartsAndAssets(BOMGroupResult result) {
		List<BOMGroupPart> groupParts = new ArrayList<>();
		List<BOMGroupPart> currentParts = result.getDomainEntity().getBomGroupParts();

		addAssetToGroupParts(result, groupParts, currentParts);
		addPartsToGroupParts(result, groupParts, currentParts);

		result.getDomainEntity().setBomGroupParts(null);
		result.getDomainEntity().setBomGroupParts(groupParts);
	}

	private void addAssetToGroupParts(BOMGroupResult result, List<BOMGroupPart> groupParts, List<BOMGroupPart> currentParts) {
		for (AssetDTO dto : result.getDtoEntity().getAssets()) {
			BOMGroupPart part = updateGroupParts(result.getDomainEntity(), dto.getId(), currentParts);
			groupParts.add(part);
			result.addAsset(part);
		}
	}

	private void addPartsToGroupParts(BOMGroupResult result, List<BOMGroupPart> groupParts, List<BOMGroupPart> currentParts) {
		for (PartDTO dto : result.getDtoEntity().getParts()) {
			BOMGroupPart groupPart = updateGroupParts(result.getDomainEntity(), dto.getId(), currentParts);
			addAssetConsumeReferences(groupPart, result.getAssets());
			groupParts.add(groupPart);
		}
	}

	private void addAssetConsumeReferences(BOMGroupPart groupPart, List<BOMGroupPart> assets) {
		List<AssetConsumingReference> assetConsumingRefs;
		AssetConsumingReference assetConsumeRef;

		if ((groupPart.getAssetConsumingReferences() != null) && (groupPart.getAssetConsumingReferences().size() > 0)) {
			assetConsumingRefs = groupPart.getAssetConsumingReferences();
		} else {
			assetConsumingRefs = new ArrayList<>();
		}

		for (BOMGroupPart asset : assets) {
			assetConsumeRef = new AssetConsumingReference();
			assetConsumeRef.setBomGroupAsset(asset);
			assetConsumeRef.setPart(groupPart.getPart());
			assetConsumeRef.setMaxConsumption(0.0);
			assetConsumeRef.setIsDeleted(false);
			assetConsumingRefs.add(assetConsumeRef);
		}
		groupPart.setAssetConsumingReferences(assetConsumingRefs);
	}

	private BOMGroupPart updateGroupParts(BOMGroup bomGroup, Integer assetId, List<BOMGroupPart> currentParts) {
		BOMGroupPart part;
		if ((currentParts != null) && (currentParts.size() > 0)) {

			Optional<BOMGroupPart> groupPart = currentParts.stream().filter((x) -> x.getPart().getId() == assetId).findAny();

			if (groupPart.isPresent()) {
				part = groupPart.get();
			} else {
				part = createBOMGroupPart(bomGroup, partDao.findOne(assetId));
			}
		} else {
			part = createBOMGroupPart(bomGroup, partDao.findOne(assetId));
		}

		return part;
	}

	private BOMGroupPart createBOMGroupPart(BOMGroup bomGroup, Asset part) {
		BOMGroupPart groupPart = new BOMGroupPart();

		groupPart.setBomGroup(bomGroup);
		groupPart.setPart(part);
		groupPart.setMaxConsumption(100.0);
		groupPart.setIsDeleted(false);

		return groupPart;
	}

	private void setBusiness(BOMGroupResult result) {
		if ((result.getDtoEntity() != null) && (result.getDtoEntity().getBusinessId() != null)) {
			result.getDomainEntity().setBusiness(businessDao.findById(result.getDtoEntity().getBusinessId()));
		}
	}

	@Override
	public DataTablesOutput<BOMGroupDTO> findBOMGroupsByBusiness(FocusDataTablesInput input, Integer bizId) throws Exception {
 
		BOMGroupSearchPropertyMapper.getInstance().generateDataTableInput(input);
		Specification<BOMGroup> specification = (root, query, cb) -> cb.equal(root.get("business").get("id"), bizId);
		DataTablesOutput<BOMGroup> domainOut = bomGroupDao.findAll(input, specification); 
		DataTablesOutput<BOMGroupDTO> out = BomGroupMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

}
