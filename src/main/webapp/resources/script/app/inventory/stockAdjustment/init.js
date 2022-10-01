jQuery(document).ready(function () {

    Main.init();
    StockAdjustmentAdd.init();

    /*********************************************************************
     * Stock Item Tab
     *********************************************************************/

    $(document).on('click', '#btnStockView', function (event) {
        event.preventDefault();
//        var partId = $("#partId").val();
//        StockAdjustmentAdd.getStockView(partId);
    });


});