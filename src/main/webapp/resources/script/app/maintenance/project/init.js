jQuery(document).ready(function () {	

    $.fn.modalmanager.defaults.resize = true;
    
    $.fn.modal.defaults.spinner = $.fn.modalmanager.defaults.spinner = '<div class="loading-spinner" style="width: 200px; margin-left: -100px;">'
        + '<div class="progress progress-striped active">'
        + '<div class="progress-bar" style="width: 100%;"></div></div></div>';

    Main.init();
    ProjectAdd.init();
    TabWorkOrder.runWorkOrderDataTable(projectId);
    TabScheduledMaintenance.runScheduledMaintenanceDataTable(projectId);
    
    $('#userselectmodelbtn').on('click', function () {
    	TabTechnician.userAddModal();
    });
    
    for (i = 0; i < thymeLeafUsers.length; i++) {
    	TabTechnician.addUserToList(thymeLeafUsers[i].id, thymeLeafUsers[i].fullName, thymeLeafUsers[i].personalCode);
    };
    
    TabTechnician.resetHtmlTable();
	
});