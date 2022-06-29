var AssetTab = function () {
	
	var initButtons = function () {
		
		$('#new-asset-btn').on('click', function () {
			AssetTab.assetAddModal();
        });
	};

    var assetAddModal = function () {
    	var bizId = $('#businessId').val();
    	if ( bizId != null && bizId > 0 ) {
    		var $modal = $('#master-modal-datatable');        
            CustomComponents.ajaxModalLoadingProgressBar();
            setTimeout(function () {
                var url = '../workorder/asset-add-modal-view';
                $modal.load(url, '', function () {
                    dtWorkOrderAsset.dtAssetsByBusiness("AssetTab.addAsset", bizId);
                    $modal.modal();
                });
            }, 1000); 
    	} else {
    		alert("Please Select a Business First.");
    	}    	      
    };

    var addAsset = function (id, name, code) {
        if (!validateAssetAlreadyAdd(id)) {
            var assetObj = {};
            assetObj['id'] = id;
            assetObj['name'] = EncodeDecodeComponent.getBase64().decode(name);
            assetObj['code'] = EncodeDecodeComponent.getBase64().decode(code);
            assets.push(assetObj);
            $('#master-modal-datatable').modal("toggle");
            initAssetTable();
        } else {
        	alert("Asset Already Added.");
        }
    };

    var initAssetTable = function () {
        if (assets.length > 0) {
            $("#tbl_workOrder_asset > tbody").html("");
            for (var row = 0; row < assets.length; row++) {
                var asset = assets[row];
                var html =
                    "<tr id='row_" + row + "' >" +
                    "<input id='assets" + row + ".id' name='assets[" + row + "].id' value='" + asset.id + "' type='hidden'>" +
                    "<input id='assets" + row + ".name' name='assets[" + row + "].name' value='" + asset.name + "' type='hidden' >" +
                    "<input id='assets" + row + ".code' name='assets[" + row + "].code' value='" + asset.code + "' type='hidden' >" +
                    "<td><span>" + ( row + 1 ) + "</span></td>" +
                    "<td><span>" + asset.name + "</span></td>" +
                    "<td><span>" + asset.code + "</span></td>" +
                    "<td style='text-align:center'>" +
                    	ButtonUtil.getCommonBtnDelete('AssetTab.removeAsset', row) +
                    "</td>" +
                    "</tr>";
                getTaskAssetCategory(asset);
                $('#tbl_workOrder_asset > tbody:last-child').append(html);
            }
        } else {
            CustomComponents.emptyTableRow("tbl_workOrder_asset", 4, "Please Add Asset to the Work Order.");
        }
    };

    var getTaskAssetCategory=function(asset){
		$.ajax({
			type : "GET",
			url: "../restapi/taskGroup/getTaskByAssetCategory?assetId=" + asset.id,
			contentType : "application/json",
			dataType : "json",
			success : function(output) {
				$.each(output, function(key, taskList) {
					if(!isTaskAlreadyAdded(taskList.id)){
						taskList.assignedAssetId=asset.id
						taskList.assignedAssetName=asset.name
						tasks.push(taskList);
					}


				});
				TaskTab.populateTaskTable();

			},
			error : function(xhr, ajaxOptions, thrownError) {
				alert(xhr.status + " " + thrownError);
			},
			error : function(e) {
				alert("Failed to load Task");
				console.log(e);
			}
		});
    }
    
    var removeTask=function(assetId){
        for (var i = 0; i < tasks.length; i++) {
            if (tasks[i].assignedAssetId == assetId) {
                tasks.splice(i, 1);
                TaskTab.populateTaskTable();            }
        }
    }
    
    var isTaskAlreadyAdded=function(taskId){
        for (var i = 0; i < tasks.length; i++) {
            if (tasks[i].id == taskId) {
                return true;
            }
        }
        return false;
    }

    var removeAsset = function (index) {
    	if ( !isAssetUsed(assets[index].id) ) {
    	//	removeTask(assets[index].id)
    		assets.splice(index, 1);
        	initAssetTable();
    	}    	
    };

    var validateAssetAlreadyAdd = function (id) {
        for (var i = 0; i < assets.length; i++) {
            if (assets[i].id == id) {
                return true;
            }
        }
        return false;
    };
    
    var isAssetUsed = function (assetId) {
    	var isAssetUsed = false;
    	
    	if ( isAssetUsedInTasks(assetId) ) {
    		isAssetUsed = true;
    		alert("Asset Already Used in the Tasks. Please Remove Them First..!!");
    	}
    	
    	if ( isAssetUsedInMeterReadings(assetId) ) {
    		isAssetUsed = true;
    		alert("Asset Already Used in the Meter Readings. Please Remove Them First..!!");
    	}
    	
    	return isAssetUsed;
    };
    
    var isAssetUsedInTasks = function (assetId) {
    	
    	var isAssetUsedInTasks = false;
    	
    	$.each(tasks, function (index, task) {
    		
    		if ( task.assignedAssetId == assetId ) {
    			isAssetUsedInTasks = true;
    			return false
    		}
    	});
    	
    	return isAssetUsedInTasks;
    };
    
    var isAssetUsedInMeterReadings = function (assetId) {
    	
    	var isAssetUsedInMeterReadings = false;
    	
    	$.each(meterReadings, function (index, reading) {
    		if ( reading.meterReadingAssetId == assetId ) {
    			isAssetUsedInMeterReadings = true;
    			return false
    		}
    	});
    	
    	return isAssetUsedInMeterReadings;
    };

    return {
    	
    	init : function () {
    		initButtons();
    		initAssetTable();
    	},

    	assetAddModal: function () {
    		assetAddModal();
        },

        addAsset: function (id, name, code) {
        	addAsset(id, name, code);
        },

        removeAsset: function (index) {
            removeAsset(index);
        }

    };

}();