var MRNItemTab = function () {
    
    /**********************************************************
     * Init Buttons
     *********************************************************/
    var initButtons = function () {
        $('#btn-mrn-item-modal-view').on('click', function () {
        	MRNItemTab.mrnItemView();
        });
    };
    
    /**********************************************************
     * View Modals
     *********************************************************/

    var mrnItemView = function () {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../mrn/mrnItemView';
            $modal.load(url, '', function () {
            	MRNItemAddModal.init('master-modal-datatable');
                $modal.modal();
            });
        }, 1000);
    };  

    var editListItem = function (itemIndex) {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../mrn/mrnItemView';
            $modal.load(url, '', function () {
                fillItemEditForm(mrnItemList[itemIndex]);
                MRNItemAddModal.init('master-modal-datatable');
                $modal.modal();
            });
        }, 1000);
    };
    
    /**********************************************************
     * Populate Item
     *********************************************************/
    var populateItems = function () {
        if (mrnItemList.length > 0) {
            var row, item;
            $("#tbl_aod_item > tbody").html("");
            for (row = 0; row < mrnItemList.length; row++) {
                item = mrnItemList[row]; 
                item['index'] = row;
                var html = "<tr id='row_" + row + "' >" +
	                "<input id='items_" + row + "_aodItemId' name='mrnItemDTOs[" + row + "].id' value='" + CustomValidation.nullValueReplace(item.id) + "' type='hidden'>" + 
                    "<input id='items_" + row + "_aodItemPartId' name='mrnItemDTOs[" + row + "].partId' value='" + CustomValidation.nullValueReplace(item.partId) + "' type='hidden'>" +
                    "<input id='items_" + row + "_aodItemPartName' name='mrnItemDTOs[" + row + "].partName' value='" + CustomValidation.nullValueReplace(item.partName) + "' type='hidden'>" + 
                    "<input id='items_" + row + "_aodItemStockId' name='mrnItemDTOs[" + row + "].stockId' value='" + CustomValidation.nullValueReplace(item.stockId) + "' type='hidden'>" + 
                    "<input id='items_" + row + "_aodItemStockBatchNo' name='mrnItemDTOs[" + row + "].stockBatchNo' value='" + CustomValidation.nullValueReplace(item.stockBatchNo) + "' type='hidden'>" + 
                    "<input id='items_" + row + "_aodItemQuantity' name='mrnItemDTOs[" + row + "].itemQuantity' value='" + CustomValidation.nullValueReplace(item.itemQuantity) + "' type='hidden'>" +
                    "<input id='items_" + row + "_aodItemDescription' name='mrnItemDTOs[" + row + "].description' value='" + CustomValidation.nullValueReplace(item.description) + "' type='hidden'>" +
                    "<input id='items_" + row + "_aodItemWarehouseId' name='mrnItemDTOs[" + row + "].warehouseId' value='" + CustomValidation.nullValueReplace(item.warehouseId) + "' type='hidden'>" +
                    "<input id='items_" + row + "_aodItemWarehouseName' name='mrnItemDTOs[" + row + "].warehouseName' value='" + CustomValidation.nullValueReplace(item.warehouseName) + "' type='hidden'>" + 
                    "<input id='items_" + row + "_aodItemVersion' name='mrnItemDTOs[" + row + "].version' value='" + CustomValidation.nullValueReplace(item.version) + "' type='hidden'>" + 
                    "<td>" + (row + 1) + "</td>" +
                    "<td>" + CustomValidation.nullValueReplace(item.partName) + "</td>" +
                    "<td>" + CustomValidation.nullValueReplace(item.warehouseName) + "</td>" + 
                    "<td>" + CustomValidation.nullValueReplace(item.stockBatchNo) + "</td>" + 
                    "<td>" + parseFloat(item.itemQuantity).toFixed(2) + "</td>" +
                    "<td>" + CustomValidation.nullValueReplace(item.description) + "</td>" +
                    "<td class='center'>" +
		                "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
			                ButtonUtil.getCommonBtnEdit("MRNItemTab.editListItem",  row) +
			                ButtonUtil.getCommonBtnDelete("MRNItemTab.deleteListItem", row) +
		                "</div>" +
	                "</td>"+
                    "</tr>";
                $('#tbl_aod_item > tbody:last-child').append(html);
            }
        } else {
            CustomComponents.emptyTableRow("tbl_aod_item", 8);
        }
    };

    /**********************************************************
     * Add Item
     *********************************************************/   

    var addItemToList = function (item) {
    	
        var itemObj = {};
        
        if (item.index != null && item.index != "" && item.index >= 0) {
            itemObj = mrnItemList[ item.index ];
            setCommonDataToItemObj(item, itemObj);
        } else {
            if (mrnItemList.length == 0) {
                itemObj['index'] = 0;
            } else {
            	CustomValidation.validateFieldNull(itemObj, 'index', mrnItemList.length);
            }
            setCommonDataToItemObj(item, itemObj);
            mrnItemList.push(itemObj);
        }
        populateItems();
    };

    var setCommonDataToItemObj = function (updatedItemObj, itemObj) { 
    	
    	CustomValidation.validateFieldNull(itemObj, 'id', updatedItemObj.id);
    	CustomValidation.validateFieldNull(itemObj, 'index', updatedItemObj.index);
    	CustomValidation.validateFieldNull(itemObj, 'partId', updatedItemObj.partId);
    	CustomValidation.validateFieldNull(itemObj, 'partName', updatedItemObj.partName); 
    	CustomValidation.validateFieldNull(itemObj, 'partCode', updatedItemObj.partCode); 
    	CustomValidation.validateFieldNull(itemObj, 'stockId', updatedItemObj.stockId);
    	CustomValidation.validateFieldNull(itemObj, 'stockBatchNo', updatedItemObj.stockBatchNo);
    	CustomValidation.validateFieldNull(itemObj, 'warehouseId', updatedItemObj.warehouseId);
    	CustomValidation.validateFieldNull(itemObj, 'warehouseName', updatedItemObj.warehouseName);
    	CustomValidation.validateFieldNull(itemObj, 'description', updatedItemObj.description);
    	CustomValidation.validateFieldNull(itemObj, 'itemQuantity', updatedItemObj.itemQuantity);
    	CustomValidation.validateFieldNull(itemObj, 'remainingQuantity', updatedItemObj.remainingQuantity);
        CustomValidation.validateFieldNull(itemObj, 'version', updatedItemObj.version);
        
    };
    
    /**********************************************************
     * Edit
     *********************************************************/

    var fillItemEditForm = function (item) {
    	
        $('#itemIndex').val(item['index']);
        $('#itemId').val(item['itemId']);
        $('#itemPartId').val(item['partId']);
        $('#itemPartName').val(item['partName']);
        $('#itemStockId').val(item['stockId']); 
        $('#itemStockBatchNo').val(item['stockBatchNo']);  
        $('#itemStockWarehouseId').val(item['warehouseId']);
        $('#itemStockWarehouseName').val(item['warehouseName']);  
        $("#itemStockOnHandQty").text(item['remainingQuantity']); 
        $("#itemStockQuantity").val(item['remainingQuantity']); 
        $('#itemDescription').val(item['description']);
        $('#itemQuantity').val(item['itemQuantity']); 
        $("#itemVersion").val(item['version']); 
        
    };

    /**********************************************************
     * Delete Item
     *********************************************************/
    var removeItem = function (itemIndex) {
    	mrnItemList.splice(itemIndex, 1);
        populateItems();
    };

    /*********************************************************************
     * Utilities
     *********************************************************************/

    var updateIndexes = function () {
        for (var i = 0; i < items.length; i++) {
            items[i].itemIndex = i;
        }
    };

    return {

        init: function () {
            initButtons();
            populateItems();
        },

        addItemToList: function (item) {
            addItemToList(item);
        },

        /**********************************************************
         * View Modals
         *********************************************************/
        mrnItemView: function () {
            mrnItemView();
        },  

        /**********************************************************
         * Add Item
         *********************************************************/
        addAODItem: function (obj) {
            addAODItem(obj);
        },  
        
        editListItem: function (index) {
            editListItem(index);
        },

        deleteListItem: function (index) {
            removeItem(index);
        },

    };
}();