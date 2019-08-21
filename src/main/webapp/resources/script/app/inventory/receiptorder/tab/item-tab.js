var receiptItemTab = function () {

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
            var $modal = $('#master-modal');
            CustomComponents.ajaxModalLoadingProgressBar();
            setTimeout(function () {
                var url = '../receiptorder/receiptItemView';
                $modal.load(url, '', function () {
                    $modal.modal();
                });
            }, 1000);
        } else {
            alert("Please Set Supplier First");
        }
    };


    /*********************************************************************
     * Add Receipt
     *********************************************************************/
    var addReceiptItem = function () {
        var item = {};
        item['itemId'] = $('#itemId').val();
        item['itemIndex'] = $('#itemIndex').val();

        item['itemAssetId'] = $('#itemAssetId').val();
        item['itemAssetName'] = $('#itemAssetName').val();
        item['itemStockId'] = $('#itemStockId').val();
        item['itemStockName'] = $('#itemStockName').val();
        item['itemQtyReceived'] = $('#itemQtyReceived').val();
        item['itemUnitPrice'] = $('#itemUnitPrice').val();
        item['itemDescription'] = $('#itemDescription').val();
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
                validateFieldNull(itemObj, 'itemIndex', items.length);
            }
            setCommonDataToItemObj(item, itemObj);
            items.push(itemObj);
        }
    };

    var setCommonDataToItemObj = function (updatedItemObj, itemObj) {
        validateFieldNull(itemObj, 'itemId', updatedItemObj.itemId);
        validateFieldNull(itemObj, 'itemAssetId', updatedItemObj.itemAssetId);
        validateFieldNull(itemObj, 'itemAssetName', updatedItemObj.itemAssetName);
        validateFieldNull(itemObj, 'itemStockId', updatedItemObj.itemStockId);
        validateFieldNull(itemObj, 'itemStockName', updatedItemObj.itemStockName);
        validateFieldNull(itemObj, 'itemQtyReceived', updatedItemObj.itemQtyReceived);
        validateFieldNull(itemObj, 'itemUnitPrice', updatedItemObj.itemUnitPrice);
        validateFieldNull(itemObj, 'itemDescription', updatedItemObj.itemDescription);

        validateFieldNull(itemObj, 'version', updatedItemObj.version);
    };


    /*********************************************************************
     * Edit Receipt
     *********************************************************************/

    var editItem = function (itemIndex) {
        var $modal = $('#item-add-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../receiptorder/receiptItemView';
            $modal.load(url, '', function () {
                fillItemEditForm(getItemByIndex(itemIndex));
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
        $('#itemStockId').val(item['itemStockId']);
        $('#itemStockName').val(item['itemStockName']);
        $('#itemQtyReceived').val(item['itemQtyReceived']);
        $('#itemUnitPrice').val(item['itemUnitPrice']);
        $('#itemDescription').val(item['itemDescription']);

        $('#itemVersion').val(item['version']);
    };


    /*********************************************************************
     * Remove Receipt
     *********************************************************************/

    var removeItem = function (itemIndex) {
        for (var i = 0; i < items.length; i++) {
            if (items[i].itemIndex == itemIndex) {
                items.splice(i, 1);
                updateIndexes();
                resetItemHtmlTable();
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


    /*********************************************************************
     * Receipt Item Asset Data
     *********************************************************************/
    var receiptAssetView = function () {
        loadAssetSelectModal("asset_tbl", "../restapi/asset/parts", "receiptItemTab.setReceiptItemAsset");
    };

    var loadAssetSelectModal = function (tableId, URL, method) {
        var $modal = $('#stackable-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../receiptorder/receiptAssetView';
            $modal.load(url, '', function () {
                dtReceiptAsset.dtReceiptAsset(tableId, URL, method);
                $modal.modal();
            });
        }, 1000);
    };

    var setReceiptItemAsset = function (id, name) {
        $('#itemAssetId').val(id);
        $('#itemAssetName').val(name);
    };


    /*********************************************************************
     * Receipt Item Stock Data
     *********************************************************************/
    var receiptStockView = function (partId) {
        getReceiptStockView("asset_tbl", "../restapi/stock/stockByPart", "receiptItemTab.setReceiptItemStock", partId);
    };

    var getReceiptStockView = function (tableId, url, method, partId) {
        var $modal = $('#stackable-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../receiptorder/receiptStockView';
            $modal.load(url, '', function () {
                dtReceiptStock.getStockDataTable(tableId, url, method, partId);
                $modal.modal();
            });
        }, 1000);
    };

    var setReceiptItemStock = function (id, location) {
        $('#itemStockId').val(id);
        $('#itemStockName').val(location);
        $('#select-pages-open-modal').modal('toggle');
    };


    return {

        /*********************************************************************
         * Add Receipt
         *********************************************************************/
        addReceiptItem: function () {
            addReceiptItem();
        },

        addItemToList: function (item) {
            addItemToList(item);
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


        /*********************************************************************
         * Receipt Item Asset Data
         *********************************************************************/
        receiptAssetView: function () {
            receiptAssetView();
        },
        setReceiptItemAsset: function (id, name) {
            setReceiptItemAsset(id, name);
        },


        /*********************************************************************
         * Receipt Item Stock Data
         *********************************************************************/
        receiptStockView: function (partId) {
            receiptStockView(partId);
        },
        setReceiptItemStock: function (id, location) {
            setReceiptItemStock(id, location);
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


    };
}();