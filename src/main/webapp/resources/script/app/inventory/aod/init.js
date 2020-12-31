jQuery(document).ready(function () {

    Main.init();
    AODAdd.init();
    AodItemTab.init();
    
    $(document).on('click', '#btnAddPreviousAodReturn', function (event) {
        event.preventDefault();
        aodItemTab.preViewPreviousAodReturn();
    });

    $(document).on('click', '#saveAOD', function (event) {
        event.preventDefault();
        if (aodItemList.length > 0) {
            $("#btn_aod_submit").trigger("click");
        } else {
            alert('Please add One or More Item');
        }
    });

});