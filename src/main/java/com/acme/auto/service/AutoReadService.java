package com.acme.auto.service;

import com.acme.auto.entity.Auto;
import com.acme.auto.repository.SpecificationBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.acme.auto.repository.AutoRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Geschäftslogik für das Lesen von Autos
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AutoReadService {
    private final AutoRepository repo;
    private final SpecificationBuilder specBuilder;

    /**
     * Auto anhand der ID suchen
     * @param id Die ID des gesuchten Autos
     * @return Das gefundene Auto
     * @throws NotFoundException Kein Auto mit der ID wurde gefunden
     */
    public Auto findById(final UUID id) {
        log.debug("findById: id={}", id);
        final var auto = repo.findById(id)
            .orElseThrow(() -> new NotFoundException(id));
        log.debug("findById: {}", auto);
        return auto;
    }

    /**
     * Autos anhand Suchkriterien abfragen
     * @param suchkriterien Suchkriterien für Autos
     * @return Alle Autos entsprechend den Suchkriterien
     * @throws NotFoundException Kein Auto entspricht den Suchkriterien
     */
    public Collection<Auto> find(final Map<String, List<String>> suchkriterien) {
        log.debug("find: suchkriterien={}", suchkriterien);
        if (suchkriterien.isEmpty()) {
            return repo.findAll();
        }

        if (suchkriterien.size() == 1) {
            return findSingleCriteria(suchkriterien);
        }

        final var specs = specBuilder.build(suchkriterien)
            .orElseThrow(() -> new NotFoundException(suchkriterien));

        final var autos = repo.findAll(specs);
        if (autos.isEmpty()) {
            throw new NotFoundException(suchkriterien);
        }

        log.debug("find: {}", autos);
        return autos;
    }

    private Collection<Auto> findSingleCriteria(final Map<String, List<String>> suchkriterien) {
        final var namen = suchkriterien.get("name");
        if (namen != null && namen.size() == 1) {
            final var autos = repo.findByName(namen.getFirst());
            if (autos.isEmpty()) {
                throw new NotFoundException(suchkriterien);
            }
            log.debug("find (name): {}", autos);
            return autos;
        }

        final var marken = suchkriterien.get("marke");
        if (marken != null && marken.size() == 1) {
            final var autos = repo.findByMarke(marken.getFirst());
            if (autos.isEmpty()) {
                throw new NotFoundException(suchkriterien);
            }
            log.debug("find (marke): {}", autos);
            return autos;
        }

        final var reparaturen = suchkriterien.get("reparatur");
        if (reparaturen != null && reparaturen.size() == 1) {
            final var autos = repo.findByReparatur(reparaturen.getFirst());
            if (autos.isEmpty()) {
                throw new NotFoundException(suchkriterien);
            }
            log.debug("find (reparatur): {}", autos);
            return autos;
        }

        throw new NotFoundException(suchkriterien);
    }
}
