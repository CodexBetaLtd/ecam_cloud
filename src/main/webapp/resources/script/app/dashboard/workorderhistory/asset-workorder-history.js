var AssetWorkOrderHistory = function () {
    //function to initiate Full CAlendar
    var runCalendar = function () {
    	
        var $modal = $('#common-modal');
        
        /* initialize the calendar
				 -----------------------------------------------------------------*/
        var currentDate = new Date();
        var lastDay = moment(currentDate).subtract(30, 'days'); 
        var form = '';
        var calendar = $('#calendar').fullCalendar({ 
        	defaultView: 'lastThirtyDays', 
        	defaultDate: lastDay, 
        	titleRangeSeparator:' - ',
            header: {
              left: false,
              center: 'title',
              right: false
            },
            views: {
              lastThirtyDays: {
                type: 'month', 
                duration: { week: 4}
              }
            }, 
            events: "restapi/dashboard/asset-workorder-history", 
            eventRender: function (event, element, view) {              	
                var dateString = event.start.format("YYYY-MM-DD"); 
                $(view.el[0]).find('.fc-day[data-date=' + dateString + ']').css('background-color', event.cellColor );
            }, 
            dayRender: function (date, cell) { 
                if(date >= lastDay && date <= currentDate) {
                    cell.css("background-color", "#CC9 !important");
                } 
            },
             
            editable: false,
            droppable: false,
            selectable: false,
            selectHelper: false,
            eventClick: function (calEvent, jsEvent, view) {
                var date = calEvent.start.format('DD-MM-YYYY');  
                CustomComponents.ajaxModalLoadingProgressBar();
                setTimeout(function () {
                    var url = 'dashboard/asset-workorder-history-calendar-detail-view';
                    $modal.load(url, '', function () {
                    	WorkOrderLastThirtyDaysHistory.init(date);
                        $modal.modal({
                        	backdrop: 'static'
                        });
                    });
                }, 500); 
            }, 
        });
    };
    
    return {
        init: function () {
            runCalendar();
        }
    };
}();