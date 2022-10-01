var GeneralTab = function () {

    /********************************************
     * Initialize InputClear Components
     ********************************************/

    function initInputClearComponents(){
        initInputClearCurrency()
        initInputClearBusinessClassification()
    };
    
    function initInputClearCurrency(){
        $("#currencyName").inputClear({
            placeholder:"Please specify a currency",
            btnMethod:"GeneralTab.addCurrency()",
        });
    };
    
    function initInputClearBusinessClassification(){
        $("#businessClassificationName").inputClear({
            placeholder:"Please specify a business classification",
            btnMethod:"GeneralTab.addBusinessClassification()",
        });
    };

    /********************************************
     * Initialize modal views
     ********************************************/
    
    function initModalBusinessClassificationSelect() {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../supplier/view/modal/business-classification';
            $modal.load(url, '', function () {
                DatatableModalBusinessClassifications.init(
                        "common-modal",
                        "tbl_business_classifications",
                        "../restapi/business-classification/tabledata",
                        "setData"
                );
                $modal.modal();
            });
        }, 1000);
    };
    
    function initModalCurrencySelect() {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../supplier/view/modal/currencies';
            $modal.load(url, '', function () {
                DatatableModalCurrencies.init(
                        "common-modal",
                        "tbl_currencies",
                        "../restapi/currency/tabledata",
                        "setData"
                        );
                $modal.modal();
            });
        }, 1000);
    };

    return {
        init: function () {
            initInputClearComponents();
        },
        
        addBusinessClassification: function () {
            initModalBusinessClassificationSelect(); 
        },
        
        addCurrency: function () {
            initModalCurrencySelect(); 
        }
    };
}();