package com.neolith.focus.service.maintenance.impl;


import com.neolith.focus.dao.maintenance.WorkOrderTaskDao;
import com.neolith.focus.dto.maintenance.workOrder.WorkOrderTaskDTO;
import com.neolith.focus.mappers.maintenance.workorder.WorkOrderTaskMapper;
import com.neolith.focus.model.maintenance.workorder.WorkOrderTask;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.service.maintenance.api.WorkOrderTaskService;
import com.neolith.focus.util.search.workorder.WorkOrderTaskSearchPropertyMapper;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class WorkOrderTaskServiceImpl implements WorkOrderTaskService {

    @Autowired
    private WorkOrderTaskDao workOrderTaskDao;

    @Override
    public DataTablesOutput<WorkOrderTaskDTO> findAllWorkOrderTaskByWorkOrderId(FocusDataTablesInput input, Integer id) throws Exception {
    	WorkOrderTaskSearchPropertyMapper.getInstance().generateDataTableInput(input);
        Specification<WorkOrderTask> specification = new Specification<WorkOrderTask>() {
            @Override
            public Predicate toPredicate(Root<WorkOrderTask> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("workOrder").get("id"), id);
            }
        };
        DataTablesOutput<WorkOrderTask> domainOut = workOrderTaskDao.findAll(input, specification);
        DataTablesOutput<WorkOrderTaskDTO> out = WorkOrderTaskMapper.getInstance().domainToDTODataTablesOutput(domainOut);
        return out;
    }

    @Override
    public WorkOrderTaskDTO findWorkOrderTaskById(Integer workOrderId) throws Exception {
        WorkOrderTask domain = workOrderTaskDao.findOne(workOrderId);
        if (domain != null) {
            return WorkOrderTaskMapper.getInstance().domainToDto(domain);
        }
        return new WorkOrderTaskDTO();
    }

}
