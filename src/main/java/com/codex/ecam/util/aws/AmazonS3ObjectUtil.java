package com.codex.ecam.util.aws;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.codex.ecam.service.asset.impl.AssetServiceImpl;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AmazonS3ObjectUtil {

	final Logger logger = LoggerFactory.getLogger(AssetServiceImpl.class);

	@Autowired
	private AmazonS3Util amazonS3Util;

	@Autowired
	private AmazonS3 s3Client;

	public ResponseEntity<ByteArrayResource> downloadByteArrayResourceResponseEntity(final String key, String fileName)
			throws IOException {

		if (s3Client.doesObjectExist(amazonS3Util.getS3BucketName(), key)) {

			S3Object s3object = null;

			try {

				s3object = s3Client.getObject(amazonS3Util.getS3BucketName(), key);

				final byte[] bytes = getBytesFromS3Object(s3object);

				final ByteArrayResource resource = new ByteArrayResource(bytes);

				final HttpHeaders headers = getHeaders(fileName, s3object, bytes);

				return new ResponseEntity<ByteArrayResource>(resource, headers, HttpStatus.OK);
			} finally {

				if (s3object != null) {
					try {
						s3object.close();
					} catch (final IOException e) {
						logger.error("Unable to close S3 object: {}", e.getMessage(), e);
					}
				}
			}

		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	public ResponseEntity<byte[]> downloadByteArrayResponseEntity(final String key, String fileName)
			throws IOException {

		if (s3Client.doesObjectExist(amazonS3Util.getS3BucketName(), key)) {

			S3Object s3object = null;

			try {

				s3object = s3Client.getObject(amazonS3Util.getS3BucketName(), key);

				final byte[] bytes = getBytesFromS3Object(s3object);
				final HttpHeaders headers = getHeaders(fileName, s3object, bytes);

				return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);

			} finally {

				if (s3object != null) {
					try {
						s3object.close();
					} catch (final IOException e) {
						logger.error("Unable to close S3 object: {}", e.getMessage(), e);
					}
				}

			}

		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	public byte[] downloadByteArray(final String key) throws IOException {

		if (s3Client.doesObjectExist(amazonS3Util.getS3BucketName(), key)) {

			S3Object s3object = null;

			try {

				s3object = s3Client.getObject(amazonS3Util.getS3BucketName(), key);

				return getBytesFromS3Object(s3object);

			} finally {

				if (s3object != null) {
					try {
						s3object.close();
					} catch (final IOException e) {
						logger.error("Unable to close S3 object: {}", e.getMessage(), e);
					}
				}

			}

		}
		return null;
	}

	public void downloadToResponse(final String key, String fileName, HttpServletResponse response) throws IOException {

		if (s3Client.doesObjectExist(amazonS3Util.getS3BucketName(), key)) {

			S3Object s3object = null;

			try {

				s3object = s3Client.getObject(amazonS3Util.getS3BucketName(), key);

				final byte[] bytes = getBytesFromS3Object(s3object);

				response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
				response.addHeader("Content-Length", String.valueOf(bytes.length));
				response.addHeader("Content-type", s3object.getObjectMetadata().getContentType());

				FileCopyUtils.copy(bytes, response.getOutputStream());
				response.getOutputStream().flush();

			} finally {

				if (s3object != null) {
					try {
						s3object.close();
					} catch (final IOException e) {
						logger.error("Unable to close S3 object: {}", e.getMessage(), e);
					}
				}
			}

		}
	}

	private byte[] getBytesFromS3Object(S3Object s3object) throws IOException {
		final S3ObjectInputStream inputStream = s3object.getObjectContent();
		return IOUtils.toByteArray(inputStream);
	}

	private HttpHeaders getHeaders(String fileName, S3Object s3object, final byte[] bytes) {
		final HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		headers.add("Content-Length", String.valueOf(bytes.length));
		headers.add("Content-type", s3object.getObjectMetadata().getContentType());
		return headers;
	}

	public void deleteS3Object(String key) throws Exception {

		if (s3Client.doesObjectExist(amazonS3Util.getS3BucketName(), key)) {
			s3Client.deleteObject(amazonS3Util.getS3BucketName(), key);
		}
	}

	public void uploadS3Object(String key, MultipartFile file) throws IOException {

		final ObjectMetadata meta = new ObjectMetadata();
		meta.setContentType(file.getContentType());
		meta.setContentLength(file.getBytes().length);

		s3Client.putObject(amazonS3Util.getS3BucketName(), key, file.getInputStream(), meta);

	}

	public void uploadS3Object(String key, InputStream inputStream) throws IOException {

		final ObjectMetadata meta = new ObjectMetadata();
		meta.setContentLength(inputStream.available());

		s3Client.putObject(new PutObjectRequest(amazonS3Util.getS3BucketName(), key, inputStream, meta));

	}

	public BufferedImage downloadBufferedmage(String key) throws IOException {
		BufferedImage img = null;
		S3Object s3object = null;
		try {

			if (s3Client.doesObjectExist(amazonS3Util.getS3BucketName(), key)) {

				s3object = s3Client.getObject(amazonS3Util.getS3BucketName(), key);

				final S3ObjectInputStream inputStream = s3object.getObjectContent();

				img = ImageIO.read(inputStream);
			}

		} finally {

			if (s3object != null) {
				// Close the object
				s3object.close();
			}

		}

		return img;
	}
}