var TabPersonal = function () {
	
	var initButtons = function () {		

	    $('#btnNewUser').on('click', function () {
	        TabPersonal.userSelectModal();
	    });

	    $(document).on('click', '.btnDeleteRow', function () {
	        ($(this).closest("tr")).remove();
	    });
	};

    var userSelectModal = function () {
    	var bizId = $('#businessId').val();
    	if ( bizId != null && bizId > 0 ) {
	        var $modal = $('#common-modal');
	        CustomComponents.ajaxModalLoadingProgressBar();
	        setTimeout(function () {
	            var url = '../../asset/usermodelview';
	            $modal.load(url, '', function () {
	                UserSelectModel.init(bizId);
	                $modal.modal();
	            });
	        }, 2000);
    	} else {
			alert("Please Select a Business First.");
		} 
    };
    
    var setUserList = function (thymeLeafUserList) {
        for (var i = 0; i < thymeLeafUserList.length; i++) {
            console.log(thymeLeafUserList[i])
            addUserToList(thymeLeafUserList[i].id, thymeLeafUserList[i].userId, thymeLeafUserList[i].version, thymeLeafUserList[i].name, thymeLeafUserList[i].email);
            resetUserHtmlTable();
        }
        ;
    };

    var addUser = function (Id, userId, version, name, email) {
        if (!isUserAlreadyAdd(userId)) {
            addUserToList(Id, userId, version, name, email);
            resetUserHtmlTable();
        } else {
            alert("User Already Added.");
        }
    };

    var addUserToList = function (Id, userId, version, name, email) {
        var userObj = {}

        if (userList.length == 0) {
            userObj['userIndex'] = 0;
        } else {
        	CustomValidation.validateFieldNull(userObj, 'userIndex', userList.length);
        }

        CustomValidation.validateFieldNull(userObj, 'id', Id);
        CustomValidation.validateFieldNull(userObj, 'userId', userId);
        CustomValidation.validateFieldNull(userObj, 'name', name);
        CustomValidation.validateFieldNull(userObj, 'email', email);
        CustomValidation.validateFieldNull(userObj, 'version', version);
        userList.push(userObj);
    };

    var resetUserHtmlTable = function () {

        if (userList.length > 0) {
            var row, user;
            $("#userTbl > tbody").html("");

            for (row = 0; row < userList.length; row++) {
                user = userList[row];
                var html = "<tr id='row_" + row + "' >" +
                    "<input id='assetUserDTOs" + row + ".id' name='assetUserDTOs[" + row + "].id' value='" + user['id'] + "' type='hidden'>" +
                    "<input id='assetUserDTOs" + row + ".userId' name='assetUserDTOs[" + row + "].userId' value='" + user['userId'] + "' type='hidden' >" +
                    "<input id='assetUserDTOs" + row + ".name' name='assetUserDTOs[" + row + "].name' value='" + user['name'] + "' type='hidden' >" +
                    "<input id='assetUserDTOs" + row + ".email' name='assetUserDTOs[" + row + "].email' value='" + user['email'] + "' type='hidden' >" +
                    "<input id='assetUserDTOs" + row + ".version' name='assetUserDTOs[" + row + "].version' value='" + user['version'] + "' type='hidden' >" +
                    "<td><span>" + ( row + 1 ) + "</span></td>" +
                    "<td><span>" + user.name + "</span></td>" +
                    "<td class='hidden-xs'><span>" + user.email + "</span></td>" +
                    "<td class='center'>" +
                    "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
                    ButtonUtil.getCommonBtnDelete("TabPersonal.removeUser", user.userIndex) +
                    "</div></td></tr>";
                $('#userTbl > tbody:last-child').append(html);
            }
        } else {
            $("#userTbl > tbody").html("<tr><td colspan='4' align='center'>Please Add User.</td></tr>");
        }
    };

    var isUserAlreadyAdd = function (userId) {
        for (var i = 0; i < userList.length; i++) {
            if (userList[i].userId == userId) {
                return true;
            }
        }
        return false;
    };

    var getUserByIndex = function (userIndex) {
        for (var i = 0; i < userList.length; i++) {
            if (userList[i].userIndex == userIndex) {
                return userList[i];
            }
        }
    };

    var updateIndexes = function () {
        for (var i = 0; i < userList.length; i++) {
            userList[i].userIndex = i;
        }
    };

    var removeUser = function (userIndex) {
        var user = getUserByIndex(userIndex);

        if (user.userId == "" || user.userId == null) {
            alert("Cant remove user.");
        } else {
            for (var i = 0; i < userList.length; i++) {
                if (userList[i].userIndex == userIndex) {
                    userList.splice(i, 1);
                    updateIndexes();
                    resetUserHtmlTable();
                    break;
                }
            }
        }
    };
    
    var initUserList = function () {
    	setUserList(thymeLeafUserList);
    };

    return {
    	
    	init: function () {
    		initButtons();
    		initUserList();
    		resetUserHtmlTable();
    	},

        setUserList: function (thymeLeafUserList) {
            setUserList(thymeLeafUserList);
        },

        addUser: function (Id, userId, version, name, email) {
            addUser(Id, userId, version, name, email);
        },

        removeUser: function (userIndex) {
            removeUser(userIndex);
        },

        userSelectModal: function () {
            userSelectModal();
        },

        resetUserHtmlTable: function () {
            resetUserHtmlTable();
        }
    };

}();
