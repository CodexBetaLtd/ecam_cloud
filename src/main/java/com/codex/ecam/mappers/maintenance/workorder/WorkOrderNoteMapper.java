package com.codex.ecam.mappers.maintenance.workorder;
   
import com.codex.ecam.dto.maintenance.workOrder.WorkOrderNoteDTO;
import com.codex.ecam.mappers.GenericMapper;
import com.codex.ecam.model.maintenance.workorder.WorkOrderNotes;
import com.codex.ecam.util.DateUtil;

public class WorkOrderNoteMapper extends GenericMapper<WorkOrderNotes, WorkOrderNoteDTO> {

    private static WorkOrderNoteMapper instance = null;

    private WorkOrderNoteMapper() {
    }

    public static WorkOrderNoteMapper getInstance() {
        if (instance == null) {
            instance = new WorkOrderNoteMapper();
        }
        return instance;
    }


    @Override
    public WorkOrderNoteDTO domainToDto(WorkOrderNotes domain) throws Exception {
    	WorkOrderNoteDTO dto = new WorkOrderNoteDTO();
        dto.setId(domain.getId());
        dto.setVersion(domain.getVersion());
        dto.setWoNote(domain.getNotes());
        dto.setWoNoteDate(DateUtil.getSimpleDateString(domain.getNoteDate()));

        if (domain.getCreatedUser() != null && domain.getCreatedUser().getId() != null) {
        	dto.setWoUserId(domain.getCreatedUser().getId());
            dto.setWoUserName(domain.getCreatedUser().getFullName());
        }
        
        if (domain.getWorkOrderStatus() != null) {
			dto.setWorkOrderStatus(domain.getWorkOrderStatus());
		}
        setCommanDTOFields(dto, domain);
        return dto;
    }

    @Override
    public WorkOrderNoteDTO domainToDtoForDataTable(WorkOrderNotes domain) throws Exception {
        return null;
    }

    @Override
    public void dtoToDomain(WorkOrderNoteDTO dto, WorkOrderNotes domain) throws Exception {
        domain.setId(dto.getId());
        domain.setVersion(dto.getVersion());
        domain.setNoteDate(DateUtil.getDateObj(dto.getWoNoteDate()));
        domain.setNotes(dto.getWoNote()); 
        domain.setWorkOrderStatus(dto.getWorkOrderStatus());
        setCommanDomainFields(dto, domain);
    }
}
