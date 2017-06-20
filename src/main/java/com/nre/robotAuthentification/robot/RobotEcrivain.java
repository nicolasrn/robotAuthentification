package com.nre.robotAuthentification.robot;

import java.awt.*;
import java.awt.event.KeyEvent;

import org.apache.commons.lang.StringUtils;

/**
 * Classe fournissant des méthodes d'écriture extrement simplifié
 */
public class RobotEcrivain {

  private Robot robot;

  public RobotEcrivain() throws AWTException {
    robot = new Robot();
  }

  private boolean isNumeric(char c) {
    return StringUtils.isNumeric(c + "");
  }

  private boolean isUpper(char c) {
    return StringUtils.isAllUpperCase(c + "");
  }

  private int getKeyCode(char c) {
    return isNumeric(c) ? c : KeyEvent.getExtendedKeyCodeForChar(c);
  }

  /**
   * permet d'écrire un mot en respectant les chiffres, majuscules, minuscules
   */
  public void ecrireMot(String mot) {
    for (char c : mot.toCharArray()) {
      int key = getKeyCode(c);
      if (isNumeric(c) || isUpper(c)) {
        robot.keyPress(KeyEvent.VK_SHIFT);
      }
      robot.keyPress(key);
      robot.keyRelease(key);
      if (isNumeric(c) || isUpper(c)) {
        robot.keyRelease(KeyEvent.VK_SHIFT);
      }
    }
  }

  /**
   * permet d'écrire une tabulation
   */
  public void simulerTabulation() {
    robot.keyPress(KeyEvent.VK_TAB);
    robot.keyRelease(KeyEvent.VK_TAB);
  }

  /**
   * permet d'écrire un retour chariot
   */
  public void simulerEntre() {
    robot.keyPress(KeyEvent.VK_ENTER);
    robot.keyRelease(KeyEvent.VK_ENTER);
  }

}
