package com.example.floodmonitor.controller;

import com.example.floodmonitor.model.Measurement;
import com.example.floodmonitor.model.Station;
import com.example.floodmonitor.model.WarningLevel;
import com.example.floodmonitor.service.SimulationService;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController 
@RequestMapping("/api/v1")
public class StationController {
    private final SimulationService simulationService;
    public StationController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }
    @GetMapping(value = "/stations", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Station> getStations(@RequestParam(required = false) String river,@RequestParam(required = false) Boolean isActive, @RequestParam(required = false) WarningLevel warningLevel) {
        return simulationService.getfilterStationen(river, isActive, warningLevel);
    }
    @GetMapping(value = "/stations/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Station getStationById(@PathVariable String id) {
        return simulationService.getStationById(id);
    }
    @GetMapping(value="/stations/{id}/measurements/latest", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Measurement getLatesMeasurement(@PathVariable String id) {
        return simulationService.getLatesMeasurement(id);
    }
    @GetMapping(value = "/stations/{id}/measurements", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Measurement> getMeasurements(@PathVariable String id,@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,@RequestParam(required = false) WarningLevel warningLevel, @RequestParam(required = false, defaultValue = "100") Integer limit) {
    if(limit < 1 || limit > 500) {
        throw new IllegalArgumentException("Das Limit darf nur zwischen 1 und 500 sein");
    }
    if(from != null && to != null && from.isAfter(to)) {
        throw new IllegalArgumentException("From muss vor To sein");
    }
    return simulationService.getMeasurements(id, from, to, warningLevel, limit);
    }@GetMapping(value = "/alerts", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Measurement> getAlerts(@RequestParam(required = false) WarningLevel warningLevel, @RequestParam(required = false) String river) {
        return simulationService.getAlerts(warningLevel, river);
    } 
    
}