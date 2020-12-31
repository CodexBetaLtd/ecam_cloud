jQuery(document).ready(function () {

    Main.init();
    PartAdd.init();
    
    StockTab.init();
    LogTab.init();
    WarrantyTab.init();    
    BomTab.init();
    NotificationTab.init();
    
    $('#btn-new-bom').on('click', function () {
    	BomTab.assetSelectModal();
    });

    /***************************************************************************
     * init Load
     * *************************************************************************/
    if ($("#partId").val() !== undefined || $("#partId").val() !== "" || $("#partId").val() !== " ") {
        StockTab.stockLevelDetails();
//        POTab.viewOpenPOs();
    };
    
});