var MRNItemTab = function () {
    
    /**********************************************************
     * Init Buttons
     *********************************************************/
    var initButtons = function () {
        $('#btn-mrn-item-modal-view').on('click', function () {
        	MRNItemTab.mrnItemView();
        });
        
        $('#btn-generate-aod').on('click', function () {
        	MRNItemTab.generateAODFromMrn();
        });
        
        $('#btn-generate-po').on('click', function () {
        	MRNItemTab.generatePOFromMrn();
        });
        
        $('#btn-generate-rfq').on('click', function () {
        	MRNItemTab.generateRFQFromMrn();
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
                    "<input id='items_" + row + "_approvedQuantity' name='mrnItemDTOs[" + row + "].approvedQuantity' value='" + CustomValidation.nullValueReplace(item.approvedQuantity) + "' type='hidden'>" +
                    "<input id='items_" + row + "_aodItemDescription' name='mrnItemDTOs[" + row + "].description' value='" + CustomValidation.nullValueReplace(item.description) + "' type='hidden'>" +
                    "<input id='items_" + row + "_aodItemVersion' name='mrnItemDTOs[" + row + "].version' value='" + CustomValidation.nullValueReplace(item.version) + "' type='hidden'>" + 
                    "<td>" +
                    "<div class='checkbox-center'>" +
                    "<input type='checkbox' name='selectedMRNItem' value='" + CustomValidation.nullValueReplace(item.id)+ "' class='grey'>" +
                    "</div>" +
                    "<td>" + CustomValidation.nullValueReplace(item.partName) + "</td>" + 
                    "<td>" + CustomValidation.nullValueReplace(item.description) + "</td>" +
                    "<td>" + parseFloat(item.itemQuantity).toFixed(2) + "</td>" +
                    "<td>" + parseFloat(item.approvedQuantity).toFixed(2) + "</td>" +
                    "<td class='center'> " + ButtonUtil.getEditDeleteBtnFromList(row, "MRNItemTab") + "</td>" +
                    "</tr>";
                $('#tbl_aod_item > tbody:last-child').append(html);
            }
        } else {
            CustomComponents.emptyTableRow("tbl_aod_item", 8);
        }
    };
    
    
    var generateAODFromMrn=function(){
    	var checkedValues = $("input[name='selectedMRNItem']:checkbox:checked").map(function() {
    		if(this.value == null || this.value == "") {
    			isSaved = false;
    		}
    		return this.value;
    	}).get();
    	
    	if(checkedValues.length>0){
    		$.ajax({
    			type: "GET",
    			url: "../aod/generateAodFromMrn?ids=" + checkedValues + "&mrnId=" + $('#mrnId').val(),
    			contentType: "application/json",
    			dataType: "json",
    			success: function (result) {
    				if (result.status == "SUCCESS") {
    					var mappingUrlText = "../aod/edit?id=" + result.msgList[1];
    					$("#message-div").html(CustomComponents.getSuccessMsgDivWithUrl(result.msgList[0],result.msgList[2],mappingUrlText));
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
    	}else{
    		alert("Please select at least one MRN Item")
    	}
    	
    	
    }
    var generateRFQFromMrn=function(){
    	var checkedValues = $("input[name='selectedMRNItem']:checkbox:checked").map(function() {
    		if(this.value == null || this.value == "") {
    			isSaved = false;
    		}
    		return this.value;
    	}).get();
    	
    	if(checkedValues.length>0){
    		$.ajax({
    			type: "GET",
    			url: "../rfq/generateRFQFromMrn?ids=" + checkedValues + "&mrnId=" + $('#mrnId').val(),
    			contentType: "application/json",
    			dataType: "json",
    			success: function (result) {
    				if (result.status == "SUCCESS") {
    					var mappingUrlText = "../rfq/edit?id=" + result.msgList[1];
    					console.log(result)
    					$("#message-div").html(CustomComponents.getSuccessMsgDivWithUrl(result.msgList[0],result.msgList[2],mappingUrlText));
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
    	}else{
    		alert("Please select at least one MRN Item")
    	}
    	
    	
    }
   var generatePOFromMrn=function(){
   	var checkedValues = $("input[name='selectedMRNItem']:checkbox:checked").map(function() {
		if(this.value == null || this.value == "") {
			isSaved = false;
		}
	    return this.value;
	}).get();
   	
   	if(checkedValues.length>0){
   		$.ajax({
   	        type: "GET",
   	        url: "../purchaseorder/generatePoFromMrn?ids=" + checkedValues + "&mrnId=" + $('#mrnId').val(),
   	        contentType: "application/json",
   	        dataType: "json",
   	        success: function (result) {
   	       	if (result.status == "SUCCESS") {
   	        		var mappingUrlText = "../purchaseorder/edit?id=" + result.msgList[1];
   	        		console.log(result)
   	        		$("#message-div").html(CustomComponents.getSuccessMsgDivWithUrl(result.msgList[0],result.msgList[2],mappingUrlText));
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
   	}else{
   		alert("Please select at least one MRN Item")
   	}
   	

   }

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
    	CustomValidation.validateFieldNull(itemObj, 'approvedQuantity', updatedItemObj.approvedQuantity);
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
        $('#approvedItemQuantity').val(item['approvedQuantity']); 
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
        generatePOFromMrn:function(){
        	generatePOFromMrn();
        },
        generateAODFromMrn:function(){
        	generateAODFromMrn();
        },
        generateRFQFromMrn:function(){
        	generateRFQFromMrn();
        }

    };
}();