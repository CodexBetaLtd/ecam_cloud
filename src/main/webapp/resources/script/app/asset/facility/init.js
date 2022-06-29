jQuery(document).ready(function () {
    Main.init();
    FacilityHome.init();  
    initButtons();
}); 

var initButtons = function() {
	
	$(document).on('click', '.panel-datatable', function(event) {
		event.preventDefault(); 
		getFacilityView();
	});
	
	$(document).on('click', '.panel-treeview', function(event) {
		event.preventDefault()
		getFacilityTreeView();
	});
	
}; 

var getFacilityView = function (id) {		
	$.ajax({
		url: "../../asset/facility-table",
		type: 'GET',
		success: function(response) { 
			CustomComponents.ajaxModalLoadingProgressBar();
			$("#tableDiv").empty().append(response); 
			FacilityHome.init();
		}
	});
};

var getFacilityTreeView = function (id) {		
	 $.ajax({
      url: "../../asset/facility-table",
      type: 'GET',
      success: function(response) { 
  		CustomComponents.ajaxModalLoadingProgressBar();
        $("#tableDiv").empty().append(response); 
  		FacilityTreeView.init();
      }
  });
};
