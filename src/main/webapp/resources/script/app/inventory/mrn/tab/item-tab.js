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
                    "<input id='items_" + row + "_aodItemQuantity' name='mrnItemDTOs[" + row + "].itemQuantity' value='" + CustomValidation.nullValueReplace(item.itemQuantity) + "' type='hidden'>" +
                    "<input id='items_" + row + "_aodItemDescription' name='mrnItemDTOs[" + row + "].description' value='" + CustomValidation.nullValueReplace(item.description) + "' type='hidden'>" +
                    "<input id='items_" + row + "_aodItemVersion' name='mrnItemDTOs[" + row + "].version' value='" + CustomValidation.nullValueReplace(item.version) + "' type='hidden'>" + 
                    "<td>" + (row + 1) + "</td>" +
                    "<td>" + CustomValidation.nullValueReplace(item.partName) + "</td>" + 
                    "<td>" + CustomValidation.nullValueReplace(item.description) + "</td>" +
                    "<td>" + parseFloat(item.itemQuantity).toFixed(2) + "</td>" +
                    "<td class='center'> " + ButtonUtil.getEditDeleteBtnFromList(row, "MRNItemTab") + "</td>" +
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
    	console.log(mrnItemList)

        if (item.index != null && item.index != "" && item.index >= 0) {
            itemObj = mrnItemList[item.index];
            console.log(mrnItemList[ item.index ])
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
    	CustomValidation.validateFieldNull(itemObj, 'partId', updatedItemObj.partId);
    	CustomValidation.validateFieldNull(itemObj, 'partName', updatedItemObj.partName); 
    	CustomValidation.validateFieldNull(itemObj, 'partCode', updatedItemObj.partCode); 
    	CustomValidation.validateFieldNull(itemObj, 'description', updatedItemObj.description);
    	CustomValidation.validateFieldNull(itemObj, 'itemQuantity', updatedItemObj.itemQuantity);
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