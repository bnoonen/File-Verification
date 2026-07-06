package org.fileVerification;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class FileVerification {
    public void verifyDepthsInFile (Path toInputFile) {
        try {
            System.out.println(Files.readAllLines(toInputFile));
        } catch (IOException ioE) {
            System.err.println("Error reading file during verification: " + ioE);
        }
    }
}
