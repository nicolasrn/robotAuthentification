package com.nre.robotAuthentification;

import static org.hamcrest.core.Is.is;

import org.junit.*;

import com.nre.robotAuthentification.crypt.CipherUtilSecret;

public class TestCipherUtilSecret {
  @BeforeClass
  public static void before() {
    CipherUtilSecret.loadPassPhraseFromJar("./keystore");
  }

  @Test
  public void encryption() {
    String avantCryptage = "1,2,3 allons dans les bois : " + String.valueOf("123456");
    String encryptedString = CipherUtilSecret.encrypt(avantCryptage);
    String apresCryptage = CipherUtilSecret.decrypt(encryptedString);

    Assert.assertThat(apresCryptage, is(avantCryptage));
  }

  @Test
  public void decryption() {
    String avantDecryptage = "sN4iEQe6ZRnr/59Jtx2+MLaQtzLs6rgV1O/7hig1VERZzkP9FexNxkgWeJLAm/8f";
    String apresDecryptage = CipherUtilSecret.decrypt(avantDecryptage);

    Assert.assertThat("1,2,3 allons dans les bois : " + String.valueOf("123456"), is(apresDecryptage));
  }
}
