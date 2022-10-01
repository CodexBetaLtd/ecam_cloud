var PurchaseOrderAdd = function () {
	
    var runWorkOrderInput = function () {
        $("#mrnWoNo").inputClear({
            placeholder: "Please Specify a Work Order",
            btnMethod: "MRNAdd.mrnWorkOrderModalView()",
        });
    };
	var runBusinessSelect = function () {
		$("#businessId").select2({
			placeholder: "Select a Business",
			allowClear: true
		});
	};
    
    var runBillingTermSelect = function () {
    	$("#billingTerms").select2({
    		placeholder: "Select Billing Term",
    		allowClear: true
    	});
    };
    
    var runSendUsingSelect = function () {
    	$("#sendUsing").select2({
    		placeholder: "Select Method",
    		allowClear: true
    	});
    };
    var runAdditionalCostTypeSelect = function () {
        $("#poAdditionalCostTypeId").select2({
            placeholder: "Select Method",
            allowClear: true,
            dropdownParent: $("#additionalCostModal")
        });
    };

    var runDatePicker = function () {
        $('.date-picker').datepicker({
            autoclose: true,
            container: '#picker-container'
        });
    };

    var runPurchaseorderBusinessSelect = function(){
    	$("#businessId").change(function() {
			var businessId = $("#businessId option:selected").val(); 
			setPurchaseorderCode(businessId);  

		});
    };
    
    var runTaxInput = function () {
        $("#taxName").inputClear({
            placeholder: "Please Specify a Tax",
            btnMethod: "PurchaseOrderAdd.poTaxLoad()",
        });
    };
    var poTaxLoad = function(){
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../purchaseorder/poTaxView';
            $modal.load(url, '', function () {
            	dtPurchaseOrderTax.dtPOTaxList("tax_select_tbl","../restapi/tax/tabledata", "setData");
                $modal.modal();
            });
        }, 1000);
    };
    
	var setPurchaseorderCode = function(id) {
		
        	$.ajax({
                type: "GET",
                url: "../purchaseorder/code-by-business?businessId=" + id,
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
         
	};
    /* 
	 *load siteList relevant to business
	 */   
    var runAssetBusinessSelect = function(){
    	$("#businessId").change(function() {
			var businessId = $("#businessId option:selected").val();
			$.ajax({
				type : "GET",
				url : "../workorder/selectBusiness/" + businessId,
				contentType : "application/json",
				dataType : "json",
				success : function(output) {
					$("#siteId").find("option").remove();
					$.each(output, function(key, siteList) {
						$('#siteId').append($('<option>', {
							value : siteList.id
						}).text(siteList.name));
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
		});
    	
    };
    var runValidator = function () {
        var form = $('#bom_group_add_frm');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        $('#bom_group_add_frm').validate({
            errorElement: "span", // contain the error msg in a span tag
            errorClass: 'help-block',
            errorPlacement: function (error, element) { // render error placement for each input type
                if (element.attr("type") == "radio" || element.attr("type") == "checkbox") { // for chosen elements, need to insert the error after the chosen container
                    error.insertAfter($(element).closest('.form-group').children('div').children().last());
                } else if (element.attr("name") == "dd" || element.attr("name") == "mm" || element.attr("name") == "yyyy") {
                    error.insertAfter($(element).closest('.form-group').children('div'));
                } else {
                    error.insertAfter(element);
                    // for other inputs, just perform default behavior
                }
            },
            ignore: "",
            rules: {
                name: {
                    minlength: 2,
                    required: true
                },
                businessId: {
                    required: true
                }
            },
            messages: {
                name: "Please Specify a Name",
                businessId: "Please Select a Business"
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
            runValidator();
            runBillingTermSelect();
            runDatePicker();
            runSendUsingSelect();
            runAdditionalCostTypeSelect();
            runBusinessSelect();
            runPurchaseorderBusinessSelect();
            runTaxInput();
            
        },
    
    poTaxLoad:function(){
    	poTaxLoad();
    }
    };
}();


var PurchaseOrderValidation = function () {

    var isNullOrIsEmpty = function (idList, clsList) {

        var emptyValidation = {
            errorName: "",
            successType: true,
            errorList: []
        };

        if (idList != null && idList.length > 0) {
            $.each(idList, function (index, obj) {
                // $el.append($("<option></option>").attr("value", obj.id).text(obj.name));
                if ($(obj).val() == null || $.trim($(obj).val()) === "") {
                    emptyValidation.successType = false;
                    // emptyValidation.errorName = emptyValidation.errorName + "Cannot be Null or Empty ("+obj+")";
                    emptyValidation.errorName += "Cannot be Null or Empty (" + obj + ")";
                }
            });
        }
        ;

        if (clsList != null && clsList.length > 0) {
            $.each(clsList, function (index, clsObj) {
                if ($(clsObj).val() == null || $.trim($(clsObj).val()) === "") {
                    emptyValidation.successType = false;
                    // emptyValidation.errorName = emptyValidation.errorName + "Cannot be Null or Empty ("+clsObj+")";
                    emptyValidation.errorName += "Cannot be Null or Empty (" + clsObj + ")";
                }
            });
        }

        return emptyValidation;
    };

    var isCheckedAny = function (idList, clsList) {
        var checkedAnyValidation = {
            errorName: "",
            successType: false,
            errorList: []
        };

        if (clsList != null && clsList.length > 0) {
            $.each(clsList, function (index, obj) {
                var chkBoxes = "";
                $("." + obj).each(function () {
                    if ($(this).prop("checked") == true) {
                        checkedAnyValidation.errorName = " Please Checked Any Notification Type";
                        checkedAnyValidation.successType = true;
                    }
                });
            });
        }
        return checkedAnyValidation;
    };

    return {

        isNullOrIsEmpty: function (idList, clsList) {
            return isNullOrIsEmpty(idList, clsList);
        },
        isCheckedAny: function (idList, clsList) {
            return isCheckedAny(idList, clsList);
        },

 

    };
}();