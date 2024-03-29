var PurchaseOrderReportView = function () {

    /*********************************************************************
     * Init Components
     *********************************************************************/

    var initSearchMonthPicker = function () {
        $('#searchMonth').datepicker({
            format: "yyyy-mm",
            startView: "year",
            minViewMode: "months",
            autoclose: true,
            container: '#picker-container'
        });
    };

    var initDataTable = function () {
        if ($("#searchType").val() == "SUMMARY") {
            CloseJobReportSummaryTable.initDataTable();
        } else {
            CloseJobReportDetailTable.initDataTable();
        }
    };
	
	var initTypeSelect = function () {
        $("#searchType").select2({
            placeholder: "Select a Report Type",
            allowClear: true
        }).change(function () {
            var searchType = $("#searchType option:selected").val();
            $.ajax({
                type: "GET",
                url: "../../report/closejob/getReportTable?searchType=" + searchType,
                success: function (output) {
                	$('#report_tbl_div').html("");
                	$("#report_tbl_div").html(output);
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    alert(xhr.status + " " + thrownError);
                },
                error: function (e) {
                    console.log(e);
                }
            });
        });
    };

    var initBusinessSelect = function () {
        $("#searchBusiness").select2({
            placeholder: "Select a Business",
            allowClear: true
        });
    };

    /*********************************************************************
     * Init Buttons
     *********************************************************************/
    
    var initSearchButton = function () {
    	$('#btn-close-job-search').on('click', function () {
    		if ($('#frm_purchase_order_report').valid()) {
    			initDataTable();
    		}
    	});
    };
    
    var initPrintPDFButton = function () {
    	$('#btn_print_pdf').on('click', function () {
    		if ($('#frm_purchase_order_report').valid()) {
    			$('#document-type').val("pdf");
    			$('#frm_purchase_order_report').attr('action', "../../report/purchaseorder/print").submit();
    		}
    	});
    };
    
    var initPrintCSVButton = function () {
        $('#btn_print_csv').on('click', function () {
            if ($('#frm_purchase_order_report').valid()) {
            	$('#document-type').val("csv");
            	$('#frm_purchase_order_report').attr('action', "../../report/purchaseorder/print").submit();
            }
        });
    };


    /*********************************************************************
     * Validation
     *********************************************************************/
    var initValidator = function () {

        var form = $('#frm_purchase_order_report');
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
                searchBusiness: {
                    required: true
                },
                searchMonth: {
                    required: true
                },
                searchType: {
                    required: true
                }
            },
            messages: {
                searchBusiness: "Please Select a Business",
                searchMonth: "Please Select a Month",
                searchType: "Please Select a Report Type"
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
            initSearchMonthPicker();
            initBusinessSelect();
            initTypeSelect();
            initSearchButton();
            initPrintPDFButton();
            initPrintCSVButton();
            initValidator();
        }
    };
}();