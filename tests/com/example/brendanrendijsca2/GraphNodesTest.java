package com.example.brendanrendijsca2;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GraphNodesTest {
    private GraphNodes<String> node1;
    private GraphNodes<String> node2;
    private GraphNodes<String> node3;

    @Before
    public void setUp() {
        node1 = new GraphNodes<>("Station1", 1);
        node2 = new GraphNodes<>("Station2", 2);
        node3 = new GraphNodes<>("Station3");
    }

    @Test
    public void testConstructorsAndToString() {
        assertEquals("Graphnodes :Station1", node1.toString());
        assertEquals("Graphnodes :Station2", node2.toString());
        assertEquals("Graphnodes :Station3", node3.toString());
    }

    @Test
    public void testConnectToNodeUndirected() {
        node1.connectToNodeUndirected(node2, 1);
        assertTrue(node1.neighbours.contains(node2));
        assertTrue(node2.neighbours.contains(node1));
    }

    @Test
    public void testTraverseGraphDepthFirst() {
        node1.connectToNodeUndirected(node2, 1);
        node2.connectToNodeUndirected(node3, 2);
        GraphNodes.traverseGraphDepthFirst(node1, null); // This will print Station1, Station2, Station3
    }

}