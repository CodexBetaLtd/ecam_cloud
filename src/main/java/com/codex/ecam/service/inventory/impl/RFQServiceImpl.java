package com.codex.ecam.service.inventory.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.codex.ecam.constants.inventory.RFQStatus;
import com.codex.ecam.dao.admin.CountryDao;
import com.codex.ecam.dao.admin.UserDao;
import com.codex.ecam.dao.asset.AssetDao;
import com.codex.ecam.dao.biz.BusinessDao;
import com.codex.ecam.dao.biz.SupplierDao;
import com.codex.ecam.dao.inventory.PurchaseOrderItemDao;
import com.codex.ecam.dao.inventory.RFQDao;
import com.codex.ecam.dto.inventory.purchaseOrder.PurchaseOrderDTO;
import com.codex.ecam.dto.inventory.rfq.RFQDTO;
import com.codex.ecam.dto.inventory.rfq.RFQFileDTO;
import com.codex.ecam.dto.inventory.rfq.RFQItemDTO;
import com.codex.ecam.dto.inventory.rfq.RFQNotificationDTO;
import com.codex.ecam.dto.inventory.rfq.RFQRepDTO;
import com.codex.ecam.dto.inventory.rfq.RFQStatusChangeDTO;
import com.codex.ecam.mappers.purchasing.RFQFileMapper;
import com.codex.ecam.mappers.purchasing.RFQItemMapper;
import com.codex.ecam.mappers.purchasing.RFQMapper;
import com.codex.ecam.mappers.purchasing.RFQNotificationMapper;
import com.codex.ecam.mappers.purchasing.RFQReportMapper;
import com.codex.ecam.model.admin.User;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderItem;
import com.codex.ecam.model.inventory.purchaseOrder.PurchaseOrderItemRFQItem;
import com.codex.ecam.model.inventory.rfq.RFQ;
import com.codex.ecam.model.inventory.rfq.RFQFile;
import com.codex.ecam.model.inventory.rfq.RFQItem;
import com.codex.ecam.model.inventory.rfq.RFQNotification;
import com.codex.ecam.model.inventory.rfq.RFQStausChangeLog;
import com.codex.ecam.params.VelocityMail;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.result.purchasing.RFQResult;
import com.codex.ecam.service.inventory.api.RFQService;
import com.codex.ecam.util.FileDownloadUtil;
import com.codex.ecam.util.FileUploadUtil;
import com.codex.ecam.util.VelocityEmailSender;

@Service
public class RFQServiceImpl implements RFQService {

	private final static Logger logger = LoggerFactory.getLogger(RFQServiceImpl.class);

	@Autowired
	Environment environment;
	
	@Autowired
	private RFQDao rfqDao;
	
	@Autowired
	private BusinessDao businessDao;
	
	@Autowired
	private AssetDao assetDao;
	
	@Autowired
	private CountryDao countryDao;
	
	@Autowired
	private SupplierDao supplierDao;
	
	@Autowired
	private PurchaseOrderItemDao purchaseOrderItemDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
    private VelocityEmailSender velocityEmailService;

	@Override
	public RFQDTO findById(Integer id) throws Exception {
		RFQ domain = rfqDao.findOne(id);
		if (domain != null) {
			return RFQMapper.getInstance().domainToDto(domain);
		}
		return null;
	}
	
	@Override
	public RFQRepDTO findRFQRepDTOById(Integer id) throws Exception {
		RFQRepDTO repDTO = null;
        try {
            RFQ domain = rfqDao.findOne(id);
            if (domain != null) {
                repDTO = RFQReportMapper.getInstance().domainToRepDTO(domain);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return repDTO;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public RFQResult update(RFQDTO dto) {
		RFQResult result = new RFQResult(null, dto);
		try {
			RFQ domain = rfqDao.findOne(dto.getId());
			result.setDomainEntity(domain);
			saveOrUpdate(result);
			result.addToMessageList("RFQ Updated Successfully.");
		} catch (Exception e) {
			result.setResultStatusError();
			result.addToErrorList(e.getMessage());
		}
		return result;
	}


	@Override
	public PurchaseOrderDTO saveWithPurchaseOrder(RFQDTO rfq) {
		return null;
//		List<Integer> rfqItemIds = save(rfq).getDomainEntity().getRfqItems().stream().map(RFQItem::getId).collect(Collectors.toList());
//		return purchaseOrderService.createPurchaseOrderFromRFQItems(rfqItemIds);
	}

	@Override
	public RFQResult save(RFQDTO dto)  {
		RFQResult result = createPartResult(dto);
		try{
			saveOrUpdate(result);
			result.addToMessageList(getMessageByAction(dto));
		} catch (ObjectOptimisticLockingFailureException e) {
			result.setResultStatusError();
			result.addToErrorList("RFQ Already updated. Please Reload RFQ.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}

		return result;
	}

	private RFQResult createPartResult(RFQDTO dto) {
		RFQResult result;
		if ((dto.getId() != null) && (dto.getId() > 0)) {
			result = new RFQResult(rfqDao.findOne(dto.getId()), dto);
		} else {
			result = new RFQResult(new RFQ(), dto);
		}

		return result;
	}

	private String getMessageByAction(RFQDTO dto) {
		if (dto.getId() == null) {
			return "RFQ Added Successfully.";
		} else {
			return "RFQ Updated Successfully.";
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveOrUpdate(RFQResult result) throws Exception {
		try {
			setRFQStatusChange(result);
			RFQMapper.getInstance().dtoToDomain(result.getDtoEntity(), result.getDomainEntity());
			setRFQData(result);
			rfqDao.save(result.getDomainEntity());
			result.setDtoEntity(findById(result.getDomainEntity().getId()));
		} catch (ObjectOptimisticLockingFailureException ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			result.setResultStatusError();
			result.addToErrorList("RFQ Already updated. Please Reload RFQ.");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			result.setResultStatusError();
			result.addToErrorList(ex.getMessage());
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public RFQResult delete(Integer id) {
		RFQResult result = new RFQResult(null, null);
		try {
			rfqDao.delete(id);
			result.setResultStatusSuccess();
			result.addToMessageList("RFQ Deleted Successfully.");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
			result.setResultStatusError();
			result.addToMessageList("Error Occurred! RFQ Deleted Unsuccessful.".concat(ex.getMessage()));
		}
		return result;
	}

	@Override
	public DataTablesOutput<RFQDTO> findAll(FocusDataTablesInput dataTablesInput) throws Exception {
		DataTablesOutput<RFQ> domainOut = rfqDao.findAll(dataTablesInput);
		DataTablesOutput<RFQDTO> out = RFQMapper.getInstance().domainToDTODataTablesOutput(domainOut);
		return out;
	}


	private void setRFQData(RFQResult result) throws Exception {
		setBusiness(result);
		setSite(result);
		setSupplier(result);
		setSupplierCountry(result);
		setShipToFacility(result);
		setShipToCountry(result);
		setItems(result);
		setRFQFiles(result);
		setRFQNotification(result);
		
		//genaratePDF(result,request,response);
	}
	
//	private void genaratePDF(RFQResult result,HttpServletRequest request, HttpServletResponse response) throws IOException, JRException{
//
//		try {
//			String inputFilePath = "/rfq/rfq_report";
//			String fileName = result.getDtoEntity().getCode();
//	        //List<RFQDTO> dtoList = new ArrayList<>();
//	       
//	        String reportDir = request.getRealPath("").concat("/resources/report/");
//	        OutputStream outputStream = null;
//	        Map<String, Object> params = new HashMap<String, Object>();
//			String uploadFolder = environment.getProperty("upload.rfq.file.folder");
//			String uploadLocation = environment.getProperty("upload.location");
//			 List<RFQRepDTO> dtoList = new ArrayList<>();
//			 String outPutDir=uploadLocation+uploadFolder + "/reports/"+fileName;
//			 dtoList.add(RFQReportMapper.getInstance().repDTOToDto(result.getDtoEntity()));
//			JasperExportManager.exportReportToPdfFile(ReportUtil.getInstance().generatePDF(dtoList, reportDir, params, inputFilePath, outputStream),outPutDir);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
	
	private void setRFQStatusChange(RFQResult result){
		RFQStatus previousStatus=result.getDomainEntity().getRfqStatus();
		RFQStatus currentStatus=result.getDtoEntity().getRfqStatus();
		if(!previousStatus.equals(currentStatus)){
			RFQStausChangeLog changeLog=new RFQStausChangeLog();
			changeLog.setRfqStatus(currentStatus);
			changeLog.setRfq(result.getDomainEntity());
			result.getDomainEntity().getRfqStausChangeLogs().add(changeLog);
		}
	}

	private void setRFQFiles(RFQResult result) throws Exception {
		Set<RFQFile> rfqFiles = new HashSet<>();

		if ( (result.getDtoEntity().getRfqFileDTOs() != null) && (result.getDtoEntity().getRfqFileDTOs().size() > 0) ) {

			Set<RFQFile> currentRfqFiles = result.getDomainEntity().getRfqFiles();

			for (RFQFileDTO rfqFileDTO : result.getDtoEntity().getRfqFileDTOs()) {
				RFQFile rfqFile;

				if (rfqFileDTO.getId() != null) {
					rfqFile = currentRfqFiles.stream().filter((x) -> x.getId().equals(rfqFileDTO.getId())).findAny().orElseGet(RFQFile :: new);
				} else {
					rfqFile = new RFQFile();
				}

				RFQFileMapper.getInstance().dtoToDomain(rfqFileDTO, rfqFile);
				rfqFile.setRfq(result.getDomainEntity());

				rfqFiles.add(rfqFile);
			}
		}
		result.getDomainEntity().setRfqFiles(rfqFiles);
	}
	private void setItems(RFQResult result) throws Exception {
		List<RFQItem> items = new ArrayList<>();

		if (result.getDtoEntity().getItems() != null && result.getDtoEntity().getItems().size() > 0) {

			List<RFQItem> currentItems = result.getDomainEntity().getRfqItems();

			for (RFQItemDTO itemDTO : result.getDtoEntity().getItems()) {

				RFQItem item;

				if (currentItems != null && currentItems.size() > 0) {
					Optional<RFQItem> optionalItem = currentItems.stream().filter((x) -> x.getId() == itemDTO.getItemId()).findAny();
					if (optionalItem.isPresent()) {
						item = optionalItem.get();
					} else {
						item = new RFQItem();
					}
				} else {
					item = new RFQItem();
				}
				createRFQItem(itemDTO, item, result.getDomainEntity());
				items.add(item);
			}
		}
		result.getDomainEntity().setRfqItems(items);
	}

	private void createRFQItem(RFQItemDTO dto, RFQItem domain, RFQ rfq) throws Exception {
		RFQItemMapper.getInstance().dtoToDomain(dto, domain);
		domain.setRfq(rfq);

		if (dto.getItemAssetId() != null && dto.getItemAssetId() > 0) {
			domain.setAsset(assetDao.findOne(dto.getItemAssetId()));
		}
		
		if (dto.getItemPurchaseOrderItemId() != null && dto.getItemPurchaseOrderItemId() > 0) {
			
			PurchaseOrderItem poItem = purchaseOrderItemDao.findOne(dto.getItemPurchaseOrderItemId());
			
			PurchaseOrderItemRFQItem item = new PurchaseOrderItemRFQItem();
			item.setPurchaseOrderItem(poItem);
			item.setRfqItem(domain);
			item.setIsDeleted(false);
			
			domain.getPurchaseOrderItems().add(item);
		}

	}

	private void setBusiness(RFQResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getBusinessId() != null) {
			result.getDomainEntity().setBusiness(businessDao.findOne(result.getDtoEntity().getBusinessId()));
		}
	}

	private void setSite(RFQResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getSiteId() != null) {
			result.getDomainEntity().setSite(assetDao.findOne(result.getDtoEntity().getSiteId()));
		}
	}

	private void setSupplier(RFQResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getSupplierId() != null) {
			result.getDomainEntity().setSupplier(supplierDao.findOne(result.getDtoEntity().getSupplierId()));
		}
	}

	private void setSupplierCountry(RFQResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getSupplierCountry() != null) {
			result.getDomainEntity().setSupplierCountry(countryDao.findOne(result.getDtoEntity().getSupplierCountry()));
		}
	}

	private void setShipToFacility(RFQResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getShipToId() != null) {
			result.getDomainEntity().setShipTo(assetDao.findOne(result.getDtoEntity().getShipToId()));
		}
	}

	private void setShipToCountry(RFQResult result) {
		if (result.getDtoEntity() != null && result.getDtoEntity().getShipToCountry() != null) {
			result.getDomainEntity().setShippingCountry(countryDao.findOne(result.getDtoEntity().getShipToCountry()));
		}
	}

	private void setRFQNotification(RFQResult result)throws Exception {
        Set<RFQNotification> rfqNotifications = new HashSet<>();
        List<RFQNotificationDTO> rfqNotificationDTOs = result.getDtoEntity().getNotificationDTOs();

        if ((rfqNotificationDTOs != null) && (rfqNotificationDTOs.size() > 0)) {
            Set<RFQNotification> currentRFQtNotifications = result.getDomainEntity().getRfqNotifications();
            RFQNotification rfqNotification = new RFQNotification();

            for (RFQNotificationDTO rfqNotificationDTO : rfqNotificationDTOs) {
                if ((currentRFQtNotifications != null) && (currentRFQtNotifications.size() > 0)) {
                	rfqNotification = currentRFQtNotifications.stream().filter((x) -> x.getId().equals(rfqNotificationDTO.getId())).findAny().orElseGet(RFQNotification::new);
                }else {
                	rfqNotification = new RFQNotification();
                }
                createNotification(result.getDomainEntity(), rfqNotificationDTO, rfqNotification);
                rfqNotifications.add(rfqNotification);
            }
        }
        result.getDomainEntity().setRfqNotifications(rfqNotifications);
    }
	
    private void createNotification(RFQ domainEntity, RFQNotificationDTO rfqNotificationDTO, RFQNotification rfqNotification) throws Exception {
        RFQNotificationMapper.getInstance().dtoToDomain(rfqNotificationDTO, rfqNotification);
        rfqNotification.setRfq(domainEntity);
        rfqNotification.setUser(rfqNotificationDTO.getUserId() != null ? userDao.findOne(rfqNotificationDTO.getUserId()) : null);
    }
    /***************************************
     ********** RFQ Status Change **********
     ***************************************/
	@Override
	public RFQResult statusChange(Integer id, RFQStatus rfqStatus) {
		RFQResult  result=new RFQResult(null, null);
		try {
			RFQDTO rfqdto = findById(id);
			RFQStatus rfqPreviousStatus=rfqdto.getRfqStatus();
			if(rfqStatus !=null){
				if(rfqStatus.equals(RFQStatus.SENT)){
					rfqdto.setRfqStatus(RFQStatus.SENT);
					result.addToMessageList("RFQ Status Changes as SENT.");		
				}
				else if(rfqStatus.equals(RFQStatus.RECEIVED)){
					rfqdto.setRfqStatus(RFQStatus.RECEIVED);
					result.addToMessageList("RFQ Status Changes as RECEIVED.");		
				}
				else if(rfqStatus.equals(RFQStatus.CONFIRM)){
					rfqdto.setRfqStatus(RFQStatus.CONFIRM);
					result.addToMessageList("RFQ Status Changes as CONFIRM.");		
				}
				else if(rfqStatus.equals(RFQStatus.CANCELLED)){
					rfqdto.setRfqStatus(RFQStatus.DRAFT);
					result.addToMessageList("RFQ Status Changes as DRAFT.");		
				}
			}

			 result=update(rfqdto);
			 sendStatusChangeMail(result,rfqPreviousStatus);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void sendStatusChangeMail(RFQResult  result,RFQStatus prestatus){
		List<RFQNotification> domainOut = rfqDao.findByNotificationById(result.getDtoEntity().getId());
		for(RFQNotification notification:domainOut){
			if(notification.getUser()!=null && notification.getNotifyOnStatusChannged()){
				rfqStatusChangeMail(result,prestatus,notification.getUser());
			}
		}
	}

	private void rfqStatusChangeMail(RFQResult  result,RFQStatus prestatus, User user){
        VelocityMail velocityMail = new VelocityMail();
        velocityMail.getModel().put("rfqCode", result.getDtoEntity().getCode());
		velocityMail.getModel().put("previousstatus", prestatus.getName());
		velocityMail.getModel().put("currentstatus", result.getDtoEntity().getRfqStatus().getName());
		velocityMail.setSubject("RFQ status change - "+ result.getDtoEntity().getCode());
		velocityMail.setTo(user.getEmailAddress());
		velocityMail.setVmTemplate("rfqstatuschange");
		velocityEmailService.sendEmail(velocityMail);
	}
	
	

	@Override
	public RFQDTO createRFQFromPoItems(String poItemIds) {
		RFQDTO dto = new RFQDTO();		
		List<RFQItemDTO> items = new ArrayList<>();		
		String[] ids = poItemIds.split(",");	
		
		RFQItemDTO item;
		
		for ( String id : ids ) {
			item = new RFQItemDTO();			
			PurchaseOrderItem poItem = purchaseOrderItemDao.findOne(Integer.parseInt(id));
			item.setItemAssetId(poItem.getAsset().getId());
			item.setItemAssetName(poItem.getAsset().getName());
			item.setItemQtyRequested(poItem.getQtyOnOrder());
			item.setItemPurchaseOrderItemId(poItem.getId());
			item.setItemPurchaseOrderCodes(poItem.getPurchaseOrder().getCode());
			items.add(item);
		}
		
		dto.setItems(items);		
		return dto;
	}

	
	/************************************
	 ************* RFQ File Upload ******
	************************************/
	@Override
	public String rfqFileUpload(MultipartFile file, String refId) throws Exception {
		String uploadFolder = environment.getProperty("upload.rfq.file.folder");
		String uploadLocation = environment.getProperty("upload.location");
		try {
			return FileUploadUtil.createFile(file, refId, uploadFolder, uploadLocation);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/************************************
	 ********** RFQ File Download ******
	************************************/
	@Override
	public void rfqFileDownload(Integer id, HttpServletResponse response) throws Exception {
		String uploadLocation = new File(environment.getProperty("upload.location")).getPath();
		if (id != null) {
			RFQFile file = rfqDao.findByFileId(id);
			String externalFilePath = uploadLocation + file.getFileLocation();
			FileDownloadUtil.flushFile(externalFilePath, file.getFileType(), response);
		}
		
	}



}
