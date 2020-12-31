var RelatedAppTab = function () {

    var initButtons = function () {
        $('#btn-new-related-app').on('click', function () {
            RelatedAppTab.initAppSelectTable();
        });
    };

    var initAppSelectTable = function () {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../app/app-select-view';
            $modal.load(url, '', function () {
                AppSelectModal.init();
                $modal.modal();
            });
        }, 1000);
    };

    var initRelatedAppTable = function () {
        if (relatedApps.length > 0) {
            $("#related_app_tbl > tbody").html("");
            for (var row = 0; row < relatedApps.length; row++) {
                var relatedApp = relatedApps[row];
                var html =
                    "<tr id='row_" + row + "' >" +
                    "<input id='relatedApps" + row + ".id' name='relatedApps[" + row + "].id' value='" + CustomValidation.nullValueReplace(relatedApp.id) + "' type='hidden'>" +
                    "<input id='relatedApps" + row + ".relatedAppId' name='relatedApps[" + row + "].relatedAppId' value='" + CustomValidation.nullValueReplace(relatedApp.relatedAppId) + "' type='hidden'>" +
                    "<input id='relatedApps" + row + ".relatedAppName' name='relatedApps[" + row + "].relatedAppName' value='" + CustomValidation.nullValueReplace(relatedApp.relatedAppName) + "' type='hidden' >" +
                    "<input id='relatedApps" + row + ".version' name='relatedApps[" + row + "].version' value='" + CustomValidation.nullValueReplace(relatedApp.version) + "' type='hidden' >" +
                    "<td><span>" + ( row + 1 ) + "</span></td>" +
                    "<td><span>" + relatedApp.relatedAppName + "</span></td>" +
                    "<td class='center'>" +
	                    "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
                    ButtonUtil.getCommonBtnDelete("RelatedAppTab.removeRelatedApp", row) +
	                    "</div>" +
                    "</td>" +
                    "</tr>";
                $('#related_app_tbl > tbody:last-child').append(html);
            }
        } else {
            CustomComponents.emptyTableRow("related_app_tbl", 3);
        }
    };

    var addRelatedAppToApp = function (appId, appName) {
        if (!isAppAlreadyAdd(appId)) {
            var relatedAppObj = {}
            relatedAppObj['relatedAppId'] = appId;
            relatedAppObj['relatedAppName'] = appName;
            relatedApps.push(relatedAppObj);         
            initRelatedAppTable();
        }
    };

    var removeRelatedApp = function (index) {
    	relatedApps.splice(index, 1);
    	initRelatedAppTable();        
    };

    var isAppAlreadyAdd = function (appId) {
        for (var i = 0; i < relatedApps.length; i++) {
            if (relatedApps[i].relatedAppId == appId) {
                return true;
            }
        }
        return false;
    };

    return {

        init: function () {
            initButtons();
            initRelatedAppTable();
        },

        initRelatedAppTable: function () {
        	initRelatedAppTable();
        },

        initAppSelectTable: function () {
        	initAppSelectTable();
        },

        addRelatedApp: function (appId, appName) {
        	addRelatedAppToApp(appId, appName);
        },

        removeRelatedApp: function (index) {
        	removeRelatedApp(index);
        }

    };

}();