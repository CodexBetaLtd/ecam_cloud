var TabPurchasing = function () {

    var initDatePicker = function () {
        $('.date-picker').datepicker({
            autoclose: true,
            container: '#picker-container'
        });
    };  
    
    /*****************************************
     * Initialize InputClear Components
     *****************************************/
    
    function initInputClearComponents() {
        initInputClearSuppliers();
        initInputClearCurrency();
    };    
    
    function initInputClearSuppliers(){
        $("#purchasedSupplierName").inputClear({
            placeholder:"Please specify a supplier",
            btnMethod:"TabPurchasing.addSupplier()",
        });
    };   
    
    function initInputClearCurrency(){
        $("#purchasedCurrencyName").inputClear({
            placeholder:"Please specify a currency",
            btnMethod:"TabPurchasing.addCurrency()",
        });
    };   
    
    /*****************************************
     * Initialize Modal Views
     *****************************************/
    
    function initModalViewSupplierSelect() {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../view/modal/suppliers?title=Supplier(s)';
            $modal.load(url, '', function () {
                DatatableModalSupplier.init(
                        "common-modal", 
                        "tbl_suppliers", 
                        "../../restapi/supplier/tabledata", 
                        "setData");
                $modal.modal();
            });
        }, 1000);
    };
    
    function initModalViewCurrencySelect() {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../view/modal/currencies';
            $modal.load(url, '', function () {
                DatatableModalCurrencies.init(
                        "common-modal",
                        "tbl_currencies", 
                        "../../restapi/lookuptable/tabledatacurrency",
                        "setData");
                $modal.modal();
            });
        }, 1000);
    };

    return {
    	
        init: function () {
            initInputClearComponents();
            initDatePicker();
        },
        
        addSupplier: function () {
            initModalViewSupplierSelect();
        },
        
        addCurrency: function () {
            initModalViewCurrencySelect();
        }
    };
}();