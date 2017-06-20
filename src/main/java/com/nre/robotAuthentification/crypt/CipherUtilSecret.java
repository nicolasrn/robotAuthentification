package com.nre.robotAuthentification;

import java.nio.charset.Charset;
import java.util.*;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CipherUtilSecret {

  public static final String CIPHER_ALGORITHM = "AES";
  public static final String KEY_ALGORITHM = "AES";
  public static final byte[] SECRET_KEY = "16BYTESSECRETKEY".getBytes(Charset.forName("UTF8")); // exactly 16 bytes to not use JCE (Java Cryptography Extension)

  public static String decrypt(String encryptedInput) {
    try {
      Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(SECRET_KEY, KEY_ALGORITHM));
      return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedInput.getBytes())), Charset.forName("UTF8"));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static String encrypt(String str) {
    try {
      Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
      cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(SECRET_KEY, KEY_ALGORITHM));
      return Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes(Charset.forName("UTF8"))));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}