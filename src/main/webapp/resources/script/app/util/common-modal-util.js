/**
 * This JS contain the common modal which need to dev the FOCUS functionality
 */

var customModal = function () {

    var getCommonModal = function (modalName, modalLoadedURL, func) {
        var $modal = $('#' + modalName);
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            $modal.load(modalLoadedURL, '', function () {
                if (func == "getBrandList") {
                    customModal.callBrand();
                }
                $modal.modal();
            });
        }, 1000);
    };

    return {
        getCommonModal: function (modalName, modalLoadedURL, func) {
            getCommonModal(modalName, modalLoadedURL, func);
        },
        
        callBrand: function () {
        }
    };

}();

