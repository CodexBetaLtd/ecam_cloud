/*********************************************************************
 * Work Order add-view
 *********************************************************************/
var WorkorderAdd = function () {
	
    /*********************************************************************
     * Validation
     *********************************************************************/

    var runValidator = function () {
        var form = $('#wo_add_frm');
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
                    // for other inputs, just perform default behavior
                }
            },
            ignore: "",
            rules: {
                code: {
                    minlength: 4,
                    required: true,
                    remote: {
                        url: "../validate/workorder/validate-by-code",
                        type: "GET",
                        data: {
                            id: $('#woId').val()
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
                workOrderStatus: "Please Select a WorkOrder status"
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

    /*********************************************************************
     * Initialization
     *********************************************************************/
//
//	var initButtons = function() {
//		$('#btn-wo-approved').on('click', function() {
//			WorkorderAdd.woApprovedModalView();
//		});
//
//		$('#btn-wo-closed').on('click', function() {
//			WorkorderAdd.woClosedModalView();
//		});		
//	};
    
    /************************************************
     * Initialize InputClear Components
     ************************************************/
    
    function initInputClearComponents() {
        initInputClearProject();
        initInputClearMaintenanceType();
        initInputClearPriorities();
    };

    
    function initInputClearProject() {
        $("#projectName").inputClear({
            placeholder: "Select Project",
            btnMethod: "WorkorderAdd.woProjectView()"
        });
    };
    
    function initInputClearMaintenanceType() {
        $("#maintenanceTypeName").inputClear({
            placeholder: "Please specify a maintenance type",
            btnMethod: "WorkorderAdd.addMaintenanceType()"
        });
    };

    function initInputClearPriorities() {
        $("#priorityName").inputClear({
            placeholder: "Please specify a priority",
            btnMethod: "WorkorderAdd.addPriority()"
        });
    };
    
    /**********************************************
     * Modals
     **********************************************/
    
    var woProjectView = function () {
        var $modal = $('#master-modal-datatable');
        var bizId = $("#businessId option:selected").val();
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../workorder/project-select-modal-view';
            $modal.load(url, '', function () {
                woProject.woProjects("WorkorderAdd.setWOProject", bizId);
                $modal.modal();
            });
        }, 1000);
    };
    
    var woStatusChangeView = function(status) {
        var $modal = $("#master-modal-datatable");
        
        var woId = $("#woId").val();
        var note = "Status Change [ " + $("#workOrderStatus").val() + " -> " + status + " ]";
        
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../workorder/statuschangeview';
            $modal.load(url, '', function () {
                StatusChangeModal.init(woId, status, note); 
                $modal.modal();
            });
        }, 1000);
    };
    
    function initModalMaintenanceTypeSelect() {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        var bizId = $("#businessId").val();
        setTimeout(function () {
            var url = '../workorder/view/modal/maintenance-types';
            $modal.load(url, '', function () {
                DatatableModalMaintenanceTypes.init(
                        "master-modal-datatable",
                        "tbl_maintenance_types",
                        "../restapi/maintenance-type/tabledata?bizId=" + bizId,
                        "setData"
                );
                $modal.modal();
            });
        }, 1000);
    };
    
    function initModalPriotritySelect() {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        var bizId = $("#businessId").val();
        setTimeout(function () {
            var url = '../workorder/view/modal/priorities';
            $modal.load(url, '', function () {
                DatatableModalPriorities.init(
                        "master-modal-datatable",
                        "tbl_priorities",
                        "../restapi/priority/tabledata?bizId=" + bizId,
                        "setData"
                );
                $modal.modal();
            });
        }, 1000);
    };
    
    /************************************************
     * Initialize DatePickers
     ************************************************/
    
    var runDatePicker = function () {
        $('.date-picker').datepicker({
            autoclose: true,
            container: '#picker-container'
        });
    };
    
    /************************************************
     * Initialize Select2 Components
     ************************************************/
    
    var runBusinessSelect = function () {
        $("#businessId").select2({
            placeholder: "Select a Business",
            allowClear: true
        });
    };

    var runSiteSelect = function () {
        $("#siteId").select2({
            placeholder: "Select a Site",
            allowClear: true
        });
    };

    var runTaskTypeSelect = function () {
        $("#tasktypeId").select2({
            placeholder: "Select a Task Type",
            allowClear: true,
            dropdownParent: $("#addLabourTaskModal")
        });
    };

    var runAssingedUserSelect = function () {
        $("#assignedUserId").select2({
            placeholder: "Select a User",
            allowClear: true,
            dropdownParent: $("#addLabourTaskModal")
        });
    };

    var runNotificationUserSelect = function () {
        $("#notificationUserId").select2({
            placeholder: "Select a User",
            allowClear: true
        });
    };

    var runCompletedUserSelect = function () {
        $("#completedUserId").select2({
            placeholder: "Select a User",
            allowClear: true,
            dropdownParent: $("#addLabourTaskModal")
        });
    };

    var runPartSelect = function () {
        $("#PartId").select2({
            placeholder: "Select a Part",
            allowClear: true,
            dropdownParent: $("#addPartsModel")
        });
    };

    var runMeterReadingUnitSelect = function () {
        $("#woMeterReadingId").select2({
            placeholder: "Select a Meter reading Unit",
            allowClear: true,
            dropdownParent: $("#addMeterReadingModal")
        });
    };

    var runAssignedUserIdNotificationSelect = function () {
        $("#assignedUserIdNotification").select2({
            placeholder: "Select a User",
            allowClear: true,
            dropdownParent: $("#addNotificationModal")
        });
    };

    /* 
	 *load siteList relevant to business
	 */   
    var runAssetBusinessSelect = function(){
    	$("#businessId").change(function() {
			var businessId = $("#businessId option:selected").val(); 
			setDataToSiteSelect2(businessId);
			setWorkOrderCode(businessId);
			$("#maintenanceTypeName").val("");
			$("#maintenanceTypeId").val("");
			$("#priorityName").val("");
			$("#priorityId").val("");
			$("#accountName").val("");
			$("#accountId").val("");
			$("#chargeDepartmentName").val("");
			$("#chargeDepartmentId").val("");
		});
    };
    
    var setDataToSiteSelect2 = function(id) {
		
		$.ajax({
			type : "GET",
			url: "../workorder/getsites?id=" + id,
			contentType : "application/json",
			dataType : "json",
			success : function(output) {
				$("#siteId").find("option").remove();
				$.each(output, function(key, siteList) {
					$('#siteId').append($('<option>', {value: siteList.id}).text(siteList.name).trigger('change'));
				});
				runSiteSelect();
			},
			error : function(xhr, ajaxOptions, thrownError) {
				alert(xhr.status + " " + thrownError);
			},
			error : function(e) {
				alert("Failed to load site");
				console.log(e);
			}
		});
		
    };
	
	var setWorkOrderCode = function(id) {
		
		if ( $('#woId').val() == null ||  $('#woId').val() == undefined || $('#woId').val() == "" ) {

        	$.ajax({
                type: "GET",
                url: "../workorder/code-by-business?businessId=" + id,
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

    /*********************************************************************
     * Set Data
     *********************************************************************/

    var setWOProject = function (id, name) {
        $('#projectId').val(id);
        $('#projectName').val(EncodeDecodeComponent.getBase64().decode(name));
        $("#master-modal-datatable").modal('toggle');
    };
    
    /**********************************************************
     * View Data Set
     * ********************************************************/
    
    var woApprovedModalView = function() { 
    	var status = "APPROVED"; 
    	woStatusChange(status);
    }; 
	
	var woClosedModalView = function() {
		var status = "CLOSED";
		woStatusChange(status);
	}; 
	
    var woStatusChange = function(status) {  
    	woStatusChangeView(status);
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
        if ($('#woId').val() != null && $('#woId').val() != "") { 
            setDisableFields();
            appendBusinessSiteDiv($('#businessId').val() );
        }
	};

    return {
        init: function () {
            runValidator();
            initInputClearComponents();
            runDatePicker();

            runTaskTypeSelect();
            runAssingedUserSelect();
            runCompletedUserSelect();
            runPartSelect();
            runMeterReadingUnitSelect();
            runNotificationUserSelect();
            runAssignedUserIdNotificationSelect();
            runSiteSelect();
            runBusinessSelect();
            runAssetBusinessSelect();
            setBusinessSiteView();
        },

        woAssignedUserView: function () {
            woAssignedUserView();
        },

        woCompletedUserView: function () {
            woCompletedUserView();
        },

        woProjectView: function () {
            woProjectView();
        },
        
        setWOProject: function (id, name) {
            setWOProject(id, name);
        },
        
        woApprovedModalView: function() {
        	woApprovedModalView();
		},
		
		woClosedModalView: function() {
			woClosedModalView();
		},
		
		addMaintenanceType: function () {
		    initModalMaintenanceTypeSelect(); 
		},
		
		addPriority: function () {
		    initModalPriotritySelect(); 
		},
    };
}();