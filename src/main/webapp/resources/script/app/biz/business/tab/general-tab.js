var TabGeneral = function () { 

	var initView = function() {
		
		$('#roleSupplier').on('ifChecked', function () {
			$("#roleSupplier").val(true);
			$('#roleCustomer').iCheck('uncheck');
			$("#roleCustomer").val(false);  
		});
		
        $('#roleCustomer').on('ifChecked', function () {
            $("#roleSupplier").iCheck('uncheck');
            $("#roleSupplier").val(false);
			$("#roleCustomer").val(true);
        }); 
        
        $('#roleCustomer').on('ifUnchecked', function () {
        	$("#roleCustomer").val(false);
        	$("#roleSupplier").val(false);
        });
        
        $('#roleSupplier').on('ifUnchecked', function () {
        	$("#roleSupplier").val(false);
            $("#roleCustomer").val(false);
        });
        
	};
	
    return {
        init: function () { 
        	initView(); 
        }, 
    };
    
}();