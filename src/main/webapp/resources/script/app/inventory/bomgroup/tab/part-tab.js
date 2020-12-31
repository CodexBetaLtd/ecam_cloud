
var PartTab = function () {
	
	var initButtons = function () {

	    $('#btn-new-part').on('click', function () {
	    	PartTab.partSelectModalView();
	    });
		
	};  

    var partSelectModal = function () {
    	var bizId = $('#businessId').val();
    	if ( bizId != null && bizId > 0 ) {	 
	        var $modal = $('#common-modal');
	        CustomComponents.ajaxModalLoadingProgressBar();
	        setTimeout(function () {
	            var url = '../bomgroup/partmodelview';
	            $modal.load(url, '', function () {
	            	PartSelectModal.init('common-modal', bizId);
	                $modal.modal();
	            });
	        }, 1000);
        } else {
    		alert("Please Select a Business First.");
    	} 
    };


    var resetPartHtmlTable = function () {

        if (parts.length > 0) {
            var row, part;
            $("#bom-group-part-tbl > tbody").html("");

            for (row = 0; row < parts.length; row++) {
                part = parts[row];
                var html = "<tr id='row_" + row + "' >" +
                    "<input id='parts" + row + ".id' name='parts[" + row + "].id' value='" + part.id + "' type='hidden'>" +
                    "<input id='parts" + row + ".name' name='parts[" + row + "].name' value='" + part.name + "' type='hidden' >" +
                    "<td><span>" + ( row + 1 ) + "</span></td>" +
                    "<td class='hidden-xs'><span>" + part.name + "</span></td>" +
                    "<td class='center'>" +
	                    "<div class='visible-md visible-lg hidden-sm hidden-xs'>" +
	                    	ButtonUtil.getCommonBtnDelete("PartTab.removePart", part.id) +
	                    "</div>" +
                    "</td>" +
                    "</tr>";

                $('#bom-group-part-tbl > tbody:last-child').append(html);
            }
        } else {

            $("#bom-group-part-tbl > tbody").html("<tr><td colspan='3' align='center'>Please Add Parts for the BOM Group.</td></tr>");
        }

    };

    var removePart = function (id) {
        for (var i = 0; i < parts.length; i++) {
            if (parts[i].id == id) {
                parts.splice(i, 1);
            }
        }
        resetPartHtmlTable();
    };
    
    return {
    	
    	init: function() {
    		initButtons();
    		resetPartHtmlTable();
		}, 
		
        removePart: function (id) {
            removePart(id);
        }, 
        
        resetPartHtmlTable: function () {
        	resetPartHtmlTable();
		},
		
		partSelectModalView: function() {
			partSelectModal();
		}
    }; 
	
}();
