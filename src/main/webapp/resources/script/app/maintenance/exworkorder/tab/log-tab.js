var LogTab = function () {
	
	var initWorkOrderLogTable = function () {
		WorkOrderLog.init(workOrderId);
	};	
	
	return {
    	
    	init: function () {
    		initWorkOrderLogTable();
    	}
    };

}();
