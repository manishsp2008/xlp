/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myplanet.utils;

import com.myplanet.inf.XLConcept;
import com.myplanet.inf.XLPattern;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author root
 */
public class CreateSQL {

    /**
    public static void main(String[] args) {
        create("/home/manish/Desktop/template.sql", 10, 38, 165, 102, "/home/manish/Desktop/template.xls");
    }
    **/

    public static boolean create(String opfile, int lastTopicID, int lastConceptID, int lastPatternID, int stdID, String ipFile) {

        // Read input parameters
        //String opfile = opFile;
        int strTopicID = lastTopicID;
        int strConceptID = lastConceptID;
        int strPatternID = lastPatternID;
        int patType = 1;

        // Read excel sheet
        //Sheet sheet = ReadExcel.read("/home/manish/Desktop/template.xls");
        Sheet sheet = ReadExcel.read(ipFile);

        // create list of XLPattern
        List<XLPattern> patList = new ArrayList<>();
        List<XLConcept> concList = new ArrayList<>();
        Map<Integer, String> topicMap = new LinkedHashMap<>();
        XLPattern xlpObj;
        XLConcept xlcObj;

        String topicName, conceptName, conceptUrl, patName;
        int topicID = strTopicID;
        int conceptID = strConceptID;
        int patID = strPatternID;
        String prevTopicName = "", prevConceptName = "";

        for (Row row : sheet) {
            if (row.getRowNum() > 0) {
                topicName = row.getCell(0).toString();
                conceptName = row.getCell(1).toString();
                conceptUrl = row.getCell(2).toString();
                patName = row.getCell(3).toString();
                if (!topicName.equals(prevTopicName)) {
                    topicID++;
                    topicMap.put(topicID, topicName);
                }
                if (!conceptName.equals(prevConceptName)) {

                    conceptID++;
                    xlcObj = new XLConcept(conceptID, conceptName, conceptUrl, topicID);
                    concList.add(xlcObj);
                }
                xlpObj = new XLPattern(topicName, conceptName, conceptUrl, patName, topicID, conceptID, patID++);
                patList.add(xlpObj);
                prevTopicName = topicName;
                prevConceptName = conceptName;
            }
        }

        StringBuffer ptrnQuery = new StringBuffer(), concQuery = new StringBuffer(), topicQuery = new StringBuffer();
        ptrnQuery = ptrnQuery.append("# -- pattern query -- #").append("\n");
        concQuery = concQuery.append("# -- concept query -- #").append("\n");
        topicQuery = topicQuery.append("# -- topic query -- #").append("\n");

        // Process list to create queries

        createPatternQuery(patList, ptrnQuery, patType);
        createConceptQuery(concList, concQuery, stdID);
        createTopicQuery(topicMap, topicQuery);

        if (writeFile(opfile, topicQuery.toString(), concQuery.toString(), ptrnQuery.toString())) {
            System.out.println("Script created successfully.");
            return true;

        } else {
            System.out.println("Error occured while creating file.");
            return false;
        }
    }

    /** internal queries **/

    private static void createPatternQuery(List<XLPattern> patList, StringBuffer ptrnQuery, int patType) {
        for (XLPattern xlp : patList) {

            ptrnQuery = ptrnQuery.append("INSERT INTO `pattern` (`PAT_ID`,`PAT_NAME`, `PAT_QUIZ`, `PAT_TYPE`) VALUES (").
                    append(xlp.getPatID()).append(",'").append(xlp.getPatName()).append("',").append(xlp.getQuizID()).
                    append(",").append(patType).append(");").append("\n");
        }
    }

    private static void createConceptQuery(List<XLConcept> concList, StringBuffer concQuery, int stdID) {
        for (XLConcept xlc : concList) {
            concQuery = concQuery.append("INSERT INTO `quiz` (`QUIZ_ID`, `QUIZ_PREFIX`, `QUIZ_TITLE`, `QUIZ_URL`, `QUIZ_TIPS`, "
                    + "`QUIZ_TOPIC`, `QUIZ_STD`) VALUES (").append(xlc.getConceptID()).append(",'','")
                    .append(xlc.getConceptName()).append("','").append(xlc.getConceptUrl()).append("','coming soon',")
                    .append(xlc.getTopicID()).append(",").append(stdID).append(");").append("\n");
        }
    }

    private static void createTopicQuery(Map<Integer, String> topicMap, StringBuffer topicQuery ){
        for (Map.Entry<Integer, String> entry : topicMap.entrySet()) {
            topicQuery = topicQuery.append("INSERT INTO `topic` (`TOPIC_ID`, `TOPIC_NAME`) VALUES (").append(entry.getKey()).append(",'").append(entry.getValue()).append("');").append("\n");
        }
    }

    private static boolean writeFile(String fileName, String s1, String s2, String s3) {

        // write script to file
        File f = new File(fileName);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(CreateSQL.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error while creating file");
            }
        }
        FileWriter fw;
        try {
            fw = new FileWriter(f.getAbsoluteFile());
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(s1);
                bw.newLine();
                bw.write(s2);
                bw.newLine();
                bw.write(s3);
                bw.close();
                return true;
            }
        } catch (IOException ex) {
            Logger.getLogger(CreateSQL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
}
