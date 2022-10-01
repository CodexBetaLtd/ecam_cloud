var AODReportView = function () {


	var initButtons = function() {
		initButtonViewReport();
	};
	
	var initButtonViewReport = function() {
	    $('#viewAODReport').on('click', function (event) {
	    	if ($('#frmReportAOD').valid()) {				
	    		dtAOD.dtAODList();
			}
	    });
	};
	

    /*********************************************************************
     * AOD Customer
     *********************************************************************/
    var runCustomerInput = function () {
        $("#aodCustomerName").inputClear({
            placeholder: "Select Customer",
            btnMethod: "AODReportView.customerView()",
        });
    };

    var customerView = function () {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../report/aod/getcustomer';
            $modal.load(url, '', function () {
                var dt_url = "../../restapi/customer/tabledata";
                var func = "AODReportView.setCustomer";
                dtCustomer.getCustomer(dt_url, func);
                $modal.modal();
            });
        }, 1000);
    };
    
    var setCustomer = function (id, name) {
        $("#aodCustomerId").val(id);
        $("#aodCustomerName").val(name);
        $("#aodCustomerName").attr('readonly', true);
        $('#common-modal').modal("hide");
    };


    /*********************************************************************
     * AOD Requested by User
     *********************************************************************/
    var runRequestedByUserInput = function () {
    	$("#requestedByUserName").inputClear({
    		placeholder: "Select a User",
    		btnMethod: "AODReportView.requsestedByUserView()",
    	});
    };
    
    var requsestedByUserView = function () {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../aod/view/modal/user';
            $modal.load(url, '', function () {
                dtUsers.getUserList('AODReportView.setRequestedByUser');
                $modal.modal();
            });
        }, 1000);
    };
    
    var setRequestedByUser = function (id, name,job) {
    	$("#requestedByUserId").val(id);
    	$("#requestedByUserName").val(name);
    	$("#requestedByUserName").attr('readonly', true);
    	$('#common-modal').modal("hide");
    };
    
    /*********************************************************************
     * AOD Item Job
     *********************************************************************/
    var runJobInput = function () {
        $("#jobNo").inputClear({
            placeholder: "Select Job",
            btnMethod: "AODReportView.jobView()",
        });
    };

    var jobView = function () {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../aod/view/modal/job';
            $modal.load(url, '', function () {
                var dt_url = "../../restapi/job/tabledata";
                var func = "AODReportView.setJob";
                dtJob.getJob(dt_url, func);
                $modal.modal();
            });
        }, 1000);
    };
    
    var setJob = function (id, name) {
        $("#jobId").val(id);
        $("#jobNo").val(name);
        $("#jobNo").attr('readonly', true);
        $('#common-modal').modal("hide");
    };
    
    /*********************************************************************
     * AOD Job Maintenance Type
     *********************************************************************/    
    var runMaintenanceTypeInput = function () {
        $("#maintenanceTypeName").inputClear({
            placeholder: "Select a MaintenanceType",
            btnMethod: "AODReportView.getModalViewMaintenanceType()",
        });
    };
    
    var getModalViewMaintenanceType = function () {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../aod/view/modal/maintenancetype';
            $modal.load(url, '', function () {
                var dt_url = "../../restapi/job/tabledata";
                var func = "AODReportView.setJob";
                MaintenanceTypeSelectModal.init();
                $modal.modal();
            });
        }, 1000);
    };
    
    var setMaintenanceType = function (id, name) {
        $("#maintenanceTypeId").val(id);
        $("#maintenanceTypeName").val(name);
        $("#maintenanceTypeName").attr('readonly', true);
        $('#common-modal').modal("hide");
    };
    
    /*********************************************************************
     * Validation
     *********************************************************************/
    var initValidator = function () {

        var form = $('#frmReportAOD');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        form.validate({
            errorElement: "span", // contain the error msg in a span tag
            errorClass: 'help-block',
            errorPlacement: function (error, element) { // render error placement for each input type
                if (element.attr("type") == "radio" || element.attr("type") == "checkbox") { // for chosen elements, need to insert the error after the chosen container
                    error.insertAfter($(element).closest('.form-group').children('div').children().last());
                } else if (element.attr("name") == "dd" || element.attr("name") == "mm" || element.attr("name") == "yyyy" || element.closest('.input-group').has('.input-group-btn').length || element.closest('.form-group').has('.input-group-addon').length) {
                    error.insertAfter($(element).closest('.form-group').children('div'));
                } else if (element.closest('.form-group').has('.select2').length) {
                    error.insertAfter($(element).closest('.form-group').children('span'));
                } else {
                    error.insertAfter(element);
                }
            },
            ignore: "",
            rules: {
            	fromDate: {
                    required: true
                },
                toDate: {
                	required: true
                }, 
            },
            messages: {
            	fromDate: "Please Specify a From Date",
            	toDate: "Please Specify a To Date", 
            },
            invalidHandler: function (event, validator) { //display error alert on form submit
                successHandler.hide();
                errorHandler.show();
            },
            highlight: function (element) {
                $(element).closest('.help-block').removeClass('valid');
                $(element).closest('.form-group').removeClass('has-success').addClass('has-error').find('.symbol').removeClass('ok').addClass('required');
            },
            unhighlight: function (element) { // revert the change done by hightlight
                $(element).closest('.form-group').removeClass('has-error');
            },
            success: function (label, element) {
                label.addClass('help-block valid');
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
            runCustomerInput();
            runJobInput();
            runRequestedByUserInput();
            runMaintenanceTypeInput();
           // initValidator();
            initButtons();
        },
        
        customerView: function () {
            customerView();
        },
        
        setCustomer: function (id, name) {
            setCustomer(id, name);
        },
        
        jobView: function () {
        	jobView();
        },
        
        setJob: function (id, name) {
        	setJob(id, name);           
        },

        getModalViewMaintenanceType: function () {
        	getModalViewMaintenanceType();
        },
        
        setMaintenanceType: function (id, name) {
        	setMaintenanceType(id, name);           
        },
        
        requsestedByUserView:function(){
        	requsestedByUserView();
        },
        
        setRequestedByUser:function(id, name,job){
        	setRequestedByUser(id, name,job);
        } 

    };
}();