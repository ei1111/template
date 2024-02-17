package com.app.global.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//yml 암호화를 사용하기 위해서 사용
@EnableEncryptableProperties
public class JasyptConfig {
    @Value("${jasypt.password}")
    private String password;

    @Bean
    public PooledPBEStringEncryptor jasyptStringEncryption() {
        //멀티 코어 시스템에서 해독을 병렬처리하는 encrytor
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        //머신의 코어수와 동일하게 지정해주는 것이 좋음
        encryptor.setPoolSize(4);
        encryptor.setPassword(password);
        //암호화할때 사용하는 알고리즘
        encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
        return encryptor;
    }
}
