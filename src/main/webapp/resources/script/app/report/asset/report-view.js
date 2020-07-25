var AssetReportView = function () {


	var initButtons = function() {
		initButtonViewReport();
	};
	
	var initButtonViewReport = function() {
	    $('#viewAssetReport').on('click', function (event) {
	    	//if ($('#frmReportAsset').valid()) {				
	    		AssetDetailTable.init();
			//}
	    });
	};
	

    /*********************************************************************
     * Asset Location
     *********************************************************************/
    var runAssetInput = function () {
        $("#locationName").inputClear({
            placeholder: "Select a Location",
            btnMethod: "AssetReportView.assetView()",
        });
    };

    var assetView = function () {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../asset/locationview';
            $modal.load(url, '', function () {
                var func = "AssetReportView.setAsset";
                dtLocation.init(func);
                $modal.modal();
            });
        }, 1000);
    };
    
    var setAsset= function (id, name) {
        $("#locationId").val(id);
        $("#locationName").val(name);
        $('#common-modal').modal("hide");
    };


    /*********************************************************************
     * Asset Model
     *********************************************************************/
    var runAssetModelInput = function () {
    	$("#assetModelName").inputClear({
    		placeholder: "Select a Model",
    		btnMethod: "AssetReportView.assetModelView()",
    	});
    };
    
    var assetModelView = function () {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../asset/modelview';
            $modal.load(url, '', function () {
            	AssetModelSelectModal.runDataTable('AssetReportView.setAssetModel');
                $modal.modal();
            });
        }, 1000);
    };
    
    var setAssetModel= function (id, name) {
    	$("#assetModelId").val(id);
    	$("#assetModelName").val(name);
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
        	runAssetInput();
        	runAssetModelInput();
            initButtons();
        },
        
        assetView: function () {
        	assetView();
        },
        
        setAsset: function (id, name) {
        	setAsset(id, name);
        },
        
        assetModelView: function () {
        	assetModelView();
        },
        
        setAssetModel: function (id, name) {
        	setAssetModel(id, name);           
        }

    };
}();