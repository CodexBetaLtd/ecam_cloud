var IssueNoteAdd = function () {
	
   /*********************************************************************
     * Init Components
     *********************************************************************/
	
    var runAODTypeIdSelect = function () {
        $("#aodTypeId").select2({
            placeholder: "Select a AOD Type",
            allowClear: true
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
    
    /*********************************************************************
     * Init Custom Components
     *********************************************************************/

    var runIssueNoteCustomerInput = function () {
        $("#aodCustomerName").inputClear({
            placeholder: "Customer Name",
            btnMethod: "AODAdd.aodCustomerModalView()",
        });
    };

    var runIssuedUserInput = function () {
        $("#requestedUserName").inputClear({
            placeholder: "Select Issued User",
            btnMethod: "IssueNoteAdd.issuNoteUserModalView()",
        });
    };
    
    /*********************************************************************
     * Init Modals
     *********************************************************************/

    var aodCustomerModalView = function () {
        var bizId = $('#businessId').val();
        if (bizId != null && bizId > 0) {
            var $modal = $('#master-modal-datatable');
            CustomComponents.ajaxModalLoadingProgressBar();
            setTimeout(function () {
                var url = '../aod/customermodalview';
                $modal.load(url, '', function () {
                    AODCustomers.init(bizId);
                    $modal.modal();
                });
            }, 1000);
        } else {
            alert("Please Select a Business First.");
        }
    };

    var issuNoteUserModalView = function () {
        var bizId = $('#businessId').val();
        if (bizId != null && bizId > 0) {
            var $modal = $('#master-modal-datatable');
            CustomComponents.ajaxModalLoadingProgressBar();
            setTimeout(function () {
                var url = '../issuenote/issuedusermodalview';
                $modal.load(url, '', function () {
                    AODUsers.init(bizId);
                    $modal.modal();
                });
            }, 1000);
        } else {
            alert("Please Select a Business First.");
        }
    };


    var acceptIsNotNull = function (obj) {
        return CustomValidation.isEmptyValueById("#")
    };

    /*********************************************************************
     * Init Validation
     *********************************************************************/
    
    var runValidator = function () {

        var form = $('#frm_issue_note');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        $('#frm_issue_note').validate({
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
                aodNo: {
                    required: true
                },

                aodType: {
                    required: true
                },
                businessId: {
                    required: true
                },
                siteId: {
                    required: true
                }
            },
            messages: {
                aodNo: "Please Specify a Issue Note No",
                aodType: "Please Select AOD Type",
                businessId: "Please Select Business",
                siteId: "Please Select Site",
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
            runAODTypeIdSelect();
            runDatePicker(); 
            runValidator();
            runBusinessSelect();
            runBusinessSiteFetch();
            runBusinessSiteSelect();
            runIssuedUserInput();
        },



        issuNoteUserModalView: function () {
        	issuNoteUserModalView();
        },

        acceptIsNotNull: function () {
            acceptIsNotNull();
        }

    };
}();
