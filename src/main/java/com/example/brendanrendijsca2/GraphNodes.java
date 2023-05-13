package com.example.brendanrendijsca2;

import java.util.ArrayList;
import java.util.List;

public class GraphNodes<T> {
    public T station;

    public int line;
    public List<GraphNodes<T>> neighbours = new ArrayList<>();

    public GraphNodes(T station, int line){
        this.station = station;
        this.line = line;
    }

    public GraphNodes(T station) {
        this.station = station;
    }

    public void connectToNodeUndirected(GraphNodes<T> destinationNode, int line) {
        if (!(destinationNode instanceof GraphNodes)) {
            throw new IllegalArgumentException("destinationNode must be of type GraphNodes");
        } else {
            neighbours.add(destinationNode);
            destinationNode.neighbours.add(this);
        }
    }

    @Override
    public String toString() {
        return "Graphnodes :" + station;
    }
}
