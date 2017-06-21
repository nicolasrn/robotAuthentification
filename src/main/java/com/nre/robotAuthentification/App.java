package com.nre.robotAuthentification;

import static com.nre.robotAuthentification.I18nUtils.translateMessage;

import java.awt.AWTException;
import java.io.*;
import java.util.*;

import org.apache.commons.cli.*;

import com.nre.robotAuthentification.crypt.CipherUtilSecret;
import com.nre.robotAuthentification.listener.KeyListener;
import com.nre.robotAuthentification.robot.RobotWriter;

/**
 * main App
 */
public class App {
  private static final File KEYSTORE_FILE;
  private static final Properties PROPERTIES;

  static {
    KEYSTORE_FILE = new File("./keystore");
    PROPERTIES = new Properties();
    initProperties();
    initKeyStoreFile(KEYSTORE_FILE, "16BYTESSECRETKEY");
  }

  private static void initKeyStoreFile(File file, String defaultValue) {
    if (!file.exists()) {
      System.out.println(translateMessage("init_with_default_value"));
      try {
        if (!file.createNewFile()) {
          System.out.println(translateMessage("error_creation_file", new Object[] { "keystore" }));
        }
        try (FileWriter fw = new FileWriter(file)) {
          fw.write(defaultValue);
          fw.flush();
        }
      } catch (Exception e) {
        System.err.println(translateMessage("error_init_app"));
      }
    }
  }

  private static void initProperties() {
    File file = new File("./properties");
    if (!file.exists()) {
      System.out.println(translateMessage("init_with_default_value"));
      try {
        if (!file.createNewFile()) {
          System.out.println(translateMessage("error_creation_file", new Object[] { "properties" }));
        }
        PROPERTIES.put("CTRL+A", "MBnLpZDkaA5PIoRTiqXQsA==;MBnLpZDkaA5PIoRTiqXQsA==");
        PROPERTIES.store(new FileOutputStream(file), "default file\nShift, Ctrl, Meta, Alt");
      } catch (Exception e) {
        System.err.println(translateMessage("error_init_app"));
      }
    } else {
      try {
        PROPERTIES.load(new FileInputStream(file));
      } catch (IOException e) {
        System.err.println(translateMessage("error_on_creation_config_file"));
      }
    }
  }

  public static void main(String[] args) {
    CommandLineParser parser = new BasicParser();
    Options options = new Options();
    options.addOption(new Option("e", true, translateMessage("cmd_line_encrypt")));
    options.addOption(new Option("d", true, translateMessage("cmd_line_decrypt")));
    options.addOption(new Option("t", true, translateMessage("cmd_line_tmp_pause")));
    options.addOption(new Option("h", false, translateMessage("cmd_line_help")));
    options.addOption(new Option("z", false, translateMessage("cmd_line_keyboard_event")));

    try {
      CipherUtilSecret.loadPassPhrase(KEYSTORE_FILE);
      CommandLine cmd = parser.parse(options, args);
      long tempsPause = 0;

      if (cmd.hasOption("t")) {
        tempsPause = Long.parseLong(cmd.getOptionValue("t", "1000"));
      }

      if (cmd.hasOption("e")) {
        System.out.println(encrypt(cmd.getOptionValue("e")));
      } else if (cmd.hasOption("d")) {
        System.out.println(decrypt(cmd.getOptionValue("d")));
      } else if (cmd.hasOption("h")) {
        printHelp(options);
      } else if (cmd.hasOption("z")) {
        listenKeys(tempsPause);
      } else {
        startMain(cmd.getArgs(), tempsPause);
      }
    } catch (ParseException e) {
      printHelp(options);
    }
  }

  private static void listenKeys(long tempsPause) {
    try {
      new KeyListener(PROPERTIES, tempsPause).launch();
    } catch (AWTException e) {
      System.err.println(translateMessage("error_init_copy"));
    }
  }

  private static void printHelp(Options options) {
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp("App", options);
  }

  private static void startMain(String[] strings, long tempsPause) {
    strings[0] = decrypt(strings[0]);
    strings[1] = decrypt(strings[1]);

    try {
      Thread.sleep(tempsPause);

      RobotWriter robot = new RobotWriter();
      robot.writeLoginAndPassword(strings);
    } catch (Exception e) {
      System.err.println(translateMessage("error_init_copy"));
    }
  }

  private static String decrypt(String data) {
    return CipherUtilSecret.decrypt(data);
  }

  private static String encrypt(String data) {
    return CipherUtilSecret.encrypt(data);
  }

}
