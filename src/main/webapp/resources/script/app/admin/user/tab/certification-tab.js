var TabUserCertification = function () {

    var loadUserCertificationModal = function () {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../userProfile/usercertificationmodalview';
            $modal.load(url, function () {
                UserCertificationAddModal.init();
                $modal.modal();
            });
        }, 1000);
    };

    var resetUserCertificationHtmlTable = function () {
        if (userCertifications.length > 0) {
            var row, userCertification;
            $("#userCertificationTbl > tbody").html("");
            for (row = 0; row < userCertifications.length; row++) {
                userCertification = userCertifications[row];
                var html = "<tr id='row_" + row + "' >" +
                    "<input id='useCertificationDTOs" + row + ".id' name='useCertificationDTOs[" + row + "].id' value='" + userCertification.id + "' type='hidden'>" +
                    "<input id='useCertificationDTOs" + row + ".version' name='useCertificationDTOs[" + row + "].version' value='" + userCertification.version + "' type='hidden'>" +
                    "<input id='useCertificationDTOs" + row + ".userId' name='useCertificationDTOs[" + row + "].userId' value='" + userCertification.userId + "' type='hidden'>" +
                    "<input id='useCertificationDTOs" + row + ".userCertificationLevel' name='useCertificationDTOs[" + row + "].userCertificationLevel' value='" + userCertification.userCertificationLevel + "' type='hidden' >" +
                    "<input id='useCertificationDTOs" + row + ".userCertificationLevelName' name='useCertificationDTOs[" + row + "].userCertificationLevelName' value='" + userCertification.userCertificationLevelName + "' type='hidden' >" +
                    "<input id='useCertificationDTOs" + row + ".certificationTypeId' name='useCertificationDTOs[" + row + "].certificationTypeId' value='" + userCertification.certificationTypeId + "' type='hidden' >" +
                    "<input id='useCertificationDTOs" + row + ".certificationTypeName' name='useCertificationDTOs[" + row + "].certificationTypeName' value='" + userCertification.certificationTypeName + "' type='hidden' >" +
                    "<input id='useCertificationDTOs" + row + ".description' name='useCertificationDTOs[" + row + "].description' value='" + userCertification.description + "' type='hidden' >" +
                    "<input id='useCertificationDTOs" + row + ".validToDate' name='useCertificationDTOs[" + row + "].validToDate' value='" + userCertification.validToDate + "' type='hidden' >" +
                    "<input id='useCertificationDTOs" + row + ".validFromDate' name='useCertificationDTOs[" + row + "].validFromDate' value='" + userCertification.validFromDate + "' type='hidden' >" +
                    "<input id='useCertificationDTOs" + row + ".userCertificationName' name='useCertificationDTOs[" + row + "].userCertificationName' value='" + userCertification.userCertificationName + "' type='hidden' >" +
                    "<td>" + (row + 1) + "</td>" +
                    "<td>" + userCertification['userCertificationName'] + "</td>" +
                    "<td>" + userCertification['certificationTypeName'] + "</td>" +
                    "<td>" + userCertification['userCertificationLevelName'] + "</td>" +
                    "<td>" + userCertification['validFromDate'] + "</td>" +
                    "<td>" + userCertification['validToDate'] + "</td>" +
                    "<td>" + userCertification['description'] + "</td>" +
                    "<td class='center'>" +
                    "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
                    ButtonUtil.getCommonBtnEdit("TabUserCertification.editUserCertification", userCertification.userCertificationIndex) +
                    ButtonUtil.getCommonBtnDelete("TabUserCertification.removeCertification", userCertification.userCertificationIndex) +
                    "</div></td></tr>";

                $('#userCertificationTbl> tbody:last-child').append(html);
            }
        } else {
            CustomComponents.emptyTableCustomeMessageRow('userCertificationTbl', 8, "Please Add Certification for the User.");
        }
    };

    var addUserCertificationToList = function (userCertification) {

        var userCertificationObj = {}

        if (userCertification.userCertificationIndex != null && userCertification.userCertificationIndex != "" && userCertification.userCertificationIndex >= 0) {
            userCertificationObj = getCertificationByIndex(userCertification.userCertificationIndex);
            setCommonDataToUserCertificationObj(userCertification, userCertificationObj);
        } else {
            if (userCertifications.length == 0) {
                userCertificationObj['userCertificationIndex'] = 0;
            } else {
            	CustomValidation.validateFieldNull(userCertificationObj, 'userCertificationIndex', userCertifications.length);
            }            
            setCommonDataToUserCertificationObj(userCertification, userCertificationObj);
            userCertifications.push(userCertificationObj);            
        }
    };

    var setCommonDataToUserCertificationObj = function (updatedUserCertificationObj, userCertificationObj) {
    	CustomValidation.validateFieldNull(userCertificationObj, 'id', updatedUserCertificationObj.id);
    	CustomValidation.validateFieldNull(userCertificationObj, 'version', updatedUserCertificationObj.version);
    	CustomValidation.validateFieldNull(userCertificationObj, 'userId', updatedUserCertificationObj.userId);
    	CustomValidation.validateFieldNull(userCertificationObj, 'validFromDate', updatedUserCertificationObj.validFromDate);
    	CustomValidation.validateFieldNull(userCertificationObj, 'validToDate', updatedUserCertificationObj.validToDate);
    	CustomValidation.validateFieldNull(userCertificationObj, 'userCertificationLevel', updatedUserCertificationObj.userCertificationLevel);
    	CustomValidation.validateFieldNull(userCertificationObj, 'userCertificationLevelName', updatedUserCertificationObj.userCertificationLevelName);
    	CustomValidation.validateFieldNull(userCertificationObj, 'certificationTypeId', updatedUserCertificationObj.certificationTypeId);
    	CustomValidation.validateFieldNull(userCertificationObj, 'certificationTypeName', updatedUserCertificationObj.certificationTypeName);
    	CustomValidation.validateFieldNull(userCertificationObj, 'description', updatedUserCertificationObj.description);
    	CustomValidation.validateFieldNull(userCertificationObj, 'userCertificationName', updatedUserCertificationObj.userCertificationName);
    };


    var getCertificationByIndex = function (userCertificationIndex) {
        for (var i = 0; i < userCertifications.length; i++) {
            if (userCertifications[i].userCertificationIndex == userCertificationIndex) {
                return userCertifications[i];
            }
        }
    };

    var addUserCertification = function () {

        var userCertification = {};
        var index = "";
        if ($('#userCertificationIndex').val() != null && $('#userCertificationIndex').val() != "" && $('#userCertificationIndex').val() >= 0) {
            index = $('#userCertificationIndex').val();
        }

        if (!isUserCertificationAlreadyAdd($('#certificationId').val(), $('#certificationLevelId').val(), index)) {
            createUserCertificate(userCertification);
            addUserCertificationToList(userCertification);
            resetUserCertificationHtmlTable();
            $('#common-modal').modal('toggle'); 
        } else {
            alert('Duplicate User Certification. Please Edit Relevant Certificate.');
        } 
    };

    var createUserCertificate = function (userCertification) {
    	
        userCertification['id'] = $('#id').val();
        userCertification['version'] = $('#version').val();
        userCertification['userCertificationIndex'] = $('#userCertificationIndex').val();
        userCertification['userId'] = $('#userId').val();
        userCertification['validToDate'] = $('#validTo').val();
        userCertification['validFromDate'] = $('#validFrom').val();
        userCertification['userCertificationLevelName'] = $('#certificationLevelId option:selected').text().trim();
        userCertification['userCertificationLevel'] = $('#certificationLevelId').val();
        userCertification['certificationTypeId'] = $('#certificationTypeId').val();
        userCertification['certificationTypeName'] = $('#certificationTypeName').val();
        userCertification['description'] = $('#description').val();
        userCertification['userCertificationName'] = $('#name').val();
        
    };

    var editUserCertification = function (userCertificationIndex) {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../userProfile/usercertificationmodalview';
            $modal.load(url, '', function () {
                var userCertification = getCertificationByIndex(userCertificationIndex);
                UserCertificationAddModal.init();
                fillUserCertificationEditForm(userCertification);
                $modal.modal();
            });
        }, 1000);
    };

    var fillUserCertificationEditForm = function (userCertification) {
        $('#id').val(userCertification['id']);
        $('#userCertificationIndex').val(userCertification['userCertificationIndex']);
        $('#userId').val(userCertification['userId']);
        $('#version').val(userCertification['version']);            
        $('#validTo').val(userCertification['validToDate']);
        $('#validFrom').val(userCertification['validFromDate']);
        $('#certificationLevelId').val(userCertification['userCertificationLevel']).trigger('change');
        $('#certificationTypeId').val(userCertification['certificationTypeId']);
        $('#certificationTypeName').val(userCertification['certificationTypeName']);
        $('#description').val(userCertification['description']);
        $('#name').val(userCertification['userCertificationName']);
    };

  
    var isUserCertificationAlreadyAdd = function (certificationTypeId, userCertificationLevel, index) {
        for (var i = 0; i < userCertifications.length; i++) {
            if (userCertifications[i].certificationTypeId == certificationTypeId || certificationTypeId == "") {
                if (userCertifications[i].userCertificationLevel == userCertificationLevel &&
                    ( index == "" || userCertifications[i].userCertificationIndex != index )) {
                    return true;
                }
            }
        }
        return false;
    };
    
    var removeCertification = function (userCertificationIndex) {
        for (var i = 0; i < userCertifications.length; i++) {
            if (userCertifications[i].userCertificationIndex == userCertificationIndex) {
                userCertifications.splice(i, 1);
                updateIndexes();
                resetUserCertificationHtmlTable();
                break;
            }
        }
    };
    
    var updateIndexes = function () {
        for (var i = 0; i < userCertifications.length; i++) {
            userCertifications[i].userCertificationIndex = i;
        }
    };

    return {
    	
        loadUserCertificationModal: function () {
            loadUserCertificationModal();
        },

        addUserCertification: function () {
            addUserCertification();
        },

        addUserCertificationToList: function (userCertification) {
            addUserCertificationToList(userCertification);
        },

        removeCertification: function (userCertificationIndex) {
            removeCertification(userCertificationIndex);
        },

        editUserCertification: function (index) {
            editUserCertification(index);
        },
        
        isUserCertificationAlreadyAdd: function (userCertification) {
            isUserCertificationAlreadyAdd(userCertification);
        },

        resetUserCertificationHtmlTable: function () {
            resetUserCertificationHtmlTable();
        }
    };
}();