/*********************************************************************
 * Data Table Task Group Wizard
 * *********************************************************************/
var ScheduledMaintenanceTaskGroup = function () {
	
	var setModal = function( modalName ) {
		this.modalName = modalName;
	};

    var dtTaskGroups = function (businessId) {
        var tableId = "sm_task_group_tbl";
        $("#" + tableId).dataTable().fnDestroy();
        var oTable = $("#" + tableId).dataTable({
            processing: true,
            serverSide: true,
            ajax: $.fn.dataTable.pipeline({
                url: "../restapi/taskGroup/tabledata-by-business?bizId=" + businessId,
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
                {data: 'name'},
                {data: 'description'}
            ],
            aoColumnDefs: [{
                targets: 3,
                width: "10%",
                data: "id",
                render: function (data, type, row, meta) {
                    var vars = [data, EncodeDecodeComponent.getBase64().encode(row.name)];
                    return ButtonUtil.getCommonBtnSelectWithMultipleVars('ScheduledMaintenanceTaskGroup.setTaskGroup', data, vars);
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
            bLengthChange: false
        });

        $('#' + tableId + '_wrapper .dataTables_filter input').addClass("form-control input-sm").attr("placeholder", "Search");
        $('#' + tableId + '_wrapper .dataTables_length select').addClass("m-wrap small");
        $('#' + tableId + '_wrapper .dataTables_length select').select2();
        $('#' + tableId + '_toggler input[type="checkbox"]').change(function () {
            var iCol = parseInt($(this).attr("data-column"));
            var bVis = oTable.fnSettings().aoColumns[iCol].bVisible;
            oTable.fnSetColumnVis(iCol, (bVis ? false : true));
        });
    }; 

    var setTaskGroup = function (id, name) {
    	$('#smTaskGroupId').val(id);
        $('#smTaskGroupName').val(EncodeDecodeComponent.getBase64().decode(name)); 
        
        setTaskGroupTasks(id); 
        $("#" + this.modalName).modal('toggle');
    };  
    
    var setTaskGroupTasks = function(taskGroupId) { 
    	
        $.ajax({
            type: "GET",
            url: "../restapi/taskGroup/tasks-by-task-group?id=" + taskGroupId,
            contentType: "application/json",
            dataType: "json",
            success: function (tasks) { 
            	clearTaskTempList();
                $.each(tasks, function (index, task) {
                	tempTasks.push(task);
                }); 
                TaskGroupAddModal.initTaskGroupTaskTable();
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert(xhr.status + " " + thrownError);
            }
        }); 
        
	};
	
    var clearTaskTempList = function () {
    	tempTasks = [];
    };

    return {
    	
        init: function (modalName, businessId) {
        	setModal(modalName)
            dtTaskGroups(businessId);
        },
        
        setTaskGroup: function(id, name) {
        	setTaskGroup(id, name)
		}
    
    };
}();