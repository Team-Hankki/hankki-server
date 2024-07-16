package org.hankki.hankkiserver.external.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class S3Service {

    @Value("${aws-property.s3-endpoint}")
    private String bucketUrl;
    private final String bucketName;
    private final AwsConfig awsConfig;
    private static final List<String> IMAGE_EXTENSIONS = Arrays.asList("image/jpeg", "image/png", "image/jpg", "image/webp");


    public S3Service(@Value("${aws-property.s3-bucket-name}") final String bucketName, AwsConfig awsConfig) {
        this.bucketName = bucketName;
        this.awsConfig = awsConfig;
    }


    public String uploadImage(String directoryPath, MultipartFile image) throws IOException {
        final String key = directoryPath + generateImageFileName();
        final S3Client s3Client = awsConfig.getS3Client();

        validateExtension(image);
        validateFileSize(image);

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(image.getContentType())
                .contentDisposition("inline")
                .build();

        RequestBody requestBody = RequestBody.fromBytes(image.getBytes());
        s3Client.putObject(request, requestBody);
        return bucketUrl + key;
    }

    public void deleteImage(String key) throws IOException {
        final S3Client s3Client = awsConfig.getS3Client();

        s3Client.deleteObject((DeleteObjectRequest.Builder builder) ->
                builder.bucket(bucketName)
                        .key(key)
                        .build()
        );
    }


    private String generateImageFileName() {
        return UUID.randomUUID() + ".jpg";
    }


    private void validateExtension(MultipartFile image) {
        String contentType = image.getContentType();
        if (!IMAGE_EXTENSIONS.contains(contentType)) {
            throw new RuntimeException("이미지 확장자는 jpg, png, webp만 가능합니다.");
        }
    }

    private static final Long MAX_FILE_SIZE = 5 * 1024 * 1024L;

    private void validateFileSize(MultipartFile image) {
        if (image.getSize() > MAX_FILE_SIZE) {
            throw new RuntimeException("이미지 사이즈는 5MB를 넘을 수 없습니다.");
        }
    }

}
