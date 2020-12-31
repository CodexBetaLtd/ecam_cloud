jQuery(document).ready(function() {
    Main.init();
    BusinessAdd.init();
    PersonnelTab.init();
    TabGeneral.init();
    
    /* -----------------------------------------------------
     * ------------------- Business Table-----------------------
     * -----------------------------------------------------*/
	$(document).on('click', '.btnDeleteRow', function () {
	    ($(this).closest("tr")).remove();
	});

});