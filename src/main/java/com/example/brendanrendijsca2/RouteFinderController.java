package com.example.brendanrendijsca2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        void ShowSingleRoute(ActionEvent event) {

        }

        @FXML
        void UploadImage(ActionEvent event) {

        }
        public void ShowFastestRoute(ActionEvent event) {

        }



    List<GraphNodes<Station>> stationList = new ArrayList<>();
    Map<Integer, List<Station>> stationsById = new HashMap<>();
    @FXML
    public void ReadCSVStationData(ActionEvent event) throws Exception{ //fillListWithStations
        String filepath = "C:\\Users\\brend\\IdeaProjects\\BrendanRendijsCA2\\src\\main\\java\\com\\example\\brendanrendijsca2\\London.csv";
        String splitCSVBy = ",";

            BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
            String line ="";
            line = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String [] splitArray = line.split(splitCSVBy);

                Station station = new Station(Integer.parseInt(splitArray[0]), Float.parseFloat(splitArray[1]), Float.parseFloat(splitArray[2]), splitArray[3]);
                GraphNodes<Station> stationGN = new GraphNodes<>(station);
                stationList.add(stationGN);
                createNodeEdges();
                startPoint.getItems().add(splitArray[3]);
                EndPoint.getItems().add(splitArray[3]);
            }
            createNodeEdges();
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
                Station endStation = destinationNode.station;

                if (!stationLineNumber.contains(startStation)) {
                    stationLineNumber.add(startStation);
                }
                if (endStation != null && !stationLineNumber.contains(endStation)) {
                    stationLineNumber.add(endStation);
                }
            }

        }
    }


}