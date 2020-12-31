jQuery(document).ready(function () {
    Main.init();
    ReportViewComponent.init();
	ReceiptOrderItemReportView.init();
    $(document).on('click', '#viewReceiptOrderItemReport', function (event) {
        ReceiptOrderItemDetailTable.initDataTable();
    });

});