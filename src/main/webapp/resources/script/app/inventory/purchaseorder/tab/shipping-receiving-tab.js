
var TabShippingReceiving = function() {
    
    /********************************************
     * Initialize InputClear Components
     ********************************************/

    function initInputClearComponents(){
        initInputClearSupplierCountry();
        initInputClearSiteCountry();
        initInputClearBillingCountry();
        initInputClearSupplier();
        initInputClearShipTo();
        initInputClearBillTo();
    };
    
    function initInputClearSupplierCountry(){
        $("#supplierCountryName").inputClear({
            placeholder:"Please specify a country",
            btnMethod:"TabShippingReceiving.addSupplierCountry()",
        });
    };
    
    function initInputClearSiteCountry(){
        $("#shipToCountryName").inputClear({
            placeholder:"Please specify a country",
            btnMethod:"TabShippingReceiving.addShipToCountry()",
        });
    };
    
    function initInputClearBillingCountry(){
        $("#billToCountryName").inputClear({
            placeholder:"Please specify a country",
            btnMethod:"TabShippingReceiving.addBillToCountry()",
        });
    };
    
    function initInputClearSupplier(){
        $("#supplierName").inputClear({
            placeholder:"Please specify a ship to location",
            btnMethod:"TabShippingReceiving.addSupplier()",
        });
    };
    
    function initInputClearShipTo(){
        $("#shipToName").inputClear({
            placeholder:"Please specify a ship to location",
            btnMethod:"TabShippingReceiving.addShipTo()",
        });
    };
    
    function initInputClearBillTo(){
        $("#billToName").inputClear({
            placeholder:"Please specify a ship to location",
            btnMethod:"TabShippingReceiving.addBillTo()",
        });
    };

    /********************************************
     * Initialize modal views
     ********************************************/
    
    function addSupplierCountry() {
        initModalCountrySelect("setSupplierCountry")
    };
    
    function addShipToCountry() {
        initModalCountrySelect("setShipToCountry")
    };
    
    function addBillToCountry() {
        initModalCountrySelect("setBillToCountry")
    };
    
    function initModalCountrySelect(func) {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../purchaseorder/view/modal/countries';
            $modal.load(url, '', function () {
                DatatableModalCountries.init(
                        "common-modal",
                        "tbl_countries",
                        "../restapi/country/tabledata",
                        func
                );
                $modal.modal();
            });
        }, 1000);
    };
    
    function initModalSupplierSelect() {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../purchaseorder/view/modal/suppliers';
            $modal.load(url, '', function () {
                DatatableModalSuppliers.init(
                        "common-modal",
                        "tbl_suppliers",
                        "../restapi/supplier/tabledata",
                        "setData"
                );
                $modal.modal();
            });
        }, 1000);
    };
    
    function addShipTo() {
        initModalSiteSelect("setShipTo");
    };
    
    function addBillTo() {
        initModalSiteSelect("setBillTo")
    };
    
    function initModalSiteSelect(func) {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../purchaseorder/view/modal/sites';
            $modal.load(url, '', function () {
                DatatableModalSites.init(
                        "common-modal",
                        "tbl_sites",
                        "../restapi/sites/tabledata",
                        func
                );
                $modal.modal();
            });
        }, 1000);
    };

    return {
        init: function () {
            initInputClearComponents();
        },
        
        addSupplierCountry: function () {
            addSupplierCountry(); 
        },
        
        addShipToCountry: function () {
            addShipToCountry(); 
        },
        
        addBillToCountry: function () {
            addBillToCountry(); 
        },
        
        addSupplier: function () {
            initModalSupplierSelect(); 
        },
        
        addShipTo: function () {
            addShipTo(); 
        },
        
        addBillTo: function () {
            addBillTo(); 
        }
    };
    
}();