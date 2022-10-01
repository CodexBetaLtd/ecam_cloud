var ReceiptOrderAdd = function () {
    
	var initSupplierBusinessSelect = function () {
		$("#supplierId").select2({
			placeholder: "Select a Supplier",
			allowClear: true
		});
	};
	var initBusinessSelect = function () {
		$("#businessId").select2({
			placeholder: "Select a Business",
			allowClear: true
		});
	};
	var initReceiptOrderTypeSelect = function () {
		$("#receiptOrderTypeId").select2({
			placeholder: "Select a ReceiptOrder Type",
			allowClear: true
		});
	};

    var initDatePicker = function () {
        $('.date-picker').datepicker({
            autoclose: true
        });
    }; 
    
    /********************************************
     * Initialize InputClear Components
     ********************************************/

    function initInputClearComponents(){
        initInputClearSupplier();
    };    
    
    function initInputClearSupplier(){
        $("#supplierName").inputClear({
            placeholder:"Please specify a supplier",
            btnMethod:"ReceiptOrderAdd.addSupplier()",
        });
    };

    /********************************************
     * Initialize modal views
     ********************************************/
    
    function initModalSupplierSelect() {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../receiptorder/view/modal/suppliers';
            $modal.load(url, '', function () {
                DatatableModalSuppliers.init(
                        "common-modal",
                        "tbl_suppliers",
                        "../restapi/supplier/tabledata",
                        "setData"
                );
                $modal.modal();
            });
        }, 1000);
    };
    
    var initValidator = function () {
        var form = $('#receipt_order_add_frm');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        $('#receipt_order_add_frm').validate({
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
                supplierId: {
                    required: true
                },
                code: {
                    required: true
                }
            },
            messages: {
            	supplierId: "Please Select a Supplier",
            	code: "Please type code"
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
    
    var initReceiptOrdeBusinessSelect = function(){
    	$("#businessId").change(function() {
			var businessId = $("#businessId option:selected").val(); 
			setReceiptOrderCode(businessId);  

		});
    };
    
	var setReceiptOrderCode = function(id) {
		
        	$.ajax({
                type: "GET",
                url: "../receiptorder/code-by-business?businessId=" + id,
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

    return {

        init: function () {
            initValidator();
            initInputClearComponents();
            initBusinessSelect();
            initDatePicker();
            initReceiptOrderTypeSelect();
            initReceiptOrdeBusinessSelect();
   
        },
    
        addSupplier: function () {
            initModalSupplierSelect(); 
        }
    };
}();
