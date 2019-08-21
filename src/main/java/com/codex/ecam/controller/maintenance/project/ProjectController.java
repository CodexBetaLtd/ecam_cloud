package com.codex.ecam.controller.maintenance.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.dto.asset.AssetDTO;
import com.codex.ecam.dto.maintenance.ProjectDTO;
import com.codex.ecam.result.maintenance.ProjectResult;
import com.codex.ecam.service.asset.api.AssetService;
import com.codex.ecam.service.biz.api.BusinessService;
import com.codex.ecam.service.maintenance.api.ProjectService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(ProjectController.REQUEST_MAPPING_URL)
public class ProjectController {

    public static final String REQUEST_MAPPING_URL = "/project";

    @Autowired
    private ProjectService projectService;

    @Autowired
    private BusinessService businessService;

    @Autowired
    private AssetService assetService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        return "maintenance/project/home-view";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
        model.addAttribute("success", success);
        model.addAttribute("error", error);
        return "maintenance/project/home-view";
    }

    @RequestMapping(value = "/usermodelview", method = RequestMethod.GET)
    public String getTableView(Model model) {
        return "maintenance/modals/user-modal";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model, RedirectAttributes ra) {
        try {
            setCommonData(model, new ProjectDTO());
            return "maintenance/project/add-view";
        } catch (Exception ex) {
            ra.addFlashAttribute("error", new ArrayList<String>().add("Error While Loading Initial Data."));
            return "redirect:/project/index";
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Integer id, Model model, RedirectAttributes ra) {
        try {
            ProjectResult result = projectService.findById(id);
            setCommonData(model, result.getDtoEntity());

            return "maintenance/project/add-view";
        } catch (Exception e) {
            ra.addFlashAttribute("error", new ArrayList<String>().add("Error occured. Please Try again."));

            return "redirect:/project/index";
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveOrUpdate(@ModelAttribute("project") @Valid ProjectDTO project, Model model, RedirectAttributes ra) {

        ProjectResult result;

        if ((project.getId() != null) && (project.getId() > 0)) {
            result = projectService.update(project);
        } else {
            result = projectService.save(project);
        }

        if (result.getStatus().equals(ResultStatus.ERROR)) {
            model.addAttribute("error", result.getErrorList());
        } else {
            model.addAttribute("success", result.getMsgList());
        }
        setCommonData(model, project);
        return "maintenance/project/add-view";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(Integer id, Model model) {
        ProjectResult result = projectService.delete(id);
        
        if (result.getStatus().equals(ResultStatus.ERROR)) {
            model.addAttribute("error", result.getErrorList());
        } else {
            model.addAttribute("success", result.getMsgList());
        }
        
        return "maintenance/project/home-view";
    }

    @RequestMapping(value = "/getsites", method = RequestMethod.GET)
    public @ResponseBody
    List<AssetDTO> selectParent(Integer id) throws Exception {
        List<AssetDTO> siteList = assetService.findSiteByBusinessId(id, AssetCategoryType.LOCATIONS_OR_FACILITIES);
        return siteList;
    }

    private void setCommonData(Model model, ProjectDTO project) {
        model.addAttribute("project", project);
        model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
        model.addAttribute("sites", assetService.findSiteByBusinessId(project.getBusinessId(), AssetCategoryType.LOCATIONS_OR_FACILITIES));
    }

}
