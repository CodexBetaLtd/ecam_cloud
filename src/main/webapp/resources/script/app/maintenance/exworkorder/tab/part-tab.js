var PartTab = function () {
	
	var initButtons = function () {
		
		$('#new-wo-part-btn').on('click', function () {
			if (tasks.length > 0) {
				PartTab.partAddModal();
	        } else {
	            alert("Please Add Tasks First.");
	        }
        });		
		
	};

    var partEditModal = function (index) {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../workorder/part-add-model-view';
            $modal.load(url, '', function () {
            	PartAddModal.init();
                fillPartView(parts[index], index);
                $modal.modal();
            });
        }, 1000);
    };

    var partAddModal = function () {
    	var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
		setTimeout(function() {
            var url = '../workorder/part-add-model-view';
			$modal.load(url, '', function() {
				PartAddModal.init();
				$modal.modal();
			});
		}, 1000);
	};

    var initPartTable = function () {
        if (parts.length > 0) {
			var row, part;
            $("#wo_part_tbl > tbody").html("");
            for (row = 0; row < parts.length; row++) {
                part = parts[row];
                part['index'] = row;
                var html = "<tr id='row_" + row + "' >" +
                    "<input id='parts" + row + ".woPartId' name='parts[" + row + "].woPartId' value='" + CustomValidation.nullValueReplace(part.woPartId) + "' type='hidden'>" +
                    "<input id='parts" + row + ".woPartPartId' name='parts[" + row + "].woPartPartId' value='" + CustomValidation.nullValueReplace(part.woPartPartId) + "' type='hidden'>" +
                    "<input id='parts" + row + ".woPartPartName' name='parts[" + row + "].woPartPartName' value='" + CustomValidation.nullValueReplace(part.woPartPartName) + "' type='hidden'>" +
                    "<input id='parts" + row + ".woPartLocation' name='parts[" + row + "].woPartLocation' value='" + CustomValidation.nullValueReplace(part.woPartLocation) + "' type='hidden'>" +
                    "<input id='parts" + row + ".woPartTaskIndex' name='parts[" + row + "].woPartTaskIndex' value='" + CustomValidation.nullValueReplace(part.woPartTaskIndex) + "' type='hidden'>" +
                    "<input id='parts" + row + ".woPartTaskDescription' name='parts[" + row + "].woPartTaskDescription' value='" + CustomValidation.nullValueReplace(part.woPartTaskDescription) + "' type='hidden'>" +
                    "<input id='parts" + row + ".woPartAssetId' name='parts[" + row + "].woPartAssetId' value='" + CustomValidation.nullValueReplace(part.woPartAssetId) + "' type='hidden'>" +
                    "<input id='parts" + row + ".woPartAssetName' name='parts[" + row + "].woPartAssetName' value='" + CustomValidation.nullValueReplace(part.woPartAssetName) + "' type='hidden'>" +
                    "<input id='parts" + row + ".woPartWorkOrderId' name='parts[" + row + "].woPartWorkOrderId' value='" + CustomValidation.nullValueReplace(part.woPartWorkOrderId) + "' type='hidden'>" +
                    "<input id='parts" + row + ".woPartStockId' name='parts[" + row + "].woPartStockId' value='" + CustomValidation.nullValueReplace(part.woPartStockId) + "' type='hidden'>" +
                    "<input id='parts" + row + ".woPartEstimatedQuantity' name='parts[" + row + "].woPartEstimatedQuantity' value='" + CustomValidation.nullValueReplace(part.woPartEstimatedQuantity) + "' type='hidden'>" +
                    "<input id='parts" + row + ".woPartActualQuantity' name='parts[" + row + "].woPartActualQuantity' value='" + CustomValidation.nullValueReplace(part.woPartActualQuantity) + "' type='hidden'>" +
                    "<input id='parts" + row + ".woPartRemark' name='parts[" + row + "].woPartRemark' value='" + CustomValidation.nullValueReplace(part.woPartRemark) + "' type='hidden'>" +
					"<input id='parts" + row + ".version' name='parts[" + row + "].version' value='" + CustomValidation.nullValueReplace(part.version) + "' type='hidden' >" + 
					"<td><span>" + (row + 1) + "</span></td>" +
                    "<td ><span>" +  CustomValidation.nullValueReplace( part.woPartPartName ) + "</span></td>" +
                    "<td ><span>" + CustomValidation.nullValueReplace( part.woPartLocation ) + "</span></td>" +
                    "<td >" + CustomValidation.nullValueReplace( part.woPartEstimatedQuantity ) + "</span></td>" +
                    "<td >" + CustomValidation.nullValueReplace( part.woPartActualQuantity ) + "</span></td>" +
                    "<td ><span>" + CustomValidation.nullValueReplace( part.woPartAssetName ) + "</td>" +
                    "<td ><span>" + CustomValidation.nullValueReplace( part.woPartTaskDescription ) + "</td>" +
                    "<td style='text-align:center'>" +
	                    ButtonUtil.getCommonBtnEdit('PartTab.editPart', row) +
	                    ButtonUtil.getCommonBtnDelete('PartTab.removePart', row) +
                    "</td>" +
                    "</tr>";

                $('#wo_part_tbl > tbody:last-child').append(html);
			}
		} else {
            CustomComponents.emptyTableRow("wo_part_tbl", 8, "Please Add Part for the Work Order.")
		}
	};

    var addPart = function () {
        if ($("#woPartIndex").val() != null && $("#woPartIndex").val() != "") {
            updatePart(parts[$("#woPartIndex").val()]);
            $('#master-modal-datatable').modal('toggle');
        } else {
            newPart();
        }
        initPartTable();
    };

    var newPart = function () {
        if (isDuplicatePart()) {
            alert("Part already Added.")
        } else {
            var part = {};
            part['woPartId'] = "";
            updatePart(part);
            parts.push(part);
            $('#master-modal-datatable').modal('toggle');
        }
    };

    var updatePart = function (part) {
        CustomValidation.validateFieldNull(part, 'woPartWorkOrderId', $("#woPartWorkOrderId").val());
        CustomValidation.validateFieldNull(part, 'woPartPartId', $("#woPartPartId").val());
        CustomValidation.validateFieldNull(part, 'woPartPartName', $("#woPartPartName").val());
        CustomValidation.validateFieldNull(part, 'woPartStockId', $("#woPartStockId").val());
        CustomValidation.validateFieldNull(part, 'woPartLocation', $("#woPartLocation").val());        
		CustomValidation.validateFieldNull(part, 'woPartTaskIndex', $("#woPartTaskIndex").val());
		CustomValidation.validateFieldNull(part, 'woPartTaskDescription', $('#woPartTaskIndex').select2('data')[0].text);
        CustomValidation.validateFieldNull(part, 'woPartAssetId', $("#woPartAssetId").val());
        CustomValidation.validateFieldNull(part, 'woPartAssetName', $("#woPartAssetId :selected").text());
        CustomValidation.validateFieldNull(part, 'woPartEstimatedQuantity', $("#woPartEstimatedQuantity").val());
        CustomValidation.validateFieldNull(part, 'woPartActualQuantity', $("#woPartActualQuantity").val());
        CustomValidation.validateFieldNull(part, 'woPartRemark', $("#woPartRemark").val());
        CustomValidation.validateFieldNull(part, 'version', $("#woPartVersion").val());
    };

    var fillPartView = function (part, index) {
        $('#woPartIndex').val(index);
        $('#woPartId').val(part['woPartId']);
        $('#woPartLocation').val(part['woPartLocation']);
        $('#woPartPartId').val(part['woPartPartId']);
        $('#woPartPartName').val(part['woPartPartName']);
        $('#woPartAssetId').val(part['woPartAssetId']).trigger('change');
        $('#woPartAssetName').val(part['woPartAssetName']);
        $('#woPartTaskIndex').val(part['woPartTaskIndex']).trigger('change');        
        $('#woPartStockId').val(part['woPartStockId']);
        $('#woPartEstimatedQuantity').val(part['woPartEstimatedQuantity']);
        $('#woPartActualQuantity').val(part['woPartActualQuantity']);
        $('#woPartWorkOrderId').val(part['woPartWorkOrderId']);
        $('#woPartRemark').val(part['woPartRemark']);
        $('#woPartVersion').val(part['version']);
	};

    var isDuplicatePart = function () {
        for (var i = 0; i < parts.length; i++) {
            if ((parts[i].woPartId != $("#woPartId").val() ) || $("#woPartId").val() == "") {
                if ((parts[i].woPartStockId == $("#woPartStockId").val()) && (parts[i].woPartTaskIndex == $("#woPartTaskIndex").val())) {
                    return true;
				}
			}
		}
        return false;
	};

    var removePart = function (index) {
        parts.splice(index, 1);
        initPartTable();
	};

	return {
		
		init : function () {
			initButtons();
			initPartTable();
		},
		
		partAddModal: function () {
        	partAddModal();
        },

        addPart: function () {
            addPart();
        },       

        editPart: function (index) {
        	partEditModal(index);
        },

        removePart: function (index) {
            removePart(index);
        },

        initPartTable: function () {
        	initPartTable();
        }
        
	};

}();