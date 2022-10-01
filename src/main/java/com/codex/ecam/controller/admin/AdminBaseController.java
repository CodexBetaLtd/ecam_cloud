package com.codex.ecam.controller.admin;
 

import com.codex.ecam.controller.BaseController;

public abstract class AdminBaseController extends BaseController {


	public String getLevelTwo(){
		return getLevelOne().concat("/Setting");
	}
	
	public String getLevelTwoNotification(){
		return getLevelOne().concat("/Notification");
	}


}
