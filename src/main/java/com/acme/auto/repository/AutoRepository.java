package com.acme.auto.repository;

import com.acme.auto.entity.Auto;
import com.acme.auto.entity.Marke;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import static com.acme.auto.repository.DB.AUTOS;
import static java.util.Collections.emptyList;
import static java.util.UUID.randomUUID;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * Repository für den DB-Zugriff bei Autos
 */
@Repository
@Slf4j
@SuppressWarnings("PublicConstructor")
public class AutoRepository {
    /**
     * Auto nach seiner ID suchen
     * @param id Die ID des gesuchten Autos
     * @return Optionales Auto mit ID oder leeres Optional
     */
    public Optional<Auto> findById(final UUID id) {
        log.debug(STR."findById: id=\{id}");
        final var autoOpt = AUTOS.stream()
            .filter(auto -> Objects.equals(auto.getId(), id))
            .findFirst();
        log.debug(STR."findById: \{autoOpt}");
        return autoOpt;
    }

    /**
     * Autos nach Suchkriterien filtern und zurückgeben
     * @param suchkriterien Suchkriterien für Autos
     * @return Autos nach Suchkriterien zurückgeben
     */
    public Collection<Auto> find(final Map<String, String> suchkriterien) {
        log.debug(STR."find: suchkriterien=\{suchkriterien}");

        if(suchkriterien.isEmpty()) {
            return findAll();
        }

        for (final var entry : suchkriterien.entrySet()) {
            switch (entry.getKey()) {
                case "name" -> {
                    return findByName(entry.getValue());
                }
                case "marke" -> {
                    return findByMarke(entry.getValue());
                }
                case "reparatur" -> {
                    return findByReparatur(entry.getValue());
                }
                default -> {
                    log.debug(STR."find: ungueltiges Suchkriterium=\{entry.getKey()}");
                    return emptyList();
                }
            }
        }

        return emptyList();
    }

    /**
     * Rückgabe aller Autos in der DB
     * @return Alle Autos in DB
     */
    public Collection<Auto> findAll() { return AUTOS; }

    /**
     * Auto anhand des Namen suchen
     * @param name Name des Autos
     * @return Alle gefundenen Autos
     */
    public Collection<Auto> findByName(final String name) {
        log.debug(STR."findByName: name=\{name}");
        final var autos = AUTOS.stream()
            .filter(auto -> auto.getName().contains(name))
            .toList();
        log.debug(STR."findbyName: \{autos}");
        return autos;
    }

    /**
     * Auto anhand der Automarke suchen
     * @param markeStr Automarke des Autos
     * @return Alle gefundenen Autos
     */
    public Collection<Auto> findByMarke(final String markeStr) {
        log.debug(STR."findByMarke: marke=\{markeStr}");

        final var marke = Marke.of(markeStr).orElse(null);
        if(marke == null) {
            log.debug("findByMarke: Keine Autos gefunden.");
            return emptyList();
        }

        final var autos = AUTOS.stream()
            .filter(auto -> auto.getMarke().equals(marke))
            .toList();

        log.debug(STR."findByMarke: \{autos}");
        return autos;
    }

    /**
     * Auto anhand Beschreibung der Reparaturen suchen
     * @param beschreibung Beschreibung der Reparatur
     * @return Alle gefundenen Autos
     */
    public Collection<Auto> findByReparatur(final String beschreibung) {
        log.debug(STR."findByReparatur: beschreibung=\{beschreibung}");

        final var autos = AUTOS.stream()
            .filter(auto -> auto.getReparaturen() != null &&
                !auto.getReparaturen().isEmpty())
            .filter(auto -> auto.getReparaturen().stream()
                .anyMatch(reparatur -> reparatur.getBeschreibung()
                    .equalsIgnoreCase(beschreibung)))
            .toList();

        log.debug(STR."findByReparatur: \{autos}");
        return autos;
    }

    /**
     * Existiert ein Kennzeichen bereits
     * @param kennzeichen Das Autokennzeichen
     * @return Existiert das Kennzeichen?
     */
    public boolean isKennzeichenExisting(final String kennzeichen) {
        log.debug(STR."existiertKennzeichen: \{kennzeichen}");

        final var count = AUTOS.stream()
            .filter(a -> a.getKennzeichen().equals(kennzeichen))
            .count();

        log.debug(STR."existiertKennzeichen: count=\{count}");
        return count > 0L;
    }

    /**
     * Ein neues Auto anlegen
     * @param auto Autoobjekt des neu anzulegenden Autos
     * @return Neuangelegtes Auto mit generierter ID
     */
    public Auto create(final Auto auto) {
        log.debug(STR."create: input=\{auto}");

        auto.setId(randomUUID());
        auto.getBesitzer().setId(randomUUID());
        auto.getReparaturen()
            .forEach(reparatur -> reparatur.setId(randomUUID()));

        AUTOS.add(auto);
        log.debug(STR."create: \{auto}");
        return auto;
    }

    /**
     * Ein vorhandenes Auto aktualisieren
     * @param auto Objekt mit neuen Daten
     */
    public void update(final Auto auto) {
        log.debug(STR."update: input=\{auto}");

        final OptionalInt index = IntStream
            .range(0, AUTOS.size())
            .filter(a -> AUTOS.get(a).getId().equals(auto.getId()))
            .findFirst();
        log.trace(STR."update: index=\{index}");

        if(index.isEmpty()) {
            return;
        }

        // Neue ID für untergeordnete Objekte
        auto.getBesitzer().setId(randomUUID());
        auto.getReparaturen()
            .forEach(reparatur -> reparatur.setId(randomUUID()));

        AUTOS.set(index.getAsInt(), auto);
        log.debug(STR."update: \{auto}");
    }
}
