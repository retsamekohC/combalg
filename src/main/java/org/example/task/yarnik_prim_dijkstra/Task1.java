package org.example.task.yarnik_prim_dijkstra;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Task1 {

    public void doTask(String inputFileName, String outputFileName) throws IOException {
        Scanner input = new Scanner(Paths.get(inputFileName));
        Graph mst = readDataToGraph(input).findMinimumSpanningTree();
        Map<Integer, List<Integer>> adjacencyLists = new HashMap<>();
        for (Edge edge : mst) {
            if (!adjacencyLists.containsKey(edge.getFrom())) {
                adjacencyLists.put(edge.getFrom(), new ArrayList<>());
            }
            if (!adjacencyLists.containsKey(edge.getTo())) {
                adjacencyLists.put(edge.getTo(), new ArrayList<>());
            }
            adjacencyLists.get(edge.getFrom()).add(edge.getTo());
        }
        StringBuilder builder = new StringBuilder();
        for (Integer key : adjacencyLists.keySet()) {
            List<Integer> sorted = adjacencyLists.get(key);
            sorted.sort(Integer::compareTo);
            sorted.add(0);
            builder.append(sorted.stream().map(String::valueOf).collect(Collectors.joining(" ")));
            builder.append("\r\n");
        }
        builder.append(mst.getSumWeight());
        writeResultToFile(outputFileName, builder.toString());
    }

    private Graph readDataToGraph(Scanner input) {
        int arrayLength = input.nextInt();
        int firstInfoIndex = input.nextInt();
        int[] array = new int[arrayLength];
        array[0] = arrayLength;
        array[1] = firstInfoIndex;
        int counter = 2;
        while (input.hasNext()) {
            if (counter == arrayLength) break;
            array[counter] = input.nextInt();
            counter += 1;
        }
        Graph graph = new Graph();
        for (int i = 1; i < firstInfoIndex; i++) {
            for (int j = array[i]; j < array[i + 1]; j += 2) {
                graph.add(new Edge(i, array[j], array[j + 1]));
            }
        }
        return graph;
    }

    private void writeResultToFile(String outputFileName, String result) throws IOException {
        try (FileWriter writer = new FileWriter(outputFileName)) {
            writer.write(result);
        }
    }
}
