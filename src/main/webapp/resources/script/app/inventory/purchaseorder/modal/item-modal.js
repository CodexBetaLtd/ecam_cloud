var ItemAddModal = function () {
    /*********************************************************************
     * Assets/Part/Location/Site View
     *********************************************************************/
var initButton=function(){
	$('#btn-item-tax-view').on('click', function() {
		loadTaxSelectModal();
	});
	
	$('#btn-add-po-item').on('click', function() {
		
		poItemtax=poItemTaxes
        TabItem.addPOItem();
        TabItem.populatePOItems();
        
	});

}
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
    var loadTaxSelectModal = function(tableId, URL, method) {
        var $modal = $('#stackable-modal');
		CustomComponents.ajaxModalLoadingProgressBar();
		setTimeout(function() {
			var url = '../purchaseorder/poTaxView';
			$modal.load(url, '', function() {
				dtPurchaseOrderTax.dtPOTaxList("tax_select_tbl",
						"../restapi/tax/tabledata", "ItemAddModal.setPOItem");
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

var setPOItem=  function(id, name, order, value,taxType){
	var tax = {};
	tax['id'] = "";
	tax['valueId'] = id;
	tax['version'] = "";
	tax['valueName'] = name;
	tax['value'] = value;
	tax['order'] = order;
	tax['taxType'] = taxType;
	poItemTaxes.push(tax);
	populatePOItemTax();
}
var poItemTaxes=[];


var populatePOItemTax = function() {
	$("#po-item-tax-tbl > tbody").html("");
	if (poItemTaxes != undefined && poItemTaxes.length > 0) {
		var row, tax;
		for (row = 0; row < poItemTaxes.length; row++) {        
			tax=poItemTaxes[row]
			var html = "<tr id='taxRow_"+ row+ "' >"
			+ "<input type=\"hidden\" id=\"poItemTax["+ row+ "].id\" value='"+ tax.id+ "'>"
			+ "<input type=\"hidden\" id=\"poItemTax["+ row+ "].version\" value='"+ tax.version+ "'>"
			+ "<input type=\"hidden\" id=\"poItemTax["+ row+ "].valueId\" value='"+ tax.valueId+ "'>"	
			+"<td><span>" + (row+1)+ "</span></td>"
            +"<td><span>" + tax.valueName + "</span></td>"
            +"<td><span>" + tax.value + "</span></td>" 
            +"<td><span>" + tax.taxType + "</span></td>" 
            +"<td >" + tax.order + "</td>" 
			+ "<td>"+ButtonUtil.getCommonBtnDelete("TaxTab.removeTax", row) + "</td>" 
			+ "</tr>";
			$('#po-item-tax-tbl > tbody:last-child').append(html);
			//updateItemTax();
		}
	} else {
		CustomComponents.emptyTableRow('po-item-tax-tbl', 7);
	}
	//setItemTaxOverride();
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
            dropdownParent: $("#common-modal")
        });
    };
    
    var runChargeDepartmentSelect = function () {
        $("#itemChargeDepartmentId").select2({
            placeholder: "Select a Charge Department",
            allowClear: true,
            dropdownParent: $("#common-modal")
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
        	initButton();
        	
        	populatePOItemTax();
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
        setPOItem:function(id, name, order, value,taxType){
        	setPOItem(id, name, order, value,taxType)
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