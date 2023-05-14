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

    public static void traverseGraphDepthFirst(GraphNodes<?> from, List<GraphNodes<?>> encountered ){
        System.out.println(from.station);
        if(encountered==null) encountered=new ArrayList<>(); //First node so create new (empty) encountered list
        encountered.add(from);
        for(GraphNodes<?> adjNode : from.neighbours)
            if(!encountered.contains(adjNode)) traverseGraphDepthFirst(adjNode, encountered );
    }

    public static void traverseGraphBreadthFirst(List<GraphNodes<?>> agenda, List<GraphNodes<?>> encountered ){
        if(agenda.isEmpty()) return;
        GraphNodes<?> next=agenda.remove(0);
        System.out.println(next.station);
        if(encountered==null) encountered=new ArrayList<>(); //First node so create new (empty) encountered list
        encountered.add(next);
        for(GraphNodes<?> adjNode : next.neighbours)
            if(!encountered.contains(adjNode) && !agenda.contains(adjNode)) agenda.add(adjNode); //Add children to
//end of agenda
        traverseGraphBreadthFirst(agenda, encountered ); //Tail call
    }


    @Override
    public String toString() {
        return "Graphnodes :" + station;
    }
}
