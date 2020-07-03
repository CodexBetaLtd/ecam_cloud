var TaxTab = function() {

	var initButton = function() {
		$('#btn-tax-view').on('click', function() {
			TaxTab.poTaxLoad();
		});
	}

	var poTaxLoad = function() {
		var $modal = $('#common-modal');
		CustomComponents.ajaxModalLoadingProgressBar();
		setTimeout(function() {
			var url = '../purchaseorder/poTaxView';
			$modal.load(url, '', function() {
				dtPurchaseOrderTax.dtPOTaxList("tax_select_tbl",
						"../restapi/tax/tabledata", "TaxTab.addTax");
				$modal.modal();
			});
		}, 1000);
	}

	var addTax = function(id, name, order, value,taxType) {
		var tax = {};
		tax['id'] = "";
		tax['valueId'] = id;
		tax['version'] = "";
		tax['valueName'] = name;
		tax['value'] = value;
		tax['order'] = order;
		tax['taxType'] = taxType;
		taxes.push(tax);
		populatePOTax();

	};

	var populatePOTax = function() {
		$("#po-tax-tbl > tbody").html("");

		if (taxes.length > 0) {
			var row, tax;
			for (row = 0; row < taxes.length; row++) {
				tax=taxes[row]
				var html = "<tr id='taxRow_"+ row+ "' >"
				+ "<input type=\"hidden\" name=\"purchaseOrderTaxDTOs["+ row+ "].id\" value='"+ tax.id+ "'>"
				+ "<input type=\"hidden\" name=\"purchaseOrderTaxDTOs["+ row+ "].version\" value='"+ tax.version+ "'>"
				+ "<input type=\"hidden\" name=\"purchaseOrderTaxDTOs["+ row+ "].valueId\" value='"+ tax.valueId+ "'>"	
				+"<td><span>" + (row+1)+ "</span></td>"
                +"<td><span>" + tax.valueName + "</span></td>"
                +"<td><span>" + tax.value + "</span></td>" 
                +"<td><span>" + tax.taxType + "</span></td>" 
                +"<td >" + tax.order + "</td>" 
				+ "<td>"+ButtonUtil.getCommonBtnDelete("TaxTab.removeTax", row) + "</td>" 
				+ "</tr>";
				$('#po-tax-tbl> tbody:last-child').append(html);
				updateItemTax();
			}
		} else {
			CustomComponents.emptyTableRow('po-tax-tbl', 7);
		}
		//setItemTaxOverride();
	};
	
	var updateItemTax=function(){
		TabItem.updateSummaryDetail();
	}
	
	var removeTax = function(index) {
		taxes.splice(index, 1);
    	populatePOTax();
	};

	return {
		init : function() {
			initButton();
			populatePOTax();
		},
		poTaxLoad : function() {
			poTaxLoad();
		},
		addTax:function(id, name, order, value,taxType){	
			addTax(id, name, order, value,taxType)
		},
		removeTax:function(index){
			removeTax(index)
		}
	};

}();
