var ScheduledMaintenanceAdd = function () {

    var initProjectSelect = function () {
        $("#projectName").inputClear({
            placeholder: "Select a Project",
            btnMethod: "ScheduledMaintenanceAdd.selectProjectModal()",
        });
    };
    
    var initAssignUserSelect = function () {
        $("#requestorName").inputClear({
            placeholder: "Select a Assigned User",
            btnMethod: "ScheduledMaintenanceAdd.selectAssignedUserModal()",
        });
    };

    var initBusinessSelect = function () {
		$("#businessId").select2({
			placeholder: "Select a Business",
			allowClear: true
        });
	}; 
	
	var initAssetBusinessSelect = function(){
		$("#businessId").change(function () { 
            var businessId = $("#businessId option:selected").val(); 
            setDataToSiteSelect2(businessId);
			setDataToMaintenanceSelect2(businessId);
			setDataToPrioritySelect2(businessId);
			setScheduleMaintenanceCode(businessId);   
			setDataToAccountSelect2(businessId);   
			setDataToDepartmentSelect2(businessId);   
        });
	};
	   
    var setDataToSiteSelect2 = function(id) { 
    	
        $.ajax({
            type: "GET",
            url: "../scheduledmaintenance/sitesbybusiness?id=" + id,
            contentType: "application/json",
            dataType: "json",
            success: function (output) {
                $("#siteId").find("option").remove();
                $.each(output, function (key, site) {
                    $('#siteId').append($('<option>', {value: site.id}).text(site.name));
                });
                initSiteSelect();
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert(xhr.status + " " + thrownError);
            },
            error: function (e) {
                alert("Failed to load site");
                console.log(e);
            }
        });
		
    };
    
    var setDataToMaintenanceSelect2 = function(id) { 
    	
		$.ajax({
			type : "GET",
			url: "../scheduledmaintenance/maintenance-type-by-business/" + id,
			contentType : "application/json",
			dataType : "json",
			success : function(output) {
				$("#maintenanceTypeId").find("option").remove();
				$.each(output, function(key, typeList) {
					$('#maintenanceTypeId').append($('<option>', {value: typeList.id}).text(typeList.name).trigger('change'));
				});
				initMaintainanceTypeSelect();
			},
			error : function(xhr, ajaxOptions, thrownError) {
				alert(xhr.status + " " + thrownError);
			},
			error : function(e) {
				alert("Failed to load Maintainance Type");
				console.log(e);
			}
		});
    };
    
    var setDataToPrioritySelect2 = function(id) {
    	
    	$.ajax({
    		type : "GET",
    		url: "../scheduledmaintenance/priorities-by-business/" + id,
    		contentType : "application/json",
    		dataType : "json",
    		success : function(output) {
    			$("#priorityId").find("option").remove();
    			$.each(output, function(key, typeList) {
    				$('#priorityId').append($('<option>', {value: typeList.id}).text(typeList.name).trigger('change'));
    			});
    			initPrioritySelect();
    		},
    		error : function(xhr, ajaxOptions, thrownError) {
    			alert(xhr.status + " " + thrownError);
    		},
    		error : function(e) {
    			alert("Failed to load Priorities");
    			console.log(e);
    		}
    	});
    	
    };
    
    var setDataToAccountSelect2 = function(id) {
    	
    	$.ajax({
    		type : "GET",
    		url: "../scheduledmaintenance/accounts-by-business/" + id,
    		contentType : "application/json",
    		dataType : "json",
    		success : function(output) {
    			$("#accountId").find("option").remove();
    			$.each(output, function(key, accountList) {
    				$('#accountId').append($('<option>', {value: accountList.id}).text(accountList.code).trigger('change'));
    			}); 
				initAccountSelect();
    		},
    		error : function(xhr, ajaxOptions, thrownError) {
    			alert(xhr.status + " " + thrownError);
    		},
    		error : function(e) {
    			alert("Failed to load Accounts");
    			console.log(e);
    		}
    	});
    	
    };
    
    var setDataToDepartmentSelect2 = function(id) {
		
		$.ajax({
			type : "GET",
            url: "../scheduledmaintenance/departments-by-business/" + id,
			contentType : "application/json",
			dataType : "json",
			success : function(output) {
				$("#chargeDepartmentId").find("option").remove();
				$.each(output, function(key, departmentList) {
                    $('#chargeDepartmentId').append($('<option>', {value: departmentList.id}).text(departmentList.code).trigger('change'));
				});
	            initChargeDepartmentSelect(); 
			},
			error : function(xhr, ajaxOptions, thrownError) {
				alert(xhr.status + " " + thrownError);
			},
			error : function(e) {
				alert("Failed to load Departments");
				console.log(e);
			}
		});
		
	};
	
	var setScheduleMaintenanceCode = function(id) { 
		
        if ( businessId != null && ( $('#smId').val() == null ||  $('#smId').val() == undefined || $('#smId').val() == "" ) ) {
        	
        	$.ajax({
                type: "GET",
                url: "../scheduledmaintenance/code-by-business?businessId=" + id,
                contentType: "application/json",
                dataType: "json",
                success: function (result) {
                    if (result.status == "SUCCESS") {
                    	$('#code').val(result.data);
                    } else {
                    	alert(result.msg);
                    }
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    alert(xhr.status + " " + thrownError);
                },
                error: function (e) {
                    alert("Failed to load Code");
                    console.log(e);
                }
            });            	
        } 
	};


    var initSiteSelect = function () {
        $("#siteId").select2({
            placeholder: "Select a Site",
            allowClear: true
        });
    };

    var initMaintainanceTypeSelect = function () {
        $("#maintenanceTypeId").select2({
            placeholder: "Select a Maintanence Type",
            allowClear: true
        });
    };

    var initWorkOrderStatusSelect = function () {
        $("#workOrderStatus").select2({
            placeholder: "Select a Work Order Status ",
            allowClear: true
        });
    };

    var initPrioritySelect = function () {
        $("#priorityId").select2({
            placeholder: "Select a priority",
            allowClear: true
        });
    };

    var initAccountSelect = function () {
        $("#accountId").select2({
            placeholder: "Select a Account",
            allowClear: true
        });
    };

    var initChargeDepartmentSelect = function () {
        $("#chargeDepartmentId").select2({
            placeholder: "Select a Charge Department",
            allowClear: true
        });
    };

    var initDatePicker = function () {
        $('.date-picker').datepicker({
            autoclose: true,
            container: '#picker-container'
        });
    };

    var initIsRunningSwitch = function () {
    	 
         $("#isRunning").bootstrapSwitch().on('switchChange.bootstrapSwitch', 
    		 function (event, state) {
	        	 if ( $('#isRunning').attr('checked') != undefined && $('#isRunning').attr('checked') == 'checked' ) {
	        		 $('#isRunning').removeAttr('checked')
	        	 } else {
	        		 $('#isRunning').attr('checked', 'checked');
	        	 }
     		}
         );
    };

    var selectProjectModal = function () {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../scheduledmaintenance/projectselectmodalview';
            $modal.load(url, '', function () {
                ProjectSelectModal.init();
                $modal.modal();
            });
        }, 1000);
    };

    var selectAssignedUserModal = function () {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../scheduledmaintenance/userselectmodalview';
            $modal.load(url, '', function () {
                UserSelectModel.init();
                $modal.modal();
            });
        }, 1000);
    };

    var initValidator = function () {
        var form = $('#sm_add_frm');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        $('#sm_add_frm').validate({
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
                code: {
                    minlength: 4,
                    required: true,
                    remote: {
                        url: "../validate/scheduledmaintenance/validate-by-code",
                        type: "GET",
                        data: {
                            id: $('#smId').val()
                        }
                    }
                },
                businessId: {
                    required: true
                },
                siteId: {
                    required: true
                },
                priorityId: {
                    required: true
                },
                maintenanceTypeId: {
                    required: true
                },
                workOrderStatus: {
                    required: true
                }
            },
            messages: {
            	code: {
                	required : "Please Specify a Code",
                	minlength : "Code Contain atleast 4 Characters",
                	remote: jQuery.validator.format("Code {0} is already taken.")
                },
                businessId: "Please Select a Business",
                priorityId: "Please Select a Priority",
                siteId: "Please Select a site",
                maintenanceTypeId: "Please Select a MaintenanceType",
                workOrderStatus: "Please Select a WorkOrder Status"
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
     * View Customize
     * ********************************************************/
    
    var appendBusinessSiteDiv = function(businessId ) {
        var html = 	'<div id="businessSiteDiv">' +
        				'<input type="hidden" id="hdnBusinessId" name="businessId" value="'+ businessId +'"/>' + 
        			'</div>';
        $('#job_add_frm').append(html);
    };

    var setDisableFields = function () {
        $("#businessId").attr("disabled", "disabled"); 
    };

    var setBusinessSiteView = function() { 
        if ($('#smId').val() != null && $('#smId').val() != "") { 
            setDisableFields();
            appendBusinessSiteDiv($('#businessId').val() );
        }
	};
	
    /**********************************************************
     * Scheduled Maintenance Scheduling
     * ********************************************************/

    var selectAssignedUser = function (id, name) {
        $('#requestorId').val(id);
        $('#requestorName').val(name);
        $('#master-modal-datatable').modal('toggle');
    };

    var selectProject = function (id, name) {
        $('#projectId').val(id);
        $('#projectName').val(name);
        $('#master-modal-datatable').modal('toggle');
    };

    return {
        init: function () {
        	initValidator();
            initProjectSelect();
            initAssignUserSelect();
            
            initMaintainanceTypeSelect();
            initPrioritySelect();
            
            initDatePicker();
            initWorkOrderStatusSelect();
            initAccountSelect();
            initChargeDepartmentSelect();
            initIsRunningSwitch();
            initSiteSelect();
            initBusinessSelect();
            initAssetBusinessSelect();
            setBusinessSiteView();
        },

        selectAssignedUser: function (id, name) {
            selectAssignedUser(id, name);
        },

        selectProjectModal: function () {
            selectProjectModal();
        },

        selectAssignedUserModal: function () {
            selectAssignedUserModal();
        },

        selectProject: function (id, name) {
            selectProject(id, name);
        }

    };

}();