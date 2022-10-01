package com.codex.ecam.mappers.inventory.aodReturn;

import java.util.ArrayList;
import java.util.List;

import com.codex.ecam.dto.inventory.aodReturn.AODReturnItemRepDTO;
import com.codex.ecam.dto.inventory.aodReturn.AODReturnRepDTO;
import com.codex.ecam.mappers.GenericReportMapper;
import com.codex.ecam.model.inventory.aodRetun.AODReturn;
import com.codex.ecam.model.inventory.aodRetun.AODReturnItem;


public class AODReturnReportMapper extends GenericReportMapper<AODReturn, AODReturnRepDTO> {

    private static AODReturnReportMapper instance = null;

    private AODReturnReportMapper() {
    }

    public static AODReturnReportMapper getInstance() {
        if (instance == null) {
            instance = new AODReturnReportMapper();
        }
        return instance;
    }

    @Override
    public AODReturnRepDTO domainToRepDTO(AODReturn domain) throws Exception {
        AODReturnRepDTO repDTO = new AODReturnRepDTO();
        repDTO.setAodReturnNo(domain.getReturnNo());
        if (domain.getAod() != null) {
//            repDTO.setAodNo(domain.getAod().getAodNo());
            repDTO.setAodReturnRefNo(domain.getAod().getAodNo());
            if (domain.getAod() != null && domain.getAod().getCustomer() != null) {
                repDTO.setAodCustomerCode(domain.getAod().getCustomer().getCode());
                repDTO.setAodCustomerName(domain.getAod().getCustomer().getName());
//                repDTO.setAodCustomerAddress01(domain.getAod().getCustomer().getAddressLine1());
//                repDTO.setAodCustomerAddress02(domain.getAod().getCustomer().getAddressLine2());
//                repDTO.setAodCustomerAddress03(domain.getAod().getCustomer().getAddressLine3());
            }
//            if (domain.getAod().getJob() != null) {
//                repDTO.setAodJobNo(domain.getAod().getJob().getCode());
//            }
            if (domain.getAod().getRequestedBy() != null) {
                repDTO.setAodRequestBy(domain.getAod().getRequestedBy().getFullName());
            }
        }
        repDTO.setAodReturnDate(domain.getReturnDate());
        if (domain.getUpdatedUser() == null && domain.getCreatedUser() != null) {
            repDTO.setPreparedBy(domain.getCreatedUser().getFullName());
        } else {
            repDTO.setPreparedBy(domain.getUpdatedUser().getFullName());
        }
        if (domain.getSite() != null && domain.getSite().getId() != null) {
            repDTO.setSiteName(domain.getSite().getName());
        }
        if (domain.getAodReturnStatus() != null && domain.getAodReturnStatus().getId() != null) {
            repDTO.setAodReturnStatus(domain.getAodReturnStatus().getName());
        }
        if (domain.getAodReturnItems() != null && domain.getAodReturnItems().size() > 0 == Boolean.TRUE) {
            setAODReturnItemRepDTO(domain, repDTO);
        }
        return repDTO;
    }

    private void setAODReturnItemRepDTO(AODReturn domain, AODReturnRepDTO repDTO) {
        List<AODReturnItemRepDTO> itemRepDTOs = new ArrayList<>();
        for (AODReturnItem item : domain.getAodReturnItems()) {
            AODReturnItemRepDTO dto = new AODReturnItemRepDTO();
            dto.setDescription(item.getDescription());
            dto.setReturnQty(item.getReturnQty());
            if (item.getAodItem() != null && item.getAodItem().getId() != null) {
                if (item.getAodItem().getPart() != null && item.getAodItem().getPart().getId() != null) {
                    dto.setAodItemPartNo(item.getAodItem().getPart().getName());
                }
//                if (item.getAodItem().getJob() != null && item.getAodItem().getJob().getId() != null) {
//                    dto.setAodItemJobNo(item.getAodItem().getJob().getCode());
//                }
                if (item.getAodItem().getAod() != null && item.getAodItem().getAod().getId() != null) {
                    dto.setAodNo(item.getAodItem().getAod().getAodNo());
                }
                if (item.getAodItem().getAod().getCustomer() != null && item.getAodItem().getAod().getCustomer().getId() != null) {
                    dto.setAodCustomer(item.getAodItem().getAod().getCustomer().getName());
                }
            }
            itemRepDTOs.add(dto);
        }
        repDTO.setItemRepDTOs(itemRepDTOs);
    }

}

