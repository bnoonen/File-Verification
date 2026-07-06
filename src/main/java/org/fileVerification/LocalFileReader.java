package org.fileVerification;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocalFileReader implements FileReader {

    //Initializing regex pattern for filenames and Matcher to confirm files match regex
    private static final Pattern FILE_PATTERN = Pattern.compile("([PST][0-9]+[DU])\\s-\\s(.*)\\s-\\s(.*)\\s([0-9]+\\.[0-9])\\sto\\s([0-9]+\\.[0-9]*)(\\.[a-zA-Z]+)", Pattern.CASE_INSENSITIVE);
    private Matcher matcher;

    //Private variables, immutable from outside for safety
    private String holeID;
    private String operationType;
    private String stageID;
    private String startDepth;
    private String endDepth;
    private String fileType;

    // Method to intialize fileData and save on resources of individual intialization
    public void initializeFileData (File workingFile) {
        //Test that workFile name matches the regex criteria, else returns nulls when variable called for
        matcher = FILE_PATTERN.matcher(workingFile.getName().trim());
        if (matcher.matches()) {
            //Instantiating Variables
            holeID =  matcher.group(1).trim();
            operationType =  matcher.group(2).trim();
            stageID =  matcher.group(3).trim();
            startDepth =  matcher.group(4).trim();
            endDepth =  matcher.group(5).trim();
            fileType =  matcher.group(6).trim();
        } else {
            //Return to user that file did not meet criterion
            System.err.println("Error matching file: " + workingFile.getName());
        }
    }

    // Method to fetch initialized holeID
    public String fetchHoleID(){
        return holeID;
    }

    // Method to fetch initialized operationID
    public String fetchOperation() {
        return operationType;
    }

    // Method to fetch initialized stageID
    public String fetchStageID() {
        return stageID;
    }

    // Method to fetch initialized startDepth
    public String fetchStartDepth() {
        return startDepth;
    }

    // Method to fetch initialized endDepth
    public String fetchEndDepth() {
        return endDepth;
    }

    // Method to fetch initialized fileType
    public String fetchFileType() {
        return fileType;
    }


    // Method to fetch uninitialized holeID
    //Same logic applies to methods below
    public String fetchHoleID(File workingFile){
        //Test that file meets regex criteria, else return error
        matcher = FILE_PATTERN.matcher(workingFile.getName().trim());
        if (matcher.matches()) {
            //return group(1), holeID
            return matcher.group(1).trim();
        }
        //return error
        return "ERROR matching holeID: " + workingFile.getName();
    }

    // Method to fetch uninitialized operationID
    public String fetchOperation(File workingFile){
        //Test that file meets regex criteria, else return error
        matcher = FILE_PATTERN.matcher(workingFile.getName().trim());
        if (matcher.matches()) {
            //return group(1), operationID
            return matcher.group(2).trim();
        }
        //return error
        return "ERROR matching holeID: " + workingFile.getName();
    }

    // Method to fetch initialized stageID
    public String fetchStageID(File workingFile){
        //Test that file meets regex criteria, else return error
        matcher = FILE_PATTERN.matcher(workingFile.getName().trim());
        if (matcher.matches()) {
            //return group(1), stageID
            return matcher.group(3).trim();
        }
        //return error
        return "ERROR matching holeID: " + workingFile.getName();
    }

    // Method to fetch initialized startDepth
    public String fetchStartDepth(File workingFile){
        //Test that file meets regex criteria, else return error
        matcher = FILE_PATTERN.matcher(workingFile.getName().trim());
        if (matcher.matches()) {
            //return group(1), startDepth
            return matcher.group(4).trim();
        }
        //return error
        return "ERROR matching holeID: " + workingFile.getName();
    }

    // Method to fetch initialized endDepth
    public String fetchEndDepth(File workingFile){
        //Test that file meets regex criteria, else return error
        matcher = FILE_PATTERN.matcher(workingFile.getName().trim());
        if (matcher.matches()) {
            //return group(1), endDepth
            return matcher.group(5).trim();
        }
        //return error
        return "ERROR matching holeID: " + workingFile.getName();
    }

    // Method to fetch initialized fileType
    public String fetchFileType(File workingFile){
        //Test that file meets regex criteria, else return error
        matcher = FILE_PATTERN.matcher(workingFile.getName().trim());
        if (matcher.matches()) {
            //return group(1), fileType
            return matcher.group(6).trim();
        }
        //return error
        return "ERROR matching holeID: " + workingFile.getName();
    }

}
