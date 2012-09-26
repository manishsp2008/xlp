/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myplanet.inf;

/**
 *
 * @author root
 */
public interface XLPInterface {

    /*
     * Get description of provided spreadsheet.
     */
    public String getXLPDescription();

    /*
     * Validate every column in spreadsheet
     */
    public String validateXL();

    /*
     * Check syncronization between file and folder location provided
     */
    public String checkFolderSync();

    /*
     * Generate sql script according to spreadsheet data
     */
    public String generateSQL();
}
