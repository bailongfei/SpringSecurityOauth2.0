package com.itheima.security.distributed.uaa.util;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

/**
 * @ClassName:   JasypUtil
 * @Description: Jasyp加解密工具类
 * @Author:      Rambo
 * @CreateDate:  2020/7/13 10:12
 * @UpdateUser:  Rambo
 * @UpdateDate:  2020/7/13 10:12
 * @Version:     1.0.0
 */
public class JasyptUtil {

    private static final String PBEWITHMD5ANDDES = "PBEWithMD5AndDES";

    private static final String PBEWITHHMACSHA512ANDAES_256 = "PBEWITHHMACSHA512ANDAES_256";

    /**
     * @Description: Jasyp加密（PBEWithMD5AndDES）
     * @Author:      Rambo
     * @CreateDate:  2020/7/13 10:24
     * @UpdateUser:  Rambo
     * @UpdateDate:  2020/7/13 10:24
     * @param		 plainText      待加密的原文
     * @param		 factor         加密秘钥
     * @return       java.lang.String
     * @Version:     1.0.0
     */
    public static String encryptWithMD5(String plainText, String factor) {
        // 1. 创建加解密工具实例
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        // 2. 加解密配置
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        config.setAlgorithm(PBEWITHMD5ANDDES);
        config.setPassword(factor);
        encryptor.setConfig(config);
        // 3. 加密
        return encryptor.encrypt(plainText);
    }

    /**
     * @Description: Jaspy解密（PBEWithMD5AndDES）
     * @Author:      Rambo
     * @CreateDate:  2020/7/13 10:28
     * @UpdateUser:  Rambo
     * @UpdateDate:  2020/7/13 10:28
     * @param		 encryptedText      待解密密文
     * @param		 factor             解密秘钥
     * @return       java.lang.String
     * @Version:     1.0.0
     */
    public static String decryptWithMD5(String encryptedText, String factor) {
        // 1. 创建加解密工具实例
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        // 2. 加解密配置
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        config.setAlgorithm(PBEWITHMD5ANDDES);
        config.setPassword(factor);
        encryptor.setConfig(config);
        // 3. 解密
        return encryptor.decrypt(encryptedText);
    }

    /**
     * @Description: Jasyp 加密（PBEWITHHMACSHA512ANDAES_256）
     * @Author:      Rambo
     * @CreateDate:  2020/7/25 14:34
     * @UpdateUser:  Rambo
     * @UpdateDate:  2020/7/25 14:34
     * @param		 plainText  待加密的原文
     * @param		 factor     加密秘钥
     * @return       java.lang.String
     * @Version:     1.0.0
     */
    public static String encryptWithSHA512(String plainText, String factor) {
        // 1. 创建加解密工具实例
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        // 2. 加解密配置
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(factor);
        config.setAlgorithm(PBEWITHHMACSHA512ANDAES_256);
        // 为减少配置文件的书写，以下都是 Jasyp 3.x 版本，配置文件默认配置
        config.setKeyObtentionIterations( "1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        // 3. 加密
        return encryptor.encrypt(plainText);
    }

    /**
     * @Description: Jaspy解密（PBEWITHHMACSHA512ANDAES_256）
     * @Author:      Rambo
     * @CreateDate:  2020/7/25 14:40
     * @UpdateUser:  Rambo
     * @UpdateDate:  2020/7/25 14:40
     * @param		 encryptedText  待解密密文
     * @param		 factor         解密秘钥
     * @return       java.lang.String
     * @Version:     1.0.0
     */
    public static String decryptWithSHA512(String encryptedText, String factor) {
        // 1. 创建加解密工具实例
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        // 2. 加解密配置
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(factor);
        config.setAlgorithm(PBEWITHHMACSHA512ANDAES_256);
        // 为减少配置文件的书写，以下都是 Jasyp 3.x 版本，配置文件默认配置
        config.setKeyObtentionIterations( "1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        // 3. 解密
        return encryptor.decrypt(encryptedText);
    }
    public static void main(String[] args) {
        String factor = "jasyptSalt@2022"; //加密秘钥
        String plainText = "root"; //待加密的原文
        String encryptWithMD5Str = encryptWithMD5(plainText, factor);
        String decryptWithMD5Str = decryptWithMD5(encryptWithMD5Str, factor);

        String encryptWithSHA512Str = encryptWithSHA512(plainText, factor);
        String decryptWithSHA512Str = decryptWithSHA512(encryptWithSHA512Str, factor);
        System.out.println("采用MD5加密前原文密文：" + encryptWithMD5Str);
        System.out.println("采用MD5解密后密文原文:" + decryptWithMD5Str);
        System.out.println();
        System.out.println("采用SHA512加密前原文密文：" + encryptWithSHA512Str);
        System.out.println("采用SHA512解密后密文原文:" + decryptWithSHA512Str);
    }
    //jdk的无限强度管辖权政策权限不够（JCE），去下载一个JCE替换一下jre下的权限包就好。
    //6,7，8版本下载地址：https://www.oracle.com/technetwork/java/javase/downloads/jce-all-download-5170447.html

}


/*public class JasyptUtil {

    *//**
     * Jasypt生成加密结果
     * @param password 配置文件中设定的加密盐值
     * @param value 加密值
     * @return
     *//*
    public static String encyptPwd(String password,String value){
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(cryptor(password));
        String result = encryptor.encrypt(value);
        return result;
    }

    *//**
     * 解密
     * @param password 配置文件中设定的加密盐值
     * @param value 解密密文
     * @return
     *//*
    public static String decyptPwd(String password,String value){
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(cryptor(password));
        String result = encryptor.decrypt(value);
        return result;
    }

    public static SimpleStringPBEConfig cryptor(String password){
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        return config;
    }


    public static void main(String[] args) {
        // 加密
        String encPwd = encyptPwd("jasypt", "123456");
        // 解密
        String decPwd = decyptPwd("jasypt", encPwd);
        System.out.println(encPwd);
        System.out.println(decPwd);
    }
}*/

