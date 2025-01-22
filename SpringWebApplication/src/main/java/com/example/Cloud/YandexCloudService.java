package com.example.Cloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CompletableFuture;

@Service
public class YandexCloudService {

    private static final Logger logger = LoggerFactory.getLogger(YandexCloudService.class);

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.s3.access-key}")
    private String accessKey;

    @Value("${cloud.aws.s3.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.s3.endpoint}")
    private String endpointUrl;

    /**
     * Асинхронная загрузка файла в Yandex Object Storage с логированием и обработкой ошибок.
     *
     * @param file MultipartFile для загрузки
     * @return CompletableFuture с URL загруженного файла
     * @throws IOException если возникли проблемы с чтением файла
     */
    @Async
    public CompletableFuture<String> uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        // Логируем начало процесса загрузки
        logger.info("Начало загрузки файла '{}' в bucket '{}'", fileName, bucketName);

        // Установка AWS-креденциалов
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);

        try {
            // Инициализация клиента S3
            S3Client s3Client = S3Client.builder()
                    .endpointOverride(URI.create(endpointUrl)) // Указываем кастомный эндпоинт
                    .credentialsProvider(StaticCredentialsProvider.create(awsCredentials)) // Креденциалы
                    .region(Region.of("ru-central1")) // Регион Yandex Cloud
                    .build();

            // Загрузка файла в Object Storage
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName) // Указываем bucket
                            .key(fileName) // Указываем имя файла (ключ)
                            .build(),
                    software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()) // Тело файла
            );

            // Формируем URL загруженного файла
            String fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;

            // Логируем успешную загрузку
            logger.info("Файл '{}' успешно загружен в bucket '{}'. URL: {}", fileName, bucketName, fileUrl);

            return CompletableFuture.completedFuture(fileUrl);

        } catch (Exception e) {
            // Логируем ошибку
            logger.error("Ошибка при загрузке файла '{}' в bucket '{}': {}", fileName, bucketName, e.getMessage(), e);

            // Пробрасываем исключение с понятным сообщением
            throw new RuntimeException("Ошибка загрузки файла: " + e.getMessage(), e);
        }
    }
}
