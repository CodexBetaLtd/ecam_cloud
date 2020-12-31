jQuery(document).ready(function () {
    Main.init();
    AssetAdd.init();
    
    TabMeterReading.init();
    TabPurchasing.init();
    TabCustomerHistory.hasCustomerHistory(assetId);
    TabWarranty.init();
    TabAssetEvent.init();
    TabPersonal.init();
    TabBom.init();
    TabLog.init();
    TabFile.init();
    SparePartTab.init();
    LocationTreeView.init();
});