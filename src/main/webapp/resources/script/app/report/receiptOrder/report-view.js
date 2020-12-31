var ReceiptOrderReportView = function () {


    /*********************************************************************
     * Receipt Order  Supplier
     *********************************************************************/
    var runSupplierInput = function () {
        $("#supplierName").inputClear({
            placeholder: "Select Supplier",
            btnMethod: "ReceiptOrderReportView.supplierView()",
        });
    };

    var supplierView = function () {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../receiptOrder/supplierView';
            $modal.load(url, '', function () {
                var dt_url = "../../restapi/supplies/getSupplierDataTable";
                var func = "ReceiptOrderReportView.setSupplier";
                dtSupplier.getSupplier(dt_url, func);
                $modal.modal();
            });
        }, 1000);
    };
    
    var setSupplier = function (id, name) {
        $("#supplierId").val(id);
        $("#supplierName").val(name);
        $("#supplierName").attr('readonly', true);
        $('#common-modal').modal("hide");
    };


    return {
    	
        init: function () {
            runSupplierInput();
        },
        
        supplierView: function () {
        	supplierView();
        },
        
        setSupplier: function (id, name) {
            setSupplier(id, name);
        },

    };
}();