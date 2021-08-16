var ReservationDone = function () {

	/******************************************
	 * Initialize Buttons
	 ******************************************/
	
	var initButtons = function() {
		initButtonSubmit();
	};
	
	var initButtonSubmit = function() {
		$("#submit").on('click', function(event) {
			event.preventDefault();
			var form = $("#reservationDoneForm");
			
			if (form.valid()) {
				if (!isExceedmaximumAverage()) {
					$('#btn_submit').trigger("click");
				} else {
					swal({
						title: '',
						text: "Meter Reading 'Average daily' value exceeding 'Maximum average' value.\n\nDo you want to continue ?",
						showCancelButton: true,
						confirmButtonText: 'Yes',
					    cancelButtonText: "No",
					    customClass: "swal-error",
			        }, function(ifConfirmed) {
						if (ifConfirmed) {
							$('#btn_submit').trigger("click");
						} else {
							return false;
						}
					});
				}
			}
		})
	};
	
	var isExceedmaximumAverage = function() {
		var isExeed = false;

		var vehicleCode = $("#vehicleNo").val();
		var endMeter = $("#endMeter").val();
		
		$.ajax({
            type: "GET",
            url: "../reservationdone/vehicle/" + vehicleCode + "/reading/" + endMeter + "/is-exceed-maxmium-average",
            contentType: "application/json",
            async: false,
            success: function (output) {
            	isExeed = output;
            },
            error: function (xhr, ajaxOptions, thrownError) {
            	console.log(xhr.status + " " + thrownError);
            },
            error: function (e) {
            	console.log(e)
                console.log("Failed load vehicle details ");
            }
        });
		
		return isExeed;
	};
	
	var initUI = function () {
        $('.box-forgot').show();        
	};
    
    var initLoadingImage=function(){
        $(window).load(function(){   
            setTimeout(function() {
            	$('.loader').hide();
            }, 1000);
       });
    }
    

    var runForgotValidator = function () {

        var form = $('#reservationDoneForm');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        
        jQuery.validator.addMethod("greaterStartMeter", function (value, element) {
        	return parseFloat(element.value) >= parseFloat( $("#startMeter").val())
        }, "");
        jQuery.validator.addMethod("vehicleNoExist", function (value, element) {
        	return  $("#vehicleId").attr("value")  != "" 
        }, "");
        
        form.validate({
            errorElement: "span", // contain the error msg in a span tag
            errorClass: 'help-block',
            errorPlacement: function (error, element) { // render error placement for each input type
                if (element.attr("type") == "radio" || element.attr("type") == "checkbox") { // for chosen elements, need to insert the error after the chosen container
                    error.insertAfter($(element).closest('.form-group').children('div').children().last());
                } else if (element.attr("name") == "dd" || element.attr("name") == "mm" || element.attr("name") == "yyyy") {
                    error.insertAfter($(element).closest('.form-group').children('div'));
                } else {
                    error.insertAfter(element);
                }
            },
            ignore: "",
            rules: {
            	endMeter: {
            		required: true,
                    number:true,
                    greaterStartMeter:[]
            	},
            	vehicleNo: {
            		required: true,
            		vehicleNoExist: [],
                }
            },
            messages: {
            	vehicleNo: {
            		required:"Please Insert a Vehicle No",
            		vehicleNoExist:"Please Select Vehicle No From List",
            	},
            	endMeter:{
            		required:"Please specify your end meter",
            		number:"Please insert numbers only",
            		greaterStartMeter:"Current meter reading greater than your end meter reading"
            	}
            },
            invalidHandler: function (event, validator) { //display error alert on form submit
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
                errorHandler.hide();
                form.submit();
            }
        });
    };
    
    var vehilceAutoComplete=function() {
    	
        $('#vehicleNo').autocomplete({
            minLength: 1,
            source: function(request, response){
                $.ajax({
                    url:'../reservationdone/findvehiclebycode',
                    dataType:"json",
                    html: true,
                    data:{term:request.term},
                    success: function(data){
                        response(data.slice(0,5));
                    }
                });
            },
	        select: function (event, ui) {
	        	ui.item.value=ui.item.code
	        	ui.item.lable=ui.item.code
	        	$("#vehicleId").val("");
	        	$("#vehicleId").val(ui.item.id)
	        	getPerviousMeterReading(ui.item.code)
	        },
	        change:function (event, ui) {
	        	getVehicleIdForAutoCompleteChange(event.target.value)
	        	getPerviousMeterReading(event.target.value)
	        },
            create: function (event,ui){
               $(this).data('ui-autocomplete')._renderItem = function (ul, item) {
                return $('<li>').append('<a>' + item.code +'</a>').appendTo(ul);
            };
        }
        }).focus(function() {
	        $(this).autocomplete("search", "");        
	    });
    }
    
    var getVehicleIdForAutoCompleteChange=function(code){
    	$("#vehicleId").val("");
    	$.ajax({
            type: "GET",
            url: "../reservationdone/findVehicleIdByCode?code="+code,
            contentType: "application/json",
            success: function (output) {
            	$("#vehicleId").val(output)
            },
            error: function (xhr, ajaxOptions, thrownError) {
            	console.log(xhr.status + " " + thrownError);
            },
            error: function (e) {
            	console.log(e)
                console.log("Failed load vehicle details ");
            }
        });
    }
    var getPerviousMeterReading=function(code){
    	$("#endMeter").val("")
    	$("#startMeter").val("")
    	$.ajax({
            type: "GET",
            url: "../reservationdone/findVehicleDetailsByCode?code="+code,
            contentType: "application/json",
            success: function (output) {
           	
            	$("#endMeter").val(output)
            	$("#startMeter").val(output)
            	document.getElementById("endMeter").focus();
            },
            error: function (xhr, ajaxOptions, thrownError) {
            	console.log(xhr.status + " " + thrownError);
            },
            error: function (e) {
            	console.log(e)
                console.log("Failed load vehicle details ");
            }
        });
    }
    
    return {
        init: function () {        	 
            initUI();
            initLoadingImage();
            runForgotValidator();
            vehilceAutoComplete();
            initButtons();
        }
    };
}();