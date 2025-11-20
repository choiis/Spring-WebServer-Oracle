package com.singer.infrastructure.util;

import com.singer.infrastructure.config.S3Properties;
import jakarta.annotation.PostConstruct;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

/**
 * AWS SDK v2 + MinIO �샇�솚 S3 �쑀�떥由ы떚.
 * 湲곗〈 AmazonS3 湲곕컲 S3Util 怨� �룞�옉�쓣 理쒕��븳 鍮꾩듂�븯寃� 留욎땄.
 */
@Component("s3util")
public class S3Util {

    private final S3Properties s3Properties;

    private S3Client s3client;
    private String bucketName;

    public S3Util(S3Properties s3Properties) {
        this.s3Properties = s3Properties;
    }

    @PostConstruct
    private void init() {
        // 湲곗〈泥섎읆 properties �뿉�꽌 bucketName �쓣 �븳 踰� �꽭�똿
        this.bucketName = s3Properties.bucketName();

        AwsBasicCredentials credentials = AwsBasicCredentials.create(
                s3Properties.access(),
                s3Properties.secret()
        );

        // MinIO 媛숈� custom endpoint 瑜� �쐞�븳 �꽕�젙 (path-style �젒洹�)
        S3Configuration s3Configuration = S3Configuration.builder()
                .pathStyleAccessEnabled(true)
                .build();

        this.s3client = S3Client.builder()
                .endpointOverride(URI.create(s3Properties.endpoint()))
                .region(Region.US_EAST_1)  // region �� MinIO�뿉 �겕寃� �쁺�뼢 �뾾�쓬
                .serviceConfiguration(s3Configuration)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    /**
     * 湲곗〈:
     *  S3Object s3Object = s3client.getObject(bucketName, fileName);
     *  return s3Object.getObjectContent();
     *
     * AWS SDK v2 �뿉�꽌�룄 getObject(...) 媛� InputStream �쓣 諛붾줈 由ы꽩�븯誘�濡�,
     * 理쒕��븳 鍮꾩듂�븳 �삎�깭濡� �쑀吏�.
     */
    public InputStream getS3FileStream(@NonNull String fileName) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        ResponseInputStream<GetObjectResponse> stream = s3client.getObject(request);
        return stream; // 湲곗〈 getObjectContent() �� 媛숈� InputStream �뿭�븷
    }

    /**
     * �뙆�씪 �뾽濡쒕뱶 - 湲곗〈�뿉 �꽕媛� �궗�슜�븯�뜕 �삎�깭瑜� 洹몃�濡� �쑀吏�
     */
    public void uploadFile(@NonNull String fileName, @NonNull File file) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        s3client.putObject(request, RequestBody.fromFile(file.toPath()));
    }

    public void uploadInputStream(@NonNull String fileName,
                                  @NonNull InputStream inputStream,
                                  long contentLength,
                                  String contentType) {

        PutObjectRequest.Builder builder = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName);

        if (contentType != null && !contentType.isBlank()) {
            builder.contentType(contentType);
        }

        PutObjectRequest request = builder.build();

        s3client.putObject(request, RequestBody.fromInputStream(inputStream, contentLength));
    }

    /**
     * 湲곗〈 ObjectMetadata 諛섑솚 ���떊,
     * AWS SDK v2 �쓽 HeadObjectResponse 瑜� 諛섑솚.
     */
    public HeadObjectResponse getS3Meta(@NonNull String fileName) {
        HeadObjectRequest request = HeadObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        return s3client.headObject(request);
    }

    public void deleteS3File(@NonNull String fileName) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        s3client.deleteObject(request);
    }

    public void putS3File(@NonNull String fileName, @NonNull File file) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        s3client.putObject(request, RequestBody.fromFile(file.toPath()));
    }

    /**
     * 湲곗〈 AmazonS3.getUrl(bucketName, key) �� 鍮꾩듂�븳 �삎�깭濡� URL 援ъ꽦.
     * MinIO / custom endpoint �솚寃쎌뿉�꽌�룄 �룞�옉 媛��뒫.
     */
    public URL getS3Url(String fileName) {
        try {
            // endpoint 媛� �삁: http://localhost:9000 �씠�씪怨� 媛��젙�븯硫�,
            // 寃곌낵: http://localhost:9000/{bucketName}/{fileName}
            return new URL(s3Properties.endpoint()
                    + "/" + bucketName
                    + "/" + fileName);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to build S3 URL", e);
        }
    }
}
