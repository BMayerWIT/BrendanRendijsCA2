package com.example.brendanrendijsca2;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class RouteFinderController {

        @FXML
        private ComboBox<String> EndPoint;

        @FXML
        private ImageView ImageView;

        @FXML
        private ComboBox<String> startPoint;

        @FXML
        private ComboBox<String> waypointStation;

        @FXML
        private ListView routeView;

        private Image defaultImage = new Image("file:src/main/resources/images/London_Underground_Zone_1_Highlighted.svg.png");
        private WritableImage writtenImage;
        List<GraphNodes<Station>> stationList = new ArrayList<>();


        @FXML
        void SelectEndPoint(ActionEvent event) {

        }

        @FXML
        void SelectStartPoint(ActionEvent event) {

        }

        @FXML
        void ResetMap(ActionEvent event) {
            ImageView.setImage(defaultImage);

        }


        @FXML
        public List<Station> ShowSingleRoute(ActionEvent event) {
            //clear list view
            routeView.getItems().clear();

            // Get the selected start and end stations from the UI
            int startStationIndex = startPoint.getSelectionModel().getSelectedIndex();
            int endStationIndex = EndPoint.getSelectionModel().getSelectedIndex();
            int waypointIndex = waypointStation.getSelectionModel().getSelectedIndex();

            // Get the start and end nodes from the station list using their indices
            GraphNodes<Station> startNode = stationList.get(startStationIndex);
            GraphNodes<Station> endNode = stationList.get(endStationIndex);
            GraphNodes<Station> waypointNode = null;
            if (waypointIndex != -1) {
                GraphNodes<Station> waypointValidNode = stationList.get(waypointIndex);
                waypointNode = waypointValidNode;
            }


            List<Station> stationRoute = new ArrayList<>();

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

            List<GraphNodes<Station>> noWaypointPath = null;
            List<GraphNodes<Station>> startToWaypoint = null;
            List<GraphNodes<Station>> waypointToEnd = null;

            if (waypointNode != null) {
                // Traverse the graph using BFS
                List<GraphNodes<Station>> path = GraphNodes.TraverseGraphBreadthFirst(startNode, waypointNode);
                List<GraphNodes<Station>> path2 = GraphNodes.TraverseGraphBreadthFirst(waypointNode, endNode);
                startToWaypoint = path;
                waypointToEnd = path2;
            } else {
                List<GraphNodes<Station>> path = GraphNodes.TraverseGraphBreadthFirst(startNode, endNode);
                noWaypointPath = path;
            }


            if (waypointNode == null) {
                // If the path is null, no route exists between the two stations
                if (noWaypointPath == null) {
                    System.out.println("No route exists between " + startNode.station.getStationName() + " and " + endNode.station.getStationName());
                    return null;
                }


                // Construct the list of stations in the path
                List<Station> stationPath = new ArrayList<>();
                for (GraphNodes<Station> node : noWaypointPath) {
                    stationPath.add(node.station);
                }

                stationRoute = stationPath;

                // Print the route
                System.out.println("Route from " + startNode.station.getStationName() + " to " + endNode.station.getStationName() + ":");

                // Add the route to the UI list view

                for (Station station : stationPath) {
                    System.out.println("- " + station.getStationName());
                    routeView.getItems().add(station.getStationName());
                }
                drawBlueLine();
            }

            if (waypointNode != null) {
                // If the path is null, no route exists between the two stations
                if ( startToWaypoint == null || waypointToEnd == null) {
                    System.out.println("No route exists between " + startNode.station.getStationName() + " and " + endNode.station.getStationName());
                    return null;
                }


                // Construct the list of stations in the path
                List<Station> stationPath = new ArrayList<>();
                List<Station> startToWaypointPath = new ArrayList<>();
                List<Station> waypointToEndPath = new ArrayList<>();
                for (GraphNodes<Station> node : startToWaypoint) {
                    stationPath.add(node.station);
                    startToWaypointPath.add(node.station);
                }
                for (GraphNodes<Station> node : waypointToEnd) {
                    stationPath.add(node.station);
                    waypointToEndPath.add(node.station);
                }

                stationRoute = stationPath;

                // Print the route
                System.out.println("Route from " + startNode.station.getStationName() + " to " + endNode.station.getStationName() + ":");
                // Add the route to the UI list view

                for (Station station : startToWaypointPath) {
                    System.out.println("- " + station.getStationName());
                    routeView.getItems().add(station.getStationName());
                }

                for (Station station : waypointToEndPath) {
                    System.out.println("- " + station.getStationName());
                    routeView.getItems().add(station.getStationName());
                }
                drawBlueLine();
            }



            // Return the path
            return stationRoute;
        }

        public List<Station> ShowFastestRoute(ActionEvent event) {


            //clear list view
            routeView.getItems().clear();

            // Get the selected start and end stations from the UI
            int startStationIndex = startPoint.getSelectionModel().getSelectedIndex();
            int endStationIndex = EndPoint.getSelectionModel().getSelectedIndex();
            int waypointIndex = waypointStation.getSelectionModel().getSelectedIndex();

            // Get the start and end nodes from the station list using their indices
            GraphNodes<Station> startNode = stationList.get(startStationIndex);
            GraphNodes<Station> endNode = stationList.get(endStationIndex);
            GraphNodes<Station> waypointNode = null;
            if (waypointIndex != -1) {
                GraphNodes<Station> waypointValidNode = stationList.get(waypointIndex);
                waypointNode = waypointValidNode;
            }
            List<Station> stationRoute = new ArrayList<>();

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

            List<GraphNodes<Station>> noWaypointPath = null;
            List<GraphNodes<Station>> startToWaypoint = null;
            List<GraphNodes<Station>> waypointToEnd = null;
            if (waypointNode != null) {
                // Traverse the graph using BFS
                List<GraphNodes<Station>> path = GraphNodes.dijkstra(startNode, waypointNode);
                List<GraphNodes<Station>> path2 = GraphNodes.dijkstra(waypointNode, endNode);
                startToWaypoint = path;
                waypointToEnd = path2;
            }
            else {
                // Traverse the graph using BFS
                List<GraphNodes<Station>> path = GraphNodes.dijkstra(startNode, endNode);
                noWaypointPath = path;
            }

            if (waypointNode == null) {
                // If the path is null, no route exists between the two stations
                if (noWaypointPath == null) {
                    System.out.println("No route exists between " + startNode.station.getStationName() + " and " + endNode.station.getStationName());
                    return null;
                }


                // Construct the list of stations in the path
                List<Station> stationPath = new ArrayList<>();
                for (GraphNodes<Station> node : noWaypointPath) {
                    stationPath.add(node.station);
                }

                stationRoute = stationPath;

                // Print the route
                System.out.println("Route from " + startNode.station.getStationName() + " to " + endNode.station.getStationName() + ":");

                // Add the route to the UI list view

                for (Station station : stationPath) {
                    System.out.println("- " + station.getStationName());
                    routeView.getItems().add(station.getStationName());
                }
                drawBlueLine();
            }

            if (waypointNode != null) {
                // If the path is null, no route exists between the two stations
                if ( startToWaypoint == null || waypointToEnd == null) {
                    System.out.println("No route exists between " + startNode.station.getStationName() + " and " + endNode.station.getStationName());
                    return null;
                }


                // Construct the list of stations in the path
                List<Station> stationPath = new ArrayList<>();
                List<Station> startToWaypointPath = new ArrayList<>();
                List<Station> waypointToEndPath = new ArrayList<>();
                for (GraphNodes<Station> node : startToWaypoint) {
                    stationPath.add(node.station);
                    startToWaypointPath.add(node.station);
                }
                for (GraphNodes<Station> node : waypointToEnd) {
                    stationPath.add(node.station);
                    waypointToEndPath.add(node.station);
                }

                stationRoute = stationPath;

                // Print the route
                System.out.println("Route from " + startNode.station.getStationName() + " to " + endNode.station.getStationName() + ":");
                // Add the route to the UI list view

                for (Station station : startToWaypointPath) {
                    System.out.println("- " + station.getStationName());
                    routeView.getItems().add(station.getStationName());
                }

                for (Station station : waypointToEndPath) {
                    System.out.println("- " + station.getStationName());
                    routeView.getItems().add(station.getStationName());
                }
                drawBlueLine();
            }



            // Return the path
            return stationRoute;
        }

        private void drawBlueLine() {
            int height = (int)defaultImage.getHeight();
            int width = (int)defaultImage.getWidth();

            WritableImage wimage = imageProcessor.setGrey(defaultImage);
            PixelReader pixelReader = defaultImage.getPixelReader();
            PixelWriter pixelWriter = wimage.getPixelWriter();



            Canvas canvas = new Canvas(ImageView.getImage().getWidth(), ImageView.getImage().getHeight());
            GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
            graphicsContext.setStroke(Color.BLUE);
            graphicsContext.setLineWidth(5);
            graphicsContext.setStroke(Color.BLUE);
            int x1=0;
            int y1=0;
            int x2=0;
            int y2=0;

            for (int i = 0; i < routeView.getItems().size(); i++) {
                Station previousStation;
                Station nextStation;

                for (int j = 0; j < stationList.size(); j++) {

                    if (routeView.getItems().get(i).equals(stationList.get(j).station.getStationName())) {
                        previousStation = stationList.get(j).station;

                        x1 = (int)( previousStation.getX() * 1.822857);
                        y1 = (int)( previousStation.getY()* 1.825);
                        System.out.println(x1 + " " + y1);

                    }

                    if (i + 1 < routeView.getItems().size()) {
                        if (routeView.getItems().get(i + 1).equals(stationList.get(j).station.getStationName())) {
                            nextStation = stationList.get(j).station;

                            x2 = (int) (nextStation.getX() * 1.822857);
                            y2 = (int) (nextStation.getY() * 1.825);
                            System.out.println(x2 + " " + y2);
                        }
                    }
                }
                graphicsContext.strokeLine(x1, y1, x2, y2);
           }

            ImageView newImageView = new ImageView(wimage);
            Group group = new Group(newImageView, canvas);
            newImageView = new ImageView(group.snapshot(null, null));
            ImageView.setImage(newImageView.getImage());
        }


/**

 Reads data from a CSV file and fills the station list with stations that are located in zone 1.

 Also populates the start point, end point, and waypoint station dropdown menus.

 @throws Exception if the CSV file cannot be read or the values cannot be parsed as integers or floats.
 */
    @FXML
    public void ReadDataFromCSV() throws Exception { // fillListWithStations
        defaultImage = ImageView.getImage();
        String filepath = "src/main/java/com/example/brendanrendijsca2/London.csv";
        String splitCSVBy = ",";

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            String[] splitArray = line.split(splitCSVBy);
            if (splitArray.length > 4 && ("1".equals(splitArray[4]) || "1.5".equals(splitArray[4]))) {
                Station station = new Station(Integer.parseInt(splitArray[0]), Float.parseFloat(splitArray[1]), Float.parseFloat(splitArray[2]), splitArray[3],
                        Integer.parseInt(splitArray[5]), Integer.parseInt(splitArray[6]));
                GraphNodes<Station> stationGN = new GraphNodes<>(station);
                stationList.add(stationGN);
                startPoint.getItems().add(splitArray[3]);
                EndPoint.getItems().add(splitArray[3]);
                waypointStation.getItems().add(splitArray[3]);
            }
        }
        bufferedReader.close();
    }

    /**

     Reads a CSV file containing lines information and creates adjacent nodes between nodes in the station list

     based on the data in the CSV file. Also stores the stations by line number in a hashmap.

     @throws Exception if the CSV file cannot be read or the values cannot be parsed as integers.
     */
    public void createAdjacentNodeEdges() throws Exception {
        String filepath = "src/main/java/com/example/brendanrendijsca2/Lines.csv";
        String splitCSVBy = ",";

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String line = bufferedReader.readLine(); // Skip the header line

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

            for (GraphNodes<Station> node : stationList) {
                if (node.station.getID() == startID) {
                    List<Station> stationLineNumber = node.station.getStationsByLineNumber();
                    if (stationLineNumber == null) {
                        stationLineNumber = new ArrayList<>();
                        node.station.setStationsByLineNumber(stationLineNumber);
                    }
                    Station startStation = startNode.station;
                    Station endStation = destinationNode != null ? destinationNode.station : null;

                    if (!stationLineNumber.contains(startStation)) {
                        stationLineNumber.add(startStation);
                    }

                    if (endStation != null && !stationLineNumber.contains(endStation)) {
                        stationLineNumber.add(endStation);
                    }
                    break; // Found the start node, no need to continue the loop
                }
            }
        }

        bufferedReader.close();
    }


    //Method found online to get the pixel coordinates of the mouse click on the imageview
//    @FXML
//    private void SelectStartPoint(MouseEvent event) {
//        ImageView.setCursor(Cursor.CROSSHAIR);
//        ImageView.setPickOnBounds(true);
//        ImageView.setOnMouseClicked(e -> {
//            double secondposx = e.getX();
//            double secondposy = e.getY();
//            System.out.println(secondposx + "," + secondposy);
//        });
//    }

    @FXML
        private void LoadData(ActionEvent event) throws Exception {
            ReadDataFromCSV();
            createAdjacentNodeEdges();


        }
    @FXML
    private void ClearWaypoint() {
        waypointStation.getSelectionModel().clearSelection();
    }


}