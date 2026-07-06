package org.fileVerification;

/*
Project by Brenden Noonen
 */

import java.io.File;

public interface FileReader {
    public void initializeFileData (File workingFile);

    String fetchHoleID();
    String fetchOperation();
    String fetchStageID();
    String fetchStartDepth();
    String fetchEndDepth();
    String fetchFileType();

    String fetchHoleID(File workingFile);
    String fetchOperation(File workingFile);
    String fetchStageID(File workingFile);
    String fetchStartDepth(File workingFile);
    String fetchEndDepth(File workingFile);
    String fetchFileType(File workingFile);
}
