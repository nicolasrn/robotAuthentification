package com.nre.robotAuthentification.crypt;

import static com.nre.robotAuthentification.I18nUtils.translateMessage;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * utils used to crypt and decrypt some datas<br>
 * the secret key is stored un an external file
 */
public class CipherUtilSecret {

  public static final String CIPHER_ALGORITHM = "AES";
  public static final String KEY_ALGORITHM = "AES";
  public static byte[] SECRET_KEY;

  /** load the passphrase from a file. truncate the phrase to 16 bytes length */
  public static void loadPassPhrase(File file) {
    String phrase = null;
    try (Scanner sc = new Scanner(file)) {
      phrase = sc.nextLine();
      phrase = String.format("%16s", phrase);
    } catch (FileNotFoundException e) {
      System.err.println(translateMessage("error_init_file_not_exist"));
    }

    // exactement 16 bytes
    SECRET_KEY = phrase.getBytes(Charset.forName("UTF8"));
  }

  /** load the passphrase from a internal jar path. truncate the phrase to 16 bytes length */
  public static void loadPassPhraseFromJar(String path) {
    String phrase = "";

    try (Scanner sc = new Scanner(CipherUtilSecret.class.getClassLoader().getResourceAsStream(path))) {
      phrase = sc.nextLine();
    }
    phrase = String.format("%16s", phrase);

    // exactement 16 bytes
    SECRET_KEY = phrase.getBytes(Charset.forName("UTF8"));
  }

  /** @return a decrypted input */
  public static String decrypt(String encryptedInput) {
    try {
      Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(SECRET_KEY, KEY_ALGORITHM));
      return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedInput.getBytes())), Charset.forName("UTF8"));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /** @return a encrypted input */
  public static String encrypt(String entry) {
    try {
      Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
      cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(SECRET_KEY, KEY_ALGORITHM));
      return Base64.getEncoder().encodeToString(cipher.doFinal(entry.getBytes(Charset.forName("UTF8"))));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}