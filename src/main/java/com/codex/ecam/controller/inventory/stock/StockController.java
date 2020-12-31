package com.codex.ecam.controller.inventory.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codex.ecam.constants.ResultStatus;
import com.codex.ecam.constants.inventory.StockType;
import com.codex.ecam.dto.inventory.stock.StockDTO;
import com.codex.ecam.dto.inventory.stock.StockHistoryDTO;
import com.codex.ecam.dto.inventory.stock.StockViewFilterDTO;
import com.codex.ecam.result.inventory.StockResult;
import com.codex.ecam.service.biz.api.BusinessService;
import com.codex.ecam.service.inventory.api.StockService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(StockController.REQUEST_MAPPING_URL)
public class StockController {

    public static final String REQUEST_MAPPING_URL = "/stock";

    @Autowired
    private StockService stockService;

	@Autowired
	private BusinessService businessService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("stock", new StockViewFilterDTO());
        model.addAttribute("stockList", new ArrayList<StockDTO>());
        return "inventory/stock/home-view";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
        model.addAttribute("success", success);
        model.addAttribute("error", error);
        model.addAttribute("stock", new StockViewFilterDTO());
        model.addAttribute("stockList", new ArrayList<StockDTO>());
        return "inventory/stock/home-view";
    }

    @RequestMapping(value = "/stockLedger", method = RequestMethod.GET)
    public String indexStockLedger(Model model, @ModelAttribute("success") final ArrayList<String> success, @ModelAttribute("error") final ArrayList<String> error) {
        model.addAttribute("success", success);
        model.addAttribute("error", error);
        return "inventory/stockLedger/home-view";
    }


    /*********************************************************************
     * Stock Modal Views
     *********************************************************************/

    @RequestMapping(value = "/partView", method = RequestMethod.GET)
    public String getAssetView(Model model) {
        return "inventory/stock/modal/part-select-modal";
    }

    @RequestMapping(value = "/siteView", method = RequestMethod.GET)
    public String getSiteView(Model model) {
        return "general/modal/dt-modal-site";
    }

    @RequestMapping(value = "/warehouseView", method = RequestMethod.GET)
    public String getWarehouseView(Model model) {
        return "inventory/stock/modal/warehouse-select-modal";
    }

    @RequestMapping(value = "/itemView", method = RequestMethod.GET)
    public String getItemView(Model model) {
        return "general/modal/dt-modal-item";
    }


    /*********************************************************************
     * Stock Functions
     *********************************************************************/

    @RequestMapping(value = {"/getStock"}, method = {RequestMethod.GET, RequestMethod.POST}, params = "cancel")
    public String cancel(@ModelAttribute("stockViewFilterDTO") @Valid StockViewFilterDTO stockViewFilterDTO, Model model) throws Exception {
        model.addAttribute("stock", new StockViewFilterDTO());
        model.addAttribute("stockList", new ArrayList<StockDTO>());
        return "inventory/stock/home-view";
    }

    @RequestMapping(value = "/getStock", method = RequestMethod.POST, params = "init")
    public String init(Model model) throws Exception {
        model.addAttribute("stock", new StockViewFilterDTO());
        model.addAttribute("stockList", new ArrayList<StockDTO>());
        return "inventory/stock/add-view";
    }

    @RequestMapping(value = "/getStock", method = RequestMethod.POST, params = "view")
    public String find(@ModelAttribute("stockViewFilterDTO") @Valid StockViewFilterDTO stockViewFilterDTO, Model model) throws Exception {
        List<StockDTO> stockDetailList = stockService.getStockDetailList(stockViewFilterDTO);
        model.addAttribute("stock", stockViewFilterDTO);
        model.addAttribute("stockList", stockDetailList);
        return "inventory/stock/add-view";
    }

	@RequestMapping(value = "/notification-add-modal-view", method = RequestMethod.GET)
    public String notificationAddView(Model model) {
        return "inventory/stock/modal/notification-add-modal";
    }

	@RequestMapping(value = "/user-select-modal-view", method = RequestMethod.GET)
	public String getUserSelectView(Model model) {
		return "inventory/stock/modal/user-select-modal";
	}

    @RequestMapping(value = "/item", method = RequestMethod.GET)
    public String editForm(Integer itemId, String itemName, Model model, RedirectAttributes ra) {
        try {
            StockViewFilterDTO stockViewFilterDTO = new StockViewFilterDTO();
            stockViewFilterDTO.setItemName(itemName);
            stockViewFilterDTO.setItemId(itemId);
            List<StockDTO> stockDetailList = stockService.getStockDetailList(stockViewFilterDTO);
            model.addAttribute("stock", stockViewFilterDTO);
            model.addAttribute("stockList", stockDetailList);
        } catch (Exception e) {
            ra.addFlashAttribute("error", new ArrayList<String>().add("Error occured. Please Try again."));
        }
        return "inventory/stock/add-view";

    }

    @RequestMapping(value = "/ledger-modal-view", method = RequestMethod.GET)
    public String getStockList(Model model, RedirectAttributes ra) {
        return "inventory/stock/modal/stock-ledger-view-modal";
    }

    /*********************************************************************
     * Stock History Functions
     *********************************************************************/

    @RequestMapping(value = "/stockHistory", method = RequestMethod.GET)
    public String init(Integer stockId, Model model, RedirectAttributes ra) {
        try {
            List<StockHistoryDTO> stockHistoryDTOs;
            stockHistoryDTOs = stockService.findStockHistoryByStock(stockId);
            model.addAttribute("stockHistory", new StockHistoryDTO());
            model.addAttribute("stockHistoryList", stockHistoryDTOs);
        } catch (Exception e) {
            ra.addFlashAttribute("error", new ArrayList<String>().add("Error Occurred. Please Try again."));
        }
        return "inventory/stockHistory/add-view";
    }

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("stock") @Valid StockDTO stock, Model model) throws Exception {

		StockResult result = stockService.save(stock);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			model.addAttribute("error", result.getErrorList());
		} else {
			model.addAttribute("success", result.getMsgList());
		}

		setCommonData(model, result.getDtoEntity());
		return "inventory/stock/add-view";
	}

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addForm(Model model, RedirectAttributes ra) {
		try {
			setCommonData(model, new StockDTO());
			return "inventory/stock/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error While Loading Initial Data."));
			return "redirect:/stock/index";
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editForm(Integer id, Model model, RedirectAttributes ra) {
		try {
			StockDTO dto = stockService.findById(id);
			setCommonData(model, dto);
			return "inventory/stock/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error occured. Please Try again."));
			return "redirect:/stock/index";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model, RedirectAttributes ra) {

		StockResult result = stockService.delete(id);

		if (result.getStatus().equals(ResultStatus.ERROR)) {
			ra.addFlashAttribute("error", result.getErrorList());
		} else {
			ra.addFlashAttribute("success", result.getMsgList());
		}
		return "redirect:/stock/index";
	}
	
	@RequestMapping(value = "/createstock", method = RequestMethod.GET)
	public String createStock(Integer partId, Model model, RedirectAttributes ra) {
		try {
			StockDTO dto = stockService.createNewStock(partId);
			setCommonData(model, dto);
			return "inventory/stock/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error occured. Please Try again."));
			return "redirect:/stock/index";
		}
	}
	@RequestMapping(value = "/createrefubishstock", method = RequestMethod.GET)
	public String createRefurbishStock(Integer partId, Model model, RedirectAttributes ra) {
		try {
			StockDTO dto = stockService.createNewStock(partId);
			setCommonData(model, dto);
			return "inventory/stock/add-view";
		} catch (Exception e) {
			ra.addFlashAttribute("error", new ArrayList<>().add("Error occured. Please Try again."));
			return "redirect:/stock/index";
		}
	}

    private void setCommonData(Model model, StockDTO stock) {
        model.addAttribute("stock", stock);
        model.addAttribute("businesses", businessService.findAllActualBusinessByLevel());
		model.addAttribute("stockTypes", StockType.getPartTypes());
	}


}
