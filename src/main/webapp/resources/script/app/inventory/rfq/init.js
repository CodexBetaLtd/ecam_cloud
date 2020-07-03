jQuery(document).ready(function () {

    Main.init();

    RFQAdd.init();
    TabFile.init();
    TabItem.init();
    TabSupplier.init();
    NotificationTab.init();


    for (var i = 0; i < thymeLeafItems.length; i++) {
        TabItem.addItemToList(thymeLeafItems[i])
    }
    ;

    if ($("#rfqId").val() !== undefined || $("#rfqId").val() !== "" || $("#rfqId").val() !== " ") {
        TabItem.populateRFQItems();
    }
    ;
    
    $(function(){    
        $('#rfqView').on('click',function(){
            var pdf_link = $(this).attr('href');
            var iframe = '<div class="iframe-container"><iframe src="'+pdf_link+'"></iframe></div>'
            $.createModal({
            title:'RFQ view',
            message: iframe,
            closeButton:true,
            scrollable:false
            });
            return false;        
        });    
    })


    /*********************************************************************
     * RFQ Item Tab
     *********************************************************************/

    $(document).on('click', '#btnRFQItemView', function (event) {
        event.preventDefault();
        TabItem.rfqItemView();
    });
    $(document).on('click', '#btnRFQItemAsset', function (event) {
        event.preventDefault();
        TabItem.assetSelectModal();
    });
    $(document).on('click', '#btnAddRFQItem', function (event) {
        event.preventDefault();
        TabItem.addRFQItem();
        TabItem.populateRFQItems();
    });




    $(document).on('click', '#btnaddRFQ', function (event) {
        event.preventDefault();
        var supplierBusiness = $("#supplierId").val();
        var supplier = null;
        var email = null;
        for (var i = 0; i < thymeLeafBussiness.length; i++) {
            if (thymeLeafBussiness[i].id == supplierBusiness) {

                supplier = thymeLeafBussiness[i].name;
                email = thymeLeafBussiness[i].primaryEmail;
                supplierAddress = thymeLeafBussiness[i].address;
            }
        }
        var supplierAddress = $("#supplierAddress").val();
        var shipToAddress = $("#shipToAddress").val();
        $("#shiptoaddressinvoice").text(supplierAddress);
        $("#supplieraddressinvoice").text(shipToAddress);
        $("#sendRFQ #supplier").text(supplier);
        $("#sendRFQ #email").text(email);

        $('#sendRFQ').modal('show');
    });


    $(document).on('click', '#btnsendRFQ', function (event) {
        event.preventDefault();
        $("submit").attr("disabled", true);
        if (id == null) {
            $("#model").modal();
        } else {
            $.ajax({
                url: '../rfq/statusChange?id=' + id + '&rfqStatus=' + status, //this is the submit URL
                type: 'GET', //or POST
                success: function (data) {
                    location.reload();
                }
            });
        }
    });


    /*********************************************************************
     * RFQ Toggle Tab
     *********************************************************************/

    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        var target = $(e.target).attr("href") // activated tab
        if ((target == '#preview_tab')) {
            $("#shiptoaddressinvoice").text(supplierAddress);
            $("#supplieraddressinvoice").text(shipToAddress);
        }
    });





});