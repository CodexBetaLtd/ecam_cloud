var InventoryGroupAdd = function () {

    /* 
     *load siteList relevant to business
     */
    var runValidator = function () {

        var form = $('#frm_inventory_group');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        $('#frm_inventory_group').validate({
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
                businessId: {
                    required: true
                },
                siteId: {
                    required: true
                },
                inventoryGroupNo:{
                	required: true,
                    remote: {
                        url: "../validate/inventorygroup/inventoryGroupCode",
                        type: "GET",
                        data: {
                            id: $('#inventoryGroupId').val()
                        }
                    }
                },
                name:{
                	required: true,
                    remote: {
                        url: "../validate/inventorygroup/inventoryGroupName",
                        type: "GET",
                        data: {
                            id: $('#inventoryGroupId').val()
                        }
                    }
                }
                
  
            },
            messages: {
                businessId: "Please Select Business",
                siteId: "Please Select Site",
                inventoryGroupNo:{
                    required: "Please Insert inventory group no",
                    remote: jQuery.validator.format("Inventory Group No {0} is already taken.")
                },
                name:{
                    required: "Please Insert inventory group name",
                    remote: jQuery.validator.format("Inventory Group Name {0} is already taken.")
                }
            },
            invalidHandler: function (event, validator) {
                successHandler.hide();
                errorHandler.show();
            },
            highlight: function (element) {
                $(element).closest('.help-block').removeClass('valid');
                $(element).closest('.form-group').removeClass('has-success').addClass('has-error').find('.symbol').removeClass('ok').addClass('required');
            },
            unhighlight: function (element) { // revert the change done by hightlight
                $(element).closest('.form-group').removeClass('has-error');
            },
            success: function (label, element) {
                label.addClass('help-block valid');
                $(element).closest('.form-group').removeClass('has-error').addClass('has-success').find('.symbol').removeClass('required').addClass('ok');
            },
            submitHandler: function (form) {
                successHandler.show();
                errorHandler.hide();
                return true;
            }
        });
    };

    var runBusinessSelect = function () {
        $("#businessId").select2({
            placeholder: "Select a Business",
            allowClear: true
        });
    };

    var runBusinessSiteSelect = function () {
        $("#siteId").select2({
            placeholder: "Select a Site",
            allowClear: true
        });
    };

    var runBusinessSiteFetch = function () {
        $("#businessId").change(function () {
            var businessId = $("#businessId option:selected").val();
            $.ajax({
                type: "GET",
                url: "../asset/getsites?id=" + businessId,
                contentType: "application/json",
                dataType: "json",
                success: function (output) {
                    $("#siteId").find("option").remove();
                    $.each(output, function (key, siteList) {
                        $('#siteId').append($('<option>', {
                            value: siteList.id
                        }).text(siteList.name));
                    });
                    runBusinessSiteSelect();
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    alert(xhr.status + " " + thrownError);
                },
                error: function (e) {
                    alert("Failed to load site");
                }
            });
        });
    };

    return {

        init: function () {
            runValidator();
            runBusinessSelect();
            runBusinessSiteFetch();
            runBusinessSiteSelect();
        },

    };
}();
