var AssetCategoryAdd = function() {

	var runAssetCategorySelect = function() {
		$("#type").select2({
			placeholder : "Select a Asset Category",
			allowClear : true
		});
	};

	var runParentAssetSelect = function() {
		$("#parentId").select2({
			placeholder : "Select a Parent Asset",
			allowClear : true,
		});
	};
	
    var runBusinessSelect = function () {
        $("#businessId").select2({
            placeholder: "Select a Business",
            allowClear: true
        });
    };

	var saveCategory = function () {

        var form = $("#assetCategoryAddForm");
		
		if (form.valid()) {
			$.ajax({
	             url: form.attr('action'),
	             type: 'POST',
	             data: form.serialize(),
	             success: function(response) {
                     $("#assetCategoryTab").empty().append(response);
	                 AssetCategoryAdd.init();
	             }
	         });
		}
	};

	var initChangeFunctionToAssetCategory = function() {	
		
		$("#type").change(function() {			
			var child = $("#type").val();
			if ( child == null || child == "" || child == undefined) {
				$("#parentId").find("option").remove();
				runParentAssetSelect();
			} else {
				$.ajax({
					type : "GET",
					url : "../assetCategory/selectParent/" + child,
					contentType : "application/json",
					dataType : "json",
					success : function(output) {
						$("#parentId").find("option").remove();
						$.each(output, function(key, value) {
							$('#parentId').append($('<option>', { value : value.id }).text(value.name));
						});
                        runParentAssetSelect();
					},
					error : function(xhr, ajaxOptions, thrownError) {
						alert(xhr.status + " " + thrownError);
					},
					error : function(e) {
						alert("Failed to load Asset");
						console.log(e);
					}
				});
			}

        });
	};
	
	var runValidator = function() {

        var form = $('#assetCategoryAddForm');
		var errorHandler = $('.errorHandler', form);
		var successHandler = $('.successHandler', form);

        $('#assetCategoryAddForm').validate(
		{
			errorElement : "span", // contain the error msg in a span tag
			errorClass : 'help-block',
			errorPlacement : function(error, element) { // render error placement for each input type
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
			ignore : "",
			rules : {
				name : {
					minlength : 2,
					required : true
				},
				description : {
					minlength : 2,
					required : true
				},
				overideRule : {
					minlength : 2,
				//	required : true
				},
				type : {
					required : true
				},
				businessId : {
					required : true
				}
			},
			messages : {
				name : "Please Specify a Name",
				description : "Please Specify a Description",
				overrideRule : "Please Specify a Override Rule",
				type : "Please Select System Code",
				businessId : "Please Select Business"
			},
			invalidHandler : function(event, validator) { //display error alert on form submit
				successHandler.hide();
				errorHandler.show();
			},
			highlight : function(element) {
				$(element).closest('.help-block').removeClass('valid');
				// display OK icon
				$(element).closest('.form-group').removeClass('has-success').addClass('has-error').find('.symbol').removeClass('ok').addClass('required');
				// add the Bootstrap error class to the control group
			},
			unhighlight : function(element) { // revert the change done by hightlight
				$(element).closest('.form-group').removeClass('has-error');
				// set error class to the control group
			},
			success : function(label, element) {
				label.addClass('help-block valid');
				// mark the current input as valid and display OK icon
				$(element).closest('.form-group').removeClass('has-error').addClass('has-success').find('.symbol').removeClass('required').addClass('ok');
			},

			submitHandler : function(form) {
				successHandler.show();
				errorHandler.hide();
				// submit form
				return true;
			}
		});
	};

	return {

		init : function() {
			runValidator();
			runAssetCategorySelect();
			runParentAssetSelect();
			runBusinessSelect();
			initChangeFunctionToAssetCategory();
			TaskTab.init();
		},
		
		runValidator: function(){
			runValidator();
		},
		
		initChangeFunctionToAssetCategory: function(){
			initChangeFunctionToAssetCategory();
		},
		
		saveCategory : function() {
			saveCategory();
		},
	
	};
}();