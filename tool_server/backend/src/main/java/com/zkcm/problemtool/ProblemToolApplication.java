package com.zkcm.problemtool;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
public class ProblemToolApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ProblemToolApplication.class)
                .run(args);
    }
}
