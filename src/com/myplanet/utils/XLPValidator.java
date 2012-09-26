/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myplanet.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author root
 */
public class XLPValidator {



    public static List validate() {

        Sheet sheet = ReadExcel.read("/home/manish/Desktop/template.xls");
        List<String> errorList = new ArrayList<>();

        for(Row row : sheet){

            // validate `Topic` column
            if(validateTopic(row.getCell(0).toString(),1,row.getRowNum()) != null){
                errorList.add(validateTopic(row.getCell(0).toString(),1,row.getRowNum()));
            }

            // validate `Concept' column
            if(validateConcept(row.getCell(1).toString(),2,row.getRowNum()) != null){
                errorList.add(validateConcept(row.getCell(1).toString(),2,row.getRowNum()));
            }

            // validate `Url' column
            if(validateUrl(row.getCell(2).toString(),3,row.getRowNum()) != null){
                errorList.add(validateUrl(row.getCell(2).toString(),3,row.getRowNum()));
            }

            // Validate `Pattern' column
            if(validatePattern(row.getCell(3).toString(),4,row.getRowNum()) != null){
                errorList.add(validatePattern(row.getCell(3).toString(),4,row.getRowNum()));
            }

            // validate `Root' column
            if(validateRoot(row.getCell(4).toString(),5,row.getRowNum())  != null){
                errorList.add(validateRoot(row.getCell(4).toString(),5,row.getRowNum()));
            }
        }

        return errorList;

    }

    private static String validateTopic(String val, int col, int row) {

        String errMsg = "Topic name must be in Sentence case.";
        // check if first char is uppercase or not
        if(!Character.isUpperCase(val.charAt(0))){
            return createErrorText(errMsg, col, row);
        }
        return null;
    }

    private static String validateConcept(String val, int col, int row){

        String errMsg = "Concept name must be in Sentence case.";
        // check if first char is uppercase or not
        if(!Character.isUpperCase(val.charAt(0))){
            return createErrorText(errMsg, col, row);
        }
        return null;
    }

    private static String validateUrl(String val, int col, int row) {

        String errMsg = "Url must be strictly formatted according to rules";
        Pattern p = Pattern.compile("[^-a-z0-9]");
        Matcher m = p.matcher(val);
        if(m.find() && row > 0){
            return createErrorText(errMsg, col, row);
        }
        return null;
    }

    private static String validatePattern(String val, int col, int row) {

        String errMsg = "Pattern name must be strictly formatted according to rules";
        Pattern p = Pattern.compile("[^-a-z0-9.]");
        Matcher m = p.matcher(val);
        if(m.find() && row > 0){
            return createErrorText(errMsg, col, row);
        }
        return null;
    }

    private static String validateRoot(String val, int col, int row) {

        String errMsg = "Pattern name must be strictly formatted according to rules";
        Pattern p = Pattern.compile("[^A-Z0-9]");
        Matcher m = p.matcher(val);
        if(m.find() && row > 0){
            return createErrorText(errMsg, col, row);
        }
        return null;
    }

    private static String createErrorText(String errorDesc, int col, int row){

        return "Error: " + errorDesc + " @ " + "C" + col + ": R" + row + ".";
    }

    public static void main(String[] args) {

        List<String> l = validate();
        for (String s : l) {
            System.out.println(s);
        }
    }

}
