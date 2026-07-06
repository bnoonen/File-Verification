package org.fileVerification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;


/*
The entire point of this program is to cerate junk data for us to move to folders and organize. This is a replica of something I have made before, but I have not been able to get a copy of it back.
Eventually, I would like to include the ability to randomize the data and shift things, but for now we are keeping it simple.

I am keeping these files separated from each other as they perform different functions. This one "should" not exist should I have data files already.
I do not, so this will not "officially" be in the File Verification

Current uses: Generate blank PDFs based on ranges provided for holeID, operationType, and stages (Functions only to allow more stages (MORE FILES))
              Generate CSVs with random data. These will eventually be compared in another program for file validation.
 */
public class FileCreator {

    //Initialize scanner for use within class
    private static Scanner userInput = new Scanner(System.in);

    static void main(String[] args) {

        //Simple array to iterate and create files with holeIDs. Easily mutable to allow for mistakes, etc
        String[] holeID = {"P10D", "P11D", "P12D", "P13D", "P14D", "P15D",
                "S10D", "S11D", "S12D", "S13D", "S14D", "S15D",
                "T10D", "T11D", "T12D", "T13D", "T14D", "T15D"};
        String[] operationType = {"OPTV Bore Log", "ATV Bore Log", "Drill Log", "Drill Data", "Geologist Log", "Grouting Data"};

        //Iteratable array in case I want even depths. Each depth will be considered a stage
        int stages = 10;

        //User input to file path for saving example files using scanner
        System.out.println("Please enter the path to where you want to save your files");
        String pathToSave = getUserInput();

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
                double curDepth = 0;

                //Iterating through
                for (int i = 1; i < stages + 1; i++) {
                    randomDistance = (Math.random() * (20 - 5)) + 5;
                    randomDistance = Math.round(randomDistance * 10.0) / 10.0;
                    endDepth = startDepth + randomDistance;

                    startDepth = roundToDecimal(startDepth, 1);
                    endDepth = roundToDecimal(endDepth, 1);

                    curDepth = startDepth;
                    String fileName = hole + " - " + operation + " - STAGE-" + stageNum + " " + startDepth + " to " + endDepth;
                    System.out.println();

                    Path filePath;

                    int timeSigMax = (int) Math.round((Math.random() * (5400000 - 1080000) + 1080000)) / 3600;
                    switch (operation) {

                        case "Drill Data":
                            filePath = Path.of(pathToSave + "/" + fileName + ".csv");
                            writeRandomData(timeSigMax, curDepth, startDepth, endDepth, filePath);

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

                        case "Grouting Data":
                            filePath = Path.of(pathToSave + "/" + fileName + ".csv");
                            writeRandomData(timeSigMax, curDepth, startDepth, endDepth, filePath);

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
                    }
                    stageNum++;
                    startDepth = endDepth;
                }
                System.out.println("File Created Successfully");
            }
        }
    }

    public static double roundToDecimal(double i, int n) {
        double factor = Math.pow(10, n);
        double num = Math.round(i * factor) / factor;

        System.out.println(num);
        return num;
    }

    public static String getUserInput() {
        return userInput.nextLine();
    }

    public static void writeRandomData(int timeSigMax, double curDepth, double startDepth, double endDepth, Path filePath) {
        for (int i = 0; i < timeSigMax; i++) {
            String content = i + ", " + curDepth + "\n";

            try {
                Files.writeString(filePath, content, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException e) {
                System.out.println("An error occurred while creating the file.");
                e.printStackTrace();
            }
            curDepth += (endDepth - startDepth) / timeSigMax + startDepth;
        }
    }
    public static void createPDFFile(String fileName, String pathToSave) {
        Path filePath = Path.of(pathToSave + "/" + fileName + ".pdf");
        try {
            Files.writeString(filePath, "TEST", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("An error occurred while creating the file: " + e);
        }
    }
}