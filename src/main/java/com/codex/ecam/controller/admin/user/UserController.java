package com.codex.ecam.controller.admin.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.AssetCategoryType;
import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.constants.UserCertifiCationLevel;
import com.codex.ecam.controller.admin.AdminBaseController;
import com.codex.ecam.dto.admin.CertificationDTO;
import com.codex.ecam.dto.admin.UserDTO;
import com.codex.ecam.dto.admin.UserJobTitleDTO;
import com.codex.ecam.dto.admin.UserSkillLevelDTO;
import com.codex.ecam.dto.admin.usergroup.UserGroupDTO;
import com.codex.ecam.dto.asset.AssetDTO;
import com.codex.ecam.result.admin.UserResult;
import com.codex.ecam.service.admin.api.CertificationService;
import com.codex.ecam.service.admin.api.CurrencyService;
import com.codex.ecam.service.admin.api.UserGroupService;
import com.codex.ecam.service.admin.api.UserJobTitleService;
import com.codex.ecam.service.admin.api.UserService;
import com.codex.ecam.service.admin.api.UserSkillLevelService;
import com.codex.ecam.service.asset.api.AssetService;
import com.codex.ecam.service.biz.api.BusinessService;


@Controller
@RequestMapping(UserController.REQUEST_MAPPING_URL)
public class UserController extends AdminBaseController {

	public static final String REQUEST_MAPPING_URL = "/userProfile";

	@Autowired
	private UserService userService;

	@Autowired
	private AssetService assetService;

	@Autowired
	private CertificationService certificationService;

	@Autowired
	private UserGroupService userGroupService;

	@Autowired
	private BusinessService businessService;

	@Autowired
	private CurrencyService currencyService;

	@Autowired
	private UserSkillLevelService userSkillLevelService;

	@Autowired
	private UserJobTitleService userJobTitleService;


	public String getLevelThree() {
		return getLevelOne().concat(UserController.REQUEST_MAPPING_URL).concat("/");
	}

	public List<Map<String, String>> getBreadcrumbs() {
		return null;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		return "admin/user/home-view";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
		model.addAttribute("success", success);
		model.addAttribute("error", error);

		return "admin/user/home-view";
	}

	@RequestMapping(value = "/usercertificationmodalview", method = {RequestMethod.GET})
	private String getCertificationAddView(Model model) {
		model.addAttribute("userCertifications", certificationService.findAll());
		model.addAttribute("userCertificationLevels", UserCertifiCationLevel.getUserCertifiCationLevelList());

		return "admin/user/modal/user-certification-add-modal";
	}

	@RequestMapping(value = "/userCertificationtypeview", method = {RequestMethod.GET})
	private String getCertificationTypeSelectView(Model model) throws Exception {
		return "admin/user/modal/usercertificationtype/certification-type-select-modal";
	}

	@RequestMapping(value = "/usercertificationadd", method = {RequestMethod.GET})
	private String getCertificationTypeAddView(Model model) throws Exception {
		model.addAttribute("certification", new CertificationDTO());
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
		return "admin/user/modal/usercertificationtype/certification-type-add-modal";
	}

	@RequestMapping(value = "/userjobTitleview", method = {RequestMethod.GET})
	private String getjobTitleAddView(Model model) throws Exception {
		return "admin/user/modal/userjobtitle/job-title-select-modal";
	}

	@RequestMapping(value = "/userskilllevelview", method = {RequestMethod.GET})
	private String getSkillLevelView(Model model) throws Exception {
		return "admin/user/modal/userskilllevel/user-skill-level-select-modal";
	}

	@RequestMapping(value = "/userjobTitleadd", method = RequestMethod.GET)
	public String userJobTitleAdd(Model model) throws Exception {
		model.addAttribute("userJobTitle", new UserJobTitleDTO());
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
		return "admin/user/modal/userjobtitle/job-title-add-modal";
	}

	@RequestMapping(value = "/userjobTitleedit", method = RequestMethod.GET)
	public String userJobTitleEdit(Model model, Integer id) throws Exception {
		model.addAttribute("userJobTitle", userJobTitleService.findById(id));
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
		return "admin/user/modal/userjobtitle/job-title-add-modal";
	}

	@RequestMapping(value = "/userskillLeveleadd", method = RequestMethod.GET)
	public String userSkillLevelAdd(Model model) throws Exception {
		model.addAttribute("userSkillLevel", new UserSkillLevelDTO());
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
		return "admin/user/modal/userskilllevel/user-skill-level-add-modal";
	}

	@RequestMapping(value = "/skilleveledit", method = RequestMethod.GET)
	public String skillLevelEdit(Model model, Integer id) throws Exception {
		model.addAttribute("userSkillLevel", userSkillLevelService.findById(id));
		model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
		return "admin/user/modal/userskilllevel/user-skill-level-add-modal";
	}

	@RequestMapping(value = "/view/modal/currencies", method = RequestMethod.GET)
	public String getCurrencyModalView(Model model, @RequestParam(name = "title", defaultValue = "Currency(s)")String title) {
		model.addAttribute("title", title);
		return "general/table/currencies";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, RedirectAttributes ra) throws Exception {
		try {
			model.addAttribute("userCertifications", certificationService.findAll());
			model.addAttribute("userCertificationLevels", UserCertifiCationLevel.getUserCertifiCationLevelList());
			setCommonData(model, new UserDTO());
			return "admin/user/add-view";
		} catch (final Exception ex) {
			ra.addFlashAttribute("error", new ArrayList<String>().add("Error While Loading Initial Data."));
			return "redirect:/userProfile/index";
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Integer id, Model model, RedirectAttributes ra) {
		try {
			final UserDTO user = userService.findById(id);
			setCommonData(model, user);
			return "admin/user/add-view";
		} catch (final Exception e) {
			ra.addFlashAttribute("error", new ArrayList<String>().add("Error occured. Please Try again."));
			return "redirect:/userProfile/index";
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("user") @Valid UserDTO user, Model model) throws Exception {

		final UserResult result = userService.save(user);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			user = userService.findById(result.getDomainEntity().getId());
			model.addAttribute("success", result.getMsgList());
		}

		setCommonData(model, user);
		return "admin/user/add-view";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model, RedirectAttributes ra) {

		final UserResult result = userService.delete(id);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			ra.addFlashAttribute("error", result.getErrorList());
		} else {
			ra.addFlashAttribute("success", result.getMsgList());
		}

		return "redirect:/userProfile/index";
	}

	@RequestMapping(value = "/getUserList", method = {RequestMethod.GET})
	private String getUserList(Model model) {
		try {
			model.addAttribute("userList", userService.findAll());
		} catch (final Exception ex) {
			ex.printStackTrace();
			model.addAttribute("userList", new ArrayList<UserDTO>());
		}

		return "admin/user/home-view";
	}

	@RequestMapping(value = "/sitemodalview", method = {RequestMethod.GET})
	private String getSiteAddView(Integer id, Model model) throws Exception {
		final List<AssetDTO> sites = assetService.findSiteByBusinessId(id, AssetCategoryType.LOCATIONS_OR_FACILITIES);
		model.addAttribute("sites", sites);
		model.addAttribute("userGroups", sites.size() > 0 ? userGroupService.findAll() : new ArrayList<UserGroupDTO>());

		return "admin/user/modal/site-add-modal";
	}

	@RequestMapping(value = "/delete-multiple", method = RequestMethod.GET)
	public String deleteMultiple(Integer ids[], Model model) {

		try {
			final UserResult result = userService.deleteMultiple(ids);
			if (result.getStatus().equals(ResultStatus.ERROR)) {
				model.addAttribute("error", result.getErrorList().get(0));
			} else {
				model.addAttribute("success", result.getMsgList().get(0));
			}
		} catch (final DataIntegrityViolationException e) {
			model.addAttribute("error", "User already assigned. Please remove from where assigned and try again.");
		}  catch (final Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "admin/user/home-view";
	}

	private void setCommonData(Model model, UserDTO user) throws Exception {
		model.addAttribute("userDTO", user);
		model.addAttribute("levelOne", getLevelOne());
		model.addAttribute("levelTwo", getLevelTwo());
		model.addAttribute("levelThree", getLevelThree());
		model.addAttribute("businessList", businessService.findAllActualBusinessByLevel());
		model.addAttribute("userCertifications", certificationService.findAll());
		model.addAttribute("userGroupDTOList", userGroupService.findAll());
		model.addAttribute("userCertificationLevels", UserCertifiCationLevel.getUserCertifiCationLevelList());
		model.addAttribute("currencies", currencyService.findAll());
		model.addAttribute("userJobTitles", userJobTitleService.findAll());
		model.addAttribute("userSkillLevels", userSkillLevelService.findAll());
	}

}
