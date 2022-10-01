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
	    	console.log(businessId)

	$(".dropzone")
			.dropzone(
					{
						paramName : "fileData",
						maxFilesize : 10.0, // MB
						addRemoveLinks : true,
						maxFiles : 1,
						autoProcessQueue : false,
						acceptedFiles : ".csv,.xlsx",
						init : function() {
							
							$('#uploadingGif').css("display","none")
							$('#uploadingGif').css("padding","20px")
							var submitButton = document
									.querySelector("#btn-import-assets")
							fileDropzone = this;
							submitButton.addEventListener("click", function() {
								fileDropzone.processQueue();
							});
							this.on("addedfiles", function(event) {
								
							})

						},
						sending : function(file, xhr, formData) {
							$('#dropZoneText').empty();
							$('#dropZoneText').css("color","#008000")
							$('#dropZoneText').css("font-weight","bold")
							$('#uploadedFile').css("border-color","#008000")
							$('#uploadingGif').css("display","")
							$('#uploadingGif').css("padding","20px")
				    		$('#dropZoneText').text("Please Wait File Uploading Progress");
						      formData.append("bussinessId", businessId);
						},
						success : function(file, response) {
							$('#dropZoneText').empty();
							$('#dropZoneText').css("color","#008000")
							$('#dropZoneText').css("font-weight","bold")
							$('#uploadedFile').css("border-color","#008000")
							$('#uploadingGif').css("display","none")
							$('#dropZoneText').css("padding","20px")
				    		$('#dropZoneText').text("File Sucessfully Uploaded");
							console.log(response)
						},
						error : function() {
							$('#dropZoneText').empty();
							$('#dropZoneText').css("color","#a94442")
							$('#dropZoneText').css("font-weight","bold")
							$('#dropZoneText').css("padding","20px")
							$('#uploadedFile').css("border-color","#a94442")
				    		$('#dropZoneText').text("Invalid file.Unable to process for upload this file");

						},
						dictDefaultMessage : "Drop files or click here to Import Asset List ",
					});
	;
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