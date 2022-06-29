jQuery(document).ready(function () {

    Main.init();
    MRNAdd.init();
    MRNItemTab.init();
    
    $(document).on('click', '#btnAddPreviousAodReturn', function (event) {
        event.preventDefault();
       // aodItemTab.preViewPreviousAodReturn();
    });


});