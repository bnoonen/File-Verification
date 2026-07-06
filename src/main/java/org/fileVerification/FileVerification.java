package org.fileVerification;

/*
Project by Brenden Noonen
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/*
Class in progress. WIP WIP WIP.
 */
public class FileVerification {

    private LocalFileReader localReader;
    public FileVerification(LocalFileReader masterReader) {
        this.localReader = masterReader;
    }

    public void verifyDepthsInFile (Path toInputFile) {
        try {
            System.out.println(Files.readAllLines(toInputFile));
        } catch (IOException ioE) {
            System.err.println("Error reading file during verification: " + ioE);
        }
    }
}
