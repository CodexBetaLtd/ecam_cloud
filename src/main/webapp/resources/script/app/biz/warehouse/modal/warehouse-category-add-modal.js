var WarehouseCategoryAddModal = function(){

	var runValidator = function() {

        var form = $('#warehouseCategoryAddForm');
		var errorHandler = $('.errorHandler', form);
		var successHandler = $('.successHandler', form);

        $('#warehouseCategoryAddForm').validate(
		{
			errorElement : "span", // contain the error msg in a span tag
			errorClass : 'help-block',
			errorPlacement : function(error, element) { // render error placement for each input type
				if (element.attr("type") == "radio" || element.attr("type") == "checkbox") { // for chosen elements, need to insert the error after the chosen container
					error.insertAfter($(element).closest('.form-group').children('div').children().last());
				} else if (element.attr("name") == "dd" || element.attr("name") == "mm" || element.attr("name") == "yyyy") {
					error.insertAfter($(element).closest('.form-group').children('div'));
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
				parentId : {
					required : true
				}
			},
			messages : {
				name : "Please Specify a Name",
				description : "Please Specify a Description",
				parentId : "Please Select Parent Asset"
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
    
    var runParentAssetSelect = function () {
        $("#parentId").select2({
            placeholder: "Select parent asset",
            allowClear: true,
            dropdownParent: $("#category-add-modal")
        });        
    };
    
    var setParentCategory = function(){
		$('#type').val(categoryType);
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
    }
	
	var saveCategory = function() {

		var $modal = $('#category-add-modal');
		var form = $('#warehouseCategoryAddForm');

		if (form.valid()) {
			$.ajax({
				url : form.attr('action'),
				type : 'POST',
				data : form.serialize(),
				success : function(response) {
					$modal.empty().append(response);
					WarehouseCategoryAddModal.init();
					WarehouseCategoryDataTable.init();
					$modal.modal();
				},
				error : function(response) {
					console.log(response);
				}
			});
		}
	};
    
    return {
    	init: function(){
    		runParentAssetSelect();
    		setParentCategory();
    		runValidator();
    	},
    	
    	saveCategory: function(){
    		saveCategory();
    	}
    };
}();