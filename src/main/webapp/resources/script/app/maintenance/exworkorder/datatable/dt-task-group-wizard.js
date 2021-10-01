/*********************************************************************
 * Data Table Task Group Wizard
 * *********************************************************************/
var dtWorkOrderTaskGroupWizard = function () {

    var dtAssetList = function () {
        $('#tbl_wo_task_group_asset').dataTable().fnDestroy();
        var oTable = $('#tbl_wo_task_group_asset').dataTable({
            // scrollY: "300px",
            // scrollX: "300px",
            // scrollCollapse: true,
            processing: true,
            data: woAssetList,
            columns: [
                {
                    orderable: false,
                    searchable: false,
                    width: "2%",
                    render: function (data, type, row, meta) {
                        return meta.row + meta.settings._iDisplayStart + 1;
                    }
                },
                {data: 'name'},
                {data: 'code'}
            ],
            aoColumnDefs: [{
                targets: 3,
                data: "id",
                render: function (data, type, row, meta) {
                    return "<button id='" + data + "' type='button' class='btn btn-blue next-step btn-squared newWorkOrderTaskGroup btn-xs'> Next </button>";
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
                [1, 'asc']
            ],
            aLengthMenu: [
                [5, 10, 15, 20, -1],
                [5, 10, 15, 20, "All"]
            ],
            // scrollY: "195px",
            sPaginationType: "full_numbers",
            sPaging: 'pagination',
            bLengthChange: false,
            select: {
                style: 'os',
            },
            rowClick : {
                sFunc: "AssetBrandSelectModal.setAssetBrand",
                aoData:[  
                    {
                        sName : "brandId",
                    }, {
                        sName : "brandName"
                    },
                ],
            },
        });

        $('#tbl_wo_task_group_asset_wrapper .dataTables_filter input').addClass("form-control input-sm").attr("placeholder", "Search");
        $('#tbl_wo_task_group_asset_wrapper .dataTables_length select').addClass("m-wrap small");
        $('#tbl_wo_task_group_asset_wrapper .dataTables_length select').select2();
        $('#tbl_wo_task_group_asset_column_toggler input[type="checkbox"]').change(function () {
            var iCol = parseInt($(this).attr("data-column"));
            var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
            oTable.fnSetColumnVis(iCol, (bVis ? false : true));
        });

    };

    var dtTaskGroupList = function () {
        $("#tbl_workOrder_task_group").dataTable().fnDestroy();
        var oTable = $("#tbl_workOrder_task_group").dataTable({
            processing: true,
            serverSide: true,
            ajax: $.fn.dataTable.pipeline({
                url: "../restapi/taskGroup/tableData",
                pages: 5
            }),
            columns: [
                {
                    orderable: false,
                    searchable: false,
                    width: "2%",
                    render: function (data, type, row, meta) {
                        return meta.row + meta.settings._iDisplayStart + 1;
                    }
                },
                {data: 'description'},
                {
                    data: 'id',
                    render: function (data, type, row, meta) {
                        return "<button type='button' id='" + data + "' class='btn btn-xs btn-squared btn-blue back-step'> Previous </button>";
                    }
                }
            ],
            aoColumnDefs: [{
                targets: 3,
                width: "10%",
                data: "id",
                render: function (data, type, row, meta) {
                    return "<button type='button' id='" + data + "' class='btn btn-xs btn-squared btn-blue next-step newWorkOrderTaskList'>  Next </button>";
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
                [1, 'asc']
            ],
            aLengthMenu: [
                [5, 10, 15, 20, -1],
                [5, 10, 15, 20, "All"]
            ],
            // scrollY: "195px",
            sPaginationType: "full_numbers",
            sPaging: 'pagination',
            bLengthChange: false,
            select: {
                style: 'os',
            },
            rowClick : {
                sFunc: "AssetBrandSelectModal.setAssetBrand",
                aoData:[  
                    {
                        sName : "brandId",
                    }, {
                        sName : "brandName"
                    },
                ],
            },
        });

        $('#tbl_workOrder_task_group_wrapper .dataTables_filter input').addClass("form-control input-sm").attr("placeholder", "Search");
        $('#tbl_workOrder_task_group_wrapper .dataTables_length select').addClass("m-wrap small");
        $('#tbl_workOrder_task_group_wrapper .dataTables_length select').select2();
        $('#tbl_workOrder_task_group_column_toggler input[type="checkbox"]').change(function () {
            var iCol = parseInt($(this).attr("data-column"));
            var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
            oTable.fnSetColumnVis(iCol, (bVis ? false : true));
        });
    };

    var dtTaskGroupTaskList = function (id) {
        var url = "../restapi/taskGroup/taskListByTaskGroup?id=" + id;
        var tskTbl = "#tbl_workOrder_task_list";
        $.ajax({
            type: "GET",
            url: url,
            dataType: 'json',
            success: function (_json_result) {
                $(tskTbl + ' > tbody').empty();
                if (_json_result.length > 0) {
                    $.each(_json_result, function (index, obj) {
                        var newRow = "<tr id='row_" + index + "'> " +
                            "<input type='hidden' id='task_group_id_" + index + "' value='" + id + "'>" +
                            "<input type='hidden' id='task_id_" + index + "' value='" + obj.id + "'>" +

                            "<input type='hidden' id='task_assigned_user_" + index + "' value=''>" +
                            "<input type='hidden' id='task_completed_user_" + index + "' value=''>" +
                            "<input type='hidden' id='task_name_" + index + "' value='" + obj.name + "' > " +
                            "<input type='hidden' id='task_description_" + index + "' value='" + obj.description + "' > " +

                            "<input type='hidden' id='task_type_" + index + "' > " +
                            "<input type='hidden' id='task_type_description" + index + "' > " +

                            "<input type='hidden' id='task_completionNote_" + index + "' > " +
                            "<input type='hidden' id='task_completionRemark_" + index + "' > " +
                            "<input type='hidden' id='task_startedDate_" + index + "' > " +
                            "<input type='hidden' id='task_estimatedHours_" + index + "' > " +

                            "<input type='hidden' id='task_completedDate_" + index + "' > " +
                            "<input type='hidden' id='task_spentHours_" + index + "'> " +

                            "<td> " + (index + 1) + " </td> " +
                            "<td>  " + obj.name + "   </td> " +
                            "<td>  " + obj.description + "  </td> " +
                            "<td>  " + obj.estimatedHours + "  </td> " +
                            "<td>  <button type='button' class='btn btn-xs btn-squared btn-blue back-step'> Previous </button></td> " +
                            "<td>  " + ButtonUtil.getEditDeleteBtnFromList(index, "woTaskGroup") + "  </td> " +

                            "</tr>";
                        $(tskTbl + ' > tbody:last-child').append(newRow);
                    });


                    $(".date-picker").datepicker({
                        autoclose: true
                    });

                } else {
                    CustomComponents.emptyTableRow("tbl_workOrder_task_list", 11)
                }

            }
        });
    };


    return {

        woTaskGroupAssetList: function () {
            dtAssetList();
        },
        woTaskGroupList: function () {
            dtTaskGroupList();
        },
        woTaskGroupTaskList: function (id) {
            dtTaskGroupTaskList(id)
        }


    };
}();