package com.nre.robotAuthentification;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class I18nUtils {
  private static ResourceBundle bundle;
  
  static {
    bundle = ResourceBundle.getBundle("bundleApplication");
  }

  public static String translateMessage(String keyMessage) {
    return bundle.getString(keyMessage);
  }

  public static String translateMessage(String keyMessage, Object[] datas) {
    MessageFormat formatter = new MessageFormat("");
    formatter.setLocale(bundle.getLocale());
    formatter.applyPattern(bundle.getString(keyMessage));
    return formatter.format(datas);
  }
}
