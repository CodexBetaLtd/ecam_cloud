var TaskAddModal = function () {	
	
	var setModal = function (modalName) {
		this.modalName = modalName;
	} 

    /**********************************************************
     * Init Buttons
     * ********************************************************/
	
	var initButtons = function () {

        $('#add-scheduled-task-btn').on('click', function () {
        	TaskAddModal.addSmTask();
        });

    };	
    

    /**********************************************************
     * Init Components
     * ********************************************************/ 
    
    var initDropDown = function () {

        $("#stAssetId").select2({
            dropdownParent: $("#" + this.modalName),
            placeholder: "Select a Asset",
            allowClear: true
        }).change(function () {
            var assetId = $("#stAssetId option:selected").val();
            
            $("#stTriggerIndex").empty();
            $("#stTriggerIndex").append($("<option></option>").attr("value", '').text('Please Select a Trigger'));        
            $.each(triggers, function (index, trigger) {
            	if ( trigger.assetId == assetId ) {
            		$("#stTriggerIndex").append($("<option></option>").attr("value", trigger.index).text(TabScheduling.createSummary(trigger)));
            	}
            });
        }).trigger('change');

        $("#stTaskType").select2({
            dropdownParent: $("#" + this.modalName),
            placeholder: "Select a Task Type",
            allowClear: true
        });

        $("#stTriggerIndex").select2({
            dropdownParent: $("#" + this.modalName),
            placeholder: "Select a Trigger",
            allowClear: true
        });
        
    };
    /********************************************
     * Initialize InputClear Components
     ********************************************/

    function initInputClearComponents(){
        initInputClearUser();
    };
    
    function initInputClearUser(){
        $("#stUserName").inputClear({
            placeholder:"Please specify a user",
            btnMethod:"TaskAddModal.addUser()",
        });
    };   
    
    
    /********************************************
     * Initialize modal views
     ********************************************/

    function initModalUserSelect() {
        
        var businessId = $("#businessId option:selected").val(); 
        
        if (businessId != null && businessId != "" && businessId != undefined) { 
            var $modal = $('#sm-task-add-child-modal');
            CustomComponents.ajaxModalLoadingProgressBar();
            setTimeout(function () {
                var url = '../scheduledmaintenance/view/modal/users';
                $modal.load(url, '', function () {
                    DatatableModalUsers.init(
                            "sm-task-add-child-modal",
                            "tbl_users",
                            "../restapi/users/usersbybusinessid?id=" + businessId,
                            "setTaskUser");
                    $modal.modal();
                });
            }, 1000);
        } else {
            alert("Please Select a Bisuness first");
        }

    };    
    
    /**********************************************************
     * Init Validator
     * ********************************************************/
    
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
            	stTimeEstimate: {
                	number: true,
                    required: true
                },
                stAssetId: {
                    required: true
                },
                stTriggerIndex: {
                    required: true
                },
                stTaskType: {
                    required: true
                },
                stDescription: {
                    required: true
                },
                stUserName: {
                    required: true
                }
            },
            messages: {
            	stTimeEstimate: {
                	number : "Please Insert a numeric value",
                	required : "Please Insert a Time"
                },
                stAssetId: "Please Select a Asset",
                stTriggerIndex: "Please Select a Trigger",
                stTaskType: "Please Select a Task Type",
                stDescription: "Please Insert a Description",
                stUserName: "Please Select a User"
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
    

    /**********************************************************
     * Set Data
     * ********************************************************/ 
    
    var initAssetSelect = function () {
        $("#stAssetId").empty();
        $("#stAssetId").append($("<option></option>").attr("value", '').text('Please Select a Asset'));
        $.each(assets, function (index, obj) {
        	$("#stAssetId").append($("<option></option>").attr("value", obj.id).text(obj.name));
        });
    };
    
    var addSmTask = function () {
    	
    	if ( $("#task-add-frm").valid() ) {   		
    		
    		var smTask = {};
    		
        	if ($('#stIndex').val() != null && $('#stIndex').val() != "" && $('#stIndex').val() >= 0 ) {
        		smTask = TabTask.getTaskByIndex($('#stIndex').val());
        	} else {    
        		CustomValidation.validateFieldNull(smTask, 'index', TabTask.getCurrentTaskIndex());    		
        		scheduledTasks.push(smTask);
        	}
        	
        	CustomValidation.validateFieldNull(smTask, 'id', $('#stId').val());
        	CustomValidation.validateFieldNull(smTask, 'version', $('#stVersion').val());
        	CustomValidation.validateFieldNull(smTask, 'triggerIndex', $('#stTriggerIndex').val());
            CustomValidation.validateFieldNull(smTask, 'taskGroupId', $('#stGroupId').val());
            CustomValidation.validateFieldNull(smTask, 'description', $('#stDescription').val());        
            CustomValidation.validateFieldNull(smTask, 'assetId', $('#stAssetId').val());
            CustomValidation.validateFieldNull(smTask, 'assetName', $('#stAssetId :selected').text());
            CustomValidation.validateFieldNull(smTask, 'userId', $('#stUserId').val());
            CustomValidation.validateFieldNull(smTask, 'userName', $('#stUserName').val());
            CustomValidation.validateFieldNull(smTask, 'taskType', $('#stTaskType').val());
            CustomValidation.validateFieldNull(smTask, 'estimatedHours', $('#stTimeEstimate').val());
        	
            TabTask.initScheduledTaskTable();
            $("#" + this.modalName).modal('toggle');
    	}
    };
    
    var fillScheduledTask = function (task) {
        $("#stIndex").val(task.index);
        $("#stId").val(task.id);
        $("#stVersion").val(task.version);
        $("#stAssetId").val(task.assetId).trigger("change");
        $("#stTriggerIndex").val(task.triggerIndex).trigger("change");
        $("#stGroupId").val(task.taskGroupId);        
        $("#stDescription").val(task.description);
        $("#stTimeEstimate").val(task.estimatedHours);
        $("#stUserId").val(task.userId);
        $("#stUserName").val(task.userName);
        $("#stTaskType").val(task.taskType).trigger("change");;
    };
	
	return {
		init: function (modalName) {
			setModal(modalName);			
            initButtons();
            initDropDown();
            initAssetSelect();         
            initInputClearComponents();         
            initValidator();
        },
        
        addUser: function() {
            initModalUserSelect();
        },
        
        addSmTask: function () {
        	addSmTask();
        },
        
        fillScheduledTask: function (task) {
        	fillScheduledTask(task)
        }

	};
	
}();