var TabAccount = function() {
    
    /********************************************
     * Initialize InputClear Components
     ********************************************/

    function initInputClearComponents(){
        initInputClearAccount();
        initInputClearDepartment();
        initInputClearCurrency();
    };
    
    function initInputClearAccount(){
        $("#accountName").inputClear({
            placeholder:"Please specify a account",
            btnMethod:"TabAccount.addAccount()",
        });
    };
    
    function initInputClearDepartment(){
        $("#chargeDepartmentName").inputClear({
            placeholder:"Please specify a department",
            btnMethod:"TabAccount.addDepartment()",
        });
    };    
    
    function initInputClearCurrency(){
        $("#purchaseCurrencyName").inputClear({
            placeholder:"Please specify a currency",
            btnMethod:"TabAccount.addCurrency()",
        });
    };

    /********************************************
     * Initialize modal views
     ********************************************/
    
    function initModalAccountSelect() {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../purchaseorder/view/modal/accounts';
            $modal.load(url, '', function () {
                DatatableModalAccounts.init(
                        "common-modal",
                        "tbl_accounts",
                        "../restapi/account/tabledata",
                        "setData"
                );
                $modal.modal();
            });
        }, 1000);
    };
    
    function initModalDepartmentSelect() {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../purchaseorder/view/modal/departments';
            $modal.load(url, '', function () {
                DatatableModalDepartments.init(
                        "common-modal",
                        "tbl_departments",
                        "../restapi/charge-department/tabledata",
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
            var url = '../purchaseorder/view/modal/currencies';
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
        
        addAccount: function () {
            initModalAccountSelect(); 
        },
        
        addDepartment: function () {
            initModalDepartmentSelect(); 
        },
        
        addCurrency: function () {
            initModalCurrencySelect(); 
        }
    };
    
}();