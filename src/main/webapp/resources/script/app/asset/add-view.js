var AssetAdd = function () {
	
    var runImageInput = function () { 
        var src = "../../restapi/asset/asset-image?id=" + $("#id").val();
        CustomComponents.fileInput("assetImage", true, true, src, "Asset Image" );        
    };
    
    var initPrintButton=function(){
        var src = "../../restapi/asset/asset-qr?id=" + $("#id").val();

	    $('#btn-print-qr').on('click', function () {
	    	AssetAdd.qrPrint(src);
	    });
    }
    
    var generateQRCode=function(){
        var src = "../../restapi/asset/asset-qr?id=" + $("#id").val();
        $('#qrcode').append(
        		"<img class='qr-img' src='"+src+"'>") 
        if($("#qrimage").val()!==''){
        	$('#qrcode').append(
        			"<a href='../../asset/download-qr?id="+$('#id').val()+"' class='btn btn-xs btn-teal tooltips qr-btn' data-placement='top' data-original-title='Download'>" +
        			"<i class='fa fa-qrcode'></i>" +
        			" Download QR" +
        			"</a>"+
        			"<button id='btn-print-qr' type='button' class='btn btn-xs btn-teal tooltips qr-print-btn' data-placement='top' data-original-title='Download'>" +
        					"<i class='fa fa-print'></i>" +
        					" Print QR" +
        					"</button>"
            )
            initPrintButton();
        }else{
        	$('#qrcode').append("<span class='qr-not'>QR Not Found</span>")
        }
    }
    
    function qrPrint(source) {  
        printJS({
          printable: source,
          type: 'image',
          imageStyle: 'width:75px'
        });
    };
    
    var initMap=function(){
        var var_location = new google.maps.LatLng(locationDetails['latitude'],locationDetails['longitude']);
        var var_mapoptions = {
            center: var_location,
            zoom: 10
        };
        var var_map = new google.maps.Map(document.getElementById("map-container"),var_mapoptions);
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
     
	var initParentAssetSelect = function () {
        $("#parentAssetName").inputClear({
            placeholder: "Select A Parent Asset",
            btnMethod: "AssetAdd.selectParentAssetModal()",
        });
    };
	
	var initCustomerSelect = function () {
        $("#customerName").inputClear({
            placeholder: "Select A Owner",
            btnMethod: "AssetAdd.selectCustomerModal()",
        });
    };
	
	var initAssetCategorySelect = function(){
    	$("#assetCategoryName").inputClear({
    		placeholder:"Select a Asset Category",
        	btnMethod:"AssetAdd.assetCategoryView()",
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
    
    var runMeterReadingUnitSelect = function () {
    	$("#meterReaingUnitId").select2({
    		placeholder: "Select a Meter Reading Unit",
    		allowClear: true,
    		dropdownParent: $("#addMeterReadingModal")
    	});
    };
    
    var runOnlineSwitch = function () {
        var state = false;
        if ($('#isOnline').val() == 'true') {
            state = true;
        }
        $('#isOnline').bootstrapSwitch({
            onText: "Online",
            offText: "Offline",
            state: state
        }).on('switchChange.bootstrapSwitch', function (event, state) {
            if ($('#isOnline').val() == 'true') {
                $('#isOnline').val('false');
            } else {
                $('#isOnline').val('true');
            }
        });
    };
    
    var initValidator = function () {
        var form = $('#asset_add_frm');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        $('#asset_add_frm').validate({
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
            	name: {
                    required: true
                },
                code: {
                    minlength: 2,
                    required: true,
                    remote: {
                        url: "../../validate/asset/assetCode",
                        type: "GET",
                        data: {
                            id: $('#id').val()
                        }
                    }
                },
                assetCategoryName: {
                    required: true
                },
                businessId: {
                    required: true
                }, 
                siteId: {
                    required: true
                }
            },
            messages: {
            	name : "Please Specify a Name",
                code: {
                    required: "Please Specify a Code.",
                    remote: jQuery.validator.format("Code {0} is already taken.")
                },
                assetCategoryName: "Please Select a Category",
                businessId: "Please Select a Business", 
                siteId: "Please Select a Site"
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
    
    var selectParentAssetModal = function () {
    	var $modal = $('#common-modal');
    	if ($('#businessId').val() != null & $('#businessId').val() != "") {
            CustomComponents.ajaxModalLoadingProgressBar();
            setTimeout(function () {
                var url = '../../asset/parentassetselectview';
                $modal.load(url, '', function () {
                	ParentAssetSelectModal.init();
                    $modal.modal();
                });
            }, 1000);
		} else {
			alert("Please select a Business first!");
		}

    };
    
    var selectCustomerModal = function () {
    	var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../../asset/customerselectview';
            $modal.load(url, '', function () {
            	CustomerSelectModal.init();
                $modal.modal();
            });
        }, 1000);
    };
    
    var setCustomer = function (customerId, customerName) {
    	$('#customerId').val(customerId);
    	$('#customerName').val(customerName);
    	$('#common-modal').modal('toggle');
    };
    
    var clearCustomer = function () {
    	$('#customerId').val("");
    	$('#customerName').val("");
    };
    
    var setParentAsset = function (parentAssetId, parentAssetName) {
    	$('#parentAssetId').val(parentAssetId);
    	$('#parentAssetName').val(parentAssetName);
    	$('#common-modal').modal('toggle');
    };

    var runAssetBusinessSelect = function () {

        $("#businessId").change(function () {
            var businessId = $("#businessId option:selected").val();
            setSites(businessId);
            setParentAssets(businessId);
        });
    };
    
    var setSites = function (businessId) {
    	$.ajax({
            type: "GET",
            url: "../../asset/getsites?id=" + businessId,
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
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert(xhr.status + " " + thrownError);
            },
            error: function (e) {
                alert("Failed to load site");
            }
        });
    };
    
    var setParentAssets = function (businessId) {
    	$.ajax({
            type: "GET",
            url: "../../restapi/asset/getParentAssetsByBusiness?businessId=" + businessId + "&type=" + $("#type").val(),
            contentType: "application/json",
            dataType: "json",
            success: function (output) {
                $("#parentAssetId").find("option").remove();
                $.each(output, function (key, asset) {
                    $('#parentAssetId').append($('<option>', {
                        value: asset.id
                    }).text(asset.name));
                });
                initParentAssetSelect();
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert(xhr.status + " " + thrownError);
            },
            error: function (e) {
                alert("Failed to load Assets");
            }
        });
    };
    
    var setAssetCategory  = function(id,name){
    	$('#assetCategoryId').val(id);
    	$('#assetCategoryName').val(name);
    	$('#master-modal-datatable').modal('toggle');
    };
    
    /*********************************************************************
     * Modals
     *********************************************************************/

    var assetCategoryView = function () {
    	var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
    	setTimeout(function () {
    		var url = '../../asset/assetcategoryview';
    		$modal.load(url, '', function () {
    			AssetCategoryDataTable.runDataTable();
    			$modal.modal();
    		});
    	}, 1000);
    };
    
    var assetCategoryAddView = function () {
    	var $modal = $('#category-add-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
    	setTimeout(function () {
    		var url = '../asset/../assetcategoryadd?type=' + categoryType;
    		$modal.load(url, '', function (){
    			AssetCategoryAddModal.init();
    			AssetCategoryDataTable.runDataTable();
    			$modal.modal();
    		});
    	}, 1000);
    };
    
    function initDocumentOnLoad(){
        var id = $("#id").val();
        if (id == null || id == "") {
            var siteId = $("#siteId option:selected").val();
            if ( siteId == null || siteId == "" ) {
                var businessId = $("#businessId option:selected").val();
                setSites(businessId);
            }
        }
    };
    
    return {

        init: function () {  
            runCountrySelect();
            runOnlineSwitch();
            runBusinessSelect();
            runAssetBusinessSelect();
            runSiteSelect();
            runMeterReadingUnitSelect();
            initAssetCategorySelect();
            initBrandSelect();
            initModelSelect();
            initCustomerSelect();
            initParentAssetSelect();
            runImageInput();
            initValidator();
            generateQRCode();
            initDocumentOnLoad();
          //  initMap();
        },
    
        assetCategoryView: function(){
        	assetCategoryView();
        },
        
	    setAssetCategory: function(id, name){
	    	setAssetCategory(id, name);
	    },
	    
	    selectCustomerModal : function () {
        	selectCustomerModal();
        },
	    
	    assetCategoryAddView: function(){
	    	assetCategoryAddView();
	    },
	    
	    setCustomer : function (customerId, customerName) {
	    	setCustomer(customerId, customerName);
	    }, 
	    
	    setParentAsset : function(parentAssetId, parentAssetName) {
	    	setParentAsset(parentAssetId, parentAssetName);
	    },
	    
	    selectParentAssetModal: function () {
	    	selectParentAssetModal();
	    },
	    
	    clearCustomer : function () {
	    	clearCustomer();
	    },
	    
	    qrPrint : function (s) {
	    	qrPrint(s);
	    }

    };
}();