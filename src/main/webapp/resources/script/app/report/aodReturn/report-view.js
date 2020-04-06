var AODReturnReportView = function () {

	var initButtons = function() {
		initButtonViewReport();
	};
	
	var initButtonViewReport = function() {
	    $('#viewAODReturnReport').on('click', function (event) {
	    	if ($('#frmReportAODReturn').valid()) {				
	    		AODReturnDetailTable.init();
			}
	    });
	};
	
	var runAODInput = function () {
		$("#aodNo").inputClear({
			placeholder: "Select a AOD",
			btnMethod: "AODReturnReportView.aodView()",
		});
	};
    var aodView = function () {
		console.log("sadasd")
        var $modal = $('#common-modal');
		CustomComponents.ajaxModalLoadingProgressBar();
		console.log("sadasd")

        setTimeout(function () {
            var url = '../aodreturn/aodView';
            $modal.load(url, '', function () {
				var func = "AODReturnReportView.setAOD";
                dtAOD.dtAODList('../../restapi/aod/getApprovedAOD','AODReturnReportView.setAOD');
                $modal.modal();
            });
        }, 1000);
    };
    var setAOD = function (id, name) {
    	$("#aodId").val(id);
    	$("#aodNo").val(name);
    	$('#common-modal').modal("hide");
    };
    return {
        init: function () {
        	initButtons();
        	runAODInput();
        },
        aodView:function(){
        	aodView();
        },
        setAOD:function(id,name){
        	setAOD(id,name);
        }


    };
}();