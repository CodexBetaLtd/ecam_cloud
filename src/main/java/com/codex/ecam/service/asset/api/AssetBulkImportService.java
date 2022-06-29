package com.codex.ecam.service.asset.api;

import org.springframework.web.multipart.MultipartFile;

public interface AssetBulkImportService {

	void importBulk(MultipartFile fileData, Integer bussinessId) throws Exception;

}
