package com.example.floodmonitor.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.time.LocalDateTime;
@JacksonXmlRootElement(localName = "measurement")
public class Measurement {

    private String stationId;
    private LocalDateTime timestamp;
    private double waterLevel;   
    private double flowRate;     
    private double rainfall;     
    private double temperature; 
    private int batteryLevel;  
    private StationStatus status;
    private WarningLevel warningLevel;

    public Measurement(String stationId, LocalDateTime timestamp, double waterLevel,double flowRate, double rainfall, double temperature,int batteryLevel, StationStatus status, WarningLevel warningLevel) {
        this.stationId = stationId;
        this.timestamp = timestamp;
        this.waterLevel = waterLevel;
        this.flowRate = flowRate;
        this.rainfall = rainfall;
        this.temperature = temperature;
        this.batteryLevel = batteryLevel;
        this.status = status;
        this.warningLevel = warningLevel;
    }

    public String getStationId() { return stationId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public double getWaterLevel() { return waterLevel; }
    public double getFlowRate() { return flowRate; }
    public double getRainfall() { return rainfall; }
    public double getTemperature() { return temperature; }
    public int getBatteryLevel() { return batteryLevel; }
    public StationStatus getStatus() { return status; }
    public WarningLevel getWarningLevel() { return warningLevel; }
}