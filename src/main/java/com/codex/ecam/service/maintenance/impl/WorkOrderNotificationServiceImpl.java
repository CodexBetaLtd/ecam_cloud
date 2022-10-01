package com.codex.ecam.service.maintenance.impl;


import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.codex.ecam.dao.maintenance.WorkOrderNotificationDao;
import com.codex.ecam.dto.maintenance.workOrder.WorkOrderNotificationDTO;
import com.codex.ecam.mappers.maintenance.workorder.WorkOrderNotificationMapper;
import com.codex.ecam.model.maintenance.workorder.WorkOrderNotification;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.maintenance.api.WorkOrderNotificationService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class WorkOrderNotificationServiceImpl implements WorkOrderNotificationService {

    @Autowired
    private WorkOrderNotificationDao workOrderNotificationDao;

    @Override
    public DataTablesOutput<WorkOrderNotificationDTO> findAllWorkOrderNotificationByWorkOrderId(FocusDataTablesInput input, Integer id) throws Exception {

        Specification<WorkOrderNotification> specification = new Specification<WorkOrderNotification>() {
            @Override
            public Predicate toPredicate(Root<WorkOrderNotification> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("workOrder").get("id"), id);
            }
        };
        DataTablesOutput<WorkOrderNotification> domainOut = workOrderNotificationDao.findAll(input, specification);
        DataTablesOutput<WorkOrderNotificationDTO> out = WorkOrderNotificationMapper.getInstance().domainToDTODataTablesOutput(domainOut);
        return out;
    }

    @Override
    public WorkOrderNotificationDTO findWorkOrderNotificationById(Integer workOrderId) throws Exception {
        WorkOrderNotification domain = workOrderNotificationDao.findOne(workOrderId);
        if (domain != null) {
            return WorkOrderNotificationMapper.getInstance().domainToDto(domain);
        }
        return new WorkOrderNotificationDTO();
    }

}
