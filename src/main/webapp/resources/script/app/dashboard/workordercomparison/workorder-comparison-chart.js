var WorkorderComparisonChart = function () {
	
    var runCharts = function () {

    	$.ajax({
            type: "GET",
            url: 'restapi/dashboard/workorder-comparison-chart',
            error: function () {
                alert("An error occurred.");
            },
            success: function (data) {  
            	var openWo = getBarGraphData(data.openWorkOrders);
            	var closedWo = getBarGraphData(data.closeWorkOrders);
            	
            	createBarGraph(openWo, closedWo, data.openBarColor, data.closedBarColor);
            }
        }); 
    	
    };
    
    var getBarGraphData = function(data) {
    	var out = [];
    	$.each( data , function( key, value ) {
    		var dataObj = [value.month , value.count];
    		out.push(dataObj);
    	});
    	return out;
	};
    
    var createBarGraph = function(data1, data2, color1, color2) {
    	
    	$.plot("#placeholder3",  [
        	{ 
        		data: data1,
        		color: color1,
	        	label: ' Open',
	            bars: {
	                show: true,
	                barWidth: 0.4,
	                align: "right",
	            }
	        },
	        {
	            data: data2,
	            color: color2,
	            label: ' Close',
	            bars: {
	                show: true,
	                barWidth: 0.4,
	                align: "left", 
	            }
	        }
        	
        ], {
        	xaxis: {
                mode: "categories",  
                tickLength: 1,

            },
            grid: {
                hoverable: true,
            },
            yAxis: {
                allowDecimals: false,
            }
        }); 
	}; 
	
	var initWorkOrderComparisonCharts = function(woComparison){ 
		
		//var previousWeek = [ woComparison.previousWeekOpenWo, woComparison.previousWeekClosedWo ]; 
		var previousWeek = [ woComparison.allCompletedWo, woComparison.allOnTimeCompletedWo ]; 
		var currentWeek = [ woComparison.currentWeekOpenWo, woComparison.currentWeekClosedWo ]; 
		var nextWeek = [ woComparison.nextWeekOpenWo, woComparison.nextWeekClosedWo ]; 
		
		setChartData(previousWeek, currentWeek, nextWeek);		
	};
	
	var setChartData = function(previousWeek, currentWeek, nextWeek) {
		previousWeekChart(previousWeek);
		currentWeekChart(currentWeek);
		nextWeekChart(nextWeek); 
	};
	
	var previousWeekChart = function( previousWeek ){

		if (previousWeek[0] < 1 && previousWeek[1] < 1) { 
			createNoDataDoughnutChart ("previous", [1] , "");					
		} else {
			//createDoughnutChart ("previous", previousWeek , "Previous Week");
			createDoughnutChartOnTimeCompleted ("previous", previousWeek , "");
		} 
	};
	
	var currentWeekChart = function( currentWeek ){ 
    	if (currentWeek[0] < 1 && currentWeek[1] < 1) {  
    		createNoDataDoughnutChart ("current", [1] , "Current Week");					
		} else {
			createDoughnutChart ("current", currentWeek , "Current Week");
		} 
    	
	};
	
	var nextWeekChart = function( nextWeek ){ 
		
    	if (nextWeek[0] < 1 && nextWeek[1] < 1) { 
    		createNoDataDoughnutChart ("next", [1] , "Next Week");					
		} else {
			createDoughnutChart ("next", nextWeek , "Next Week");
		}
    	 
	};
	
	var createDoughnutChartOnTimeCompleted = function(canvas, data, title){ 
		
		var ctx = document.getElementById(canvas); 
		new Chart(ctx , {
			type: 'doughnut',
			data: {
				datasets: [{
					data: data,
					backgroundColor: [
						'rgb(55,183,243)', 
						'rgb(203, 75, 75)', 
						], 
				}],
				labels: [
					'Late/Before', 
					'On Time', 
					]
			},
			options: {
				responsive: false,
				circumference:Math.PI,
				rotation:Math.PI,
				legend: {
					position: 'top',
					fullWidth:true,
					boxWidth:60,
					fontSize:16
				},
				title: {
					fontSize: 13,
					display: false,
					text: title
				},
				animation: {
					animateScale: true,
					animateRotate: true
				}, 
			}
		});
	};
	
	var createDoughnutChart = function(canvas, data, title){ 
		
		var ctx = document.getElementById(canvas); 
		new Chart(ctx , {
			type: 'doughnut',
			data: {
				datasets: [{
					data: data,
					backgroundColor: [
						'rgb(55,183,243)', 
						'rgb(203, 75, 75)', 
						], 
				}],
				labels: [
					'Open', 
					'Closed', 
					]
			},
			options: {
				responsive: false,
				legend: {
					position: 'top',
					fullWidth:false,
				},
				title: {
					fontSize: 13,
					display: true,
					text: title
				},
				animation: {
					animateScale: true,
					animateRotate: true
				}, 
			}
		});
	};
	
	var createNoDataDoughnutChart = function(canvas, data, title){ 

		var ctx = document.getElementById(canvas); 
		new Chart(ctx , {
		    type: 'doughnut',
		    data: {
				datasets: [{
					data: data,
					backgroundColor: [ 
						'rgb(229, 231, 235)',
					], 
				}],
				labels: [ 
					'No Data',
				]
			},
			options: {
				responsive: false,
				legend: {
					position: 'top',
				},
				title: {
					fontSize: 13,
					display: true,
					text: title
				},
				tooltips: {
					enabled:false
				},
				animation: {
					animateScale: true,
					animateRotate: true
				}, 
			}
		});
	};
	
    return { 
    	
        init: function () {  
        	initWorkOrderComparisonCharts(woComparison);
        }
    };
}();