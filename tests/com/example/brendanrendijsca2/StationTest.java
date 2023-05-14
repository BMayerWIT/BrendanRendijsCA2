package com.example.brendanrendijsca2;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StationTest {
    private Station station;

    @Before
    public void setUp() {
        station = new Station(1, 51.5074f, -0.1278f, "London");
    }

    @Test
    public void testGetID() {
        assertEquals(1, station.getID());
    }

    @Test
    public void testGetLatitude() {
        assertEquals(51.5074f, station.getLatitude(), 0.0001f);
    }

    @Test
    public void testGetLongitude() {
        assertEquals(-0.1278f, station.getLongitude(), 0.0001f);
    }

    @Test
    public void testGetStationName() {
        assertEquals("London", station.getStationName());
    }

    @Test
    public void testToString() {
        assertEquals("1, 51.5074, -0.1278, London\n", station.toString());
    }
}