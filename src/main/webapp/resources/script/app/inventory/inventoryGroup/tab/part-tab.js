var InventoryGroupPartTab = function () {

    /**********************************************************
     * Populate Item
     *********************************************************/
    var populatePart = function () {
        if (partList.length > 0) {
            var row, part;
            $("#tbl_inventory_group_part > tbody").html("");
            for (row = 0; row < partList.length; row++) {
                part = partList[row];
                var html = "<tr id='row_" + row + "' >" +
                    "<input id='parts_" + row + "id' name='inventoryGroupPartDTOs[" + row + "].id' value='" + CustomComponents.nullValueReplace(part.id) + "' type='hidden'>" +
                    "<input id='parts_" + row + "version' name='inventoryGroupPartDTOs[" + row + "].version' value='" + CustomComponents.nullValueReplace(part.version) + "' type='hidden'>" +
                    "<input id='parts_" + row + "partId' name='inventoryGroupPartDTOs[" + row + "].partId' value='" + CustomComponents.nullValueReplace(part.partId) + "' type='hidden'>" +
                    "<td>" + (row + 1) + "</td>" +
                    "<td>" + part.partCode + "</td>" +
                    "<td>" + part.partName + "</td>" +
                    "<td class='center'>" +
                    "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
                    ButtonUtil.getCommonBtnDelete("InventoryGroupPartTab.removeItem", row) +
                    "</div></td>"+
                    "</tr>";
                $('#tbl_inventory_group_part > tbody:last-child').append(html);
            }
        } else {
            CustomComponents.emptyTableRow("tbl_inventory_group_part", 4);
        }
    };


    /**********************************************************
     * View Modals
     *********************************************************/
    var getInventoryGroupPartView = function () {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../inventorygroup/partView';
            $modal.load(url, '', function () {
            	dtInventoryGroupPart.dtGetPartList();
                $modal.modal();
            });
        }, 1000);
    };




    /**********************************************************
     * Add Part
     *********************************************************/

    var addNewPart = function (partId,partName,partCode) {
        var itemObj = {
            id: '',
            version: '',
            index: partList.length,
            partId: CustomComponents.nullValueReplace(partId),
            partName: CustomComponents.nullValueReplace(partName),
            partCode: CustomComponents.nullValueReplace(partCode),
        };
        console.log(itemObj)
        partAdd(itemObj);
    };

    var partAdd = function (itemObj) {
        if (isPartAlreadyAdd(itemObj)) {
            alert("Part already Added.Please change it");
        } else {
            partList.push(itemObj);
            populatePart();
            $('#master-modal-datatable').modal('hide');
        }
    }

   var isPartAlreadyAdd =function(part){
	   for(var i=0;i<partList.length;i++){
		   if(partList[i].index != part.index){
			   if(partList[i].partId==part.partId){
				   return true;
			   }
		   }
	   }
	   return false;
   }
    /**********************************************************
     * Delete Item
     *********************************************************/
    var removeItem = function (itemIndex) {
    	partList.splice(itemIndex, 1);
    	populatePart();
        //aodItemTab.populateAODItems();
    };


    return {
        init: function () {
        	populatePart();
        },

        addNewPart:function(partId,partName,partCode){
        	addNewPart(partId,partName,partCode);
        },
        /**********************************************************
         * Delete
         *********************************************************/
        deleteListItem: function (index) {
            removeItem(index);
        },
        getInventoryGroupPartView:function(){
        	getInventoryGroupPartView();
        }


    };
}();