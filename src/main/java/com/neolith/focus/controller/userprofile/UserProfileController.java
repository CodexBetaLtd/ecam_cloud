package com.neolith.focus.controller.userprofile;

import com.neolith.focus.constants.ResultStatus;
import com.neolith.focus.dto.admin.CurrencyDTO;
import com.neolith.focus.dto.admin.UserDTO;
import com.neolith.focus.result.userprofile.UserProfileResult;
import com.neolith.focus.service.admin.api.CurrencyService;
import com.neolith.focus.service.admin.api.UserService;
import com.neolith.focus.service.userprofile.api.UserProfileService;
import com.neolith.focus.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping(UserProfileController.REQUEST_MAPPING_URL)
@PropertySource(value = {"classpath:application.properties"})
public class UserProfileController {

	public static final String REQUEST_MAPPING_URL = "/profile";

	@Autowired
	private UserService userService;

	@Autowired
	private CurrencyService currencyService;

	@Autowired
    private UserProfileService userProfileService;

	@Value("${upload.location}")
	private String uploadLocation;

	@RequestMapping(value = "/myaccount", method = RequestMethod.GET)
    public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error, @ModelAttribute("active") final String active) {
        model.addAttribute("success", success);
        model.addAttribute("error", error);
        model.addAttribute("active", active);

		try {
            UserDTO userDTO = userService.findUserById(AuthenticationUtil.getCurrentUser().getUserObj().getId()); 
            if (userDTO.getCurrencyId() != null) {
                CurrencyDTO currencyDTO = currencyService.findById(userDTO.getCurrencyId());
                model.addAttribute("currency", currencyDTO.getSymbol());
            }
            model.addAttribute("userDTO", userDTO);
            return "userprofile/general";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String editAccount(@ModelAttribute("userDTO") @Valid UserDTO userDTO, @RequestParam("avatar") MultipartFile file, Model model, RedirectAttributes redirectAttributes) {
        UserProfileResult result = userProfileService.update(userDTO, file);
        if (result.getStatus().equals(ResultStatus.ERROR)) {
            redirectAttributes.addFlashAttribute("error", result.getErrorList());
            redirectAttributes.addAttribute("active", "edit");
            model.addAttribute("userDTO", userDTO);
            return "redirect:/profile/myaccount";
        } else {
            redirectAttributes.addAttribute("success", result.getMsgList());
            return "redirect:/profile/myaccount";
        }
    }
}
