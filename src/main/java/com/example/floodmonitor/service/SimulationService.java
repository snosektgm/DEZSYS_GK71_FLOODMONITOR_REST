package com.example.floodmonitor.service;

import com.example.floodmonitor.model.Measurement;
import com.example.floodmonitor.model.Station;
import com.example.floodmonitor.model.StationStatus;
import com.example.floodmonitor.model.WarningLevel;
import com.example.floodmonitor.repository.FloodMonitorRepository;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.floodmonitor.exception.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SimulationService {
    private final Random r = new Random(); 
    private double simulationsIntervall;
    private double letzteRunde = 0;
    private final FloodMonitorRepository repository;

    public SimulationService(FloodMonitorRepository repository) {
        this.repository = repository;

    }
    private double runden(double wert) {
    return Math.round(wert * 10.0) / 10.0;
}
    //Simulation
    public void simulation() {
        long jetzt = System.currentTimeMillis();
        if(jetzt - letzteRunde < simulationsIntervall) {
            return;
        }
        letzteRunde = jetzt;
        LocalDateTime now = LocalDateTime.now();
        List<Station> stations = repository.alleStationen();
        for(int i = 0; i < stations.size(); i++) {
            Station station = stations.get(i);
            if(!station.getIsActive() || station.getStatus() == StationStatus.OFFLINE) {
                station.setWarningLevel(WarningLevel.UNKNOWN);
                continue;
            }
            int neueBatterie = Math.max(0, station.getBatteryLevel()-r.nextInt(3));
            station.setBatteryLevel(neueBatterie);
            if(neueBatterie<20) {
                station.setStatus(StationStatus.MAINTENANCE);
            }
            double regen = runden(r.nextDouble()*5.0);
            station.setRainfall(regen);
            double veränderung = (r.nextDouble() *4.0-1.8) + (regen*0.3);
            double neuerWasserstand = Math.max(0, station.getWaterLevel()+veränderung);
            station.setWaterLevel(runden(neuerWasserstand));

            station.setFlowRate(runden(station.getWaterLevel()*2.2));
            station.setTemperature(runden(station.getTemperature()+(r.nextDouble()-0.5)));
            station.setTimestamp(now);
            station.setWarningLevel(calculateWarninglevel(station));
            repository.messungSpeichern(new Measurement(station.getId(), station.getTimestamp(), station.getWaterLevel(), station.getFlowRate(), station.getRainfall(), station.getTemperature(), station.getBatteryLevel(), station.getStatus(), station.getWarningLevel()));
        }
    }
    public WarningLevel calculateWarninglevel(Station station) {
        if(!station.getIsActive() || station.getStatus()  == StationStatus.OFFLINE) {
            return WarningLevel.UNKNOWN;
        }
        if(station.getWaterLevel() >= station.getCriticalWaterLevel()) {
            return WarningLevel.CRITICAL;
        }
        else if(station.getWaterLevel()>=station.getWarningWaterLevel()) {
            return WarningLevel.WARNING;
        }
        else {
            return WarningLevel.NORMAL;
        }
    }
    public List<Station> getfilterStationen(String river, Boolean isActive, WarningLevel warnung) {
        simulation();
        List<Station> filterListe= new ArrayList<>();
        List<Station> stations = repository.alleStationen();
        for(int i = 0; i< stations.size(); i++) {
            Station s = stations.get(i);
            if(river != null && !s.getRiver().equals(river)) {
                continue;
            }
            if(isActive != null && s.getIsActive() != isActive) {
                continue;
            }
            if(warnung != null && s.getWarningLevel() != warnung) {
                continue;
            }
            filterListe.add(s);
        }
        return filterListe;
    }
    public Station getStationById(String id) {
        simulation();
        Station station = repository.findeStationMitId(id);
        if(station== null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Diese Station existiert nicht");
        }
        return station;
    }
    public Measurement getLatesMeasurement(String stationId) {
        simulation();
        if(repository.findeStationMitId(stationId)== null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Diese Station existiert nicht");
        }
        Measurement neu = repository.neuesteMessungVonStation(stationId);
        if(neu == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Es gibt keine Messungen");
        }
        return neu;
    }
    public List<Measurement> getMeasurements(String stationId, LocalDateTime from, LocalDateTime to, WarningLevel warning, int limit) {
        simulation();
        if(repository.findeStationMitId(stationId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Diese Station existiert nicht");
        }
        List<Measurement> messung = repository.alleMessungenVonStation(stationId);
        List<Measurement> ergebnis = new ArrayList<>();
        int zähler = 0;
        for(int i = messung.size() -1; i >=0; i--) {
            Measurement m = messung.get(i);

            if(from != null && m.getTimestamp().isBefore(from)) {
                continue;

            }
            if(to != null && m.getTimestamp().isAfter(to)) {
                continue;
            }
            if(warning != null && m.getWarningLevel() != warning) {
                continue;
            }
            ergebnis.add(m);
            zähler++;
            if(zähler >= limit) {
                break;
            }
        }
        return ergebnis;
    
    }
    public List<Measurement> getAlerts(WarningLevel warning, String river) {
        simulation();
        List<Measurement> alerts = new ArrayList<>();
        List<Station> stations = repository.alleStationen();
        for(int i = 0; i < stations.size(); i++) {
            Station s = stations.get(i);

            Measurement neu = repository.neuesteMessungVonStation(s.getId());
            if(neu==null) {
                continue;
            }       
            boolean isWarnung = neu.getWarningLevel()==WarningLevel.WARNING;
            boolean isCritical= neu.getWarningLevel()==WarningLevel.CRITICAL;
            if(!isWarnung && !isCritical) {
                continue;
            }
            if(warning != null && neu.getWarningLevel() != warning) {
                continue;
            }
            if(river != null && !s.getRiver().equals(river)) {
                continue;
            }
            alerts.add(neu);
        }
        return alerts;
    }
}