/*********************************************************************
 * Work Order add-view
 *********************************************************************/
var ExWorkorderAdd = function () {
	
    /*********************************************************************
     * Validation
     *********************************************************************/

    var runValidator = function () {
        var form = $('#ex_wo_add_frm');
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
*/

    var initBtnAssetInput = function () {
        $("#assetName").inputClear({
            placeholder: "Select an asset",
            btnMethod: "ExWorkorderAdd.woAssetView()"
        });
    };

    var initBtnServiceProviderInput = function () {
    	$("#serviceProviderName").inputClear({
    		placeholder: "Select a service provider",
    		btnMethod: "ExWorkorderAdd.woSupplireView()"
    	});
    };
    var initBtnServiceRequestInput = function () {
        $("#serviceRequestName").inputClear({
            placeholder: "Select a user",
            btnMethod: "ExWorkorderAdd.woAssignedUserView()"
        });
    };

    var runDatePicker = function () {
        $('.date-picker').datepicker({
            autoclose: true,
            container: '#picker-container'
        });
    };

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

    /* 
	 *load siteList relevant to business
	 */   
    var runAssetBusinessSelect = function(){
    	$("#businessId").change(function() {
			var businessId = $("#businessId option:selected").val(); 
			setDataToSiteSelect2(businessId);
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

    var setWOServiceProvider = function (id, name) {
        $('#serviceProviderId').val(id);
        $('#serviceProviderName').val(name);
        $('#master-modal-datatable').modal('toggle');
    };

    var setAssignedUser = function (id, name) {
        $('#serviceRequestId').val(id);
        $('#serviceRequestName').val(name);
        $('#master-modal-datatable').modal('toggle');
    };

    var setAsset = function (id, name) {
        $('#assetId').val(id);
        $('#assetName').val(name);
        $('#master-modal-datatable').modal('toggle');
    };


    /*********************************************************************
     * Modals
     *********************************************************************/
    var woAssetView = function () {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../exworkorder/asset-select-modal-view';
            $modal.load(url, '', function () {
            
            	dtWorkOrderAsset.dtAssetsByBusiness("ExWorkorderAdd.setAsset");
            	
                $modal.modal();
            });
        }, 1000);
    };


    var woAssignedUserView = function () {
            var $modal = $('#master-modal-datatable');
            CustomComponents.ajaxModalLoadingProgressBar();
            setTimeout(function () {
                var url = '../exworkorder/user-select-modal-view';
                $modal.load(url, '', function () {
                    dtWorkOrderUser.getUserList("ExWorkorderAdd.setAssignedUser", businessId);
                    $modal.modal();
                });
            }, 1000);


    };

    var woSupplireView = function () {
  	        var $modal = $('#master-modal-datatable');
	        CustomComponents.ajaxModalLoadingProgressBar();
	        setTimeout(function () {
	            var url = '../exworkorder/supplier-select-modal-view';
	            $modal.load(url, '', function () {
	            	woServiceProvider.woSupplier("ExWorkorderAdd.setWOServiceProvider");
	                $modal.modal();
	            });
	        }, 1000);		
    }
    
    

    return {
        init: function () {
        //    runValidator();
            runDatePicker();
            initBtnAssetInput();
            initBtnServiceProviderInput();
            initBtnServiceRequestInput();
            runBusinessSelect();
            runSiteSelect();
            runAssetBusinessSelect();
        },

        woSupplireView: function () {
        	woSupplireView();
        },
        
        setWOServiceProvider: function (id, name) {
        	setWOServiceProvider(id, name);
        },

        woAssignedUserView: function () {
        	woAssignedUserView();
        },
        
        setAssignedUser: function (id, name) {
        	setAssignedUser(id, name);
        },

        woAssetView: function () {
        	woAssetView();
        },
        
        setAsset: function (id, name) {
        	setAsset(id, name);
        }
        
    };
}();