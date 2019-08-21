/**
 * This JS contain the common components which need to dev the FOCUS functionality
 */

var CustomComponents = function () {


    var nullValueReplace = function (val) {
        return val == null ? "" : val;
    };

    var emptyTableRow = function (tableName, colspanCount) {
        return $("#" + tableName + " > tbody").html("<tr><td colspan='" + colspanCount + "' align='center'> <i>Table Data Not Found.</i></td></tr>");
    };

    var emptyTableCustomeMessageRow = function (tableName, colspanCount, message) {
        return $("#" + tableName + " > tbody").html("<tr><td colspan='" + colspanCount + "' align='center'> <i>'" + message + "'</i></td></tr>");
    };

    var ajaxModalLoadingProgressBar = function () {
        $("#loader div").addClass("ajaxprogress-bar");
        $('#loader').fadeIn("slow");
        $(document).ajaxComplete(function () {
            $('#loader').fadeOut("slow");
        });
    };
    
    var getSuccessMsgDiv = function (msg) {
    	var html = '<div class="alert alert-success"><button data-dismiss="alert" class="close">&times;</button>' +
    	'<i class="fa fa-check-circle"></i><span>' + msg +'</span></div>';
    	
    	return html;
    };
    
    var getSuccessMsgDivWithUrl = function (msg, mappingUrlText, mappingUrl) {
    	var html = '<div class="alert alert-success"><button data-dismiss="alert" class="close">&times;</button>' +
            '<i class="fa fa-check-circle"></i> <span>' + msg +'</span> <a target="_blank" href="'+ mappingUrl +'" ><span><b>' + mappingUrlText + '</b></span></a></div>';
    	
    	return html;
    };
    
    var getErrorMsgDiv = function (msg) {
    	var html = '<div class="alert alert-danger"><button data-dismiss="alert" class="close">&times;</button>' +
            '<i class="fa fa-check-circle"></i><span>' + msg +'</span></div>';
    	
    	return html;
    };
    
    var fileInput = function( divId, browseOnZone, dropZone, src, alt) {
    	$("#"+divId).fileinput({   
            overwriteInitial: false,
            maxFileSize: 2000,    
            showClose: false,
            showCaption: false, 
            dropZoneEnabled: dropZone,
            browseOnZoneClick: browseOnZone,
            browseLabel: '',
            removeLabel: '',
            browseIcon: '<i class="glyphicon glyphicon-folder-open"></i>',
            removeIcon: '<i class="glyphicon glyphicon-remove"></i>',
            removeTitle: 'Cancel or reset changes',
            elErrorContainer: '#kv-avatar-errors',
            msgErrorClass: 'alert alert-block alert-danger',     
            defaultPreviewContent: '<img height="160px" src="'+ src +'" alt="'+ alt+'" >',
            layoutTemplates: { main2: '{preview} {remove} {browse} ', footer:'', },
            allowedFileExtensions: ["jpg", "jpeg", "png", "gif"]
        }); 
	};
    
    return {

        nullValueReplace: function (val) {
            return nullValueReplace(val);
        },

        emptyTableRow: function (tableName, colspanCount) {
            return emptyTableRow(tableName, colspanCount);
        },

        emptyTableCustomeMessageRow: function (tableName, colspanCount, message) {
            return emptyTableCustomeMessageRow(tableName, colspanCount, message);
        },

        ajaxModalLoadingProgressBar: function () {
            return ajaxModalLoadingProgressBar();
        },
        
        getSuccessMsgDiv: function (msg) {
        	return getSuccessMsgDiv(msg);
        },
        
        getSuccessMsgDivWithUrl: function (msg, mappingUrlText, mappingUrl) {
        	return getSuccessMsgDivWithUrl(msg, mappingUrlText, mappingUrl);
        },
        
        getErrorMsgDiv: function (msg) {
        	return getErrorMsgDiv(msg);
        },
        
        fileInput: function(divId, browseOnZone, dropZone, src, alt) {
        	fileInput(divId, browseOnZone, dropZone, src, alt);
		}
    };

}();

