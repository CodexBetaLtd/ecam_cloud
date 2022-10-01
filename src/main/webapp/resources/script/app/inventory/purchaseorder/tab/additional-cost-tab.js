var additionalCostAddModal = function () {

    var runAdditionalCostTypeSelect = function () {
        $("#poAdditionalCostTypeId").select2({
            placeholder: "Select Additional cost Type",
            dropdownParent: $("#divadditionalcosttype"),
            allowClear: true,
        });
    };

    var runShippingTypeSelect = function () {
        $("#shippingTypeId").select2({
            placeholder: "Select a shipping type",
            dropdownParent: $("#divshippingType"),
            allowClear: true,
        });
    };

    var initCheckbox = function () {
        $("input[type='checkbox']").iCheck({
            checkboxClass: 'icheckbox_minimal'
        });
    };

    return {
        init: function () {
            runAdditionalCostTypeSelect();
            runShippingTypeSelect();
            initCheckbox();
        }
    };

}();


var tabAdditionalCost = function () {

    var getAdditionalCostModalView = function () {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        // $('body').modalmanager('loading');
        setTimeout(function () {
            var url = '../purchaseorder/poAdditionalCostView';
            $modal.load(url, function () {
                additionalCostAddModal.init();
                $modal.modal();
            });

        }, 1000);

    };

    var editAdditionalCost = function (index) {
        console.log('index ' + index);

        var $modal = $('#common-modal');
        // $('body').modalmanager('loading');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../purchaseorder/poAdditionalCostView';
            $modal.load(url, function () {
                additionalCostAddModal.init();
                setAdditionalCost(purchaseOrderAdditionalCosts[index]);
                $modal.modal();
            });
        }, 1000);
    };


    var addAdditionalCost = function () {
        var additionalCost = {};
        additionalCost['id'] = $('#additionalCostId').val();
        additionalCost['version'] = $('#additionalCostVersion').val();
        additionalCost['additionalCostIndex'] = $('#additionalCostIndex').val();
        
        additionalCost['additionalCostName'] = $('#additionalCostName').val();
        additionalCost['additionalCostDescription'] = $('#additionalCostDescription').val();
        additionalCost['amount'] = $('#additionalCostAmount').val();
        additionalCost['taxRate'] = $('#additionalCostTaxRate').val();

        additionalCost['additionalCostTypeId'] = $('#poAdditionalCostTypeId').val();
        additionalCost['additionalCostTypeName'] = $('#poAdditionalCostTypeId option:selected').text().trim();

        additionalCost['shippingTypeId'] = $('#shippingTypeId').val();
        additionalCost['shippingTypeName'] = $('#shippingTypeId option:selected').text().trim();

        additionalCost['isOverridePoItemTax'] = $("#isOverridePoItemTax").prop("checked") == true ? true : false;

        // purchaseOrderAdditionalCosts.push(additionalCost);
        if (!isAdditionalCostAlreadyAdd(additionalCost)) {
            addAdditionalCostToList(additionalCost);
            //   purchaseOrderAdditionalCosts.push(additionalCost);
        } else {
            //addAdditionalCostToList(additionalCost);

            alert('Duplicate Addittional cost type !');
        }


    };

    var initVariables = function (additionalCost) {
        additionalCost['id'] = "";
        additionalCost['version'] = "";
        additionalCost['additionalCostName'] = "";
        additionalCost['taxRate'] = "";
        additionalCost['amount'] = "";
        additionalCost['additionalCostTypeName'] = "";
        additionalCost['additionalCostTypeId'] = "";
        additionalCost['shippingTypeId'] = "";
        additionalCost['shippingTypeName'] = "";
        additionalCost['description'] = "";
        additionalCost['isOverridePoItemTax'] = "";
    };

    var additionalCostType = function () {
        var costTypeId = $('#poAdditionalCostTypeId').val();
        console.log(costTypeId);
        if (costTypeId == 1) {
            $("#divshippingType").hide();
            $("#divoverrideTax").show();
        }
        else if (costTypeId == 2) {
            $(" #divoverrideTax").hide();
            $("#shippingTypeId").select2({
                placeholder: "Select a Shipping Type",
                allowClear: true,
                dropdownParent: $("#divshippingType")
            });
            $("#divshippingType").show();
        }
        else {
            $("#divoverrideTax").hide();
            $("#divshippingType").hide();
        }
    };


    var addAdditionalCostToList = function (additionalCost) {
        var additionalCostObj = {}
        initVariables(additionalCostObj);
        if (additionalCost.additionalCostIndex != null && additionalCost.additionalCostIndex != "" && additionalCost.additionalCostIndex >= 0) {
            additionalCostObj = getAdditionalCostByIndex(additionalCost.additionalCostIndex);
            setCommonDataToAdditionalCostObj(additionalCost, additionalCostObj);
        } else {
            if (purchaseOrderAdditionalCosts.length == 0) {
                additionalCostObj['additionalCostIndex'] = 0;
            } else {
                validateFieldNull(additionalCostObj, 'additionalCostIndex', purchaseOrderAdditionalCosts.length);
            }
            setCommonDataToAdditionalCostObj(additionalCost, additionalCostObj);
            purchaseOrderAdditionalCosts.push(additionalCostObj);
        }


    };


    var populateAdditionalCost = function () {
        $("#tbl_po_additional_cost > tbody").html("");
        totalTax = 0;
        additionalTotalCost = 0;
        if (purchaseOrderAdditionalCosts.length > 0) {
            var row, additionalCost;
            for (row = 0; row < purchaseOrderAdditionalCosts.length; row++) {
                //purchaseOrderAdditionalCosts[row].additionalCostIndex = row;
                additionalCost = purchaseOrderAdditionalCosts[row];
            setTotalAdditionalCost(additionalCost);
            if (additionalCost['shippingTypeId'] == null) {
                additionalCost['shippingTypeId'] = "";
            }
            if (additionalCost['shippingTypeName'] == null) {
                additionalCost['shippingTypeName'] = "";
            }
            var html = "<tr id='additionalCostRow_" + row + "' >" +
                "<input type=\"hidden\" name=\"additionalCostDTOs[" + row + "].id\" value='" + additionalCost['id'] + "'>" +
                "<input type=\"hidden\" name=\"additionalCostDTOs[" + row + "].additionalCostTypeName\" value=\"" + additionalCost['additionalCostTypeName'] + "\">" +
                "<input type=\"hidden\" name=\"additionalCostDTOs[" + row + "].additionalCostTypeId\" value=\"" + additionalCost['additionalCostTypeId'] + "\">" +
                "<input type=\"hidden\" name=\"additionalCostDTOs[" + row + "].additionalCostName\" value=" + additionalCost['additionalCostName'] + ">" +
                "<input type=\"hidden\" name=\"additionalCostDTOs[" + row + "].shippingTypeId\" value=" + additionalCost['shippingTypeId'] + ">" +
                "<input type=\"hidden\" name=\"additionalCostDTOs[" + row + "].shippingTypeName\" value=" + additionalCost['shippingTypeName'] + ">" +
                "<input type=\"hidden\" name=\"additionalCostDTOs[" + row + "].amount\" value=" + additionalCost['amount'] + ">" +
                "<input type=\"hidden\" name=\"additionalCostDTOs[" + row + "].taxRate\" value=" + additionalCost['taxRate'] + ">" +
                "<input type=\"hidden\" name=\"additionalCostDTOs[" + row + "].isOverridePoItemTax\" value=" + additionalCost['isOverridePoItemTax'] + ">" +
                "<td>" + (row + 1) + "</td>" +
                "<td>" + additionalCost['additionalCostName'] + "</td>" +
                "<td>" + additionalCost['additionalCostTypeName'] + "</td>" +
                "<td>" + additionalCost['shippingTypeName'] + "</td>" +
                "<td>" + additionalCost['taxRate'] + "</td>" +
                "<td>" + additionalCost['amount'] + "</td>" +
                "<td>" + ButtonUtil.getBtnGroup(row, "btnEditClsAdditionalCost", row, "btnDelClsAdditionalCost") + "</td>" +
                "</tr>";
            $('#tbl_po_additional_cost> tbody:last-child').append(html);

            }
        }
        else {
            CustomComponents.emptyTableRow('tbl_po_additional_cost', 7);
        }
        setItemTaxOverride();
    };


    var removeAdditionalCost = function (index) {
        removeAdditionalTotalCost(purchaseOrderAdditionalCosts[index].additionalCostTypeId, purchaseOrderAdditionalCosts[index].amount)
        purchaseOrderAdditionalCosts.splice(index, 1);
    };

    var setCommonDataToAdditionalCostObj = function (updatedAdditionalCostObj, additionalCostObj) {
        validateFieldNull(additionalCostObj, 'id', updatedAdditionalCostObj.id);
        validateFieldNull(additionalCostObj, 'version', updatedAdditionalCostObj.version);
        validateFieldNull(additionalCostObj, 'additionalCostName', updatedAdditionalCostObj.additionalCostName);
        validateFieldNull(additionalCostObj, 'taxRate', updatedAdditionalCostObj.taxRate);
        validateFieldNull(additionalCostObj, 'amount', updatedAdditionalCostObj.amount);
        validateFieldNull(additionalCostObj, 'additionalCostTypeName', updatedAdditionalCostObj.additionalCostTypeName);
        validateFieldNull(additionalCostObj, 'additionalCostTypeId', updatedAdditionalCostObj.additionalCostTypeId);
        validateFieldNull(additionalCostObj, 'shippingTypeId', updatedAdditionalCostObj.shippingTypeId);
        validateFieldNull(additionalCostObj, 'shippingTypeName', updatedAdditionalCostObj.shippingTypeName);
        validateFieldNull(additionalCostObj, 'isOverridePoItemTax', updatedAdditionalCostObj.isOverridePoItemTax);
    };

    var setAdditionalCost = function (additionalCost) {

        $('#additionalCostIndex').val(additionalCost['additionalCostIndex']);
        $('#additionalCostId').val(additionalCost['id']);
        $('#additionalCostVersion').val(additionalCost['version']);

        $('#additionalCostName').val(additionalCost['additionalCostName']);
        $('#additionalCostDescription').val(additionalCost['additionalCostDescription']);
        $('#additionalCostAmount').val(additionalCost['amount']);
        $('#additionalCostTaxRate').val(additionalCost['taxRate']);

        $('#poAdditionalCostTypeId').val(additionalCost['additionalCostTypeId']).trigger('change');
        $('#shippingTypeId').val(additionalCost['shippingTypeId']).trigger('change');
        setTaxOverride(additionalCost['isOverridePoItemTax']);

    };


    var validateFieldNull = function (obj, field, value) {
        if (value != null && value != undefined) {
            obj[field] = value;
        } else {
            obj[field] = "";
        }
    };

    var updateIndexes = function () {
        for (var i = 0; i < purchaseOrderAdditionalCosts.length; i++) {
            purchaseOrderAdditionalCosts[i].additionalCostIndex = i;
        }
    };


    var getAdditionalCostByIndex = function (additionalCostIndex) {
        for (var i = 0; i < purchaseOrderAdditionalCosts.length; i++) {
            if (purchaseOrderAdditionalCosts[i].additionalCostIndex == additionalCostIndex) {
                return purchaseOrderAdditionalCosts[i];
            }
        }
    };

    var isAdditionalCostAlreadyAdd = function (additionalCost) {
        for (var i = 0; i < purchaseOrderAdditionalCosts.length; i++) {
            if (purchaseOrderAdditionalCosts[i].additionalCostIndex != additionalCost.additionalCostIndex) {
                if (purchaseOrderAdditionalCosts[i].additionalCostTypeId != additionalCost.additionalCostTypeId) {
                    return true;
                }
            }
        }
        return false;

    };


    var setTotalAdditionalCost = function (additionalCost) {
        if (additionalCost.additionalCostTypeId == 1 && additionalCost.isOverridePoItemTax) {
            totalTax = parseFloat(totalTax) + parseFloat(additionalCost.amount);
        } else if (additionalCost.additionalCostTypeId != 1) {
            additionalTotalCost = parseFloat(additionalTotalCost) + parseFloat(additionalCost.amount);
        } else {
            additionalTotalCost = parseFloat(additionalTotalCost)
        }
        TabItem.populatePOItems();
    }

    var removeAdditionalTotalCost = function (additionalCostTypeId, amount) {
        if (additionalCostTypeId == 1 && totalTax != 0 && isOverridePoItemTax == true) {
            totalTax = parseFloat(totalTax) - parseFloat(amount);
            isOverridePoItemTax = false;
        } else if (additionalCostTypeId != 1 && additionalTotalCost != 0) {
            additionalTotalCost = parseFloat(additionalTotalCost) - parseFloat(amount);
        }
        TabItem.populatePOItems();
    }

    var setTaxOverride = function (isOverridePoItemTax) {
        if (isOverridePoItemTax) {
            $('#isOverridePoItemTax').iCheck('check');
        } else {
            $('#isOverridePoItemTax').iCheck('uncheck');
        }
    }

    var setItemTaxOverride = function () {
        for (var row = 0; row < purchaseOrderAdditionalCosts.length; row++) {
            var additionalCost = purchaseOrderAdditionalCosts[row];
            if (additionalCost.isOverridePoItemTax) {
                isOverridePoItemTax = true;
            }
        }
    };


    return {
        getAdditionalCostModalView: function () {
            getAdditionalCostModalView();
        },
        additionalCostType: function () {
            additionalCostType();
        },
        addAdditionalCost: function () {
            addAdditionalCost();
        },
        findAdditionalCost: function (id) {
            editAdditionalCost(id);
        },
        removeAdditionalCost: function (index) {
            removeAdditionalCost(index);
        },
        addAdditionalCostToList: function (additionalCost) {
            addAdditionalCostToList(additionalCost);
        },

        populateAdditionalCost: function () {
            populateAdditionalCost();
        }
    };


}();