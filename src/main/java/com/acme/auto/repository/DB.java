package com.acme.auto.repository;

import com.acme.auto.entity.Auto;
import com.acme.auto.entity.Besitzer;
import com.acme.auto.entity.Marke;
import com.acme.auto.entity.Reparatur;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Emulation der Datenbasis für persistente Autos.
 */
final class DB {
    /**
     * Liste der Autos zur Emulation der DB
     */
    @SuppressWarnings("StaticCollection")
    static final List<Auto> AUTOS;
    static {
        // Helper-Methoden ab Java 9: List.of(), Set.of, Map.of, Stream.of
        // List.of() baut eine unveraenderliche Liste: kein Einfuegen, Aendern, Loeschen
        AUTOS = Stream.of(
            // HTTP GET
            Auto.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                .name("VW Polo")
                .marke(Marke.VOLKSWAGEN)
                .pferdeStaerke(130)
                .preis(new BigDecimal(10000))
                .besitzer(Besitzer.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                    .vorname("Harry")
                    .nachname("Potter")
                    .build())
                .reparaturen(null)
                .build(),
            Auto.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .name("Ford Mustang")
                .marke(Marke.FORD)
                .pferdeStaerke(150)
                .preis(new BigDecimal(20000))
                .besitzer(Besitzer.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                    .vorname("Ronald")
                    .nachname("Weasley")
                    .build())
                .reparaturen(Stream.of(Reparatur.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                    .beschreibung("Reifenwechsel")
                    .datum(LocalDate.parse("2022-01-01"))
                    .kosten(new BigDecimal(300))
                    .build()).collect(Collectors.toList()))
                .build(),
            Auto.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                .name("Tesla Model Y")
                .marke(Marke.TESLA)
                .pferdeStaerke(160)
                .preis(new BigDecimal(35000))
                .besitzer(Besitzer.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                    .vorname("Hermine")
                    .nachname("Granger")
                    .build())
                .reparaturen(Stream.of(Reparatur.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                    .beschreibung("Außenspiegel")
                    .datum(LocalDate.parse("2022-06-04"))
                    .kosten(new BigDecimal(250))
                    .build()).collect(Collectors.toList()))
                .build(),
            Auto.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000003"))
                .name("Mercedes C-Klasse")
                .marke(Marke.MERCEDES)
                .pferdeStaerke(155)
                .preis(new BigDecimal(30000))
                .besitzer(Besitzer.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000003"))
                    .vorname("Albus")
                    .nachname("Dumbledore")
                    .build())
                .reparaturen(Stream.of(
                    Reparatur.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                    .beschreibung("Reifenwechsel")
                    .datum(LocalDate.parse("2022-04-01"))
                    .kosten(new BigDecimal(350))
                    .build(),
                    Reparatur.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000003"))
                    .beschreibung("Ölwechsel")
                    .datum(LocalDate.parse("2023-01-01"))
                    .kosten(new BigDecimal(150))
                    .build()).collect(Collectors.toList()))
                .build()
        )
        // CAVEAT Stream.toList() erstellt eine "immutable" List
        .collect(Collectors.toList());
    }

    private DB() {}
}
