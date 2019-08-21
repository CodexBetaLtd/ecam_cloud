package com.neolith.focus.controller.biz.notification;

import com.neolith.focus.constants.AssetCategoryType;
import com.neolith.focus.constants.NotificationType;
import com.neolith.focus.constants.ResultStatus;
import com.neolith.focus.controller.admin.AdminBaseController;
import com.neolith.focus.dto.biz.notification.NotificationDTO;
import com.neolith.focus.result.notification.NotificationResult;
import com.neolith.focus.service.asset.api.AssetService;
import com.neolith.focus.service.biz.api.BusinessService;
import com.neolith.focus.service.notification.api.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping(NotificationController.REQUEST_MAPPING_URL)
public class NotificationController extends AdminBaseController {

    public static final String REQUEST_MAPPING_URL = "/notification";

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private BusinessService businessService;
    @Autowired
    private AssetService assetService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        try {
            model.addAttribute("folderType", "INBOX");
            model.addAttribute("notificationList", notificationService.findAllNotification(NotificationType.INBOX_NOTIFICATION));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "biz/notification/inbox-view";
    }

    @RequestMapping(value = "/inbox", method = RequestMethod.GET)
    public String inbox(Model model) {
        model.addAttribute("folderType", "Inbox");
        return "biz/notification/inbox-view";
    }

    @RequestMapping(value = "/outbox", method = RequestMethod.GET)
    public String outbox(Model model) {
        model.addAttribute("folderType", "Outbox");
        return "biz/notification/outbox-view";
    }

    @RequestMapping(value = "/trash", method = RequestMethod.GET)
    public String trash(Model model) {
        model.addAttribute("folderType", "Trash");
        return "biz/notification/trash-view";
    }

	/*
     *=-=-=-=-=-=-=-=-=-= Modals =-=-=-=-=-=-=-=-=-=-=-=-=
	 * */
    @RequestMapping(value = "/notificationUserModal", method = RequestMethod.GET)
    public String getUserDataTableModal(Model model) {
        return "biz/notification/modal/user-modal";
    }


    /*
     *=-=-=-=-=-=-=-=-=-= CRUD =-=-=-=-=-=-=-=-=-=-=-=-=
     * */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newUser(Model model, RedirectAttributes ra) {
        try {
            NotificationResult result = notificationService.newNotification();
            setCommonData(model, result.getDtoEntity());
            model.addAttribute("folderType", "New");
            if (result.getStatus().equals(ResultStatus.ERROR)) {
                ra.addFlashAttribute("error", result.getErrorList());
            } else {
                ra.addFlashAttribute("success", result.getMsgList());
            }
            return "biz/notification/add-view";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:/notification/";
        }
    }

    @RequestMapping(value = "/save", method = {RequestMethod.POST, RequestMethod.GET})
    public String saveUser(@ModelAttribute("notificationDTO") NotificationDTO notificationDTO, Model model, RedirectAttributes ra) {
        try {
            NotificationResult result = notificationService.saveNotification(notificationDTO);
            setCommonData(model, result.getDtoEntity());
            if (result.getStatus().equals(ResultStatus.ERROR)) {
                ra.addFlashAttribute("error", result.getErrorList());
            } else {
                ra.addFlashAttribute("success", result.getMsgList());
            }
            return "redirect:/notification/inbox";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:/notification/new";
        }

    }

    @RequestMapping(value = "/find", method = {RequestMethod.GET})
    public String findUserById(Integer id, Model model, RedirectAttributes ra) {
        try {
            NotificationResult result = notificationService.findNotificationById(id);
            setCommonData(model, result.getDtoEntity());
            model.addAttribute("folderType", "Edit");
            if (result.getStatus().equals(ResultStatus.ERROR)) {
                ra.addFlashAttribute("error", result.getErrorList());
            } else {
                ra.addFlashAttribute("success", result.getMsgList());
            }
            return "redirect:/notification/new";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:/notification/";
        }
    }

    @RequestMapping(value = "/preview", method = {RequestMethod.GET})
    public String findUserPreviewById(Integer id, Model model, RedirectAttributes ra) {
        try {
            NotificationResult result = notificationService.findNotificationById(id);
            setCommonData(model, result.getDtoEntity());
            if (result.getStatus().equals(ResultStatus.ERROR)) {
                ra.addFlashAttribute("error", result.getErrorList());
            } else {
                ra.addFlashAttribute("success", result.getMsgList());
            }
            return "biz/notification/notification-preview";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:/notification/inbox";
        }
    }

    @RequestMapping(value = "/trashItem", method = {RequestMethod.GET})
    public String moveToTrash(Integer id, Model model, RedirectAttributes ra) {
        try {
            NotificationResult result = notificationService.toggleTrashedNotification(Boolean.TRUE, id);
            if (result.getStatus().equals(ResultStatus.ERROR)) {
                ra.addFlashAttribute("error", result.getErrorList());
            } else {
                ra.addFlashAttribute("success", result.getMsgList());
            }
            return "redirect:/notification/trash";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:/notification/inbox";
        }
    }

    @RequestMapping(value = "/undoTrashItem", method = {RequestMethod.GET})
    public String undoMoveToTrash(Integer id, Model model, RedirectAttributes ra) {
        try {
            NotificationResult result = notificationService.toggleTrashedNotification(Boolean.FALSE, id);
            if (result.getStatus().equals(ResultStatus.ERROR)) {
                ra.addFlashAttribute("error", result.getErrorList());
            } else {
                ra.addFlashAttribute("success", result.getMsgList());
            }
            return "redirect:/notification/trash";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:/notification/inbox";
        }
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public String deleteById(Integer id, RedirectAttributes ra) {
        try {
            NotificationResult result = notificationService.deleteNotification(id);
            if (result.getStatus().equals(ResultStatus.ERROR)) {
                ra.addFlashAttribute("error", result.getErrorList());
            } else {
                ra.addFlashAttribute("success", result.getMsgList());
            }
            return "redirect:/notification/";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:/notification/trash";
        }
    }


    @RequestMapping(value = "/replyTo", method = {RequestMethod.GET})
    public String replyTo(Integer id, Model model, RedirectAttributes ra) {
        try {
            NotificationResult result = notificationService.replyNotification(id);
            model.addAttribute("folderType", "Reply");
            setCommonData(model, result.getDtoEntity());
            if (result.getStatus().equals(ResultStatus.ERROR)) {
                ra.addFlashAttribute("error", result.getErrorList());
            } else {
                ra.addFlashAttribute("success", result.getMsgList());
            }
            return "biz/notification/add-view";
        } catch (Exception ex) {
            ex.printStackTrace();
            ra.addFlashAttribute("error", "Error occurred. Please Try again.");
            return "redirect:/notification/inbox";
        }
    }

    @RequestMapping(value = "/forwardTo", method = {RequestMethod.GET})
    public String forwardTo(Integer id, Model model, RedirectAttributes ra) {
        try {
            NotificationResult result = notificationService.forwardNotification(id);
            setCommonData(model, result.getDtoEntity());
            if (result.getStatus().equals(ResultStatus.ERROR)) {
                ra.addFlashAttribute("error", result.getErrorList());
            } else {
                ra.addFlashAttribute("success", result.getMsgList());
            }
            return "biz/notification/add-view";
        } catch (Exception ex) {
            ex.printStackTrace();
            ra.addFlashAttribute("error", "Error occurred. Please Try again.");
            return "redirect:/notification/inbox";
        }
    }



    /*********************************************************************
     * Common Data
     *********************************************************************/
    private void setCommonData(Model model, NotificationDTO notificationDTO) {
        model.addAttribute("notificationDTO", notificationDTO);
        model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
        model.addAttribute("sites", assetService.findSiteByBusinessId(notificationDTO.getBusinessId(), AssetCategoryType.LOCATIONS_OR_FACILITIES));
    }


}