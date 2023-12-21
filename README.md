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
* Multithreading kein Problem, da Attribute final
* Suche wird in LOKALER Variable gespeichert

### Übung 10
* Abgeleitet von AutoCloseable, dann kann in try() ein Argument automatisch schließen ohne finally

### Tag 11
* Postgres einrichten
* Schema:
Namespace
  Betriebssystem / Dateisystem
    Ordner = Verzeichnis = Directory
  Java
    package
  DB-Systeme (außer MySQL)
    Schema (NICHT: Unter-Schema)
* Tabellen immer in eigenem Schema (nicht default)

### Tag 12
FieldInjection (schlecht dass es klappt)
KundeService: KS               BestellungService: BS
  @Autowired bs: BS              @Autowired ks: KS

ConstructorInjection (gut, dass es nicht klappt)
<< Service >>
KundeService(BestellungService)
  bs: BS
<< Service >>
BestellungService(KundeService)
  ks: KS
Konstruktor können sich eigentlich nicht aufrufen
Funktionen
Hier einladung für Spaghetticode, Funktionen rufen gegenseitig alle anderen auf
Service ist

<< Entity >>
Kunde
  id
  nachname
  bestellungen
<< Entity >>
Bestellung
  id
  datum
  kunde
Attribute
=> Bidirektionale Beziehung (vorwärts, rückwarts-verweise)


Spring Framework
  Broadcom (hat VMware übernommen)
    VMware
      VMware Tanzu

Wirtschaftszeitungen:
  - FAZ
  - Handelsblatt
  - NZZ
  - Wirtschaftswoche
  - SZ
  - Welt
  - Frankfurter Rundschau
  - TAZ

Unsinnige Frage: Objekt kann nicht ablaufen
Thread 1
  Controller.get() -> Service.find() -> repo.findByName()

Thread 2
  Controller.get() -> Service.find() -> repo.findByName()
  = result = Lokale variable im stack im Service NICHT IM ATTRIBUT, daher völlig ausreichend, dass Service-Klasse Singleton-Pattern


### Übung 12
* Datenbankmigration meist sehr aufwändig
* Mehrere Checkboxen -> In der DB als Enum
---------------------------
Kunde
  id: 1, 2

Interesse
  kunde_id: 1, 1
  interesse: "Lesen", "Schreiben"

=> Schlechte Lösung, weil DB-Join
---------------------------
cascade update mit vorsicht genießen -> unnötige updates
kein eager fetching -> unnötige joins

Warum ist m-n Beziehung schlecht:

Bestellung1 -> L1, L2         Lieferung1
Bestellung2                   Lieferung2 -> B1, B2
Bestellung3                   Lieferung3

Daraus 1-m machen:

Bestellung hat List<Lieferung>
Lieferung hat List<Bestellung>

Relationenmodell: Relationen zwischen SPALTEN
In Tabellen gibt es KEINE Reihenfolge
idx: Reihenfolge für Liste in Tabelle, startet für jeden Fremdschlüssel von 0, 1, ...
Heißt idx, da index ein Schlüsselwort ist


### Vorlesung 14
            App-Server          DB-Server
findByEmail                     Datensatz
                              <-
                      Datensatz (Konvert. durch Hibernate)
                    <-
             Objekt
--------------------------------------------------
Nachname: [...]
Geschlecht o o o
Interessen [] [] []

Nur Checkboxen: 2^3
Mit optional Nachname: 2^4
Mit optional Geschlecht 2^5


### Übung 14
@Bean -> Factory Methode
Flyway.java -> Aufräumen und migrieren der DB

V1.0__Create.sql
V1.1__Insert.sql
In der Reihenfolge der Versionsnummern werden die Skripte ausgeführt (Name danach irrelevant)

Hikari -> Hibernate richtet sich an Hikari für Connection Pooling

Bei Versionsnummer: "1" -> "" Teil der Versionsnummer
