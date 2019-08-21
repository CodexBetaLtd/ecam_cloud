var PartAdd = function () { 
	
	var initJsTree=function(){

	}

    var initImageInput = function () {
    	 var src = "../restapi/part/part-image?id=" + $("#partId").val();
         CustomComponents.fileInput("partImage", true, true, src, "Part Image" );
    };
    
    var initBusiness = function () {
        $("#businessId").select2({
            placeholder: "Select Business",
            allowClear: true
        });
    };

    var initSite = function () {
        $("#siteId").select2({
            placeholder: "Select Site",
            allowClear: true
        });
    };

    var initBusinessGroupSelect = function () {
        $("#businessGroupId").select2({
            placeholder: "Select a Business Group",
            allowClear: true
        });
    };

    var initAccountSelect = function () {
        $("#accountsId").select2({
            placeholder: "Select a Account",
            allowClear: true
        });
    };

    var initChargeDepartmentSelect = function () {
        $("#partChargeDepartmentId").select2({
            placeholder: "Select a Charge Department",
            allowClear: true
        });
    };
    
    var initBrandSelect = function(){
    	$("#brandName").inputClear({
    		placeholder:"Select a Brand",
        	btnMethod:"AssetBrandSelectModal.assetBrandView()",
    	});
    };
    
    var initModelSelect = function(){
    	$("#modelName").inputClear({
    		placeholder:"Select a Model",
    		btnMethod:"AssetModelSelectModal.assetModelView()",
    	});
    };
    
    var initPartCategoryInput = function(){
    	$("#partCategoryName").inputClear({
    		placeholder:"Select a Part Category",
        	btnMethod:"PartAdd.partCategoryView()",
    	});
    };
    
    var partCategoryView = function () {
    	var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../part/part-category-select-view';
            $modal.load(url, '', function () {
            	PartCategoryDataTable.init();
                $modal.modal();
            });
        }, 1000);
    };
    
    var partCategoryAddView = function () {
    	var $modal = $('#category-add-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
    	setTimeout(function () {
    		var url = '../part/part-category-add-view?type=PARTS_AND_SUPPLIES';
    		$modal.load(url, '', function (){
    			PartCategoryAddModal.init();
    			PartCategoryDataTable.init();
    			$modal.modal();
    		});
    	}, 1000);
    };

    var setPartCategory = function (categoryId, categoryName) {
    	$('#partCategoryId').val(categoryId);
    	$('#partCategoryName').val(categoryName);
    	$('#common-modal').modal('toggle');
    };
    
	var initValidator = function () {
        var form = $('#part_add_frm');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        $('#part_add_frm').validate({
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
                    minlength: 3,
                    required: true,
                    remote: {
                        url: "../validate/part/validate-by-code",
                        type: "GET",
                        data: {
                            id: $('#partId').val()
                        }
                    }
                },
                name: {
                    minlength: 2,
                    required: true
                },
                businessId: {
                    required: true
                },
                businessGroupId: {
                	required: true
                },
                partCategoryName: {
                    required: true
                }
            },
            messages: {
                code: {
                    required: "Please Specify a Code",
                    minlength: "Code Contain minimum 3 Characters",
                    remote: jQuery.validator.format("Code {0} is already taken.")
                },
                name: "Please Specify a Name",
                businessId: "Please Selecet a Business",
                businessGroupId: "Please Selecet a Business Group",
                partCategoryName: "Please Select a part Category"
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
    		initValidator();
            initBusinessGroupSelect();
            initAccountSelect();
            initChargeDepartmentSelect();
            initBusiness();
            initSite();
            initBrandSelect();
            initModelSelect();
            initImageInput();
            initPartCategoryInput();
            initJsTree();
        },
        
	    setPartCategory:function(categoryId, categoryName){
	    	setPartCategory(categoryId, categoryName);
	    },
	    
	    partCategoryView:function(){
	    	partCategoryView();
	    },
	    
	    partCategoryAddView:function(){
	    	partCategoryAddView();
	    }
    };
}();