var StockAdjustmentAdd = function () {

    var runPartNameInput = function () {
        $("#partName").inputClear({
            placeholder: "Select Item",
            btnMethod: "StockAdjustmentAdd.getItemView()",
        });
    };
    var runWarehouseInput = function () {
        $("#warehouseName").inputClear({
            placeholder: "Select Warehouse",
            btnMethod: "StockAdjustmentAdd.getWarehouseView()",
        });
    };
    var runStockNoInput = function () {
        $("#stockNo").inputClear({
            placeholder: "Select Stock",
            btnMethod: "StockAdjustmentAdd.StockAdjView()",
        });
    };
    var StockAdjView = function () {
        var partId = $("#partId").val();
        StockAdjustmentAdd.getStockView(partId);
    };

    var runDatePicker = function () {
        $('.date-picker').datepicker({
            container: $('#dateOrdered').closest("div"),
            autoclose: true
        });
    };


    var getItemView = function () {
        var $modal = $('#master-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../stockAdjustment/itemView';
            $modal.load(url, '', function () {
                dtPart.getPartDataTable(null, 'dtPart.setPart');
                $modal.modal();
            });
        }, 1000);
    };

    var getWarehouseView = function () {

        var partId = $('#partId').val();
        if (partId == null || partId == "") {
            alert('Please Select item first')
        } else {
            var url = '../stockAdjustment/warehouseView';
            var dturl = "../restapi/wearhouse/warehouseWithItemQty?partId=" + partId;
            loadWarehouseModal("StockAdjustmentAdd.setWarehouse", url, dturl);
        }

    };
    var loadWarehouseModal = function (func, urlmodal, dturl) {
        var $modal = $('#master-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = urlmodal;
            $modal.load(url, '', function () {
                dtWarehouse.getWarehouseDataTable(null, dturl, func);
                $modal.modal();
            });
        }, 1000);
    }

    var getStockView = function (partId) {
        var warehouseId = $('#warehouseId').val();
        if (warehouseId == null || warehouseId == "") {
            dtURL = '../restapi/stock/stockByPart?partId=' + partId;
        } else {
            dtURL = '../restapi/stock/stockByPartAndWarehouse?partId=' + partId + '&&warehouseId=' + warehouseId;
        }
        loadStockModal(dtURL);
    };

    var loadStockModal = function (dtURL) {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../stockAdjustment/stockView';
            $modal.load(url, '', function () {
                // var dtURL = '../restapi/stock/dtStockByPart?partId=' + partId;
                dtStock.getDataTableStock(dtURL, 'dtStock.setStock');
                $modal.modal();
            });
        }, 1000);
    }


    var runValidator = function () {
        var form = $('#frm_stockAdjustment');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        jQuery.validator.addMethod("greaterThanGRNQuantity", function (value, element) {
            return value > 0
        }, "This item quantity not valid");
        $('#frm_stockAdjustment').validate({
            errorElement: "span", // contain the error msg in a span tag
            errorClass: 'help-block',
            errorPlacement: function (error, element) { // render error placement for each input type
                if (element.attr("type") == "radio" || element.attr("type") == "checkbox") { // for chosen elements, need to insert the error after the chosen container
                    error.insertAfter($(element).closest('.form-group').children('div').children().last());
                } else if (element.attr("name") == "dd" || element.attr("name") == "mm" || element.attr("name") == "yyyy" || element.closest('.input-group').has('.input-group-btn').length || element.closest('.form-group').has('.input-group-addon').length) {
                    error.insertAfter($(element).closest('.form-group').children('div'));
                } else if (element.closest('.form-group').has('.select2').length) {
                    error.insertAfter($(element).closest('.form-group').children('span'));
                } else {
                    error.insertAfter(element);
                    // for other inputs, just perform default behavior
                }
            },
            ignore: "",
            rules: {
                partId: {
                    required: true
                },
                stockId: {
                    required: true
                },
                newQuantity: {
                    required: true,
                    number: true,
                    min: 1,
                }
            },
            messages: {
                partId: "Please Select a Item",
                stockId: "Please Select a Stock Item",
                newQuantity: {
                    required: "Please Specify new stock level",
                    min: jQuery.validator.format("Please enter a value greater than or equal to {0}."),
                    number: "Please input numeric characters only "

                }
            },
            invalidHandler: function (event, validator) { //display error alert on form submit
                successHandler.hide();
                errorHandler.show();
            },
            highlight: function (element) {
                $(element).closest('.help-block').removeClass('valid');
                // display OK icon
                $(element).closest('.form-group').removeClass('has-success').addClass('has-error').find('.symbol').removeClass('ok').addClass('required');
                // add the Bootstrap error class to the control group
            },
            unhighlight: function (element) { // revert the change done by hightlight
                $(element).closest('.form-group').removeClass('has-error');
                // set error class to the control group
            },
            success: function (label, element) {
                label.addClass('help-block valid');
                // mark the current input as valid and display OK icon
                $(element).closest('.form-group').removeClass('has-error').addClass('has-success').find('.symbol').removeClass('required').addClass('ok');
            },
            submitHandler: function (form) {
                successHandler.show();
                errorHandler.hide();
                return true;
            }
        });
    };

    var setWarehouse = function (id, name) {
        $('#warehouseId').val(id);
        $('#warehouseName').val(name);
    }
    return {
        init: function () {
            runValidator();
            runPartNameInput();
            runStockNoInput();
            runWarehouseInput();
            runDatePicker();
        },

        getItemView: function () {
            runValidator();
            getItemView();
        },
        getStockView: function (partId) {
            getStockView(partId);
        },
        StockAdjView: function () {
            StockAdjView();
        },
        getWarehouseView: function () {
            getWarehouseView();
        },
        setWarehouse: function (id, name) {
            setWarehouse(id, name);
        }


    };
}();
