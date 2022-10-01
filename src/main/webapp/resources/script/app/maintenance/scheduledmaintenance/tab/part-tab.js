var TabPart = function() {
	
	var initButtons = function () {
		
		$('#btn-new-scheduled-part').on('click', function () {
			if (scheduledTasks.length > 0) {
				TabPart.partAddModal();
	        } else {
	            alert("Please Add Tasks First.");
	        }
        });		
		
	};
	
	var partAddModal = function() {
		var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
		setTimeout(function() {
			var url = '../scheduledmaintenance/partmodelview';
			$modal.load(url, '', function() {
				PartAddModal.init();
				$modal.modal();
			});
		}, 1000);
	};

    var initPartHtmlTable = function () {

		if (parts.length > 0) {
			var row, part;
			$("#part_tbl > tbody").html("");

			for (row = 0; row < parts.length; row++) {
				part = parts[row];
                part['index'] = row;
                var html = "<tr id='row_" + row + "' >" +
                    "<input id='parts" + row + ".partId' name='parts[" + row + "].partId' value='" + CustomValidation.nullValueReplace(part.partId) + "' type='hidden'>" +
                    "<input id='parts" + row + ".partPartId' name='parts[" + row + "].partPartId' value='" + CustomValidation.nullValueReplace(part.partPartId) + "' type='hidden'>" +
                    "<input id='parts" + row + ".partName' name='parts[" + row + "].partName' value='" + CustomValidation.nullValueReplace(part.partName) + "' type='hidden'>" +
                    "<input id='parts" + row + ".partLocation' name='parts[" + row + "].partLocation' value='" + CustomValidation.nullValueReplace(part.partLocation) + "' type='hidden'>" +
                    "<input id='parts" + row + ".partTaskAssetId' name='parts[" + row + "].partTaskAssetId' value='" + CustomValidation.nullValueReplace(part.partTaskAssetId) + "' type='hidden'>" +
                    "<input id='parts" + row + ".partTaskAssetName' name='parts[" + row + "].partTaskAssetName' value='" + CustomValidation.nullValueReplace(part.partTaskAssetName) + "' type='hidden'>" +
                    "<input id='parts" + row + ".partTaskIndex' name='parts[" + row + "].partTaskIndex' value='" + CustomValidation.nullValueReplace(part.partTaskIndex) + "' type='hidden'>" +
                    "<input id='parts" + row + ".partTaskDescription' name='parts[" + row + "].partTaskDescription' value='" + CustomValidation.nullValueReplace(part.partTaskDescription) + "' type='hidden'>" +
                    "<input id='parts" + row + ".partScheduledMaintenanceId' name='parts[" + row + "].partScheduledMaintenanceId' value='" + CustomValidation.nullValueReplace(part.partScheduledMaintenanceId) + "' type='hidden'>" +
                    "<input id='parts" + row + ".partStockId' name='parts[" + row + "].partStockId' value='" + CustomValidation.nullValueReplace(part.partStockId) + "' type='hidden'>" +
                    "<input id='parts" + row + ".partSuggestedQuantity' name='parts[" + row + "].partSuggestedQuantity' value='" + CustomValidation.nullValueReplace(part.partSuggestedQuantity) + "' type='hidden'>" +
                    "<input id='parts" + row + ".version' name='parts[" + row + "].version' value='" + CustomValidation.nullValueReplace(part.version) + "' type='hidden' >" +
                    "<td><span>" + (row + 1) + "</span></td>" +
                    "<td class='hidden-xs'><span>" + part.partName + "</span></td>" +
                    "<td ><span>" + part.partLocation + "</span></td>" +
                    "<td class='hidden-xs'>" + part.partSuggestedQuantity + "</span></td>" +
                    "<td ><span>" + part.partTaskAssetName + "</td>" +
                    "<td class='hidden-xs'><span>" + part.partTaskDescription + "</td>" +
                    "<td class='center'>" +
                    "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
                    	ButtonUtil.getCommonBtnEdit("TabPart.editPart", part.index ) +
                    	ButtonUtil.getCommonBtnDelete("TabPart.removePart", part.index ) +
                    "</div></td></tr>";

				$('#part_tbl > tbody:last-child').append(html);
			}
		} else {
			CustomComponents.emptyTableRow("part_tbl", 6, "Please Add Part for the Scheduled Maintenance.");
		}
	};	

	var setCommonDataToPartObj = function(updatedPartObj, partObj) {
        CustomValidation.validateFieldNull(partObj, 'id', updatedPartObj.id);
        CustomValidation.validateFieldNull(partObj, 'partId', updatedPartObj.partId);
        CustomValidation.validateFieldNull(partObj, 'partPartId', updatedPartObj.partPartId);
        CustomValidation.validateFieldNull(partObj, 'partName', updatedPartObj.partName);
        CustomValidation.validateFieldNull(partObj, 'partLocation', updatedPartObj.partLocation);
        CustomValidation.validateFieldNull(partObj, 'partTaskIndex', updatedPartObj.partTaskIndex);
        CustomValidation.validateFieldNull(partObj, 'partTaskDescription', updatedPartObj.partTaskDescription);
        CustomValidation.validateFieldNull(partObj, 'partTaskAssetId', updatedPartObj.partTaskAssetId);
        CustomValidation.validateFieldNull(partObj, 'partTaskAssetName', updatedPartObj.partTaskAssetName);
        CustomValidation.validateFieldNull(partObj, 'partStockId', updatedPartObj.partStockId);
        CustomValidation.validateFieldNull(partObj, 'partScheduledMaintenanceId', updatedPartObj.partScheduledMaintenanceId);
        CustomValidation.validateFieldNull(partObj, 'partSuggestedQuantity', updatedPartObj.partSuggestedQuantity);
        CustomValidation.validateFieldNull(partObj, 'version', updatedPartObj.version);
	};

	var addPart = function() {
		var part = {};
		
		part['partId'] = $('#partId').val();
        part['index'] = $('#partIndex').val();
		part['partPartId'] = $('#partPartId').val();
		part['partName'] = $('#partName').val();
		part['partLocation'] = $('#partLocation').val();
		part['partTaskIndex'] = $('#partTaskIndex').val();
		part['partTaskDescription'] = $('#partTaskIndex').select2('data')[0].text;
		part['partTaskAssetId'] = $('#partTaskAssetId').val();
		part['partTaskAssetName'] = $('#partTaskAssetId').select2('data')[0].text;
		part['partStockId'] = $('#partStockId').val();
		part['partSuggestedQuantity'] = $('#partSuggestedQuantity').val();
        part['partScheduledMaintenanceId'] = $('#partScheduledMaintenanceId').val();
		part['version'] = $('#partVersion').val();
		
		if (!checkDuplicatePart(part)) {
			 addPartToList(part);		        
		     initPartHtmlTable();
		     $('#master-modal-datatable').modal('toggle');
        } else {
            alert('Duplicate part !');
        }   
        
	};
	
	var addPartToList = function(part) {
        var partObj = {};	
        if (part.index != null && part.index != "" && part.index >= 0) {
        	partObj = parts[part.index]
			setCommonDataToPartObj(part, partObj);        	          
		} else {
			setCommonDataToPartObj(part, partObj);
			parts.push(partObj);			
		}
	};

    var editPart = function (index) {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
		setTimeout(function() {
			var url = '../scheduledmaintenance/partmodelview';
			$modal.load(url, '', function() {
                var part = parts[index];
				PartAddModal.init();
				fillPartEditForm(part);
				$modal.modal();
			});
		}, 1000);
	};

	var fillPartEditForm = function(part) {
        $('#partId').val(part['partId']);
        $('#partIndex').val(part['index']);
        $('#partName').val(part['partName']);
        $('#partLocation').val(part['partLocation']);
        $('#partPartId').val(part['partPartId']);
        $('#partTaskAssetId').val(part['partTaskAssetId']).trigger('change');
        $('#partTaskIndex').val(part['partTaskIndex']).trigger('change');       
        $('#partStockId').val(part['partStockId']);
        $('#partSuggestedQuantity').val(part['partSuggestedQuantity']);
        $('#partScheduledMaintenanceId').val(part['partScheduledMaintenanceId']);
		$('#partVersion').val(part['version']);
	};

	var checkDuplicatePart = function(part) {
        for (var i = 0; i < parts.length; i++) {
            if ((parts[i].index != part.index ) || part.index == "") {
                if ((parts[i].partStockId == part.partStockId) && (parts[i].partAssetId == part.partAssetId) 
                		&&  (parts[i].partTaskIndex == part.partTaskIndex)) {
                    return true;
				}
			}
		}
        return false;
	};

    var removePart = function (index) {
        parts.splice(index, 1);
        initPartHtmlTable();
	};

	return {

        init: function () {
        	initButtons();
            initPartHtmlTable();
        },

		/***********************************************************************
		 * Part Add
		 **********************************************************************/
		addPart : function() {
			addPart();
		},

        checkDuplicatePart: function (part) {
			checkDuplicatePart(part);
		},

		addPartToList : function(part) {
			addPartToList(part);
		},

		removePart : function(index) {
			removePart(index);
		},

		editPart : function(index) {
			editPart(index);
		},

        initPartHtmlTable: function () {
            initPartHtmlTable();
		},

        /***********************************************************************
		 * Initialize Modals
		 **********************************************************************/
		partAddModal : function() {
			partAddModal();
        }
	};
}();