package org.example.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
public class Edge implements Comparable<Edge> {

    private Integer from;
    private Integer to;
    private Integer weight;

    @Override
    public int compareTo(@NotNull Edge other) {
        return Double.compare(this.weight, other.weight);
    }
}