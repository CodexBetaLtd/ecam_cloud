var AppWigetTab = function () {

    var initButtons = function () {
    	console.log("xx")
        $('#btn-new-wiget-menu').on('click', function () {
        	
        	AppWigetTab.initWigetSelectTable();
        });
    };

    var initWigetSelectTable = function () {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../app/wiget-select-view';
            $modal.load(url, '', function () {
            	WigetSelectModal.init();
                $modal.modal();
            });
        }, 1000);
    };

    var initWigetTable = function () {
        if (appWigets.length > 0) {
            $("#app_wiget_tbl > tbody").html("");
            for (var row = 0; row < appWigets.length; row++) {
                var appWiget = appWigets[row];
                var html =
                    "<tr id='row_" + row + "' >" +
                    "<input id='appWigets" + row + ".id' name='appWigets[" + row + "].id' value='" + CustomValidation.nullValueReplace(appWiget.id) + "' type='hidden'>" +
                    "<input id='appWigets" + row + ".wigetId' name='appWigets[" + row + "].wigetId' value='" + CustomValidation.nullValueReplace(appWiget.wigetId) + "' type='hidden'>" +
                    "<input id='appWigets" + row + ".wigetName' name='appWigets[" + row + "].wigetName' value='" + CustomValidation.nullValueReplace(appWiget.wigetName) + "' type='hidden' >" +
                    "<input id='appWigets" + row + ".version' name='appWigets[" + row + "].version' value='" + CustomValidation.nullValueReplace(appWiget.version) + "' type='hidden' >" +
                    "<td><span>" + ( row + 1 ) + "</span></td>" +
                    "<td><span>" + appWiget.wigetName + "</span></td>" +
                    "<td class='center'>" +
                    "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
                    ButtonUtil.getCommonBtnDelete("AppWigetTab.removeAppWiget", row) +
                    "</div>" +
                    "</td>" +
                    "</tr>";
                $('#app_wiget_tbl > tbody:last-child').append(html);
            }
        } else {
            CustomComponents.emptyTableRow("app_wiget_tbl", 3);
        }
    };

    var addWigetToApp = function (wigetId, name) {
        if (!isWigetAlreadyAdd(wigetId)) {
            var appWigetObj = {}
            appWigetObj['wigetId'] = wigetId;
            appWigetObj['wigetName'] = name;
            appWigets.push(appWigetObj);
            initWigetTable();
        }
    };

    var removeAppWiget = function (index) {
    	appWigets.splice(index, 1);
    	initWigetTable();
    };

    var isWigetAlreadyAdd = function (wigetId) {
        for (var i = 0; i < appWigets.length; i++) {
            if (appWigets[i].wigetId == wigetId) {
                return true;
            }
        }
        return false;
    };

    return {

        init: function () {
            initButtons();
            initWigetTable();
        },

        initWigetTable: function () {
        	initWigetTable();
        },

        initWigetSelectTable: function () {
        	initWigetSelectTable();
        },

        addWiget: function (wigetId, name) {
            addWigetToApp(wigetId, name);
        },

        removeAppWiget: function (index) {
        	removeAppWiget(index);
        }

    };

}();