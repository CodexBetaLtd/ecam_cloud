var TabReceiving = function() {
    
    /********************************************
     * Initialize InputClear Components
     ********************************************/

    function initInputClearComponents(){
        initInputClearCountry();
        initInputClearShipTo();
    };
    
    function initInputClearShipTo(){
        $("#shipToId").inputClear({
            placeholder:"Please specify a ship to location",
            btnMethod:"TabReceiving.addShipTo()",
        });
    };
    
    function initInputClearCountry(){
        $("#shipToCountry").inputClear({
            placeholder:"Please specify a country",
            btnMethod:"TabReceiving.addCountry()",
        });
    };

    /********************************************
     * Initialize modal views
     ********************************************/
    
    function initModalCountrySelect() {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../rfq/view/modal/countries';
            $modal.load(url, '', function () {
                DatatableModalCountries.init(
                        "common-modal",
                        "tbl_countries",
                        "../restapi/country/tabledata",
                        "setData"
                );
                $modal.modal();
            });
        }, 1000);
    };
    
    function initModalShipToSelect() {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../rfq/view/modal/sites';
            $modal.load(url, '', function () {
                DatatableModalSites.init(
                        "common-modal",
                        "tbl_sites",
                        "../restapi/sites/tabledata",
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
        
        addCountry: function () {
            initModalCountrySelect(); 
        },
        
        addShipTo: function () {
            initModalShipToSelect(); 
        }
    };
    
}();