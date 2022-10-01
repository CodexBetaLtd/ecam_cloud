var TabBom = function () {
	
	var initButtons = function () {		

	    $('#btn-add-part').on('click', function () {
	        TabBom.partSelectModal();
	    });

	    $('#btn-add-bom').on('click', function () {
	        TabBom.bomSelectModal();
	    });
	    
	};

    var partSelectModal = function () {
    	var bizId = $('#businessId').val();
    	if ( bizId != null && bizId > 0 ) {
	        var $modal = $('#master-modal-datatable');
	        CustomComponents.ajaxModalLoadingProgressBar();
	        setTimeout(function () {
	            var url = '../../asset/partmodelview';
	            $modal.load(url, '', function () {
	                PartSelectModel.init(bizId);
	                $modal.modal();
	            });
	        }, 1000); 
        } else {
			alert("Please Select a Business First.");
		} 
    };
    
    var bomSelectModal = function () {
    	var bizId = $('#businessId').val();
    	if ( bizId != null && bizId > 0 ) {
	    	var $modal = $('#master-modal-datatable');
	        CustomComponents.ajaxModalLoadingProgressBar();
	        setTimeout(function () {
	            var url = '../../asset/bommodelview';
	            $modal.load(url, '', function () {
	                BomSelectModel.init(bizId);
	                $modal.modal();
	            });
	        }, 1000);
        } else {
			alert("Please Select a Business First.");
		} 
    };

    var setPartConsumingRefs = function (partConsumeRefs) {
        for (var i = 0; i < partConsumeRefs.length; i++) {
            addPartConsumeRefToList(partConsumeRefs[i].id, partConsumeRefs[i].partName,
                partConsumeRefs[i].partId, partConsumeRefs[i].maxConsumption,
                partConsumeRefs[i].bomGroupName, partConsumeRefs[i].bomGroupPartId,
                partConsumeRefs[i].version);
        };
        resetPartConsumeRefHtmlTable();
    };

    var resetPartConsumeRefHtmlTable = function () {

        if (partConsumeRefs.length > 0) {
            var row, partConsumeRef;
            $("#bom-tbl > tbody").html("");

            for (row = 0; row < partConsumeRefs.length; row++) {
                partConsumeRef = partConsumeRefs[row];
                var html = "<tr id='row_" + row + "' >" +
                    "<input id='partConsumeRefs" + row + ".id' name='partConsumeRefs[" + row + "].id' value='" + partConsumeRef.id + "' type='hidden'>" +
                    "<input id='partConsumeRefs" + row + ".partId' name='partConsumeRefs[" + row + "].partId' value='" + partConsumeRef.partId + "' type='hidden' >" +
                    "<input id='partConsumeRefs" + row + ".partName' name='partConsumeRefs[" + row + "].partName' value='" + partConsumeRef.partName + "' type='hidden' >" +
                    "<input id='partConsumeRefs" + row + ".bomGroupName' name='partConsumeRefs[" + row + "].bomGroupName' value='" + partConsumeRef.bomGroupName + "' type='hidden' >" +
                    "<input id='partConsumeRefs" + row + ".bomGroupPartId' name='partConsumeRefs[" + row + "].bomGroupPartId' value='" + partConsumeRef.bomGroupPartId + "' type='hidden' >" +
                    "<input id='partConsumeRefs" + row + ".version' name='partConsumeRefs[" + row + "].version' value='" + partConsumeRef.version + "' type='hidden' >" +
                    "<td><span>" + ( row + 1 ) + "</span></td>" +
                    "<td><span>" + partConsumeRef.partName + "</span></td>" +
                    "<td class='hidden-xs'><input id='partConsumeRefs" + row + ".maxConsumption' name='partConsumeRefs[" + row + "].maxConsumption' value='" + partConsumeRef.maxConsumption + "' type='text' ></td>" +
                    "<td class='hidden-xs'><span>" + partConsumeRef.bomGroupName + "</span></td>" +
                    "<td class='center'>" +
	                    "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
	                    	ButtonUtil.getCommonBtnDelete("TabBom.removePartConsumeRef", partConsumeRef.partIndex) +
	                    "</div>" +
                    "</td>" +
                    "</tr>";
                $('#bom-tbl > tbody:last-child').append(html);
            }
        } else {

            $("#bom-tbl > tbody").html("<tr><td colspan='6' align='center'>Please Add Assets Consume References for the Asset.</td></tr>");
        }

    };

    var isPartConsumeRefAlreadyAdd = function (partId) {
        for (var i = 0; i < partConsumeRefs.length; i++) {
            if (partConsumeRefs[i].partId == partId) {
                if (partConsumeRefs[i].bomGroupPartId == "") {
            		return true;
            	}
            }
        }
        return false;
    };

    var addPartConsumeRef = function (id, partName, partId, maxConsumption, bomGroupName, bomGroupPartId, version) {
        if (!isPartConsumeRefAlreadyAdd(partId)) {
            addPartConsumeRefToList(id, partName, partId, maxConsumption, bomGroupName, bomGroupPartId, version);
            resetPartConsumeRefHtmlTable();
        }
        $('#master-modal-datatable').modal('toggle');
    };

    var addPartConsumeRefFromBom = function (bomId) {
    	$.ajax({
            type: "GET",
            url: '../../restapi/bomgrouppart/partsbybomgroupid?bomId=' + bomId,
            dataType: 'json',
            success: function (json) {
                $.each(json, function (index, obj) {
                    addPartConsumeRefToList("", obj.partName, obj.partId, obj.maxConsumption, obj.bomGroupName, obj.id, "");
                });
                resetPartConsumeRefHtmlTable();
            },
            error: function (xhr, ajaxOptions, thrownError) {
                console.log(xhr.status + " " + thrownError)
            }
        }).done(function (data) {
            
        });
        $('#master-modal-datatable').modal('toggle');
    };

    var addPartConsumeRefToList = function (id, partName, partId, maxConsumption, bomGroupName, bomGroupPartId, version) {
        var partConsumeRefObj = {}

        if (partConsumeRefs.length == 0) {
            partConsumeRefObj['partIndex'] = 0;
    	} else {
    		CustomValidation.validateFieldNull(partConsumeRefObj, 'partIndex', partConsumeRefs.length);
    	}

        CustomValidation.validateFieldNull(partConsumeRefObj, 'id', id);
        CustomValidation.validateFieldNull(partConsumeRefObj, 'partId', partId);
        CustomValidation.validateFieldNull(partConsumeRefObj, 'partName', partName);
        CustomValidation.validateFieldNull(partConsumeRefObj, 'bomGroupPartId', bomGroupPartId);
        CustomValidation.validateFieldNull(partConsumeRefObj, 'bomGroupName', bomGroupName);
        CustomValidation.validateFieldNull(partConsumeRefObj, 'maxConsumption', maxConsumption);
        CustomValidation.validateFieldNull(partConsumeRefObj, 'version', version);

        partConsumeRefs.push(partConsumeRefObj);
    };
    
    var getPartByIndex = function (partIndex) {
        for (var i = 0; i < partConsumeRefs.length; i++) {
            if (partConsumeRefs[i].partIndex == partIndex) {
                return partConsumeRefs[i];
			}
		}
    };

    var removePartConsumeRef = function (partIndex) {
		var part = getPartByIndex( partIndex );
		
		if (part.partId == "" || part.partId == null) {
			alert("Cant remove BOM group item from here.");
		} else {
            for (var i = 0; i < partConsumeRefs.length; i++) {
                if (partConsumeRefs[i].partIndex == partIndex) {
                    partConsumeRefs.splice(i, 1);
                    resetPartConsumeRefHtmlTable();
					break;
				}
			}
		}
	};
	
	var initPartConsumingRefs = function () {
		setPartConsumingRefs(thymeLeafPartConsumeRefs);
	};

    return {
    	
    	init: function () {
    		initButtons();
    		initPartConsumingRefs();
    	},

        /***********************************************************************
         * Part Consuming ref Add
		 **********************************************************************/
    	
        addPartConsumeRef: function (id, partName, partId, maxConsumption, bomGroupName, bomGroupPartId, version) {
        	addPartConsumeRef(id, partName, partId, maxConsumption, bomGroupName, bomGroupPartId, version);
        },

        addPartConsumeRefFromBom: function (id) {
            addPartConsumeRefFromBom(id);
        },

        addPartConsumeRefToList: function (id, partName, partId, maxConsumption, bomGroupName, bomGroupPartId, version) {
            addPartConsumeRefToList(id, partName, partId, maxConsumption, bomGroupName, bomGroupPartId, version);
        },

        removePartConsumeRef: function (partId) {
            removePartConsumeRef(partId);
        },

        resetPartConsumeRefHtmlTable: function () {
            resetPartConsumeRefHtmlTable();
        },

        setPartConsumingRefs: function (partConsumeRefs) {
            setPartConsumingRefs(partConsumeRefs);
        },

        /**********************************************************
         * Initialize Modals
         *********************************************************/
        
        partSelectModal: function () {
        	partSelectModal();
        },
        
        bomSelectModal: function () {
        	bomSelectModal();
        }
    };
}();