var WearHouseAdd = function () {
	

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

    var runCountrySelect = function () {
        $("#countryId").select2({
            placeholder: "Select a Country",
            allowClear: true
        });
    };

    var initWarehouseCategorySelect = function(){
    	$("#warehouseCategoryName").inputClear({
    		placeholder:"Select a Warehouse Category",
    		btnMethod:"WearHouseAdd.assetCategoryView()",
    	});
    };
	var initParentCategorySelect = function(){
    	$("#parentCategoryName").inputClear({
    		placeholder:"Select a Warehouse Category",
        	btnMethod:"WearHouseAdd.parentAssetCategoryView()",
    	});
    };
    
    var initMap=function(){
        var var_location = new google.maps.LatLng(locationDetails['latitude'],locationDetails['longitude']);
        var var_mapoptions = {
            center: var_location,
            zoom: 10
        };
        var var_map = new google.maps.Map(document.getElementById("map-container-custome"),var_mapoptions);
        var var_marker = new google.maps.Marker({
            position: var_location,
            map: var_map,
            title: locationDetails['titel'],
            animation: google.maps.Animation.DROP
        });
        
        google.maps.event.addListener(var_map, 'click', function (event) {
        	displayCoordinates(event.latLng); 
        	var_location = new google.maps.LatLng(locationDetails['latitude'],locationDetails['longitude'])
        	var_marker.setPosition(var_location)
        });
        
        $('#longitude').on('keyup', function () {
        	locationDetails['longitude']=$('#longitude').val()
        	var_location = new google.maps.LatLng(locationDetails['latitude'],locationDetails['longitude'])
        	var_marker.setPosition(var_location)
        	initMap();
        });
	    $('#latitude').on('keyup', function () {
	    	locationDetails['latitude']=$('#latitude').val()
        	var_location = new google.maps.LatLng(locationDetails['latitude'],locationDetails['longitude'])
        	var_marker.setPosition(var_location)
        	initMap()
	    });
	    setLocation()
    }
    
    function displayCoordinates(pnt) {
        locationDetails['latitude']= pnt.lat().toFixed(4)
        locationDetails['longitude']=pnt.lng().toFixed(4)    
        setLocation()
    }
    
    var setLocation=function(){	
    	$('#longitude').val(locationDetails['longitude'])
    	$('#latitude').val(locationDetails['latitude'])
    }

    var runValidator = function () {
        var form = $('#wearhouse_add_frm');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        $('#wearhouse_add_frm').validate({
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
                    minlength: 2,
                    required: true,
                },
                name: {
                    required: true
                },
                businessId: {
                    required: true
                },
                businessTypeId: {
                	required: true
                },
                siteId: {
                    required: true
                },
                address: {
                	required: true
                },
                assetCategoryName: {
                	required: true
                },
                parentAssetCategoryName: {
                    required: true
                }
            },
            messages: {
                code: "Please Specify a Code.",
                name: "Please specify a WareHouse name",
                businessId: "Please Select a Business",
                businessTypeId: "Please Select a Business Type",
                siteId: "Please Select a Site",
                addressLine1: "Please specify address ",
                assetCategoryName: "Please specify warehouse category ",
            	parentAssetCategoryName: "Please specify parent warehouse category "
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
                    runSiteSelect();
                    // runBusinessSiteSelect();
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

    var assetCategoryView = function () {
    	var id=$('#parentCategoryId').val()
    	var $modal = $('#master-modal-datatable');
    	CustomComponents.ajaxModalLoadingProgressBar();
    	setTimeout(function () {
    		var url = '../warehouse/assetcategoryview?type='+categoryType;
    		$modal.load(url, '', function () {
    			WarehouseCategoryDataTable.init("../restapi/assetCategory/tabledatabytypeparentid?id="+id,'WearHouseAdd.setAssetCategory');
    			$modal.modal();
    		});
    	}, 1000);
    };
    var parentAssetCategoryView = function () {
    	var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
    	setTimeout(function () {
    		var url = '../warehouse/assetcategoryview?type='+categoryType
    		$modal.load(url, '', function () {
    			WarehouseCategoryDataTable.init("../restapi/assetCategory/tabledatabytype?type="+categoryType,'WearHouseAdd.setParentAssetCategoryView');
    			$modal.modal();
    		});
    	}, 1000);
    };
    
    var assetCategoryAddView = function () {
    	var $modal = $('#category-add-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
    	setTimeout(function () {
    		var url = '../warehouse/assetcategoryadd?type='+categoryType;
    		$modal.load(url, '', function (){
    			WarehouseCategoryAddModal.init();
    			WarehouseCategoryDataTable.init();
    			$modal.modal();
    		});
    	}, 1000);
    };
    
    var setAssetCategory  = function(id,name){
    	$('#warehouseCategoryId').val(id);
    	$('#warehouseCategoryName').val(name);
    	$('#master-modal-datatable').modal('toggle');
    };
    var setParentAssetCategoryView  = function(id,name){
    	$('#parentCategoryId').val(id);
    	$('#parentCategoryName').val(name);
    	$('#master-modal-datatable').modal('toggle');
    };
    
    return {

        init: function () {
            runValidator();
            runCountrySelect();
            runBusinessSelect();
            runSiteSelect();
            runBusinessSiteFetch();
            initWarehouseCategorySelect();
            initParentCategorySelect();
          //  initMap();

        },
        assetCategoryView:function(){
        	assetCategoryView();
        },
        assetCategoryAddView:function(){
        	assetCategoryAddView();
        },
        setAssetCategory(id,name){
        	setAssetCategory(id,name)
        },
        parentAssetCategoryView(id,name){
        	parentAssetCategoryView(id,name)
        },
        setParentAssetCategoryView(id,name){
        	setParentAssetCategoryView(id,name)
        }


    };
}();