package com.neolith.focus.service.maintenance.impl; 

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.neolith.focus.dao.maintenance.ScheduledMaintenanceTaskDao;
import com.neolith.focus.dto.maintenance.scheduledmaintenance.ScheduledMaintenanceTaskDTO;
import com.neolith.focus.mappers.maintenance.schedulemaintenance.ScheduledMaintenanceTaskMapper;
import com.neolith.focus.model.maintenance.scheduledmaintenance.ScheduledMaintenanceTask;
import com.neolith.focus.repository.FocusDataTablesInput;
import com.neolith.focus.service.maintenance.api.ScheduledMaintenanceTaskService;
import com.neolith.focus.util.AuthenticationUtil; 
import com.neolith.focus.util.search.scheduledmaintenance.ScheduledMaintenanceTaskSearchPropertyMapper;

@Service
public class ScheduledMaintenanceTaskServiceImpl implements ScheduledMaintenanceTaskService {

	@Autowired
	private ScheduledMaintenanceTaskDao scheduledMaintenanceTaskDao;

	@Override
	public ScheduledMaintenanceTaskDTO findScheduledMaintenanceTaskById(Integer scheduledMaintenanceTaskId) throws Exception {
		ScheduledMaintenanceTask domain = scheduledMaintenanceTaskDao.findOne(scheduledMaintenanceTaskId);
		if (domain != null) {
			return ScheduledMaintenanceTaskMapper.getInstance().domainToDto(domain);
		}
		return new ScheduledMaintenanceTaskDTO();
	}

	@Override
	public DataTablesOutput<ScheduledMaintenanceTaskDTO> findUpComingScheduleMaintenanceTask(FocusDataTablesInput input, Integer triggerId) throws Exception {
		DataTablesOutput<ScheduledMaintenanceTask> domainOut;
		ScheduledMaintenanceTaskSearchPropertyMapper.getInstance().generateDataTableInput(input);

		if(AuthenticationUtil.isAuthUserAdminLevel()){
			Specification<ScheduledMaintenanceTask> specification = (root, query, cb) -> {
				return cb.equal(root.get("scheduledMaintenanceTrigger").get("id"), triggerId);
			};
			domainOut = scheduledMaintenanceTaskDao.findAll(input, specification);
		} else if(AuthenticationUtil.isAuthUserSystemLevel()){
			Specification<ScheduledMaintenanceTask> specification = (root, query, cb) -> {
				Predicate predicate = cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
				Predicate predicate2 = cb.equal(root.get("scheduledMaintenanceTrigger").get("id"), triggerId);
				return cb.and(predicate, predicate2);
			};
			domainOut = scheduledMaintenanceTaskDao.findAll(input, specification);
		} else {
			Specification<ScheduledMaintenanceTask> specification = (root, query, cb) -> {
				Predicate predicate = cb.equal(root.get("site"), AuthenticationUtil.getLoginSite().getSite());
				Predicate predicate2 = cb.equal(root.get("scheduledMaintenanceTrigger").get("id"), triggerId);
				return cb.and(predicate, predicate2);
			};
			domainOut = scheduledMaintenanceTaskDao.findAll(input, specification);
		}

		DataTablesOutput<ScheduledMaintenanceTaskDTO> out = ScheduledMaintenanceTaskMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}


}
