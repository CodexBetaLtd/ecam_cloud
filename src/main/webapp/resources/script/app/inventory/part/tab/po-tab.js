var POTab = function () {

    var initOpenPOTable = function () {
    	
        $('#tbl_open_po >tbody').html("");
        $.each(openPOs, function (index, result) {
            var newRow = "<tr> " +
                "<td> " + index + "</td>" +
                "<td> <a href='../purchaseorder/edit?id=" + result.purchaseOrderId + "'> " + result.purchaseOrderCode + " </a> </td>" +
                "<td> " + result.purchaseOrderCode + "</td>" +
                "<td> " + result.itemQtyOnOrder + "</td>" +
                "<td> " + result.itemSupplierId + "</td>" +
                "<td> " + result.itemSupplierPartNo + "</td>" +
                "</tr>";
            $("#tbl_open_po > tbody").append(newRow);
        });
    }

    return {
        init: function () {
            initOpenPOTable();
        }

    };
}();