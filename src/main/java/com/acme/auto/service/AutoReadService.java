package com.acme.auto.service;

import com.acme.auto.entity.Auto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.acme.auto.repository.AutoRepository;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Geschäftslogik für das Lesen von Autos
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AutoReadService {
    private final AutoRepository repo;

    /**
     * Auto anhand der ID suchen
     * @param id Die ID des gesuchten Autos
     * @return Das gefundene Auto
     * @throws NotFoundException Kein Auto mit der ID wurde gefunden
     */
    public Auto findById(final UUID id) {
        log.debug(STR."findById: id=\{id}");
        final var auto = repo.findById(id)
            .orElseThrow(() -> new NotFoundException(id));
        log.debug(STR."findById: \{auto}");
        return auto;
    }

    /**
     * Autos anhand Suchkriterien abfragen
     * @param suchkriterien Suchkriterien für Autos
     * @return Alle Autos entsprechend den Suchkriterien
     * @throws NotFoundException Kein Auto entspricht den Suchkriterien
     */
    public Collection<Auto> find(final Map<String, String> suchkriterien) {
        log.debug(STR."find: suchkriterien=\{suchkriterien}");

        if(suchkriterien.isEmpty()) {
            return repo.findAll();
        }

        final var autos = repo.find(suchkriterien);
        if(autos.isEmpty()) {
            throw new NotFoundException(suchkriterien);
        }

        log.debug(STR."find: \{autos}");
        return autos;
    }
}
