package org.example.tasks;

import org.example.base.Edge;
import org.example.base.Graph;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Task2 implements Task {


    @Override
    public void doTask(String inputFileName, String outputFileName) throws IOException {
        Scanner input = new Scanner(Paths.get(inputFileName));
        int leftBipartiteSize = input.nextInt();
        int rightBipartiteSize = input.nextInt();
        Graph graph = new Graph();
        input.nextLine();
        for (int i = 0; i < leftBipartiteSize; i++) {
            String line = input.nextLine();
            for (String vertex : line.split(" ")) {
                graph.add(new Edge(i, Integer.parseInt(vertex) - 1, 1));
            }
        }
        Graph matching = graph.getMaxPairMatching(leftBipartiteSize, rightBipartiteSize);
        int[] result = new int[leftBipartiteSize];
        for (Edge edge : matching) {
            result[edge.getFrom()] = edge.getTo() == null ? 0 : edge.getTo() + 1;
        }
        StringBuilder builder = new StringBuilder();
        for (int j : result) {
            builder.append(j);
            builder.append(" ");
        }
        try (FileWriter writer = new FileWriter(outputFileName)) {
            writer.write(builder.toString());
        }
    }
}
