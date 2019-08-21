var TaskAddModal = function () {
	
	var setModal = function (modalName) {
		this.modalName = modalName;
	}
	
	var initButtons = function () {

		$('#add-task-btn').on('click', function () {
			TaskAddModal.addTask();
        });
		
	};
	
	var initDropDown = function () {
		
		$("#taskType").select2({
            dropdownParent: $("#" + this.modalName),
            placeholder: "Please Select a Task Type",
            allowClear: true
        })       
		
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
            	taskName: {
            		required: true
            	},
            	taskDescription: {
                    required: true
                },
                taskType: {
                	required: true
                },
                taskEstimatedHours: {
                	number: true,
                	required: true
                }
            },
            messages: {
            	taskName: "Please Insert a Task Name",
            	taskType: "Please Select a Task type",
            	taskDescription: "Please Insert a Description",
            	taskEstimatedHours: {
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
    		
    		var task = {};
        	
        	if ($('#taskIndex').val() != null && $('#taskIndex').val() != "" && $('#taskIndex').val() >= 0 ) {
        		task = tasks[$('#taskIndex').val()];
        	} else {   		
        		tasks.push(task);
        	}
        	
        	CustomValidation.validateFieldNull(task, 'id', $('#taskId').val());
        	CustomValidation.validateFieldNull(task, 'version', $('#taskVersion').val());
            CustomValidation.validateFieldNull(task, 'description', $('#taskDescription').val());    
            CustomValidation.validateFieldNull(task, 'name', $('#taskName').val());
            CustomValidation.validateFieldNull(task, 'taskType', $('#taskType').val());
            CustomValidation.validateFieldNull(task, 'estimatedHours', $('#taskEstimatedHours').val());
        	
            TaskTab.initTaskTable();
            $("#" + this.modalName).modal('toggle');
    	}
    };
    
    var fillScheduledTask = function (task) {
        $("#taskIndex").val(task.index);
        $("#taskId").val(task.id);
        $("#taskVersion").val(task.version);    
        $("#taskDescription").val(task.description);
        $("#taskEstimatedHours").val(task.estimatedHours);
        $("#taskName").val(task.name);
        $("#taskType").val(task.taskType).trigger('change');
    };
	
	return {
		init: function (modalName) {
			setModal(modalName);
			initButtons();
			initDropDown();
            initValidator();
        },
        
        addTask: function () {
        	addTask();
        },
        
        fillScheduledTask: function (task) {
        	fillScheduledTask(task)
        }

	};
	
}();