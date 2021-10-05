var TabGeneral = function () { 

	var initView = function() {
		
		$('#roleSupplier').on('ifChecked', function () {
			$("#roleSupplier").val(true);
			$('#roleCustomer').iCheck('uncheck');
			$("#roleCustomer").val(false);  
		});
		
        $('#roleCustomer').on('ifChecked', function () {
            $("#roleSupplier").iCheck('uncheck');
            $("#roleSupplier").val(false);
			$("#roleCustomer").val(true);
        }); 
        
        $('#roleCustomer').on('ifUnchecked', function () {
        	$("#roleCustomer").val(false);
        	$("#roleSupplier").val(false);
        });
        
        $('#roleSupplier').on('ifUnchecked', function () {
        	$("#roleSupplier").val(false);
            $("#roleCustomer").val(false);
        });
        
	};
	
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
            btnMethod:"TabGeneral.addCurrency()",
        });
    };
    
    function initInputClearBusinessClassification(){
        $("#businessClassificationName").inputClear({
            placeholder:"Please specify a business classification",
            btnMethod:"TabGeneral.addBusinessClassification()",
        });
    };

    /********************************************
     * Initialize modal views
     ********************************************/
    
    function initModalBusinessClassificationSelect() {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../business/view/modal/business-classification';
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
            var url = '../business/view/modal/currencies';
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
            initView(); 
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