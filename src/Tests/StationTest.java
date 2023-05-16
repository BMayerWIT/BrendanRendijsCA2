import com.example.brendanrendijsca2.Station;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class StationTest {

    @Test
    public void testStationsByLineNumber() {
        // Create sample stations
        Station station1 = new Station(1, 51.5074f, -0.1278f, "Station 1", 100, 200);
        Station station2 = new Station(2, 51.5074f, -0.1278f, "Station 2", 300, 400);

        // Create a list of stations for line number 1
        List<Station> stationsByLineNumber = new ArrayList<>();
        stationsByLineNumber.add(station1);
        stationsByLineNumber.add(station2);

        // Set the stations by line number for station1
        station1.setStationsByLineNumber(stationsByLineNumber);

        // Get the stations by line number from station1
        List<Station> retrievedStations = station1.getStationsByLineNumber();

        // Assert that the retrieved list contains the same stations
        Assertions.assertNotNull(retrievedStations);
        Assertions.assertEquals(2, retrievedStations.size());
        Assertions.assertTrue(retrievedStations.contains(station1));
        Assertions.assertTrue(retrievedStations.contains(station2));
    }

    @Test
    public void testGettersAndSetters() {
        // Create a sample station
        Station station = new Station(1, 51.5074f, -0.1278f, "Station 1", 100, 200);

        // Test getters
        Assertions.assertEquals(1, station.getID());
        Assertions.assertEquals(51.5074f, station.getLatitude());
        Assertions.assertEquals(-0.1278f, station.getLongitude());
        Assertions.assertEquals("Station 1", station.getStationName());
        Assertions.assertNull(station.getStationsByLineNumber());
        Assertions.assertEquals(100, station.getX());
        Assertions.assertEquals(200, station.getY());

        // Test setters
        station.setID(2);
        station.setLatitude(52.5200f);
        station.setLongitude(13.4050f);
        station.setStationName("Station 2");
        station.setStationsByLineNumber(new ArrayList<>());
        station.setX(300);
        station.setY(400);

        Assertions.assertEquals(2, station.getID());
        Assertions.assertEquals(52.5200f, station.getLatitude());
        Assertions.assertEquals(13.4050f, station.getLongitude());
        Assertions.assertEquals("Station 2", station.getStationName());
        Assertions.assertNotNull(station.getStationsByLineNumber());
        Assertions.assertEquals(0, station.getStationsByLineNumber().size());
        Assertions.assertEquals(300, station.getX());
        Assertions.assertEquals(400, station.getY());
    }
}
