var AssetDepreciationReportView = function () {
    
    function initDatePickers() {
        $('.date-picker').datepicker({
            format: "yyyy-mm-dd",
            autoclose: true,
            container: $(this).closest('#picker-container')
        });
    };
    
    
    function initInputClearComponents() {
        initInputClearComponent("assetCategoryName", "Select a Asset Category", "AssetDepreciationReportView.assetCategoryView()");
        initInputClearComponent("mainLocationName", "Select a Main Location", "AssetDepreciationReportView.mainLocationView()");
//        initInputClearComponent("subLocationName", "Select a Sub Location", "AssetDepreciationReportView.subLocationView()");
//        initInputClearComponent("subLocation2Name", "Select a Sub Location 2", "AssetDepreciationReportView.subLocation2View()");
    };
    
    function initInputClearComponent(ele, placeholder, btnMethod, clearMethod) {
        $("#"+ele).inputClear({
            placeholder:placeholder,
            btnMethod:btnMethod,
            clearMethod: clearMethod
        });
    };

    function initSelect2Components() {
        $("#businessId").select2({
            placeholder: "Select a Business",
            allowClear: true
        });
    };
    
    function initModalViewCategorySelect() {
        
        var $modal = $('#common-modal');
        
        var bizId = CustomValidation.nullValueReplace($('#businessId option:selected').val());
            
            CustomComponents.ajaxModalLoadingProgressBar();
            setTimeout(function () {
                var url = '../../report/asset-depreciation/modal/view/asset-categories';
                $modal.load(url, '', function () {
                    AssetCategoryDataTable.init(bizId);
                    $modal.modal();
                });
            }, 1000);
        
    };
    
    function initModalViewMainLocationSelect() {
        
        var bizId = $('#businessId option:selected').val();
        
        if ( bizId != null && bizId != "") {
        
            var func = "setMainLocation";
            var url = "../../restapi/asset/facility-tabledata?bizId=" + bizId;
            
            loadAssetViewModal(func, url, "Main+Locations");
        
        } else {
            alert("Please select a business!");
        }
    };
    
    function initModalViewSubLocationSelect() {
        
        var mainLocation = $('#mainLocationId').val();
       
        if ( mainLocation != null && mainLocation != "") {
            
            var func = "setSubLocation";
            var url = "../../restapi/asset/sub-location?parentLocationId=" + mainLocation;
            
            loadAssetViewModal(func, url, "Sub+Locations");
        } else {
            alert("Please select a main location!");
        }
        
    };
    
    function initModalViewSubLocation2Select() {
        
        var subLocation = $('#subLocationId').val();
        
        if ( subLocation != null && subLocation != "") {
            var func = "setSubLocation2";
            var url = "../../restapi/asset/sub-location?parentLocationId=" + subLocation;
           
            loadAssetViewModal(func, url, "Sub+Locations");       
        } else {
            alert("Please select a sub location!");
        }

    };
    
    function loadAssetViewModal(func, dUrl, title) {
        
        var $modal = $('#common-modal');
        
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../../report/asset-depreciation/modal/view/assets?title=' + title;
            $modal.load(url, '', function () {
                AssetSelectModal.init(func, dUrl);
                $modal.modal();
            });
        }, 1000);
    };

    /*********************************************************************
     * Validation
     *********************************************************************/
    function initValidator() {

        var form = $('#frmReportAssetDepreciation');
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
            initValidator();
            initDatePickers();
            initSelect2Components();
            initInputClearComponents();
        },
        
        assetCategoryView: function () {
            initModalViewCategorySelect();
        },
        
        mainLocationView: function () {
            initModalViewMainLocationSelect();
        },
        
        subLocationView: function () {
            initModalViewSubLocationSelect();
        },
        
        subLocation2View: function () {
            initModalViewSubLocation2Select();
        }

    };
}();