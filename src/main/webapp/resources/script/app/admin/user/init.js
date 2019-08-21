jQuery(document).ready(function () {

    Main.init();
    UserAdd.init();
    //  $("#cmms-setting-add-modal #delete").addClass("none");

    
    /*********************************************************************
     * User Sites
     *********************************************************************/
    $(document).on('click', '#btnUserSite', function () {
        TabSites.loadSiteModal();
    });

    /* ------------------------------------------------------
     * --------------- User Certification  ------------------
     * ------------------------------------------------------*/
    $(document).on('click', '#newUserCertificationBtn', function () {
        TabUserCertification.loadUserCertificationModal();
    });

    $(document).on('click', '#cmms-setting-add-modal #save', function () {
    	UserAdd.userCertificationView();
    	$('#cmms-setting-add-modal').modal('toggle');
    });
    
    /* ------------------------------------------------------
     * ------------------ User Job Title-------------------------
     * ------------------------------------------------------*/
    
    $(document).on('click', '#job-title-new', function (event) {
        event.preventDefault();
    	UserAdd.jobTitleAddView();       
    });
    
    $(document).on('click', '#jobTitleSave', function (event) {
        event.preventDefault();
    	UserJobTitleAdd.userJobTitleSave();     
    });

    /* ------------------------------------------------------
     * ------------------ User Skill Level-------------------------
     * ------------------------------------------------------*/
    
    $(document).on('click', '#user-skill-level-new', function (event) {
        event.preventDefault();
    	UserAdd.skillLevelAddView();       
    });
    
    $(document).on('click', '#userSkilLevelSave', function (event) {
        event.preventDefault();
        UserSkillAdd.userSkillLevelSave();     
    });
  
    /* ------------------------------------------------------
     * ------------------ User Login-------------------------
     * ------------------------------------------------------*/
    
    $('#changePassword').iCheck({
        checkboxClass: 'icheckbox_minimal',
    });
    
	$('#changePassword').on('ifChecked', function () {
		$("#password, #passwordAgain").removeAttr('disabled');
	});
	
    $('#changePassword').on('ifUnchecked', function () {
    	$("#password, #passwordAgain").attr('disabled', 'disabled');
    });
    
    $("#password").attr('disabled', 'disabled');
    $("#passwordAgain").attr('disabled', 'disabled');
    
    for (var i = 0; i < thymeLeafSites.length; i++) {
        TabSites.addSiteToList(thymeLeafSites[i]);
    };

    for (var i = 0; i < thymeLeafUseCertifications.length; i++) {
        TabUserCertification.addUserCertificationToList(thymeLeafUseCertifications[i]);
    };
    
    TabSites.resetSiteHtmlTable();
    TabUserCertification.resetUserCertificationHtmlTable();

});