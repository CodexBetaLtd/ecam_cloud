/*****************************************************
 * Schedule Maintenance Task Group Add
 * ***************************************************/
var TaskGroupAddModal = function () { 
    
    /*****************************************************
     * Init Buttons
     * ***************************************************/
    
    var initButtons = function () {
    	
    	$('#btn-add-task-group').on('click', function () {
    		TaskGroupAddModal.addFromTaskGroup();
        });
    	
    };

    var initTaskGroupSelect = function () {
    	
        $("#smTaskGroupName").inputClear({
            placeholder: "Select Task Group",
            btnMethod: "TaskGroupAddModal.taskGroupSelectView()",
            tooltip: "Select Task Group",
        });
        
    };
    
    /*****************************************************
     * Init Components
     * ***************************************************/
    
    var initDropDowns = function () {
       
        $("#smTaskGroupAssetId").select2({
            dropdownParent: $("#master-modal-datatable"),
            placeholder: "Select a Asset",
            allowClear: true
        }).change(function () {
            var assetId = $("#smTaskGroupAssetId option:selected").val();
            
            $("#stTriggerIndex").empty();
            $("#stTriggerIndex").append($("<option></option>").attr("value", '').text('Please Select a Trigger'));        
            $.each(triggers, function (index, trigger) {
            	if ( trigger.assetId == assetId ) {
            		$("#stTriggerIndex").append($("<option></option>").attr("value", trigger.index).text(TabScheduling.createSummary(trigger)));
            	}
            });
        }).trigger('change');


        $("#stTriggerIndex").select2({
            dropdownParent: $("#master-modal-datatable"),
            placeholder: "Select a Trigger",
            allowClear: true
        });
    	
    };

    var initAssetList = function () {
        var $as = $("#smTaskGroupAssetId");
        $as.empty();
        $as.append($("<option></option>").attr("value", '').text('Please Select a Asset'));
        $.each(assets, function (index, asset) {
            $as.append($("<option></option>").attr("value", asset.id).text(asset.name));
        });
    };

    /*****************************************************
     * Init Modals
     * ***************************************************/
    
    var taskGroupSelectView = function () {
    	var businessId = $("#businessId option:selected").val(); 
    	if (businessId != null && businessId != "" && businessId != undefined) { 
	        var $modal = $('#sm-task-group-add-child-modal');
	        CustomComponents.ajaxModalLoadingProgressBar();
	        setTimeout(function () {
	            var url = '../scheduledmaintenance/smTaskGroupDataTableView';
	            $modal.load(url, '', function () {
	            	ScheduledMaintenanceTaskGroup.init('sm-task-group-add-child-modal', businessId);
	                $modal.modal();
	            });
	        }, 1000);
    	} else {
    		alert("Please Select a Bisuness first");
    	}
    }; 

    /*****************************************************
     * Init Table
     * ***************************************************/
        
    var initTaskGroupTaskTable = function () {
        $('#sm_task_group_task_list_tbl > tbody').html("");
        var html="";
        if(tempTasks.length>0){
        $.each(tempTasks, function (index, obj) { 
        	var html =
                "<tr id='row_" + index + "' >" +
                "<input name='tasks[" + index + "].id' value='" + obj.id + "' type='hidden'>" +
                "<input name='tasks[" + index + "].name' value='" + obj.name + "' type='hidden' >" +
                "<input name='tasks[" + index + "].description' value='" + obj.estimatedHours + "' type='hidden' >" +
                "<input name='tasks[" + index + "].taskType' value='" + obj.taskType + "' type='hidden' >" +
                "<td><span>" + ( index + 1 ) + "</span></td>" +
                "<td><span>" + obj.name + "</span></td>" +
                "<td><span>" + obj.taskType + "</span></td>" +
                "<td><span>" + obj.description + "</span></td>" +
                "<td><span>" + obj.estimatedHours + "</span></td>" +
                "<td style='text-align:center'>" +
                	ButtonUtil.getCommonBtnDelete('TaskGroupAddModal.removeTaskGroupTask', index) +
                "</td>" +
                "</tr>";
           $('#sm_task_group_task_list_tbl > tbody:last-child').append(html);
        });
        }else{
        	tempTasks.length=0
        	html="<tr >" +
        	"<td colspan='6'><center><span>Table Data not Found</span></center></td>" +
        	"</tr>";
         $('#sm_task_group_task_list_tbl > tbody:last-child').append(html);
        }
           
        
    };

    var removeTaskGroupTask = function (index) {
        tempTasks.splice(index, 1);
        TaskGroupAddModal.initTaskGroupTaskTable();
    };

    /*********************************************************************
     * Add Task Via Task Group
     * *********************************************************************/

    var addFromTaskGroup = function () {
    	
    	if ($("#sm-task-group-add-frm").valid()) {
    		var date = new Date();
            var tempDate = date.getDate() + '-' + (date.getMonth() + 1) + '-' + date.getFullYear();
            $.each(tempTasks, function (index, task) {
            	var taskObj = []; 
                	taskObj['id'] = '';
                	taskObj['scheduledMaintenanceId'] = '';
            		taskObj['taskGroupId'] = $("#woTaskGroupId").val();
        			taskObj['taskGroupName'] = $("#woTaskGroupName").val();
    				taskObj['name'] = task.name;
					taskObj['description'] = task.name; 
					taskObj['taskType'] = task.taskType; 
                    taskObj['taskTypeName'] = task.taskTypeName;
                    taskObj['assetId'] = $("#smTaskGroupAssetId").val();
                    taskObj['assetName'] = $("#smTaskGroupAssetId :selected").text(); 
                    taskObj['estimatedHours'] = task.estimatedHours; 
                    taskObj['triggerIndex'] = $('#stTriggerIndex').val(); 
                    taskObj['index'] = TabTask.getCurrentTaskIndex();  
                    scheduledTasks.push(taskObj);
            });
            
            TabTask.initScheduledTaskTable();
            $('#master-modal-datatable').modal("toggle");
    	}  
    };

    var clearTaskTempList = function () {
        tempTasks = [];
    };
    
    var initValidator = function () {
        var form = $('#sm-task-group-add-frm');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        form.validate({
            errorElement: "span", // contain the error msg in a span tag
            errorClass: 'help-block',
            errorPlacement: function (error, element) { // render error placement for each input type
            	if (element.attr("type") == "radio" || element.attr("type") == "checkbox" ) { // for chosen elements, need to insert the error after the chosen container
                    error.insertAfter($(element).closest('.form-group').children('div').children().last());
                } else if (element.attr("name") == "dd" || element.attr("name") == "mm" || element.attr("name") == "yyyy" || element.closest('.input-group').has('.input-group-btn').length || element.closest('.form-group').has('.input-group-addon').length) {
                    error.insertAfter($(element).closest('.form-group').children('div'));
                } else if (element.closest('.form-group').has('.select2').length ){
                	error.insertAfter($(element).closest('.form-group').children('span'));
                } else {
                    error.insertAfter(element);
                }
            },
            ignore: "",
            rules: {
            	smTaskGroupAssetId: {
                    required: true
                },
                smTaskGroupName: {
                	required: true
                },
                stTriggerIndex: {
                	required: true
                }
            },
            messages: {
            	smTaskGroupAssetId: "Please Select a Asset",
            	smTaskGroupName: "Please Select a Task Group",
            	stTriggerIndex: "Please Select a Trigger"
            },
            invalidHandler: function (event, validator) { //display error alert on form submit
                successHandler.hide();
                errorHandler.show();
            },
            highlight: function (element) {
                $(element).closest('.help-block').removeClass('valid');
                // display OK icon
                $(element).closest('.form-group').removeClass('has-success').addClass('has-error').find('.symbol').removeClass('ok').addClass('required');
                // add the Bootstrap error class to the control group
            },
            unhighlight: function (element) { // revert the change done by hightlight
                $(element).closest('.form-group').removeClass('has-error');
                // set error class to the control group
            },
            success: function (label, element) {
                label.addClass('help-block valid');
                // mark the current input as valid and display OK icon
                $(element).closest('.form-group').removeClass('has-error').addClass('has-success').find('.symbol').removeClass('required').addClass('ok');
            },
            submitHandler: function (form) {
                successHandler.show();
                errorHandler.hide();
                return true;
            }
        });
    };

    return {

        init: function () {
        	clearTaskTempList();
            initTaskGroupSelect();
            initDropDowns();
            initButtons();
            initAssetList();
            initValidator();
            initTaskGroupTaskTable();
        },

        taskGroupSelectView: function () {
        	taskGroupSelectView();
        },

        setTaskGroup: function (id, code) {
            setTaskGroup(id, code);
        },
        
        initTaskGroupTaskTable: function () {
        	initTaskGroupTaskTable();
        },

        removeTaskGroupTask: function (index) {
            removeTaskGroupTask(index);
        },
        
        addFromTaskGroup: function () {
        	addFromTaskGroup();            
        }

    };
}();