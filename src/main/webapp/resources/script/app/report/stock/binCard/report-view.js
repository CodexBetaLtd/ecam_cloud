var BinCardReportView = function () {
	
	var initButtons = function() {
	    $('#viewBinCardReport').on('click', function (event) {
	    	if ($("#frmReportBinCard").valid()) {				
	    		BinCardDetailTable.initDataTable(gcurrentQuantity);
			}
	    });
	};

    /*********************************************************************
     *  Item
     *********************************************************************/
	var runItemTypeSelect = function () {
		$("#itemTypeId").select2({
			placeholder: "Select a Item Type",
			allowClear: true
		});
	};
	
	var runPartInput = function () {
		$("#partCode").inputClear({
			placeholder: "Select a Item",
			btnMethod: "BinCardReportView.partView()",
		});
	};
	
	var runWearhouseInput = function () {
		$("#warehouseName").inputClear({
			placeholder: "Select a Warehouse",
			btnMethod: "BinCardReportView.warehouseView()",
		});
	};
	
    var runStockInput = function () {
        $("#stockNo").inputClear({
            placeholder: "Select a Stock",
            btnMethod: "BinCardReportView.stockView()",
        });
    };
    
    var partView = function () {
    	var $modal = $('#common-modal');
    	CustomComponents.ajaxModalLoadingProgressBar();
    	setTimeout(function () {
    		var url = '../bincard/partView';
    		var func = "BinCardReportView.setPart";
    		$modal.load(url, '', function () {
    			dtAsset.dtGetPartList();
    			//dtPart.getPartDataTable(null, null,func);
    			$modal.modal();
    			
    		});
    	}, 1000);
    	
    };
      
    var warehouseView = function () {
    	var $modal = $('#common-modal');
    	CustomComponents.ajaxModalLoadingProgressBar();
    	setTimeout(function () {
    		var url = '../bincard/warehouseView';
    		$modal.load(url, '', function () {
    			var func = "BinCardReportView.setWarehouse";
    			dtWarehouse.getWarehouseDataTable(null,'../../restapi/asset/getWarehouseDataTable' ,func);
    			$modal.modal();
    		});
    	}, 1000);
    };
    
    var stockView = function () {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../bincard/stockView';
            $modal.load(url, '', function () {
                var func = "BinCardReportView.setStock";
                dtStock.stockTable();
                $modal.modal();
            });
        }, 1000);
    };

    var setPart = function (id, name,currentQunatity, description, rackNo, positionNo, lineNo) {
    	$("#partId").val(id);
    	$("#partCode").val(name);
    	$("#partDescription").val(description);
        $("#partRackNo").val(rackNo);
        $("#partPosition").val(positionNo);
        $("#partLineNo").val(lineNo);
    	$("#currentQuantityData").val(currentQunatity);
    	gcurrentQuantity = currentQunatity;
      	$("#partCode").attr('readonly', true);
      	$("#partDescription").attr('readonly', true);
        $("#partRackNo").attr('readonly', true);
        $("#partPosition").attr('readonly', true);
        $("#partLineNo").attr('readonly', true);
        
    	$('#common-modal').modal("hide");
    	
    	BinCardDetailTable.initDataTable(gcurrentQuantity);
    };
    
    var setWarehouse = function (id, name) {
    	$("#warehouseId").val(id);
    	$("#warehouseName").val(name);
    	$("#warehouseName").attr('readonly', true);
    	$('#common-modal').modal("hide");
    };
    
    var setStock = function (id, code,qtyOnHand) {
        $("#stockId").val(id);
        $("#stockNo").val(code);       
        $("#currentQuantityData").val(qtyOnHand);
        gcurrentQuantity=qtyOnHand;
        $("#stockNo").attr('readonly', true);
        $('#common-modal').modal("hide");
    };
    
    /*********************************************************************
     * Validation
     *********************************************************************/
    var initValidator = function () {

        var form = $('#frmReportBinCard');
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
            	partCode:{
            		required: true
            	}
            },
            messages: {
            	partCode:"Please select a part"
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
        	initButtons();
            runPartInput();
            runWearhouseInput();
            runStockInput();
            initValidator();
        },
        partView:function(){
        	partView();
        },
        setPart:function(id, name,currentQunatity, description, rackNo, positionNo, lineNo){
        	setPart(id, name,currentQunatity, description, rackNo, positionNo, lineNo)
        },
        warehouseView:function(){
        	warehouseView();
        },
        setWarehouse:function(id,code){
        	setWarehouse(id,code)
        },
        stockView:function(){
        	stockView();
        },
        setStock :function(id,code,qtyOnHand){
        	setStock(id,code,qtyOnHand)
        }

    };
}();