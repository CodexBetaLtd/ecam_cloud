var SparePartTab = function () {
    
    /**********************************************************
     * Init Buttons
     *********************************************************/
    var initButtons = function () {
        $('#btn-new-spare-part').on('click', function () {
            SparePartTab.sparePartView();
        });
    };
    
    /**********************************************************
     * View Modals
     *********************************************************/

    var sparePartView = function () {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../sparepartaddview';
            $modal.load(url, '', function () {
                SparePartAddModal.init();
               // $("#sparePartindex").val(spareParts.length)
                $modal.modal();
            });
        }, 1000);
    };  

    var editListItem = function (itemIndex) {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../sparepartaddview';
            $modal.load(url, '', function () {
                fillItemEditForm(spareParts[itemIndex]);
                SparePartAddModal.init();
                $modal.modal();
            });
        }, 1000);
    };
    
    /**********************************************************
     * Populate Item
     *********************************************************/
    var populateItems = function () {
        if (spareParts.length > 0) {
            var row, item;
            $("#spare-part-tbl > tbody").html("");
            for (row = 0; row < spareParts.length; row++) {
                item = spareParts[row]; 
                item['index'] = row;
                var html = "<tr id='row_" + row + "' >" +
	                "<input id='items_" + row + "_aodItemId' name='sparePartDTOs[" + row + "].id' value='" + CustomValidation.nullValueReplace(item.id) + "' type='hidden'>" + 
                    "<input id='items_" + row + "_aodItemPartId' name='sparePartDTOs[" + row + "].partId' value='" + CustomValidation.nullValueReplace(item.partId) + "' type='hidden'>" +
                    "<input id='items_" + row + "_aodItemPartName' name='sparePartDTOs[" + row + "].partCode' value='" + CustomValidation.nullValueReplace(item.partCode) + "' type='hidden'>" + 
                    "<input id='items_" + row + "_aodItemQuantity' name='sparePartDTOs[" + row + "].quantity' value='" + CustomValidation.nullValueReplace(item.quantity) + "' type='hidden'>" +
                    "<input id='items_" + row + "_aodItemDescription' name='sparePartDTOs[" + row + "].description' value='" + CustomValidation.nullValueReplace(item.description) + "' type='hidden'>" +
                    "<input id='items_" + row + "_aodItemVersion' name='sparePartDTOs[" + row + "].version' value='" + CustomValidation.nullValueReplace(item.version) + "' type='hidden'>" + 
                    "<td>" + (row + 1) + "</td>" +
                    "<td>" + CustomValidation.nullValueReplace(item.partCode) + "</td>" +
                    "<td>" + CustomValidation.nullValueReplace(item.description) + "</td>" + 
                    "<td>" + parseFloat(item.quantity).toFixed(2) + "</td>" +
                    "<td class='center'>" +
		                "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
			                ButtonUtil.getCommonBtnEdit("SparePartTab.editListItem",  row) +
			                ButtonUtil.getCommonBtnDelete("SparePartTab.deleteListItem", row) +
		                "</div>" +
	                "</td>"+
                    "</tr>";
                $('#spare-part-tbl > tbody:last-child').append(html);
            }
        } else {
            CustomComponents.emptyTableRow("spare-part-tbl", 8);
        }
    };

    /**********************************************************
     * Add Item
     *********************************************************/   

    var addSparePart= function (item) {
    	console.log(item);
        var itemObj = {};
        
        if (item.index != null && item.index != "" && item.index >= 0) {
            itemObj = spareParts[ item.index ];
            setCommonDataToItemObj(item, itemObj);
        } else {
            if (spareParts.length == 0) {
                itemObj['index'] = 0;
            } else {
            	CustomValidation.validateFieldNull(itemObj, 'index', spareParts.length);
            }
            setCommonDataToItemObj(item, itemObj);
            if(!isSparePartAlreadyAdd(itemObj)){
                spareParts.push(itemObj);
            }else{
                alert("Spare Part Already Added");
            }
        }
        populateItems();
        $('#master-modal-datatable').modal('toggle');

    };

    var setCommonDataToItemObj = function (updatedItemObj, itemObj) { 
    	
    	CustomValidation.validateFieldNull(itemObj, 'id', updatedItemObj.id);
    	CustomValidation.validateFieldNull(itemObj, 'index', updatedItemObj.index);
    	CustomValidation.validateFieldNull(itemObj, 'partId', updatedItemObj.partId);
    	CustomValidation.validateFieldNull(itemObj, 'partCode', updatedItemObj.partCode); 
    	CustomValidation.validateFieldNull(itemObj, 'description', updatedItemObj.description);
    	CustomValidation.validateFieldNull(itemObj, 'quantity', updatedItemObj.quantity);
        CustomValidation.validateFieldNull(itemObj, 'version', updatedItemObj.version);
        
    };
    
    /**********************************************************
     * Edit
     *********************************************************/

    var fillItemEditForm = function (item) {
    	
        $('#sparePartindex').val(item['index']);
        $('#spareId').val(item['id']);
        $('#sparePartPartId').val(item['partId']);
        $('#sparePartPartCode').val(item['partCode']);
        $('#sparePartDescription').val(item['description']);
        $('#sparePartQuantity').val(item['quantity']); 
        $("#sparePartVersion").val(item['version']); 
        
    };

    /**********************************************************
     * Delete Item
     *********************************************************/
    var removeItem = function (itemIndex) {
        spareParts.splice(itemIndex, 1);
        populateItems();
    };

    /*********************************************************************
     * Utilities
     *********************************************************************/

    var isSparePartAlreadyAdd = function (sparePart) {
        for (var i = 0; i < spareParts.length; i++) {
            if(spareParts[i].index==sparePart.index){
                if (spareParts[i].partId == sparePart.partId) {
                    return true;
                }
            }

        }
        return false;
    };

    return {

        init: function () {
            initButtons();
            populateItems();
        },

        addSparePart: function (item) {
            addSparePart(item);
        },

        /**********************************************************
         * View Modals
         *********************************************************/
        sparePartView: function () {
            sparePartView();
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