var BusinessAddModal = function () {
	
	var initBusinessPartSelect = function () {
		$("#businessPartId").select2({
			placeholder: "Select a Business",
			allowClear: true,
			maximumSelectionLength: 2,
			dropdownParent: $("#common-modal")
		});
	};
    
	var initBusinessTypeSelect = function () {
        $("#businessTypeId").select2({
            placeholder: "Select a Business Type",
            allowClear: true,
            maximumSelectionLength: 2,
			dropdownParent: $("#common-modal")
        });
    };
    
    var initBusinessAddBtn = function () {
    	$('#addBusiness').on('click', function () {
    		BusinessTab.addBusiness();
        });
    };    

    return {

        init: function () {
        	initBusinessPartSelect();
        	initBusinessTypeSelect();
        	initBusinessAddBtn();
        }
    };
}();