package com.nre.robotAuthentification;

import java.awt.*;
import java.awt.event.KeyEvent;

import org.apache.commons.cli.*;
import org.apache.commons.lang.StringUtils;

public class App {
  public static void main(String[] args) {
    CommandLineParser parser = new BasicParser();
    Options options = new Options();
    options.addOption(new Option("e", true, "encryptage"));
    options.addOption(new Option("d", true, "décryptage"));
    options.addOption(new Option("t", true, "temps de pause"));
    options.addOption(new Option("h", false, "aide"));
    
    try {
      CommandLine cmd = parser.parse(options, args);
      if (cmd.hasOption("e")) {
        System.out.println(encrypt(cmd.getOptionValue("e")));
      } else if (cmd.hasOption("d")) {
        System.out.println(decrypt(cmd.getOptionValue("d")));
      } else if (cmd.hasOption("h")) {
        printHelp(options);
      } else {
        long tempsPause = 0;
        if (cmd.hasOption("t")) {
          tempsPause = Long.parseLong(cmd.getOptionValue("t", "1000"));
        }
        startMain(cmd.getArgs(), tempsPause);
      }
    } catch (ParseException e) {
      printHelp(options);
    }
  }

  private static void printHelp(Options options) {
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp("App", options);
  }

  private static void startMain(String[] strings, long tempsPause) {
    String login = decrypt(strings[0]);
    String password = decrypt(strings[1]);

    try {
      Thread.sleep(tempsPause);

      App app = new App();
      app.ecrireMot(login);
      app.simulerTabulation();
      app.ecrireMot(password);
      app.simulerEntre();
    } catch (AWTException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static String decrypt(String data) {
    return CipherUtilSecret.decrypt(data);
  }

  private static String encrypt(String data) {
    return CipherUtilSecret.encrypt(data);
  }

  private Robot robot;

  private App() throws AWTException {
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

  private void ecrireMot(String mot) {
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

  private void simulerTabulation() {
    robot.keyPress(KeyEvent.VK_TAB);
    robot.keyRelease(KeyEvent.VK_TAB);
  }

  private void simulerEntre() {
    robot.keyPress(KeyEvent.VK_ENTER);
    robot.keyRelease(KeyEvent.VK_ENTER);
  }

}
