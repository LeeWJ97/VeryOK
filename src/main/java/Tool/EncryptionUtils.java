package Tool;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class EncryptionUtils {
    private EncryptionUtils() {
    }

    // MD5加密
    public static String md5Encrypt(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] inputBytes = input.getBytes();
        byte[] hashBytes = md.digest(inputBytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private static void addBouncyCastleProvider() {
        Security.addProvider(new BouncyCastleProvider());
    }

    // DES加密
    public static byte[] desEncrypt(byte[] input, byte[] key, String padding) throws Exception {
        // 填充方式可选：
        // - NoPadding: 不进行任何填充，要求明文长度必须是8字节的倍数
        // - DES/ECB/PKCS5Padding: 采用PKCS#5标准填充方式，适用于任意长度的明文
        // 示例：desEncrypt(data, key, "DES/ECB/PKCS5Padding")
        addBouncyCastleProvider();
        Cipher cipher = Cipher.getInstance(padding);
        SecretKeySpec secretKey = new SecretKeySpec(key, "DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(input);
    }

    // DES解密
    public static byte[] desDecrypt(byte[] input, byte[] key, String padding) throws Exception {
        // 填充方式可选：
        // - NoPadding: 不进行任何填充，要求明文长度必须是8字节的倍数
        // - DES/ECB/PKCS5Padding: 采用PKCS#5标准填充方式，适用于任意长度的明文
        // 示例：desDecrypt(encryptedData, key, "DES/ECB/PKCS5Padding")
        addBouncyCastleProvider();
        Cipher cipher = Cipher.getInstance(padding);
        SecretKeySpec secretKey = new SecretKeySpec(key, "DES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(input);
    }

    // AES加密
    public static byte[] aesEncrypt(byte[] input, byte[] key, String padding) throws Exception {
        // 填充方式可选：
        // - NoPadding: 不进行任何填充，要求明文长度必须是16字节的倍数
        // - AES/ECB/PKCS5Padding: 采用PKCS#5标准填充方式，适用于任意长度的明文
        // 示例：aesEncrypt(data, key, "AES/ECB/PKCS5Padding")
        Cipher cipher = Cipher.getInstance(padding);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(input);
    }

    // AES解密
    public static byte[] aesDecrypt(byte[] input, byte[] key, String padding) throws Exception {
        // 填充方式可选：
        // - NoPadding: 不进行任何填充，要求明文长度必须是16字节的倍数
        // - AES/ECB/PKCS5Padding: 采用PKCS#5标准填充方式，适用于任意长度的明文
        // 示例：aesDecrypt(encryptedData, key, "AES/ECB/PKCS5Padding")
        Cipher cipher = Cipher.getInstance(padding);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(input);
    }

    // RSA加密
    public static byte[] rsaEncrypt(byte[] input, PublicKey publicKey, String padding) throws Exception {
        // 填充方式可选：
        // - NoPadding: 不进行任何填充，要求明文长度必须小于密钥长度减去11字节
        // - RSA/ECB/PKCS1Padding: 采用PKCS#1标准填充方式，适用于任意长度的明文
        // 示例：rsaEncrypt(data, publicKey, "RSA/ECB/PKCS1Padding")
        Cipher cipher = Cipher.getInstance(padding);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(input);
    }

    // RSA解密
    public static byte[] rsaDecrypt(byte[] input, PrivateKey privateKey, String padding) throws Exception {
        // 填充方式可选：
        // - NoPadding: 不进行任何填充，要求明文长度必须小于密钥长度减去11字节
        // - RSA/ECB/PKCS1Padding: 采用PKCS#1标准填充方式，适用于任意长度的明文
        // 示例：rsaDecrypt(encryptedData, privateKey, "RSA/ECB/PKCS1Padding")
        Cipher cipher = Cipher.getInstance( padding);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(input);
    }

    // 生成RSA密钥对
    public static KeyPair generateRSAKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048); // 设置密钥长度为2048位
        return keyPairGenerator.generateKeyPair();
    }

    // 生成AES密钥
    public static byte[] generateAESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256); // 设置密钥长度为256位
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    // 生成DES密钥
    public static byte[] generateDESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(56); // 设置密钥长度为56位
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

}
