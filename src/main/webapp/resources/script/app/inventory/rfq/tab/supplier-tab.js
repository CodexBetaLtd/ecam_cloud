var TabSupplier = function() {

	var initButtons = function() {
		$('#btn-add-rfq-supplier').on('click', function() {
			TabSupplier.rfqSupplierView();
		});

	};

	var populateRFQSupplier = function() {
		if (supplires.length > 0) {
			var row, supplier;
			$("#rfq-supplier-tbl > tbody").html("");
			for (row = 0; row < supplires.length; row++) {
				 supplires[row].itemIndex=row;
				supplier = supplires[row];
				
				var html = "<tr id='row_" + row + "' >"+
				"<input id='supplires" + row+ ".id' name='rfqSupplireDTOs[" + row + "].id' value='"+ CustomValidation.nullValueReplace(supplier.id)+ "' type='hidden'>"+
				"<input id='supplires" + row+ ".supplierId' name='rfqSupplireDTOs[" + row+ "].supplierId' value='" + CustomValidation.nullValueReplace(supplier.supplierId)+ "' type='hidden'>"+
				"<input id='supplires" + row+ ".version' name='rfqSupplireDTOs[" + row+ "].version' value='" + CustomValidation.nullValueReplace(supplier.version)+ "' type='hidden' >" +
				"<td>" + (row + 1)+"</td>" +
				"<td><span>" + supplier.supplierName + "</span></td>"+
				"<td >"+ supplier.supplierAddress + "</span></td>"+
				"<td><span>" + supplier.supplierCity + "</td>"+
				"<td>" + supplier.supplierProvince + "</span></td>"+
				"<td>" + supplier.supplierPostalCode + "</span></td>"+
				"<td>" + supplier.supplierCountry + "</span></td>"+
				"<td class='center'> "+
            	ButtonUtil.getCommonBtnDelete("TabSupplier.removeItem", row) +
				"</td>" +
				"</tr>";
				$('#rfq-supplier-tbl > tbody:last-child').append(html);
			}
		} else {
			CustomComponents.emptyTableRow("rfq-supplier-tbl", 8);
		}
	};

	var rfqSupplierView = function() {
		var $modal = $('#common-modal');
		CustomComponents.ajaxModalLoadingProgressBar();
		setTimeout(function() {
			var url = '../rfq/supplier-select-modal-view';
			$modal.load(url, '', function() {
				dtRFQSupplier.init();
				$modal.modal();
			});
		}, 1000);
	};

	var addItemToList = function(item) {
		var itemObj = {}
		if (item.itemIndex != null && item.itemIndex != ""
				&& item.itemIndex >= 0) {
			itemObj = getItemByIndex(item.itemIndex);
			setCommonDataToItemObj(item, itemObj);
		} else {
			if (items.length == 0) {
				itemObj['itemIndex'] = 0;
			} else {
				validateFieldNull(itemObj, 'itemIndex', items.length);
			}
			setCommonDataToItemObj(item, itemObj);
			items.push(itemObj);
		}
	};

	var setCommonDataToItemObj = function(updatedItemObj, itemObj) {
		validateFieldNull(itemObj, 'itemId', updatedItemObj.itemId);
		validateFieldNull(itemObj, 'itemAssetId', updatedItemObj.itemAssetId);
		validateFieldNull(itemObj, 'itemAssetName',
				updatedItemObj.itemAssetName);
		validateFieldNull(itemObj, 'itemPurchaseOrderItemId',
				updatedItemObj.itemPurchaseOrderItemId);
		validateFieldNull(itemObj, 'itemPurchaseOrderCodes',
				updatedItemObj.itemPurchaseOrderCodes);
		validateFieldNull(itemObj, 'itemQtyRequested',
				updatedItemObj.itemQtyRequested);
		validateFieldNull(itemObj, 'itemQuotedQty',
				updatedItemObj.itemQuotedQty);
		validateFieldNull(itemObj, 'itemQuotedUnitPrice',
				updatedItemObj.itemQuotedUnitPrice);
		validateFieldNull(itemObj, 'itemQuotedTotalPrice',
				updatedItemObj.itemQuotedTotalPrice);
		validateFieldNull(itemObj, 'itemDescription',
				updatedItemObj.itemDescription);

		validateFieldNull(itemObj, 'version', updatedItemObj.version);
	};

	var getItemByIndex = function(itemIndex) {
		for (var i = 0; i < items.length; i++) {
			if (items[i].itemIndex == itemIndex) {
				return items[i];
			}
		}
	};

	var getItemById = function(itemId) {
		for (var i = 0; i < items.length; i++) {
			if (items[i].itemId == itemId) {
				return items[i];
			}
		}
	};

	var addRFQSupplier = function(id,name,address,city,province,postalcode,countryName) {

		var item = {};

		item['id'] = '';
		item['version'] = ''
		item['itemIndex'] =  supplires.length;
		item['supplierId'] = CustomValidation.nullValueReplace(id);
		item['supplierName'] = CustomValidation.nullValueReplace(name);
		item['supplierAddress'] = CustomValidation.nullValueReplace(address);
		item['supplierCity'] = CustomValidation.nullValueReplace(city);
		item['supplierProvince'] = CustomValidation.nullValueReplace(province);
		item['supplierPostalCode'] = CustomValidation.nullValueReplace(postalcode);
		item['supplierCountry'] = CustomValidation.nullValueReplace(countryName);
		supplires.push(item);
		$('#common-modal').modal('toggle');

		populateRFQSupplier();
	};

	var validateFieldNull = function(obj, field, value) {
		if (value != null && value != undefined) {
			obj[field] = value;
		} else {
			obj[field] = "";
		}
	};

	var removeItem = function(itemIndex) {
		for (var i = 0; i < supplires.length; i++) {
			if (supplires[i].itemIndex == itemIndex) {
				supplires.splice(i, 1);
				populateRFQSupplier();
				break;
			}
		}
	};

	return {
		init : function() {
			initButtons();
			populateRFQSupplier();
		},

		addRFQSupplier : function(id,name,address,city,province,postalcode,countryName) {
			addRFQSupplier(id,name,address,city,province,postalcode,countryName);
		},

		removeItem : function(index) {
			removeItem(index);
		},

		/***********************************************************************
		 * Initialize Modals
		 **********************************************************************/
		rfqSupplierView : function() {
			rfqSupplierView();
		},

	};
}();