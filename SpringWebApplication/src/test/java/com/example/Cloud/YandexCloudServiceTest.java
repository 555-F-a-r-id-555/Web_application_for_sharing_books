package com.example.Cloud;

//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.util.ReflectionTestUtils;
//import software.amazon.awssdk.core.sync.RequestBody;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.PutObjectRequest;
//import software.amazon.awssdk.services.s3.model.PutObjectResponse;
//
//import java.io.IOException;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutionException;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//class YandexCloudServiceTest {
//
//    @Mock
//    private S3Client s3Client;
//
//    @InjectMocks
//    private YandexCloudService yandexCloudService;
//
//    @Value("${cloud.aws.s3.endpoint}")
//    private String endpointUrl;
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucketName;
//
//    @Value("${cloud.aws.s3.access-key}")
//    private String accessKey;
//
//    @Value("${cloud.aws.s3.secret-key}")
//    private String secretKey;
//
//
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        ReflectionTestUtils.setField(yandexCloudService, "bucketName", bucketName);
//        ReflectionTestUtils.setField(yandexCloudService, "endpointUrl", endpointUrl);
//        ReflectionTestUtils.setField(yandexCloudService, "accessKey", accessKey);
//        ReflectionTestUtils.setField(yandexCloudService, "secretKey", secretKey);
//
//        // Мокируем клиент S3
//        PutObjectResponse mockPutObjectResponse = mock(PutObjectResponse.class);
//        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class)))
//                .thenReturn(mockPutObjectResponse);
//    }
//
//    @Test
//    void testUploadFileSuccess() throws IOException, ExecutionException, InterruptedException {
//        // Подготовим файл для загрузки
//        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, world!".getBytes());
//
//        // Симуляция успешной загрузки файла
//        CompletableFuture<String> result = yandexCloudService.uploadFile(file);
//
//        // Проверяем, что результат правильный
//        assertNotNull(result);
//        assertEquals(endpointUrl + "/" + bucketName + "/test.txt", result.get());
//        verify(s3Client, times(1)).putObject(any(PutObjectRequest.class), any(RequestBody.class));
//    }
//
//    @Test
//    void testUploadFileFailure() throws IOException {
//        // Подготовим файл для загрузки
//        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, world!".getBytes());
//
//        // Симуляция ошибки при загрузке
//        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class)))
//                .thenThrow(new RuntimeException("Ошибка загрузки"));
//
//        // Проверяем, что метод выбрасывает исключение
//        Exception exception = assertThrows(RuntimeException.class, () -> {
//            yandexCloudService.uploadFile(file);
//        });
//
//        assertTrue(exception.getMessage().contains("Ошибка загрузки"));
//        verify(s3Client, times(1)).putObject(any(PutObjectRequest.class), any(RequestBody.class));
//    }
//}


