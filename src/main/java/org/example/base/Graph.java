package org.example.base;

import lombok.Data;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;

@Data
public class Graph implements Iterable<Edge> {

    private final List<Edge> edgeList;

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

    public void remove(Edge edge) {
        edgeList.remove(edge);
        if (vertices.containsKey(edge.getFrom())) {
            vertices.get(edge.getFrom()).remove(edge);
        }
        weights.remove(edge);
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

    /**
     * Ищет минимальное остовное дерево методом Ярника-Прима-Дейкстры
     *
     * @return минимальное остовное дерево для текущего графа
     */
    public Graph findMinimumSpanningTree() {
        Edge edge = edgeList.stream().findFirst().orElse(null);
        if (edge == null) return null;
        Set<Integer> visited = new HashSet<>();
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

    public Graph getMaxPairMatching(int leftBipartiteSize, int rightBipartiteSize) {
        int source = leftBipartiteSize + rightBipartiteSize + 1;
        int sink = source + 1;
        for (int i = 0; i < leftBipartiteSize; i++) {
            add(new Edge(source, i, 1));
        }
        for (int i = leftBipartiteSize; i < leftBipartiteSize + rightBipartiteSize; i++) {
            add(new Edge(i, sink, 1));
        }
        vertices.put(sink, new ArrayList<>());
        List<Edge> path = findExtengingPath(source, sink);
        Graph result = new Graph();
        while (path.size() > 0) {
            for (Edge edge : path) {
                if (edge.getFrom() == source || edge.getTo() == sink) {
                    remove(edge);
                } else {
                    result.add(edge);
                }
            }
            path = findExtengingPath(source, sink);
        }

        return result;
    }

    private List<Edge> findExtengingPath(int source, int sink) {
        List<Edge> result = new ArrayList<>();

        Stack<Integer> stack = new Stack<>();
        Set<Integer> visited = new HashSet<>();
        String res = dfs(source, stack, visited, sink);
        if (res == null)
            return result;
        String[] splitted = res.split(" ");
        for (int i = 0; i < splitted.length - 1; i++) {
            result.add(new Edge(Integer.valueOf(splitted[i]), Integer.valueOf(splitted[i + 1]), 1));
        }
        return result;
    }

    private String dfs(int curVertex, Stack<Integer> stack, Set<Integer> visited, int stop) {
        if (curVertex == stop) {
            return String.valueOf(curVertex);
        }
        for (Edge e : vertices.get(curVertex)) {
            int neighbour = e.getTo();
            if (!visited.contains(neighbour)) {
                stack.push(neighbour);
                String t = dfs(neighbour, stack, visited, stop);
                if (!"".equals(t)) {
                    return curVertex + " " + t;
                }
                stack.pop();
            }
        }
        return "";
    }
}
