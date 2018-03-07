/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesearch;

/**
 *
 * @author Arod6
 * Description: 
 *  Read files in a directory
 *  Search each file fro text matching a regular expression
 *  Add matching all files to a ZIP file
 *  
 */
public class FileSearchApp {

    String path;
    String regex;
    String zipFileName;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getZipFileName() {
        return zipFileName;
    }

    public void setZipFileName(String zipFileName) {
        this.zipFileName = zipFileName;
    }
    
    public static void main(String[] args) {
        FileSearchApp app = new FileSearchApp();
        
    }
    
}
