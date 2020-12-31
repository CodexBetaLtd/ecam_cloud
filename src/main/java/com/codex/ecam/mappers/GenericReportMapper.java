package com.codex.ecam.mappers;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.codex.ecam.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericReportMapper<DOMAIN extends BaseModel, REPDTO> {

    /**
     * Transform domain object into REPDTO
     * Mappings are used to 'bind' entity fields to REPDTO fields (for the mapper's implementation).
     *
     * @param domain
     * @return REPDTO
     */
    public abstract REPDTO domainToRepDTO(DOMAIN domain) throws Exception;

    public List<REPDTO> domainToRepDTOList(Iterable<? extends DOMAIN> domainList) throws Exception {
        if (domainList == null) return new ArrayList<REPDTO>();
        List<REPDTO> dtoList = new ArrayList<REPDTO>();

        for (DOMAIN domain : domainList) {
            dtoList.add(domainToRepDTO(domain));
        }
        return dtoList;
    }

    public DataTablesOutput<REPDTO> domainToRepDTODataTablesOutput(DataTablesOutput<? extends DOMAIN> domainOut) throws Exception {
        DataTablesOutput<REPDTO> out = new DataTablesOutput<REPDTO>();
        out.setData(domainToRepDTOList(domainOut.getData()));
        out.setDraw(domainOut.getDraw());
        out.setError(domainOut.getError());
        out.setRecordsFiltered(domainOut.getRecordsFiltered());
        out.setRecordsTotal(domainOut.getRecordsTotal());
        return out;

    }

}
