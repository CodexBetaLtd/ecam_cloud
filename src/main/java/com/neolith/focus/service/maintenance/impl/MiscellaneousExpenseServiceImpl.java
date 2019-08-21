package com.neolith.focus.service.maintenance.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neolith.focus.dao.maintenance.MiscellaneousExpenseTypeDao;
import com.neolith.focus.dao.maintenance.WorkOrderMiscellaneousExpenseDao;
import com.neolith.focus.dto.maintenance.miscellaneousExpense.MiscellaneousExpenseTypeDTO;
import com.neolith.focus.dto.maintenance.workOrder.MiscellaneousExpenseDTO;
import com.neolith.focus.mappers.maintenance.MiscellaneousExpenseTypeMapper;
import com.neolith.focus.mappers.maintenance.workorder.WorkOrderMiscellaneousExpenseMapper;
import com.neolith.focus.model.maintenance.miscellaneous.MiscellaneousExpenseType;
import com.neolith.focus.model.maintenance.miscellaneous.WorkOrderMiscellaneousExpense;
import com.neolith.focus.service.maintenance.api.MiscellaneousExpenseService;


@Service
public class MiscellaneousExpenseServiceImpl implements MiscellaneousExpenseService {

	@Autowired
	private MiscellaneousExpenseTypeDao miscellaneousExpenseTypeDao;

	@Autowired
	private WorkOrderMiscellaneousExpenseDao workOrderMiscellaneousExpenseDao;

	@Override
	public List<MiscellaneousExpenseTypeDTO> findAll() {
		List<MiscellaneousExpenseType> miscellaneousExpenseTypes = (List<MiscellaneousExpenseType>) miscellaneousExpenseTypeDao.findAll();
		try {
			return MiscellaneousExpenseTypeMapper.getInstance().domainToDTOList(miscellaneousExpenseTypes);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<MiscellaneousExpenseTypeDTO>();
		}
	}

	@Override
	public MiscellaneousExpenseDTO findById(Integer id) {
		try {
			WorkOrderMiscellaneousExpense domain = workOrderMiscellaneousExpenseDao.findOne(id);
			if (domain != null) {
				return WorkOrderMiscellaneousExpenseMapper.getInstance().domainToDto(domain);
			}
			return new MiscellaneousExpenseDTO();
		} catch (Exception ex) {
			return new MiscellaneousExpenseDTO();
		}

	}



}
