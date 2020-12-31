package com.codex.ecam.validation.maintenance.scheduledmaintenance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codex.ecam.dao.maintenance.ScheduledMaintenanceDao;
import com.codex.ecam.model.maintenance.scheduledmaintenance.ScheduledMaintenance;

@Component
public class ScheduledMaintenanceCustomValidator {

	@Autowired
	private ScheduledMaintenanceDao smDao;

	public Boolean validateDuplicateSmCode(Integer id, String code) {
		List<ScheduledMaintenance> sms = smDao.findDuplicateByCodeAndId(id, code);
		if ((sms != null) && (sms.size() > 0)) {
			return false;
		}
		return true;
	}

}
