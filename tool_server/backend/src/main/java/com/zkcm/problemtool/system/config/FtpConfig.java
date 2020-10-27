package com.zkcm.problemtool.system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.ftp")
public class FtpConfig {
    private String host;
    private int port;
    private String user;
    private String password;
    private String bookUploadPath;
    private String pendingUploadPath;
    private String alreadyUploadPath;
    private String xmlPath;
    private String imagePath;
    private String coverPath;
}
