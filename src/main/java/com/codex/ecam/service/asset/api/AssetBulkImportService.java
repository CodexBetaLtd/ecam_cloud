package com.codex.ecam.service.asset.api;

import org.springframework.web.multipart.MultipartFile;

import com.codex.ecam.result.asset.AssetResult;

public interface AssetBulkImportService {

	AssetResult importBulk(MultipartFile fileData, Integer bussinessId) ;

}
