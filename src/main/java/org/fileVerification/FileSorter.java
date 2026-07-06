package org.fileVerification;

/*
Project by Brenden Noonen
 */

import java.io.File;

public class FileSorter implements Sorter{

    //Initialize FileReader to allow sorting
    private LocalFileReader localReader;

    //Initialize FileSorter object variables for use in file upon object creation
    public FileSorter(LocalFileReader masterReader) {
        this.localReader = masterReader;
    }
    public void sortFiles(File oldDirectory, File targetDir) {

        //Try/catch used for NullPointerException to Directory
        try {

            //For loop to iterate through all files in directory, folders will currently fail inspection and be routed to an appropriate mismatch directory
            for (File f : oldDirectory.listFiles()) {

                //initialize data for current hole as local variables
                localReader.initializeFileData(f);
                String holeID = localReader.fetchHoleID();
                String operation = localReader.fetchOperation();
                String stageID = localReader.fetchStageID();
                String startDepth = localReader.fetchStartDepth();
                String endDepth = localReader.fetchEndDepth();
                String fileType = localReader.fetchFileType();

                //Test output to confirm that each method pulls properly or what is being pulled
                //System.out.println("HoleID: " + localReader.fetchHoleID() + " Operation: " + localReader.fetchOperation() + " Stage ID: " + localReader.fetchStageID() + " Start Depth: " + localReader.fetchStartDepth() + " End Depth: " + localReader.fetchEndDepth() + " File Type: " + localReader.fetchFileType());

                //Test that the directory for the holeID exists within the target output directory
                //Initialize the directory as File and then check that it exists, else make the directory and output to user
                File holeDir = new File (targetDir + "/" + holeID);
                if (!holeDir.exists()) {
                    holeDir.mkdir();
                    System.out.println("making directory for object for: " + holeDir);
                }


                //Test that the directory for the Operation exists within the target output directory > holeID
                //Initialize the directory as File and then check that it exists, else make the directory and output to user
                File operationDir = new File (holeDir + "/" + operation);
                if (!operationDir.exists()) {
                    operationDir.mkdir();
                    System.out.println("making directory for object for: " + operationDir);
                }

                //If directory and operation are made/exist, move file to directory, else move to null directory
                f.renameTo(new File (operationDir + "/ " + f.getName()));

            }
        } catch (NullPointerException n) {
            //Give user feedback about the directory to read from
            System.err.println("Error reading files from Directory, does directory exist?");
        }
    }

}
