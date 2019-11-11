var MRNReturnItemTab = function () {
    
    /**********************************************************
     * Init Buttons
     *********************************************************/
    var initButtons = function () {
        $('#btn-mrn-return-item-modal-view').on('click', function () {
        	MRNReturnItemTab.mrnReturnItemView();
        });
    };
    
    /**********************************************************
     * View Modals
     *********************************************************/

    var mrnReturnItemView = function () {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../mrnReturn/mrnReturnItemView';
            $modal.load(url, '', function () {
            	MRNReturnItemAddModal.init('master-modal-datatable');
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
                MRNReturnItemAddModal.init('master-modal-datatable');
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
            $("#tbl_mrn_return_item> tbody").html("");
            for (row = 0; row < mrnItemList.length; row++) {
                item = mrnItemList[row]; 
                item['index'] = row;
                var html = "<tr id='row_" + row + "' >" +
                "<input id='items_" + row + "_aodItemId' name='mrnItemDTOs[" + row + "].id' value='" + CustomValidation.nullValueReplace(item.id) + "' type='hidden'>" + 
	                "<input id='items_" + row + "_aodItemId' name='mrnItemDTOs[" + row + "].mrnItemId' value='" + CustomValidation.nullValueReplace(item.mrnItemId) + "' type='hidden'>" + 
                    "<input id='items_" + row + "_aodItemPartName' name='mrnItemDTOs[" + row + "].partName' value='" + CustomValidation.nullValueReplace(item.partName) + "' type='hidden'>" + 
                    "<input id='items_" + row + "_aodItemQuantity' name='mrnItemDTOs[" + row + "].itemReturnQuantity' value='" + CustomValidation.nullValueReplace(item.itemReturnQuantity) + "' type='hidden'>" +
                    "<input id='items_" + row + "_aodItemDescription' name='mrnItemDTOs[" + row + "].description' value='" + CustomValidation.nullValueReplace(item.description) + "' type='hidden'>" +
                    "<input id='items_" + row + "_aodItemVersion' name='mrnItemDTOs[" + row + "].version' value='" + CustomValidation.nullValueReplace(item.version) + "' type='hidden'>" + 
                    "<td>" + (row + 1) + "</td>" +
                    "<td>" + CustomValidation.nullValueReplace(item.partName) + "</td>" + 
                    "<td>" + CustomValidation.nullValueReplace(item.description) + "</td>" +
                    "<td>" + parseFloat(item.itemReturnQuantity).toFixed(2) + "</td>" +
                    "<td class='center'> " + ButtonUtil.getEditDeleteBtnFromList(row, "MRNReturnItemTab") + "</td>" +
                    "</tr>";
                $('#tbl_mrn_return_item > tbody:last-child').append(html);
            }
        } else {
            CustomComponents.emptyTableRow("tbl_mrn_return_item", 8);
        }
    };

    /**********************************************************
     * Add Item
     *********************************************************/   

    var addItemToList = function (item) {
        var itemObj = {};
    	console.log(mrnItemList)

        if (item.index != null && item.index != "" && item.index >= 0) {
            itemObj = mrnItemList[item.index];
            setCommonDataToItemObj(item, itemObj);
        } else {
        	console.log(itemObj)

            if (mrnItemList.length == 0) {
                itemObj['index'] = 0;
            } else {
            	CustomValidation.validateFieldNull(itemObj, 'index', mrnItemList.length);
            }
            setCommonDataToItemObj(item, itemObj);
            mrnItemList.push(itemObj);
        }
    	console.log(itemObj)

        populateItems();
    };

    var setCommonDataToItemObj = function (updatedItemObj, itemObj) { 
    	
    	CustomValidation.validateFieldNull(itemObj, 'id', updatedItemObj.id);
    	CustomValidation.validateFieldNull(itemObj, 'index', updatedItemObj.index);
    	CustomValidation.validateFieldNull(itemObj, 'mrnItemId', updatedItemObj.mrnItemId);
    	CustomValidation.validateFieldNull(itemObj, 'partName', updatedItemObj.partName); 
    	CustomValidation.validateFieldNull(itemObj, 'description', updatedItemObj.description);
    	CustomValidation.validateFieldNull(itemObj, 'itemReturnQuantity', updatedItemObj.itemReturnQuantity);
        CustomValidation.validateFieldNull(itemObj, 'version', updatedItemObj.version);
        
    };
    
    /**********************************************************
     * Edit
     *********************************************************/

    var fillItemEditForm = function (item) {	
        $('#itemIndex').val(item['index']);
        $('#mrnItemId').val(item['mrnItemId']);
        $('#itemId').val(item['id']);
        $('#itemPartName').val(item['partName']);
        $('#mrnReturnItemDescription').val(item['description']);
        $('#itemQuantity').val(item['itemReturnQuantity']); 
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
        mrnReturnItemView: function () {
        	mrnReturnItemView();
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