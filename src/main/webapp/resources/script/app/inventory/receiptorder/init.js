jQuery(document).ready(function () {

    Main.init();

    ReceiptOrderAdd.init();
    receiptItemTab.populateReceiptItem();
//    for (var i = 0; i < thymeLeafItems.length; i++) {
//        receiptItemTab.addItemToList(thymeLeafItems[i])
//    }
//    ;


    /*********************************************************************
     * init
     *********************************************************************/
    if ($("#receiptOrderId").val() !== undefined || $("#receiptOrderId").val() !== "" || $("#receiptOrderId").val() !== " ") {
        receiptItemTab.populateReceiptItem();
    }


    /*********************************************************************
     * Receipt Order Item
     *********************************************************************/
    $(document).on('click', '#btnNewReceiptItem', function (event) {
        event.preventDefault();
        receiptItemTab.receiptItemView();
    });
    
    $(document).on('click', '#asset-select-modal-btn', function (event) {
        event.preventDefault();
        receiptItemTab.receiptAssetView();
    });
    
    $(document).on('click', '#stock-select-modal-btn', function (event) {
        event.preventDefault();
        receiptItemTab.receiptStockView($('#itemAssetId').val());
    });
    
    $(document).on('click', '#btnAddReceiptItem', function (event) {
        event.preventDefault();
        receiptItemTab.addReceiptItem();
        receiptItemTab.populateReceiptItem();
    });


});