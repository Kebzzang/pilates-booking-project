package com.keb.club_pila.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.keb.club_pila.AmazonEnv;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class AmazonConfig {

    private final AmazonEnv amazonEnv;
    @Bean
    public AmazonS3 s3() {
        System.out.println("Bean Injection for Amazon S3");
        AWSCredentials awsCredentials = new BasicAWSCredentials(
                amazonEnv.getAccessKey(),
                amazonEnv.getSecretKey()
        );

        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
