var aodReturnItemTab = function () {


    /**********************************************************
     * Populate Item
     *********************************************************/
    var populateItems = function () {
        if (aodReturnItemList.length > 0) {
            var row, item;
            $("#tbl_aod_return_item > tbody").html("");
            for (row = 0; row < aodReturnItemList.length; row++) {
                item = aodReturnItemList[row];
                var html = "<tr id='row_" + row + "' >" +
                    "<input id='items_" + row + "_aodReturnItemPartId' name='aodReturnItemList[" + row + "].aodId' value='" + item.aodId + "' type='hidden'>" +
                    "<input id='items_" + row + "_aodReturnItemPartName' name='aodReturnItemList[" + row + "].aodNo' value='" + item.aodNo + "' type='hidden'>" +
                    "<input id='items_" + row + "_aodReturnItemStockId' name='aodReturnItemList[" + row + "].aodItemId' value='" + item.aodItemId + "' type='hidden'>" +
                    "<input id='items_" + row + "_aodReturnItemStockId' name='aodReturnItemList[" + row + "].aodItemPartNo' value='" + item.aodItemPartNo + "' type='hidden'>" +
                    "<input id='items_" + row + "_aodReturnItemStockNo' name='aodReturnItemList[" + row + "].returnQty' value='" + item.returnQty + "' type='hidden'>" +
                    "<input id='items_" + row + "_itemAODReturnDescription' name='aodReturnItemList[" + row + "].description' value='" + item.description + "' type='hidden'>" +
                    "<td>" + (row + 1) + "</td>" +
                    "<td>" + item.aodItemPartNo + "</td>" +
                    "<td>" + item.returnQty + "</td>" +
                    "<td>" + item.description + "</td>" +
                    "<td class='center'> " + ButtonUtil.getEditDeleteBtnFromList(row, "aodReturnItemTab") + "</td>" +
                    "</tr>";
                $('#tbl_aod_return_item > tbody:last-child').append(html);
            }
        } else {
            CustomComponents.emptyTableRow("tbl_aod_return_item", 4);
        }
    };


    /**********************************************************
     * View Modals
     *********************************************************/
    var aodReturnItemView = function () {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../aodReturn/aodReturnItemView';
            $modal.load(url, '', function () {
                aodReturnModal.init();
                $modal.modal();
            });
        }, 1000);

    };


    var aodReturnAODView = function () {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../aodReturn/aodView';
            $modal.load(url, '', function () {
                dtAOD.dtAODList("../restapi/aod/getApprovedAOD", "dtAOD.setADOReturnAOD");
                $modal.modal();
            });
        }, 1000);

    };


    var aodReturnAODItemView = function () {
        var itemPartId = $("#aodReturnAODId").val();
        var $modal = $('#stackable-datatable-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../aodReturn/aodItemView';// Then See html table
            $modal.load(url, '', function () {
                dtAODReturnItem.dtAODReturnItemList("../restapi/aod/getAODItemDataTable?id=" + itemPartId, "dtAODReturnItem.setADOReturnAODItem");
                $modal.modal();
            });
        }, 1000);

    };


    /**********************************************************
     * Add Item
     *********************************************************/
    var addAODReturnItem = function (obj) {
        if (($(obj).closest(".modal").find("#rowIndex")).val() != null && ($(obj).closest(".modal").find("#rowIndex")).val() != "") {
            updateAODReturnItem(aodReturnItemList[$(obj).closest(".modal").find("#rowIndex").val()], obj);
            $('#common-modal').modal('toggle');
        } else {
            addNewAODReturnItem(obj);
        }
    };

    var updateAODReturnItem = function (aodItem, obj) {
        aodItem['aodId'] = CustomComponents.nullValueReplace($("#aodReturnAODId").val());
        aodItem['aodNo'] = CustomComponents.nullValueReplace($("#aodReturnAODNo").val());
        aodItem['aodItemId'] = CustomComponents.nullValueReplace($("#aodReturnAodItemId").val());
        aodItem['aodItemPartNo'] = CustomComponents.nullValueReplace($("#aodReturnAodItemNo").val());
        aodItem['returnQty'] = CustomComponents.nullValueReplace($("#returnQuantity").val());
        aodItem['description'] = CustomComponents.nullValueReplace($("#returnDescription").val());
        aodItem['aodCustomerName'] = CustomComponents.nullValueReplace($("#aodReturnAODCustomerName").val());
        aodItem['aodCustomerAddress'] = CustomComponents.nullValueReplace($("#aodReturnAODCustomerAddress").val());
    };

    var addNewAODReturnItem = function (btnObj) {
        var itemObj = {
            id: '',
            itemIndex: aodReturnItemList.length,
            aodId: CustomComponents.nullValueReplace($("#aodReturnAODId").val()),
            aodNo: CustomComponents.nullValueReplace($("#aodReturnAODNo").val()),
            aodItemId: CustomComponents.nullValueReplace($("#aodReturnAodItemId").val()),
            aodItemPartNo: CustomComponents.nullValueReplace($("#aodReturnAodItemNo").val()),
            returnQty: CustomComponents.nullValueReplace($("#returnQuantity").val()),
            description: CustomComponents.nullValueReplace($("#returnDescription").val()),
            aodCustomerName: CustomComponents.nullValueReplace($("#aodReturnAODCustomerName").val()),
            aodCustomerAddress: CustomComponents.nullValueReplace($("#aodReturnAODCustomerAddress").val()),
            other: false
        };
        aodReturnItemList.push(itemObj);
        $('#common-modal').modal('toggle');
    };


    /**********************************************************
     * Edit
     *********************************************************/
    var editListItem = function (index) {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../aodReturn/aodReturnItemView';
            $modal.load(url, '', function () {
                fillItemEditForm(aodReturnItemList[index], index);
                aodReturnModal.init();
                $modal.modal();
            });
        }, 1000);
    };


    var fillItemEditForm = function (item, index) {
        $('#rowIndex').val(index);
        $('#aodReturnAODId').val(item['aodId']);
        $('#aodReturnAODNo').val(item['aodNo']);
        $('#aodReturnAodItemId').val(item['aodItemId']);
        $('#aodReturnAodItemNo').val(item['aodItemPartNo']);
        $('#returnQuantity').val(item['returnQty']);
        $('#returnDescription').val(item['description']);
        $('#aodReturnAODCustomerName').val(item['aodCustomerName']);
        $('#aodReturnAODCustomerAddress').val(item['aodCustomerAddress']);
    };


    /**********************************************************
     * Delete Item
     *********************************************************/
    var deleteItem = function (index) {
        aodReturnItemList.splice(index, 1);
        populateItems();
    };


    var partInputClearFromAOD = function () {
        $('#aodReturnAODCustomerName').val("");
        $('#aodReturnAODCustomerAddress').val("");
        $('#aodReturnAodItemId').val("");
        $('#aodItemQuantity').val("");
        $('#aodReturnAodItemNo').val("");
    };

    return {
        populateAODReturnItems: function () {
            populateItems();
        },

        /**********************************************************
         * View Modals
         *********************************************************/
        aodReturnItemView: function () {
            aodReturnItemView();
        },
        aodReturnAODView: function () {
            aodReturnAODView();
        },
        aodReturnAODItemView: function () {
            aodReturnAODItemView();
        },


        /**********************************************************
         * Add Item
         *********************************************************/
        addAODReturnItem: function (obj) {
            addAODReturnItem(obj);
        },
        partInputClearFromAOD: function () {
            partInputClearFromAOD();
        },

        /**********************************************************
         * Edit
         *********************************************************/
        editListItem: function (index) {
            editListItem(index);
        },

        /**********************************************************
         * Delete
         *********************************************************/
        deleteListItem: function (index) {
            deleteItem(index);
        },


    };
}();