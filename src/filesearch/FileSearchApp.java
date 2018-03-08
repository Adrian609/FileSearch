
package filesearch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipOutputStream;

/**
 * @author Arod6
 *
 * Description: Read files in a directory Search each file fro text matching a
 * regular expression Add matching all files to a ZIP file Variables String
 * path, String regex, String zipFIleName,Methods: walkDirectory,
 * searchFile(File file), addFileToZip (File file)
 */
public class FileSearchApp {

    String path;
    String regex;
    String zipFileName;
    Pattern pattern;
    List<File> zipFiles = new ArrayList<File>(); //global arraylist to collect the files

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRegex() {
        return regex;
    }

    //compile expression in the setRegex to give us a pattern we can compile once 
    //and use as many time as we want. 
    public void setRegex(String regex) {
        this.regex = regex;
        this.pattern = Pattern.compile(regex);

    }

    public String getZipFileName() {
        return zipFileName;
    }

    public void setZipFileName(String zipFileName) {
        this.zipFileName = zipFileName;
    }


    public static void main(String[] args) {
        FileSearchApp app = new FileSearchApp();

        switch (Math.min(args.length, 3)) {
            case 0:
                System.out.println("USAGE: FileSearchApp path [regex] [zipfile]");
            case 3:
                app.setZipFileName(args[2]);
            case 2:
                app.setRegex(args[1]);
            case 1:
                app.setPath(args[0]);

        }
        try {
            app.walkDirectory(app.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void walkDirectory(String path) throws IOException {
        walkFileDirectory(path);
        zipFiles();
    }

    // Walk directory method in Java 8
    public void walkFileDirectory(String path) throws IOException {
        Files.walk(Paths.get(path)).forEach(f -> processFile(f.toFile()));
    }

    public boolean searchFile(File file) throws IOException {
        return searchForFile(file);
    }

    /* 3 techniques to search for files in Java: Java 6 uses FileScanner, 
     Java 7 uses Files class, Java 8 uses streams */
    public boolean searchForFile(File file) throws IOException {
        /* Files.lines uses UTF_8 by default, Character set was set for readability 
        and to know where I can change Character set in the future
        "anyMatch" method is a short circuiting method that will return true 
        when any of the elements return true for the given condition */
        return Files.lines(file.toPath(), StandardCharsets.UTF_8).anyMatch(t -> searchText(t));
    }

    public void addFileToZip(File file) {
        if (getZipFileName() != null) {
            zipFiles.add(file);
        }
    }

    /**
     * Different file opening techniques for: Java 6 uses FileScanner, Java 7
     * uses File Class, Java 8 uses streams
     *
     * "processFile" method will catch exceptions allow application to continue
     * processing files
     *
     * @param file
     */
    public void processFile(File file) {
        try {
            if (searchFile(file)) {
                addFileToZip(file);
            }
        } catch (IOException | UncheckedIOException e) {
            System.out.println("Error processing file: " + file + ": " + e);
        }
    }

    /* "Full String VS. Partial String Matches" : "matcher" & "matches" 
    method will try to match the entire string if we want to find a match 
    anywhere in the String we need to use "matcher.find" method*/
    public boolean searchText(String text) {
        if (this.getRegex() == null) {
            return true;
        }
        return this.pattern.matcher(text).matches();
    }

    public void zipFiles() throws IOException {
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(getZipFileName()))) {
            File baseDir = new File(getPath());

            for (File file : zipFiles) {
                //fileName must be a realative path, not an absolute one.
                String fileName = getRelativeFilename(file, baseDir);
            }
        }
    }

    private String getRelativeFilename(File file, File baseDir) {
        String fileName = file.getAbsolutePath().substring(
                baseDir.getAbsolutePath().length());

        // IMPORTANT: the ZipEntry file name must use "/", not "\".
        fileName = fileName.replace('\\', '/');

        while (fileName.startsWith("/")) {
            fileName = fileName.substring(1);
        }

        return fileName;

    }
}
