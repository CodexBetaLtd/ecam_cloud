var TabMenu = function () {
    
    var initRadioButtons = function () {
        $('#select_all').on('ifChecked', function(event){
            selectAll();
        });
        
        $('#deselect_all').on('ifChecked', function(event){
            deselectAll();
        });
    };
    
    function selectAll() {
        $(".cb-menu").iCheck('check');
    };
    
    function deselectAll() {
        $(".cb-menu").iCheck('uncheck');
    };

    return {
        init: function () {
            initRadioButtons();
        }
    };
	
}();