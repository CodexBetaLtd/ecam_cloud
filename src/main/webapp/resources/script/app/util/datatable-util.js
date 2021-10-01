var DataTableUtil = function () {
	
	var deleteMultiple = function (oTable, elementId, url, id) {
		$("#" + elementId).on('click', function() {
			var data = oTable.rows( { selected: true }).data();
			var ids = [];
			for (var i = 0; i < data.length; i++) {
				var o = data[i];
				ids[i] = o[id];
			}
			
			if (ids.length > 0) { 
				var s = "../" + url + "/delete-multiple?ids=" + ids;
				confirm(s); 
			} else {
				CustomComponents.sweetAlertError("Cannot find any selected rows!");
			}
		});
	};
	
	var deleteMultipleFunc = function(oTable, elementId, func, id){
		$(document).on('click',"#" + elementId, function() {
			var data = oTable.rows( { selected: true }).data();
			var ids = [];
			for (var i = 0; i < data.length; i++) {
				var o = data[i];
				ids[i] = o[id];
			}
			
			if (ids.length > 0) {
				confirmFunc(func, ids); 
			} else {
				CustomComponents.sweetAlertError("Cannot find any selected rows!");
			}
		});
	};
	
	var confirmFunc = function(func, ids) {
		 setTimeout(function () {
			 swal({
					title: '',
					text: "Are You Sure? You want to delete ?",
					showCancelButton: true,
					confirmButtonText: 'Yes',
				    cancelButtonText: "No",
				    customClass: "swal-error",
		        }, function(ifConfirmed) {
					if (ifConfirmed) {
						var f = func+"('" + ids +"');";
						eval(f);
					} else {
						return false;
					}
				});

		 }, 500); 
	};
	
	var confirm = function(url) {
		 setTimeout(function () {
			 swal({
					title: '',
					text: "Are You Sure? You want to delete ?",
					showCancelButton: true,
					confirmButtonText: 'Yes',
				    cancelButtonText: "No",
				    customClass: "swal-error",
		        }, function(ifConfirmed) {
					if (ifConfirmed) {
						window.location.href = url;
					} else {
						return false;
					}
				});

		 }, 500); 
		
	};
	
	var editRow = function(oTable, url, id){ 
		oTable.on('click', 'td', function () {
			if (!$(this).hasClass("select-checkbox")) {		
				ajaxModalLoadingProgressBar(); 
				var data = oTable.row(this).data();
				window.location.href = "../" + url + "/edit?id=" + data[id];
			}
		});
	};

	var ajaxModalLoadingProgressBar = function () {
        $("#loader-modal div").addClass("ajaxprogress-bar");
        $('#loader').fadeIn("slow");
        $(document).ajaxComplete(function () {
            $('#loader').fadeOut("slow");
        });
	};
	 
	return {
		
		deleteRows: function(oTable, elementId, url, id) {
			return deleteMultiple(oTable, elementId, url, id);
		},
		
		
		deleteRowsFunc: function(oTable, elementId, func, id) {
			return deleteMultipleFunc(oTable, elementId, func, id);
		},
		
		editRow: function(oTable, url, id) {
			return editRow(oTable, url, id);
		}
	
	
	};
	
	
	
}();