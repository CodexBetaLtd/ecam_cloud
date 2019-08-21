package com.neolith.focus.controller.admin;
 

import com.neolith.focus.controller.BaseController;

public abstract class AdminBaseController extends BaseController {


	public String getLevelTwo(){
		return getLevelOne().concat("/Setting");
	}
	
	public String getLevelTwoNotification(){
		return getLevelOne().concat("/Notification");
	}


}
