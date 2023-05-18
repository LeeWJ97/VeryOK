package Tool;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class EncryptionUtilsTest {

    @Test
    public void testMd5Encrypt() throws Exception {
        String input = "MD5 Very OK !!!";
        String expectedHash = "aa0bb808e9bc049a6eb868e1f6ae96e1"; // MD5 hash

        String hash = EncryptionUtils.md5Encrypt(input);
        System.out.println(hash);
        Assertions.assertEquals(expectedHash, hash);
    }

    @Test
    public void testDesEncryptAndDecrypt() throws Exception {
        String input = "DES Very OK !!!";
        byte[] key = EncryptionUtils.generateDESKey();
        String padding = "DES/ECB/PKCS5Padding";


        byte[] encryptedData = EncryptionUtils.desEncrypt(input.getBytes(), key, padding);
        byte[] decryptedData = EncryptionUtils.desDecrypt(encryptedData, key, padding);

        String decryptedString = new String(decryptedData);
        System.out.println(decryptedString);
        Assertions.assertEquals(input, decryptedString);
    }

    @Test
    public void testAesEncryptAndDecrypt() throws Exception {
        String input = "AES Very OK !!!";
        byte[] key = EncryptionUtils.generateAESKey();
        String padding = "AES/ECB/PKCS5Padding";

        byte[] encryptedData = EncryptionUtils.aesEncrypt(input.getBytes(), key, padding);
        byte[] decryptedData = EncryptionUtils.aesDecrypt(encryptedData, key, padding);

        String decryptedString = new String(decryptedData);
        System.out.println(decryptedString);
        Assertions.assertEquals(input, decryptedString);
    }

    @Test
    public void testRsaEncryptAndDecrypt() throws Exception {
        String input = "RSA Very OK !!!";
        String padding = "RSA/ECB/PKCS1Padding";

        KeyPair keyPair = EncryptionUtils.generateRSAKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        byte[] encryptedData = EncryptionUtils.rsaEncrypt(input.getBytes(), publicKey, padding);
        byte[] decryptedData = EncryptionUtils.rsaDecrypt(encryptedData, privateKey, padding);

        String decryptedString = new String(decryptedData);
        System.out.println(decryptedString);
        Assertions.assertEquals(input, decryptedString);
    }

    @Test
    public void testGenerateRSAKeyPair() throws Exception {
        KeyPair keyPair = EncryptionUtils.generateRSAKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // Ensure the keys are not null
        Assertions.assertNotNull(publicKey);
        Assertions.assertNotNull(privateKey);

        // Encode the keys to Base64 for visualization
        //String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        //String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        String publicKeyBase64 = EncryptionUtils.encodeToString(publicKey.getEncoded());
        String privateKeyBase64 = EncryptionUtils.encodeToString(privateKey.getEncoded());



        System.out.println("Public Key: " + publicKeyBase64);
        System.out.println("Private Key: " + privateKeyBase64);
    }

    @Test
    void testGenerateAESKey() throws NoSuchAlgorithmException {
        byte[] key = EncryptionUtils.generateAESKey();

        System.out.print("AES Key: ");
        for (byte k:
             key) {
            System.out.print(k + " ");
        }
        System.out.println();

        Assertions.assertNotNull(key);
    }

    @Test
    void testGenerateDESKey() throws NoSuchAlgorithmException {
        byte[] key = EncryptionUtils.generateDESKey();

        System.out.print("DES Key: ");
        for (byte k:
                key) {
            System.out.print(k + " ");
        }
        System.out.println();

        Assertions.assertNotNull(key);
    }

    @Test
    public void testEncodeToString() {
        String plainText = "Hello, World!";
        String expectedBase64 = "SGVsbG8sIFdvcmxkIQ==";
        String encodedString = EncryptionUtils.encodeToString(plainText);
        Assertions.assertEquals(expectedBase64, encodedString);
    }

    @Test
    public void testDecodeToString() {
        String base64Text = "SGVsbG8sIFdvcmxkIQ==";
        String expectedPlainText = "Hello, World!";
        String decodedString = EncryptionUtils.decodeToString(base64Text);
        System.out.println(decodedString);
        Assertions.assertEquals(expectedPlainText, decodedString);
    }

    @Test
    public void testEncodeToBytes() {
        byte[] bytes = {0x48, 0x65, 0x6C, 0x6C, 0x6F, 0x2C, 0x20, 0x57, 0x6F, 0x72, 0x6C, 0x64, 0x21};
        String expectedBase64 = "SGVsbG8sIFdvcmxkIQ==";
        String encodedString = EncryptionUtils.encodeToString(bytes);
        Assertions.assertEquals(expectedBase64, encodedString);
    }

    @Test
    public void testDecodeToBytes() {
        String base64Text = "SGVsbG8sIFdvcmxkIQ==";
        byte[] expectedBytes = {0x48, 0x65, 0x6C, 0x6C, 0x6F, 0x2C, 0x20, 0x57, 0x6F, 0x72, 0x6C, 0x64, 0x21};
        byte[] decodedBytes = EncryptionUtils.decodeToBytes(base64Text);
        Assertions.assertArrayEquals(expectedBytes, decodedBytes);
    }
}
