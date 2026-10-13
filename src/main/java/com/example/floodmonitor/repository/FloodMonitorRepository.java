package com.example.floodmonitor.repository;

import com.example.floodmonitor.model.Location;
import com.example.floodmonitor.model.Measurement;
import com.example.floodmonitor.model.Station;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FloodMonitorRepository {

    private final List<Station> stations = new ArrayList<>();
    private final List<Measurement> messungen = new ArrayList<>();

    public FloodMonitorRepository() {
        stationsErstellen();
    }

    private void stationsErstellen() {
        Station wien = new Station("1", "Wien Donauinsel", "Donau",
                new Location(48.2313, 16.3772), 250.0, 320.0, 430.0, true);

        Station linz = new Station("2", "Linz Donau", "Donau",
                new Location(48.3069, 14.2858), 240.0, 310.0, 420.0, true);

        Station koeln = new Station("3", "Köln Rhein", "Rhein",
                new Location(50.9375, 6.9603), 350.0, 420.0, 500.0, true);


        Station krakow = new Station("4", "Krakow", "Wisla",
                new Location(48.5667, 13.4319), 260.0, 330.0, 440.0, false);

        
        linz.setWaterLevel(330.0);   
        linz.setFlowRate(726.0);
        koeln.setWaterLevel(520.0);  
        koeln.setFlowRate(1144.0);

        stations.add(wien);
        stations.add(linz);
        stations.add(koeln);
        stations.add(krakow);
    }

    public List<Station> alleStationen() {
        return stations;
    }
    public Station findeStationMitId(String id) {
        for (int i = 0; i < stations.size(); i++) {
            Station s = stations.get(i);
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }
    public void messungSpeichern(Measurement m) {
        messungen.add(m);
    }

    public List<Measurement> alleMessungenVonStation(String stationId) {
        List<Measurement> result = new ArrayList<>();
        for (int i = 0; i < messungen.size(); i++) {
            Measurement m = messungen.get(i);
            if (m.getStationId().equals(stationId)) {
                result.add(m);
            }
        }
        return result;
    }
    public Measurement neuesteMessungVonStation(String stationId) {
        Measurement neueste = null;
        for (int i = 0; i < messungen.size(); i++) {
            Measurement m = messungen.get(i);
            if (!m.getStationId().equals(stationId)) {
                continue;
            }
            if (neueste == null || m.getTimestamp().isAfter(neueste.getTimestamp())) {
                neueste = m;
            }
        }
        return neueste;
    }
}