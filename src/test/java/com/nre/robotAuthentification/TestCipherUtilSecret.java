package com.nre.robotAuthentification;

import org.hamcrest.core.Is;
import org.junit.*;

public class TestCipherUtilSecret {
  @Test
  public void encryption() {
    CipherUtilSecret cipherUtil = new CipherUtilSecret();
    String avantCryptage = "1,2,3 allons dans les bois : " + String.valueOf("123456");
    // Encryption
    String encryptedString = cipherUtil.encrypt(avantCryptage);
    // Before Decryption
    String apresCryptage = cipherUtil.decrypt(encryptedString);

    Assert.assertThat(apresCryptage, Is.is(avantCryptage));
  }
  
  @Test
  public void decryption() {
    CipherUtilSecret cipherUtil = new CipherUtilSecret();
    String avantDecryptage = "sN4iEQe6ZRnr/59Jtx2+MLaQtzLs6rgV1O/7hig1VERZzkP9FexNxkgWeJLAm/8f";
    String apresDecryptage = cipherUtil.decrypt(avantDecryptage);

    Assert.assertThat("1,2,3 allons dans les bois : " + String.valueOf("123456"), Is.is(apresDecryptage));
  }
}
