import com.example.brendanrendijsca2.GraphNodes;
import com.example.brendanrendijsca2.Station;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GraphNodesTest {

    @Test
    public void testConnectToNodeUndirected() {
        // Create sample stations
        Station stationA = new Station(1, 0.0f, 0.0f, "Station A", 0, 0);
        Station stationB = new Station(2, 1.0f, 1.0f, "Station B", 1, 1);
        Station stationC = new Station(3, 2.0f, 2.0f, "Station C", 2, 2);

        // Create GraphNodes for stations
        GraphNodes<Station> graphNodeA = new GraphNodes<>(stationA);
        GraphNodes<Station> graphNodeB = new GraphNodes<>(stationB);
        GraphNodes<Station> graphNodeC = new GraphNodes<>(stationC);

        // Connect nodes undirected
        graphNodeA.connectToNodeUndirected(graphNodeB, 1);
        graphNodeB.connectToNodeUndirected(graphNodeC, 1);

        // Test the neighbors of graphNodeA
        List<GraphNodes<Station>> neighborsA = graphNodeA.neighbours;
        Assertions.assertEquals(1, neighborsA.size());
        Assertions.assertEquals(graphNodeB, neighborsA.get(0));

        // Test the neighbors of graphNodeB
        List<GraphNodes<Station>> neighborsB = graphNodeB.neighbours;
        Assertions.assertEquals(2, neighborsB.size());
        Assertions.assertEquals(graphNodeA, neighborsB.get(0));
        Assertions.assertEquals(graphNodeC, neighborsB.get(1));

        // Test the neighbors of graphNodeC
        List<GraphNodes<Station>> neighborsC = graphNodeC.neighbours;
        Assertions.assertEquals(1, neighborsC.size());
        Assertions.assertEquals(graphNodeB, neighborsC.get(0));
    }

    @Test
    public void testTraverseGraphBreadthFirst() {
        // Create sample stations
        Station stationA = new Station(1, 0.0f, 0.0f, "Station A", 0, 0);
        Station stationB = new Station(2, 1.0f, 1.0f, "Station B", 1, 1);
        Station stationC = new Station(3, 2.0f, 2.0f, "Station C", 2, 2);

        // Create GraphNodes for stations
        GraphNodes<Station> graphNodeA = new GraphNodes<>(stationA);
        GraphNodes<Station> graphNodeB = new GraphNodes<>(stationB);
        GraphNodes<Station> graphNodeC = new GraphNodes<>(stationC);

        // Connect nodes undirected
        graphNodeA.connectToNodeUndirected(graphNodeB, 1);
        graphNodeB.connectToNodeUndirected(graphNodeC, 1);

        // Traverse the graph
        List<GraphNodes<Station>> path = GraphNodes.TraverseGraphBreadthFirst(graphNodeA, graphNodeC);

        // Check the path
        Assertions.assertNotNull(path);
        Assertions.assertEquals(3, path.size());
        Assertions.assertEquals(graphNodeA, path.get(0));
        Assertions.assertEquals(graphNodeB, path.get(1));
        Assertions.assertEquals(graphNodeC, path.get(2));
    }

    @Test
    public void testDijkstra() {
        // Create sample stations
        Station stationA = new Station(1, 0.0f, 0.0f, "Station A", 0, 0);
        Station stationB = new Station(2, 1.0f, 1.0f, "Station B", 1, 1);
        Station stationC = new Station(3, 2.0f, 2.0f, "Station C", 2, 2);

        // Create GraphNodes for stations
        GraphNodes<Station> graphNodeA = new GraphNodes<>(stationA);
        GraphNodes<Station> graphNodeB = new GraphNodes<>(stationB);
        GraphNodes<Station> graphNodeC = new GraphNodes<>(stationC);

        // Connect nodes undirected
        graphNodeA.connectToNodeUndirected(graphNodeB, 1);
        graphNodeB.connectToNodeUndirected(graphNodeC, 1);

        // Find the shortest path using Dijkstra's algorithm
        List<GraphNodes<Station>> path = GraphNodes.dijkstra(graphNodeA, graphNodeC);

        // Check the path
        Assertions.assertNotNull(path);
        Assertions.assertEquals(3, path.size());
        Assertions.assertEquals(graphNodeA, path.get(0));
        Assertions.assertEquals(graphNodeB, path.get(1));
        Assertions.assertEquals(graphNodeC, path.get(2));
    }
}

