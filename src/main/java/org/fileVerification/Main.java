package org.fileVerification;

import java.io.File;
import java.util.Scanner;

public class Main {

    //Initialize reusable objects for passing into other methods
    private static final LocalFileReader localReader = new LocalFileReader();
    private static final Scanner userInput = new Scanner(System.in);
    private static final FileSorter sorter = new FileSorter(localReader);

    static void main() {
        //Take user input for old directory. Save as File per io.File
        System.out.println("Please enter the directory where your files are: ");
        File oldDirectory = new File(retreiveUserInput());

        //try/catch to test if there are files in the directory to experiment with, if not, create them. Catch returns NullPointerException and user feedback.
        try {
            if (oldDirectory.listFiles().length == 0) {
                FileCreator FC = new FileCreator();
                FC.createTestData(oldDirectory.toString());
            }
        } catch (NullPointerException npE) {
            System.err.println("Exception trying to createTestData: " + npE);
        }

        //Take user input for old directory. Save as File per io.File
        System.out.println("Please enter the directory where you would like your files saved (note this should be the master directory containing holes, not a subdirectory): ");
        File targetDir = new File(retreiveUserInput());
        System.out.println("Thank you! Please note that any file not meeting company criteria will automatically end up in a \"null\" directory at the location specified. Please fix them accordingly. I will output a document showing what is wrong as best as possible.");


        //Begin sorting files, separating good match from bad match
        sorter.sortFiles(oldDirectory, targetDir);

    }

    //Method to allow easier retrieval of user input
    public static String retreiveUserInput() {
        return userInput.nextLine();
    }
}
