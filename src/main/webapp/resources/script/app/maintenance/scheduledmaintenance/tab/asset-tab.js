var TabAsset = function () {

    var initButtons = function () {

        $('#assetselectmodelbtn').on('click', function () {
            TabAsset.assetSelectModal();
        });

    };

    var assetSelectModal = function () {
    	var bizId = $('#businessId').val();
    	if ( bizId != null && bizId > 0 ) {
    		var $modal = $('#master-modal-datatable');
            CustomComponents.ajaxModalLoadingProgressBar();
            setTimeout(function () {
                var url = '../scheduledmaintenance/assetselectmodalview';
                $modal.load(url, '', function () {
                    AssetSelectModal.init(bizId);
                    $modal.modal();
                });
            }, 1000);
    	} else {
    		alert("Please Select a Business First.");
    	}   	
    };

    var initHtmlTable = function () {

        if (assets.length > 0) {
            var row, asset;
            $("#sm-asset-tbl > tbody").html("");

            for (row = 0; row < assets.length; row++) {
                asset = assets[row];
                var html = "<tr id='row_" + row + "' >" +
                    "<input id='assets" + row + ".id' name='assets[" + row + "].id' value='" + asset.id + "' type='hidden'>" +
                    "<input id='assets" + row + ".name' name='assets[" + row + "].name' value='" + asset.name + "' type='hidden' >" +
                    "<input id='assets" + row + ".code' name='assets[" + row + "].code' value='" + asset.code + "' type='hidden' >" +
                    "<td class='hidden-xs'><span>" + ( row + 1 ) + "</span></td>" +
                    "<td><span>" + asset.name + "</span></td>" +
                    "<td class='hidden-xs'><span>" + asset.code + "</span></td>" +
                    "<td class='center'>" +
                    "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
                    ButtonUtil.getCommonBtnDelete("TabAsset.removeAsset", row) +
                    "</div>" +
                    "</td></tr>";

                $('#sm-asset-tbl > tbody:last-child').append(html);
            }
        } else {

            $("#sm-asset-tbl > tbody").html("<tr><td colspan='4' align='center'>Please Add Assets for the Scheduled Maintenance.</td></tr>");
        }

    };

    var addAssetToList = function (id, name, code) {
        var assetObj = {}
        assetObj['id'] = id;
        assetObj['name'] = name;
        assetObj['code'] = code;
        assets.push(assetObj);
    };

    var addAsset = function (id, name, code) {
        if (!isAssetAlreadyAdd(id)) {
            addAssetToList(id, name, code);
            initHtmlTable();
            $('#master-modal-datatable').modal('toggle');
        } else {
        	alert("Asset Already Added.");
        }
        
    };

    var removeAsset = function (index) {
    	if (!isAssetUsed(assets[index].id)) {
    		assets.splice(index, 1);
            initHtmlTable();
    	}        
    };
    
    var isAssetUsed = function (assetId) {
    	var isAssetUsed = false;
    	if ( isAssetUsedInTrigger(assetId) ) {
    		isAssetUsed = true;
    		alert("Asset Already Used in the Scheduled Triggers. Please Remove Them First..!!");
    	} 
    	
    	if ( isAssetUsedInTasks(assetId) ) {
    		isAssetUsed = true;
    		alert("Asset Already Used in the Scheduled Tasks. Please Remove Them First..!!");
    	}
    	
    	if ( isAssetUsedInParts(assetId) ) {
    		isAssetUsed = true;
    		alert("Asset Already Used in the Parts. Please Remove Them First..!!");
    	}
    	
    	return isAssetUsed;
    };
    
    var isAssetUsedInTrigger = function (assetId) {
    	
    	var isAsserUsedInTrigger = false;
    	
    	$.each(triggers, function (index, trigger) {
    		if ( trigger.assetId == assetId ) {
    			isAsserUsedInTrigger = true;
    			return false
    		}
    	});
    	
    	return isAsserUsedInTrigger;
    };
    
    var isAssetUsedInTasks = function (assetId) {
    	
    	var isAssetUsedInTasks = false;
    	
    	$.each(scheduledTasks, function (index, task) {
    		if ( task.assetId == assetId ) {
    			isAssetUsedInTasks = true;
    			return false
    		}
    	});
    	
    	return isAssetUsedInTasks;
    };
    
    var isAssetUsedInParts = function (assetId) {
    	
    	var isAssetUsedInParts = false;
    	
    	$.each(parts, function (index, part) {
    		if ( part.assetId == assetId ) {
    			isAssetUsedInParts = true;
    			return false
    		}
    	});
    	
    	return isAssetUsedInParts;
    };

    var isAssetAlreadyAdd = function (id) {
        for (var i = 0; i < assets.length; i++) {
            if (assets[i].id == id) {
                return true;
            }
        }
        return false;
    };

    return {

        init: function () {
            initButtons();
            initHtmlTable();
        },

        assetSelectModal: function () {
            assetSelectModal();
        },

        addAsset: function (id, name, code) {
            addAsset(id, name, code);
        },

        addAssetToList: function (id, name, code) {
            addAssetToList(id, name, code);
        },

        removeAsset: function (id) {
            removeAsset(id);
        }

    };
}();