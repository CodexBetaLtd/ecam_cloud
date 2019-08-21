jQuery(document).ready(function () {

    Main.init();

    AODReturnAdd.init();
    /*********************************************************************
     * init
     *********************************************************************/
    if ($("#aodReturnId").val() !== undefined || $("#aodReturnId").val() !== "" || $("#aodReturnId").val() !== " ") {
        aodReturnItemTab.populateAODReturnItems();
    }

    $(document).on('click', '#btnAODView', function (event) {
        event.preventDefault();
        aodView();
    });

    var aodView = function () {
        var $modal = $('#master-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../aodReturn/aodView';
            $modal.load(url, '', function () {
                // dtAODWorkOrders.dtAODWorkOrderList();
                $modal.modal();
            });
        }, 1000);
    };
//    var setAOD = function (id, aodNo) {
//        $("#aodId").val(id);
//        $("#aodNo").val(aodNo);
//    };

    /*********************************************************************
     * Item Tab
     *********************************************************************/
    $(document).on('click', '#btnAODReturnItemView', function (event) {
        event.preventDefault();
        var itemPartId = $("#aodReturnAODId").val();
        if (itemPartId === "") {
            alert("Please select AOD");
        } else {
            aodReturnItemTab.aodReturnItemView();
        }

    });

    /*    $(document).on('click', '#btnAODReturnItemAdd', function () {
     aodReturnModal.addAODReturnItem(this);
     aodReturnItemTab.addAODReturnItem(this);
     aodReturnItemTab.populateAODReturnItems();
     });*/

    $(document).on('click', '#saveAODReturn', function (event) {
        event.preventDefault();
        if (aodReturnItemList.length > 0) {
            $("#btn_aodReturn_submit").trigger("click");
        } else {
            alert('Please add One or More Item');
        }
    });

});