var ItemAddModal = function () {
	
	var initButton = function(){
        $('#btn-add-receipt-item').on('click', function () {
        	ItemAddModal.addReceiptOrderItem(this);
        });
	}
	

    /*********************************************************************
     * Receipt Item Asset Data
     *********************************************************************/
	
	var initPartSelect = function () {
        $("#itemAssetName").inputClear({
            placeholder: "Select A Part",
            btnMethod: "ItemAddModal.receiptAssetView()",
        });
    };
    
    var receiptAssetView = function () {
    	loadAssetSelectModal("asset_tbl", "../restapi/asset/parts", "ItemAddModal.setReceiptItemAsset");
    };
    
    
    var loadAssetSelectModal = function (tableId, URL, method) {
    	var $modal = $('#stackable-modal');
    	CustomComponents.ajaxModalLoadingProgressBar();
    	setTimeout(function () {
    		var url = '../receiptorder/receiptAssetView';
    		$modal.load(url, '', function () {
    			dtReceiptAsset.dtReceiptAsset(tableId, URL, method);
    			$modal.modal();
    		});
    	}, 1000);
    };
    
    var setReceiptItemAsset = function (id, name) {
        $('#itemAssetId').val(id);
        $('#itemAssetName').val(name);
        createLinkForStok(id);
    	$('#stackable-modal').modal('toggle');

    }
    
    
    var createLinkForStok=function(id){
    	$( "#createStock" ).empty();
       	var thelink = $('<a>',{
        	    text: 'Create New Stock',
        	    href: '../stock/createstock?partId='+id,
        	    target:'_blank'
        	}).appendTo('#createStock');
    }
    
    /*********************************************************************
     * Receipt Item Stock Data
     *********************************************************************/
	var initStockSelect = function () {
        $("#itemStockName").inputClear({
            placeholder: "Select A Stock",
            btnMethod: "ItemAddModal.receiptStockView()",
        });
    };

    var receiptStockView = function () {
    	partId=$("#itemAssetId").val();
    	if(partId!=null && partId!='' ){
            getReceiptStockView("stock_tbl", "../restapi/stock/stockByPart", "receiptItemTab.setReceiptItemStock", partId);
    	}else{
    		alert("Please select part first")
    	}
    };

    var getReceiptStockView = function (tableId, URL, method, partId) {
        var $modal = $('#stackable-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../receiptorder/receiptStockView';
            $modal.load(url, '', function () {
                dtReceiptStock.getStockDataTable(tableId, URL, method, partId);
                $modal.modal();
            });
        }, 1000);
    };

    var setReceiptItemStock = function (id, name) {
        $('#itemStockId').val(id);
        $('#itemStockName').val(name);
        $('#stackable-modal').modal('toggle');
    };
	var runValidator = function() {

        var form = $('#receiptOrderItemForm');
		var errorHandler = $('.errorHandler', form);
		var successHandler = $('.successHandler', form);
        jQuery.validator.addMethod("greaterThanZero", function (value, element) {   
          	return value>0
        }, "0 is not valid");
        $('#receiptOrderItemForm').validate(
		{
			errorElement : "span", // contain the error msg in a span tag
			errorClass : 'help-block',
			errorPlacement : function(error, element) { // render error placement for each input type
				if (element.attr("type") == "radio" || element.attr("type") == "checkbox") { // for chosen elements, need to insert the error after the chosen container
					error.insertAfter($(element).closest('.form-group').children('div').children().last());
				} else if (element.attr("name") == "dd" || element.attr("name") == "mm" || element.attr("name") == "yyyy") {
					error.insertAfter($(element).closest('.form-group').children('div'));
				} else {
					error.insertAfter(element);
					// for other inputs, just perform default behavior
				}
			},
			ignore : "",
			rules : {
				itemAssetName : {
					required : true
				},
				itemStockName : {
					required : true
				},
				itemQtyReceived :{
					required : true,
					number: true,
					greaterThanZero:[]
				},
				itemUnitPrice :{
					required : true,
					number: true,
					greaterThanZero:[]
				}
			},
			messages : {
				itemAssetName : "Please Select a Part",
				itemStockName : "Please Select a Stock",
				itemQtyReceived :{ 
					required:"Please Specify Quantity Received",
					number:"Please Enter numeric value only",
					greaterThanZero:"Received Quantity can not be 0"
					},
				itemUnitPrice : {
					required:"Please Specify Unit Price",
					number:"Please Enter numeric value only",
					greaterThanZero:"Unit Price can not be 0"
				}
			},
			invalidHandler : function(event, validator) { //display error alert on form submit
				successHandler.hide();
				errorHandler.show();
			},
			highlight : function(element) {
				$(element).closest('.help-block').removeClass('valid');
				// display OK icon
				$(element).closest('.form-group').removeClass('has-success').addClass('has-error').find('.symbol').removeClass('ok').addClass('required');
				// add the Bootstrap error class to the control group
			},
			unhighlight : function(element) { // revert the change done by hightlight
				$(element).closest('.form-group').removeClass('has-error');
				// set error class to the control group
			},
			success : function(label, element) {
				label.addClass('help-block valid');
				// mark the current input as valid and display OK icon
				$(element).closest('.form-group').removeClass('has-error').addClass('has-success').find('.symbol').removeClass('required').addClass('ok');
			},

			submitHandler : function(form) {
				successHandler.show();
				errorHandler.hide();
				// submit form
				return true;
			}
		});
	};
	
	var addReceiptOrderItem = function(obj) {
		var form = $('#receiptOrderItemForm');
		if (form.valid()) {
			receiptItemTab.addItemToList(obj);
		}
	}


    return {

        init: function () {
        	initButton();
        	runValidator();
        	initPartSelect();
        	initStockSelect();
        },
        
        receiptAssetView:function(){
        	receiptAssetView();
        },
        
        setReceiptItemAsset:function(id,name){
        	setReceiptItemAsset(id,name);
        },
        
        receiptStockView:function(){
        	receiptStockView()
        },
        
        setReceiptItemStock:function(id,name){
        	setReceiptItemStock(id,name)
        },
        
        addReceiptOrderItem:function(obj){
        	addReceiptOrderItem(obj);
        },

    };
}();