var StockAdd = function () {

    var runWarehouseNameInput = function () {
        $("#warehouseName").inputClear({
            placeholder: "Select Warehouse",
            btnMethod: "StockAdd.setWarehouseView()",
        });
    };
    var runItemNameInput = function () {
        $("#partName").inputClear({
            placeholder: "Select Item",
            btnMethod: "StockAdd.getPartView()",
        });
    };
    
    var runDatePicker = function () {
        $('.date-picker').datepicker({
            container: $('.date-picker').closest("div"),
            autoclose: true
        });
    };
    
    var runBusinessSelect = function () {
    	$("#businessId").select2({
    		placeholder: "Select Business",
    		allowClear: true
    	});
    };
    var runStockTypeSelect = function () {
        $("#stockType").select2({
            placeholder: "Select Stock Type",
            allowClear: true
        });
    };

    var getPartView = function () {
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../stock/partView';
            $modal.load(url, '', function () {
            	dtStockPart.dtGetPartList();
                $modal.modal();
            });
        }, 1000);
    };
    
    var addNewPart = function (partId,partName,partCode) {
    	$('#partId').val(partId)
    	$('#partName').val(partName)
    }

    var setWarehouseView = function () {
        warehouseView("StockAdd.setStockWarehouse");
    }
    var warehouseView = function (func) {
        var $modal = $('#master-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../stock/warehouseView';
            $modal.load(url, '', function () {
                dtWarehouse.getWarehouseDataTable(null, null, func);
                $modal.modal();
            });
        }, 1000);
    };


   var setStockWarehouse = function (id, name) {
        $("#warehouseId").val(id);
        $("#warehouseName").val(name);
        $('#master-modal').modal("hide");
    };

    var setStockItem = function (data, id, name) {
        $("#itemId").val(id);
        $("#itemName").val(name);
        $('#master-modal').modal("hide");
    };

    var initValidator = function () {
        var form = $('#frm_stock');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        $('#frm_stock').validate({
            errorElement: "span", // contain the error msg in a span tag
            errorClass: 'help-block',
            errorPlacement: function (error, element) { // render error placement for each input type
            	if (element.attr("type") == "radio" || element.attr("type") == "checkbox" ) { // for chosen elements, need to insert the error after the chosen container
                    error.insertAfter($(element).closest('.form-group').children('div').children().last());
                } else if (element.attr("name") == "dd" || element.attr("name") == "mm" || element.attr("name") == "yyyy" || element.closest('.input-group').has('.input-group-btn').length || element.closest('.form-group').has('.input-group-addon').length) {
                    error.insertAfter($(element).closest('.form-group').children('div'));
                } else if (element.closest('.form-group').has('.select2').length ){
                	error.insertAfter($(element).closest('.form-group').children('span'));
                } else {
                    error.insertAfter(element);
                    // for other inputs, just perform default behavior
                }
            },
            ignore: "",
            rules: {
            	partName: {
                    required: true
                },
                businessId: {
                    required: true,
                },
                warehouseName: {
                    required: true
                },
                batchNo: {
                    required: true
                }, 
                qtyOnHand: {
                	required: true
                },
                minQty: {
                    required: true
                }
            },
            messages: {
            	partName : "Please Select a Part",
            	businessId:"Please Select a Business",
            	warehouseName: "Please Select a Warehouse ",
                businessId: "Please Select a Business", 
                batchNo: "Please Specify a Batch No",
                qtyOnHand: "Please Specify a Quantity On Hand",
                minQty: "Please Specify a Minimum Quantity"
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

    return {

        init: function () {
            runWarehouseNameInput();
            runItemNameInput();
            runBusinessSelect();
            runStockTypeSelect();
            initValidator();
            runDatePicker();
        },
        setStockWarehouse: function (id, name) {
            setStockWarehouse(id, name);
        },
        setStockItem: function (data, id, name) {
            setStockItem(data, id, name);
        },

        setWarehouseView: function () {
            setWarehouseView();
        },
        warehouseView: function () {
            warehouseView();
        },

        addNewPart:function(partId,partName,partCode){
        	addNewPart(partId,partName,partCode);
        },
        
        getPartView:function(){
        	getPartView();
        }

    };
}();
