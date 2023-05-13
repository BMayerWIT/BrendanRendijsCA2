package com.example.brendanrendijsca2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
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
        void LoadData(ActionEvent event){

        }

        @FXML
        void UploadImage(ActionEvent event) {

        }



    List<GraphNodes<Station>> stationList = new ArrayList<>();
    @FXML
    public void ShowFastestRoute(ActionEvent event) throws Exception{ //fillListWithStations
        String filepath = "src/main/java/com/example/brendanrendijsca2/London.csv";
        String splitCSVBy = ",";

            BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
            String line ="";
            line = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String [] splitArray = line.split(splitCSVBy);

                Station station = new Station(Integer.parseInt(splitArray[0]), Float.parseFloat(splitArray[1]), Float.parseFloat(splitArray[2]), splitArray[3]);
                GraphNodes<Station> stationGN = new GraphNodes<>(station);
                stationList.add(stationGN);
                startPoint.getItems().add(splitArray[3]);
                EndPoint.getItems().add(splitArray[3]);
            }
            bufferedReader.close();
    }
}