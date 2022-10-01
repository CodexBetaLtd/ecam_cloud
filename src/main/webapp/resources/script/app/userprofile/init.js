jQuery(document).ready(function () {
	
    Main.init();
    PagesUserProfile.init();
    MyAccount.init();
    FormElements.init();

    $('#changePassword').iCheck({
        checkboxClass: 'icheckbox_minimal',
    });

    $("#currentPassword").attr('disabled', 'disabled');
    $("#password").attr('disabled', 'disabled');
    $("#password_again").attr('disabled', 'disabled');
    
});