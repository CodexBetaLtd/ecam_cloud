package com.codex.ecam.controller.admin.cmmssettings;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codex.ecam.dto.admin.AccountDTO;
import com.codex.ecam.dto.admin.AssetBrandDTO;
import com.codex.ecam.dto.admin.AssetEventTypeDTO;
import com.codex.ecam.dto.admin.AssetModelDTO;
import com.codex.ecam.dto.admin.CertificationDTO;
import com.codex.ecam.dto.admin.ChargeDepartmentDTO;
import com.codex.ecam.dto.admin.CountryDTO;
import com.codex.ecam.dto.admin.CurrencyDTO;
import com.codex.ecam.dto.admin.MaintenanceTypeDTO;
import com.codex.ecam.dto.admin.MeterReadingUnitDTO;
import com.codex.ecam.dto.admin.PriorityDTO;
import com.codex.ecam.dto.admin.UserJobTitleDTO;
import com.codex.ecam.dto.admin.UserSkillLevelDTO;
import com.codex.ecam.dto.biz.business.BusinessClassificationDTO;
import com.codex.ecam.dto.biz.business.BussinessTypeDTO;
import com.codex.ecam.dto.maintenance.miscellaneousExpense.MiscellaneousExpenseTypeDTO;
import com.codex.ecam.repository.FocusDataTablesInput;
import com.codex.ecam.service.admin.api.AccountService;
import com.codex.ecam.service.admin.api.AssetBrandService;
import com.codex.ecam.service.admin.api.AssetEventTypeService;
import com.codex.ecam.service.admin.api.AssetModelService;
import com.codex.ecam.service.admin.api.CertificationService;
import com.codex.ecam.service.admin.api.ChargeDepartmentService;
import com.codex.ecam.service.admin.api.CountryService;
import com.codex.ecam.service.admin.api.CurrencyService;
import com.codex.ecam.service.admin.api.MaintenanceTypeService;
import com.codex.ecam.service.admin.api.MeterReadingUnitService;
import com.codex.ecam.service.admin.api.MiscellaneousExpenseTypeService;
import com.codex.ecam.service.admin.api.PriorityService;
import com.codex.ecam.service.admin.api.UserJobTitleService;
import com.codex.ecam.service.admin.api.UserSkillLevelService;
import com.codex.ecam.service.biz.api.BusinessClassificationService;
import com.codex.ecam.service.biz.api.BusinessTypeService;

@RestController
@RequestMapping(LookupTableRestController.REQUEST_MAPPING_URL)
public class LookupTableRestController {

    public static final String REQUEST_MAPPING_URL = "restapi/lookuptable";

    @Autowired
    private MeterReadingUnitService meaterReadingUnitsService;

    @Autowired
    private MaintenanceTypeService maintenanceTypeService;

    @Autowired
    private PriorityService priorityService;

    @Autowired
    private CertificationService certificationService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private AssetEventTypeService assetEventTypeService;

    @Autowired
    private BusinessTypeService bussinessTypeService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private BusinessClassificationService businessClassificationService;

    @Autowired
    private AccountService accountService;
    
    @Autowired
    private ChargeDepartmentService chargeDeparmentService;
    
    @Autowired
    private UserJobTitleService jobTitleService;
    
    @Autowired
    private UserSkillLevelService userSkillLevelService;
    
    @Autowired
    private AssetBrandService assetBrandService;
    
    @Autowired
    private AssetModelService assetModelService;
    
    @Autowired
    private MiscellaneousExpenseTypeService miscellaneousExpenseTypeService;


    @RequestMapping(value = "/tabledataMeterReadingUnits", method = RequestMethod.GET)
    public DataTablesOutput<MeterReadingUnitDTO> getMeterReadings(@Valid FocusDataTablesInput input) {
        try {
            return meaterReadingUnitsService.findAll(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/tabledatamaintenancetype", method = RequestMethod.GET)
    public DataTablesOutput<MaintenanceTypeDTO> getMaintenanceType(@Valid FocusDataTablesInput input) {
        try {
            return maintenanceTypeService.findAll(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/tabledataPriorities", method = RequestMethod.GET)
    public DataTablesOutput<PriorityDTO> getPriorities(@Valid FocusDataTablesInput input) {
        try {
            return priorityService.findAll(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/tabledataCertification", method = RequestMethod.GET)
    public DataTablesOutput<CertificationDTO> getCertification(@Valid FocusDataTablesInput input) {
        try {
            return certificationService.findAll(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/tabledatacurrency", method = RequestMethod.GET)
    public DataTablesOutput<CurrencyDTO> getCurrancy(@Valid FocusDataTablesInput input) {
        try {
            return currencyService.findAll(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value = "/tabledataAssetEventType", method = RequestMethod.GET)
    public DataTablesOutput<AssetEventTypeDTO> getAssetEventType(@Valid FocusDataTablesInput input) {
    	try {
    		return assetEventTypeService.findAll(input);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }

    @RequestMapping(value = "/asset-event-type-by-business-tabledata", method = RequestMethod.GET)
    public DataTablesOutput<AssetEventTypeDTO> getAssetEventTypeByBusiness(@Valid FocusDataTablesInput input, @Valid Integer bizId) {
        try {
            return assetEventTypeService.getAssetEventTypeByBusiness(input, bizId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/tabledataBussinessType", method = RequestMethod.GET)
    public DataTablesOutput<BussinessTypeDTO> getBussinesType(@Valid FocusDataTablesInput input) {
        try {
            return bussinessTypeService.findAll(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/tabledataCountry", method = RequestMethod.GET)
    public DataTablesOutput<CountryDTO> getCountry(@Valid FocusDataTablesInput input) {
        try {
            return countryService.findAll(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/tabledataBusinessClassification", method = RequestMethod.GET)
    public DataTablesOutput<BusinessClassificationDTO> getBusinessClassification(@Valid FocusDataTablesInput input) {
        try {
            return businessClassificationService.findAllDataTable(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/tabledataaccount", method = RequestMethod.GET)
    public DataTablesOutput<AccountDTO> getAccount(@Valid FocusDataTablesInput input) {
        try {
            return accountService.findAll(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/tabledataChargeDepartment", method = RequestMethod.GET)
    public DataTablesOutput<ChargeDepartmentDTO> getChargeDepartment(@Valid FocusDataTablesInput input) {
    	try {
    		return chargeDeparmentService.findAll(input);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    @RequestMapping(value = "/tabledataUserjobtitle", method = RequestMethod.GET)
    public DataTablesOutput<UserJobTitleDTO> getUserJobTitelList(@Valid FocusDataTablesInput input) {
    	try {
    		return    jobTitleService.findAll(input);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    @RequestMapping(value = "/tabledataUserSkillLevel", method = RequestMethod.GET)
    public DataTablesOutput<UserSkillLevelDTO> getUserSkillLevelList(@Valid FocusDataTablesInput input) {
        try {
            return    userSkillLevelService.findAll(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    @RequestMapping(value = "/tabledataassetmodel", method = RequestMethod.GET)
    public DataTablesOutput<AssetModelDTO> getAssetModel(@Valid FocusDataTablesInput input){
    	try {
    		return assetModelService.findAll(input);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    @RequestMapping(value = "/tabledataassetmodelbybrand", method = RequestMethod.GET)
    public DataTablesOutput<AssetModelDTO> getAssetModelByBrand(@Valid FocusDataTablesInput input, Integer id){
    	try {
    		return assetModelService.findAllByBrand(input, id);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    @RequestMapping(value = "/tabledataassetbrand", method = RequestMethod.GET)
    public DataTablesOutput<AssetBrandDTO> getAssetBrand(@Valid FocusDataTablesInput input){
    	try {
    		return assetBrandService.findAll(input);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    @RequestMapping(value = "/tabledatamiscellaneousexpensetype", method = RequestMethod.GET)
    public DataTablesOutput<MiscellaneousExpenseTypeDTO> getMiscellaneousExpenseType(@Valid FocusDataTablesInput input){
    	try {
			return miscellaneousExpenseTypeService.findAll(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }

}
