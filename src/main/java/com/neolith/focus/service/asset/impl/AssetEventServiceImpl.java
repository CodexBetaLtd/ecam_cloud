package com.neolith.focus.service.asset.impl;

import com.neolith.focus.dao.asset.AssetEventDao;
import com.neolith.focus.dao.asset.AssetEventTypeAssetDao;
import com.neolith.focus.dto.asset.AssetEventDTO;
import com.neolith.focus.dto.asset.AssetEventTypeAssetDTO;
import com.neolith.focus.mappers.asset.AssetEventMapper;
import com.neolith.focus.mappers.asset.AssetEventTypeAssetMapper;
import com.neolith.focus.model.asset.AssetEvent;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.service.asset.api.AssetEventService;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
