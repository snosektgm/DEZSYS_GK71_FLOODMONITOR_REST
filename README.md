# DEZSYS_GK71_FLOODMONITOR_REST
#### Hochwasser Warnsystem 
  1) Datenerfassen: Stationen am Fluss messen kontinuierlich Daten, wie den Wasserstand.
  2) Schnittstelle: REST-API in JSON und XML, für aktuelle und historische Werte, aber auch Warnungen
  3) Frontend: Eine Webanwendung mit Filtermöglichkeiten nach Station, Zeitraum oder Warnstufe
#### Ziel
Das Ziel dieses Projekt war die Entwicklung einer REST basierten Spring Boot Anwendung zur Überwachung von Hochwassermessstationen und Messwerten. Mein Floodmonitor hat verschiedene Endpunkte, um Stationen nach Flüssen oder Warnstufen zu filtern und dann aufzurufen. Bei mir war das größte Problem, dass die Daten von den Endpunkten, im JSON als auch im XML-Format aufgerufen werden.

#### Struktur
```
    DEZSYS Floodmonitor/
├── src/main/java/com/example/floodmonitor/
│   ├── controller/
│   │   ├── StationController.java
│   │ 
│   ├── model/
│   │   ├── Location.java
|   |   ├── Measurement.java
|   |   ├── Station.java
|   |   ├── StationStatus.java
|   |   ├── WarningLevel.java
│   ├── service/
|   |   ├──SimulationService.java
│   ├── exception/
|   |   ├── GlobalExceptionHandler
│   ├── repository/
|   |   ├── FloodMonitorRepository.java
├── src/resources/static/
│   └── index.html
├── src/test/java/com/example/floodmonitor/controller/
│   └── StationControllerTest.java
```
