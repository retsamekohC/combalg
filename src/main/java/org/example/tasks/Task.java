package org.example.tasks;

import java.io.IOException;

public interface Task {

    void doTask(String inputFileName, String outputFileName) throws IOException;
}
