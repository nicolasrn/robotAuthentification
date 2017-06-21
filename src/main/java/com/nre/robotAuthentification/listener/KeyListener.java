package com.nre.robotAuthentification.listener;

import static com.nre.robotAuthentification.I18nUtils.translateMessage;

import java.awt.AWTException;
import java.util.*;
import java.util.logging.*;

import org.jnativehook.*;
import org.jnativehook.keyboard.*;

import com.nre.robotAuthentification.crypt.CipherUtilSecret;
import com.nre.robotAuthentification.robot.RobotWriter;

/**
 * class used to listen keyboard input
 */
public class KeyListener implements NativeKeyListener {
  private Properties properties;
  private RobotWriter robot;
  private long tempsPause;

  static {
    Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
    logger.setLevel(Level.WARNING);

    logger.setUseParentHandlers(false);
  }

  public KeyListener(Properties properties, long tempsPause) throws AWTException {
    this.properties = properties;
    this.tempsPause = tempsPause;
    this.robot = new RobotWriter();
  }

  @Override
  public void nativeKeyPressed(NativeKeyEvent e) {
  }

  @Override
  public void nativeKeyReleased(NativeKeyEvent e) {
  }

  @Override
  public void nativeKeyTyped(NativeKeyEvent event) {
    Enumeration<Object> keys = properties.keys();
    while (keys.hasMoreElements()) {
      String key = String.valueOf(keys.nextElement());
      if (isSameCombinaison(convertEventToKey(event), key)) {
        String[] mots = properties.getProperty(key).split(";");
        mots[0] = CipherUtilSecret.decrypt(mots[0]);
        mots[1] = CipherUtilSecret.decrypt(mots[1]);
        try {
          Thread.sleep(tempsPause);
        } catch (InterruptedException e) {
          System.err.println(translateMessage("error_init_copy"));
        }
        robot.writeLoginAndPassword(mots);
      }
    }
  }

  /** @return combinaison of typed key ex: Ctrl+A */
  private static String convertEventToKey(NativeKeyEvent event) {
    return NativeKeyEvent.getModifiersText(event.getModifiers()) + "+" + event.getKeyChar();
  }

  /** @return if the event intercepted is equal to key */
  private static boolean isSameCombinaison(String nativeEventKey, String key) {
    List<String> elements = new ArrayList<>(Arrays.asList(nativeEventKey.split("\\+")));
    List<String> keyElements = new ArrayList<>(Arrays.asList(key.split("\\+")));
    int size = elements.size();
    elements.retainAll(keyElements);
    return elements.size() == size && keyElements.size() == size;
  }

  /** start listening of keyboard input */
  public void launch() {
    try {
      GlobalScreen.registerNativeHook();
    } catch (NativeHookException ex) {
      System.err.println("There was a problem registering the native hook.");
      System.err.println(ex.getMessage());

      System.exit(1);
    }

    GlobalScreen.addNativeKeyListener(this);
  }

}
