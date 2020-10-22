var NotificationAdd = function() {

	var notificationValidator = function() {
		var form = $('#frm_notification');
		// var errorHandler = $('.errorHandler', form);
		// var successHandler = $('.successHandler', form);
		$('#frm_notification').validate(
				{

					errorElement : "span",
					errorClass : 'help-block',
					errorPlacement : function(error, element) {
						if (element.attr("type") == "radio"
								|| element.attr("type") == "checkbox") {
							// for chosen elements, need to insert the error
							// after the chosen container
							error.insertAfter($(element).closest('.form-group')
									.children('div').children().last());
						} else if (element.attr("name") == "dd"
								|| element.attr("name") == "mm"
								|| element.attr("name") == "yyyy") {
							error.insertAfter($(element).closest('.form-group')
									.children('div'));
						} else {
							error.insertAfter(element);
							// for other inputs, just perform default behavior
						}
					},

					ignore : "",

					rules : {
						subject : {
							minlength : 2,
							required : true
						},
						content : {
							minlength : 2,
							required : true
						}
					},

					messages : {
						subject : "Please Specify a Subject",
						content : "Please Specify a Content"
					},

					invalidHandler : function(event, validator) { // display
																	// error
																	// alert on
																	// form
																	// submit
						successHandler.hide();
						errorHandler.show();
					},

					highlight : function(element) {
						$(element).closest('.help-block').removeClass('valid');
						// display OK icon
						$(element).closest('.form-group').removeClass(
								'has-success').addClass('has-error').find(
								'.symbol').removeClass('ok').addClass(
								'required');
						// add the Bootstrap error class to the control group
					},

					unhighlight : function(element) { // revert the change
														// done by hightlight
						$(element).closest('.form-group').removeClass(
								'has-error');
						// set error class to the control group
					},

					success : function(label, element) {
						label.addClass('help-block valid');
						// mark the current input as valid and display OK icon
						$(element).closest('.form-group').removeClass(
								'has-error').addClass('has-success').find(
								'.symbol').removeClass('required').addClass(
								'ok');
					},
					submitHandler : function(form) {
						successHandler.show();
						errorHandler.hide();
						return true;
					}

				});
	};

	var ckeditorInit = function() {
		// CKEDITOR.disableAutoInline=true;
		// $("textarea.ckeditor").ckeditor();
	};

	/*
	 * ================================================================ Init
	 * Functions
	 * ================================================================
	 */

	var initNotificationUserSelect = function() {
		$("#receivedUserName").inputClear({
			placeholder : "Select Recipient",
			btnMethod : "NotificationAdd.getUserModal()"
		});
	};

	var initBusinessSiteSelect = function() {
		$("#businessId").select2({
			placeholder : "Select Business",
			allowClear : true
		});
		$("#siteId").select2({
			placeholder : "Select Site",
			allowClear : true
		});
	};

	var getUserModal = function() {
		var $modal = $('#stackable-datatable-modal');
		CustomComponents.ajaxModalLoadingProgressBar();
		setTimeout(function() {
			var url = '../notification/notificationUserModal';
			$modal.load(url, '', function() {
				dtNotificationUser.getUserList("NotificationAdd.setToUser");
				$modal.modal();
			});
		}, 1000);
	};

	var setToUser = function(id, name) {
		addUserToNotify(id, name)
	};


	var setRecipeient = function() {
		var elt = $('#receivedUserName');
		elt.tagsinput({
			itemValue : 'value',
			itemText : 'text',
			  tagClass: function() {
				  return 'label label-danger label-important'
			  }
		});

	}

	var addUserToNotify = function(id, name) {
		var elt = $('#receivedUserName');
		elt.tagsinput('add', {
			"value" : id,
			"text" : EncodeDecodeComponent.getBase64().decode(name),
			"continent" : "User Notify"
		});
	}

	return {

		init : function() {
			notificationValidator();
			initNotificationUserSelect();
			initBusinessSiteSelect();
			ckeditorInit();
			notificationValidator();
			setRecipeient();
		},
		getUserModal : function() {
			getUserModal();
		},
		setToUser : function(id, name) {
			setToUser(id, name);
		}
	};
}();
