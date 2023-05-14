package com.example.brendanrendijsca2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.*;
import javafx.scene.paint.Color;

import static com.example.brendanrendijsca2.GraphNodes.FindAllPathsDepthFirst;

public class RouteFinderController {

        @FXML
        private ComboBox<String> EndPoint;

        @FXML
        private ImageView ImageView;

        @FXML
        private Label number;

        @FXML
        private ComboBox<String> startPoint;

        @FXML
        private Label welcomeText;
        @FXML
        private ListView routeView;

        @FXML
        void SelectEndPoint(ActionEvent event) {

        }

        @FXML
        void SelectStartPoint(ActionEvent event) {

        }

        @FXML
        public void ShowMultipleRoutes(ActionEvent event) {
            routeView.getItems().clear();

            // Get the selected start and end stations from the UI
            int startStationIndex = startPoint.getSelectionModel().getSelectedIndex();
            int endStationIndex = EndPoint.getSelectionModel().getSelectedIndex();

            // Get the start and end nodes from the station list using their indices
            GraphNodes<Station> startNode = stationList.get(startStationIndex);
            GraphNodes<Station> endNode = stationList.get(endStationIndex);

            List<List<GraphNodes<Station>>> allPaths = GraphNodes.FindAllPathsDepthFirst(startNode, null, endNode.station);

            if (allPaths == null || allPaths.isEmpty()) {
                System.out.println("No route found from " + startNode + " to " + endNode);
                return;
            }

            System.out.println("Multiple routes found from " + startNode + " to " + endNode + ":");
            int count = 1;
            for (List<GraphNodes<Station>> path : allPaths) {
                System.out.println("Route " + count + ":");
                for (GraphNodes<Station> node : path) {
                    System.out.println(node.station);
                }
                count++;
            }

             //Construct the list of stations in each path and add to the list view
            List<List<Station>> stationPaths = new ArrayList<>();
            for (List<GraphNodes<Station>> path : allPaths) {
                List<Station> stationPath = new ArrayList<>();
                for (GraphNodes<Station> node : path) {
                    stationPath.add(node.station);
                }
                stationPaths.add(stationPath);
            }
            routeView.getItems().addAll(stationPaths);
        }

    @FXML
        public List<Station> ShowSingleRoute(ActionEvent event) {
            //clear list view
            routeView.getItems().clear();

            // Get the selected start and end stations from the UI
            int startStationIndex = startPoint.getSelectionModel().getSelectedIndex();
            int endStationIndex = EndPoint.getSelectionModel().getSelectedIndex();

            // Get the start and end nodes from the station list using their indices
            GraphNodes<Station> startNode = stationList.get(startStationIndex);
            GraphNodes<Station> endNode = stationList.get(endStationIndex);

            // Check if start and end nodes exist
            if (startNode == null || endNode == null) {
                System.out.println("Start or end station not found");
                return null;
            }

            // Check if start and end nodes are the same
            if (startNode.equals(endNode)) {
                System.out.println("Start and end stations are the same");
                return null;
            }

            // Traverse the graph using BFS
            List<GraphNodes<Station>> path = GraphNodes.TraverseGraphBreadthFirst(startNode, endNode);

            // If the path is null, no route exists between the two stations
            if (path == null) {
                System.out.println("No route exists between " + startNode.station.getStationName() + " and " + endNode.station.getStationName());
                return null;
            }

            // Construct the list of stations in the path
            List<Station> stationPath = new ArrayList<>();
            for (GraphNodes<Station> node : path) {
                stationPath.add(node.station);
            }

            // Print the route
            System.out.println("Route from " + startNode.station.getStationName() + " to " + endNode.station.getStationName() + ":");

            // Add the route to the UI list view
            routeView.getItems().add("Route from " + startNode.station.getStationName() + " to " + endNode.station.getStationName() + ":");
            for (Station station : stationPath) {
                System.out.println("- " + station.getStationName());
                routeView.getItems().add("- " + station.getStationName());
            }

            // Return the path
            return stationPath;
        }

        public List<Station> ShowFastestRoute(ActionEvent event) {
            //clear list view
            routeView.getItems().clear();

            // Get the selected start and end stations from the UI
            int startStationIndex = startPoint.getSelectionModel().getSelectedIndex();
            int endStationIndex = EndPoint.getSelectionModel().getSelectedIndex();

            // Get the start and end nodes from the station list using their indices
            GraphNodes<Station> startNode = stationList.get(startStationIndex);
            GraphNodes<Station> endNode = stationList.get(endStationIndex);

            // Check if start and end nodes exist
            if (startNode == null || endNode == null) {
                System.out.println("Start or end station not found");
                return null;
            }

            // Check if start and end nodes are the same
            if (startNode.equals(endNode)) {
                System.out.println("Start and end stations are the same");
                return null;
            }

            // Traverse the graph using BFS
            List<GraphNodes<Station>> path = GraphNodes.dijkstra(startNode, endNode);

            // If the path is null, no route exists between the two stations
            if (path == null) {
                System.out.println("No route exists between " + startNode.station.getStationName() + " and " + endNode.station.getStationName());
                return null;
            }

            // Construct the list of stations in the path
            List<Station> stationPath = new ArrayList<>();
            for (GraphNodes<Station> node : path) {
                stationPath.add(node.station);
            }

            // Print the route
            System.out.println("Route from " + startNode.station.getStationName() + " to " + endNode.station.getStationName() + ":");

            // Add the route to the UI list view
            routeView.getItems().add("Route from " + startNode.station.getStationName() + " to " + endNode.station.getStationName() + ":");
            for (Station station : stationPath) {
                System.out.println("- " + station.getStationName());
                routeView.getItems().add("- " + station.getStationName());
            }

            // Return the path
            return stationPath;

        }



    List<GraphNodes<Station>> stationList = new ArrayList<>();
    Map<Integer, List<Station>> stationsById = new HashMap<>();
    @FXML
    public void ReadDataFromCSV() throws Exception{ //fillListWithStations
        String filepath = "C:\\Users\\brend\\IdeaProjects\\BrendanRendijsCA2\\src\\main\\java\\com\\example\\brendanrendijsca2\\London.csv";
        String splitCSVBy = ",";

            BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
            String line ="";
            while ((line = bufferedReader.readLine()) != null) {
                String [] splitArray = line.split(splitCSVBy);
                if (splitArray.length > 4 && ("1".equals(splitArray[4]) || "1.5".equals(splitArray[4]))) {
                    Station station = new Station(Integer.parseInt(splitArray[0]), Float.parseFloat(splitArray[1]), Float.parseFloat(splitArray[2]), splitArray[3]);
                    GraphNodes<Station> stationGN = new GraphNodes<>(station);
                    stationList.add(stationGN);
                    startPoint.getItems().add(splitArray[3]);
                    EndPoint.getItems().add(splitArray[3]);
                }
            }
            bufferedReader.close();
    }

    public void createAdjacentNodeEdges() throws Exception {

        // Define the filepath of the CSV file and the separator.
        String filepath = "C:\\Users\\brend\\IdeaProjects\\BrendanRendijsCA2\\src\\main\\java\\com\\example\\brendanrendijsca2\\Lines.csv";
        String splitCSVBy = ",";

        // Read the CSV file and create edges between nodes.
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String line = "";
        line = bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            String[] values = line.split(splitCSVBy);
            int startID = Integer.parseInt(values[0]);
            int destinationID = Integer.parseInt(values[1]);
            int lineID = Integer.parseInt(values[2]);

            // Find the start node and destination node in the station list.
            GraphNodes<Station> startNode = null;
            GraphNodes<Station> destinationNode = null;
            for (GraphNodes<Station> node : stationList) {
                if (node.station.getID() == startID) {
                    startNode = node;
                }
                if (node.station.getID() == destinationID) {
                    destinationNode = node;
                }
            }

            // Create an undirected edge between the start node and destination node.
            if (startNode != null && destinationNode != null) {
                startNode.connectToNodeUndirected(destinationNode, lineID);
            }

            // Store the stations by line number.
            int lineNumber = -1;
            for (GraphNodes<Station> node : stationList) {
                if (node.station.getID() == startID) {
                    lineNumber = lineID;
                    break;
                }
            }
            if (lineNumber != -1) {
                List<Station> stationLineNumber = stationsById.get(lineNumber);
                if (stationLineNumber == null) {
                    stationLineNumber = new ArrayList<>();
                    stationsById.put(lineNumber, stationLineNumber);
                }
                Station startStation = startNode.station;
                Station endStation = destinationNode != null ? destinationNode.station : null;

                // Add the start station to the station list for the line number.
                if (!stationLineNumber.contains(startStation)) {
                    stationLineNumber.add(startStation);
                }

                // Add the end station to the station list for the line number.
                if (endStation != null && !stationLineNumber.contains(endStation)) {
                    stationLineNumber.add(endStation);
                }
            }
        }
    }
        @FXML
        private void LoadData(ActionEvent event) throws Exception {
            ReadDataFromCSV();
            createAdjacentNodeEdges();


        }

}