var Index = function () {  
	
	var countDownAnimation=function(){
       	$('.counter-count').each(function () {
            $(this).prop('Counter',0).animate({
                Counter: $(this).text()
            }, {
                duration: 5000,
                easing: 'swing',
                step: function (now) {
                    $(this).text(Math.ceil(now));
                }
            });
        });
	}
	
    return {
        init: function () {
        	WorkorderComparisonChart.init();
        	countDownAnimation();
        }
    
    };
}();