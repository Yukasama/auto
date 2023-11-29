## Softwarearchitektur Setup

### Format
* ``C:\...\kunde = C:\Users\Student\IdeaProjects\kunde-v0\kunde>``

### Tag 1
* Neues Projekt in IntelliJ IDEA erstellen (mit Gradle 8.2)
* Settings.zip und Plugins.zip (einzelne Plugins) in IntelliJ einfügen
* gradle-config.zip und kunde1.zip in Projektverzeichnis entpacken (Zuerst gradle-config.zip und IntelliJ notwendige packages installieren lassen)
* ``C:\...\kunde> gradle bootRun`` (Serverstart)
* Postman installieren (und scripts von ``C:\...\kunde\extras\postman`` einfügen)

### Tag 2
* Neues Gradle-Release in C:\Zimmermann\ installieren
* Neue gradle-config.zip und kunde1.zip in Projektverzeichnis entpacken (IntelliJ geschlossen währenddessen)
* ``C:\...\kunde> gradle compileTestJava``
* ``C:\...\kunde> .\gradlew compileJava`` (IntelliJ kann danach geöffnet werden)
* ``C:\...\kunde\extras\compose\sonarqube> docker compose up`` (Sonarqube Server starten)
* Im Browser ``localhost:9000`` eingeben → Token erstellen und in gradle.properties ersetzen
* ``C:\...\kunde> gradle sonar``

### Tag 3
* Neues Docker compose von Github herunterladen und .exe in ``C:\Programme\Docker\cli-plugins\`` ersetzen
* cd extras/kubernetes/kubescape.ps1, zeigt Sicherheitslücken
* cd extras/kubernetes/pluto.ps1, zeigt veraltete Software
* cd extras/kubernetes/polaris, zeigt Sicherheitslücken in web oberfläche

### Übung 9
* Nvd API-Key ersetzen (https://nvd.nist.gov/developers/request-an-api-key)
* OpenAPI
  * .json oder .yaml als Konfiguration
  * Schema first oder API first
  * Swagger UI

### Tag 10
* Application.class (Reflection, Importiert .class Dateien, welche zur Laufzeit benötigt werden)
* Config-Klassen können mehrere Interfaces durch Mehrfach-Vererbung implementieren, welche default Methoden besitzen (sprich Functional Interfaces)
* CREATE INDEX -> B+ Baum
* Multithreading kein Problem, da Attriute final
* Suche wird in LOKALER Variable gespeichert
