jQuery(document).ready(function () {
    Main.init();
    WearHouseHome.init();  
    initButtons();
}); 

var initButtons = function() {
	
	$(document).on('click', '.panel-datatable', function(event) {
		event.preventDefault(); 
		getWareHouseView();
	});
	
	$(document).on('click', '.panel-treeview', function(event) {
		event.preventDefault()
		getWareHouseTreeView();
	});
	
}; 

var getWareHouseView = function (id) {		
	$.ajax({
		url: "../restapi/wearhouse/parent-warehouse",
		type: 'GET',
		success: function(response) { 
			CustomComponents.ajaxModalLoadingProgressBar();
			$("#tableDiv").empty().append(response); 
			WearHouseHome.init();
		}
	});
};

var getWareHouseTreeView = function (id) {		
	 $.ajax({
      url: "../restapi/wearhouse/parent-warehouse",
      type: 'GET',
      success: function(response) { 
  		CustomComponents.ajaxModalLoadingProgressBar();
        $("#tableDiv").empty().append(response); 
  		WareHouseTreeView.init();
      }
  });
};