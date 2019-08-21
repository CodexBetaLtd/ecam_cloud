package com.neolith.focus.service.log.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.neolith.focus.constants.LogType;
import com.neolith.focus.dao.asset.AssetDao;
import com.neolith.focus.dao.asset.AssetLogDao;
import com.neolith.focus.dto.biz.part.PartLogDTO;
import com.neolith.focus.mappers.inventory.part.PartLogMapper;
import com.neolith.focus.model.BaseModel;
import com.neolith.focus.model.asset.Asset;
import com.neolith.focus.model.asset.AssetLog;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.service.log.LogSupport;
import com.neolith.focus.service.log.api.PartLogService;

@Service
@Qualifier("partLogService")
public class PartLogServiceImpl implements LogSupport, PartLogService {

	@Autowired
	private AssetLogDao assetLogDao;

	@Autowired
	private AssetDao assetDao;

	@Override
	public DataTablesOutput<PartLogDTO> findAllByPartId(FocusDataTablesInput input, Integer id) throws Exception {
		final Specification<AssetLog> specification = (root, query, cb) -> cb.equal(root.get("asset").get("id"), id);
		final DataTablesOutput<AssetLog> domainOut = assetLogDao.findAll(input, specification);
		final DataTablesOutput<PartLogDTO> out = PartLogMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}

	@Override
	public void createPersistLog(BaseModel model, String notes) {
		if (model instanceof Asset) {
			addAssetLog((Asset) model, notes, LogType.CREATE);
		}
	}

	@Override
	public void createUpdateLog(BaseModel model, String notes) {
		if (model instanceof Asset) {
			addAssetLog((Asset) model, notes, LogType.UPDATE);
		}
	}

	@Override
	public void createRemoveLog(BaseModel model, String notes) {
		if (model instanceof Asset) {
			addAssetLog((Asset) model, notes, LogType.REMOVE);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void addAssetLog(Asset asset, String notes, LogType type) {
        AssetLog log = createLogRecord(asset, notes, type);
        if (log != null) {
            assetLogDao.save(log);
		}
	}

	private AssetLog createLogRecord(Asset asset, String notes, LogType type) {
        if ((asset != null) && (asset.getId() != null) && (asset.getId() > 0)) {
            AssetLog log = new AssetLog();
            Asset currentAsset = assetDao.findOne(asset.getId());
            if (currentAsset != null) {
                log.setAsset(currentAsset);
                log.setNotes(notes);
                log.setLogType(type);
                log.setIsDeleted(false);
                return log;
            }
        }
        return null;
	}

}
