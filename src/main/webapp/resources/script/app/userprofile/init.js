jQuery(document).ready(function () {
	
    Main.init();
    PagesUserProfile.init();
    MyAccount.init();
    FormElements.init();

    $('#changePassword').iCheck({
        checkboxClass: 'icheckbox_minimal',
    });

    $("#passwordCurrent").attr('disabled', 'disabled');
    $("#password").attr('disabled', 'disabled');
    $("#passwordAgain").attr('disabled', 'disabled');
    
});