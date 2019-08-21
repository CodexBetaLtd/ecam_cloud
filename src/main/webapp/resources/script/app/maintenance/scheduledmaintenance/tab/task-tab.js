var TabTask = function () {  
	
    /**********************************************************
     * Init Buttons
     * ********************************************************/
	
	var initButtons = function () {
		
		$('#btn-new-scheduled-task').on('click', function () {
			if (triggers.length > 0) {
	        	TabTask.smTaskAddModal();
	        } else {
	            alert("Please Add Trigger First");
	        }
        });	
		
		$('#btn-new-scheduled-task-group').on('click', function () {
			if (triggers.length > 0) {
				TabTask.smTaskGroupAddView();
	        } else {
	            alert("Please Add Trigger First");
	        }			
        });
		
	};
	
    /**********************************************************
     * Init Modals
     * ********************************************************/
	
	var smTaskGroupAddView = function () {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../scheduledmaintenance/smTaskGroupView';
            $modal.load(url, '', function () {
                TaskGroupAddModal.init();
                $modal.modal();
            });
        }, 1000);
    };

    var smTaskAddModal = function () {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../scheduledmaintenance/smTaskView';
            $modal.load(url, '', function () {
            	TaskAddModal.init("master-modal-datatable");
                $modal.modal();
            });
        }, 1000);
    };
    
    var smTaskEditModal = function (index) {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../scheduledmaintenance/smTaskView';
            $modal.load(url, '', function () {
            	TaskAddModal.init('master-modal-datatable');
            	TaskAddModal.fillScheduledTask(getTaskByIndex(index));
                $modal.modal();
            });
        }, 1000);
    };
    
    var getTaskByIndex = function (index) { 
    	for (var i = 0; i < scheduledTasks.length; i++ ) {
    		if ( scheduledTasks[i].index == index ) {
    			return scheduledTasks[i];
    		}
    	}     
    };
    
    var getCurrentTaskIndex = function () {    	
    	var currentIndex = 0;    	
    	$.each(scheduledTasks, function (index, smTask) {    		
    		if (smTask.index >= currentIndex) {
    			currentIndex = ( smTask.index + 1 );
    		}
        });
    	
    	return currentIndex;
    };

    var initScheduledTaskTable = function () {
    	
        $('#tbl_scheduled_task > tbody').html("");
        if (scheduledTasks.length > 0) {
            $.each(scheduledTasks, function (index, task) {
                var row = "<tr> " +
                    "<input type='hidden' name='scheduledTasks[" + index + "].id' value='" + CustomComponents.nullValueReplace(task.id) + "'> " +
                    "<input type='hidden' name='scheduledTasks[" + index + "].index' value='" + CustomComponents.nullValueReplace(task.index) + "'> " +
                    "<input type='hidden' name='scheduledTasks[" + index + "].version' value='" + CustomComponents.nullValueReplace(task.version) + "'> " +
                    "<input type='hidden' name='scheduledTasks[" + index + "].triggerIndex'  value=' " + CustomComponents.nullValueReplace(task.triggerIndex) + "'> " +
                    "<input type='hidden' name='scheduledTasks[" + index + "].taskGroupId'  value=' " + CustomComponents.nullValueReplace(task.taskGroupId) + "'> " +
                    "<input type='hidden' name='scheduledTasks[" + index + "].description' value='" + CustomComponents.nullValueReplace(task.description) + "'> " +
                    "<input type='hidden' name='scheduledTasks[" + index + "].taskType' value='" + CustomComponents.nullValueReplace(task.taskType) + "'> " +
                    "<input type='hidden' name='scheduledTasks[" + index + "].assetId' value='" + CustomComponents.nullValueReplace(task.assetId) + "'> " +
                    "<input type='hidden' name='scheduledTasks[" + index + "].assetName' value='" + CustomComponents.nullValueReplace(task.assetName) + "'> " +
                    "<input type='hidden' name='scheduledTasks[" + index + "].userId' value='" + CustomComponents.nullValueReplace(task.userId) + "'> " +
                    "<input type='hidden' name='scheduledTasks[" + index + "].userName' value='" + CustomComponents.nullValueReplace(task.userName) + "'> " +
                    "<input type='hidden' name='scheduledTasks[" + index + "].estimatedHours' value='" + CustomComponents.nullValueReplace(task.estimatedHours) + "'> " +
                    "<td> " + ( index + 1 ) + "</td>" +
                    "<td> " + task.assetName + "</td>" +
                    "<td> " + task.description + "</td>" +
                    "<td> " + task.taskType + "</td>" +
                    "<td> " + task.estimatedHours + "</td>" +
                    "<td class='center'>" +
                    "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
                    ButtonUtil.getCommonBtnEdit("TabTask.smTaskEditModal", task.index) +
                    ButtonUtil.getCommonBtnDelete("TabTask.removeSmTask", task.index) +
                    "</div>" +
                    "</td></tr>";
                
                $('#tbl_scheduled_task > tbody:last-child').append(row);
            });
        } else {
            CustomComponents.emptyTableRow("tbl_scheduled_task", 6, "Please Add Tasks for the Scheduled Maintenance.");
        }
    };
    
    var removeSmTask = function (index) {
    	
    	if (!isSmTaskUsed(index)) {
    		
    		$.each(scheduledTasks, function (i, smTask) {    		
        		if (smTask.index == index) {
        			scheduledTasks.splice(i, 1);
        			return false;
        		}
            });            
    		initScheduledTaskTable();
    	}    	
    	
    };
    
    var isSmTaskUsed = function (smTaskIndex) {
    	var isTaskUsed = false;
    	
    	if ( isTaskUsedInParts(smTaskIndex) ) {
    		isTaskUsed = true;
    		alert("Scheduled Task Already Used in the Parts. Please Remove Them First..!!");
    	} 
    	
    	return isTaskUsed;
    };
    
    var isTaskUsedInParts = function (smTaskIndex) {
    	
    	var isTaskUsedInParts = false;
    	
    	$.each(parts, function (index, part) {
    		if ( part.partTaskIndex == smTaskIndex ) {
    			isTaskUsedInParts = true;
    		}
    	});
    	
    	return isTaskUsedInParts;
    };

    return {
    	
    	init : function () {
    		initButtons();
    		initScheduledTaskTable();
    	},

    	initScheduledTaskTable: function () {
    		initScheduledTaskTable();
        },
        
        smTaskGroupAddView: function () {
        	smTaskGroupAddView();
        },
        
        smTaskAddModal: function () {
        	smTaskAddModal();
        },
        
        smTaskEditModal: function (index) {
        	smTaskEditModal(index);
        },
        
        removeSmTask: function (index) {
        	removeSmTask(index);
        },
        
        getTaskByIndex: function (index) {
        	return getTaskByIndex(index);
        },
        
        getCurrentTaskIndex: function () {
        	return getCurrentTaskIndex();
        }
    };

}();