package com.neolith.focus.controller.dashboard;
 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController; 

@RestController
@RequestMapping(DashBoardRestController.REQUEST_MAPPING_URL)
public class DashBoardRestController {
	
	public static final String REQUEST_MAPPING_URL = "/restapi/dashboard";
	
}
