/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myplanet.utils;

import java.io.File;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author root
 */
public class SyncTest {

    public static void test(String folderLoc, String filename) {

        // Read excel file
        //String filename = "/home/manish/Desktop/template.xls";
        Sheet sheet = ReadExcel.read(filename);
        File file;
        String filePath;
        // create list of filenames to be checked
        //List<String> patList = new ArrayList<>();
        for (Row row : sheet) {
            if(row.getRowNum()>0){
                //patList.add(row.getCell(3).toString()+".jsp");
                filePath = row.getCell(3).toString()+".jsp";
                //System.out.println(folderLoc + filePath);
                file = new File(folderLoc + filePath);
                if(!file.exists()){
                    System.out.println(file.getName() +" - NOT EXIST, " + "Row num - " + (row.getRowNum()+1));
                }
            }
        }
    }

    /**
    public static void main(String[] args) {
        test("/home/manish/workspace/praktick/kindergarten/patterns/","/home/manish/Desktop/template.xls");
    }
    **/

}
