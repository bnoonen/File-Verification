package org.fileVerification;

import java.io.File;
import java.util.Scanner;

public class FileSorter {
    private static final Scanner userInput = new Scanner(System.in);
    private static final LocalFileReader localReader = new LocalFileReader();

    static void main () {

        //Take user input for old and new directory. Save as File per io.File
        System.out.println("Please enter the directory where your files are: ");
        File oldDirectory = new File(retreiveUserInput());


        System.out.println("Please enter the directory where you would like your files saved (note this should be the master directory containing holes, not a subdirectory): ");
        File targetDir = new File(retreiveUserInput());

        System.out.println("Thank you! Please note that any file not meeting company criteria will automatically end up in a \"null\" directory at the location specified. Please fix them accordingly. I will output a document showing what is wrong as best as possible.");

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
                    System.out.println("making directory for object for: " + holeDir.toString());
                }


                //Test that the directory for the Operation exists within the target output directory > holeID
                //Initialize the directory as File and then check that it exists, else make the directory and output to user
                File operationDir = new File (holeDir + "/" + operation);
                if (!operationDir.exists()) {
                    operationDir.mkdir();
                    System.out.println("making directory for object for: " + operationDir.toString());
                }

                //If directory and operation are made/exist, move file to directory, else move to null directory
                f.renameTo(new File (operationDir + "/ " + f.getName()));

            }
        } catch (NullPointerException n) {
            //Give user feedback about the directory to read from
            System.err.println("Error reading files from Directory, does directory exist?");
        }
    }

    //Method to allow easier retrieval of user input
    public static String retreiveUserInput() {
        return userInput.nextLine();
    }
}
