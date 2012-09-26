/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myplanet.inf;

/**
 *
 * @author root
 */
public class XLPattern {

    private String topicName;
    private String conceptName;
    private String conceptUrl;
    private String patName;
    private int topicID;
    private int quizID;
    private int patID;

    public XLPattern() {

    }

    public XLPattern(String topicName, String conceptName, String conceptUrl, String patName, int topicID, int quizID, int patID) {
        this.topicName = topicName;
        this.conceptName = conceptName;
        this.conceptUrl = conceptUrl;
        this.patName = patName;
        this.topicID = topicID;
        this.quizID = quizID;
        this.patID = patID;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getConceptName() {
        return conceptName;
    }

    public String getConceptUrl() {
        return conceptUrl;
    }

    public String getPatName() {
        return patName;
    }

    public int getTopicID() {
        return topicID;
    }

    public int getQuizID() {
        return quizID;
    }

    public int getPatID() {
        return patID;
    }

}
