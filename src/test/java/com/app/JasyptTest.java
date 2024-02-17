package com.app;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Target;

public class JasyptTest {
    @Test
    public void jasyptest() {
        String password = "1234";
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        //머신의 코어수와 동일하게 지정해주는 것이 좋음
        encryptor.setPoolSize(4);
        encryptor.setPassword(password);
        //암호화할때 사용하는 알고리즘
        encryptor.setAlgorithm("PBEWithMD5AndTripleDES");

        String content = "";//암호화 할 내용
        String encryptContent = encryptor.encrypt(content);// 암호화 한 내용
        String decrypt = encryptor.decrypt(encryptContent); // 복호화 한 내용

        System.out.println(decrypt);
    }
}
