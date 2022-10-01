var TabTechnician = function () {
	
	var userAddModal = function () {
		 var $modal = $('#common-modal');
        //$('body').modalmanager('loading');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../project/usermodelview';
            $modal.load(url, '', function () {
                UserSelectModel.init();
                $modal.modal();
            });
        }, 1000);
    };

	function addUser(id, name, code) {
        if (!isUserAlreadyAdd(id)) {
            addUserToList(id, name, code);
            resetHtmlTable();
        }
        $('#common-modal').modal('toggle');
    };

    function isUserAlreadyAdd(id) {
        for (var i = 0; i < users.length; i++) {
            if (users[i].id == id) {
                return true;
            }
        }
        return false;
    };

    function resetHtmlTable() {

        if (users.length > 0) {
            var row, user;
            $("#project-user-tbl > tbody").html("");

            for (row = 0; row < users.length; row++) {
                user = users[row];
                var html = "<tr id='row_" + row + "' >" +
                    "<input id='users" + row + ".id' name='users[" + row + "].id' value='" + user.id + "' type='hidden'>" +
                    "<input id='users" + row + ".fullName' name='users[" + row + "].fullName' value='" + user.fullName + "' type='hidden' >" +
                    "<input id='users" + row + ".personalCode' name='users[" + row + "].personalCode' value='" + user.personalCode + "' type='hidden' >" +
                    "<td class='hidden-xs'><span>" + user.fullName + "</span></td>" +
                    "<td><span>" + user.personalCode + "</span></td>" +
                    "<td class='center'>" +
                    "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
                    ButtonUtil.getCommonBtnDelete("TabTechnician.removeUser", user.id) +
                    "</div>" +
                    "</td></tr>";

                $('#project-user-tbl > tbody:last-child').append(html);
            }
        } else {
            $("#project-user-tbl > tbody").html("<tr><td colspan='3' align='center'>Please Add Users for the Project.</td></tr>");
        }
    };

    function addUserToList(id, name, code) {
        var userObj = {}
        userObj['id'] = id;
        userObj['fullName'] = name;
        userObj['personalCode'] = code;
        users.push(userObj);
    };

    function removeUser(id) {
        for (var i = 0; i < users.length; i++) {
            if (users[i].id == id) {
                users.splice(i, 1);
            }
        }
        resetHtmlTable();
    };
    
    return {
    	addUser: function (id, name, code) {
    		addUser(id, name, code);
        },

        removeUser: function (id) {
        	removeUser(id);
        },

        resetHtmlTable: function () {
        	resetHtmlTable();
        },

        addUserToList: function (id, name, code) {
        	addUserToList(id, name, code);
        },
        
        userAddModal : function () {
        	userAddModal();
        }
    };
}();