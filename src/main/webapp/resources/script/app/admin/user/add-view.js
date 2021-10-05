var UserAdd = function () {

    var runUserLevelSelect = function () {
        $("#userLevel").select2({
            allowClear: true,
            placeholder: "Select a Level",

        });
        setUserLevelElements(getUserLevel());   
    };
	
	var getUserLevel = function(){
		var levelId = $("#userLevel option:selected").val();
		return levelId;
	}
	
	var getSelectedUserLevel = function(){
		 $("#userLevel").change(function () {
            setUserLevelElements(getUserLevel());
	     });
	};
	
	var initUserSkillLevelSelect = function () {
        $("#userSkillLevelName").inputClear({
            placeholder: "Select a User Skill Level",
            btnMethod: "UserAdd.SkillLevelView()",
        });
    };
    
    var initUserJobTitleSelect = function () {
        $("#jobTitleName").inputClear({
            placeholder: "Select a Job Title",
            btnMethod: "UserAdd.jobTitleView()",
        });
    };

    var runBusinessSelect = function () {
        $("#businessId").select2({
            allowClear: true,
            placeholder: "Select a Business",
        });
    };
	
	var setUserLevelElements = function (level){
		if(level == 'ADMIN_LEVEL'){
			$('#businessSelect').hide();
			$('#userSiteTab').hide();
			$('#userSitesTabFragment').hide();
        }else if(level == 'SYSTEM_LEVEL'){
        	$('#businessSelect').show();
        	$('#userSiteTab').hide();
        	$('#userSitesTabFragment').hide();
        }else{
        	$('#businessSelect').show();
        	$('#userSiteTab').show();
        	$('#userSitesTabFragment').show();
        }
	};
	
	/********************************************
     * Initialize InputClear Components
     ********************************************/

    function initInputClearComponents(){
        initInputClearCurrency();
    };
    
    function initInputClearCurrency(){
        $("#currencyName").inputClear({
            placeholder:"Please specify a currency",
            btnMethod:"UserAdd.addCurrency()",
        });
    };

    /********************************************
     * Initialize modal views
     ********************************************/
    
    function initModalCurrencySelect() {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../userProfile/view/modal/currencies';
            $modal.load(url, '', function () {
                DatatableModalCurrencies.init(
                        "common-modal",
                        "tbl_currencies",
                        "../restapi/currency/tabledata",
                        "setData"
                        );
                $modal.modal();
            });
        }, 1000);
    };

    var initValidator = function () {
    	
        var form = $('#userProfileForm');
        var errorHandler = $('.errorHandler', form);
        var successHandler = $('.successHandler', form);
        form.validate({
            errorElement: "span", // contain the error msg in a span tag
            errorClass: 'help-block',
            errorPlacement: function (error, element) {
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
            	fullName: {
            		minlength: 2,
            		required: true
            	},
            	userLevel: {
            		required: true,
            	},
            	assignedBusinessList: {
            		required: function(){
            			return getUserLevel()=='ADMIN_LEVEL' ? false : true;
            		}
            	},
                emailAddress: {
                    minlength: 2,
                    required: true,
                    email: true
                },
                userName: {
                	required: true,
                    minlength: 6,
                },
                password: {
            		required: true,
                    minlength: 6,
            	},
            	passwordAgain :{                
            		required: true,
            		minlength: 6,
            		equalTo: password,
            	}
            },

            messages: {
                fullName: "Please specify a Name",
                userLevel: "Please select User Level",
                assignedBusinessList: "Please select a Business",
                emailAddress: {
                    email: "Your email address must be in the format of name@domain.com",
                    required: "Please specify a vaild email"
                },
                userName :"Please Specify the User Name",
                password :{
                	required: "Password does not specify",
                	minlength: "Please enter at least {0} characters.",
                },
                passwordAgain : {
                	required: "Re-enter password does not specify",
                	minlength: "Please enter at least {0} characters.",
                	equalTo: "Password does not match",
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
                // submit form
                return true;
            }
        });
    };   
    
    var jobTitleView = function () {    	
    	var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
    	setTimeout(function () {
    		var url = '../userProfile/userjobTitleview';
    		$modal.load(url, '', function () {
    			jobTitle.getJobTitleTable();
    			$modal.modal();
    		});
    	}, 1000);
    };
    
    var jobTitleAddView = function () {
    	var $modal = $('#stackable-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
    	setTimeout(function () {
    		var url = '../userProfile/userjobTitleadd';
    		$modal.load(url, '', function (){
    			UserJobTitleAdd.init();
    			$modal.modal();
    		});
    	}, 1000);
    };
    
    var jobTitleEditView = function (id) {
    	var $modal = $('#stackable-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
    	setTimeout(function () {
    		var url = '../userProfile/userjobTitleedit?id='+id;
    		$modal.load(url, '', function (){
    			UserJobTitleAdd.init();
    			$modal.modal();
    		});
    	}, 1000);
    };
    
    var setJobTitle = function (id, name) {
    	$('#jobTitleId').val(id);
    	$('#jobTitleName').val(name);
    	$('#master-modal-datatable').modal('toggle');
    };
    
    var SkillLevelView = function () {    	
        var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../userProfile/userskilllevelview';
            $modal.load(url, '', function () {
            	UserSkillLevel.getSkillLevelTable();
                $modal.modal();
            });
        }, 1000);
    };

    var skillLevelAddView = function () {
    	var $modal = $('#stackable-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
    	setTimeout(function () {
    		var url = '../userProfile/userskillLeveleadd';
    		$modal.load(url, '', function (){
    			UserSkillAdd.init();
    			$modal.modal();
    		});
    	}, 1000);
    };
    
    var skillLevelEditView = function (id) {
    	var $modal = $('#stackable-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
    	setTimeout(function () {
    		var url = '../userProfile/skilleveledit?id='+id;
    		$modal.load(url, '', function (){
    			UserSkillAdd.init();
    			$modal.modal();
    		});
    	}, 1000);
    };

	var setSkillLevel = function (id, name) {
		$('#skillLevelId').val(id);
		$('#userSkillLevelName').val(name);
		$('#master-modal-datatable').modal('toggle');
	};
	
    return {
        init: function () {
        	runBusinessSelect();
        	runUserLevelSelect();
        	initInputClearComponents();
            getSelectedUserLevel();
            initUserSkillLevelSelect();
            initUserJobTitleSelect();
            initValidator();
        },

        getUserLevel : function(){
        	getUserLevel();
        },
        
        isPasswordFieldEmpty: function(){
        	isPasswordFieldEmpty();
        },
        
        userCertificationView: function(){
        	userCertificationView();
        },
        
        jobTitleView: function(){
        	jobTitleView();
        },
        
        setJobTitle: function(id, name){
        	setJobTitle(id, name);
        },
        
        jobTitleAddView:function(){
    	   jobTitleAddView();
        },
        
        SkillLevelView:function(){
    	   SkillLevelView();
        },
        
        setSkillLevel:function (id,name){
    	   setSkillLevel (id,name);
        },
        
        skillLevelAddView:function(){
    	   skillLevelAddView();
        },
        
        jobTitleEditView:function(id){
    	   jobTitleEditView(id);
        },
        
        skillLevelEditView:function(id){
    	   skillLevelEditView(id);
        },
        
        addCurrency: function () {
            initModalCurrencySelect(); 
        }
    };

}();