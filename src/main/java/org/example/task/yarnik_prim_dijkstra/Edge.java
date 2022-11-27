package org.example.task.yarnik_prim_dijkstra;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
class Edge implements Comparable<Edge> {

    private int from;
    private int to;
    private int weight;

    @Override
    public int compareTo(@NotNull Edge other) {
        return Double.compare(this.weight, other.weight);
    }
}