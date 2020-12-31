jQuery(document).ready(function () {

    Main.init();
    InventoryGroupAdd.init();
    InventoryGroupPartTab.init();
    
    $(document).on('click', '#btn-add-part', function (event) {
        event.preventDefault();
        InventoryGroupPartTab.getInventoryGroupPartView();
    });

});