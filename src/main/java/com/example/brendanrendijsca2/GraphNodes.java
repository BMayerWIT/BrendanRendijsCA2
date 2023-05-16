package com.example.brendanrendijsca2;

import java.util.*;

public class GraphNodes<T> {
    public T station;

    public int line;
//    public List<GraphLinks> adjList = new ArrayList<>();
    public List<GraphNodes<T>> neighbours = new ArrayList<>();
    private GraphNodes<T> parent;

    public GraphNodes(T station, int line){
        this.station = station;
        this.line = line;
    }

    public GraphNodes(T station) {
        this.station = station;
    }

    public GraphNodes<T> getParent() {
        return parent;
    }

    public void setParent(GraphNodes<T> parent) {
        this.parent = parent;
    }

    public void connectToNodeUndirected(GraphNodes<T> destinationNode, int line) {
        if (!(destinationNode instanceof GraphNodes)) {
            throw new IllegalArgumentException("destinationNode must be of type GraphNodes");
        } else {
            neighbours.add(destinationNode);
            destinationNode.neighbours.add(this);
        }
    }

    public static <T> List<GraphNodes<T>> TraverseGraphBreadthFirst(GraphNodes<T> startNode, GraphNodes<T> endNode) {
        // Initialize the agenda list to hold nodes to be processed,
        // encountered list to hold nodes that have already been processed,
        // and path list to hold the final path from start to end node
        List<GraphNodes<T>> agenda = new ArrayList<>();
        List<GraphNodes<T>> encountered = new ArrayList<>();
        List<GraphNodes<T>> path = new ArrayList<>();

        // Add the start node to the agenda list and mark it as encountered
        agenda.add(startNode); //yet to be processed
        encountered.add(startNode); //processed

        // Traverse the graph while the agenda list is not empty
        while (!agenda.isEmpty()) {
            // Get the first node from the agenda list and remove it
            GraphNodes<T> currentNode = agenda.remove(0);

            // Check if the current node is the end node
            if (currentNode.equals(endNode)) {
                // If yes, backtrack from end node to start node to get the final path
                GraphNodes<T> pathNode = endNode;
                while (pathNode != startNode) {
                    path.add(pathNode);
                    pathNode = pathNode.getParent();
                }
                path.add(startNode);
                Collections.reverse(path); //reverse so it starts at start node
                // Return the final path
                return path;
            }
            //if current node is not the end node, add its neighbors to the agenda list
            // Add the neighbors of the current node to the agenda list if they have not been encountered before
            for (GraphNodes<T> neighbor : currentNode.neighbours) {
                if (!encountered.contains(neighbor) && !agenda.contains(neighbor)) {
                    agenda.add(neighbor);
                    encountered.add(neighbor);
                    // Set the parent of the neighbor node to the current node
                    neighbor.setParent(currentNode);
                }
            }
        }

        // If no path is found between the start and end nodes, return null
        return null;
    }


    //Recursive depth-first search of graph (all paths identified returned)

    public static <T> List<GraphNodes<T>> dijkstra(GraphNodes<T> startNode, GraphNodes<T> endNode) {
        // Initialize the distance map to hold the shortest distances from the start node to each node in the graph
        Map<GraphNodes<T>, Integer> distance = new HashMap<>();
        // Initialize the predecessor map to hold the previous node in the shortest path from the start node to each node in the graph
        Map<GraphNodes<T>, GraphNodes<T>> predecessor = new HashMap<>();
        // Initialize the set of unvisited nodes
        Set<GraphNodes<T>> unvisited = new HashSet<>();
        // Initialize the set of visited nodes
        Set<GraphNodes<T>> visited = new HashSet<>();

        // Set the distance of the start node to 0 and add it to the unvisited set
        distance.put(startNode, 0);
        unvisited.add(startNode);

        // Traverse the graph while there are unvisited nodes
        while (!unvisited.isEmpty()) {
            // Get the unvisited node with the smallest distance
            GraphNodes<T> currentNode = null;
            int minDistance = Integer.MAX_VALUE;
            for (GraphNodes<T> node : unvisited) {
                int nodeDistance = distance.getOrDefault(node, Integer.MAX_VALUE);
                if (nodeDistance < minDistance) {
                    currentNode = node;
                    minDistance = nodeDistance;
                }
            }

            // Remove the current node from the unvisited set and add it to the visited set
            unvisited.remove(currentNode);
            visited.add(currentNode);

            // Update the distances and predecessors of the current node's unvisited neighbors
            for (GraphNodes<T> neighbor : currentNode.neighbours) {
                if (visited.contains(neighbor)) {
                    continue;
                }
                int newDistance = distance.getOrDefault(currentNode, Integer.MAX_VALUE) + 1; //assuming unweighted graph
                if (newDistance < distance.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    distance.put(neighbor, newDistance);
                    predecessor.put(neighbor, currentNode);
                    unvisited.add(neighbor);
                }
            }
        }

        // Backtrack from the end node to the start node to get the shortest path
        List<GraphNodes<T>> path = new ArrayList<>();
        GraphNodes<T> currentNode = endNode;
        while (currentNode != null) {
            path.add(currentNode);
            currentNode = predecessor.get(currentNode);
        }
        Collections.reverse(path);

        // Return the shortest path
        return path;
    }






        @Override
    public String toString() {
        return "Graphnodes :" + station;
    }
}
