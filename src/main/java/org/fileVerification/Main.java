package org.fileVerification;

/*
Project by Brenden Noonen
 */

import java.io.File;
import java.util.Scanner;

public class Main {

    //Initialize reusable objects for passing into other methods
    private static final LocalFileReader localReader = new LocalFileReader();
    private static final Scanner userInput = new Scanner(System.in);
    private static final FileSorter sorter = new FileSorter(localReader);
    private static final FileVerification verifier = new FileVerification(localReader);

    static void main() {



        //Take user input for old directory. Save as File per io.File
        System.out.println("Please enter the directory where your files are: ");
        File oldDir = new File(retreiveUserInput());

        //Take user input for old directory. Save as File per io.File
        System.out.println("Please enter the directory where you would like your files saved (note this should be the master directory containing holes, not a subdirectory): ");
        File targetDir = new File(retreiveUserInput());
        System.out.println("Thank you! Please note that any file not meeting company criteria will automatically end up in a \"null\" directory at the location specified. Please fix them accordingly. I will output a document showing what is wrong as best as possible.");

        String userOperation = "";

        while (!userOperation.equals("Quit")) {
            System.out.println("Please confirm operation you would like to perform. Enter 'quit' when you would like the program to end. " +
                    "\n 1. 'Create' Files" +
                    "\n 2. 'Sort' Files" +
                    "\n 3. 'Verify' Files");
            userOperation = retreiveUserInput();
            switch(userOperation) {

                case "Create":

                    System.out.println("Creating Files at oldDirectory");
                //try/catch to test if there are files in the directory to experiment with, if not, create them. Catch returns NullPointerException and user feedback.
                    try {
                        if (oldDir.listFiles().length == 0) {
                            LocalFileCreator FC = new LocalFileCreator();
                            FC.createTestData(oldDir.toString());
                        }
                    } catch (NullPointerException npE) {
                        System.err.println("Exception trying to createTestData: " + npE);
                    }
                    break;

                case "Sort":

                    System.out.println("Sorting files from oldDirectory to targetDir");
                    //Begin sorting files, separating good match from bad match
                    sorter.sortFiles(oldDir, targetDir);
                    break;

                case "Verify":
                    System.out.println("Verifying Files at targetDir");
                    break;

                case "Quit":
                    System.out.println("Quitting");
                    break;

                default:
                    System.out.println("Not a valid input...");
                    break;

            }
        }
    }

    //Method to allow easier retrieval of user input
    public static String retreiveUserInput() {
        return userInput.nextLine();
    }
}
