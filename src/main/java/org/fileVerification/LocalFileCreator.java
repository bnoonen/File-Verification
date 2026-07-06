package org.fileVerification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/*
Project by Brenden Noonen
 */

/*
The entire point of this program is to cerate junk data for us to move to folders and organize. This is a replica of something I have made before, but I have not been able to get a copy of it back.
Eventually, I would like to include the ability to randomize the data and shift things, but for now we are keeping it simple.

I am keeping these files separated from each other as they perform different functions. This one "should" not exist should I have data files already.
I do not, so this will not "officially" be in the File Verification

Current uses: Generate blank PDFs based on ranges provided for holeID, operationType, and stages (Functions only to allow more stages (MORE FILES))
              Generate CSVs with random data. These will eventually be compared in another program for file validation.
 */
public class LocalFileCreator implements FileCreator {

    //DEPRECATED
    //Initialize scanner for use within class
    //private static Scanner userInput = new Scanner(System.in);

    public void createTestData(String pathToSave) {

        //Simple array to iterate and create files with holeIDs. Easily mutable to allow for mistakes, etc
        String[] holeID = {"P10D", "P11D", "P12D", "P13D", "P14D", "P15D",
                "S10D", "S11D", "S12D", "S13D", "S14D", "S15D",
                "T10D", "T11D", "T12D", "T13D", "T14D", "T15D"};
        String[] operationType = {"OPTV Bore Log", "ATV Bore Log", "Drill Log", "Drill Data", "Geologist Log", "Grouting Data"};

        //Iteratable array in case I want even depths. Each depth will be considered a stage
        int stageCount = 10;

        //User input to file path for saving example files using scanner
        //System.out.println("Please enter the path to where you want to save your files");
        //String pathToSave = getUserInput();

        /*
        For loop structure:
        Iterate through holes, as this will be the outer folders and the first chunk of the file name
        Then iterate through the operations, because this will be the sorted folders, which is also the second part of the name
         */

        //Iterating through holeIDs
        for (String hole : holeID) {

            //Iterating through operationTypes
            for (String operation : operationType) {

                //Initializing variables to allow for stage tracking and random data generation
                /*
                randomDistance = distance that a stage can go, we are going to limit to 15 feet maximum and 5 feet minimum below. We want this different per operation, as in reality, we do not get the same distances.
                startDepth = 0... iterating by stage. This will sequentially increase, such that stage 1 = 000.0 to 010.0, stage 2 = 010.0 to 020.0, etc
                endDepth = startDepth + randomDistance. This is the drilled depth done in a stage or performed by another operation in a drill stage
                stageNum = the stage you are currently on. Start at 1, since we don't use programmer logic and iterate per operation performed.
                curDepth = the current depth, iterating randomly through a stage as data. We will generate this with timestamps on every .CSV to give faux data. We will introduce error data as well
                 */
                double randomDistance = 0;
                double startDepth = 0;
                double endDepth = 0;
                int stageNum = 1;
                double curDepth = 0; //Currently unused, but planned

                //Iterating through stageCount, i = 1 as starter, count to stageCount + 1 to allow stageCount to be the total number of stages
                for (int i = 1; i < stageCount + 1; i++) {

                    //randomDistance set to be 15 based on equation below (Math.random() * (Max - Min) + Min)
                    randomDistance = (Math.random() * (20 - 5)) + 5;

                    //Round and multiple / divide randomDistance to truncate after first decimal
                    randomDistance = roundToDecimal(randomDistance, 1);
                    // randomDistance = Math.round(randomDistance * 10.0) / 10.0;

                    //endDepth set to startDepth + randomDistance per stage as described above
                    endDepth = startDepth + randomDistance;

                    //Round both startDepth and endDepth to allow for least likely .0000000000001 added or subtracted from both depths later.
                    startDepth = roundToDecimal(startDepth, 1);
                    endDepth = roundToDecimal(endDepth, 1);

                    //Set curDepth to startDepth to allow for data inputs to log as if currently drilling
                    curDepth = startDepth;

                    //Set fileName per variables mentioned above. Format does not use nice delimiters as that was the case in the real world.
                    String fileName = hole + " - " + operation + " - STAGE-" + stageNum + " " + startDepth + " to " + endDepth;

                    //Define filePath before switch, as there will be multiple fileTypes depending on the operation performed
                    Path filePath;

                    //Define maximum time signature to iterate to. Generally stages take 20 hours max, 10 min. Dividing by 3600 seconds to reduce file size and clutter
                    int timeSigMax = (int) Math.round((Math.random() * (5400000 - 1080000) + 1080000)) / 3600;

                    //Switch defined to generate differing files per operation performed
                    switch (operation) {

                        //Case "Drill Data" to create .csv with random data inside
                        case "Drill Data":
                            filePath = Path.of(pathToSave + "/" + fileName + ".csv");
                            writeRandomData(timeSigMax, startDepth, endDepth, filePath);

                            //DEPRECATED AND MOVED TO METHOD
                            /*for (int i = 0; i < timeSigMax; i++) {
                                String content = i + ", " + curDepth + "\n";

                                try {
                                    Files.writeString(filePath, content, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                                } catch (IOException e) {
                                    System.out.println("An error occurred while creating the file.");
                                    e.printStackTrace();
                                }
                                curDepth += (endDepth - startDepth) / timeSigMax;
                                //System.out.println(startDepth);
                                }
                                */
                            break;

                        //Case "Grouting Data" to create .csv with random data inside
                        case "Grouting Data":
                            filePath = Path.of(pathToSave + "/" + fileName + ".csv");
                            writeRandomData(timeSigMax, startDepth, endDepth, filePath);

                            //DEPRECATED AND MOVED TO METHOD
                            /*
                            for (int i = 0; i < timeSigMax; i++) {
                                String content = i + ", " + curDepth + "\n";

                                try {
                                    Files.writeString(filePath, content, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                                } catch (IOException e) {
                                    System.out.println("An error occurred while creating the file.");
                                    e.printStackTrace();
                                }
                                curDepth += (endDepth-startDepth)/timeSigMax;
                            }*/
                            break;

                        //Case "Drill Log" to create semi-blank .pdf
                        case "Drill Log":
                            createPDFFile(fileName, pathToSave);

                            //DEPRECATED AND MOVED TO METHOD
                            /*filePath = Path.of(pathToSave + "/" + fileName + ".pdf");
                            try {
                                Files.writeString(filePath, "TEST", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                            } catch (IOException e) {
                                System.err.println("An error occurred while creating the file: " + e);

                            }*/
                            break;


                        //Case "OPTV Bore Log" to create semi-blank .pdf
                        case "OPTV Bore Log":
                            createPDFFile(fileName, pathToSave);

                            //DEPRECATED AND MOVED TO METHOD
                            /*filePath = Path.of(pathToSave + "/" + fileName + ".pdf");
                            try {
                                Files.writeString(filePath, "TEST", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                            } catch (IOException e) {
                                System.err.println("An error occurred while creating the file: " + e);
                            }*/
                            break;

                        //Case "ATV Bore Log" to create semi-blank .pdf
                        case "ATV Bore Log":
                            createPDFFile(fileName, pathToSave);

                            //DEPRECATED AND MOVED TO METHOD
                            /*
                            filePath = Path.of(pathToSave + "/" + fileName + ".pdf");
                            try {
                                Files.writeString(filePath, "TEST", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                            } catch (IOException e) {
                                System.err.println("An error occurred while creating the file: " + e);
                            }*/
                            break;

                    }
                    //Increase stage number and reset start depth to continue into the next stage
                    stageNum++;
                    startDepth = endDepth;
                }

                //Will have toggle debug mode
                //Prompt user that file has been created
                //System.out.println("File Created Successfully");
            }
        }
    }

    //Method to round to Decimal places at will using method of multiple x factor of 10, then divide by same factor after rounding
    //Removed placeholder num variable as redundant to save space
    public static double roundToDecimal(double inputNum, int n) {
        double factor = Math.pow(10, n);
        return Math.round(inputNum * factor) / factor;
    }

    /* DEPRECATED FOR MAIN CLASS
    //Simple getUserInput using scanner.nextLine();
    public static String getUserInput() {
        return userInput.nextLine();
    }*/

    /*
    Method to write random user data to file
    Returns: none. Prints error in case of failure to write
     */
    public static void writeRandomData(int timeSigMax, double startDepth, double endDepth, Path filePath) {
        //Track curDepth to log to file as data
        double curDepth = startDepth;

        for (int i = 0; i < timeSigMax; i++) {
            //Create random chance for data to be wrong for validation later
            double causeInvalidEntry = Math.random();

            //If statement to increase depth by large amount to be caught in validation later
            if (causeInvalidEntry > .97) {
                //User Feedback on creation of file. Will swap for togglable debug mode
                //System.out.println("Writing invalid data to file: " + filePath + " : " + causeInvalidEntry);
                curDepth += causeInvalidEntry * 10;
            }

            //Content string to be written to file
            String content = i + ", " + curDepth + "\n";

            //If statement to reduce depth by same increased amount to continue data smoothly
            if (causeInvalidEntry > .97)
                curDepth -= causeInvalidEntry*10;

            //Try to write to file with content. Options set to create file if none exists or append if one does. Caught in error and outputted to user.
            try {
                Files.writeString(filePath, content, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException e) {
                System.err.println("Error writing Random Data: " + e);
            }

            /*
            Increment curDepth by interval between start/end depth and / by timeSigMax to have even steps between. Add startDepth to keep within the stage.
            Ie: start: 100, end: 115, timeSigMax = 500. So curDepth = 100 -> (115-100) / 500 + 100 -> 15/500 + initial 100. So every step will increment by 15/500
             */

            curDepth += (endDepth - startDepth) / timeSigMax;


        }
    }

    //Method to create PDF files that have basic test data in them. Will fill with regular data later. Returns error should file fail to be created.
    public static void createPDFFile(String fileName, String pathToSave) {
        Path filePath = Path.of(pathToSave + "/" + fileName + ".pdf");
        try {
            Files.writeString(filePath, "TEST", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("An error occurred while creating the file: " + e);
        }
    }
}