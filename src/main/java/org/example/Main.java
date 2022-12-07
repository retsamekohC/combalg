package org.example;



import org.example.tasks.Task;
import org.example.tasks.Task1;
import org.example.tasks.Task2;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Task task1 = new Task2();
        try {
            String inputFileName = args[0];
            String outputFileName;
            try {
                outputFileName = args[1];
            } catch (ArrayIndexOutOfBoundsException ex) {
                outputFileName = "output.txt";
            }
            task1.doTask(inputFileName, outputFileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}