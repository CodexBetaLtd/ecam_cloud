var receiptItemTab = function () {
	
	var initButton = function(){
		$('#btn-new-recieptorder-item').on('click', function () {
			receiptItemTab.receiptItemView();
		});
        $('#btn-refurbish-recieptorder-item').on('click', function () {
        	receiptItemTab.receiptRefurbishItemView();
        });
	}
	
	
    var runReceiptOrderTypeChange = function () {
        $("#receiptOrderTypeId").change(function () {
            var aodType = $("#receiptOrderTypeId option:selected").val();
            if (aodType == "REFURBISH") {
                $("#btn-new-recieptorder-item").hide();
                $("#btn-refurbish-recieptorder-item").show();
            } else {
                $("#btn-new-recieptorder-item").show();
                $("#btn-refurbish-recieptorder-item").hide();
            }
        }).trigger('change');

    };

    /*********************************************************************
     * populate Receipt
     *********************************************************************/
    var populateReceiptItem = function () {
        if (items.length > 0) {
            var row, item;
            $("#item-tbl > tbody").html("");
            for (row = 0; row < items.length; row++) {
                item = items[row];
                var html = "<tr id='row_" + row + "' >" +
                    "<input id='items" + row + ".itemId' name='items[" + row + "].itemId' value='" + item.itemId + "' type='hidden'>" +
                    "<input id='items" + row + ".itemAssetId' name='items[" + row + "].itemAssetId' value='" + item.itemAssetId + "' type='hidden'>" +
                    "<input id='items" + row + ".itemAssetName' name='items[" + row + "].itemAssetName' value='" + item.itemAssetName + "' type='hidden'>" +
                    "<input id='items" + row + ".itemStockId' name='items[" + row + "].itemStockId' value='" + item.itemStockId + "' type='hidden'>" +
                    "<input id='items" + row + ".itemStockName' name='items[" + row + "].itemStockName' value='" + item.itemStockName + "' type='hidden'>" +
                    "<input id='items" + row + ".itemQtyReceived' name='items[" + row + "].itemQtyReceived' value='" + item.itemQtyReceived + "' type='hidden'>" +
                    "<input id='items" + row + ".itemDescription' name='items[" + row + "].itemDescription' value='" + item.itemDescription + "' type='hidden'>" +
                    "<input id='items" + row + ".itemUnitPrice' name='items[" + row + "].itemUnitPrice' value='" + item.itemUnitPrice + "' type='hidden'>" +
                    "<input id='items" + row + ".version' name='items[" + row + "].version' value='" + item.version + "' type='hidden' >" +
                    "<input id='items" + row + ".issueNoteitemId' name='items[" + row + "].issueNoteitemId' value=" + CustomComponents.nullValueReplace(item.issueNoteitemId) + " type='hidden' >" +
                    "<input id='items" + row + ".poItemId' name='items[" + row + "].poItemId' value=" +CustomComponents.nullValueReplace( item.poItemId) + " type='hidden' >" +
                    "<input id='items" + row + ".poItemName' name='items[" + row + "].poItemName' value='" + CustomComponents.nullValueReplace(item.poItemName) + "' type='hidden' >" +
                    "<td><span>" + ( row + 1 ) + "</span></td>" +
                    "<td><span>" + item.itemAssetName + "</span></td>" +
                    "<td>" + item.itemQtyReceived + "</span></td>" +
                    "<td><span>" + item.itemStockName + "</td>" +
                    "<td>" + item.itemUnitPrice + "</span></td>" +
                    "<td class='center'> " + ButtonUtil.getEditDeleteBtnFromList(row, "receiptItemTab") + "</td>" +
                    "</tr>";
                $('#item-tbl > tbody:last-child').append(html);
            }
        } else {
            CustomComponents.emptyTableRow("item-tbl", 7);
        }
    };

    var receiptItemView = function () {
    	if ($('#supplierId').val() != null && $('#supplierId').val() > 0) {
    		var $modal = $('#common-modal');
    		CustomComponents.ajaxModalLoadingProgressBar();
    		setTimeout(function () {
    			var url = '../receiptorder/receiptItemView';
    			$modal.load(url, '', function () {
    				$modal.modal();
    				ItemAddModal.init();
    			});
    		}, 1000);
    	} else {
    		alert("Please Set Supplier First");
    	}
    };
    
    var receiptRefurbishItemView = function () {
             var $modal = $('#common-modal');
            CustomComponents.ajaxModalLoadingProgressBar();
            setTimeout(function () {
                var url = '../receiptorder/receiptRefurbishItemView';
                $modal.load(url, '', function () {
                    $modal.modal();
                    RefurbishItemAddModal.init();
                });
            }, 1000);

    };


    /*********************************************************************
     * Add Receipt
     *********************************************************************/
    var addReceiptItem = function () {
    	
        var item = {};
        item['itemId'] = $('#itemId').val();
        item['itemIndex'] = $('#itemIndex').val();
        item['issueNoteitemId'] = $('#issueNoteitemId').val();
        item['itemAssetId'] = $('#itemAssetId').val();
        item['itemAssetName'] = $('#itemAssetName').val();
        item['itemStockId'] = $('#itemStockId').val();
        item['itemStockName'] = $('#itemStockName').val();
        item['itemQtyReceived'] = $('#itemQtyReceived').val();
        item['itemUnitPrice'] = $('#itemUnitPrice').val();
        item['itemDescription'] = $('#itemDescription').val();
        item['poItemId'] = $('#poItemId').val();
        item['poItemName'] = $('#poItemName').val();
        item['version'] = $('#itemVersion').val();
        items.push(item)
    };

    var addItemToList = function (obj) {
        if (($(obj).closest(".modal").find("#itemIndex")).val() != null && ($(obj).closest(".modal").find("#itemIndex")).val() != "") {
        	updateItem(items[$(obj).closest(".modal").find("#itemIndex").val()], obj);
        } else {
        	addReceiptItem();        
        }
        populateReceiptItem();
        $('#common-modal').modal('hide');
    };
    
    

    var updateItem = function (item, obj) {
    	item['itemId'] = CustomComponents.nullValueReplace($("#itemId").val());
    	item['itemAssetId'] = CustomComponents.nullValueReplace($("#itemAssetId").val());
    	item['itemAssetName'] = $("#itemAssetName").val();
    	item['itemStockId'] = $("#itemStockId").val();
    	item['issueNoteitemId'] = $("#issueNoteitemId").val();
    	item['itemStockName'] = CustomComponents.nullValueReplace($("#itemStockName").val());
    	item['itemQtyReceived'] = CustomComponents.nullValueReplace($("#itemQtyReceived").val());
    	item['itemUnitPrice'] = CustomComponents.nullValueReplace($("#itemUnitPrice").val());
    	item['itemDescription'] = CustomComponents.nullValueReplace($("#itemDescription").val());
    	item['description'] = CustomComponents.nullValueReplace($("#description").val());
    	item['poItemId'] = CustomComponents.nullValueReplace($("#poItemId").val());
    	item['poItemName'] = CustomComponents.nullValueReplace($("#poItemName").val());
    	item['version'] = CustomComponents.nullValueReplace($("#itemVersion").val());
    	item['itemIndex'] = CustomComponents.nullValueReplace($("#itemIndex").val());
    };



    /*********************************************************************
     * Edit Receipt
     *********************************************************************/

    var editItem = function (itemIndex) {
        var $modal = $('#common-modal');
        var aodType = $("#receiptOrderTypeId option:selected").val();

        var URL='../receiptorder/receiptItemView';
        if (aodType == "REFURBISH") {
        	URL='../receiptorder/receiptRefurbishItemView';
        } 
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = URL;
            $modal.load(url, '', function () {
                fillItemEditForm(getItemByIndex(itemIndex),itemIndex);
                $modal.modal();
                if (aodType == "REFURBISH") {
                    RefurbishItemAddModal.init();

                } else{
                	ItemAddModal.init();
                }
                
                createLinkForStok(getItemByIndex(itemIndex).itemAssetId)
            });
        }, 1000);
    };
    
    var createLinkForStok=function(id){
    	$( "#createStock" ).empty();
       	var thelink = $('<a>',{
        	    text: 'Create New Stock',
        	    href: '../stock/createstock?partId='+id,
        	    target:'_blank'
        	}).appendTo('#createStock');
    }

    var getItemByIndex = function (itemIndex) {
        for (var i = 0; i < items.length; i++) {
            if (i== itemIndex) {
                return items[i];
            }
        }
    };

    var fillItemEditForm = function (item,itemIndex) {
    	
        $('#itemId').val(item['itemId']);
        $('#itemIndex').val(itemIndex);

        $('#itemAssetId').val(item['itemAssetId']);
        $('#itemAssetName').val(item['itemAssetName']);
        $('#issueNoteitemId').val(item['issueNoteitemId']);
        $('#itemStockId').val(item['itemStockId']);
        $('#itemStockName').val(item['itemStockName']);
        $('#itemQtyReceived').val(item['itemQtyReceived']);
        $('#itemUnitPrice').val(item['itemUnitPrice']);
        $('#itemDescription').val(item['itemDescription']);
        $('#poItemId').val(item['poItemId']);
        $('#poItemName').val(item['poItemName']);
        $('#itemVersion').val(item['version']);
    };


    /*********************************************************************
     * Remove Receipt
     *********************************************************************/

    var removeItem = function (itemIndex) {
        for (var i = 0; i < items.length; i++) {      
            if (i== itemIndex) {
                items.splice(i, 1);
                populateReceiptItem();
                break;
            }
        }
    };


    /*********************************************************************
     * Utilities
     *********************************************************************/
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




   return {
    	
        /*********************************************************************
         * Add Receipt item init
         *********************************************************************/
    	
        init: function () {
            runReceiptOrderTypeChange();
            initButton();
            populateReceiptItem();
        },

        /*********************************************************************
         * Add Receipt
         *********************************************************************/
        addReceiptItem: function () {
            addReceiptItem();
        },

        addItemToList: function (obj) {
            addItemToList(obj);
        },

        /*********************************************************************
         * Edit Receipt
         *********************************************************************/

        editListItem: function (index) {
            editItem(index);
        },

        /*********************************************************************
         * Remove Receipt
         *********************************************************************/

        deleteListItem: function (index) {
            removeItem(index);
        },
        setReceiptItemAsset: function (id, name) {
        	setReceiptItemAsset(id, name);
        },
        setReceiptRefurbishItemAsset: function (id,partId,partName) {
        	setReceiptRefurbishItemAsset(id,partId,partName);
        },

        /*********************************************************************
         * populate Receipt
         *********************************************************************/
        populateReceiptItem: function () {
            populateReceiptItem();
        },
        receiptItemView: function () {
        	receiptItemView();
        },
        receiptRefurbishItemView: function () {
        	receiptRefurbishItemView();
        }

    };
}();