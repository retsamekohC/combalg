package org.example;



import org.example.task.yarnik_prim_dijkstra.Task1;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Task1 task1 = new Task1();
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