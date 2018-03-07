/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesearch;

import java.io.File;

/**
 *
 * @author Arod6
 * Description: 
 *      Read files in a directory
 *      Search each file fro text matching a regular expression
 *      Add matching all files to a ZIP file
 * Variables
 *      String path
 *      String regex
 *      String zipFIleName
 * Methods
 *      walkDirectory
 *      searchFile(File file)
 *      addFileToZip (File file)
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
        
        switch(Math.min(args.length, 3)){
            case 0:
                System.out.println("USAGE: FileSearchApp path [regex] [zipfile]");
            case 3: app.setZipFileName(args[2]);
            case 2: app.setRegex(args[1]);
            case 1: app.setPath(args[0]);
            
        }
        try {
            app.walkDirectory(app.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }

    public void walkDirectory(String path) {
        System.out.println("walkDirectory: " + path);
        searchFile(null);
        addFileToZip(null);
    }

    public void searchFile(File file) {
        System.out.println("searchForFile: " + file);
    }

    public void addFileToZip(File file) {
        System.out.println("addFileToZip: "+ file);
    }
    
    
}
