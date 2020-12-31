/*********************************************************************
 * Work Order Miscellaneous
 *********************************************************************/
var MiscCostTab = function () {
	
	var initButtons = function () {
		
		$('#new-misc-cost-btn').on('click', function () {
			MiscCostTab.miscCostAddModal();
        });
		
	};   

    var miscCostAddModal = function () {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../workorder/mis-cost-add-modal-view';
            $modal.load(url, '', function () {
            	MiscCostAddModal.init();
                $modal.modal();
            });
        }, 1000);
    };
    
    var miscCostEditModal = function (index) {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../workorder/mis-cost-add-modal-view';
            $modal.load(url, '', function () {
            	MiscCostAddModal.init();
                setMiscData(index);
                $modal.modal();
            });
        }, 1000);
    };

    var initMiscCostTable = function () {
        $('#tbl_misc_cost >tbody').html("");
        if (miscellaneousExpenses.length > 0) {
            $.each(miscellaneousExpenses, function (index, expense) {
            	expense['index'] = index;
                var newRow = "<tr> " +
                    "<input type='hidden' name='miscellaneousExpenses[" + index + "].id' value='" + CustomValidation.nullValueReplace(expense.id) + "'> " +
                    "<input type='hidden' name='miscellaneousExpenses[" + index + "].version' value='" + CustomValidation.nullValueReplace(expense.version) + "'> " +
                    "<input type='hidden' name='miscellaneousExpenses[" + index + "].miscellaneousExpenseTypeId' value='" + CustomValidation.nullValueReplace(expense.miscellaneousExpenseTypeId) + "'> " +
                    "<input type='hidden' name='miscellaneousExpenses[" + index + "].description' value='" + CustomValidation.nullValueReplace(expense.description) + "'> " +
                    "<input type='hidden' name='miscellaneousExpenses[" + index + "].estimatedQuantity' value='" + CustomValidation.nullValueReplace(expense.estimatedQuantity) + "'> " +
                    "<input type='hidden' name='miscellaneousExpenses[" + index + "].estimatedUnitCost' value='" + CustomValidation.nullValueReplace(expense.estimatedUnitCost) + "'> " +
                    "<input type='hidden' name='miscellaneousExpenses[" + index + "].estimatedTotalCost' value='" + CustomValidation.nullValueReplace(expense.estimatedTotalCost) + "'> " +
                    "<input type='hidden' name='miscellaneousExpenses[" + index + "].actualQuantity' value='" + CustomValidation.nullValueReplace(expense.actualQuantity) + "'> " +
                    "<input type='hidden' name='miscellaneousExpenses[" + index + "].actualUnitCost' value='" + CustomValidation.nullValueReplace(expense.actualUnitCost) + "'> " +
                    "<input type='hidden' name='miscellaneousExpenses[" + index + "].actualTotalCost' value='" + CustomValidation.nullValueReplace(expense.actualTotalCost) + "'> " +
                    "<td> " + (index + 1) + "</td>" +
                    "<td> " + expense.miscellaneousExpenseTypeDescription + "</td>" +
                    "<td> " + expense.description + "</td>" +
                    "<td> " + expense.estimatedQuantity + "</td>" +
                    "<td> " + expense.estimatedUnitCost + "</td>" +
                    "<td> " + expense.estimatedTotalCost + "</td>" +
                    "<td> " + expense.actualQuantity + "</td>" +
                    "<td> " + expense.actualUnitCost + "</td>" +
                    "<td> " + expense.actualTotalCost + "</td>" +
                    "<td style='text-align:center'>" +
                    ButtonUtil.getCommonBtnEdit('MiscCostTab.miscCostEditModal', index) +
                    ButtonUtil.getCommonBtnDelete('MiscCostTab.deleteMiscCost', index) +
                    "</td>" +
                    "</tr>";
                $("#tbl_misc_cost > tbody").append(newRow);
            });
        } else {
            CustomComponents.emptyTableRow("tbl_misc_cost", 10, "Please Add Miscellaneous Cost for Work Order.");
        }
    };


    var addMiscCost = function () {
        if ( $("#miscellaneousExpenseIndex").val() != null && $("#miscellaneousExpenseIndex").val() != "") {
            updateArrayObject(miscellaneousExpenses[$("#miscellaneousExpenseIndex").val()]);
        } else {
            addArrayObject();
        }
        initMiscCostTable();
        $('#master-modal-datatable').modal("toggle");
    };

    var addArrayObject = function () {
        var miscCost = {};
        
        miscCost['id'] = "";
        miscCost['index'] = "";
        miscCost['version'] = "";
        miscCost['miscellaneousExpenseTypeId'] = $('#miscellaneousExpenseType').val();
        miscCost['miscellaneousExpenseTypeDescription'] = $('#miscellaneousExpenseType :selected').text();
        miscCost['description'] = $("#miscellaneousExpenseDescription").val();
        miscCost['estimatedUnitCost'] = $("#miscellaneousExpenseEstUnitCost").val();
        miscCost['estimatedQuantity'] = $("#miscellaneousExpenseEstQuantity").val();
        miscCost['estimatedTotalCost'] = $("#miscellaneousExpenseEstTotal").val();
        miscCost['actualUnitCost'] = $("#miscellaneousExpenseActualCost").val();
        miscCost['actualQuantity'] = $("#miscellaneousExpenseActualQuantity").val();
        miscCost['actualTotalCost'] = $("#miscellaneousExpenseActualTotal").val();
        
        addWOMiscCostToList(miscCost);
    };

    var updateArrayObject = function (miscCost) {
        miscCost['miscellaneousExpenseTypeId'] = $("#miscellaneousExpenseType").val();
        miscCost['miscellaneousExpenseTypeDescription'] = $("#miscellaneousExpenseType :selected").text();
        miscCost['description'] = $("#miscellaneousExpenseDescription").val();
        miscCost['estimatedUnitCost'] = $("#miscellaneousExpenseEstUnitCost").val();
        miscCost['estimatedQuantity'] = $("#miscellaneousExpenseEstQuantity").val();
        miscCost['estimatedTotalCost'] = $("#miscellaneousExpenseEstTotal").val();
        miscCost['actualUnitCost'] = $("#miscellaneousExpenseActualCost").val();
        miscCost['actualQuantity'] = $("#miscellaneousExpenseActualQuantity").val();
        miscCost['actualTotalCost'] = $("#miscellaneousExpenseActualTotal").val();
    };    
    
    var setMiscData = function(index){
        var miscExpense = miscellaneousExpenses[index];
        
    	$("#miscellaneousExpenseIndex").val(index);
        $("#miscellaneousExpenseId").val(miscExpense.id);
        $("#miscellaneousExpenseVersion").val(miscExpense.version);
        $("#miscellaneousExpenseType").val(miscExpense.miscellaneousExpenseTypeId).trigger('change');
        $("#miscellaneousExpenseDescription").val(miscExpense.description);
        $("#miscellaneousExpenseEstQuantity").val(miscExpense.estimatedQuantity);
        $("#miscellaneousExpenseEstUnitCost").val(miscExpense.estimatedUnitCost);
        $("#miscellaneousExpenseEstTotal").val(miscExpense.estimatedTotalCost);
        $("#miscellaneousExpenseActualQuantity").val(miscExpense.actualQuantity);
        $("#miscellaneousExpenseActualCost").val(miscExpense.actualUnitCost);
        $("#miscellaneousExpenseActualTotal").val(miscExpense.actualTotalCost);
    };    

    var deleteMiscCost = function (index) {
    	miscellaneousExpenses.splice(index, 1);
    	initMiscCostTable();
    };
    
    var addWOMiscCostToList = function (wOMiscCost) {
        var wOMiscCostObj = {};
        if (wOMiscCost.index != null && wOMiscCost.index != "" && wOMiscCost.index >= 0) {
            wOMiscCostObj = miscellaneousExpenses[wOMiscCost.index];
            setCommonDataToWOMiscCostObj(wOMiscCost, wOMiscCostObj);
        } else {
            setCommonDataToWOMiscCostObj(wOMiscCost, wOMiscCostObj);
            miscellaneousExpenses.push(wOMiscCostObj);
        }
    };

    var setCommonDataToWOMiscCostObj = function (wOMiscCost, wOMiscCostObj) {

    	CustomValidation.validateFieldNull(wOMiscCostObj, 'id', wOMiscCost.id);
    	CustomValidation.validateFieldNull(wOMiscCostObj, 'miscellaneousExpenseTypeId', wOMiscCost.miscellaneousExpenseTypeId);
    	CustomValidation.validateFieldNull(wOMiscCostObj, 'miscellaneousExpenseTypeDescription', wOMiscCost.miscellaneousExpenseTypeDescription);
    	CustomValidation.validateFieldNull(wOMiscCostObj, 'description', wOMiscCost.description);
    	CustomValidation.validateFieldNull(wOMiscCostObj, 'estimatedUnitCost', wOMiscCost.estimatedUnitCost);
    	CustomValidation.validateFieldNull(wOMiscCostObj, 'estimatedQuantity', wOMiscCost.estimatedQuantity);
    	CustomValidation.validateFieldNull(wOMiscCostObj, 'estimatedTotalCost', wOMiscCost.estimatedTotalCost);
    	CustomValidation.validateFieldNull(wOMiscCostObj, 'actualUnitCost', wOMiscCost.actualUnitCost);
    	CustomValidation.validateFieldNull(wOMiscCostObj, 'actualQuantity', wOMiscCost.actualQuantity);
    	CustomValidation.validateFieldNull(wOMiscCostObj, 'actualTotalCost', wOMiscCost.actualTotalCost);
    	CustomValidation.validateFieldNull(wOMiscCostObj, 'version', wOMiscCost.version);

    };

    return {
    	
    	init: function () {
    		initButtons();
    		initMiscCostTable();
    	},

    	miscCostAddModal: function () {
        	miscCostAddModal();
        },
        
        addMiscCost: function () {
            addMiscCost();
        },
        
        miscCostEditModal: function (index) {
        	miscCostEditModal(index);
        },
        
        deleteMiscCost: function (index) {
        	deleteMiscCost(index);
        },
        
        addWOMiscCostToList: function(miscCostObj){
        	addWOMiscCostToList(miscCostObj);
        }
    };
}();


