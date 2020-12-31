package com.codex.ecam.controller.inventory.aodReturn;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.constants.inventory.AODReturnStatus;
import com.codex.ecam.dto.inventory.aodReturn.AODReturnDTO;
import com.codex.ecam.result.inventory.AODReturnResult;
import com.codex.ecam.service.asset.api.AssetService;
import com.codex.ecam.service.biz.api.BusinessService;
import com.codex.ecam.service.inventory.api.AODReturnService;

@Controller
@RequestMapping(AODReturnController.REQUEST_MAPPING_URL)
public class AODReturnController {

    public static final String REQUEST_MAPPING_URL = "/aodReturn";

    @Autowired
    private AODReturnService aodReturnService;
    @Autowired
    private BusinessService businessService;
    @Autowired
    private AssetService assetService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        return "inventory/aodReturn/home-view";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
        model.addAttribute("success", success);
        model.addAttribute("error", error);
        return "inventory/aodReturn/home-view";
    }

    /*********************************************************************
     * Modal Views
     *********************************************************************/

    @RequestMapping(value = "/aodReturnItemView", method = RequestMethod.GET)
    public String getAODReturnItemView(Model model) {
        return "inventory/aodReturn/modal/item-modal";
    }

    @RequestMapping(value = "/aodView", method = RequestMethod.GET)
    public String getAODView(Model model) {
        return "inventory/aodReturn/modal/aod-modal";
    }

    @RequestMapping(value = "/aodItemView", method = RequestMethod.GET)
    public String getAODItemView(Model model) {
        return "inventory/aodReturn/modal/aod-item-modal";
    }


    /*********************************************************************
     * CRUD Ops
     *********************************************************************/

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model, AODReturnDTO aodReturnDTO, RedirectAttributes ra) {
        try {
            setCommonData(model, aodReturnService.newAODReturn().getDtoEntity());
            return "inventory/aodReturn/add-view";
        } catch (Exception e) {
            ra.addFlashAttribute("error", new ArrayList<String>().add("Error While Loading Initial Data."));
            return "redirect:/aodReturn/index";
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveOrUpdate(@ModelAttribute("aodReturn") @Valid AODReturnDTO dto, Model model) throws Exception {
        AODReturnResult result;
        if ((dto.getId() != null) && (dto.getId() > 0)) {
            result = aodReturnService.update(dto);
        } else {
            result = aodReturnService.save(dto);
        }
        if (result.getStatus().equals(ResultStatus.ERROR)) {
            model.addAttribute("error", result.getErrorList());
        } else {
            model.addAttribute("success", result.getMsgList());
        }
        setCommonData(model, result.getDtoEntity());
        return "inventory/aodReturn/add-view";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editForm(Integer id, Model model, RedirectAttributes ra) {
        try {
            setCommonData(model, aodReturnService.findById(id).getDtoEntity());
            return "inventory/aodReturn/add-view";
        } catch (Exception e) {
            ra.addFlashAttribute("error", new ArrayList<String>().add("Error occured. Please Try again."));
            return "redirect:/aodReturn/index";
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(Integer id, Model model, RedirectAttributes ra) {
        try {
            AODReturnResult result = aodReturnService.delete(id);
            if (result.getStatus().equals(ResultStatus.ERROR)) {
                ra.addFlashAttribute("error", result.getErrorList());
            } else {
                ra.addFlashAttribute("success", result.getMsgList());
            }
        } catch (Exception e) {
            ra.addFlashAttribute("error", new ArrayList<String>().add(e.getMessage()));
        }

        return "redirect:/aodReturn/index";
    }


    /*********************************************************************
     * Other
     *********************************************************************/
    @RequestMapping(value = "/addWithADO", method = RequestMethod.GET)
    public String addAOD(@RequestParam(value = "arrayParam") List<String> arrayParam, Model model, RedirectAttributes ra) {
        try {
            setCommonData(model, aodReturnService.returnByAODItem().getDtoEntity());
            return "inventory/aodReturn/add-view";
        } catch (Exception e) {
            ra.addFlashAttribute("error", new ArrayList<String>().add("Error While Loading Initial Data."));
            return "redirect:/aodReturn/index";
        }
    }

    @RequestMapping(value = "/getUnFinalizedAODReturns", method = RequestMethod.GET)
    public String getUnFinalizedAODReturns(Model model, RedirectAttributes ra) {
        try {
            List<AODReturnDTO> dtos = aodReturnService.getUnFinalizedAODReturns();
            model.addAttribute("aodReturnList", dtos);
            return "inventory/aodReturn/modal/aod-return-list-modal";
        } catch (Exception e) {
            ra.addFlashAttribute("error", new ArrayList<String>().add("Error While Loading Initial Data."));
            return "redirect:/aodReturn/index";
        }
    }

    @RequestMapping(value = "/statusChange", method = RequestMethod.GET)
    public String receiptOrderStatusChange(Integer id, AODReturnStatus aodReturnStatus, Model model, RedirectAttributes ra) {
        AODReturnResult result = new AODReturnResult(null, null);
        if ((id != null) && (id > 0)) {
            try {
                result = aodReturnService.statusChange(id, aodReturnStatus);
                if (result.getStatus().equals(ResultStatus.ERROR)) {
                    //                    model.addAttribute("error", result.getErrorListLine());
                } else {
                    model.addAttribute("success", result.getMsgList());
                }
                //                model.addAttribute("success", "Successfully updated the status");
            } catch (Exception e) {
                model.addAttribute("error", new ArrayList<String>().add("Error Occurred. Please Try again."));
            }
        } else {
            model.addAttribute("error", new ArrayList<String>().add("Please Save the AOD Return first"));
        }
        setCommonData(model, result.getDtoEntity());
        return "inventory/aodReturn/add-view";
    }


    /*********************************************************************
     * Common / Utility
     *********************************************************************/

    private void setCommonData(Model model, AODReturnDTO dto) {
        model.addAttribute("aodReturn", dto);
        model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
        model.addAttribute("sites", assetService.findAllByLevel());

    }

}
