package com.neolith.focus.service.dashboard.api;

import com.neolith.focus.dto.dashboard.WorkOrderComparisonChartDataDTO; 

public interface WorkOrderComparisonService {

	WorkOrderComparisonChartDataDTO getWoComparisonChartData() throws Exception;
	
}
