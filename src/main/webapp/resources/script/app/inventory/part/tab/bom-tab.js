var BomTab = function () {

    var assetSelectModal = function () {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../part/asset-select-view';
            $modal.load(url, '', function () {
                AssetSelectModel.init();
                $modal.modal();
            });
        }, 1000);
    };

    var resetAssetConsumeRefHtmlTable = function () {

        if (assetConsumeRefs.length > 0) {
            var row, assetConsumeRef;
            $("#bom-tbl > tbody").html("");

            for (row = 0; row < assetConsumeRefs.length; row++) {
            	assetConsumeRef = assetConsumeRefs[row];
                var html = "<tr id='row_" + row + "' >" +
                    "<input id='assetConsumeRefs" + row + ".id' name='assetConsumeRefs[" + row + "].id' value='" + assetConsumeRef.id + "' type='hidden'>" +
                    "<input id='assetConsumeRefs" + row + ".assetId' name='assetConsumeRefs[" + row + "].assetId' value='" + assetConsumeRef.assetId + "' type='hidden' >" +
                    "<input id='assetConsumeRefs" + row + ".assetName' name='assetConsumeRefs[" + row + "].assetName' value='" + assetConsumeRef.assetName + "' type='hidden' >" +
                    "<input id='assetConsumeRefs" + row + ".bomGroupName' name='assetConsumeRefs[" + row + "].bomGroupName' value='" + assetConsumeRef.bomGroupName + "' type='hidden' >" +
                    "<input id='assetConsumeRefs" + row + ".bomGroupPartId' name='assetConsumeRefs[" + row + "].bomGroupPartId' value='" + assetConsumeRef.bomGroupPartId + "' type='hidden' >" +
                    "<input id='assetConsumeRefs" + row + ".version' name='assetConsumeRefs[" + row + "].version' value='" + assetConsumeRef.version + "' type='hidden' >" +
                    "<td><span>" + ( row + 1 ) + "</span></td>" +
                    "<td class='hidden-xs'><span>" + assetConsumeRef.assetName + "</span></td>" +
                    "<td><input id='assetConsumeRefs" + row + ".maxConsumption' name='assetConsumeRefs[" + row + "].maxConsumption' value='" + assetConsumeRef.maxConsumption + "' type='text' ></td>" +
                    "<td class='hidden-xs'><span>" + assetConsumeRef.bomGroupName + "</span></td>" +
                    "<td class='center'>" +
                    "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
                    "<a onclick='BomTab.removeAssetConsumeRef(" + assetConsumeRef.assetId + ");' class='btn btn-xs btn-bricky tooltips' data-placement='top' data-original-title='Remove' >" +
                    "<i class='fa fa-times fa fa-white' ></i></a></div></td></tr>";

                $('#bom-tbl > tbody:last-child').append(html);
            }
        } else {
            CustomComponents.emptyTableCustomeMessageRow("bom-tbl", 6, "Please Add Assets References for the Part.");
        }
    };

    var isAssetConsumeRefAlreadyAdd = function (assetId) {
        for (var i = 0; i < assetConsumeRefs.length; i++) {
            if (assetConsumeRefs[i].assetId == assetId) {
            	if (assetConsumeRefs[i].bomGroupPartId == "") {
            		return true;
            	}
            }
        }
        return false;
    };
    
    var addAssetConsumeRefByObj = function (assetConsumeRef) {
    	addAssetConsumeRefToList(assetConsumeRef.id, assetConsumeRef.assetName, assetConsumeRef.assetId, assetConsumeRef.maxConsumption, assetConsumeRef.bomGroupName,
    			assetConsumeRef.bomGroupPartId, assetConsumeRef.version);
    };

    var addAssetConsumeRef = function (id, assetName, assetId, maxConsumption, bomGroupName, bomGroupPartId, version) {
        if (!isAssetConsumeRefAlreadyAdd(assetId)) {
            addAssetConsumeRefToList(id, assetName, assetId, maxConsumption, bomGroupName, bomGroupPartId, version);
            resetAssetConsumeRefHtmlTable();
        }
    };

    var addAssetConsumeRefToList = function (id, assetName, assetId, maxConsumption, bomGroupName, bomGroupPartId, version) {
    	var assetConsumeRefObj = {}
        
    	CustomValidation.validateFieldNull( assetConsumeRefObj, 'id', id);
    	CustomValidation.validateFieldNull( assetConsumeRefObj, 'assetId', assetId);
    	CustomValidation.validateFieldNull( assetConsumeRefObj, 'assetName', assetName);
    	CustomValidation.validateFieldNull( assetConsumeRefObj, 'bomGroupPartId', bomGroupPartId);
    	CustomValidation.validateFieldNull( assetConsumeRefObj, 'bomGroupName', bomGroupName);
    	CustomValidation.validateFieldNull( assetConsumeRefObj, 'maxConsumption', maxConsumption);
    	CustomValidation.validateFieldNull( assetConsumeRefObj, 'version', version);
        
        assetConsumeRefs.push(assetConsumeRefObj);
    };

	var removeAssetConsumeRef = function(assetId) {
		if (assetId == "" || assetId == null) {
			alert("Cant remove BOM group item from here.");
		} else {
			for (var i = 0; i < assetConsumeRefs.length; i++) {
				if (assetConsumeRefs[i].assetId == assetId) {
					assetConsumeRefs.splice(i, 1);
					resetAssetConsumeRefHtmlTable();
					break;
				}
			}
		}
	};

    return {
    	
    	init : function () {
    		resetAssetConsumeRefHtmlTable();
    	},

        /***********************************************************************
		 * Asset Consuming ref Add
		 **********************************************************************/
        addAssetConsumeRef: function (id, assetName, assetId, maxConsumption, bomGroupName, bomGroupPartId, version) {
            addAssetConsumeRef(id, assetName, assetId, maxConsumption, bomGroupName, bomGroupPartId, version);
        },
        
        addAssetConsumeRefByObj : function (assetConsumeRef) {
        	addAssetConsumeRefByObj(assetConsumeRef);
        },

        removeAssetConsumeRef: function (assetId) {
            removeAssetConsumeRef(assetId);
        },

        resetAssetConsumeRefHtmlTable: function () {
            resetAssetConsumeRefHtmlTable();
        },

        /**********************************************************
         * Initialize Modals
         *********************************************************/
        assetSelectModal: function () {
            assetSelectModal();
        }
    };

}();