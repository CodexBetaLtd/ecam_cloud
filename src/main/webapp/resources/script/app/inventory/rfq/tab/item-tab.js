var TabItem = function () {

	var initButtons = function () {
		$('#btn-add-rfq-item').on('click', function () {
			TabItem.rfqItemView();
		});	
		$('#btn-new-po').on('click', function () {
			  TabItem.generatePo();
		});	

	}; 
	
    var runCheckboxes = function () {
        $('input[type="checkbox"].grey, input[type="radio"].grey').iCheck({
            checkboxClass: 'icheckbox_minimal-grey',
            radioClass: 'iradio_minimal-grey',
            increaseArea: '10%'
        });
    };
	
    var getSelectedItems=function(){
    	var token = $("input[name='_csrf']").val();    	

    	var checkedValues = $("input[name='selectedItems']:checkbox:checked").map(function() {
    		if(this.value == null || this.value == "") {
    			isSaved = false;
    		}
    	    return this.value;
    	}).get();
    	
    	var checkedSupplires = $("input[name='selectedSuppliresItems']:checkbox:checked").map(function() {
    		if(this.value == null || this.value == "") {
    			isSaved = false;
    		}
    	    return this.value;
    	}).get();
    	if(checkedSupplires != null && checkedSupplires != ""){
    		$.ajax({
    			type: "GET",
    			url: '../purchaseorder/addfromrfq?_csrf=' + token + '&rfqItemIds=' + checkedValues+'&supplierIds='+checkedSupplires,
    			contentType: "application/json",
    			dataType: "json",
    			success: function (result) {
   
    				$("#common-modal").modal('toggle');
    			    setTimeout(function () {
    			        location.reload()
    			    }, 0);
     				if (result.status == "SUCCESS") {
    					$("#message-div").html(CustomComponents.getSuccessMsgDivWithUrl(result.msgList[0],"",""));
    				} else {
    					$("#message-div").html(CustomComponents.getErrorMsgDiv(result.errorList[0]));
    				}

    			},
    			error: function (xhr, ajaxOptions, thrownError) {
    				alert(xhr.status + " " + thrownError);
    			},
    			error: function (e) {
    				// alert("Failed to Create AOD.");
    				console.log(e);
    			}
    			
    		});
			//location.reload(); 

     	}else{
    		alert("Please select at least on supplier");
    	}
    	

    	
    }
    var populateRFQItems = function () {
        if (items.length > 0) {
        	console.log(items)
            var row, item;
            $("#item-tbl > tbody").html("");
            for (row = 0; row < items.length; row++) {
                item = items[row];
                var html = "<tr id='row_" + row + "' >" +
                    "<input id='items" + row + ".itemId' name='items[" + row + "].itemId' value='" + item.itemId + "' type='hidden'>" +
                    "<input id='items" + row + ".itemAssetId' name='items[" + row + "].itemAssetId' value='" + item.itemAssetId + "' type='hidden'>" +
                    "<input id='items" + row + ".itemAssetName' name='items[" + row + "].itemAssetName' value='" + item.itemAssetName + "' type='hidden'>" +
                    "<input id='items" + row + ".itemPurchaseOrderItemId' name='items[" + row + "].itemPurchaseOrderItemId' value='" + item.itemPurchaseOrderItemId + "' type='hidden'>" +
                    "<input id='items" + row + ".itemPurchaseOrderCodes' name='items[" + row + "].itemPurchaseOrderCodes' value='" + item.itemPurchaseOrderCodes + "' type='hidden'>" +
                    "<input id='items" + row + ".itemQtyRequested' name='items[" + row + "].itemQtyRequested' value='" + item.itemQtyRequested + "' type='hidden'>" +
                    "<input id='items" + row + ".itemDescription' name='items[" + row + "].itemDescription' value='" + item.itemDescription + "' type='hidden'>" +
                    "<input id='items" + row + ".itemQuotedTotalPrice' name='items[" + row + "].itemQuotedTotalPrice' value='" + item.itemQuotedTotalPrice + "' type='hidden'>" +
                    "<input id='items" + row + ".version' name='items[" + row + "].version' value='" + item.version + "' type='hidden' >" +

                    "<td>" +
                    "<div class='checkbox-center'>" +
                    "<input type='checkbox' name='selectedItems' value='" + item.itemId + "' class='grey'>" +
                    "</div>" +
                    "</td>" +

                    "<td><span>" + item.itemAssetName + "</span></td>" +
                    "<td >" + item.itemQtyRequested + "</span></td>" +
                    "<td><span>" + item.itemQuotedTotalPrice + "</td>" +
                    "<td>" + setpoList(item.purchaseOrderDTOs) + "</span></td>" +
                    "<td class='center'> " + ButtonUtil.getEditDeleteBtnFromList(row, "TabItem") + "</td>" +
                    "</tr>";
                $('#item-tbl > tbody:last-child').append(html);
            }
        } else {
            CustomComponents.emptyTableRow("item-tbl", 8);
        }
        RFQAdd.initCheckBoxes();
    };
    
    var setpoList=function(poList){
    	var html="";
    	 for (var row = 0; row < poList.length; row++) {
    		 html=html+"<a href='../purchaseorder/edit?id="+poList[row].id+"' target='_blank'>"+poList[row].code+"</a> "
    		 if((row+1)!=poList.length){
    			 html=html	 +"," ;
 
    		 }
    	 }
    	 
    	 return html;
    }

    var rfqItemView = function () {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../rfq/item-add-modal-view';
            $modal.load(url, '', function () {
            	ItemAddModal.init();
                $modal.modal();
            });
        }, 1000);
      };
      
      
      var editListItem = function (itemIndex) {
          var $modal = $('#master-modal-datatable');
          CustomComponents.ajaxModalLoadingProgressBar();
          setTimeout(function () {
              var url = '../rfq/item-add-modal-view';
              $modal.load(url, '', function () {
              	ItemAddModal.init();
              	fillItemEditForm(getItemByIndex(itemIndex));
                  $modal.modal();
              });
          }, 1000);
      };

    var addItemToList = function (item) {

    	var itemObj = {}
    	if (item.itemIndex != null && item.itemIndex != "" && item.itemIndex >= 0) { 
    		itemObj = getItemByIndex(item.itemIndex);            
    		setCommonDataToItemObj(item, itemObj);  

    	} else {   

    		if (items.length == 0) {
    			itemObj['itemIndex'] = 0; 
        	} else {
        		validateFieldNull( itemObj, 'itemIndex', items.length); 
        	}
        	setCommonDataToItemObj(item, itemObj);
        	items.push(itemObj);
    	}
    	populateRFQItems();

    };
    
    var setCommonDataToItemObj = function (updatedItemObj, itemObj ){
    	validateFieldNull( itemObj, 'itemId', updatedItemObj.itemId);    	
    	validateFieldNull( itemObj, 'itemAssetId', updatedItemObj.itemAssetId);
    	validateFieldNull( itemObj, 'itemAssetName', updatedItemObj.itemAssetName);
    	validateFieldNull( itemObj, 'itemPurchaseOrderItemId', updatedItemObj.itemPurchaseOrderItemId);
    	validateFieldNull( itemObj, 'itemPurchaseOrderCodes', updatedItemObj.itemPurchaseOrderCodes);
    	validateFieldNull( itemObj, 'itemQtyRequested', updatedItemObj.itemQtyRequested);
    	validateFieldNull( itemObj, 'itemQuotedQty', updatedItemObj.itemQuotedQty);
    	validateFieldNull( itemObj, 'itemQuotedUnitPrice', updatedItemObj.itemQuotedUnitPrice);
    	validateFieldNull( itemObj, 'itemQuotedTotalPrice', updatedItemObj.itemQuotedTotalPrice);
    	validateFieldNull( itemObj, 'itemDescription', updatedItemObj.itemDescription);
    	
    	validateFieldNull( itemObj, 'version', updatedItemObj.version);
    	validateFieldNull( itemObj, 'purchaseOrderDTOs', updatedItemObj.purchaseOrderDTOs);
    };
    
    var getItemByIndex = function (itemIndex) {
    	for (var i = 0; i < items.length; i++) {
			if (items[i].itemIndex == itemIndex) {
				return items[i];
			}
		}
    };
    
    var getItemById = function (itemId) {
    	for (var i = 0; i < items.length; i++) {
			if (items[i].itemId == itemId) {
				return items[i];
			}
		}
    };

    var addRFQItem = function () {

        var item = {};
        
        item['itemId'] = $('#itemId').val();
        item['itemIndex'] = $('#itemIndex').val();        
        item['itemAssetId'] = $('#itemAssetId').val();
        item['itemAssetName'] = $('#rfqItemAssetName').val();
        item['itemPurchaseOrderItemId'] = $('#itemPurchaseOrderItemId').val();
        item['itemPurchaseOrderCodes'] = $('#itemPurchaseOrderCodes').val();
        item['itemQtyRequested'] = $('#itemQtyRequested').val();

        if ($('#itemQuotedQty').val() != null && $('#itemQuotedQty').val() > 0) {
        	item['itemQuotedQty'] = $('#itemQuotedQty').val();
        } else {
        	item['itemQuotedQty'] = 0;
        }
        
        if ($('#itemQuotedUnitPrice').val() != null && $('#itemQuotedUnitPrice').val() > 0) {
        	item['itemQuotedUnitPrice'] = $('#itemQuotedUnitPrice').val();
        } else {
        	item['itemQuotedUnitPrice'] = 0;
        }

        item['itemQuotedTotalPrice'] = item['itemQuotedUnitPrice'] * item['itemQuotedQty'];
        item['itemDescription'] = $('#itemDescription').val(); 
        item['version'] = $('#itemVersion').val();

    	addItemToList(item);

    };


    
    var fillItemEditForm = function (item) {
    	$('#itemId').val(item['itemId']);
    	$('#itemIndex').val(item['itemIndex']);

    	$('#itemAssetId').val(item['itemAssetId']);
        $('#rfqItemAssetName').val(item['itemAssetName']);
    	$('#itemPurchaseOrderItemId').val(item['itemPurchaseOrderItemId']);
    	$('#itemPurchaseOrderCodes').val(item['itemPurchaseOrderCodes']);
    	$('#itemQtyRequested').val(item['itemQtyRequested']);
    	$('#itemQuotedQty').val(item['itemQuotedQty']);
    	$('#itemQuotedUnitPrice').val(item['itemQuotedUnitPrice']);
    	$('#itemQuotedTotalPrice').val(item['itemQuotedTotalPrice']);
    	$('#itemDescription').val(item['itemDescription']);
    	
    	$('#itemVersion').val(item['version']);
    };
    
    var validateFieldNull = function (obj, field, value) {
        if (value != null && value != undefined) {
            obj[field] = value;
        } else {
        	obj[field] = "";
        }
    };

	var removeItem = function(itemIndex) {
		for (var i = 0; i < items.length; i++) {
			if (items[i].itemIndex == itemIndex) {
				items.splice(i, 1);
				updateIndexes();
                populateRFQItems();
				break;
			}
		}
	};
	
	var updateIndexes = function () {
        for (var i = 0; i < items.length; i++) {
        	items[i].itemIndex = i;
        }
    };
    var  loadsupplies=function(){
		var $modal = $('#common-modal');
		CustomComponents.ajaxModalLoadingProgressBar();
		setTimeout(function() {
			var url = '../rfq/supplier-fetch-modal-view';
			$modal.load(url, '', function() {
				getAssinedSuppliers();
				$modal.modal();
			});
		}, 1000);
    }
    
    var getAssinedSuppliers=function(){
		if (supplires.length > 0) {
			var row, supplier;
			$("#supplier_fetch_tbl > tbody").html("");
			for (row = 0; row < supplires.length; row++) {
				 supplires[row].itemIndex=row;
				supplier = supplires[row];
				
				var html = "<tr id='row_" + row + "' >"+
							"<td>" +                    
				"<div class='checkbox-center'>" +
                "<input type='checkbox' name='selectedSuppliresItems' value='" + CustomValidation.nullValueReplace(supplier.supplierId)+ "' class='grey'>" +
                "</div>"+
                "</td>" +
				"<td><span>" + supplier.supplierName + "</span></td>"+
				"<td >"+ supplier.supplierAddress + "</span></td>"+
				"<td><span>" + supplier.supplierCity + "</td>"+
				"<td>" + supplier.supplierProvince + "</span></td>"+
				"<td>" + supplier.supplierPostalCode + "</span></td>"+
				"<td>" + supplier.supplierCountry + "</span></td>"+
				"</tr>";
				$('#supplier_fetch_tbl > tbody:last-child').append(html);
			}
		} else {
			CustomComponents.emptyTableRow("supplier_fetch_tbl", 7);
		}
        RFQAdd.initCheckBoxes();
		$('#btn-add-fetch-supplires').on('click', function () {    
			getSelectedItems()
		});	


    }
    var generatePo = function () {
    	var isSaved = true;    	
    	var token = $("input[name='_csrf']").val();    	
    	var checkedValues = $("input[name='selectedItems']:checkbox:checked").map(function() {
    		if(this.value == null || this.value == "") {
    			isSaved = false;
    		}
    	    return this.value;
    	}).get();
    	
    	var checkedSupplires = $("input[name='selectedSuppliresItems']:checkbox:checked").map(function() {
    		if(this.value == null || this.value == "") {
    			isSaved = false;
    		}
    	    return this.value;
    	}).get();
    		
    	if (isSaved) {
    		if (checkedValues != null && checkedValues != "") {  
    			if (isSelecetedItemsNotInPO(checkedValues)) {
    				loadsupplies()
    			} else {
    				alert("Please Select unassigned item to generate PO.");
    			}   		    
        	} else {
        		alert("Please select atleast one item to create PO");
        	}
        }
        else {
            if (confirm('Do you want save this RFQ and generate PO ?')) {
                submitAndGeneratePO();
            } else {
                alert("There are some unsaved items. Please save them before create PO");
            }
    	}
    };

    var submitAndGeneratePO = function () {
        $('#rfq_add_frm').attr('action', "/ECAM/rfq/saveWithPurchaseOrder").submit();
    };

    var isSelecetedItemsNotInPO = function (checkedValues) {
    	var ids = checkedValues.toString().split(",");    	
    	for (var i = 0; i < ids.length; i++) {
    		var item = getItemById(ids[i]);
    		if ( item.itemPurchaseOrderItemId != null && item.itemPurchaseOrderItemId > 0) {
    			return false;
    		}
        }
    	return true;
    };



    return {
    init:function(){
    	populateRFQItems();
    	initButtons();
    },

        addRFQItem: function () {
            addRFQItem();
        },

        addItemToList: function (item) {
        	addItemToList(item);
        },

        removeItem: function (index) {
            removeItem(index);
        },
        deleteListItem: function (index) {
            removeItem(index);
        },


        editListItem: function (index) {
            editListItem(index);
        },


        populateRFQItems: function () {
            populateRFQItems();
        },

        /**********************************************************
         * Initialize Modals
         *********************************************************/
        rfqItemView: function () {
            rfqItemView();
        },
        
        /**********************************************************
         * Purchase Order Generating
         *********************************************************/
        generatePo: function () {
        	generatePo();
        }
    };
}();