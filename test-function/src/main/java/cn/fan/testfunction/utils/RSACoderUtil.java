package cn.fan.testfunction.utils;

/**
 * @Auther: xyd
 * @Date: 2019/7/24 10:40
 * @Description:
 */

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by humf.需要依赖 commons-codec 包
 */
public class RSACoderUtil {

    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    public static byte[] decryptBASE64(String key) {
        return Base64.decodeBase64(key);
    }

    public static String encryptBASE64(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       加密数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        // 解密由base64编码的私钥
        byte[] keyBytes = decryptBASE64(privateKey);
        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);
        return encryptBASE64(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
        // 解密由base64编码的公钥
        byte[] keyBytes = decryptBASE64(publicKey);
        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);
        // 验证签名是否正常
        return signature.verify(decryptBASE64(sign));
    }

    public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception{
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 解密<br>
     * 用私钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(String data, String key)
            throws Exception {
        return decryptByPrivateKey(decryptBASE64(data),key);
    }

    /**
     * 解密<br>
     * 用公钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(String data, String key)
            throws Exception {
        // 对公钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data.getBytes());
    }

    /**
     * 加密<br>
     * 用私钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 取得私钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Key> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 取得公钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Key> keyMap)
            throws Exception {
        Key key = keyMap.get(PUBLIC_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Key> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        Map<String, Key> keyMap = new HashMap(2);
        keyMap.put(PUBLIC_KEY, keyPair.getPublic());
        keyMap.put(PRIVATE_KEY, keyPair.getPrivate());
        return keyMap;
    }

    public static void main(String[] args) throws Exception {
        Map<String, Key> keyMap = initKey();
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAu2mZdimGkXj1xsbSBGnz4/Ko9T1PmCkoiWhjVsv6yhgiFiSrL/4w+UtEaqTuxJJll1ZWFGFfDwwZ1iInd3JvIwU1FF0kF8zyL1GuvJ9eyybV793h1U1VO3QifFiVr3B1D0qmQ8IcVjj/FaoOySUGYyS1+Wg9AoeioPTKZFWZyBMP7Fc1r/0Hr0l9UfXqBYaKKsUqT947l8XlYu3pE7Gytv7BikoNVfQcssVyPc8OGUwgju+IrKCsF5dlRcxNBnoXvr+pnWV1yBuCeyRKGeo6RIPjRd8ZDmJOnoFQG9Rnz5fRZaEFFEM/fsPgKcCa2w8WGv65NX8KhA1Fwymko9uBQQIDAQAB";
        String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC7aZl2KYaRePXGxtIEafPj8qj1PU+YKSiJaGNWy/rKGCIWJKsv/jD5S0RqpO7EkmWXVlYUYV8PDBnWIid3cm8jBTUUXSQXzPIvUa68n17LJtXv3eHVTVU7dCJ8WJWvcHUPSqZDwhxWOP8Vqg7JJQZjJLX5aD0Ch6Kg9MpkVZnIEw/sVzWv/QevSX1R9eoFhooqxSpP3juXxeVi7ekTsbK2/sGKSg1V9ByyxXI9zw4ZTCCO74isoKwXl2VFzE0Gehe+v6mdZXXIG4J7JEoZ6jpEg+NF3xkOYk6egVAb1GfPl9FloQUUQz9+w+ApwJrbDxYa/rk1fwqEDUXDKaSj24FBAgMBAAECggEAVxfj8T2rA7+quA19W//BD5WdXxp2+sdlxzVztMpmabR9Yrz3sP331GJeERFExKUkAQhmSGJXwq5k81Y78Q6FB0lMOYJvW4AqrELRaWbGrrIFicsbP/0INvDpdc/VAArK26hdS+/RK2Chanf1FxTVuw08ZANFFv/hW9tmkuoU5RzorcoGNGz38bNu/VASyPCYFudCO83wD+NukQoezJl2TQ+Uu5BluoN6siDg2VqcCQzOCm5fWWLEqxgKED17eELceE0Y0thgCdz8S3+Ow4xuwYOWz1WWSjDMUpWGMtvV/LBro7wdIxO9O+j74g7/VdVnsYaTVTNaHTABn22V9zOOgQKBgQD/mqfgv4w63vGjcmrQLMS0oWawG221iNHvZU8nycL2uSW+lqFPOZiu/GAnb3iDceKS0ekfuZZhhODYg7wP75Y7BXuE9eD9TPFYdgc1n5hsz7DK9F2xEh3TFvpn4MivVWmHH1XiAnRW05yOoR0pobGvTxd2f/67Qj8fsM7bGlHOjQKBgQC7s+gNeKbCvQP9xolI15XqL1WGN0MrGEVFxbvwxjLC7iFaAc38OGNR03uGbuOTTDi95FN1DjBvcOqwMY8H2cdgw/HkxcR2lFbUexhrzWgs9ftN9Hgle4B6J/T62qrQaXNHwGljw/+5zVQsHsV00I7Y2ix7TBKjtjARGJ1y0Zx6hQKBgQDUnN6b8nlA1wYEwT3cIAEXh1IVlxsw77hA+/JV1FxaQjQFTuKrNeutUrzg0LZcEDU5j2XtP8C5KYCmWR3r240JV0whky0tRf1TagohLrnpePFEQPRWQA2S8wAjvDTys4duIECeGRCQ0MDfW2EYqilU++M2bM2FElvTjozicAgwWQKBgQChZufpfY/X8HSYKbovGsJcZ2thR+/IBMy8XHVpauSHXgDrxpV7hnlRCUs0o/Go3WgFUWq6QwM/MJd9/n+BPrEE8jj0gWKYnKG1gEvVlwE3eCUqOUd+453sbKNpedJb9/EbB8cX9JfXC0qPzb343sWWjrSlKRr2D1lxufrZmoi1iQKBgAMl+bBiLulXTX7dvGQnIhfA+EbvVG2BFPHwZqY65riXEuVPiwS9kjW62Ph4j+fr0O0VDIFOGY5AvRIa+Nkj6O9R2Op6c9Ql7P9MWQ968U0xVp1+56sB2b72wr1PC3Uc7NfEh2ulFOXUnY19eODuXyt5+zZccCvO5/JuK7PVjsZA";

        System.out.println(keyMap);
        System.out.println("-----------------------------------");
        System.out.println("公钥：---"+publicKey);
        System.out.println("-----------------------------------");
        System.out.println("私钥：---"+privateKey);
        System.out.println("-----------------------------------");
        byte[] encryptByPrivateKey = encryptByPrivateKey("123456".getBytes(),privateKey);
        byte[] encryptByPublicKey = encryptByPublicKey("123456",publicKey);
        System.out.println(encryptBASE64(encryptByPrivateKey));
        System.out.println("-----------------------------------");
        System.out.println(encryptBASE64(encryptByPublicKey));
        System.out.println("-----------------------------------");
        String sign = sign(encryptByPrivateKey,privateKey);
        System.out.println(sign);
        System.out.println("-----------------------------------");
        boolean verify = verify(encryptByPrivateKey,publicKey,sign);
        System.out.println(verify);
        System.out.println("-----------------------------------");
        byte[] decryptByPublicKey = decryptByPublicKey(encryptByPrivateKey,publicKey);
        byte[] decryptByPrivateKey = decryptByPrivateKey(encryptByPublicKey,privateKey);
        System.out.println(new String(decryptByPublicKey));
        System.out.println("-----------------------------------");
        System.out.println(new String(decryptByPrivateKey));

    }
}