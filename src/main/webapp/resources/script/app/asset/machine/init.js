jQuery(document).ready(function () {
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
	
};

var getMachineView = function (id) {		
	$.ajax({
		url: "../../asset/machine-table",
		type: 'GET',
		success: function(response) { 
			CustomComponents.ajaxModalLoadingProgressBar();
			$("#tableDiv").empty().append(response); 
			MachineHome.init();
		}
	});
};

var getMachineTreeView = function (id) {		
	 $.ajax({
      url: "../../asset/machine-table",
      type: 'GET',
      success: function(response) { 
  		CustomComponents.ajaxModalLoadingProgressBar();
        $("#tableDiv").empty().append(response); 
        MachineTreeView.init();
      }
  });
};