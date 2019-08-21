var TabAssetEvent = function () {
	
	var initButtons = function () {
		
		$('#btn-new-asset-event').on('click', function () {
	    	TabAssetEvent.assetEventModal();
	    });
		
	};
	
	var assetEventTypeHistory = function (assetId, assetEventTypeId) {
        if (assetId == null || assetId == undefined || assetId == "") {
            alert("Please Save the Asset First.")
        } else {
        	var $modal = $('#master-modal-datatable');
            CustomComponents.ajaxModalLoadingProgressBar();
            setTimeout(function () {
                var url = '../../asset/asseteventhistorymodelview';
                $modal.load(url, '', function () {
                    AssetEventHistoryModel.init(assetId, assetEventTypeId);
                    $modal.modal();
                });
            }, 1000);
        }

    };
	
	var assetEventModal = function () {
		var $modal = $('#master-modal-datatable');
        CustomComponents.ajaxModalLoadingProgressBar();
		setTimeout(function () {
			var url = '../../asset/asseteventmodelview';
			$modal.load(url, '', function () {
				AssetEventAddModal.init();
				$modal.modal();
			});
		}, 1000);
	};
    
    var addAssetEvent = function () {

        var assetEvent = {};

        assetEvent['assetEventTypeId'] = $('#assetEventTypeId').val();
        assetEvent['assetEventTypeName'] = $('#assetEventTypeName').val();
        assetEvent['assetEventDescription'] = $('#assetEventDescription').val();
        var time = new Date();
        assetEvent['assetEventOccuredDate'] = time.getTime();
        assetEvent['assetEventOccuredDateStr'] = moment(time).format('YYYY-MM-DD hh:mm:ss A');

        addAssetEventToList(assetEvent);

        updateAssetEventTypeAssets(assetEvent);

        resetAssetEvents();
        
        $('#master-modal-datatable').modal("toggle");
    };

    var updateAssetEventTypeAssets = function (assetEvent) {
        if (isAssetEventTypeAlreadyNotAdded(assetEvent)) {
            var assetEventTypeAssetObj = {}
            if (assetEventTypeAssets.length == 0) {
                assetEventTypeAssetObj['index'] = 0;
            } else {
                CustomValidation.validateFieldNull(assetEventTypeAssetObj, 'index', assetEventTypeAssets.length);
            }

            CustomValidation.validateFieldNull(assetEventTypeAssetObj, 'id', null);
            CustomValidation.validateFieldNull(assetEventTypeAssetObj, 'assetId', assetId);
            CustomValidation.validateFieldNull(assetEventTypeAssetObj, 'assetEventTypeId', assetEvent.assetEventTypeId);
            CustomValidation.validateFieldNull(assetEventTypeAssetObj, 'assetEventTypeName', assetEvent.assetEventTypeName);
            CustomValidation.validateFieldNull(assetEventTypeAssetObj, 'latestAssetEventId', null);
            CustomValidation.validateFieldNull(assetEventTypeAssetObj, 'latestAssetEventIndexId', assetEvent.index);
            CustomValidation.validateFieldNull(assetEventTypeAssetObj, 'latestEventOccurDate', assetEvent.assetEventOccuredDate);
            CustomValidation.validateFieldNull(assetEventTypeAssetObj, 'latestEventOccurDateStr', assetEvent.assetEventOccuredDateStr);
            CustomValidation.validateFieldNull(assetEventTypeAssetObj, 'version', null);

            assetEventTypeAssets.push(assetEventTypeAssetObj);
        }
        resetAssetEventTypeAssetHtmlTable();
    };

    var isAssetEventTypeAlreadyNotAdded = function (assetEvent) {
        for (var i = 0; i < assetEventTypeAssets.length; i++) {
            if (assetEvent.assetEventTypeId == assetEventTypeAssets[i].assetEventTypeId) {
                assetEventTypeAssets[i].latestEventOccurDate = assetEvent.assetEventOccuredDate;
                assetEventTypeAssets[i].latestEventOccurDateStr = assetEvent.assetEventOccuredDateStr;
                assetEventTypeAssets[i].latestAssetEventIndexId = assetEvent.index;
                return false;
            }
        }
        return true;
    };

    var resetAssetEvents = function () {
        if (assetEvents.length > 0) {
            var row, assetEvent;
            $("#asset-events-div").html("");
            for (row = 0; row < assetEvents.length; row++) {
                assetEvent = assetEvents[row];
                var html = "<input id='assetEvents" + row + ".assetEventTypeId' name='assetEvents[" + row + "].assetEventTypeId' value='" + assetEvent.assetEventTypeId + "' type='hidden'>" +
                    "<input id='assetEvents" + row + ".assetEventTypeName' name='assetEvents[" + row + "].assetEventTypeName' value='" + assetEvent.assetEventTypeName + "' type='hidden' >" +
                    "<input id='assetEvents" + row + ".assetEventDescription' name='assetEvents[" + row + "].assetEventDescription' value='" + assetEvent.assetEventDescription + "' type='hidden' >" +
                    "<input id='assetEvents" + row + ".assetEventOccuredDate' name='assetEvents[" + row + "].assetEventOccuredDate' value='" + assetEvent.assetEventOccuredDate + "' type='hidden' >" +
                    "<input id='assetEvents" + row + ".assetEventOccuredDateStr' name='assetEvents[" + row + "].assetEventOccuredDateStr' value='" + assetEvent.assetEventOccuredDateStr + "' type='hidden' >" +
                    "<input id='assetEvents" + row + ".assetEventIndex' name='assetEvents[" + row + "].assetEventIndex' value='" + assetEvent.index + "' type='hidden' >";
                $('#asset-events-div').append(html);
            }
        }
    };

    var resetAssetEventTypeAssetHtmlTable = function () {

        if (assetEventTypeAssets.length > 0) {
            var row, assetEventTypeAsset;
            $("#asset_event_type_asset_tbl > tbody").html("");

            for (row = 0; row < assetEventTypeAssets.length; row++) {
                assetEventTypeAsset = assetEventTypeAssets[row];
                var html = "<tr id='row_" + row + "' >" +
                    "<input id='assetEventTypeAssets" + row + ".id' name='assetEventTypeAssets[" + row + "].id' value='" + assetEventTypeAsset.id + "' type='hidden'>" +
                    "<input id='assetEventTypeAssets" + row + ".index' name='assetEventTypeAssets[" + row + "].index' value='" + assetEventTypeAsset.assetId + "' type='hidden' >" +
                    "<input id='assetEventTypeAssets" + row + ".assetId' name='assetEventTypeAssets[" + row + "].assetId' value='" + assetEventTypeAsset.index + "' type='hidden' >" +
                    "<input id='assetEventTypeAssets" + row + ".assetEventTypeId' name='assetEventTypeAssets[" + row + "].assetEventTypeId' value='" + assetEventTypeAsset.assetEventTypeId + "' type='hidden' >" +
                    "<input id='assetEventTypeAssets" + row + ".assetEventTypeName' name='assetEventTypeAssets[" + row + "].assetEventTypeName' value='" + assetEventTypeAsset.assetEventTypeName + "' type='hidden' >" +
                    "<input id='assetEventTypeAssets" + row + ".latestAssetEventId' name='assetEventTypeAssets[" + row + "].latestAssetEventId' value='" + assetEventTypeAsset.latestAssetEventId + "' type='hidden' >" +
                    "<input id='assetEventTypeAssets" + row + ".latestAssetEventIndexId' name='assetEventTypeAssets[" + row + "].latestAssetEventIndexId' value='" + assetEventTypeAsset.latestAssetEventIndexId + "' type='hidden' >" +
                    "<input id='assetEventTypeAssets" + row + ".latestEventOccurDate' name='assetEventTypeAssets[" + row + "].latestEventOccurDate' value='" + assetEventTypeAsset.latestEventOccurDate + "' type='hidden' >" +
                    "<input id='assetEventTypeAssets" + row + ".latestEventOccurDateStr' name='assetEventTypeAssets[" + row + "].latestEventOccurDateStr' value='" + assetEventTypeAsset.latestEventOccurDateStr + "' type='hidden' >" +
                    "<input id='assetEventTypeAssets" + row + ".version' name='assetEventTypeAssets[" + row + "].version' value='" + assetEventTypeAsset.version + "' type='hidden' >" +
                    "<td><span>" + ( row + 1 ) + "</span></td>" +
                    "<td><span>" + assetEventTypeAsset.assetEventTypeName + "</span></td>" +
                    "<td class='hidden-xs'>" + assetEventTypeAsset.latestEventOccurDateStr + "</td>" +                    
                    "<td class='center'>" +
                    "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
                    "<a onclick='TabAssetEvent.assetEventTypeHistory(" + assetEventTypeAsset.assetId + "," + assetEventTypeAsset.assetEventTypeId + ");' class='btn btn-xs btn-green tooltips' data-placement='top' data-original-title='History' >" +
                    "<i class='clip-history' ></i></a>\n\n" +
                    ButtonUtil.getCommonBtnDelete("TabAssetEvent.removeAssetEventTypeAsset", assetEventTypeAsset.index) +
                    "</div></td></tr>";

                $('#asset_event_type_asset_tbl > tbody:last-child').append(html);
            }
        } else {

            $("#asset_event_type_asset_tbl > tbody").html("<tr><td colspan='4' align='center'>Please Add Asset Event Type for the Asset.</td></tr>");
        }

    };

    var addAssetEventToList = function (assetEvent) {

        if (assetEvents.length == 0) {
            assetEvent['index'] = 0;
        } else {
        	CustomValidation.validateFieldNull(assetEvent, 'index', assetEvents.length);
        }
        assetEvents.push(assetEvent);
    };

    var addAssetEventTypeAssetToList = function (assetEventTypeAsset) {

        var assetEventTypeAssetObj = {}
        if (assetEventTypeAssets.length == 0) {
            assetEventTypeAssetObj['index'] = 0;
        } else {
            CustomValidation.validateFieldNull(assetEventTypeAssetObj, 'index', assetEventTypeAssets.length);
        }
        setCommonDataToAssetEventTypeAssetObj(assetEventTypeAsset, assetEventTypeAssetObj);
        assetEventTypeAssets.push(assetEventTypeAssetObj);
    };

    var setCommonDataToAssetEventTypeAssetObj = function (assetEventTypeAsset, assetEventTypeAssetObj) {
        CustomValidation.validateFieldNull(assetEventTypeAssetObj, 'id', assetEventTypeAsset.id);
        CustomValidation.validateFieldNull(assetEventTypeAssetObj, 'assetId', assetEventTypeAsset.assetId);
        CustomValidation.validateFieldNull(assetEventTypeAssetObj, 'assetEventTypeId', assetEventTypeAsset.assetEventTypeId);
        CustomValidation.validateFieldNull(assetEventTypeAssetObj, 'assetEventTypeName', assetEventTypeAsset.assetEventTypeName);
        CustomValidation.validateFieldNull(assetEventTypeAssetObj, 'latestAssetEventId', assetEventTypeAsset.latestAssetEventId);
        CustomValidation.validateFieldNull(assetEventTypeAssetObj, 'latestAssetEventIndexId', assetEventTypeAsset.latestAssetEventIndexId);
        CustomValidation.validateFieldNull(assetEventTypeAssetObj, 'latestEventOccurDate', assetEventTypeAsset.latestEventOccurDate);
        CustomValidation.validateFieldNull(assetEventTypeAssetObj, 'latestEventOccurDateStr', moment(assetEventTypeAsset.latestEventOccurDate).format('YYYY-MM-DD hh:mm:ss A'));
        CustomValidation.validateFieldNull(assetEventTypeAssetObj, 'version', assetEventTypeAsset.version);
    };

    var removeAssetEventTypeAsset = function (index) {
        for (var i = 0; i < assetEventTypeAssets.length; i++) {
            if (assetEventTypeAssets[i].index == index) {
                assetEventTypeAssets.splice(i, 1);
                updateIndexes();
                resetAssetEventTypeAssetHtmlTable();
                break;
            }
        }
    };

    var setAssetEventTypeAssets = function (assetEventTypeAssets) {
        for (var i = 0; i < assetEventTypeAssets.length; i++) {
            addAssetEventTypeAssetToList(assetEventTypeAssets[i])
        };
        resetAssetEventTypeAssetHtmlTable();
    };

    var setAssetEvents = function (assetEvents) {
        for (var i = 0; i < assetEvents.length; i++) {
            addAssetEventToList(assetEvents[i])
        }
    };

    var updateIndexes = function () {
        for (var i = 0; i < assetEventTypeAssets.length; i++) {
            assetEventTypeAssets[i].index = i;
        }
    };
    
    var initAssetEvents = function () {
    	setAssetEventTypeAssets(thymeLeafAssetEventTypeAssets);
    };

    return {
    	
    	init: function () {
    		initButtons();
    		initAssetEvents();
    	},

        /***********************************************************************
         * AssetEventTypeAsset Add
         **********************************************************************/
        addAssetEventTypeAssetToList: function (assetEventTypeAsset) {
            addAssetEventTypeAssetToList(assetEventTypeAsset);
        },

        removeAssetEventTypeAsset: function (index) {
            removeAssetEventTypeAsset(index);
        },

        resetAssetEventTypeAssetHtmlTable: function () {
            resetAssetEventTypeAssetHtmlTable();
        },

        assetEventTypeHistory: function (assetId, assetEventTypeId) {
            assetEventTypeHistory(assetId, assetEventTypeId);
        },

        setAssetEventTypeAssets: function (assetEventTypeAssets) {
            setAssetEventTypeAssets(assetEventTypeAssets);
        },

        setAssetEvents: function (assetEvents) {
            setAssetEvents(assetEvents);
        },

        addAssetEvent: function () {
            addAssetEvent();
        },

        /**********************************************************
         * Initialize Modals
         *********************************************************/
        assetEventModal: function () {
            assetEventModal();
        }
    };

}();
