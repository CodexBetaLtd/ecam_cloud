var UninstallConfirmationModal = function () {

    var initAppUninstallButton = function (appId) {
        $('#btn-app-uninstall-btn').on('click', function () {
            MyApp.uninstallApp(appId);
        });
    };

    return {
        init: function (appId) {
            initAppUninstallButton(appId);
        }
    };

}();