var MenuSelectModal = function () {

    var initDataTable = function () {
        $('#menu_select_tbl').dataTable().fnDestroy();
        var oTable = $('#menu_select_tbl').dataTable({
            ajax: {
                url: "../restapi/app/menus",
                dataSrc: ''
            },

            columns: [
                {
                    width: "2%",
                    render: function (data, type, row, meta) {
                        return meta.row + meta.settings._iDisplayStart + 1;
                    }
                },
                {
                    data: 'name'
                }],
            aoColumnDefs: [{
                targets: 2,//index of column starting from 0
                data: "id",  //this name should exist in your JSON response
                width: "3%",
                render: function (data, type, row, meta) {
                    var vars = [data, row.name];
                    return "<div align='center'>" + ButtonUtil.getCommonBtnSelectWithMultipleVars('AppMenuTab.addMenu', data, vars);
                }
            }],
            oLanguage: {
                sLengthMenu: "Show_MENU_Rows",
                sSearch: "",
                oPaginate: {
                    sPrevious: "&laquo;",
                    sNext: "&raquo;"
                }
            },
            aaSorting: [
                [0, 'asc']
            ],
            aLengthMenu: [
                [5, 10, 15, 20, -1],
                [5, 10, 15, 20, "All"] // change per page values here
            ],
            sPaginationType: "full_numbers",
            sPaging: 'pagination',
            bLengthChange: false
        });
        $('#menu_select_tbl_wrapper .dataTables_filter input').addClass("form-control input-sm").attr("placeholder", "Search");
        // modify table search input
        $('#menu_select_tbl_wrapper .dataTables_length select').addClass("m-wrap small");
        // modify table per page dropdown
        $('#menu_select_tbl_wrapper .dataTables_length select').select2();

    };

    return {

        init: function () {
            initDataTable();
        }
    };
}();