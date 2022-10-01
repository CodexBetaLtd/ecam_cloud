
var TaskTab = function () {
	
	/*********************************************************************
     * Init Buttons
     * *******************************************************************/
	var initButtons = function () {
		
		$('#new-wo-task-btn').on('click', function () {
			if (assets.length > 0) {
				TaskTab.taskAddModal();
	        } else {
	            alert("Please Add Assets First.");
	        }			
        });
		
		$('#new-wo-task-frm-group-btn').on('click', function () {			
			if (assets.length > 0) {
				TaskTab.taskGroupAddModal();
	        } else {
	            alert("Please Add Assets First.");
	        }
        });
		
	}; 
	
	/*********************************************************************
     * Init Modals
     * *******************************************************************/
	
	var taskGroupAddModal = function () {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../workorder/task-group-add-modal-view';
            $modal.load(url, '', function () {
            	TaskGroupAddModal.init();
                $modal.modal();
            });
        }, 1000);
    };

    var taskAddModal = function () {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../workorder/task-add-modal-view';
            $modal.load(url, '', function () {
            	TaskAddModal.init();
                $modal.modal();
            });
        }, 1000);
    };

    var taskEditModal = function (index) {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../workorder/task-add-modal-view';
            $modal.load(url, '', function () {
            	TaskAddModal.init();
                fillWorkOrder(index, tasks[index]);
                $modal.modal();
            });
        }, 1000);
    };

    /*********************************************************************
     * Set Data
     * *******************************************************************/
    
    var addTask = function () {
        if ( $("#woTaskIndex").val() != null && $("#woTaskIndex").val() != "" && $("#woTaskIndex").val() >= 0 ) {
            updateTask(tasks[$("#woTaskIndex").val()]);
        } else {
            newTask();
        }
        woTaskTable();
        $('#master-modal-datatable').modal("toggle");
    };

    var newTask = function () {
    	
        var task = [];
    		task['id'] = '';
			task['index'] =  getCurrentTaskIndex();
			task['taskGroupId'] =  $("#woTaskGroupId").val() == null ? '' : $("#woTaskGroupId").val();
    		task['name'] =  $("#woTaskName").val();
            task['description'] =  $("#woTaskDescription").val();
            task['completionNote'] =  $("#woTaskCompletionNote").val();
            task['assignedAssetId'] =  $("#woTaskAsset").val();
            task['assignedAssetName'] =  $("#woTaskAsset :selected").text();
            task['taskType'] =  $("#woTaskTaskType").val(); 
            task['startedDate'] =  $("#woTaskStartDate").val();
            task['estimatedHours'] =  $("#woTaskTimeEstimate").val();
            task['assignedUserId'] =  $("#woTaskAssignedUserId").val();
            task['assignedUserName'] =  $("#woTaskAssignedUserName").val();
            task['completedUserId'] =  $("#woTaskCompletedUserId").val()
            task['completedUserName'] =  $("#woTaskCompletedUserName").val();
            task['completedDate'] =  $("#woTaskCompletedDate").val();
            task['spentHours'] =  $("#woTaskTimeSpent").val();
            task['completionRemark'] =  $("#woTaskCompletionRemark").val();
            task['other'] =  false;
        
        tasks.push(task);
    };
    
    var getCurrentTaskIndex = function () {    	
    	var currentIndex = 0;    	
    	$.each(tasks, function (index, task) {    		
    		if (task.index >= currentIndex) {
    			currentIndex = ( task.index + 1 );
    		}
        });
    	
    	return currentIndex;
    };

    var updateTask = function (task) {
    	task['name'] = $("#woTaskName").val();
    	task['index'] = $("#woTaskIndex").val();
        task['description'] = $("#woTaskDescription").val();
        task['completionNote'] = $("#woTaskCompletionNote").val();
        task['taskType'] = $("#woTaskTaskType").val(); 
        task['assignedAssetId'] = $("#woTaskAsset").val();
        task['assignedAssetName'] = $("#woTaskAsset :selected").text();
        task['assignedUserId'] = $("#woTaskAssignedUserId").val();
        task['assignedUserName'] = $("#woTaskAssignedUserName").val();
        task['completedUserId'] = $("#woTaskCompletedUserId").val();
        task['completedUserName'] = $("#woTaskCompletedUserName").val();
        task['startedDate'] = $("#woTaskStartDate").val();
        task['estimatedHours'] = $("#woTaskTimeEstimate").val();
        task['completedDate'] = $("#woTaskCompletedDate").val();
        task['spentHours'] = $("#woTaskTimeSpent").val();
        task['completionRemark'] = $("#woTaskCompletionRemark").val();
    };

    var fillWorkOrder = function (index, task) {
        $("#woTaskIndex").val(index);
        $("#woTaskId").val(task.id);
        $("#woTaskGroupId").val(task.taskGroupId);
        $("#woTaskName").val(task.name);
        $("#woTaskDescription").val(task.description);
        $("#woTaskCompletionNote").val(task.completionNote);
        $("#woTaskCompletionRemark").val(task.completionRemark);
        $("#woTaskStartDate").val(task.startedDate);
        $("#woTaskTimeEstimate").val(task.estimatedHours);
        $("#woTaskCompletedDate").val(task.completedDate);
        $("#woTaskTimeSpent").val(task.spentHours);
        $("#woTaskTaskType").val(task.taskType).trigger('change');
        $("#woTaskAsset").val(task.assignedAssetId).trigger('change');
        $("#woTaskAssignedUserId").val(task.assignedUserId);
        $("#woTaskAssignedUserName").val(task.assignedUserName);
        $("#woTaskCompletedUserId").val(task.completedUserId);
        $("#woTaskCompletedUserName").val(task.completedUserName);
    };

    /*********************************************************************
     * Init Table
     * *******************************************************************/
    
    var woTaskTable = function () {
        $('#tbl_wo_labour_task >tbody').html("");
        if (tasks.length > 0) {
            $.each(tasks, function (index, task) { 
                var row = "<tr> " +
                    "<input type='hidden' name='tasks[" + index + "].id' value='" + CustomComponents.nullValueReplace(task.id) + "'> " +
                    "<input type='hidden' name='tasks[" + index + "].name' value='" + CustomComponents.nullValueReplace(task.name) + "'> " +
                    "<input type='hidden' name='tasks[" + index + "].index' value='" + CustomComponents.nullValueReplace(task.index) + "'> " +
                    "<input type='hidden' name='tasks[" + index + "].taskGroupId'  value=' " + CustomComponents.nullValueReplace(task.taskGroupId) + "'> " +
                    "<input type='hidden' name='tasks[" + index + "].description' value='" + CustomComponents.nullValueReplace(task.description) + "'> " +
                    "<input type='hidden' name='tasks[" + index + "].taskType' value='" + CustomComponents.nullValueReplace(task.taskType) + "'> " +
                    "<input type='hidden' name='tasks[" + index + "].assignedAssetId' value='" + CustomComponents.nullValueReplace(task.assignedAssetId) + "'> " +
                    "<input type='hidden' name='tasks[" + index + "].assignedUserId' value='" + CustomComponents.nullValueReplace(task.assignedUserId) + "'> " +
                    "<input type='hidden' name='tasks[" + index + "].startedDate' value='" + CustomComponents.nullValueReplace(task.startedDate) + "'> " +
                    "<input type='hidden' name='tasks[" + index + "].estimatedHours' value='" + CustomComponents.nullValueReplace(task.estimatedHours) + "'> " +
                    "<input type='hidden' name='tasks[" + index + "].completedUserId' value='" + CustomComponents.nullValueReplace(task.completedUserId) + "'> " +
                    "<input type='hidden' name='tasks[" + index + "].completedDate' value='" + CustomComponents.nullValueReplace(task.completedDate) + "'> " +
                    "<input type='hidden' name='tasks[" + index + "].spentHours' value='" + CustomComponents.nullValueReplace(task.spentHours) + "'> " +
                    "<input type='hidden' name='tasks[" + index + "].completionNote' value='" + CustomComponents.nullValueReplace(task.completionNote) + "'> " +
                    "<input type='hidden' name='tasks[" + index + "].completionRemark' value='" + CustomComponents.nullValueReplace(task.completionRemark) + "'> " +
                    "<td> " + (index + 1) + "</td>" +
                    "<td> " + CustomComponents.nullValueReplace(task.assignedAssetName) + "</td>" +
                    "<td> " + CustomComponents.nullValueReplace(task.name) + "</td>" +
                    "<td> " + CustomComponents.nullValueReplace(task.description) + "</td>" +
                    "<td> " + CustomComponents.nullValueReplace(task.taskType) + "</td>" +
                    "<td> " + CustomComponents.nullValueReplace(task.estimatedHours) + "</td>" +
                    "<td style='text-align:center'>" +
	                    ButtonUtil.getCommonBtnEdit('TaskTab.taskEditModal', index) +
	                    ButtonUtil.getCommonBtnDelete('TaskTab.woTaskRemove', index) +
                    "</td>" +
                    "</tr>";
                $("#tbl_wo_labour_task > tbody").append(row);
            });
        } else {
            CustomComponents.emptyTableRow("tbl_wo_labour_task", 7, "Please Add Tasks to Work Order.");
        }
    };

    var woTaskRemove = function (index) {
    	if (!isTaskUsed(tasks[index].index)) { 
			tasks.splice(index, 1);
			woTaskTable();  
    	}        
    };
    
    var isTaskUsed = function (taskIndex) {
    	var isTaskUsed = false;
    	
    	if ( isTaskUsedInParts(taskIndex) ) {
    		isTaskUsed = true;
    		alert("Task Already Used in the Parts. Please Remove Them First..!!");
    	} 
    	
    	return isTaskUsed;
    };
    
    var isTaskUsedInParts = function (taskIndex) {
    	
    	var isTaskUsedInParts = false;
    	
    	$.each(parts, function (index, part) {
    		if ( part.woPartTaskIndex == taskIndex ) {
    			isTaskUsedInParts = true;
    			return false
    		}
    	});
    	
    	return isTaskUsedInParts;
    };

    return {
    	
    	init : function () {
    		initButtons();
    		woTaskTable();
    	},

        taskAddModal: function () {
        	taskAddModal();
        },

        addTask: function () {
        	addTask();
        },
        
        taskEditModal: function (index) {
        	taskEditModal(index);
        },
        
        woTaskRemove: function (index) {
            woTaskRemove(index);            
        },
        
        populateTaskTable: function () {
            woTaskTable();
        },
        
        taskGroupAddModal: function () {
        	taskGroupAddModal();
        },

        _add_user_to_taskGroup_task: function (obj) {
            _add_user_to_taskGroup_task(obj);
        },

        _set_task_completed_user: function (obj) {
            _set_task_completed_user(obj);
        }
        
    };
}();
