package com.zkcm.problemtool.system.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.minio")
public class MinIOConfig {
    private String url;
    private String accessKey;
    private String secretKey;


    @Bean
    public MinioClient minioClient() throws Exception{
        return new MinioClient(url,accessKey,secretKey);
    }

}
