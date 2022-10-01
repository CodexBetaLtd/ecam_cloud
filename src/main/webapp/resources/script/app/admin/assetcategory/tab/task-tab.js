var TaskTab = function () {  

	var initButtons = function () {
		$('#new-task-btn').on('click', function () {
			TaskTab.taskAddModal();
        });
		
	};

    var taskAddModal = function () {
        var $modal = $('#cmms-setting-add-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../assetCategory/task-add-modal-view';
            $modal.load(url, '', function () {
            	TaskAddModal.init("cmms-setting-add-modal");
                $modal.modal();
            });
        }, 1000);
    };
    
    var taskEditModal = function (index) {
        var $modal = $('#cmms-setting-add-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../assetCategory/task-add-modal-view';
            $modal.load(url, '', function () {
            	TaskAddModal.init('cmms-setting-add-modal');
            	TaskAddModal.fillScheduledTask(tasks[index]);
                $modal.modal();
            });
        }, 1000);
    };

    var initTaskTable = function () {
    	
        $('#assetCategoryTaskTbl > tbody').html("");
        if (tasks.length > 0) {
            $.each(tasks, function (index, task) {
            	task['index'] = index;
                var row = "<tr> " +
                "<input type='hidden' name='tasks[" + index + "].assetCatgoryTaskId' value='" + CustomComponents.nullValueReplace(task.assetCatgoryTaskId) + "'> " +
                "<input type='hidden' name='tasks[" + index + "].assetCatgoryVersionId' value='" + CustomComponents.nullValueReplace(task.assetCatgoryVersionId) + "'> " +
                    "<input type='hidden' name='tasks[" + index + "].id' value='" + CustomComponents.nullValueReplace(task.id) + "'> " +
                    "<input type='hidden' name='tasks[" + index + "].index' value='" + CustomComponents.nullValueReplace(task.index) + "'> " +
                    "<input type='hidden' name='tasks[" + index + "].version' value='" + CustomComponents.nullValueReplace(task.version) + "'> " +
                    "<input type='hidden' name='tasks[" + index + "].name' value='" + CustomComponents.nullValueReplace(task.name) + "'> " +
                    "<input type='hidden' name='tasks[" + index + "].taskType' value='" + CustomComponents.nullValueReplace(task.taskType) + "'> " +
                    "<input type='hidden' name='tasks[" + index + "].description' value='" + CustomComponents.nullValueReplace(task.description) + "'> " +
                    "<input type='hidden' name='tasks[" + index + "].estimatedHours' value='" + CustomComponents.nullValueReplace(task.estimatedHours) + "'> " +
                    "<td> " + ( index + 1 ) + "</td>" +
                    "<td> " + task.name + "</td>" +
                    "<td> " + task.taskType + "</td>" +
                    "<td> " + task.description + "</td>" +
                    "<td> " + task.estimatedHours + "</td>" +
                    "<td class='center'>" +
                    "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
                    ButtonUtil.getCommonBtnEdit("TaskTab.taskEditModal", index) +
                    ButtonUtil.getCommonBtnDelete("TaskTab.removeTask", index) +
                    "</div>" +
                    "</td></tr>";
                
                $('#assetCategoryTaskTbl > tbody:last-child').append(row);
            });
        } else {
            CustomComponents.emptyTableRow("assetCategoryTaskTbl", 6, "Please Add Tasks for the Asset Category.");
        }
    };
    
    var removeTask = function (index) {
    	tasks.splice(index, 1);
    	initTaskTable();
    };

    return {
    	
    	init : function () {
    		initButtons();
    		initTaskTable();
    	},

    	initTaskTable: function () {
    		initTaskTable();
        },
        
        taskAddModal: function () {
        	taskAddModal();
        },
        
        taskEditModal: function (index) {
        	taskEditModal(index);
        },
        
        removeTask: function (index) {
        	removeTask(index);
        }
    };

}();