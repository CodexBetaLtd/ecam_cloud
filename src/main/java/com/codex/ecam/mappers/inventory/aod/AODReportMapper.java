package com.codex.ecam.mappers.inventory.aod;


import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.dto.inventory.aod.AODItemRepDTO;
import com.codex.ecam.dto.inventory.aod.AODRepDTO;
import com.codex.ecam.mappers.GenericReportMapper;
import com.codex.ecam.model.inventory.aod.AOD;
import com.codex.ecam.model.inventory.aod.AODItem;

public class AODReportMapper extends GenericReportMapper<AOD, AODRepDTO> {

    private static AODReportMapper instance = null;

    private AODReportMapper() {
    }

    public static AODReportMapper getInstance() {
        if (instance == null) {
            instance = new AODReportMapper();
        }
        return instance;
    }

    @Override
    public AODRepDTO domainToRepDTO(AOD domain) throws Exception {
        AODRepDTO repDTO = new AODRepDTO();
        repDTO.setAodNo(domain.getAodNo());
        repDTO.setDate(domain.getDate());
        if (domain.getCustomer() != null && domain.getCustomer().getId() != null) {
            repDTO.setCustomerNo(domain.getCustomer().getCode());
            repDTO.setCustomerName(domain.getCustomer().getName());
//            repDTO.setCustomerAddressLine01(domain.getCustomer().getAddressLine1());
//            repDTO.setCustomerAddressLine02(domain.getCustomer().getAddressLine2());
//            repDTO.setCustomerAddressLine03(domain.getCustomer().getAddressLine3());
//            repDTO.setCustomerContactPerson(domain.getCustomer().getAttention());
        }
//        repDTO.setCustomerContactPerson(domain.getCustomerContactPerson());
        if (domain.getUpdatedUser() == null && domain.getCreatedUser() != null) {
            repDTO.setPreparedBy(domain.getCreatedUser().getFullName());
        } else
            repDTO.setPreparedBy(domain.getUpdatedUser().getFullName());

//        if (domain.getJob() != null && domain.getJob().getId() != null)
//            repDTO.setAodJobNo(domain.getJob().getCode());
        if (domain.getRequestedBy() != null && domain.getRequestedBy().getId() != null)
            repDTO.setRequestedUserName(domain.getRequestedBy().getFullName());
        if (domain.getAodStatus() != null && domain.getAodStatus().getId() != null)
            repDTO.setAodStatus(domain.getAodStatus().getName());
        if (domain.getAodType() != null && domain.getAodType().getId() != null)
            repDTO.setAodType(domain.getAodStatus().getName());
        if (domain.getAodItemList() != null && domain.getAodItemList().size() > 0 == Boolean.TRUE)
            setAODItemRepDTO(domain, repDTO);
        return repDTO;
    }

    private void setAODItemRepDTO(AOD domain, AODRepDTO repDTO) {
        List<AODItemRepDTO> itemRepDTOs = new ArrayList<>();
        for (AODItem item : domain.getAodItemList()) {
            AODItemRepDTO itemRepDTO = new AODItemRepDTO();
            itemRepDTO.setBatchNo(item.getBatchNo());
            itemRepDTO.setDescription(item.getDescription());
            itemRepDTO.setItemQuantity(item.getQuantity());
            if (item.getPart() != null && item.getPart().getId() != null)
                itemRepDTO.setPartName(item.getPart().getName());
//            if (item.getStock() != null && item.getStock().getId() != null)
//                itemRepDTO.setStockNo(item.getStock().getStockNo());
            itemRepDTOs.add(itemRepDTO);
        }
        repDTO.setAodItemRepDTOs(itemRepDTOs);
    }


}
