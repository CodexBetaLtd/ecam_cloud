package com.codex.ecam.validation.maintenance.workorder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codex.ecam.dao.maintenance.WorkOrderDao;
import com.codex.ecam.model.maintenance.workorder.WorkOrder;

@Component
public class WorkOrderCustomValidator {

	@Autowired
	private WorkOrderDao workOrderDao;

	public Boolean validateDuplicateWorkOrderCode(Integer id, String code) {
		List<WorkOrder> workOrders = workOrderDao.findDuplicateByCodeAndId(id, code);
		if ((workOrders != null) && (workOrders.size() > 0)) {
			return false;
		}
		return true;
	}

}
