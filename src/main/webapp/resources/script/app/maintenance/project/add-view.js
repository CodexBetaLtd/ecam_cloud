var ProjectAdd = function () {

    var runBusinessSelect = function () {
        $("#businessId").select2({
            placeholder: "Select a Business",
            allowClear: true
        });
    };

    var runSiteSelect = function () {
        $("#siteId").select2({
            placeholder: "Select a Site",
            allowClear: true
        });
    };

    var runDatePicker = function () {
        $('.date-picker').datepicker({
            autoclose: true,
            orientation: "auto",
            zIndexOffset:9999,
            container: '#picker-container'
        });
    };

    var runValidator = function () {
        var form = $('#project_add_frm');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        $('#project_add_frm').validate({
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
                }
            },
            ignore: "",
            rules: {
                name: {
                    minlength: 2,
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
                name: "Please Specify a Name",
                businessId: "Please Select a Business",
                siteId: "Please Select a site"
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

    /* 
	 *load siteList relevant to business
     */
    var getSites = function () {
    	$("#businessId").change(function() {
			var businessId = $("#businessId option:selected").val();
			$.ajax({
				type : "GET",
                url: "../project/getsites?id=" + businessId,
				contentType : "application/json",
				dataType : "json",
				success : function(output) {
					$("#siteId").find("option").remove();
					$.each(output, function(key, siteList) {
                        $('#siteId').append($('<option>', {value: siteList.id}).text(siteList.name).trigger('change'));
					});
					runSiteSelect();
				},
				error : function(xhr, ajaxOptions, thrownError) {
					alert(xhr.status + " " + thrownError);
				},
				error : function(e) {
					alert("Failed to load site");
					console.log(e);
				}
			});
		});
    }
    
    return {
        init: function () {
            runValidator();
            runDatePicker();
            runBusinessSelect();
            runSiteSelect();
            getSites();
        }
    };
}();
