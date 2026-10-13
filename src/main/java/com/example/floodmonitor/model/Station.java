package com.example.floodmonitor.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.time.LocalDateTime;
@JacksonXmlRootElement(localName = "station")
public class Station {

    private String id;
    private String name;
    private String river;
    private Location location;
    private double normalWaterLevel;  
    private double warningWaterLevel;  
    private double criticalWaterLevel;
    private boolean isActive;
    private LocalDateTime timestamp;
    private double waterLevel;   
    private double flowRate;    
    private double rainfall;     
    private double temperature;  
    private int batteryLevel;   
    private StationStatus status;
    private WarningLevel warningLevel;

    public Station() {} 

    public Station(String id, String name, String river, Location location,double normalWaterLevel, double warningWaterLevel, double criticalWaterLevel,boolean isActive) {
        this.id = id;
        this.name = name;
        this.river = river;
        this.location = location;
        this.normalWaterLevel = normalWaterLevel;
        this.warningWaterLevel = warningWaterLevel;
        this.criticalWaterLevel = criticalWaterLevel;
        this.isActive = isActive;
        this.timestamp = LocalDateTime.now();
        this.waterLevel = normalWaterLevel;
        this.flowRate = Math.round(normalWaterLevel * 2.2 * 10.0) / 10.0;
        this.rainfall = 0.0;
        this.temperature = 12.0;
        this.batteryLevel = 100;
        this.status = StationStatus.ONLINE;
        this.warningLevel = WarningLevel.NORMAL;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRiver() { return river; }
    public void setRiver(String river) { this.river = river; }
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
    public double getNormalWaterLevel() { return normalWaterLevel; }
    public void setNormalWaterLevel(double normalWaterLevel) { this.normalWaterLevel = normalWaterLevel; }
    public double getWarningWaterLevel() { return warningWaterLevel; }
    public void setWarningWaterLevel(double warningWaterLevel) { this.warningWaterLevel = warningWaterLevel; }
    public double getCriticalWaterLevel() { return criticalWaterLevel; }
    public void setCriticalWaterLevel(double criticalWaterLevel) { this.criticalWaterLevel = criticalWaterLevel; }
    public boolean getIsActive() { return isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public double getWaterLevel() { return waterLevel; }
    public void setWaterLevel(double waterLevel) { this.waterLevel = waterLevel; }
    public double getFlowRate() { return flowRate; }
    public void setFlowRate(double flowRate) { this.flowRate = flowRate; }
    public double getRainfall() { return rainfall; }
    public void setRainfall(double rainfall) { this.rainfall = rainfall; }
    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }
    public int getBatteryLevel() { return batteryLevel; }
    public void setBatteryLevel(int batteryLevel) { this.batteryLevel = batteryLevel; }
    public StationStatus getStatus() { return status; }
    public void setStatus(StationStatus status) { this.status = status; }
    public WarningLevel getWarningLevel() { return warningLevel; }
    public void setWarningLevel(WarningLevel warningLevel) { this.warningLevel = warningLevel; }
}