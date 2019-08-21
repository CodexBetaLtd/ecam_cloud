var TabPurchasing = function () {

    var initSupplierSelect = function () {
        $("#purchasedSupplierId").select2({
            placeholder: "Select a Supplier",
            allowClear: true
        });
    };

    var initCurrencySelect = function () {
        $("#purchasedCurrencyId").select2({
            placeholder: "Select a Currency",
            allowClear: true
        });
    };

    var initDatePicker = function () {
        $('.date-picker').datepicker({
            autoclose: true,
            container: '#picker-container'
        });
    };

    return {
    	
        init: function () {
            initSupplierSelect();
            initCurrencySelect();
            initDatePicker();
        }
    };
}();