package com.nre.robotAuthentification.robot;

import java.awt.*;
import java.awt.event.KeyEvent;

import org.apache.commons.lang.StringUtils;

/**
 * Class calling the robot in order to write some word
 */
public class RobotWriter {

  private Robot robot;

  public RobotWriter() throws AWTException {
    robot = new Robot();
  }

  private static boolean isNumeric(char c) {
    return StringUtils.isNumeric(c + "");
  }

  private static boolean isUpper(char c) {
    return StringUtils.isAllUpperCase(c + "");
  }

  private static int getKeyCode(char c) {
    return isNumeric(c) ? c : KeyEvent.getExtendedKeyCodeForChar(c);
  }

  /** write a word respecting the number, uppercase and lowercase */
  public void writeWord(String word) {
    for (char caracter : word.toCharArray()) {
      int key = getKeyCode(caracter);
      if (isNumeric(caracter) || isUpper(caracter)) {
        robot.keyPress(KeyEvent.VK_SHIFT);
      }
      robot.keyPress(key);
      robot.keyRelease(key);
      if (isNumeric(caracter) || isUpper(caracter)) {
        robot.keyRelease(KeyEvent.VK_SHIFT);
      }
    }
  }

  /** write a tab */
  public void writeTab() {
    robot.keyPress(KeyEvent.VK_TAB);
    robot.keyRelease(KeyEvent.VK_TAB);
  }

  /** write a return chariot */
  public void writeCarriageReturn() {
    robot.keyPress(KeyEvent.VK_ENTER);
    robot.keyRelease(KeyEvent.VK_ENTER);
  }

  /** write login and password */
  public void writeLoginAndPassword(String[] data) {
    writeWord(data[0]);
    writeTab();
    writeWord(data[1]);
    writeCarriageReturn();
  }

}
