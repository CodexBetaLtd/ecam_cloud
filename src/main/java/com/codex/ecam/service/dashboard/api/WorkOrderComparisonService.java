package com.codex.ecam.service.dashboard.api;

import com.codex.ecam.dto.dashboard.WorkOrderComparisonChartDataDTO; 

public interface WorkOrderComparisonService {

	WorkOrderComparisonChartDataDTO getWoComparisonChartData() throws Exception;
	
}
