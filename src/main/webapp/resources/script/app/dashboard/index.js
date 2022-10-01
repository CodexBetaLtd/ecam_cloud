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
	
	var initButton=function(){
		$('#high-priority-workorder-btn').on('click', function () {
			loadHighPriorityWODataTable();
		});	
		$('#open-workorder-btn').on('click', function () {
			loadOpenWODataTable();
		});	
		$('#low-stock-item-btn').on('click', function () {
			loadLowStockItemDataTable();
        });	
	}
	var loadOpenWODataTable=function(){
		
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = 'dashboard/open-workorder-view';
            $modal.load(url, '', function () {
            	OpenWorkOrders.init();
                $modal.modal();
            });
        }, 1000);
	}
	var loadHighPriorityWODataTable=function(){
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = 'dashboard/high-priority-workorder-view';
            $modal.load(url, '', function () {
            	HighPriorityWorkOrders.init();
                $modal.modal();
            });
        }, 1000);
	}
	var loadLowStockItemDataTable=function(){
        var $modal = $('#common-modal');
        CustomComponents.ajaxModalLoadingProgressBar();
        setTimeout(function () {
            var url = 'dashboard/low-stock-item-view';
            $modal.load(url, '', function () {
            	LowStockItem.initDataTable();
                $modal.modal();
            });
        }, 1000);
	}
    return {
        init: function () {
        	WorkorderComparisonChart.init();
        	countDownAnimation();
        	initButton();
        }
    
    };
}();