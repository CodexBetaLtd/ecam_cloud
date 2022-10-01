package com.codex.ecam.service.asset.impl;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.codex.ecam.dao.asset.AssetEventDao;
import com.codex.ecam.dao.asset.AssetEventTypeAssetDao;
import com.codex.ecam.dto.asset.AssetEventDTO;
import com.codex.ecam.dto.asset.AssetEventTypeAssetDTO;
import com.codex.ecam.mappers.asset.AssetEventMapper;
import com.codex.ecam.mappers.asset.AssetEventTypeAssetMapper;
import com.codex.ecam.model.asset.AssetEvent;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.asset.api.AssetEventService;

import java.util.List;

@Service
public class AssetEventServiceImpl implements AssetEventService {

    @Autowired
    private AssetEventDao assetEventDao;

    @Autowired
    private AssetEventTypeAssetDao assetEventTypeAssetDao;

    @Override
    public DataTablesOutput<AssetEventDTO> findByEventTypeAndAsset(FocusDataTablesInput input, Integer assetId, Integer assetEventTypeId) throws Exception {
        Specification<AssetEvent> specification = (root, query, cb) -> cb.and(cb.equal(root.get("assetEventTypeAsset").get("asset").get("id"), assetId),
                cb.equal(root.get("assetEventTypeAsset").get("assetEventType").get("id"), assetEventTypeId));

        DataTablesOutput<AssetEvent> domainOut = assetEventDao.findAll(input, specification);
        DataTablesOutput<AssetEventDTO> out = AssetEventMapper.getInstance().domainToDTODataTablesOutput(domainOut);

        return out;
    }

    @Override
    public List<AssetEventDTO> findByAssetId(Integer id) throws Exception {
        List<AssetEventDTO> assetEvents = AssetEventMapper.getInstance().domainToDTOList(assetEventDao.findByAssetId(id));
        return assetEvents;
    }

    @Override
    public DataTablesOutput<AssetEventDTO> findAssetEventHistory(Integer id) {
        DataTablesOutput<AssetEventDTO> dataTablesOutput = new DataTablesOutput<>();

        //		List<AssetEvent> list = assetEventDao.findAssetEventHistory(id);
        //		List<AssetEventDTO> assetEventDTOs = new ArrayList<>();
        //		for(AssetEvent assetEvent : list){
        //			assetEventDTOs.add(addAssetEvent(assetEvent));
        //		}
        //		dataTablesOutput.setData(assetEventDTOs);

        return dataTablesOutput;
    }

    @Override
    public List<AssetEventTypeAssetDTO> findAssetEventTypeAssetByAssetId(Integer assetId) throws Exception {
        List<AssetEventTypeAssetDTO> assetEventTypeAssets = AssetEventTypeAssetMapper.getInstance().domainToDTOList(assetEventTypeAssetDao.findByAssetId(assetId));
        return assetEventTypeAssets;
    }

}
