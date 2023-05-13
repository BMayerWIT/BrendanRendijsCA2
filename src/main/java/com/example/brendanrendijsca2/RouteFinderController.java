package com.example.brendanrendijsca2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RouteFinderController {
    List<Station> stationList = new ArrayList<>();


    public List<Station> fillListWithStations() {
        String filepath = "C:\\Users\\brend\\IdeaProjects\\BrendanRendijsCA2\\src\\main\\java\\com\\example\\brendanrendijsca2\\London.csv";
        String splitCSVBy = ",";

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
            String line ="";
            line = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String [] splitArray = line.split(splitCSVBy);

                Station station = new Station(Integer.parseInt(splitArray[0]), Float.parseFloat(splitArray[1]), Float.parseFloat(splitArray[2]), splitArray[3]);
                stationList.add(station);
            }
            System.out.println(stationList);
        }
        catch (FileNotFoundException e) {
            System.out.println("No File Found");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("IOE EXCEPTION");
            e.printStackTrace();
        }
        return stationList;
    }


}