
var TabPagePermission = function () {
    
    function initRadioButtons() {
        $('#pp_select_all').on('ifChecked', function(event){
            accordionExpand();
            selectAll();
        });
        
        $('#pp_deselect_all').on('ifChecked', function(event){
            deselectAll();
        });
    };
    
    function selectAll() {
        $(".cb-pp").iCheck('check');
    };
    
    function deselectAll() {
        $(".cb-pp").iCheck('uncheck');
    };
    
    function initAccordion() {
        
        $('#btn_expand_colapse').on('click', function() {
            
            var b = $("#btn_expand_colapse").text().trim();
           
            if (b == 'Expand All') {
                accordionExpand();
            } else {
                accordionCollapse();
            }
        });
        
    };
    
    function accordionExpand(){
        $("#btn_expand_colapse").text('Collapse All');
        $('.accordion-header').each(function () {
            $(this).find('.accordion-toggle').removeClass('collapsed').addClass('collapse').attr('aria-expanded', true);
            $(this).closest('.accordion-custom').find(".panel-collapse").addClass('in').attr('aria-expanded', true);
        });
    };
    
    function accordionCollapse(){
        $("#btn_expand_colapse").text('Expand All');
        $('.accordion-header').each(function () {
            $(this).find('.accordion-toggle').removeClass('collapse').addClass('collapsed').attr('aria-expanded', false);
            $(this).closest('.accordion-custom').find(".panel-collapse").removeClass('in').attr('aria-expanded', false);
        });
    };

    return {
        init: function () {
            initRadioButtons();
            initAccordion();
        }
    };
	
}();