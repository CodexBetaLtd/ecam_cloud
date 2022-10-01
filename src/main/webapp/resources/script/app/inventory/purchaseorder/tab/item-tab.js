var TabItem = function () {

    /***********************************************************************
     * populate Purchase Order Items
     **********************************************************************/
    var populatePOItems = function () {
        if (items.length > 0) {
            var row, item;

            $("#item-tbl > tbody").html("");

            for (row = 0; row < items.length; row++) {
                item = items[row];
            	console.log(items[row])

                var html = "<tr id='row_" + row + "' >" +
                    "<input id='items" + row + ".itemId' name='items[" + row + "].itemId' value='" + item.itemId + "' type='hidden'>" +
                    "<input id='items" + row + ".itemAssetId' name='items[" + row + "].itemAssetId' value='" + item.itemAssetId + "' type='hidden'>" +
                    "<input id='items" + row + ".itemAssetName' name='items[" + row + "].itemAssetName' value='" + item.itemAssetName + "' type='hidden'>" +
                    "<input id='items" + row + ".itemRfqCodes' name='items[" + row + "].itemRfqCodes' value='" + item.itemRfqCodes + "' type='hidden'>" +
                    "<input id='items" + row + ".itemRfqItemId' name='items[" + row + "].itemRfqItemId' value='" + item.itemRfqItemId + "' type='hidden'>" +
                    "<input id='items" + row + ".itemQtyOnOrder' name='items[" + row + "].itemQtyOnOrder' value='" + item.itemQtyOnOrder + "' type='hidden'>" +
                    "<input id='items" + row + ".itemUnitPrice' name='items[" + row + "].itemUnitPrice' value='" + item.itemUnitPrice + "' type='hidden'>" +
                    "<input id='items" + row + ".itemTotalPrice' name='items[" + row + "].itemTotalPrice' value='" + item.itemTotalPrice + "' type='hidden'>" +
                    "<input id='items" + row + ".itemTaxRate' name='items[" + row + "].itemTaxRate' value='" + item.itemTaxRate + "' type='hidden'>" +
//                    "<input id='items" + row + ".itemSupplierPartNo' name='items[" + row + "].itemSupplierPartNo' value='" + item.itemSupplierPartNo + "' type='hidden'>" +
//                    "<input id='items" + row + ".itemCatalogName' name='items[" + row + "].itemCatalogName' value='" + item.itemCatalogName + "' type='hidden'>" +
                    "<input id='items" + row + ".itemAccountId' name='items[" + row + "].itemAccountId' value='" + item.itemAccountId + "' type='hidden'>" +
                    "<input id='items" + row + ".itemChargeDepartmentId' name='items[" + row + "].itemChargeDepartmentId' value='" + item.itemChargeDepartmentId + "' type='hidden'>" +
                    "<input id='items" + row + ".itemSiteId' name='items[" + row + "].itemSiteId' value='" + item.itemSiteId + "' type='hidden'>" +
                    "<input id='items" + row + ".itemSiteName' name='items[" + row + "].itemSiteName' value='" + item.itemSiteName + "' type='hidden'>" +
                    "<input id='items" + row + ".itemSourceWorkOrderId' name='items[" + row + "].itemSourceWorkOrderId' value='" + item.itemSourceWorkOrderId + "' type='hidden'>" +
                    "<input id='items" + row + ".itemSourceWorkOrderCode' name='items[" + row + "].itemSourceWorkOrderCode' value='" + item.itemSourceWorkOrderCode + "' type='hidden'>" +
                    "<input id='items" + row + ".itemSourceAssetId' name='items[" + row + "].itemSourceAssetId' value='" + item.itemSourceAssetId + "' type='hidden'>" +
                    "<input id='items" + row + ".itemSourceAssetName' name='items[" + row + "].itemSourceAssetName' value='" + item.itemSourceAssetName + "' type='hidden'>" +
                    "<input id='items" + row + ".itemRequiredByDate' name='items[" + row + "].itemRequiredByDate' value='" + item.itemRequiredByDate + "' type='hidden'>" +
                    "<input id='items" + row + ".version' name='items[" + row + "].version' value='" + item.version + "' type='hidden' >" +
                    getPOTax(row)+
                    "<td>" +
                    "<div class='checkbox-center'>" +
                    "<input type='checkbox' name='selectedItems' value='" + item.itemId + "' class='grey'>" +
                    "</div>" +
                    "</td>" +

                    "<td><span>" + item.itemAssetName + "</span></td>" +
                    "<td><span>" + item.itemQtyOnOrder + "</span></td>" +
                    "<td >" + item.itemUnitPrice + "</td>" +
                    "<td><span>" + item.itemTaxRate + "</span></td>" +
                    "<td >" + item.itemTotalPrice + "</td>" +
                    "<td><span>" + item.itemRfqCodes + "</span></td>" +
                    "<td class='center'> " + ButtonUtil.getEditDeleteBtnFromList(row, "TabItem") + "</td>" +
                    // "<td class='center'>" +
                    //     "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
                    //     "<a onclick='TabItem.editItem(" + item.itemIndex + ");' class='btn btn-xs btn-teal tooltips' data-placement='top' data-original-title='Edit' >" +
                    // "<i class='fa fa-edit' ></i></a>" +
                    // "<a onclick='TabItem.removeItem(" + item.itemIndex + ");' class='btn btn-xs btn-bricky tooltips' data-placement='top' data-original-title='Remove' >" +
                    // "<i class='fa fa-times fa fa-white' ></i></a></div></td>"+


                    "</tr>";
                $('#item-tbl > tbody:last-child').append(html);
            }

            $('input[type="checkbox"].grey, input[type="radio"].grey').iCheck({
                checkboxClass: 'icheckbox_minimal-grey',
                radioClass: 'iradio_minimal-grey',
                increaseArea: '10%'
            });

        } else {
            CustomComponents.emptyTableRow("item-tbl", 8);
        }
        updateSummaryDetail();
    };

    var getPOTax=function(itemIndex){
    	var html='';
    		var poTaxes=items[itemIndex].poItemtax
    		var totalItemTax=0.0;
    		var totalItemCost=0.0;
    	for(var i=0;i<poTaxes.length;i++){
    		var tax=poTaxes[i];

		html=html+ "<input type=\"hidden\" name=\"items["+ itemIndex+ "].poTaxes["+i+"].id\" value='"+ tax.id+ "'>"
			+ "<input type=\"hidden\" name=\"items["+ itemIndex+ "].poTaxes["+i+"].version\" value='"+ tax.version+ "'>"
			+ "<input type=\"hidden\" name=\"items["+ itemIndex+ "].poTaxes["+i+"].valueId\" value='"+ tax.valueId+ "'>"
			
			if(tax.taxType=='FIX_VALUE'){
				totalItemTax+=parseFloat(tax.value);
			}else{
				totalItemTax+=parseFloat(items[itemIndex].itemTotalPrice*tax.value/100)
			}

    	}
		totalItemCost+=totalItemTax

		items[itemIndex].itemTotalPrice+=totalItemCost
		items[itemIndex].itemTaxRate=totalItemTax
    	return html;
    }

    var updateSummaryDetail = function () {
        var subTotal = 0;
        var total = 0;
        var totalRealTax = 0;
        for (var i = 0; i < items.length; i++) {
            subTotal = parseFloat(subTotal + items[i].itemTotalPrice)
//            if (!isOverridePoItemTax) {
//                totalRealTax = parseFloat(totalRealTax) + parseFloat(( items[i].itemTotalPrice * items[i].itemTaxRate ) / 100)
//            } else {
//                totalRealTax = parseFloat(totalTax);
//            }
        }
        
        for(var i=0;i<taxes.length;i++){
        	var tax=taxes[i]
			if(tax.taxType=='FIX_VALUE'){
				totalRealTax+=parseFloat(tax.value);
			}else{
				totalRealTax+=parseFloat(subTotal*tax.value/100)
			}     		
        }

        total = parseFloat(subTotal + additionalTotalCost + totalRealTax);
        $('#total').text(total.toFixed(2));
        $('#subTotal').text(subTotal.toFixed(2));
        $('#additionalCostTotal').text(parseFloat(additionalTotalCost).toFixed(2));
        $('#taxTotal').text(totalRealTax.toFixed(2));
    };


    /***********************************************************************
     * Purchase Order Item Add
     **********************************************************************/

    var poItemView = function () {
        if ($('#supplierBusiness').val() != null && $('#supplierBusiness').val() > 0) {
            var $modal = $('#common-modal');
            CustomComponents.ajaxModalLoadingProgressBar();
            setTimeout(function () {
                var url = '../purchaseorder/itemView';
                $modal.load(url, '', function () {
                	
                    ItemAddModal.init();
                    $modal.modal();
                });
            }, 1000);
        } else {
            alert("Please Set Supplier First in the Shipping Tab");
        }

    };

    var addItem = function () {
        var item = {};
        item['itemId'] = $('#itemId').val();
        item['itemIndex'] = $('#itemIndex').val();

        item['itemAssetId'] = $('#itemAssetId').val();
        item['itemAssetName'] = $('#itemAssetName').val();
        item['itemRfqCodes'] = $('#itemRfqCodes').val();
        item['itemRfqItemId'] = $('#itemRfqItemId').val();
        item['itemQtyOnOrder'] = $('#itemQtyOnOrder').val();
        item['itemUnitPrice'] = $('#itemUnitPrice').val();
        item['itemTaxRate'] = $('#itemTaxRate').val();
//        item['itemSupplierPartNo'] = $('#itemSupplierPartNo').val();
//        item['itemCatalogName'] = $('#itemCatalogName').val();
        item['itemAccountId'] = $('#itemAccountId').val();
        item['itemChargeDepartmentId'] = $('#itemChargeDepartmentId').val();
        item['itemSiteId'] = $('#itemSiteId').val();
        item['itemSiteName'] = $('#itemSiteName').val();
        item['itemSourceWorkOrderId'] = $('#itemSourceWorkOrderId').val();
        item['itemSourceWorkOrderCode'] = $('#itemSourceWorkOrderCode').val();
        item['itemSourceAssetId'] = $('#itemSourceAssetId').val();
        item['itemSourceAssetName'] = $('#itemSourceAssetName').val();
        item['itemRequiredByDate'] = $('#itemRequiredByDate').val();
        item['itemTotalPrice'] = item['itemUnitPrice'] * item['itemQtyOnOrder'];
        item['version'] = $('#itemVersion').val();
        item['poItemtax']=poItemtax;

        addItemToList(item);
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
    };

    var setCommonDataToItemObj = function (updatedItemObj, itemObj ){
        validateFieldNull(itemObj, 'itemId', updatedItemObj.itemId);
    	validateFieldNull( itemObj, 'itemAssetId', updatedItemObj.itemAssetId);
    	validateFieldNull( itemObj, 'itemAssetName', updatedItemObj.itemAssetName);
    	validateFieldNull( itemObj, 'itemRfqCodes', updatedItemObj.itemRfqCodes);
    	validateFieldNull( itemObj, 'itemRfqItemId', updatedItemObj.itemRfqItemId);
    	validateFieldNull( itemObj, 'itemQtyOnOrder', updatedItemObj.itemQtyOnOrder);
    	validateFieldNull( itemObj, 'itemUnitPrice', updatedItemObj.itemUnitPrice);
    	validateFieldNull( itemObj, 'itemTaxRate', updatedItemObj.itemTaxRate);
    	validateFieldNull( itemObj, 'itemAccountId', updatedItemObj.itemAccountId);
    	validateFieldNull( itemObj, 'itemChargeDepartmentId', updatedItemObj.itemChargeDepartmentId);
    	validateFieldNull( itemObj, 'itemSiteId', updatedItemObj.itemSiteId);
    	validateFieldNull( itemObj, 'itemSiteName', updatedItemObj.itemSiteName);
    	validateFieldNull( itemObj, 'itemSourceWorkOrderId', updatedItemObj.itemSourceWorkOrderId);
    	validateFieldNull( itemObj, 'itemSourceWorkOrderCode', updatedItemObj.itemSourceWorkOrderCode);
    	validateFieldNull( itemObj, 'itemSourceAssetId', updatedItemObj.itemSourceAssetId);
    	validateFieldNull( itemObj, 'itemSourceAssetName', updatedItemObj.itemSourceAssetName);
        validateFieldNull(itemObj, 'itemRequiredByDate', updatedItemObj.itemRequiredByDate);
        validateFieldNull(itemObj, 'itemTotalPrice', updatedItemObj['itemUnitPrice'] * updatedItemObj['itemQtyOnOrder']);
        validateFieldNull( itemObj, 'poItemtax', updatedItemObj.poItemtax);
        validateFieldNull( itemObj, 'version', updatedItemObj.version);
    	validateFieldNull( itemObj, 'poItemtax', updatedItemObj.poItemtax);
    };


    /***********************************************************************
     * Purchase Order Item Edit
     **********************************************************************/
    var editItem = function (itemIndex) {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../purchaseorder/itemView';
            $modal.load(url, '', function () {
            	fillItemEditForm(getItemByIndex(itemIndex));
            	ItemAddModal.init();
                $modal.modal();
            });
        }, 1000);
    };

    var getItemByIndex = function (itemIndex) {
        for (var i = 0; i < items.length; i++) {
            if (items[i].itemIndex == itemIndex) {
                return items[i];
            }
        }
    };

    var fillItemEditForm = function (item) {
    	$('#itemId').val(item['itemId']);
    	$('#itemIndex').val(item['itemIndex']);
    	
    	$('#itemAssetId').val(item['itemAssetId']);
    	$('#itemAssetName').val(item['itemAssetName']);
    	$('#itemRfqCodes').val(item['itemRfqCodes']);
    	$('#itemRfqItemId').val(item['itemRfqItemId']);
    	$('#itemQtyOnOrder').val(item['itemQtyOnOrder']);
    	$('#itemUnitPrice').val(item['itemUnitPrice']);
    	$('#itemTaxRate').val(item['itemTaxRate']);
//    	$('#itemSupplierPartNo').val(item['itemSupplierPartNo']);
//    	$('#itemCatalogName').val(item['itemCatalogName']);
    	$('#itemAccountId').val(item['itemAccountId']);
    	$('#itemChargeDepartmentId').val(item['itemChargeDepartmentId']);
    	$('#itemSiteId').val(item['itemSiteId']);
    	$('#itemSiteName').val(item['itemSiteName']);
    	$('#itemSourceWorkOrderId').val(item['itemSourceWorkOrderId']);
    	$('#itemSourceWorkOrderCode').val(item['itemSourceWorkOrderCode']);
    	$('#itemSourceAssetId').val(item['itemSourceAssetId']);
    	$('#itemSourceAssetName').val(item['itemSourceAssetName']);
    	$('#itemRequiredByDate').val(item['itemRequiredByDate']);
    	
    	$('#itemVersion').val(item['version']);
    };


    /***********************************************************************
     * Purchase Order Item Delete
     **********************************************************************/
	var removeItem = function(itemIndex) {
		for (var i = 0; i < items.length; i++) {
			if (items[i].itemIndex == itemIndex) {
				items.splice(i, 1);
				updateIndexes();
				break;
			}
		}
	};


    /***********************************************************************
     * Purchase Order Items Utility
     **********************************************************************/

    var validateFieldNull = function (obj, field, value) {
        if (value != null && value != undefined) {
            obj[field] = value;
        } else {
            obj[field] = "";
        }
    };
	
	var updateIndexes = function () {
        for (var i = 0; i < items.length; i++) {
        	items[i].itemIndex = i;
        }
    };


    /***********************************************************************
     * Generate RFQ by using Purchase Order Items
     **********************************************************************/
    var generateRFQ = function () {
    	var isSaved = true;    	
    	var token = $("input[name='_csrf']").val();    	
    	var checkedValues = $("input[name='selectedItems']:checkbox:checked").map(function() {
    		if(this.value == null || this.value == "") {
    			isSaved = false;
    		}
    	    return this.value;
    	}).get();
    	
    	if (isSaved) {
    		if (checkedValues != null && checkedValues != "") {  
    			$(location).attr('href', '../rfq/addfrompo?_csrf=' + token + '&poItemIds=' + checkedValues);   		    
        	} else {
        		alert("Please select atleast one item to create RFQ");
        	}
    	} else {
    		alert("There are some unsaved items. Please save them before create RFQ");
    	}
    	
    };


    return {

        /***********************************************************************
         * Item Ops
		 **********************************************************************/
        poItemView: function () {
            poItemView();
        },
        
        updateSummaryDetail:function(){
        	updateSummaryDetail();
        },
        /***********************************************************************
         * Purchase Order Item Add
         **********************************************************************/
        addPOItem: function () {
        	addItem();
        },

        addItemToList: function (item) {
        	addItemToList(item);
        },

        /***********************************************************************
         * Purchase Order Item Edit
         **********************************************************************/
        editListItem: function (index) {
        	editItem(index);
        },

        /***********************************************************************
         * Purchase Order Item Delete
         **********************************************************************/
        deleteListItem: function (index) {
            removeItem(index);
        },

        populatePOItems: function () {
            populatePOItems();
        },

        /**********************************************************
         * RFQ Order Generating
         *********************************************************/
        generateRFQ: function () {
        	generateRFQ();
        }
    };
}();