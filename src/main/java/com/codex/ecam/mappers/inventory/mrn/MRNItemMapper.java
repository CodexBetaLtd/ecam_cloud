package com.codex.ecam.mappers.inventory.mrn;

import com.codex.ecam.dto.inventory.mrn.MRNItemDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.inventory.mrn.MRNItem;

public class MRNItemMapper extends GenericMapper<MRNItem, MRNItemDTO> {

    private static MRNItemMapper instance = null;

    private MRNItemMapper() {
    }

    public static MRNItemMapper getInstance() {
        if (instance == null) {
            instance = new MRNItemMapper();
        }
        return instance;
    }

    @Override
    public MRNItemDTO domainToDto(MRNItem domain) throws Exception {
    	MRNItemDTO dto = new MRNItemDTO();

        return dto;
    }

    @Override
    public void dtoToDomain(MRNItemDTO dto, MRNItem domain) throws Exception {
        domain.setId(dto.getId());
        domain.setIsDeleted(dto.getIsDeleted());
    }


    @Override
    public MRNItemDTO domainToDtoForDataTable(MRNItem domain) throws Exception {
    	MRNItemDTO dto = new MRNItemDTO();

        return dto;
    }


}

