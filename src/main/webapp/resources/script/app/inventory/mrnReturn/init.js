jQuery(document).ready(function () {

    Main.init();
    MRNReturnAdd.init();
    MRNReturnItemTab.init();
    
    $(document).on('click', '#btnAddPreviousAodReturn', function (event) {
        event.preventDefault();
       // aodItemTab.preViewPreviousAodReturn();
    });


});