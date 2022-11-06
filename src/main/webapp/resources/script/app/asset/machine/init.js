jQuery(document).ready(function() {
	Main.init();
	MachineHome.init();
	initButtons();
});

var initButtons = function() {

	$(document).on('click', '.machine-datatable', function(event) {
		event.preventDefault();
		CustomComponents.ajaxModalLoadingProgressBar();
		getMachineView();
	});

	$(document).on('click', '.machine-treeview', function(event) {
		event.preventDefault();
		CustomComponents.ajaxModalLoadingProgressBar();
		getMachineTreeView();
	});

	$('#btn-import-file').on('click', function() {
		// FileAddModal.addFile(this);
		importAssetView()
	});

};

var runBusinessSelect = function () {
    $("#businessId").select2({
        placeholder: "Select a Business",
        allowClear: true,
        dropdownParent: $("#common-modal")

    });
};

var importAssetView = function() {
	var $modal = $('#common-modal');
	CustomComponents.ajaxModalLoadingProgressBar();
	setTimeout(function() {
		var url = '../../asset/assetimportview';
		$modal.load(url, '', function() {
			runDropzone();
			runBusinessSelect();
			$modal.modal();
		});
	}, 1000);
}

var runDropzone = function() {
	var assetCode = $('#code').val()
	            var businessId = $("#businessId option:selected").val();
	        $("#businessId").change(function () {
            businessId = $("#businessId option:selected").val();

        });
		var myDropzone = new Dropzone(".dropzone", { 
			url: $('#frm_asset_import').attr('action'),
			paramName : "fileData",
			maxFilesize : 10.0, // MB
            autoProcessQueue: false,
            uploadMultiple: false, 
            clickable: true,
            maxFilesize: 1,
            maxFiles: 1,
            addRemoveLinks: true, 
			acceptedFiles : ".csv,.xlsx",
		
		    init: function () { 
		        var myDropzone = this;  
		        $("#btn-import-assets").click(function (e) {
		        	e.preventDefault();
		        	if($('#businessId').val() != null &&  $('#businessId').val() != ""){  		        		
			            $("#importBusinessId").val($("#businessId").val());
		        		myDropzone.processQueue();
		        	} else{
		    			CustomComponents.sweetAlertError('Please Select Business First ! and Try Again.');
		    		}
		        }); 
		    },
		    success: function(file, response) { 
		    	var $modal = $('#common-modal');
		setTimeout(function () {
			$modal.empty().append(response);
			runDropzone();
			runBusinessSelect();
			$modal.modal();
				}, 1000);
            },
			error : function(file, response) {
			$modal.empty().append(response);

			runDropzone();
			runBusinessSelect();
			$modal.modal();
			},
		dictDefaultMessage : "Drop files or click here to Import Asset List "

		}); 

	$('#fileRefId').val(assetCode)

};

var getMachineView = function(id) {
	$.ajax({
		url : "../../asset/machine-table",
		type : 'GET',
		success : function(response) {
			CustomComponents.ajaxModalLoadingProgressBar();
			$("#tableDiv").empty().append(response);
			MachineHome.init();
		}
	});
};

var getMachineTreeView = function(id) {
	$.ajax({
		url : "../../asset/machine-table",
		type : 'GET',
		success : function(response) {
			CustomComponents.ajaxModalLoadingProgressBar();
			$("#tableDiv").empty().append(response);
			MachineTreeView.init();
		}
	});
};