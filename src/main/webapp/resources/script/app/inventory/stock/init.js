jQuery(document).ready(function () {

    Main.init();
    StockAdd.init();
    NotificationTab.init();

    /*********************************************************************
     * Stock Item Tab
     *********************************************************************/

    // ToDo: Not In Used
    $(document).on('click', '#btnBusinessView', function (event) {
        event.preventDefault();
        // assetTypeView("../restapi/asset/getCustomerDataTable", "dtStockAsset.setStockBusiness","Business");
    });
    var assetTypeView = function (funcURL, func, title) {
        var $modal = $('#master-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../stock/assetView';
            $modal.load(url, '', function () {
                $("#titleText").text(title)
                dtStockAsset.dtStockAsset(funcURL, func);
                $modal.modal();
            });
        }, 1000);
    };

    $(document).on('click', '#btnItemView', function (event) {
        event.preventDefault();

    });
});