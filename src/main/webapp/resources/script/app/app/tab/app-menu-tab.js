var AppMenuTab = function () {

    var initButtons = function () {
        $('#btn-new-app-menu').on('click', function () {
            AppMenuTab.initMenuSelectTable();
        });
    };

    var initMenuSelectTable = function () {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../app/menu-select-view';
            $modal.load(url, '', function () {
                MenuSelectModal.init();
                $modal.modal();
            });
        }, 1000);
    };

    var initMenuTable = function () {
        if (appMenus.length > 0) {
            $("#app_menu_tbl > tbody").html("");
            for (var row = 0; row < appMenus.length; row++) {
                var appMenu = appMenus[row];
                var html =
                    "<tr id='row_" + row + "' >" +
                    "<input id='appMenus" + row + ".id' name='appMenus[" + row + "].id' value='" + CustomValidation.nullValueReplace(appMenu.id) + "' type='hidden'>" +
                    "<input id='appMenus" + row + ".menuId' name='appMenus[" + row + "].menuId' value='" + CustomValidation.nullValueReplace(appMenu.menuId) + "' type='hidden'>" +
                    "<input id='appMenus" + row + ".menuName' name='appMenus[" + row + "].menuName' value='" + CustomValidation.nullValueReplace(appMenu.menuName) + "' type='hidden' >" +
                    "<input id='appMenus" + row + ".version' name='appMenus[" + row + "].version' value='" + CustomValidation.nullValueReplace(appMenu.version) + "' type='hidden' >" +
                    "<td><span>" + ( row + 1 ) + "</span></td>" +
                    "<td><span>" + appMenu.menuName + "</span></td>" +
                    "<td class='center'>" +
                    "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
                    ButtonUtil.getCommonBtnDelete("AppMenuTab.removeAppMenu", row) +
                    "</div>" +
                    "</td>" +
                    "</tr>";
                $('#app_menu_tbl > tbody:last-child').append(html);
            }
        } else {
            CustomComponents.emptyTableRow("app_menu_tbl", 3);
        }
    };

    var addMenuToApp = function (menuId, name) {
        if (!isMenuAlreadyAdd(menuId)) {
            var appMenuObj = {}
            appMenuObj['menuId'] = menuId;
            appMenuObj['menuName'] = name;
            appMenus.push(appMenuObj);
            initMenuTable();
        }
    };

    var removeAppMenu = function (index) {
        appMenus.splice(index, 1);
        initMenuTable();
    };

    var isMenuAlreadyAdd = function (menuId) {
        for (var i = 0; i < appMenus.length; i++) {
            if (appMenus[i].menuId == menuId) {
                return true;
            }
        }
        return false;
    };

    return {

        init: function () {
            initButtons();
            initMenuTable();
        },

        initMenuTable: function () {
            initMenuTable();
        },

        initMenuSelectTable: function () {
            initMenuSelectTable();
        },

        addMenu: function (menuId, name) {
            addMenuToApp(menuId, name);
        },

        removeAppMenu: function (index) {
            removeAppMenu(index);
        }

    };

}();