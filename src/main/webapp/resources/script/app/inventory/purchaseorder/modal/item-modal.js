var ItemAddModal = function () {

    /*********************************************************************
     * Assets/Part/Location/Site View
     *********************************************************************/

    var loadAssetSelectModal = function(tableId, URL, method) {
        var $modal = $('#stackable-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../purchaseorder/poAssetView';
            $modal.load(url, '', function () {
                // ItemAddModal.initAssetSelectTable(tableId, URL, method);
                dtPurchaseOrderAsset.dtPOAssetList(tableId, URL, method);
                $modal.modal();
            });
        }, 1000);
    };
	
	var assetSelectModal = function () {
		loadAssetSelectModal("asset_tbl", "../restapi/asset/parts", "ItemAddModal.setAsset");
	};
	
	var siteSelectModal = function () {
		loadAssetSelectModal("asset_tbl", "../restapi/asset/parts", "ItemAddModal.setSite");
	};
	
	var sourceAssetSelectModal = function () {
		loadAssetSelectModal("asset_tbl", "../restapi/asset/parts", "ItemAddModal.setSourceAsset");
	};


    /*********************************************************************
     * Set Asset Types
     *********************************************************************/

	var setAsset = function (id, name) {
		$('#itemAssetId').val(id);
		$('#itemAssetName').val(name);
		$('#select-pages-open-modal').modal('toggle');
	};
	
	var setSite = function (id, name) {
		$('#itemSiteId').val(id);
		$('#itemSiteName').val(name);
		$('#select-pages-open-modal').modal('toggle');
	};
	
	var setSourceAsset = function (id, name) {
		$('#itemSourceAssetId').val(id);
		$('#itemSourceAssetName').val(name);
		$('#select-pages-open-modal').modal('toggle');
	};


    /*********************************************************************
     * Set Work Order
     *********************************************************************/

    var woSelectModal = function () {
        var $modal = $('#stackable-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../purchaseorder/poWorkOrderView';
            $modal.load(url, '', function () {
                dtPurchaseOrderWorkOrders.dtPOWorkOrderList();
                $modal.modal();
            });
        }, 1000);
    };

	var setWorkOrder = function (id, code) {
		$('#itemSourceWorkOrderId').val(id);
		$('#itemSourceWorkOrderCode').val(code);
		$('#select-pages-open-modal').modal('toggle');
	};


    /*********************************************************************
     * Initialization Item Modal
     *********************************************************************/

	var runAccountSelect = function () {
        $("#itemAccountId").select2({
            placeholder: "Select a Account",
            allowClear: true,
            dropdownParent: $("#item-add-modal")
        });
    };
    
    var runChargeDepartmentSelect = function () {
        $("#itemChargeDepartmentId").select2({
            placeholder: "Select a Charge Department",
            allowClear: true,
            dropdownParent: $("#item-add-modal")
        });
    };
    
    var runItemRequiredByDatePicker = function () {
        $('#itemRequiredByDate').datepicker({
            autoclose: true,
            container: '#item-date-picker-container'
        });
    };


    return {

        init: function () {
        	runAccountSelect();
        	runChargeDepartmentSelect();
        	runItemRequiredByDatePicker();
        },


        /*********************************************************************
         * Set Asset Types
         *********************************************************************/
        poItemAssetView: function () {
            assetSelectModal();
        },

        siteSelectModal: function() {
            siteSelectModal();
        },

        sourceAssetSelectModal: function() {
            sourceAssetSelectModal();
        },

        setAsset: function(id, name){
        	setAsset(id, name);
        },
        
        setSite: function(id, name){
        	setSite(id, name);
        },
        
        setSourceAsset: function(id, name){
        	setSourceAsset(id, name);
        },


        /*********************************************************************
         * Set Work Order
         *********************************************************************/

        woSelectModal: function () {
            woSelectModal();
        },

        setWorkOrder: function(id, code){
        	setWorkOrder(id, code);
        }
    };
}();