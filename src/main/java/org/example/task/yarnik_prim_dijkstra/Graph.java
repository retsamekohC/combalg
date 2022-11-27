package org.example.task.yarnik_prim_dijkstra;

import lombok.Data;

import java.util.*;
import java.util.function.Consumer;

@Data
public class Graph implements Iterable<Edge> {

    private List<Edge> edgeList;

    private Map<Integer, List<Edge>> vertices;

    private Map<Edge, Integer> weights;

    public Graph() {
        this.edgeList = new ArrayList<>();
        this.vertices = new HashMap<>();
        this.weights = new HashMap<>();
    }

    public Graph(List<Edge> edges) {
        this.edgeList = new ArrayList<>();
        this.vertices = new HashMap<>();
        this.weights = new HashMap<>();
        edges.forEach(this::add);
    }

    public void add(Edge edge) {
        edgeList.add(edge);
        if (!vertices.containsKey(edge.getFrom())) {
            vertices.put(edge.getFrom(), new ArrayList<>());
        }
        weights.put(edge, edge.getWeight());
        vertices.get(edge.getFrom()).add(edge);
    }

    public void add(Graph graph) {
        graph.getEdgeList().forEach(this::add);
    }

    public int getSumWeight() {
        int result = 0;
        result += edgeList.stream().mapToInt(Edge::getWeight).sum();
        return result;
    }

    public void sortEdges() {
        edgeList.sort(Edge::compareTo);
    }

    @Override
    public Iterator<Edge> iterator() {
        return edgeList.listIterator();
    }

    @Override
    public Spliterator<Edge> spliterator() {
        return edgeList.spliterator();
    }

    @Override
    public void forEach(Consumer<? super Edge> action) {
        edgeList.forEach(action);
    }

    public Graph findMinimumSpanningTree() {
        Edge edge = edgeList.stream().findFirst().orElse(null);
        if (edge == null) return null;
        Set<Integer> visited  = new HashSet<>();
        Set<Integer> notVisited = vertices.keySet();
        List<Edge> edges = new ArrayList<>();
        visited.add(edge.getFrom());
        while (notVisited.size() != visited.size()) {
            Edge minEdge = getMinimalOuterEdge(visited);
            edges.add(minEdge);
            visited.add(minEdge.getTo());
        }
        return new Graph(edges);
    }

    private Edge getMinimalOuterEdge(Set<Integer> visited) {
        Edge result = null;
        int min = Integer.MAX_VALUE;
        for (int v : visited) {
            for (Edge e : vertices.get(v)) {
                if (weights.get(e) < min && !visited.contains(e.getTo()) && visited.contains(e.getFrom())) {
                    result = e;
                    min = weights.get(e);
                }
            }
        }
        return result;
    }
}
