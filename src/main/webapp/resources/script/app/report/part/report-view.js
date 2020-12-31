var PartReportView = function () {

	/*****************************************
	 * Initialize Buttons
	 *****************************************/

	var initButtons = function() {
		initButtonViewReport();
	};
	
	var initButtonViewReport = function() {
	    $('#btn_view_report').on('click', function (event) {
	    	if ($('#frm_part_report').valid()) {				
	    		DataTablePartReport.init();
			}
	    });
	};
	
	/*****************************************
	 * Initialize Select2 Components
	 *****************************************/
	var initSelect2Components = function() {
		initSelect2RepItemType();
	};
	
	var initSelect2RepItemType = function() {
		$("#partType").select2({
			placeholder: "Select a Item Type",
            allowClear: true
		});
	};
	
    var initSearchButton = function () {
    	$('#btn-part-search').on('click', function () {
    		DataTablePartReport.init();
    		
    	});
    };
    
    var initPrintPDFButton = function () {
    	$('#btn-part-print-pdf').on('click', function () {
    			$('#document-type').val("pdf");
    			$('#frm_part_report').attr('action', "../../report/part/print").submit();
    		
    	});
    };
    
    var initPrintCSVButton = function () {
        $('#btn-part-print-csv').on('click', function () {
            	$('#document-type').val("csv");
            	$('#frm_part_report').attr('action', "../../report/part/print").submit();
            
        });
    };
    return {
    	
        init: function () {
        	initSelect2Components();
            initButtons();
            initPrintPDFButton();
            initPrintCSVButton();
            initSearchButton();
            DataTablePartReport.init();
        }

    };
}();