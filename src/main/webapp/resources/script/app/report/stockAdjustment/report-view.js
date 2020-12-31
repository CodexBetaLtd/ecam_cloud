var StockAdjustmentReportView = function () {


	/*********************************************************************
	 *  Stock Adjustment Item
	 *********************************************************************/
	var runItemInput = function () {
		$("#itemName").inputClear({
			placeholder: "Select a Item",
			btnMethod: "StockAdjustmentReportView.itemView()",
		});
	};
	
	var itemView = function () {
		var $modal = $('#common-modal');
		CustomComponents.ajaxModalLoadingProgressBar();
		setTimeout(function () {
			var url = '../stockadjustment/partView';
			$modal.load(url, '', function () {
				var func = "StockAdjustmentReportView.setItem";
				dtItem.getItemDataTable(null, func);
				$modal.modal();
			});
		}, 1000);
	};
	
	var setItem = function (id, name) {
		$("#itemId").val(id);
        $("#itemName").val(name);
		$("#itemName").attr('readonly', true);
		$('#common-modal').modal("hide");
	};
    /*********************************************************************
     * Stock
     *********************************************************************/
    var runStockInput = function () {
        $("#stockCode").inputClear({
            placeholder: "Select a Stock",
            btnMethod: "StockAdjustmentReportView.stockView()",
        });
    };

    var stockView = function () {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../stockadjustment/stockView';
            $modal.load(url, '', function () {
                var dt_url = "../../restapi/stock/tabledata";
                var func = "StockAdjustmentReportView.setStock";
                dtStock.getDataTableStock(dt_url, func);
                $modal.modal();
            });
        }, 1000);
    };
    
    var setStock = function (id, name,qty) {
        $("#stockId").val(id);
        $("#stockCode").val(name);
        $("#stockCode").attr('readonly', true);
        $('#common-modal').modal("hide");
    };



    return {
        init: function () {
        	runItemInput();
        	runStockInput();
        },
        itemView: function () {
        	itemView();
        },
        setItem: function (id, name) {
        	setItem(id, name);
        },
        stockView: function () {
        	stockView();
        },
        setStock: function (id, name,qty) {
        	setStock(id, name,qty);
        },

    };
}();