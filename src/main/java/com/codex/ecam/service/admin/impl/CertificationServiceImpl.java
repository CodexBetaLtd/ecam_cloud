package com.codex.ecam.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codex.ecam.dao.admin.CertificationDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dto.admin.CertificationDTO;
import com.codex.ecam.mappers.admin.CertificationMapper;
import com.codex.ecam.model.admin.Certification;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.admin.CertificationResult;
import com.codex.ecam.service.admin.api.CertificationService;
import com.codex.ecam.util.AuthenticationUtil;
import com.codex.ecam.util.search.admin.CertificationSearchPropertyMapper;

@Service
public class CertificationServiceImpl implements CertificationService {

	@Autowired
	private CertificationDao certificationDao;

	@Autowired
	private BusinessDao businessDao;

	@Override
	public DataTablesOutput<CertificationDTO> findAll(FocusDataTablesInput input) throws Exception {
		DataTablesOutput<Certification> domainOut;

		CertificationSearchPropertyMapper.getInstance().generateDataTableInput(input);

		if(AuthenticationUtil.isAuthUserAdminLevel()){
			domainOut = certificationDao.findAll(input);
		} else {
			final Specification<Certification> specification = (root, query, cb) -> cb.equal(root.get("business"), AuthenticationUtil.getLoginUserBusiness());
			domainOut = certificationDao.findAll(input, specification);
		}

		final DataTablesOutput<CertificationDTO> out = CertificationMapper.getInstance().domainToDTODataTablesOutput(domainOut);

		return out;
	}

	@Override
	public CertificationDTO findById(Integer id) throws Exception {
		final Certification domain = certificationDao.findById(id);
		if (domain != null) {
			return CertificationMapper.getInstance().domainToDto(domain);
		}
		return null;
	}

	@Override
	public CertificationResult delete(Integer id) {
		final CertificationResult result = new CertificationResult(null, null);
		try {
			certificationDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("Certification Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Certification Already Assigned. Please Remove from Assigned Certification and try again.");
		} catch (final Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public CertificationResult deleteMultiple(Integer[] ids) throws Exception {
		final CertificationResult result = new CertificationResult(null, null);
		try {
			for (final Integer id : ids) {
				certificationDao.delete(id);
			}
			result.setResultStatusSuccess();
			result.addToMessageList("Certification(s) Deleted Successfully.");
		} catch (final DataIntegrityViolationException e) {
			result.setResultStatusError();
			result.addToErrorList("Certification(s) Already Used. Cannot delete.");
		} catch (final Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
		return result;
	}

	@Override
	public CertificationResult save(CertificationDTO dto) throws Exception {
		final CertificationResult result = createAccountResult(dto);
		try{
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (final ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("Certification Already updated. Please Reload Certification.");
		} catch (final Exception ex) {
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(CertificationResult result) throws Exception {
		CertificationMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
		setBusiness(result);
		certificationDao.save(result.getDomainEntity());
		result.updateDtoIdAndVersion();
	}

	private CertificationResult createAccountResult(CertificationDTO dto) {
		CertificationResult result;
		if (dto.getId() != null && dto.getId() > 0) {
			result = new CertificationResult(certificationDao.findOne(dto.getId()), dto);
		} else {
			result = new CertificationResult(new Certification(), dto);
		}

		return result;
	}

	private String getMessageByAction(CertificationDTO dto) {
		if (dto.getId() == null) {
			return "Certification Added Successfully.";
		} else {
			return "Certification Updated Successfully.";
		}
	}

	private void setBusiness(CertificationResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getBusinessId() != null) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}

	@Override
	public void saveAll(List<CertificationDTO> allDummyData) {
		for (final CertificationDTO dto : allDummyData) {
			try {
				save(dto);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteAll() {
		certificationDao.deleteAll();
	}

	@Override
	public List<CertificationDTO> findAll() {
		final Iterable<Certification> domainList = certificationDao.findAll();
		try {
			return CertificationMapper.getInstance().domainToDTOList(domainList);
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
