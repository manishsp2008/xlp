/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myplanet.inf;

/**
 *
 * @author root
 */
public class XLConcept {

    private int conceptID;
    private String conceptName;
    private String conceptUrl;
    private int topicID;

    public XLConcept() {
    }

    public XLConcept(int conceptID, String conceptName, String conceptUrl, int topicID) {
        this.conceptID = conceptID;
        this.conceptName = conceptName;
        this.conceptUrl = conceptUrl;
        this.topicID = topicID;
    }

    public int getConceptID() {
        return conceptID;
    }

    public String getConceptName() {
        return conceptName;
    }

    public int getTopicID() {
        return topicID;
    }

    public String getConceptUrl() {
        return conceptUrl;
    }

}
