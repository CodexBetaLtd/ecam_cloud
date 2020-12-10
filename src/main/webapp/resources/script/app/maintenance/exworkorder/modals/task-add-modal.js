var TaskAddModal = function () {
	
	var initButtons = function () {

		$('#add-task-btn').on('click', function () {
			TaskAddModal.addTask();
        });
		
	};
	
	var initAssignedUserSelect = function () {
		
        $("#woTaskAssignedUserName").inputClear({
            placeholder: "Select Assigned User",
            btnMethod: "TaskAddModal.assignedUserSelect()",
            tooltip: "Select Assign User"
        });
        
    };

    var initCompletedUserSelect = function () {
    	
        $("#woTaskCompletedUserName").inputClear({
            placeholder: "Select Completed User",
            btnMethod: "TaskAddModal.completedUserSelect()",
            tooltip: "Select Completed User"
        });
        
    };
	
	var initDropDown = function () {

        $("#woTaskAsset").select2({
            dropdownParent: $('#master-modal-datatable'),
            placeholder: "Select a Asset",
            allowClear: true
        });

        $("#woTaskTaskType").select2({
            dropdownParent: $('#master-modal-datatable'),
            placeholder: "Select a Task",
            allowClear: true
        });
        
    };
    
    var initAssetSelect = function () {    	
    	$("#woTaskAsset").empty();
        $("#woTaskAsset").append($("<option></option>").attr("value", '').text('Please Select a Asset'));
        $.each(assets, function (index, obj) {
        	$("#woTaskAsset").append($("<option></option>").attr("value", obj.id).text(obj.name));
        });       
    };
    
    var initDatePickers = function () {
    	
        $('#woTaskCompletedDate').datepicker({
            autoclose: true,
            orientation: "auto top"
        });
        
        $('#woTaskStartDate').datepicker({
            autoclose: true,
            orientation: "auto top"
        });
        
    };
    
    var assignedUserSelect = function () {
    	var businessId = $("#businessId option:selected").val(); 
    	if (businessId != null && businessId != "" && businessId != undefined) { 
	        var $modal = $('#wo-task-add-child-modal');
	        CustomComponents.ajaxModalLoadingProgressBar();
	        setTimeout(function () {
	            var url = '../workorder/user-select-modal-view';
	            $modal.load(url, '', function () {
	                dtWorkOrderUser.getUserList("TaskAddModal.setAssignedUser", businessId);
	                $modal.modal();
	            });
	        }, 1000);
    	} else {
    		alert("Please Select a Bisuness first");
    	}
    };

    var setAssignedUser = function (id, name) {
        $('#woTaskAssignedUserId').val(id);
        $('#woTaskAssignedUserName').val(EncodeDecodeComponent.getBase64().decode(name));
    };

    var completedUserSelect = function () {
    	var businessId = $("#businessId option:selected").val(); 
    	if (businessId != null && businessId != "" && businessId != undefined) { 
	        var $modal = $('#wo-task-add-child-modal');
	        CustomComponents.ajaxModalLoadingProgressBar();
	        setTimeout(function () {
	            var url = '../workorder/user-select-modal-view';
	            $modal.load(url, '', function () {
	                dtWorkOrderUser.getUserList("TaskAddModal.setCompletedUser", businessId);
	                $modal.modal();
	            });
	        }, 1000);
    	} else {
    		alert("Please Select a Bisuness first");
    	}
    };

    var setCompletedUser = function (id, name) {
        $('#woTaskCompletedUserId').val(id);
        $('#woTaskCompletedUserName').val(EncodeDecodeComponent.getBase64().decode(name));
    };
	
	var initValidator = function () {
        var form = $('#task-add-frm');
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
            	woTaskName: {
                    required: true
                },
                woTaskAsset: {
                	required: true
                },
                woTaskTaskType: {
                	required: true
                },
                woTaskAssignedUserName: {
                	required: true
                },
                woTaskTimeEstimate: {
                	number: true,
                	required: true
                }
            },
            messages: {
            	woTaskName: "Please Insert a Task Name",
            	woTaskAsset: "Please Select a Asset",
            	woTaskTaskType: "Please Select a Task Type",
            	woTaskAssignedUserName: "Please Select a User",
            	woTaskTimeEstimate: {
            		required: "Please Insert a Time",
            		number: "Please insert a numeric value",
            	}
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
    
    var addTask = function () {
    	if ( $("#task-add-frm").valid() ) {
    		TaskTab.addTask();
    	}
    };
	
	return {
		init: function () {
			initButtons();	
			initDropDown();
			initAssetSelect();
			initDatePickers();
			initAssignedUserSelect();
			initCompletedUserSelect();
            initValidator();
        },
        
        addTask: function () {
        	addTask();
        },
        
        assignedUserSelect: function () {
        	assignedUserSelect();
        },
        
        setAssignedUser: function (id, name) {
        	setAssignedUser(id, name);
        },
        
        completedUserSelect: function () {
        	completedUserSelect();
        },
        
        setCompletedUser: function (id, name) {
        	setCompletedUser(id, name);
        }

	};
	
}();
