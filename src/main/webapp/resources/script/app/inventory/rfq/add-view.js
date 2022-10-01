var RFQAdd = function () {


	var runSupplierBusinessSelect = function () {
		$("#supplierId").select2({
			placeholder: "Select a Supplier",
			allowClear: true
		});
	};
	var runBusinessSelect = function () {
		$("#businessId").select2({
			placeholder: "Select a Business",
			allowClear: true
		});
	};
    
    var runSupplierContrySelect = function () {
    	$("#supplierCountry").select2({
    		placeholder: "Select a Country",
    		allowClear: true
    	});
    };

    var runDatePicker = function () {
        $('.date-picker').datepicker({
            // container: $('.date-picker').closest("div"),
            autoclose: true
        });
    };

    var runCheckboxes = function () {
        $('input[type="checkbox"].grey, input[type="radio"].grey').iCheck({
            checkboxClass: 'icheckbox_minimal-grey',
            radioClass: 'iradio_minimal-grey',
            increaseArea: '10%'
        });
    };
    var runRFQBusinessSelect = function(){
    	$("#businessId").change(function() {
			var businessId = $("#businessId option:selected").val(); 
			setRFQCode(businessId);  

		});
    };
    
	var setRFQCode = function(id) {
		
        	$.ajax({
                type: "GET",
                url: "../rfq/code-by-business?businessId=" + id,
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
    var runValidator = function () {
        var form = $('#rfq_add_frm');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        $('#rfq_add_frm').validate({
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
                code: {
                    required: true
                }
            },
            messages: {
                code: "Please Specify a Code",
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
            runSupplierBusinessSelect();
            runSupplierContrySelect();
            runDatePicker();
            runCheckboxes();
            runValidator();
            runBusinessSelect();
            runRFQBusinessSelect();
        },
        initCheckBoxes: function () {
            runCheckboxes();
        }
    };
}();
