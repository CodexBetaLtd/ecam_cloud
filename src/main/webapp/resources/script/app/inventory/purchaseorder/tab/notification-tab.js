/***********************
 * Notification
 * *********************/
var poNotification = function () {

    function initNotification() {
        $("#notificationAssignedUser").select2({
            dropdownParent: $("#notificationModalSection"),
            placeholder: "Select User"
        });
    };

    function initNotificationCheckBoxes() {
        $('input[type="checkbox"].grey, input[type="radio"].grey').iCheck({
            checkboxClass: 'icheckbox_minimal-grey',
            radioClass: 'iradio_minimal-grey',
            increaseArea: '10%', // optional
        });
    };

    var populateNotification = function () {
        $('#tbl_po_notification tbody').html("");

        for (var row = 0; row < purchaseOrderNotifications.length; row++) {
            var poNotification = purchaseOrderNotifications[row];

            var poNotificationId = poNotification.id == null ? '' : poNotification.id;
            var poId = poNotification.purchaseOrderId == null ? '' : poNotification.purchaseOrderId;
            var poUserId = poNotification.userId == null ? '' : poNotification.userId;
            var poUserName = poNotification.userName == null ? '' : poNotification.userName;

            var onAssignment = poNotification.notifyOnAssignment == true ? "checked" : " ";
            var onStatusChange = poNotification.notifyOnStatusChange == true ? 'checked' : '';
            var onCompletion = poNotification.notifyOnCompletion == true ? 'checked' : '';
            var onTaskCompleted = poNotification.notifyOnTaskCompleted == true ? 'checked' : '';
            var onOnlineOffline = poNotification.notifyOnOnlineOffline == true ? 'checked' : '';
            var htmlRow =
                "<tr id='poNotificationRow_" + row + "' >" +
                "<td>" + (row + 1) + "</td>" +
                "<td>" + poUserName + "</td>" +


                "<td>" +
                "<div class='checkbox'>" +
                "<input id='onAssignment_" + row + "' type='checkbox' " + onAssignment + " class='grey clsNotification'>" +
                "</div>" +
                "</td>" +

                "<td>" +
                "<div class='checkbox'>" +
                "<input id='onStatusChange_" + row + "' type='checkbox' " + onStatusChange + " class='grey clsNotification'>" +
                "</div>" +
                "</td>" +

                "<td>" +
                "<div class='checkbox'>" +
                "<input id='onCompletion_" + row + "' type='checkbox' " + onCompletion + " class='grey clsNotification'>" +
                "</div>" +
                "</td>" +

                "<td>" +
                "<div class='checkbox'>" +
                "<input id='onTaskCompleted_" + row + "' type='checkbox' " + onTaskCompleted + " class='grey clsNotification'>" +
                "</div>" +
                "</td>" +

                "<td>" +
                "<div class='checkbox'>" +
                "<input id='onOnlineOffline_" + row + "' type='checkbox' " + onOnlineOffline + " class='grey clsNotification'>" +
                "</div>" +
                "</td>" +

                "<td> " +
                "<div> " +
                "<button type='button' id='" + row + "' class='btn btn-xs btn-bricky tooltips btnDeletePONotification' data-placement='top' data-original-title='Remove' >" +
                "<i class='fa fa-times fa fa-white' ></i>" +
                "</button>" +
                "</div> " +
                "</td>" +
                "</tr>";
            $('#tbl_po_notification > tbody:last-child').append(htmlRow);
        }
        addNotificationRow(purchaseOrderNotifications);
    };

    function addNotificationRow(purchaseOrderNotifications) {
        for (var row = 0; row < purchaseOrderNotifications.length; row++) {
            var poNotification = purchaseOrderNotifications[row];
            var poNotificationId = poNotification.id == null ? '' : poNotification.id;
            var poId = poNotification.purchaseOrderId == null ? '' : poNotification.purchaseOrderId;
            var poUserId = poNotification.userId == null ? '' : poNotification.userId;

            var onAssignment = poNotification.notifyOnAssignment == null ? false : poNotification.notifyOnAssignment;
            var onStatusChange = poNotification.notifyOnStatusChange == null ? false : poNotification.notifyOnStatusChange;
            var onCompletion = poNotification.notifyOnCompletion == null ? false : poNotification.notifyOnCompletion;
            var onTaskCompleted = poNotification.notifyOnTaskCompleted == null ? false : poNotification.notifyOnTaskCompleted;
            var onOnlineOffline = poNotification.notifyOnOnlineOffline == null ? false : poNotification.notifyOnOnlineOffline;
            var htmlRow =
                "<tr id='tblPONotificationRow_" + row + "' >" +
                "<input id='poNotificationDTOId_" + row + "' name='notificationDTOs[" + row + "].id' value='" + poNotificationId + "' type='hidden'>" +
                "<input id='poNotificationDTOpoId_" + row + "' name='notificationDTOs[" + row + "].purchaseOrderId' value='" + poId + "' type='hidden' >" +
                "<input id='poNotificationDTOUserId_" + row + "' name='notificationDTOs[" + row + "].userId' value='" + poUserId + "' type='hidden' >" +

                "<input id='hdn_onAssignment_" + row + "' name='notificationDTOs[" + row + "].notifyOnAssignment'    value='" + onAssignment + "' type='hidden' >" +
                "<input id='hdn_onStatusChange_" + row + "' name='notificationDTOs[" + row + "].notifyOnStatusChange'  value='" + onStatusChange + "' type='hidden' >" +
                "<input id='hdn_onCompletion_" + row + "' name='notificationDTOs[" + row + "].notifyOnCompletion'    value='" + onCompletion + "' type='hidden' >" +
                "<input id='hdn_onTaskCompleted_" + row + "' name='notificationDTOs[" + row + "].notifyOnTaskCompleted' value='" + onTaskCompleted + "' type='hidden' >" +
                "<input id='hdn_onOnlineOffline_" + row + "' name='notificationDTOs[" + row + "].notifyOnOnlineOffline' value='" + onOnlineOffline + "' type='hidden' >" +
                "</tr>";
            $('#tbl_po_notification > tbody:last-child').append(htmlRow);
        }
    };


    function addNotification(obj) {
        var notification = {};
        notification['id'] = "";
        notification['userId'] = $("#notificationAssignedUser").val();
        notification['userName'] = $('#notificationAssignedUser :selected').text();
        notification['notifyOnAssignment'] = $("#notifyOnAssignment").prop("checked") == true ? true : false;
        notification['notifyOnStatusChange'] = $("#notifyOnStatusChange").prop("checked") == true ? true : false;
        notification['notifyOnCompletion'] = $("#notifyOnCompletion").prop("checked") == true ? true : false;
        notification['notifyOnTaskCompleted'] = $("#notifyOnTaskCompleted").prop("checked") == true ? true : false;
        notification['notifyOnOnlineOffline'] = $("#notifyOnOnlineOffline").prop("checked") == true ? true : false;
        purchaseOrderNotifications.push(notification);
    };

    var getNotificationModal = function () {
        var $modal = $('#common-modal');
        //$('body').modalmanager('loading');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../purchaseorder/poNotificationView';
            $modal.load(url, '', function () {
                initNotification();
                initNotificationCheckBoxes();
                $modal.modal();
            });
        }, 100);
    };


    function removeNotification(obj) {
        purchaseOrderNotifications.splice($(obj).attr("id"), 1);
    };

    var clearNotificationModal = function () {
        $('#notificationId').val("");
        $('#notificationVersion').val("");
        $('#notificationRowIndex').val("");
        $('#notificationAssignedUser').val("");

        $('#notifyOnAssignment').val("");
        $('#notifyOnStatusChange').val("");
        $('#notifyOnCompletion').val("");
        $('#notifyOnTaskCompleted').val("");
        $('#notifyOnOnlineOffline').val("");

    };

    return {

        initNotificationModal: function () {
            initNotification();
            initNotificationCheckBoxes();
        },

        getNotificationModal: function () {
            getNotificationModal();
        },

        populatePONotification: function () {
            populateNotification();
            poNotification.initNotificationModal();
        },

        addNotification: function (obj) {
            addNotification(obj);
        },

        removeNotification: function (obj) {
            removeNotification(obj);
        },



        clearNotificationModal: function () {
            clearNotificationModal();
        }

    };
}();