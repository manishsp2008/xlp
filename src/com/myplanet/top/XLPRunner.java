/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myplanet.top;

import com.myplanet.utils.CreateSQL;
import com.myplanet.utils.ReadExcel;
import com.myplanet.utils.SyncTest;
import com.myplanet.utils.XLPValidator;
import java.util.List;

/**
 *
 * @author root
 */
public class XLPRunner {

    public static void main(String[] args) {

        run(args);
    }

    private static void run(String[] args) {

        String action = getAction(args);
        if (action != null) {
            performAction(action, args);
        }
    }

    private static String getAction(String[] args) {

        // valid actions
        String[] valid = {"-desc", "-val", "-sql", "-check", "-help"};
        boolean flag = false;

        // read argument 0, and validate it
        if (args[0] != null) {
            String action = args[0];
            for (int i = 0; i < valid.length; i++) {
                if (action.equals(valid[i])) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                printError("Incorrect arguments provided. Please refer help for more.");
                return null;
            } else {
                return action;
            }

        } else {
            printError("Incorrect arguments provided. Please refer help for more.");
            return null;
        }
    }

    private static void performAction(String action, String[] args) {

        if (action.equals("-desc")) {

            execDesc(args);

        } else if (action.equals("-val")) {

            execVal(args);

        } else if (action.equals("-sql")) {

            execSql(args);

        } else if (action.equals("-check")) {

            execCheck(args);

        } else if (action.equals("-help")) {

            execHelp(args);
        }
    }

    private static void execDesc(String[] args) {

        // validate arguments
        if (args.length > 2 || args.length < 2) {

            printError("Too many or too few arguments.Please refer help.");

        } else {

            // validate file
            validateXLFile(args[1]);
            // prepare full path
            //String curDir = System.getProperty("user.dir");
            //String fullPath = curDir + "/" + args[1];
            String fullPath = args[1];
            // execute logic
            ReadExcel.getDescription(fullPath);

        }
    }

    private static void execVal(String[] args) {

        if (args.length > 2) {

            printError("Too many arguments. Please refer help.");

        } else {

            // validate file
            validateXLFile(args[1]);
            // prepare full path
            // String curDir = System.getProperty("user.dir");
            // String fullPath = curDir + "/" + args[1];
            String fullPath = args[1];
            // Get errors returned by validation module
            List<String> list = XLPValidator.validate(fullPath);
            // print errors
            if (list.size() > 0) {
                System.out.println("Total rule violations found - " + list.size());
                for (String str : list) {
                    System.out.println(str);
                }
            } else {
                System.out.println("Great, Everything is fine and upto the rules.");
            }

        }
    }

    private static void execSql(String[] args) {

        if (args.length > 7 || args.length < 7) {

            printError("Too many or too few arguments provided. Please refer help.");

        } else {

            // validate file
            validateXLFile(args[6]);
            // prepare full path
            //String curDir = System.getProperty("user.dir");
            //String ipFile = curDir + "/" + args[6];
            String ipFile = args[6];
            String opFile = args[1];
            try {
                int topicid = Integer.parseInt(args[2]);
                int conceptid = Integer.parseInt(args[3]);
                int patternid = Integer.parseInt(args[4]);
                int stdid = Integer.parseInt(args[5]);
                // execute logic
                CreateSQL.create(opFile, topicid, conceptid, patternid, stdid, ipFile);

            } catch (Exception e) {
                printError("Incorrect values provided.");
            }
        }
    }

    private static void execCheck(String[] args) {

        if (args.length > 3 || args.length < 3) {

            printError("Too many arguments.Please refer help.");

        } else {

            // validate file
            validateXLFile(args[2]);
            // prepare full path
            //String curDir = System.getProperty("user.dir");
            //String fullPath = curDir + "/" + args[2];
            String fullPath = args[2];
            // execute logic
            SyncTest.test(args[1], fullPath);
        }
    }

    private static void execHelp(String[] args) {

        if (args.length > 1 || args.length < 1) {

            printError("Too many arguments.Please refer help.");

        } else {

            StringBuffer buffer = new StringBuffer();

            buffer = buffer.append("-- Help -- ").append("\n");
            buffer = buffer.append("1. Get basic description of document --> $xlp -desc file.xls").append("\n");
            buffer = buffer.append("2. Validate excel file --> $xlp -val file.xls").append("\n");
            buffer = buffer.append("3. Generate sql file from excel file --> $xlp -sql opfile.sql topicid conceptid patternid stdid file.xls").append("\n");
            buffer = buffer.append("4. Check patterns in folder --> $xlp -check folderpath file.xls").append("\n");
            buffer = buffer.append("5. Print help for using xlp --> $xlp -help").append("\n");

            System.out.println(buffer.toString());
        }

    }

    private static boolean validateXLFile(String filename) {

        // check filetype
        /**
         * if (filename.split(".")[1].equals("xls")) { return true; } else {
         * printError("Bad filename or invalid file provided."); return false; }
         * *
         */
        return true;
    }

    private static void printError(String errormsg) {
        System.out.println(errormsg);
    }
}
