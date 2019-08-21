package com.neolith.focus.mappers;

import com.neolith.focus.dto.BaseDTO;
import com.neolith.focus.model.BaseModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericMapper<DOMAIN extends BaseModel, DTO extends BaseDTO> {

	/**
	 * Transform domain object into DTO
	 * Mappings are used to 'bind' entity fields to DTO fields (for the mapper's implementation). 
	 * @param domain 
	 * @return DTO
	 */
	public abstract DTO domainToDto(DOMAIN domain) throws Exception;

    public abstract DTO domainToDtoForDataTable(DOMAIN domain) throws Exception;

    /**
     * Transform dto to domain
	 * @param domain
	 * @param dto
	 * @return
	 */
	public abstract void dtoToDomain(DTO dto, DOMAIN domain) throws Exception;
	
	
	public Page<DTO> entityPageToDTOPage(Page<DOMAIN> pageUser)throws Exception{
		return pageUser.map(new Converter<DOMAIN, DTO>() {
          public DTO convert(DOMAIN domain) {
				try {
					return domainToDto(domain);
				} catch (Exception e) { 
					 new Throwable(e);
				}
				return null;
          }
      });
    }

	public List<DTO> domainToDTOList(Iterable<DOMAIN> domainList) throws Exception{
		if(domainList == null) return new ArrayList<DTO>();
		List<DTO> dtoList = new ArrayList<DTO>();

		for(DOMAIN domain : domainList){
			dtoList.add(domainToDto(domain));
		}
		return dtoList;
    }
	
	public List<DTO> domainToDTOListForDataTables(Iterable<DOMAIN> domainList) throws Exception{
		if(domainList == null) return new ArrayList<DTO>();
		List<DTO> dtoList = new ArrayList<DTO>();

		for(DOMAIN domain : domainList){
			dtoList.add(domainToDtoForDataTable(domain));
		}
		return dtoList;
    }

    public DataTablesOutput<DTO> domainToDTODataTablesOutput(DataTablesOutput<DOMAIN> domainOut) throws Exception {

        DataTablesOutput<DTO> out = new DataTablesOutput<DTO>();
		out.setData(domainToDTOListForDataTables(domainOut.getData()));
		out.setDraw(domainOut.getDraw());
		out.setError(domainOut.getError());
		out.setRecordsFiltered(domainOut.getRecordsFiltered());
		out.setRecordsTotal(domainOut.getRecordsTotal());
		return out;
		
	}

    protected void setCommanDTOFields(DTO dto, DOMAIN domain) {
        dto.setIsDeleted(domain.getIsDeleted());
        dto.setVersion(domain.getVersion());
    }

    protected void setCommanDomainFields(DTO dto, DOMAIN domain) {
        domain.setIsDeleted(dto.getIsDeleted());
        domain.setVersion(dto.getVersion());
    }
}
