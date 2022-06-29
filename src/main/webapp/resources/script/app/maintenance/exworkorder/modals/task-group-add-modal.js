	/*********************************************************************
	 * Task Add from Task Group
	 * *******************************************************************/
var TaskGroupAddModal = function () { 
    
    /*********************************************************************
     * Init Buttons
     * *******************************************************************/
    
    var initButtons = function () {    	
    	
    	$('#add-task-group-btn').on('click', function () {
    		TaskGroupAddModal.addFromTaskGroup();
        });
    	
    };
    
    /*********************************************************************
     * Init Components
     * *******************************************************************/
    var initTaskGroupSelect = function () {
        $("#woTaskGroupName").inputClear({
            placeholder: "Select Task Group",
            btnMethod: "TaskGroupAddModal.taskGroupSelectView()",
            tooltip: "Select Task Group"
        });
    };

    var initDropDown = function () {
        
    	$("#woTaskGroupAssetId").select2({
    		dropdownParent: $('#master-modal-datatable'),
            placeholder: "Select a Asset",
            allowClear: true
        });
    	
    };
    
    var initAssetList = function () {
        var $as = $("#woTaskGroupAssetId");
        $as.empty();
        $as.append($("<option></option>").attr("value", '').text('Please Select a Asset'));
        $.each(assets, function (index, obj) {
            $as.append($("<option></option>").attr("value", obj.id).text(obj.name));
        });
    };    

    /*********************************************************************
     * Init Modals
     * *******************************************************************/
    
    var taskGroupSelectView = function () {
    	var businessId = $("#businessId option:selected").val(); 
    	if (businessId != null && businessId != "" && businessId != undefined) { 
	        var $modal = $('#wo-task-group-add-child-modal');
	        CustomComponents.ajaxModalLoadingProgressBar();
	        setTimeout(function () {
	            var url = '../workorder/task-group-select-modal-view';
	            $modal.load(url, '', function () {
	            	WorkOrderTaskGroup.init("wo-task-group-add-child-modal", businessId);
	                $modal.modal();
	            });
	        }, 1000);
    	} else {
    		alert("Please Select a Bisuness first");
    	}
    };

    /*********************************************************************
     * Init DataTable
     * *******************************************************************/
    
    var initTaskGroupTaskTable = function () {
    		
        $("#wo_task_group_task_list_tbl").dataTable().fnDestroy();
        
        var oTable = $("#wo_task_group_task_list_tbl").dataTable({
            processing: true,
            serverSide: false,  
            data : tempTasks,
            columns: [
                {
                    orderable: false,
                    searchable: false, 
                    render: function (data, type, row, meta) {  
                        return meta.row + meta.settings._iDisplayStart + 1;
                    }
                },
                {data: 'name'},
                {data: 'description'},
                {data: 'estimatedHours'},
            ],
            aoColumnDefs: [{
                targets: 4,  
                render: function (data, type, row, meta) {
                    return '<div style="text-align:center">' + ButtonUtil.getCommonBtnDelete('TaskGroupAddModal.removeTask', data);
                }
            }],
            oLanguage: {
                sLengthMenu: "Show_MENU_Rows",
                sSearch: "",
                oPaginate: {
                    sPrevious: "&laquo;",
                    sNext: "&raquo;"
                }
            },
            aaSorting: [
                [1, 'asc']
            ],
            aLengthMenu: [
                [5, 10, 15, 20, -1],
                [5, 10, 15, 20, "All"]
            ],
            sPaginationType: "full_numbers",
            sPaging: 'pagination',
            bLengthChange: false
        });

        $('#wo_task_group_task_list_tbl_wrapper .dataTables_filter input').addClass("form-control input-sm").attr("placeholder", "Search");
        $('#wo_task_group_task_list_tbl_wrapper .dataTables_length select').addClass("m-wrap small");
        $('#wo_task_group_task_list_tbl_wrapper .dataTables_length select').select2();
        $('#wo_task_group_task_list_tbl_column_toggler input[type="checkbox"]').change(function () {
            var iCol = parseInt($(this).attr("data-column"));
            var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
            oTable.fnSetColumnVis(iCol, (bVis ? false : true));
        });
    };

    var removeTask = function (index) {
    	tempTasks.splice(index, 1);
    	initTaskGroupTaskTable();
    }; 
    
    /*********************************************************************
     * Set Data
     * *******************************************************************/

    var addFromTaskGroup = function () {
    	
    	if ($("#task-group-add-frm").valid()) {
    		var date = new Date();
            var tempDate = date.getDate() + '-' + (date.getMonth() + 1) + '-' + date.getFullYear();
            $.each(tempTasks, function (index, task) {
            	var taskObj = []; 
                	taskObj['id'] = '';
                	taskObj['workOrderId'] = '';
            		taskObj['taskGroupId'] = $("#woTaskGroupId").val();
        			taskObj['taskGroupName'] = $("#woTaskGroupName").val();
    				taskObj['name'] = task.name;
					taskObj['description'] = task.description;
					taskObj['taskType'] = task.taskType; 
                    taskObj['taskTypeName'] = task.taskTypeName;
                    taskObj['assignedAssetId'] = $("#woTaskGroupAssetId").val();
                    taskObj['assignedAssetName'] = $("#woTaskGroupAssetId :selected").text();
                    taskObj['assignedUserId'] = "";
                    taskObj['startedDate'] = tempDate;
                    taskObj['estimatedHours'] = task.estimatedHours;
                    taskObj['completedUserId'] ="";
                    taskObj['completedDate'] = tempDate;
                    taskObj['spentHours'] = "";
                    taskObj['completionNote'] = "";
                    taskObj['completionRemark'] = "";
                    taskObj['other'] = false;
                    tasks.push(taskObj);
            });
            
            TaskTab.populateTaskTable();
            $('#master-modal-datatable').modal("toggle");
    	}
    	
    };
    
    /*********************************************************************
     * Init Validator
     * *******************************************************************/
    
    var initValidator = function () {
        var form = $('#task-group-add-frm');
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
            	woTaskGroupAssetId: {
                    required: true
                },
                woTaskGroupName: {
                	required: true
                }
            },
            messages: {
            	woTaskGroupAssetId: "Please Select a Asset",
            	woTaskGroupName: "Please Select a Task Group"
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
        	tempTasks = [];
        	initButtons();
        	initTaskGroupSelect();
        	initDropDown();
        	initAssetList();
        	initValidator();
        	initTaskGroupTaskTable();
        },
        
        taskGroupSelectView: function () {
        	taskGroupSelectView();
        },
        
        removeTask : function () {
        	removeTask();
        }, 
        
        addFromTaskGroup: function () {
        	addFromTaskGroup();
        },
        
        initTaskGroupTaskTable: function() {
        	initTaskGroupTaskTable();
		},

    };
}();