var MRNAdd = function () {

    var runMRNTypeIdSelect = function () {
        $("#mrnTypeId").select2({
            placeholder: "Select a MRN Type",
            allowClear: true
        });
    };

    var runDatePicker = function () {
        $('.date-picker').datepicker({
            container: $('.date-picker').closest("div"),
            autoclose: true
        });
    };

    var runWorkOrderInput = function () {
        $("#mrnWoNo").inputClear({
            placeholder: "Please Specify a Work Order",
            btnMethod: "MRNAdd.mrnWorkOrderModalView()",
        });
    };

    /*********************************************************************
     * Init Custom Components
     *********************************************************************/

    var runMRNCustomerInput = function () {
        $("#mrnCustomerName").inputClear({
            placeholder: "Customer Name",
            btnMethod: "MRNAdd.mrnCustomerModalView()",
        });
    };

    var runRequestedUserInput = function () {
        $("#requestedUserName").inputClear({
            placeholder: "Requested User",
            btnMethod: "MRNAdd.mrnRequestUserModalView()",
        });
    };

    var runMRNBusinessSelect = function(){
    	$("#businessId").change(function() {
			var businessId = $("#businessId option:selected").val(); 
			setMRNCode(businessId);  

		});
    };
    
	var setMRNCode = function(id) {
		
    	$.ajax({
            type: "GET",
            url: "../mrn/code-by-business?businessId=" + id,
            contentType: "application/json",
            dataType: "json",
            success: function (result) {
                if (result.status == "SUCCESS") {
                	$('#mrnNo').val(result.data);
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
     
};
    /*********************************************************************
     * Init Validation
     *********************************************************************/
    
    var runValidator = function () {

        var form = $('#frm_mrn');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        $('#frm_mrn').validate({
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
                    // for other inputs, just perform default behavior
                }
            },
            ignore: "",
            rules: {
                mrnNo: {
                    required: true
                },

                mrnType: {
                    required: true
                },
                businessId: {
                    required: true
                },
                siteId: {
                    required: true
                },
                mrnCustomerName: {
                   // required: true
                },
                jobNo: {
                    required: true
                },
            },
            messages: {
            	mrnNo: "Please Specify a MRN No",
            	mrnType: "Please Select MRN Type",
                businessId: "Please Select Business",
                siteId: "Please Select Site",
                mrnCustomerName: "Please Select a customer",
                jobNo: "Please Select a job No",
            },
            invalidHandler: function (event, validator) {
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

    /*********************************************************************
     * Init Modals
     *********************************************************************/

    var mrnCustomerModalView = function () {
        var bizId = $('#businessId').val();
        if (bizId != null && bizId > 0) {
            var $modal = $('#master-modal-datatable');
            CustomComponents.ajaxModalLoadingProgressBar();
            setTimeout(function () {
                var url = '../mrn/customermodalview';
                $modal.load(url, '', function () {
                    MRNCustomers.init(bizId);
                    $modal.modal();
                });
            }, 1000);
        } else {
            alert("Please Select a Business First.");
        }
    };

    var mrnRequestUserModalView = function () {
        var bizId = $('#businessId').val();
        if (bizId != null && bizId > 0) {
            var $modal = $('#master-modal-datatable');
            CustomComponents.ajaxModalLoadingProgressBar();
            setTimeout(function () {
                var url = '../mrn/requestusermodalview';
                $modal.load(url, '', function () {
                    MRNUsers.init(bizId);
                    $modal.modal();
                });
            }, 1000);
        } else {
            alert("Please Select a Business First.");
        }
    };

    var mrnWorkOrderModalView = function () {
        var bizId = $('#businessId').val();
        if (bizId != null && bizId > 0) {
            var $modal = $('#master-modal-datatable');
            CustomComponents.ajaxModalLoadingProgressBar();
            setTimeout(function () {
                var url = '../mrn/workordermodalview';
                $modal.load(url, '', function () {
                    MRNWorkOrders.init(bizId);
                    $modal.modal();
                });
            }, 1000);
        } else {
            alert("Please Select a Business First.");
        }
    };

    var acceptIsNotNull = function (obj) {
        return CustomValidation.isEmptyValueById("#")
    };

    var runBusinessSelect = function () {
        $("#businessId").select2({
            placeholder: "Select a Business",
            allowClear: true
        });
    };

    var runBusinessSiteSelect = function () {
        $("#siteId").select2({
            placeholder: "Select a Site",
            allowClear: true
        });
    };

    var runBusinessSiteFetch = function () {
        $("#businessId").change(function () {
            var businessId = $("#businessId option:selected").val();
            $.ajax({
                type: "GET",
                url: "../asset/getsites?id=" + businessId,
                contentType: "application/json",
                dataType: "json",
                success: function (output) {
                    $("#siteId").find("option").remove();
                    $.each(output, function (key, siteList) {
                        $('#siteId').append($('<option>', {
                            value: siteList.id
                        }).text(siteList.name));
                    });
                    runBusinessSiteSelect();
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    alert(xhr.status + " " + thrownError);
                },
                error: function (e) {
                    alert("Failed to load site");
                }
            });
        });
    };

    var runMRNTypeChange = function () {

        $("#mrnTypeId").change(function () {
            var aodType = $("#mrnTypeId option:selected").val();
            if (aodType == "OTHER") {
                $("#mrnWoId").closest('.form-group').hide();
            } else {
                $("#mrnWoId").closest('.form-group').show();
            }
        }).trigger('change');

    };

    return {

        init: function () {
            runMRNTypeIdSelect();
            runDatePicker(); 
            runValidator();
            runBusinessSelect();
            runBusinessSiteFetch();
            runBusinessSiteSelect();
            runMRNTypeChange();
            runMRNCustomerInput();
            runRequestedUserInput();
            runWorkOrderInput();
            runMRNBusinessSelect();
        },

        mrnCustomerModalView: function () {
            mrnCustomerModalView();
        },

        mrnRequestUserModalView: function () {
            mrnRequestUserModalView();
        },

        mrnWorkOrderModalView: function () {
           mrnWorkOrderModalView();
        },

        acceptIsNotNull: function () {
            acceptIsNotNull();
        }

    };
}();
