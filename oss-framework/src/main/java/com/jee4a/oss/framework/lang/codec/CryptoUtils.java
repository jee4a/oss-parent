package com.jee4a.oss.framework.lang.codec;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 支持HMAC-SHA1消息签名 及 DES/AES对称加密的工具类.
 * 
 * 支持Hex与Base64两种编码方式.
 * 
 */
public class CryptoUtils {

	private static final String DES = "DES";
	private static final String AES = "AES";
	private static final String HMACSHA1 = "HmacSHA1";
	private static final String SHA1 = "SHA1";
	private static final String DESEDE = "DESede/ECB/PKCS5Padding";

	private static final int DEFAULT_HMACSHA1_KEYSIZE = 160;// RFC2401
	private static final int DEFAULT_AES_KEYSIZE = 128;

	// -- HMAC-SHA1 funciton --//
	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * 
	 * @param input
	 *            原始输入字符串
	 * @param keyBytes
	 *            HMAC-SHA1密钥
	 */
	public static byte[] hmacSha1(String input, byte[] keyBytes) {
		try {
			SecretKey secretKey = new SecretKeySpec(keyBytes, HMACSHA1);
			Mac mac = Mac.getInstance(HMACSHA1);
			mac.init(secretKey);
			return mac.doFinal(input.getBytes());
		} catch (GeneralSecurityException e) {
			throw convertRuntimeException(e);
		}
	}

	public static String hmacMd5(String input, byte[] keyBytes) {
		try {
			SecretKey secretKey = new SecretKeySpec(keyBytes, "HmacMD5");
			Mac mac = Mac.getInstance("HmacMD5");
			mac.init(secretKey);
			byte[] doFinal = mac.doFinal(input.getBytes());
			return EncodeUtils.hexEncode(doFinal);
		} catch (GeneralSecurityException e) {
			throw convertRuntimeException(e);
		}
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回Hex编码的结果,长度为40字符.
	 * 
	 * @see #hmacSha1(String, byte[])
	 */
	public static String hmacSha1ToHex(String input, byte[] keyBytes) {
		byte[] macResult = hmacSha1(input, keyBytes);
		return EncodeUtils.hexEncode(macResult);
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回Base64编码的结果.
	 * 
	 * @see #hmacSha1(String, byte[])
	 */
	public static String hmacSha1ToBase64(String input, byte[] keyBytes) {
		byte[] macResult = hmacSha1(input, keyBytes);
		return EncodeUtils.base64Encode(macResult);
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回Base64编码的URL安全的结果.
	 * 
	 * @see #hmacSha1(String, byte[])
	 */
	public static String hmacSha1ToBase64UrlSafe(String input, byte[] keyBytes) {
		byte[] macResult = hmacSha1(input, keyBytes);
		return EncodeUtils.base64UrlSafeEncode(macResult);
	}

	/**
	 * 校验Hex编码的HMAC-SHA1签名是否正确.
	 * 
	 * @param hexMac
	 *            Hex编码的签名
	 * @param input
	 *            原始输入字符串
	 * @param keyBytes
	 *            密钥
	 */
	public static boolean isHexMacValid(String hexMac, String input, byte[] keyBytes) {
		byte[] expected = EncodeUtils.hexDecode(hexMac);
		byte[] actual = hmacSha1(input, keyBytes);

		return Arrays.equals(expected, actual);
	}

	/**
	 * 校验Base64/Base64URLSafe编码的HMAC-SHA1签名是否正确.
	 * 
	 * @param base64Mac
	 *            Base64/Base64URLSafe编码的签名
	 * @param input
	 *            原始输入字符串
	 * @param keyBytes
	 *            密钥
	 */
	public static boolean isBase64MacValid(String base64Mac, String input, byte[] keyBytes) {
		byte[] expected = EncodeUtils.base64Decode(base64Mac);
		byte[] actual = hmacSha1(input, keyBytes);

		return Arrays.equals(expected, actual);
	}

	/**
	 * 生成HMAC-SHA1密钥,返回字节数组,长度为160位(20字节). HMAC-SHA1算法对密钥无特殊要求,
	 * RFC2401建议最少长度为160位(20字节).
	 */
	public static byte[] generateMacSha1Key() {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(HMACSHA1);
			keyGenerator.init(DEFAULT_HMACSHA1_KEYSIZE);
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey.getEncoded();
		} catch (GeneralSecurityException e) {
			throw convertRuntimeException(e);
		}
	}

	/**
	 * 生成HMAC-SHA1密钥, 返回Hex编码的结果,长度为40字符.
	 * 
	 * @see #generateMacSha1Key()
	 */
	public static String generateMacSha1HexKey() {
		return EncodeUtils.hexEncode(generateMacSha1Key());
	}

	// -- DES function --//
	/**
	 * 使用DES加密原始字符串, 返回Hex编码的结果.
	 * 
	 * @param input
	 *            原始输入字符串
	 * @param keyBytes
	 *            符合DES要求的密钥
	 */
	public static String desEncryptToHex(String input, byte[] keyBytes) {
		byte[] encryptResult = des(input.getBytes(), keyBytes, Cipher.ENCRYPT_MODE);
		return EncodeUtils.hexEncode(encryptResult);
	}

	/**
	 * 使用DES加密原始字符串, 返回Base64编码的结果.
	 * 
	 * @param input
	 *            原始输入字符串
	 * @param keyBytes
	 *            符合DES要求的密钥
	 */
	public static String desEncryptToBase64(String input, byte[] keyBytes) {
		byte[] encryptResult = des(input.getBytes(), keyBytes, Cipher.ENCRYPT_MODE);
		return EncodeUtils.base64Encode(encryptResult);
	}
	
	/**
     * 使用3DES加密原始字符串, 返回Base64编码的结果.
     * 
     * @param input
     *            原始输入字符串
     * @param keyBytes
     *            符合DES要求的密钥
     */
	public static String desedeEncryptToBase64(String input, byte[] keyBytes) {
        byte[] encryptResult = des(input.getBytes(), keyBytes, Cipher.ENCRYPT_MODE);
        return EncodeUtils.base64Encode(encryptResult);
    }

	/**
	 * 使用DES解密Hex编码的加密字符串, 返回原始字符串.
	 * 
	 * @param input
	 *            Hex编码的加密字符串
	 * @param keyBytes
	 *            符合DES要求的密钥
	 */
	public static String desDecryptFromHex(String input, byte[] keyBytes) {
		byte[] decryptResult = des(EncodeUtils.hexDecode(input), keyBytes, Cipher.DECRYPT_MODE);
		return new String(decryptResult);
	}

	/**
	 * 使用DES解密Base64编码的加密字符串, 返回原始字符串.
	 * 
	 * @param input
	 *            Base64编码的加密字符串
	 * @param keyBytes
	 *            符合DES要求的密钥
	 */
	public static String desDecryptFromBase64(String input, byte[] keyBytes) {
		byte[] decryptResult = des(EncodeUtils.base64Decode(input), keyBytes, Cipher.DECRYPT_MODE);
		return new String(decryptResult);
	}
	
	/**
     * 使用3DES解密Base64编码的加密字符串, 返回原始字符串.
     * 
     * @param input
     *            Base64编码的加密字符串
     * @param keyBytes
     *            符合DES要求的密钥
     */
	public static String desedeDecryptFromBase64(String input, byte[] keyBytes) {
        byte[] decryptResult = desede(EncodeUtils.base64Decode(input), keyBytes, Cipher.DECRYPT_MODE);
        return new String(decryptResult);
    }

	/**
	 * 使用DES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
	 * 
	 * @param inputBytes
	 *            原始字节数组
	 * @param keyBytes
	 *            符合DES要求的密钥
	 * @param mode
	 *            Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
	 */
	private static byte[] des(byte[] inputBytes, byte[] keyBytes, int mode) {
		try {
			DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

			Cipher cipher = Cipher.getInstance(DES);
			cipher.init(mode, secretKey);
			return cipher.doFinal(inputBytes);
		} catch (GeneralSecurityException e) {
			throw convertRuntimeException(e);
		}
	}
	
	private static byte[] desede(byte[] inputBytes, byte[] keyBytes, int mode) {
        try {
            Cipher cipher = Cipher.getInstance(DESEDE);
            SecretKeySpec key = new SecretKeySpec(keyBytes, "DESede");
            cipher.init(mode, key);
            return cipher.doFinal(inputBytes);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw convertRuntimeException(e);
        } catch (Exception e ) {
            e.printStackTrace();
        }
        return null;
    }  

	/**
	 * 生成符合DES要求的密钥, 长度为64位(8字节).
	 */
	public static byte[] generateDesKey() {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(DES);
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey.getEncoded();
		} catch (GeneralSecurityException e) {
			throw convertRuntimeException(e);
		}
	}

	/**
	 * 生成符合DES要求的Hex编码密钥, 长度为16字符.
	 */
	public static String generateDesHexKey() {
		return EncodeUtils.hexEncode(generateDesKey());
	}

	// -- AES funciton --//
	/**
	 * 使用AES加密原始字符串, 返回Hex编码的结果.
	 * 
	 * @param input
	 *            原始输入字符串
	 * @param keyBytes
	 *            符合AES要求的密钥
	 * @throws IOException
	 */
	public static String aesEncryptToHex(String input, String key) throws IOException {
		byte[] encryptResult = aes(input.getBytes(), hexToByte(key), Cipher.ENCRYPT_MODE);
		return EncodeUtils.hexEncode(encryptResult);
	}

	/**
	 * 使用AES加密原始字符串, 返回Base64编码的结果.
	 * 
	 * @param input
	 *            原始输入字符串
	 * @param key
	 *            符合AES要求的密钥
	 */
	public static String aesEncryptToBase64(String input, byte[] keyBytes) {
		byte[] encryptResult = aes(input.getBytes(), keyBytes, Cipher.ENCRYPT_MODE);
		return EncodeUtils.base64Encode(encryptResult);
	}

	/**
	 * 使用AES解密Hex编码的加密字符串, 返回原始字符串.
	 * 
	 * @param input
	 *            Hex编码的加密字符串
	 * @param key
	 *            符合AES要求的密钥
	 * @throws IOException
	 */
	public static String aesDecryptFromHex(String input, String key) throws IOException {
		byte[] decryptResult = aes(EncodeUtils.hexDecode(input), hexToByte(key), Cipher.DECRYPT_MODE);
		return new String(decryptResult);
	}

	/**
	 * 使用AES解密Base64编码的加密字符串, 返回原始字符串.
	 * 
	 * @param input
	 *            Base64编码的加密字符串
	 * @param keyBytes
	 *            符合AES要求的密钥
	 */
	public static String aesDecryptFromBase64(String input, byte[] keyBytes) {
		byte[] decryptResult = aes(EncodeUtils.base64Decode(input), keyBytes, Cipher.DECRYPT_MODE);
		return new String(decryptResult);
	}

	/**
	 * 使用AES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
	 * 
	 * @param inputBytes
	 *            原始字节数组
	 * @param keyBytes
	 *            符合AES要求的密钥
	 * @param mode
	 *            Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
	 */
	private static byte[] aes(byte[] inputBytes, byte[] keyBytes, int mode) {
		try {
			SecretKey secretKey = new SecretKeySpec(keyBytes, AES);
			byte[] ivbytes = new byte[16];
			IvParameterSpec iv = new IvParameterSpec(ivbytes);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(mode, secretKey, iv);
			return cipher.doFinal(inputBytes);
		} catch (GeneralSecurityException e) {
			throw convertRuntimeException(e);
		}
	}

	private static byte[] hexToByte(String s) throws IOException {
		int i = s.length() / 2;
		byte abyte0[] = new byte[i];
		int j = 0;
		if (s.length() % 2 != 0)
			throw new IOException("hexadecimal string with odd number of characters");
		for (int k = 0; k < i; k++) {
			char c = s.charAt(j++);
			int l = "0123456789abcdef0123456789ABCDEF".indexOf(c);
			if (l == -1)
				throw new IOException("hexadecimal string contains non hex character");
			int i1 = (l & 0xf) << 4;
			c = s.charAt(j++);
			l = "0123456789abcdef0123456789ABCDEF".indexOf(c);
			i1 += l & 0xf;
			abyte0[k] = (byte) i1;
		}

		return abyte0;
	}

	/**
	 * 生成AES密钥,返回字节数组,长度为128位(16字节).
	 */
	public static byte[] generateAesKey() {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
			keyGenerator.init(DEFAULT_AES_KEYSIZE);
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey.getEncoded();
		} catch (GeneralSecurityException e) {
			throw convertRuntimeException(e);
		}
	}

	/**
	 * 生成AES密钥, 返回Hex编码的结果,长度为32字符.
	 * 
	 * @see #generateMacSha1Key()
	 */
	public static String generateAesHexKey() {
		return EncodeUtils.hexEncode(generateAesKey());
	}

	private static IllegalStateException convertRuntimeException(GeneralSecurityException e) {
		return new IllegalStateException("Security exception", e);
	}

	public static String sha1Hex(String input) {
		String strDes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(input.getBytes());
			strDes = EncodeUtils.hexEncode(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		return strDes;
	}
	
	public static String sha1Base64(String input) {
        String strDes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(input.getBytes());
            strDes = EncodeUtils.base64Encode(md.digest()); // to base64
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

	public static void main(String[] args) throws IOException {

		System.out.println(hmacMd5("1", "11".getBytes()));
		String b = desedeEncryptToBase64("abceefe", "12345677".getBytes());
		System.out.println(b);
	}
}