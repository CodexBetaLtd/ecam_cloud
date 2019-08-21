var TabLog = function () {
	
	var initScheduledMaintenanceLogTable = function () {
		ScheduledMaintenanceLog.init(scheduledMaintenanceId);
	};	
	
	return {
    	
    	init: function () {
    		initScheduledMaintenanceLogTable();
    	}
    };

}();
