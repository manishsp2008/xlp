/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myplanet.utils;

import com.myplanet.utils.ReadExcel.PatternDesc;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author root
 */
public class ReadExcel {

    public static class PatternDesc {

        private static int total;
        private static Set data;

        public PatternDesc() {
        }

        public int getTotal() {
            return total;
        }

        public Set getData() {
            return data;
        }
    }

    public static Sheet read(String filename) {

        try {
            InputStream inp = new FileInputStream(filename);

            Workbook wb = WorkbookFactory.create(inp);

            Sheet sheet = wb.getSheetAt(0);

            return sheet;

        } catch (FileNotFoundException fe) {
            System.out.println("File not found.");
            return null;
        } catch (IOException ie) {
            System.out.println("IO Exception occured. " + ie.getLocalizedMessage());
            return null;
        } catch (InvalidFormatException ife) {
            System.out.println("Invalid format found. " + ife.getLocalizedMessage());
            return null;
        }
    }

    public static void getDescription(String filename) {

        //String filename = "/home/manish/Desktop/template.xls";
        Sheet sheet1 = read(filename);

        // Print description
        System.out.println("Filename - " + filename.split("/")[filename.split("/").length - 1]);
        System.out.println("Topics - " + (getTopics(sheet1).size()));
        System.out.println("Concepts - " + (getConcepts(sheet1).size()));
        System.out.println("Urls - " + (getUrls(sheet1).size()));
        PatternDesc pd = getPatterns(sheet1);
        System.out.println("Patterns - " + (pd.getTotal()) + " [Unique - " + (pd.getData().size()) + "]");
        System.out.println("Rootmap - " + getRootMap(sheet1).toString());
    }

    private static Set getTopics(Sheet sheet) {

        Set set = new HashSet();
        for (Row row : sheet) {
            if (row.getRowNum() > 0) {
                set.add(row.getCell(0).toString());
            }
        }
        return set;
    }

    private static Set getConcepts(Sheet sheet) {
        Set set = new HashSet();
        for (Row row : sheet) {
            if (row.getRowNum() > 0) {
                set.add(row.getCell(1).toString());
            }
        }
        return set;
    }

    private static Set getUrls(Sheet sheet) {
        Set set = new HashSet();
        for (Row row : sheet) {
            if (row.getRowNum() > 0) {
                set.add(row.getCell(2).toString());
            }
        }
        return set;
    }

    private static PatternDesc getPatterns(Sheet sheet) {
        Set set = new HashSet();
        int count = 0;
        for (Row row : sheet) {
            if (row.getRowNum() > 0) {
                set.add(row.getCell(3).toString());
                count++;
            }
        }
        PatternDesc p = new PatternDesc();
        PatternDesc.total = count;
        PatternDesc.data = set;
        return p;
    }

    private static Map getRootMap(Sheet sheet) {

        Map map = new HashMap();
        Set tempSet = new HashSet();

        for (Row row : sheet) {
            if (row.getRowNum() > 0) {
				tempSet.add(row.getCell(4).toString());
            }
        }

        int tempcount;
        for (Iterator it = tempSet.iterator(); it.hasNext();) {
            String root = (String) it.next();
            tempcount = 0;
            for (Row row : sheet) {
                if (row.getRowNum() > 0) {
                    if (root.equals(row.getCell(4).toString())) {
                        tempcount++;
                    }
                }
            }
            if (!root.equals("Root")) {
                map.put(root, tempcount);
            }
        }
        return map;
    }

    /**
    public static void main(String[] args) throws IOException {
        getDescription("/home/manish/Desktop/"+"template.xls");
    }
    **/
}
