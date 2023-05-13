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
import javafx.scene.image.ImageView;

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
        void ShowMultipleRoutes(ActionEvent event) {

        }

        @FXML
        public List<Station> ShowSingleRoute(ActionEvent event) {
            int startStationIndex = startPoint.getSelectionModel().getSelectedIndex();
            int endStationIndex = EndPoint.getSelectionModel().getSelectedIndex();
            GraphNodes<Station> startNode = stationList.get(startStationIndex);
            GraphNodes<Station> endNode = stationList.get(endStationIndex);
            Set<GraphNodes<Station>> visitedNodes = new HashSet<>();
            Stack<GraphNodes<Station>> stack = new Stack<>();
            Map<GraphNodes<Station>, GraphNodes<Station>> parents = new HashMap<>();


            if (startNode == null || endNode == null) {
                System.out.println("Start or end station not found");
                return null;
            }

            if (startNode.equals(endNode)) {
                System.out.println("Start and end stations are the same");
                return null;
            }

            stack.push(startNode);
            visitedNodes.add(startNode);

            while (!stack.isEmpty()) {
                GraphNodes<Station> currentNode = stack.pop();

                if (currentNode.equals(endNode)) {
                    // Found the destination station, construct and return the path
                    List<Station> path = new ArrayList<>();
                    while (currentNode != startNode) {
                        path.add(currentNode.station);
                        currentNode = parents.get(currentNode);
                    }
                    path.add(startNode.station);
                    Collections.reverse(path);

                    // Print the route
                    System.out.println("Route from " + startNode.station.getStationName() + " to " + endNode.station.getStationName() + ":");

                    routeView.getItems().add("Route from " + startNode.station.getStationName() + " to " + endNode.station.getStationName() + ":");
                    for (Station station : path) {
                        System.out.println("- " + station.getStationName());
                        routeView.getItems().add("- " + station.getStationName());
                    }

                    return path;
                }

                for (GraphNodes<Station> neighbor : currentNode.neighbours) {
                    if (!visitedNodes.contains(neighbor)) {
                        stack.push(neighbor);
                        visitedNodes.add(neighbor);
                        parents.put(neighbor, currentNode);
                    }
                }
            }

            // No valid route found
            System.out.println("No valid route found");
            return null;
        }

        @FXML
        void UploadImage(ActionEvent event) {

        }
        public void ShowFastestRoute(ActionEvent event) {

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

    public void createNodeEdges() throws Exception {
        String filepath = "C:\\Users\\brend\\IdeaProjects\\BrendanRendijsCA2\\src\\main\\java\\com\\example\\brendanrendijsca2\\Lines.csv";
        String splitCSVBy = ",";

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String line = "";
        line = bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            String[] values = line.split(splitCSVBy);
            int startID = Integer.parseInt(values[0]);
            int destinationID = Integer.parseInt(values[1]);
            int lineID = Integer.parseInt(values[2]);


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

            if (startNode != null && destinationNode != null) {
                startNode.connectToNodeUndirected(destinationNode, lineID);
            }

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

                if (!stationLineNumber.contains(startStation)) {
                    stationLineNumber.add(startStation);
                }
                if (endStation != null && !stationLineNumber.contains(endStation)) {
                    stationLineNumber.add(endStation);
                }
            }

        }
    }
        @FXML
        private void LoadData(ActionEvent event) throws Exception {
            ReadDataFromCSV();
            createNodeEdges();


        }


}