package com.codex.ecam.util.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AmazonS3Util {

	@Autowired
	Environment environment;

	public String getS3BucketName() {
		return environment.getProperty("cloud.aws.s3.bucket");
	}

	public String getCommonUploadKey() {
		return environment.getProperty("upload.location.s3");
	}

	public String getWorkOrderFileUploadKey() {
		return environment.getProperty("upload.location.workorder.file.s3");
	}

	public String getAssetFileUploadKey() {
		return environment.getProperty("upload.location.asset.file.s3");
	}

	public String getAssetImageUploadKey() {
		return environment.getProperty("upload.location.asset.image.s3");
	}

	public String getAssetQRUploadKey() {
		return environment.getProperty("upload.location.asset.qr.s3");
	}

}