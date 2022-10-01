var PersonnelTab = function () {
	 
	 var userSelectModal = function () {
		 var $modal = $('#common-modal');
         CustomComponents.ajaxModalLoadingProgressBar();
         setTimeout(function () {
             var url = '../part/user-select-view';
             $modal.load(url, '', function () {
                 UserSelectModel.init();
                 $modal.modal();
             });
         }, 1000);
    };

     var selectUser = function (id, userId, version, name, email) {

         if (isUserAlreadyAdd(userId)) {
             alert("User Already Added");
         } else {
             var userObj = {};

             CustomValidation.validateFieldNull(userObj, 'id', id);
             CustomValidation.validateFieldNull(userObj, 'userId', userId);
             CustomValidation.validateFieldNull(userObj, 'version', version);
             CustomValidation.validateFieldNull(userObj, 'name', name);
             CustomValidation.validateFieldNull(userObj, 'email', email);

             users.push(userObj);
             resetUserHtmlTable();
         }
     };

     var resetUserHtmlTable = function () {
         var userTable = $('#tbl_user_part').DataTable({});
         userTable.clear().draw();
         for (row = 0; row < users.length; row++) {
         	
             var userObj = users[row];
             userTable.row.add([
                 row + 1 +
                 "<input type=\"hidden\" name=\"assetUserDTOs[" + row + "].id\" value='" + userObj['id'] + "'>" +
                 "<input type=\"hidden\" name=\"assetUserDTOs[" + row + "].userId\" value=\"" + userObj['userId'] + "\">" +
                 "<input type=\"hidden\" name=\"assetUserDTOs[" + row + "].version\" value=\"" + userObj['version'] + "\">" +
                 "<input type=\"hidden\" name=\"assetUserDTOs[" + row + "].name\" value=\"" + userObj['name'] + "\">" +
                 "<input type=\"hidden\" name=\"assetUserDTOs[" + row + "].email\" value=\"" + userObj['email'] + "\">",
                 userObj['name'],
                 userObj['email'],
                 "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
                 "<a onclick='PersonnelTab.removeUser(" + userObj['userId'] + ");' class='btn btn-xs btn-bricky tooltips btnDeleteRow' data-placement='top' data-original-title='Remove' >" +
                 "<i class='fa fa-times fa fa-white' ></i></a></div>"]).draw(false);
         }
     };

    var isUserAlreadyAdd = function (userId) {
         for (var i = 0; i < users.length; i++) {
             if (users[i].userId == userId) {
                 return true;
             }
         }
         return false;
     };

    var removeUser = function (userId) {
         for (var i = 0; i < users.length; i++) {
             if (users[i].userId == userId) {
                 users.splice(i, 1);
             }
         }
         resetUserHtmlTable();
     };

    return {
    	
    	userSelectModal : function () {
    		userSelectModal();
    	},

        selectUser: function (id, userId, version, name, email) {
            selectUser(id, userId, version, name, email);
        },

        removeUser: function (userId) {
            removeUser(userId);
    	}
    	
    };

}();