jQuery(document).ready(function () {
    Main.init();
    ReportViewComponent.init();
	ReceiptOrderReportView.init();
	
    $(document).on('click', '#viewReceiptOrderReport', function (event) {
        ReceiptOrderDetailTable.initDataTable();
    });

});