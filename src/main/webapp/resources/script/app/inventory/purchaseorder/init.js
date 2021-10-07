jQuery(document).ready(function () {

    Main.init();
    TabFile.init();
    TaxTab.init();
    PurchaseOrderAdd.init();
    TabShippingReceiving.init();
    TabAccount.init();

    /*********************************************************************
     * init
     *********************************************************************/
    if ($("#poId").val() !== undefined || $("#poId").val() !== "" || $("#poId").val() !== " ") {
        poNotification.populatePONotification();
        poDiscussionTab.populateDiscussion();
        tabAdditionalCost.populateAdditionalCost();
        TabItem.populatePOItems();
    }


    /*********************************************************************
     * Notification
     *********************************************************************/
    $(document).on('click', '#btnMasterModalNotification', function (event) {
        event.preventDefault();
        poNotification.getNotificationModal();
        // poNotification.initNotification();
    });

    $(document).on('click', '#addPONotification', function (event) {
        event.preventDefault();
        var objEmptyValidation = PurchaseOrderValidation.isNullOrIsEmpty(['#notificationAssignedUser'], null);
        var objCheckedValidation = PurchaseOrderValidation.isCheckedAny(null, ['poNotificationCls']);
        if (objEmptyValidation.successType == true && objCheckedValidation.successType == true) {
            poNotification.addNotification(this);
            poNotification.populatePONotification();
            $("#master-modal").modal("hide");
        } else {
            alert(objEmptyValidation.errorName);
            alert(objCheckedValidation.errorName);
        }
    });

    $(document).on('click', '.btnDeletePONotification', function (event) {
        event.preventDefault();
        poNotification.removeNotification(this);
        poNotification.populatePONotification();
    });

    $(document).on('ifToggled', 'input', function () {
        // alert($(this).attr("id") +"  ---  "+ $(this).prop("checked")); // alert value
        if ($(this).prop("checked")) {
            $('#hdn_' + $(this).attr("id")).val(true);
        } else {
            $('#hdn_' + $(this).attr("id")).val(false);
        }
    });


    /*********************************************************************
     * Purchase Order Discussion
     *********************************************************************/
    $(document).on('click', '#btnViewDiscussion', function (event) {
        event.preventDefault();
        poDiscussionTab.getDiscussionView();
    });

    $(document).on('click', '#btnAddDiscussion', function (event) {
        event.preventDefault();
        poDiscussionTab.addDiscussion();
        poDiscussionTab.populateDiscussion();
    });

    $(document).on('click', '.btnDeleteDiscussion', function (event) {
        event.preventDefault();
        poDiscussionTab.deleteDiscussion(this);
        poDiscussionTab.populateDiscussion();
    });


    /*********************************************************************
     * Additional Cost
     *********************************************************************/
    $(document).on('click', '#btnViewAdditionalCost', function (event) {
        tabAdditionalCost.getAdditionalCostModalView();
    });

    $(document).on('click', '#btnAddAdditionalCost', function (event) {
        tabAdditionalCost.addAdditionalCost();
        tabAdditionalCost.populateAdditionalCost();
    });

    $(document).on('click', '.btnEditClsAdditionalCost', function (event) {
        tabAdditionalCost.findAdditionalCost($(this).attr("id"));
    });

    $(document).on('click', '.btnDelClsAdditionalCost', function (event) {
        tabAdditionalCost.removeAdditionalCost($(this).attr("id"));
        tabAdditionalCost.populateAdditionalCost();
        TabItem.populatePOItems();
    });


    $(document).on('change', '#poAdditionalCostTypeId', function (event) {
        tabAdditionalCost.additionalCostType();
        tabAdditionalCost.populateAdditionalCost();
    });

    $(document).on('ifChecked', '#isOverridePoItemTax', function (event) {
        isOverridePoItemTax = true;
    });

    $(document).on('ifUnchecked', '#isOverridePoItemTax', function (event) {
        isOverridePoItemTax = false;
        totalTax = 0;
    });



    /*********************************************************************
     * Item Tab
     *********************************************************************/
    $(document).on('click', '#btn-new-item', function (event) {
        event.preventDefault();
        TabItem.poItemView();
    });



    $(document).on('click', '#asset-select-modal-btn', function (event) {
        event.preventDefault();
        ItemAddModal.poItemAssetView();
    });

    $(document).on('click', '#site-select-modal-btn', function (event) {
        event.preventDefault();
        ItemAddModal.siteSelectModal();
    });

    $(document).on('click', '#source-asset-select-modal-btn', function (event) {
        event.preventDefault();
        ItemAddModal.sourceAssetSelectModal();
    });


    $(document).on('click', '#wo-select-modal-btn', function (event) {
        event.preventDefault();
        ItemAddModal.woSelectModal();
    });


    $(document).on('click', '#btn-new-rfq', function (event) {
        event.preventDefault();
        TabItem.generateRFQ();
    });


});