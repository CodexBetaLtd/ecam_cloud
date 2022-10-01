var SiteUser = function () {

    var getSiteUserList = function (id) {

        var $modal = $('#common-modal');
        //$('body').modalmanager('loading');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../site/getSiteUsers';
            $modal.load(url, '', function () {
                loadUser(id);
                $modal.modal();
            });
        }, 1000);

    }

    var loadUser = function (id) {
        var oTable = $('#site_user_tbl').dataTable({
            processing: true,
            serverSide: true,
            ajax: $.fn.dataTable.pipeline({
                url: "../restapi/setting/siteUserTable?id=" + id,
                pages: 5
            }),
            columns: [{
                orderable: false,
                searchable: false,
                width: "2%",
                render: function (data, type, row, meta) {
                    return meta.row + meta.settings._iDisplayStart + 1;
                }
            },
                {
                    data: 'fullName'
                },
                {
                    data: 'emailAddress'
                },
                {
                    data: 'personalCode'
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
                [1, 'asc']
            ],
            aLengthMenu: [
                [5, 10, 15, 20, -1],
                [5, 10, 15, 20, "All"] // change per page values here
            ],
            sPaginationType: "full_numbers",
            sPaging: 'pagination',
            bLengthChange: false
        });
        $('#site_user_tbl_wrapper .dataTables_filter input').addClass("form-control input-sm").attr("placeholder", "Search");
        // modify table search input
        $('#site_user_tbl_wrapper .dataTables_length select').addClass("m-wrap small");
        // modify table per page dropdown
        $('#site_user_tbl_wrapper .dataTables_length select').select2();
        // initialzie select2 dropdown
        $('#site_user_tbl_column_toggler input[type="checkbox"]').change(function () {
            /* Get the DataTables object again - this is not a recreation, just a get of the object */
            var iCol = parseInt($(this).attr("data-column"));
            var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
            oTable.fnSetColumnVis(iCol, (bVis ? false : true));
        });
    }


    return {
        getSiteUserList: function (id) {
            getSiteUserList(id);
        }
    };

}();