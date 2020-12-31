var PartStockAgeReportView = function () {


	/************************************************
	 *  Init Buttons
	 ************************************************/ 

	var initButoons = function() {
		$('#viewStockAgeReport').on('click', function (event) {
	    	if ($('#frmReportStockAge').valid()) {	
	    		var type =  $("#stockAgeAnalyseFilter :selected").val(); 
	    		if (type == 'BY_DAYS') { 
	    			PartStockAgeDetailTable.initTableByDays();
				} else if (type == 'BY_YEARS'){
					PartStockAgeDetailTable.initTableByYears();
				}
			}
	    });
	};
	
	/************************************************
	 *  Initialize DatePickers
	 ************************************************/
	
    var initDatePickers = function() {
    	$('#stockDate').datepicker({
    		format: "dd-mm-yyyy",
            autoclose: true,
            container: '#to-container'
        });
	};
	
    /************************************************
     *  Item
     ************************************************/
    var runItemInput = function () {
        $("#itemCode").inputClear({
            placeholder: "Select a Item",
            btnMethod: "PartStockAgeReportView.itemView()",
        });
    };

    var itemView = function () {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../stockAge/itemView';
            $modal.load(url, '', function () {
                var func = "PartStockAgeReportView.setItem";
                dtItem.getItemDataTable(null, func);
                $modal.modal();
            });
        }, 1000);
    };

    var setItem = function (id, name) {
        $("#itemId").val(id);
        $("#itemCode").val(EncodeDecodeComponent.getBase64().decode(name));
        $("#itemCode").attr('readonly', true);
        $('#common-modal').modal("hide");
    };
    
    var runItemTypeSelect = function () {
        $("#itemTypeId").select2({
            placeholder: "Select a Item Type",
            allowClear: true
        });
    };
    
    var runStockAgeTypeSelect = function () {
        $("#stockAgeAnalyseFilter").select2({
            placeholder: "Select a Stock Age Type",
            allowClear: true
        }).on('change', function() {
        	initTableView();
		});
        
        initTableView();
    };
    
    var initTableView = function() {
		var type =  $("#stockAgeAnalyseFilter :selected").val(); 
		if (type == 'BY_DAYS') {
			$("#stock_age_by_days").removeClass("hidden");
			$("#stock_age_by_years").addClass('hidden');
		} else {			
			$("#stock_age_by_days").addClass('hidden');
			$("#stock_age_by_years").removeClass("hidden");
		}
	};
    /*********************************************************************
     * Validation
     *********************************************************************/
    var initValidator = function () {

        var form = $('#frmReportStockAge');
        
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
            	stockAgeAnalyseFilter: {
                    required: true
                },
                stockDate: {
                	required: true
                }
            },
            messages: {
            	stockAgeAnalyseFilter: "Please Select a Type",
            	stockDate: "Please Specify a Date",
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
            runItemInput();
            initButoons();
            runItemTypeSelect();
            runStockAgeTypeSelect();
            initValidator();
            initDatePickers();
        },
        
        itemView: function () {
            itemView();
        },
        
        setItem: function (id, name) {
            setItem(id, name);
        },
    };
}();