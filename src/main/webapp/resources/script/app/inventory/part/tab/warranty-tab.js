var WarrantyTab = function () {

    var warrantyAddModal = function () {
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = '../part/warranty-add-view';
            $modal.load(url, '', function () {
                WarrantyAddModal.init();
                $modal.modal();
            });
        }, 1000);
    };

    var resetWarrantyHtmlTable = function () {

        if (warranties.length > 0) {
            var row, warranty;
            $("#warranty-tbl > tbody").html("");

            for (row = 0; row < warranties.length; row++) {
                warranty = warranties[row];
                var html = "<tr id='row_" + row + "' >" +
                    "<input id='warranties" + row + ".warrantyId' name='warranties[" + row + "].warrantyId' value='" + warranty.warrantyId + "' type='hidden'>" +
                    "<input id='warranties" + row + ".warrantyMeterReadingUnitId' name='warranties[" + row + "].warrantyMeterReadingUnitId' value='" + warranty.warrantyMeterReadingUnitId + "' type='hidden' >" +
                    "<input id='warranties" + row + ".warrantyProviderId' name='warranties[" + row + "].warrantyProviderId' value='" + warranty.warrantyProviderId + "' type='hidden' >" +
                    "<input id='warranties" + row + ".warrantyCertificateNo' name='warranties[" + row + "].warrantyCertificateNo' value='" + warranty.warrantyCertificateNo + "' type='hidden' >" +
                    "<input id='warranties" + row + ".warrantyDescription' name='warranties[" + row + "].warrantyDescription' value='" + warranty.warrantyDescription + "' type='hidden' >" +
                    "<input id='warranties" + row + ".warrantyType' name='warranties[" + row + "].warrantyType' value='" + warranty.warrantyType + "' type='hidden' >" +
                    "<input id='warranties" + row + ".warrantyUsageTermType' name='warranties[" + row + "].warrantyUsageTermType' value='" + warranty.warrantyUsageTermType + "' type='hidden' >" +
                    "<input id='warranties" + row + ".warrantyMeterReadingValueLimit' name='warranties[" + row + "].warrantyMeterReadingValueLimit' value='" + warranty.warrantyMeterReadingValueLimit + "' type='hidden' >" +
                    "<input id='warranties" + row + ".warrantyExpiryDate' name='warranties[" + row + "].warrantyExpiryDate' value='" + warranty.warrantyExpiryDate + "' type='hidden' >" +
                    "<input id='warranties" + row + ".version' name='warranties[" + row + "].version' value='" + warranty.version + "' type='hidden' >" +
                    "<td><span>" + ( row + 1 ) + "</span></td>" +
                    "<td class='hidden-xs'><span>" + warranty.warrantyType + "</span></td>" +
                    "<td>" + warranty.warrantyExpiryDate + "</td>" +
                    "<td class='hidden-xs'><span>" + warranty.warrantyCertificateNo + "</span></td>" +
                    "<td class='center'>" +
                    "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
                    "<a onclick='WarrantyTab.editWarranty(" + warranty.warrantyIndex + ");' class='btn btn-xs btn-teal tooltips' data-placement='top' data-original-title='Edit' >" +
                    "<i class='fa fa-edit' ></i></a>" +
                    "<a onclick='WarrantyTab.removeWarranty(" + warranty.warrantyIndex + ");' class='btn btn-xs btn-bricky tooltips' data-placement='top' data-original-title='Remove' >" +
                    "<i class='fa fa-times fa fa-white' ></i></a></div></td></tr>";

                $('#warranty-tbl > tbody:last-child').append(html);
            }
        } else {

            $("#warranty-tbl > tbody").html("<tr><td colspan='6' align='center'>Please Add Warranty for the Part.</td></tr>");
        }

    };

    var addWarrantyToList = function (warranty) {

        var warrantyObj = {}
        if (warranty.warrantyIndex != null && warranty.warrantyIndex != "" && warranty.warrantyIndex >= 0) {
            warrantyObj = getWarrantyByIndex(warranty.warrantyIndex);
            setCommonDataToWarrantyObj(warranty, warrantyObj);
        } else {
            if (warranties.length == 0) {
                warrantyObj['warrantyIndex'] = 0;
            } else {
                validateFieldNull(warrantyObj, 'warrantyIndex', warranties.length);
            }
            setCommonDataToWarrantyObj(warranty, warrantyObj);
            warranties.push(warrantyObj);
        }
    };

    var setCommonDataToWarrantyObj = function (updatedWarrantyObj, warrantyObj) {
        CustomValidation.validateFieldNull(warrantyObj, 'warrantyId', updatedWarrantyObj.warrantyId);
        CustomValidation.validateFieldNull(warrantyObj, 'warrantyMeterReadingUnitId', updatedWarrantyObj.warrantyMeterReadingUnitId);
        CustomValidation.validateFieldNull(warrantyObj, 'warrantyProviderId', updatedWarrantyObj.warrantyProviderId);
        CustomValidation.validateFieldNull(warrantyObj, 'warrantyProviderName', updatedWarrantyObj.warrantyProviderName);
        CustomValidation.validateFieldNull(warrantyObj, 'warrantyCertificateNo', updatedWarrantyObj.warrantyCertificateNo);
        CustomValidation.validateFieldNull(warrantyObj, 'warrantyDescription', updatedWarrantyObj.warrantyDescription);
        CustomValidation.validateFieldNull(warrantyObj, 'warrantyType', updatedWarrantyObj.warrantyType);
        CustomValidation.validateFieldNull(warrantyObj, 'warrantyUsageTermType', updatedWarrantyObj.warrantyUsageTermType);
        CustomValidation.validateFieldNull(warrantyObj, 'warrantyMeterReadingValueLimit', updatedWarrantyObj.warrantyMeterReadingValueLimit);
        CustomValidation.validateFieldNull(warrantyObj, 'warrantyExpiryDate', updatedWarrantyObj.warrantyExpiryDate);
        CustomValidation.validateFieldNull(warrantyObj, 'version', updatedWarrantyObj.version);
    };

    var getWarrantyByIndex = function (warrantyIndex) {
        for (var i = 0; i < warranties.length; i++) {
            if (warranties[i].warrantyIndex == warrantyIndex) {
                return warranties[i];
            }
        }
    };

    var addWarranty = function () {

        var warranty = {};

        warranty['warrantyId'] = $('#warrantyId').val();
        warranty['warrantyIndex'] = $('#warrantyIndex').val();
        warranty['version'] = $('#warrantyVersion').val();
        warranty['warrantyMeterReadingUnitId'] = $('#warrantyMeterReadingUnitId').val();
        warranty['warrantyProviderId'] = $('#warrantyProviderId').val();
        warranty['warrantyProviderName'] = $('#warrantyProviderId').select2('data')[0].text;
        warranty['warrantyCertificateNo'] = $('#warrantyCertificateNo').val();
        warranty['warrantyDescription'] = $('#warrantyDescription').val();
        warranty['warrantyType'] = $('#warrantyType').val();
        warranty['warrantyUsageTermType'] = $('#warrantyUsageTermType').val();
        warranty['warrantyMeterReadingValueLimit'] = $('#warrantyMeterReadingValueLimit').val();
        warranty['warrantyExpiryDate'] = $('#warrantyExpiryDate').val();

        addWarrantyToList(warranty);

        resetWarrantyHtmlTable();

        $('#common-modal').modal('toggle');
    };

    var editWarranty = function (warrantyIndex) {
        var $modal = $('#common-modal');
        $('body').modalmanager('loading');
        setTimeout(function () {
            var url = '../part/warranty-add-view';
            $modal.load(url, '', function () {
                fillWarrantyEditForm(getWarrantyByIndex(warrantyIndex));
                WarrantyAddModal.init();
                $modal.modal();
            });
        }, 1000);
    };

    var fillWarrantyEditForm = function (warranty) {
        $('#warrantyId').val(warranty['warrantyId']);
        $('#warrantyIndex').val(warranty['warrantyIndex']);
        $('#warrantyVersion').val(warranty['version']);
        $('#warrantyMeterReadingUnitId').val(warranty['warrantyMeterReadingUnitId']);
        $('#warrantyProviderId').val(warranty['warrantyProviderId']);
        $('#warrantyCertificateNo').val(warranty['warrantyCertificateNo']);
        $('#warrantyDescription').val(warranty['warrantyDescription']);
        $('#warrantyType').val(warranty['warrantyType']);
        $('#warrantyUsageTermType').val(warranty['warrantyUsageTermType']);
        $('#warrantyMeterReadingValueLimit').val(warranty['warrantyMeterReadingValueLimit']);
        $('#warrantyExpiryDate').val(warranty['warrantyExpiryDate']);
    };

    var validateFieldNull = function (obj, field, value) {
        if (value != null && value != undefined) {
            obj[field] = value;
        } else {
            obj[field] = "";
        }
    };

    var removeWarranty = function (warrantyIndex) {
        for (var i = 0; i < warranties.length; i++) {
            if (warranties[i].warrantyIndex == warrantyIndex) {
                warranties.splice(i, 1);
                updateIndexes();
                resetWarrantyHtmlTable();
                break;
            }
        }
    };

    var updateIndexes = function () {
        for (var i = 0; i < warranties.length; i++) {
            warranties[i].warrantyIndex = i;
        }
    };

    return {
    	
    	init : function () {
    		resetWarrantyHtmlTable();
    	},

        /***********************************************************************
         * Warranty Add
         **********************************************************************/
        addWarranty: function () {
            addWarranty();
        },

        addWarrantyToList: function (warranty) {
            addWarrantyToList(warranty);
        },

        removeWarranty: function (index) {
            removeWarranty(index);
        },

        editWarranty: function (index) {
            editWarranty(index);
        },

        resetWarrantyHtmlTable: function () {
            resetWarrantyHtmlTable();
        },

        /**********************************************************
         * Initialize Modals
         *********************************************************/
        warrantyAddModal: function () {
            warrantyAddModal();
        }
    };
 }();