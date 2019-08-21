package com.codex.ecam.service.maintenance.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codex.ecam.dao.maintenance.MiscellaneousExpenseTypeDao;
import com.codex.ecam.dao.maintenance.WorkOrderMiscellaneousExpenseDao;
import com.codex.ecam.dto.maintenance.miscellaneousExpense.MiscellaneousExpenseTypeDTO;
import com.codex.ecam.dto.maintenance.workOrder.MiscellaneousExpenseDTO;
import com.codex.ecam.mappers.maintenance.MiscellaneousExpenseTypeMapper;
import com.codex.ecam.mappers.maintenance.workorder.WorkOrderMiscellaneousExpenseMapper;
import com.codex.ecam.model.maintenance.miscellaneous.MiscellaneousExpenseType;
import com.codex.ecam.model.maintenance.miscellaneous.WorkOrderMiscellaneousExpense;
import com.codex.ecam.service.maintenance.api.MiscellaneousExpenseService;


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
