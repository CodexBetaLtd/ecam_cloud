var InstallConfirmationModal = function () {

    var initAppInstallButton = function (appId) {
        $('#btn-app-install-btn').on('click', function () {
            MyApp.installApp(appId);
        });
    };

    return {
        init: function (appId) {
            initAppInstallButton(appId);
        }
    };

}();