jQuery(document).ready(function () {
    Main.init();
    ReportViewComponent.init();
    StockAdjustmentReportView.init();

    $(document).on('click', '#viewStockAdjustmentReport', function (event) {
        StockAdjustmentDetailTable.initDataTable();
    });
});